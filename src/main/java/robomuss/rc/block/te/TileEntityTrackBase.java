package robomuss.rc.block.te;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.util.ForgeDirection;
import robomuss.rc.block.BlockTrackBase;
import robomuss.rc.track.TrackHandler;
import robomuss.rc.track.piece.TrackPiece;
import net.minecraft.block.Block;

/*@Optional.InterfaceList(
       @Optional.Interface(iface = "pneumaticCraft.api.tileentity.IPneumaticMachine", modid = "PneumaticCraft")
)*/
public class TileEntityTrackBase extends TileEntity {
	//private IAirHandler airHandler;

	public BlockTrackBase track;

	public TileEntityTrackBase(BlockTrackBase track) {
		this.track = track;
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);

		track.direction = ForgeDirection.getOrientation(compound.getInteger("direction") + 2);
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

		if (!track.converted || track.style == null) {
			track.style = TrackHandler.findTrackStyle("corkscrew");
			track.converted = true;
		}

		compound.setInteger("direction", track.direction.ordinal() - 2);
		compound.setInteger("colour", track.colour);
		compound.setString("styleName", track.style.getId());
		compound.setBoolean("converted", track.converted);
		if (track.extra != null) {
			compound.setInteger("extraID", track.extra.id);
		} else {
			compound.setInteger("extraID", -1);
		}
	}

	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound compound = new NBTTagCompound();
		this.writeToNBT(compound);
		return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 1, compound);
	}

	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity packet) {
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
