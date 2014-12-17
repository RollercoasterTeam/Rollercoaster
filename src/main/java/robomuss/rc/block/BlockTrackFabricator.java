package robomuss.rc.block;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
<<<<<<< HEAD
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.IBlockState;
=======
>>>>>>> origin/One8PortTake2
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
<<<<<<< HEAD
=======
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
>>>>>>> origin/One8PortTake2
import robomuss.rc.block.te.TileEntityTrackFabricator;

public class BlockTrackFabricator extends BlockContainer {
	public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);

	public BlockTrackFabricator() {
		super(Material.iron);
		setHardness(1f);
		setResistance(3f);
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ) {
		if (!world.isRemote) {
			TileEntityTrackFabricator teFab = (TileEntityTrackFabricator) world.getTileEntity(pos);
			return teFab.testStruct(player);
		}
		
		return true;
	}
	
	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityTrackFabricator();
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
		EnumFacing facing = placer.getHorizontalFacing().getOpposite();
		TileEntityTrackFabricator teFab = (TileEntityTrackFabricator) world.getTileEntity(pos);
		world.setBlockState(pos, state.withProperty(FACING, facing));
    }
}
