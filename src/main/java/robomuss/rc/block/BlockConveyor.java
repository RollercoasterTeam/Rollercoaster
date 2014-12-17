package robomuss.rc.block;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
<<<<<<< HEAD
import net.minecraft.util.MathHelper;
=======
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
>>>>>>> FETCH_HEAD
import net.minecraft.world.World;
import robomuss.rc.block.te.TileEntityConveyor;

public class BlockConveyor extends BlockContainer {
	public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
	public BlockConveyor() {
        super(Material.iron);
        setHardness(1f);
		setResistance(3f);
		setBlockBounds(0, 0, 0, 1, 0.25f, 1);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileEntityConveyor();
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
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		world.setBlockState(pos, state.withProperty(FACING, placer.getHorizontalFacing()), 2);
	}

	private boolean rotateToNeighbor(World world, BlockPos pos) {
		IBlockState state = world.getBlockState(pos);
		IBlockState northState = world.getBlockState(pos.north());
		IBlockState southState = world.getBlockState(pos.south());
		IBlockState westState  = world.getBlockState(pos.west());
		IBlockState eastState  = world.getBlockState(pos.east());

		if (northState.getBlock() instanceof BlockSimple && northState.getBlock().getMaterial() == Material.iron) {
			world.setBlockState(pos, state.withProperty(FACING, EnumFacing.SOUTH));
		} else if (northState.getBlock() instanceof BlockConveyor) {
			world.setBlockState(pos, state.withProperty(FACING, northState.getValue(FACING)));
		}

		if (southState.getBlock() instanceof BlockSimple && southState.getBlock().getMaterial() == Material.iron) {
			world.setBlockState(pos, state.withProperty(FACING, EnumFacing.NORTH));
		} else if (southState.getBlock() instanceof BlockConveyor) {
			world.setBlockState(pos, state.withProperty(FACING, southState.getValue(FACING)));
		}

		if (westState.getBlock() instanceof BlockSimple && westState.getBlock().getMaterial() == Material.iron) {
			world.setBlockState(pos, state.withProperty(FACING, EnumFacing.EAST));
		} else if (westState.getBlock() instanceof BlockConveyor) {
			world.setBlockState(pos, state.withProperty(FACING, westState.getValue(FACING)));
		}

		if (eastState.getBlock() instanceof BlockSimple && eastState.getBlock().getMaterial() == Material.iron) {
			world.setBlockState(pos, state.withProperty(FACING, EnumFacing.WEST));
		} else if (eastState.getBlock() instanceof BlockConveyor) {
			world.setBlockState(pos, state.withProperty(FACING, eastState.getValue(FACING)));
		}

		return true;
	}
}
