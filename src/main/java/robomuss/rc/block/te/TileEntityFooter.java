package robomuss.rc.block.te;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import robomuss.rc.RCMod;

public class TileEntityFooter extends TileEntity {
	public int colour;
	public boolean forceConnection = false;
//	private List<BlockSnapshot> supportStack = new ArrayList<BlockSnapshot>();

	public TileEntityFooter() {}

	public TileEntityFooter(World world, int colour) {
		this.worldObj = world;
		this.colour = colour;
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		colour = compound.getInteger("colour");
		forceConnection = compound.getBoolean("forceConnection");
		RCMod.supportManager.addFooter(this);
	}

	@Override
	public void writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setInteger("colour", colour);
		compound.setBoolean("forceConnection", forceConnection);
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
	public boolean canUpdate() {
		return false;
	}
}
