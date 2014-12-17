package robomuss.rc.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import robomuss.rc.block.te.TileEntitySupport;
import robomuss.rc.item.RCItems;
import robomuss.rc.util.IPaintable;

public class BlockSupport extends BlockContainer implements IPaintable {
	public BlockSupport() {
        super(Material.iron);
        setHardness(1F);
		setResistance(3F);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileEntitySupport(world);
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public int getRenderType() {
        return 110;
    }
    
    @Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if (player.getHeldItem() != null) {
			Item heldItem = player.getHeldItem().getItem();

			if (heldItem == RCItems.brush || heldItem == Items.water_bucket || heldItem == Item.getItemFromBlock(RCBlocks.support)) {
				return true;
			}
		}

	    return false;



//	    if(!world.isRemote) {
//			if(player.getHeldItem() != null) {
//				if(player.getHeldItem().getItem() == RCItems.brush) {
//					TileEntitySupport tes = (TileEntitySupport) world.getTileEntity(x, y, z);
//					tes.colour = player.getHeldItem().getItemDamage();
//					world.markBlockForUpdate(x, y, z);
//					return true;
//				} else if (player.getHeldItem().getItem() == Item.getItemFromBlock(RCBlocks.support)) {
//					return true;
//				}
//			}
//		}
//
//	    if (player.getHeldItem() != null && player.getHeldItem().getItem() == Items.water_bucket) {
//		    TileEntitySupport teSupport = (TileEntitySupport) world.getTileEntity(x, y, z);
//
//		    if (teSupport.footer != null) {
//			    teSupport.footer.clearSupportStackColors();
//		    }
//
//		    if (!player.capabilities.isCreativeMode) {
//			    player.inventory.setInventorySlotContents(player.inventory.currentItem, new ItemStack(Items.bucket));
//		    }
//
//		    return true;
//	    }
//
//	    return false;
	}
    
    @Override
	public int getPaintMeta(World world, int x, int y, int z) {
		return ((TileEntitySupport) world.getTileEntity(x, y, z)).colour;
	}

    @Override
    public void onBlockAdded(World world, int x, int y, int z) {
    	TileEntity above = world.getTileEntity(x, y + 1, z);
    	if (!(above instanceof TileEntitySupport)) {
    		int gap = 1;
    		for(int currentY = y; currentY > 0; currentY--) {
    			if(world.getTileEntity(x, currentY, z) instanceof TileEntitySupport) {
    				TileEntitySupport te = (TileEntitySupport) world.getTileEntity(x, currentY, z);

				    if(gap == 2) {
    					te.flange = true;
    					gap = 0;
    				} else {
    					te.flange = false;
    					gap++;
    				}

    				world.markBlockForUpdate(x, currentY, z);
    			}
    		}
    	}
    }

	@Override
	public boolean canPlaceBlockOnSide(World world, int x, int y, int z, int side) {
		ForgeDirection direction = ForgeDirection.getOrientation(side).getOpposite();
		Block block = world.getBlock(x + direction.offsetX, y + direction.offsetY, z + direction.offsetZ);
		return block instanceof BlockSupport || block instanceof BlockFooter;
	}
    
    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int side) {
    	int gap = 1;

    	for(int currentY = y - 1; currentY > 0; currentY--) {
    		if(world.getTileEntity(x, currentY, z) instanceof TileEntitySupport) {
    			TileEntitySupport te = (TileEntitySupport) world.getTileEntity(x, currentY, z);

			    if(gap == 2) {
    				te.flange = true;
    				gap = 0;
    			} else {
    				te.flange = false;
    				gap++;
    			}

    			world.markBlockForUpdate(x, currentY, z);
    		}
    	}
    }

//	@Override
//	public void onBlockHarvested(World world, int x, int y, int z, int meta, EntityPlayer player) {
//		if (world.getTileEntity(x, y, z) instanceof TileEntitySupport) {
//			int[] footerLoc = new int[] {
//					RCMod.supportManager.getFooterFromSupport((TileEntitySupport) world.getTileEntity(x, y, z)).xCoord,
//					RCMod.supportManager.getFooterFromSupport((TileEntitySupport) world.getTileEntity(x, y, z)).yCoord,
//					RCMod.supportManager.getFooterFromSupport((TileEntitySupport) world.getTileEntity(x, y, z)).zCoord
//			};
//			RCMod.supportManager.breakFooter(world, footerLoc[0], footerLoc[1], footerLoc[2]);
//		}
//	}
}
