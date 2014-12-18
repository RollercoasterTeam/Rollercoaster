package robomuss.rc.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockFence;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import robomuss.rc.block.te.TileEntityRideFence;
import robomuss.rc.item.RCItems;
import robomuss.rc.util.IPaintable;

import java.util.Random;

public class BlockRideFence extends Block implements IPaintable, ITileEntityProvider {
//	public static final PropertyBool NORTH = PropertyBool.create("normal");
//	public static final PropertyBool EAST = PropertyBool.create("corner");
//	public static final PropertyBool SOUTH = PropertyBool.create("triangle");
//	public static final PropertyBool WEST = PropertyBool.create("square");
	public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);

	public BlockRideFence() {
		super(Material.iron);
		setHardness(1f);
		setResistance(3f);
		setBlockBounds(0, 0, 0, 1, 1, 1);
//		this.setDefaultState(this.blockState.getBaseState().withProperty(NORTH, true).withProperty(EAST, false).withProperty(SOUTH, true).withProperty(WEST, false));
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(meta));
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		EnumFacing facing = (EnumFacing) state.getValue(FACING);
		return facing.getHorizontalIndex();
	}

//	@Override
//	public void setBlockBoundsBasedOnState(IBlockAccess world, BlockPos pos) {
//		boolean north = this.canConnectTo(world, pos.north());
//		boolean south = this.canConnectTo(world, pos.south());
//		boolean west  = this.canConnectTo(world, pos.west());
//		boolean east  = this.canConnectTo(world, pos.east());
//
//		float offsetNorth = north ? 0 : 0.375f;
//		float offsetSouth = south ? 1 : 0.625f;
//		float offsetWest  = west  ? 0 : 0.375f;
//		float offsetEast  = east  ? 1 : 0.625f;
//
//		this.setBlockBounds(offsetWest, 0, offsetNorth, offsetEast, 1, offsetWest);
//	}

	public boolean canConnectTo(IBlockAccess world, BlockPos pos) {
		Block block = world.getBlockState(pos).getBlock();
		return (block instanceof BlockRideFence || block instanceof BlockRideFenceGate) && (block != Blocks.barrier) && (block.getMaterial().isOpaque() && block.isFullCube());
	}

	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos) {
//		boolean north = this.canConnectTo(world, pos.north());
//		boolean east  = this.canConnectTo(world, pos.east());
//		boolean south = this.canConnectTo(world, pos.south());
//		boolean west  = this.canConnectTo(world, pos.west());
//		return state.withProperty(NORTH, north).withProperty(EAST, east).withProperty(SOUTH, south).withProperty(WEST, west);
		return state.withProperty(FACING, EnumFacing.NORTH);
	}

	@Override
	public BlockState createBlockState() {
//		return new BlockState(this, NORTH, EAST, SOUTH, WEST);
		return new BlockState(this, FACING);
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
					TileEntityRideFence teRideFence = (TileEntityRideFence) world.getTileEntity(pos);
					teRideFence.colour = player.getHeldItem().getItemDamage();
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
	        	TileEntityRideFence teRideFence = (TileEntityRideFence) world.getTileEntity(pos);
	        	teRideFence.open = world.isBlockIndirectlyGettingPowered(pos) >= 7;
	        	world.markBlockForUpdate(pos);
        	}
        }
    }
	
	@Override
	public Item getItemDropped(IBlockState state, Random random, int j) {
		return super.getItemDropped(state, random, j);
	}
	
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, BlockPos pos) {
//		TileEntity te = iba.getTileEntity(pos);
		Block block = world.getBlockState(pos).getBlock();

//		if(te.getBlockType() == RCBlocks.ride_fence_gate) {
		if (block == RCBlocks.ride_fence_gate) {
			TileEntityRideFence teRideFence = (TileEntityRideFence) world.getTileEntity(pos);
			setBlockBounds(0, 0, 0, 1, 1, 1);
//			setBlockBounds(0, 0, 0, teRideFence.open ? 1 : 0, teRideFence.open ? 1 : 0, teRideFence.open ? 1 : 0);
		} else {
			setBlockBounds(0, 0, 0, 1, 1, 1);
		}
	}

	@Override
	public int getPaintMeta(World world, BlockPos pos) {
		return ((TileEntityRideFence) world.getTileEntity(pos)).colour;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityRideFence();
	}
}
