package robomuss.rc.block.te;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import robomuss.rc.block.BlockSupport;
import robomuss.rc.util.ColourUtil;

public class TileEntitySupport extends TileEntity {
	public int colour;
	public boolean flange = false;

	public TileEntitySupport() {}

	public TileEntitySupport(World world) {
		this.worldObj = world;
//		this.findFooter(world, this.xCoord, this.yCoord, this.zCoord);
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);

		this.worldObj = super.getWorldObj();
		colour = compound.getInteger("colour");
		flange = compound.getBoolean("flange");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);

		compound.setInteger("colour", colour);
		compound.setBoolean("flange", flange);
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
	
	@Override
	@SideOnly(Side.CLIENT)
	public AxisAlignedBB getRenderBoundingBox() {
        int supportHeight = 0;
        Block above = worldObj.getBlock(this.xCoord, this.yCoord + 1, this.zCoord);

        if(above == null || above.getClass() != BlockSupport.class) {
        	for(int i = this.yCoord; i > 0; i--) {
        		Block support = worldObj.getBlock(this.xCoord, i, this.zCoord);
        		if(support != null && support instanceof BlockSupport) {
        			supportHeight++;
        		}
        	}
        }
        
        if(supportHeight > 0) {
        	//TODO not working for some reason
	        //TODO not working because bounding boxes can't be larger than 1x1x1 blocks
//        	return AxisAlignedBB.getBoundingBox(0, 0, 0, 1, supportHeight, 1);
        	return super.getRenderBoundingBox();
        } else {
        	return super.getRenderBoundingBox();
        }
	}
}
