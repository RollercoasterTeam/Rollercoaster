package robomuss.rc.block.te;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import robomuss.rc.block.BlockTrackBase;
import robomuss.rc.chat.ChatHandler;
import robomuss.rc.track.TrackHandler;
import robomuss.rc.track.TrackManager;
import robomuss.rc.track.piece.TrackPiece;
import net.minecraft.block.Block;

/*@Optional.InterfaceList(
       @Optional.Interface(iface = "pneumaticCraft.api.tileentity.IPneumaticMachine", modid = "PneumaticCraft")
)*/
public class TileEntityTrackBase extends TileEntity {
	//private IAirHandler airHandler;

	public BlockTrackBase track;
	public int trackMeta;
	public

	public TileEntityTrackBase() {} //required to instantiate for network handler
	public TileEntityTrackBase(World world, int trackMeta, BlockTrackBase track) {
		this.worldObj = world;
		this.trackMeta = trackMeta;
		this.track = track;
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		track.direction = ForgeDirection.valueOf(compound.getString("direction"));
		worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, track.direction.ordinal(), 2);
		track.colour = compound.getInteger("colour");
		track.converted = compound.getBoolean("converted");
		for (int i = 0; i < TrackHandler.styles.size(); i++) {
			if (TrackHandler.styles.get(i).getId().contains(compound.getString("styleName"))) {
				track.style = TrackHandler.styles.get(i);
			}
		}

		int extraID = compound.getInteger("extraID");
		track.extra = extraID == -1 ? null : TrackHandler.extras.get(extraID);
	}

	@Override
	public void writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		if (track.extra != null) {
			System.out.println(track.extra.id);
		}
		if (!track.converted || track.style == null) {
			track.style = TrackHandler.findTrackStyle("corkscrew");
			track.converted = true;
//			track.converted = false;
		}

		if (track.direction == null) {
			System.out.println("track direction is null");
//			track.direction = TrackManager.getDirectionFromPlayerFacing(Minecraft.getMinecraft().thePlayer);
//			track.direction = ForgeDirection.getOrientation(worldObj.getBlockMetadata(track.position.chunkPosX, track.position.chunkPosY, track.position.chunkPosZ));
			track.direction = ForgeDirection.getOrientation(this.worldObj.getBlockMetadata(this.xCoord, this.yCoord, this.zCoord));
		}

		compound.setString("direction", track.direction.name());
		compound.setInteger("colour", track.colour);
		compound.setString("styleName", track.style.getId());
		compound.setBoolean("converted", track.converted);
		if (track.extra != null) {
			compound.setInteger("extraID", track.extra.id);
		} else {
			compound.setInteger("extraID", -1);
		}
	}

//	public void findTrack() {
//		this.track = (BlockTrackBase) this.worldObj.getBlock(this.xCoord, this.yCoord, this.zCoord);
//	}

	@Override
	public Packet getDescriptionPacket() {
//		findTrack();
//		System.out.println("getDescriptionPacket!");
		NBTTagCompound compound = new NBTTagCompound();
		this.writeToNBT(compound);
		return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 1, compound);
	}

	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity packet) {
//		findTrack();
//		ChatHandler.broadcastChatMessageToPlayers("packet received!");
		readFromNBT(packet.func_148857_g());
	}

	@SideOnly(Side.CLIENT)
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		Block block = this.getWorldObj().getBlock(xCoord, yCoord, zCoord);
		TrackPiece type = TrackHandler.findTrackTypeFull(block.getUnlocalizedName());
		if (type != null) {
			return type.getRenderBoundingBox(this.getWorldObj(), xCoord, yCoord, zCoord);
		} else {
			return AxisAlignedBB.getBoundingBox(0, 0, 0, 1, 1, 1);
		}
	}

	@Override
	public boolean canUpdate() {
		return false;
	}

	/*@Optional.Method(modid = "PneumaticCraft")
	@Override
	public IAirHandler getAirHandler() {
		if(airHandler == null) {
			airHandler = AirHandlerSupplier.getAirHandler(5, 7, 50, 2000);
		}
		return airHandler;
	}

	@Optional.Method(modid = "PneumaticCraft")
	@Override
	public boolean isConnectedTo(ForgeDirection side) {
		if(this.extra != null && this.extra instanceof TrackExtraAirLauncher) {
			return true;
		}
		else {
			return false;
		}
	}*/

	/*@Override
	public void updateEntity() {
		super.updateEntity();
		getAirHandler().updateEntityI();
	}

	@Override
	public void validate() {
		super.validate();
		getAirHandler().validateI(this);
	}*/
}
