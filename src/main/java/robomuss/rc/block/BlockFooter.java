package robomuss.rc.block;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import robomuss.rc.block.te.TileEntityFooter;
import robomuss.rc.item.RCItems;
import robomuss.rc.util.IPaintable;

public class BlockFooter extends BlockContainer implements IPaintable {
	public BlockFooter() {
        super(Material.iron);
        setHardness(1F);
		setResistance(3F);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileEntityFooter(world, meta);
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public int getRenderType() {
        return -1;
    }

    @Override
<<<<<<< HEAD
    public boolean onBlockActivated(World world, BlockPos blockPos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ){
		if(!world.isRemote) {
			if(player.getHeldItem() != null) {
				if(player.getHeldItem().getItem() == RCItems.brush) {
					TileEntityFooter tes = (TileEntityFooter) world.getTileEntity(blockPos);
					tes.colour = player.getHeldItem().getItemDamage();
					world.markBlockForUpdate(blockPos);
					return true;
				}
				else {
					return false;
				}
				
			}
			else {
				return false;
			}
		}
		else {
			return false;
		}
=======
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
	    if (player.getHeldItem() != null) {
		    Item heldItem = player.getHeldItem().getItem();

		    if (heldItem == RCItems.brush || heldItem == Items.water_bucket || heldItem == Item.getItemFromBlock(RCBlocks.support)) {
			    return true;
		    }
	    }

	    return false;



//		if(!world.isRemote) {
//			if(player.getHeldItem() != null) {
//				if(player.getHeldItem().getItem() == RCItems.brush) {
//					TileEntityFooter tes = (TileEntityFooter) world.getTileEntity(x, y, z);
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
//		    TileEntityFooter teFooter = (TileEntityFooter) world.getTileEntity(x, y, z);
//		    teFooter.clearSupportStackColors();
//
//		    if (!player.capabilities.isCreativeMode) {
//			    player.inventory.setInventorySlotContents(player.inventory.currentItem, new ItemStack(Items.bucket));
//		    }
//
//		    return true;
//	    }
//
//	    return false;
>>>>>>> master
	}
    
    @Override
	public int getPaintMeta(World world, BlockPos blockPos) {
		return ((TileEntityFooter) world.getTileEntity(blockPos)).colour;
	}

//	@Override
//	public void onBlockHarvested(World world, int x, int y, int z, int meta, EntityPlayer player) {
//		RCMod.supportManager.breakFooter(world, x, y, z);
//	}
}
