package robomuss.rc.block.te;

import robomuss.rc.block.RCBlocks;
import robomuss.rc.tracks.TrackHandler;
import robomuss.rc.tracks.TrackType;
import robomuss.rc.tracks.extra.TrackExtra;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

public class TileEntityTrack extends TileEntity {

	public int direction;
	public int colour;
	public int model;
	public TrackExtra extra;

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		
		direction = compound.getInteger("direction");
		colour = compound.getInteger("colour");
		model = compound.getInteger("model");
		
		int extraID = compound.getInteger("extraID");
		extra = extraID == -1 ? null : TrackHandler.extras.get(extraID);
	}
	
	@Override
	public void writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		
		compound.setInteger("direction", direction);
		compound.setInteger("colour", colour);
		compound.setInteger("model", model);
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
		TrackType track_type = null;
		Block block = this.getWorldObj().getBlock(this.xCoord, this.yCoord, this.zCoord);
		for(TrackType track : RCBlocks.tracks) {
			if(block.getUnlocalizedName().substring(5, block.getUnlocalizedName().length() - 6).equalsIgnoreCase(track.unlocalized_name)) {
				track_type = track;
				break;
			}
			
		}
		return track_type.getRenderBoundingBox(this.getWorldObj(), this.xCoord, this.yCoord, this.zCoord);
	}
}
