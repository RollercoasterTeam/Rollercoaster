package robomuss.rc.block.te;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public class TileEntityTrackDesigner extends TileEntity {

	public int currentPosX;
	public int currentPosY;
	public int currentPosZ;
	
	public boolean hasTarget;
	
	public int targetX;
	public int targetY;
	public int targetZ;
	
	public int direction;
	
	/*@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound nbt = new NBTTagCompound();
		this.writeToNBT(nbt);
		return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 1, nbt);
	}

	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity packet) {
		readFromNBT(packet.func_148857_g());
	}*/
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		
		currentPosX = compound.getInteger("currentPosX");
		currentPosY = compound.getInteger("currentPosY");
		currentPosZ = compound.getInteger("currentPosZ");
		
		hasTarget = compound.getBoolean("hasTarget");
		
		targetX = compound.getInteger("targetX");
		targetY = compound.getInteger("targetY");
		targetZ = compound.getInteger("targetZ");
		
		direction = compound.getInteger("direction");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		
		compound.setInteger("currentPosX", currentPosX);
		compound.setInteger("currentPosY", currentPosY);
		compound.setInteger("currentPosZ", currentPosZ);
		
		compound.setBoolean("hasTarget", hasTarget);
		
		compound.setInteger("targetX", targetX);
		compound.setInteger("targetY", targetY);
		compound.setInteger("targetZ", targetZ);
		
		compound.setInteger("direction", direction);
	}
	
	@Override
	public boolean canUpdate() {
		return false;
	}
}
