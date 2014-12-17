package robomuss.rc.block.te;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import robomuss.rc.client.gui.GuiTrackDesigner;

public class TileEntityTrackDesigner extends TileEntity {
	public GuiTrackDesigner guiTrackDesigner;
	BlockPos currentPos;
	EnumFacing facing;
	
	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound nbt = new NBTTagCompound();
		this.writeToNBT(nbt);
		return new S35PacketUpdateTileEntity(this.pos, 1, nbt);
	}

	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity packet) {
		readFromNBT(packet.getNbtCompound());
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);

		currentPos = new BlockPos(compound.getInteger("x"), compound.getInteger("y"), compound.getInteger("z"));
	}
	
	@Override
	public void writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);

		compound.setInteger("x", currentPos.getX());
		compound.setInteger("y", currentPos.getY());
		compound.setInteger("z", currentPos.getZ());
	}
}
