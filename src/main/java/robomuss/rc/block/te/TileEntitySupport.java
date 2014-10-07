package robomuss.rc.block.te;


import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import robomuss.rc.block.BlockSupport;

public class TileEntitySupport extends TileEntity {
	
	public int colour;
	public boolean flange = false;
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
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
		return new S35PacketUpdateTileEntity(this.getPos(), 1, nbt);
	}


	@Override
	@SideOnly(Side.CLIENT)
	public AxisAlignedBB getRenderBoundingBox() {
        int supportHeight = 0;
        Block above = worldObj.getBlockState(this.getPos().add(0, 1, 0)).getBlock();
        if(above == null || above.getClass() != BlockSupport.class) {
        	for(int i = this.getPos().getY(); i > 0; i--) {
        		Block support = worldObj.getBlockState(new BlockPos(this.getPos().getX(), i, this.getPos().getZ())).getBlock();
        		if(support != null && support instanceof BlockSupport) {
        			supportHeight++;
        		}
        		else {
        			break;
        		}
        	}
        }
        
        if(supportHeight > 0) {
        	//TODO not working for some reason
        	//return AxisAlignedBB.getBoundingBox(0, -supportHeight, 0, 1, 0, 1);
        	return super.getRenderBoundingBox();
        }
        else {
        	return super.getRenderBoundingBox();
        }
	}
}
