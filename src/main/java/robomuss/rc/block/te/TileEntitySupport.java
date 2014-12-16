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

public class TileEntitySupport extends TileEntity {
	public int colour;
	public boolean flange = false;
//	public TileEntityFooter footer = null;
//	public int supportStackIndex = -1;

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
//		supportStackIndex = compound.getInteger("index");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);

		compound.setInteger("colour", colour);
		compound.setBoolean("flange", flange);
//		compound.setInteger("index",  supportStackIndex);

//		if (this.footer != null) {
//			compound.setIntArray("footerLoc", new int[]{footer.xCoord, footer.yCoord, footer.zCoord});
//
//			if (this.supportStackIndex >= 0) {
//				this.footer.getStackSnapshot(this.supportStackIndex).writeToNBT(compound);
//			}
//		} else {
//			compound.setIntArray("footerLoc", new int[]{-1, -1, -1});
//		}
//
//		compound.setInteger("index", this.supportStackIndex);
	}

//	public void setSupportFooter(TileEntityFooter footer) {
//		this.footer = footer;
//		this.supportStackIndex = footer.getNextSupportIndex();
//
//		if (this.worldObj.getBlock(this.xCoord, this.yCoord + 1, this.zCoord) instanceof BlockSupport) {
////			System.out.println("footer set, setting partner " + (this.footer == null ? "null" : "footer"));
//			((TileEntitySupport) this.worldObj.getTileEntity(this.xCoord, this.yCoord + 1, this.zCoord)).setSupportFooter(footer);
//		}
//	}

//	public void setSupportFooter(TileEntityFooter footer, int supportStackIndex) {
//		this.footer = footer;
//		this.supportStackIndex = supportStackIndex;
//	}

//	public TileEntityFooter findFooter(World world, int x, int y, int z) {
//		if (world.getBlock(x, y - 1, z) instanceof BlockFooter) {
////			System.out.println("footer found!");
//			TileEntityFooter teFooter = (TileEntityFooter) world.getTileEntity(x, y - 1, z);
//
//			if (teFooter != null) {
//				this.findSupportStackIndex(teFooter);
//			}
//
//			return teFooter;
//		} else if (world.getBlock(x, y - 1, z) instanceof BlockSupport) {
//			TileEntitySupport teSupportBelow = (TileEntitySupport) world.getTileEntity(x, y - 1, z);
//
//			if (this.footer == null) {
//				if (teSupportBelow.footer != null) {
//					this.findSupportStackIndex(teSupportBelow.footer);
//					this.setSupportFooter(teSupportBelow.footer, this.supportStackIndex);
//					return this.footer;
//				}
//			} else {
//				this.findSupportStackIndex(this.footer);
//				return this.footer;
//			}
//		}
//
//		return null;
//	}

//	public TileEntityFooter findFooter(int x, int y, int z) {
//		if (this.worldObj.getBlock(x, y - 1, z) instanceof BlockFooter) {
//			return (TileEntityFooter) this.worldObj.getTileEntity(x, y - 1, z);
//		} else if (this.worldObj.getBlock(x, y - 1, z) instanceof BlockSupport) {
//			return ((TileEntitySupport) this.worldObj.getTileEntity(x, y - 1, z)).findFooter(x, y - 1, z);
//		}
//
//		return null;
//	}

//	public void findSupportStackIndex(TileEntityFooter footer) {
//		if (this.supportStackIndex == -1) {
//			int supportsInStack = footer.getNextSupportIndex();
//			int footerY  = footer.yCoord;
//			int supportY = this.yCoord;
//			int topY     = footerY + supportsInStack + 1;
//			this.supportStackIndex = topY - supportY;
//		}
//	}

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
