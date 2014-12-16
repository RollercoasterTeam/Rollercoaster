package robomuss.rc.block.te;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import robomuss.rc.client.gui.GuiTrackDesigner;

public class TileEntityTrackDesigner extends TileEntity {
	public GuiTrackDesigner guiTrackDesigner;
//	public GuiRCKeyBindingList guiRCKeyBindingList;
//	public GuiRCControls guiRCControls;
	public int currentPosX;
	public int currentPosY;
	public int currentPosZ;
	public int direction;
	
	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound nbt = new NBTTagCompound();
		this.writeToNBT(nbt);
		return new S35PacketUpdateTileEntity(this.getPos(), 1, nbt);
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		
		currentPosX = compound.getInteger("currentPosX");
		currentPosY = compound.getInteger("currentPosY");
		currentPosZ = compound.getInteger("currentPosZ");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		
		compound.setInteger("currentPosX", currentPosX);
		compound.setInteger("currentPosY", currentPosY);
		compound.setInteger("currentPosZ", currentPosZ);
	}
}
