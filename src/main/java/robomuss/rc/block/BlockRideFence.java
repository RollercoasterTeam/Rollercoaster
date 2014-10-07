package robomuss.rc.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import robomuss.rc.block.te.TileEntityRideFence;
import robomuss.rc.item.RCItems;
import robomuss.rc.util.IPaintable;

import java.util.Random;

public class BlockRideFence extends BlockContainer implements IPaintable {

	public BlockRideFence() {
		super(Material.iron);
		setHardness(1F);
		setResistance(3F);
		setBlockBounds(0, 0, 0, 1, 1, 1);
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		return new TileEntityRideFence();
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
    public boolean onBlockActivated(World world, BlockPos blockPos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ){
		if(!world.isRemote) {
			if(player.getHeldItem() != null) {
				if(player.getHeldItem().getItem() == RCItems.brush) {
					TileEntityRideFence terf = (TileEntityRideFence) world.getTileEntity(blockPos);
					terf.colour = player.getHeldItem().getItemDamage();
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
	}
	


	@Override
	public int getPaintMeta(World world, BlockPos blockPos) {
		return ((TileEntityRideFence) world.getTileEntity(blockPos)).colour;
	}
	
	@Override
    public void onNeighborBlockChange(World world, BlockPos pos, IBlockState state, Block neighborBlock) {
        if(!world.isRemote) {
        	if(this == RCBlocks.ride_fence_gate) {
	        	TileEntityRideFence te = (TileEntityRideFence) world.getTileEntity(pos);
	        	te.open = world.isBlockPowered(pos);
	        	world.markBlockForUpdate(pos);
        	}
        }
    }
	
	@Override
	public Item getItemDropped(int i, Random random, int j) {
		return super.getItemDropped(i, random, j);
	}
}
