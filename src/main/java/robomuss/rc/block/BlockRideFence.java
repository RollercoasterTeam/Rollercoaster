package robomuss.rc.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import robomuss.rc.block.te.TileEntityRideFence;
import robomuss.rc.item.RCItems;
import robomuss.rc.util.IPaintable;

public class BlockRideFence extends BlockContainer implements IPaintable {
	public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);

	public BlockRideFence() {
		super(Material.iron);
		setHardness(1f);
		setResistance(3f);
		setBlockBounds(0, 0, 0, 1, 1, 1);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityRideFence();
	}
	
	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
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
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ) {
		if(!world.isRemote) {
			if(player.getHeldItem() != null) {
				if(player.getHeldItem().getItem() == RCItems.brush) {
					TileEntityRideFence terf = (TileEntityRideFence) world.getTileEntity(pos);
					terf.colour = player.getHeldItem().getItemDamage();
					world.markBlockForUpdate(pos);
					return true;
				}
			}
		}

		return false;
	}
	
	@Override
    public void onNeighborBlockChange(World world, BlockPos pos, IBlockState state, Block neighborBlock) {
        if(!world.isRemote) {
        	if(this == RCBlocks.ride_fence_gate) {
	        	TileEntityRideFence te = (TileEntityRideFence) world.getTileEntity(pos);
	        	te.open = world.isBlockIndirectlyGettingPowered(pos) >= 7;
	        	world.markBlockForUpdate(pos);
        	}
        }
    }
	
	@Override
	public Item getItemDropped(IBlockState state, Random random, int j) {
		return super.getItemDropped(state, random, j);
	}
	
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess iba, BlockPos pos) {
		TileEntity te = iba.getTileEntity(pos);

		if(te.getBlockType() == RCBlocks.ride_fence_gate) {
			TileEntityRideFence terf = (TileEntityRideFence) te;
			setBlockBounds(0, 0, 0, terf.open ? 1 : 0, terf.open ? 1 : 0, terf.open ? 1 : 0);
		} else {
			setBlockBounds(0, 0, 0, 1, 1, 1);
		}
	}

	@Override
	public int getPaintMeta(World world, BlockPos pos) {
		return ((TileEntityRideFence) world.getTileEntity(pos)).colour;
	}
}
