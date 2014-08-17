package robomuss.rc.block.te;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public class TileEntitySupport extends TileEntity {
	
	public boolean up, down;
	
	public int colour;

	@Override
	public void updateEntity() {
		if (this.worldObj.getTileEntity(this.xCoord, this.yCoord + 1,
				this.zCoord) instanceof TileEntitySupport) {
			up = true;
		} else {
			up = false;
		}

		if (this.worldObj.getTileEntity(this.xCoord, this.yCoord - 1,
				this.zCoord) instanceof TileEntitySupport) {
			down = true;
		} else {
			down = false;
		}
		super.updateEntity();
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		colour = compound.getInteger("colour");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setInteger("colour", colour);
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
}
