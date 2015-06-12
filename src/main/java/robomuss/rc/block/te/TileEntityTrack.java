package robomuss.rc.block.te;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import robomuss.rc.rollercoaster.RollercoasterType;
import robomuss.rc.track.TrackHandler;
import robomuss.rc.track.TrackType;
import robomuss.rc.track.extra.TrackExtra;
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
	public boolean hasCatwalk = false;
	
	//private IAirHandler airHandler;

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		
		direction = compound.getInteger("direction");
		colour = compound.getInteger("colour");
		converted = compound.getBoolean("converted");
		for(int i = 0; i < TrackHandler.types.size(); i++) {
			if(TrackHandler.types.get(i).getId().contains(compound.getString("typeName"))) {
				type = TrackHandler.types.get(i);
			}
		}
		
		int extraID = compound.getInteger("extraID");
		extra = extraID == -1 ? null : TrackHandler.extras.get(extraID);
		
		hasCatwalk = compound.getBoolean("hasCatwalk");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		
		if(!converted) {
			type = TrackHandler.types.get(0);
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
		
		compound.setBoolean("hasCatwalk", hasCatwalk);
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
		return AxisAlignedBB.getBoundingBox(this.xCoord - 2, this.yCoord - 2, this.zCoord - 2, this.xCoord + 2, this.yCoord + 2, this.zCoord + 2);
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
