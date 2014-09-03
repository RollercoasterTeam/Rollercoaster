package robomuss.rc.block.te;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import robomuss.rc.block.RCBlocks;
import robomuss.rc.rollercoaster.RollercoasterType;
import robomuss.rc.tracks.TrackHandler;
import robomuss.rc.tracks.TrackType;
import robomuss.rc.tracks.extra.TrackExtra;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileEntityTrack extends TileEntity {

	public int direction;
	public int colour;
	public TrackExtra extra;
	public RollercoasterType type;

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		
		direction = compound.getInteger("direction");
		colour = compound.getInteger("colour");
		for(int i = 0; i < TrackHandler.types.size(); i++) {
			if(TrackHandler.types.get(i).getId() == compound.getString("typeName")) {
				type = TrackHandler.types.get(i);
			}
		}
		
		int extraID = compound.getInteger("extraID");
		extra = extraID == -1 ? null : TrackHandler.extras.get(extraID);
	}
	
	@Override
	public void writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		
		compound.setInteger("direction", direction);
		compound.setInteger("colour", colour);
		if(type == null) {
			System.out.println("Type is null");
		}
		else {
			System.out.println("Type isnt null");
		}
		//compound.setString("typeName", type.getId());
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
		TrackType track_type = TrackHandler.findTrackTypeFull(block.getUnlocalizedName());
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
}
