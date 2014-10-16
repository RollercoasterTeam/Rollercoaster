package robomuss.rc.block.te;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import robomuss.rc.block.BlockDummy;
import robomuss.rc.block.BlockTrack;
import robomuss.rc.track.TrackHandler;
import robomuss.rc.track.extra.TrackExtra;
import robomuss.rc.track.piece.*;
import robomuss.rc.track.style.TrackStyle;
import net.minecraft.block.Block;

/*@Optional.InterfaceList(
       @Optional.Interface(iface = "pneumaticCraft.api.tileentity.IPneumaticMachine", modid = "PneumaticCraft")
)*/
public class TileEntityTrack extends TileEntity {
	public ForgeDirection direction;
	public int colour;
	public TrackExtra extra;
	public TrackStyle style;
	public TrackPiece track_type;

	private boolean converted = false;

	public BlockDummy dummy;                        //TODO:rewrite these as well, to use TE as main data provider
	public int dummyX, dummyY, dummyZ;

	public BlockTrack track;

	//private IAirHandler airHandler;

	public TileEntityTrack() {
		this.direction = ForgeDirection.SOUTH;
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);

		direction = ForgeDirection.getOrientation(compound.getInteger("direction") + 2);
		colour = compound.getInteger("colour");
		converted = compound.getBoolean("converted");
		for (int i = 0; i < TrackHandler.styles.size(); i++) {
			if (TrackHandler.styles.get(i).getId().contains(compound.getString("styleName"))) {
				style = TrackHandler.styles.get(i);
			}
		}
//		for (int i = 0; i < TrackHandler.pieces.size(); i++) {
//			if (TrackHandler.pieces.get(i).unlocalized_name.equals(compound.getString("typeName"))) {
//				track_type = TrackHandler.pieces.get(i);
//			}
//		}

		int extraID = compound.getInteger("extraID");
		extra = extraID == -1 ? null : TrackHandler.extras.get(extraID);
	}

	@Override
	public void writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);

		if (!converted || style == null) {
			style = TrackHandler.findTrackStyle("corkscrew");
			converted = true;
		}

		compound.setInteger("direction", direction.ordinal() - 2);
		compound.setInteger("colour", colour);
		compound.setString("styleName", style.getId());
//		compound.setString("typeName", track_type.unlocalized_name);
		compound.setBoolean("converted", converted);
		if (extra != null) {
			compound.setInteger("extraID", extra.id);
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

	@Override
	@SideOnly(Side.CLIENT)
	public AxisAlignedBB getRenderBoundingBox() {
		Block block = this.getWorldObj().getBlock(this.xCoord, this.yCoord, this.zCoord);
		TrackPiece type = TrackHandler.findTrackTypeFull(block.getUnlocalizedName());
		if (type != null) {
			return type.getRenderBoundingBox(this.getWorldObj(), this.xCoord, this.yCoord, this.zCoord);
		} else {
			return AxisAlignedBB.getBoundingBox(0, 0, 0, 1, 1, 1);
		}
	}

	@Override
	public boolean canUpdate() {
		return false;
	}

	public void setDummy(int dummyX, int dummyY, int dummyZ, BlockDummy dummy) {
		this.dummy = dummy;
		this.dummyX = dummyX;
		this.dummyY = dummyY;
		this.dummyZ = dummyZ;
	}

	public void placeDummy(World world, int dummyX, int dummyY, int dummyZ, BlockDummy dummy, boolean shouldDummyBreakSlope) {
		world.setBlock(dummyX, dummyY, dummyZ, dummy);
		this.setDummy(dummyX, dummyY, dummyZ, dummy);
		dummy.setParentSlope(this, track);
		dummy.setBreakSlope(shouldDummyBreakSlope);
	}

	public boolean isHorizontal(Block block) {
		return block instanceof BlockTrack && ((BlockTrack) block).track_type instanceof TrackPieceHorizontal;
	}

	public boolean isCorner(Block block) {
		return block instanceof BlockTrack && ((BlockTrack) block).track_type instanceof TrackPieceCorner;
	}

	public boolean hasSlope(Block block) {
		if (block instanceof BlockTrack) {
			if (((BlockTrack) block).track_type instanceof TrackPieceSlopeUp) {
				return true;
			} else if (((BlockTrack) block).track_type instanceof TrackPieceSlope) {
				return true;
			} else if (((BlockTrack) block).track_type instanceof TrackPieceSlopeDown) {
				return true;
			}
		}
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
