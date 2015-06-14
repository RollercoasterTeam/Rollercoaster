package robomuss.rc.block.te;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public class TileEntityTrackDesigner extends TileEntity {

	public int rayX;
	public int rayY;
	public int rayZ;
	
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
		
		rayX = compound.getInteger("currentPosX");
		rayY = compound.getInteger("currentPosY");
		rayZ = compound.getInteger("currentPosZ");
		
		hasTarget = compound.getBoolean("hasTarget");
		
		targetX = compound.getInteger("targetX");
		targetY = compound.getInteger("targetY");
		targetZ = compound.getInteger("targetZ");
		
		direction = compound.getInteger("direction");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		
		compound.setInteger("currentPosX", rayX);
		compound.setInteger("currentPosY", rayY);
		compound.setInteger("currentPosZ", rayZ);
		
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
