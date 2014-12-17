package robomuss.rc.block.te;

<<<<<<< HEAD
import net.minecraft.util.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
=======
>>>>>>> origin/One8PortTake2
import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
<<<<<<< HEAD
import robomuss.rc.block.BlockPath;
=======
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
>>>>>>> origin/One8PortTake2
import robomuss.rc.block.BlockSupport;

public class TileEntitySupport extends TileEntity {
	public int colour;
	public boolean flange = false;

	public TileEntitySupport() {}

	public TileEntitySupport(World world) {
		this.worldObj = world;
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);

		this.worldObj = super.getWorld();
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
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity packet) {
		readFromNBT(packet.getNbtCompound());
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public AxisAlignedBB getRenderBoundingBox() {
        int supportHeight = 0;
        Block above = this.getWorld().getBlockState(this.getPos().up()).getBlock();

        if(above == null || !(above instanceof BlockSupport)) {
	        BlockPos currentPos = this.getPos();

	        while (currentPos.getY() > BlockPos.ORIGIN.getY()) {
		        currentPos = currentPos.down();
		        Block support = this.getWorld().getBlockState(currentPos).getBlock();

		        if (support != null && support instanceof BlockSupport) {
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
