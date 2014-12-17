package robomuss.rc.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
<<<<<<< HEAD
=======
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
>>>>>>> FETCH_HEAD

public class BlockSimple extends Block {
	public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);

	public BlockSimple(Material material) {
		super(material);
		setHardness(1f);
		setResistance(3f);
		setBlockBounds(0, 0, 0, 1, 1, 1);
	}

	public boolean isOpaqueCube() {
		return getMaterial() != Material.glass && super.isOpaqueCube();
    }

	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockAccess iba, BlockPos pos, EnumFacing side) {
        if(getMaterial() == Material.glass) {
	    	Block block = iba.getBlockState(pos).getBlock();
	        if (iba.getBlockState(pos) != iba.getBlockState(pos.offset(side))) {
		        return true;
	        }
	
	        if (block == this) {
	            return false;
	        }
	
	        return block == this ? false : super.shouldSideBeRendered(iba, pos, side);
        }
        else {
        	return super.shouldSideBeRendered(iba, pos, side);
        }
    }

	public static void getBlockVariant() {
		System.out.println("block variant");
	}
}
