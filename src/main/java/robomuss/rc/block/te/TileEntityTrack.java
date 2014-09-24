package robomuss.rc.block.te;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import robomuss.rc.track.TrackHandler;
import robomuss.rc.track.extra.TrackExtra;
import robomuss.rc.track.piece.TrackPiece;
import robomuss.rc.track.style.RollercoasterType;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/*@Optional.InterfaceList(
       @Optional.Interface(iface = "pneumaticCraft.api.tileentity.IPneumaticMachine", modid = "PneumaticCraft")
)*/
public class TileEntityTrack extends TileEntity {

	public int direction;
	public int colour;
	public TrackExtra extra;
	public RollercoasterType type;
	
	private boolean converted = false;
	
	//private IAirHandler airHandler;

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		
		direction = compound.getInteger("direction");
		colour = compound.getInteger("colour");
		converted = compound.getBoolean("converted");
		for(int i = 0; i < TrackHandler.styles.size(); i++) {
			if(TrackHandler.styles.get(i).getId().contains(compound.getString("typeName"))) {
				type = TrackHandler.styles.get(i);
			}
		}
		
		int extraID = compound.getInteger("extraID");
		extra = extraID == -1 ? null : TrackHandler.extras.get(extraID);
	}
	
	@Override
	public void writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		
		if(!converted) {
			type = TrackHandler.styles.get(0);
			converted = true;
		}
		
		compound.setInteger("direction", direction);
		compound.setInteger("colour", colour);
		compound.setString("typeName", type.getId());
		compound.setBoolean("converted", converted);
		if(extra != null) {
			compound.setInteger("extraID", extra.id);
		}
		else {
			compound.setInteger("extraID", -1);
		}
	}
	
	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound nbt = new NBTTagCompound();
		this.writeToNBT(nbt);
		return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 1, nbt);
	}

	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity packet) {
		readFromNBT(packet.func_148857_g());
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public AxisAlignedBB getRenderBoundingBox() {
		Block block = this.getWorldObj().getBlock(this.xCoord, this.yCoord, this.zCoord);
		TrackPiece track_type = TrackHandler.findTrackTypeFull(block.getUnlocalizedName());
		if(track_type != null) {
			return track_type.getRenderBoundingBox(this.getWorldObj(), this.xCoord, this.yCoord, this.zCoord);
		}
		else {
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
