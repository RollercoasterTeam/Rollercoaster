package robomuss.rc.block;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import java.util.List;

public class BlockPath extends Block {
	public int paths = 10;
	
	public BlockPath() {
		super(Material.carpet);
		setBlockBounds(0, 0, 0, 1, 0.05f, 1);
		setHardness(1f);
		setResistance(3f);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public boolean isNormalCube(IBlockAccess iba, BlockPos pos) {
		return false;
	}
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		for(int i = 0; i < paths; i++) {
			list.add(new ItemStack(item, 1, i));
		}
	}

	//TODO: make a damage property?
	@Override
	public int damageDropped(IBlockState state) {
		return 0;
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBox(World world, BlockPos pos, IBlockState state) {
		return AxisAlignedBB.fromBounds(0, 0, 0, 0, 0, 0);
	}
}
