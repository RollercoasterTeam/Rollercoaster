package robomuss.rc.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
<<<<<<< HEAD
import net.minecraft.util.Facing;
=======
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.ForgeDirection;
>>>>>>> master

public class BlockSimple extends Block {

	public BlockSimple(Material material) {
		super(material);
		setHardness(1F);
		setResistance(3F);
		setBlockBounds(0, 0, 0, 1, 1, 1);
	}

	public boolean isOpaqueCube() {
<<<<<<< HEAD
		if(getMaterial() == Material.glass) {
			return false;
		}
		else {
			return super.isOpaqueCube();
		}
    }

    public boolean renderAsNormalBlock() {
    	if(getMaterial() == Material.glass) {
    		return false;
    	}
    	else {
    		return super.renderAsNormalBlock();
    	}
    }

    public int getRenderBlockPass() {
        if(getMaterial() == Material.glass) {
        	return 0;
        }
        else {
        	return super.getRenderBlockPass();
        }
    }

    public boolean shouldSideBeRendered(net.minecraft.world.IBlockAccess p_149646_1_, int p_149646_2_, int p_149646_3_, int p_149646_4_, int p_149646_5_) {
        if(getMaterial() == Material.glass) {
	    	Block block = p_149646_1_.getBlock(p_149646_2_, p_149646_3_, p_149646_4_);
	        if (p_149646_1_.getBlockMetadata(p_149646_2_, p_149646_3_, p_149646_4_) != p_149646_1_.getBlockMetadata(p_149646_2_ - Facing.offsetsXForSide[p_149646_5_], p_149646_3_ - Facing.offsetsYForSide[p_149646_5_], p_149646_4_ - Facing.offsetsZForSide[p_149646_5_])) {
=======
		return getMaterial() == Material.glass ? false : super.isOpaqueCube();
    }

    public boolean renderAsNormalBlock() {
	    return getMaterial() == Material.glass ? false : super.renderAsNormalBlock();
    }

    public int getRenderBlockPass() {
	    return getMaterial() == Material.glass ? 0 : super.getRenderBlockPass();
    }

    public boolean shouldSideBeRendered(IBlockAccess iba, int x, int y, int z, int side) {
        if(getMaterial() == Material.glass) {
	    	Block block = iba.getBlock(x, y, z);
	        if (iba.getBlockMetadata(x, y, z) != iba.getBlockMetadata(x - ForgeDirection.VALID_DIRECTIONS[side].offsetX, y - ForgeDirection.VALID_DIRECTIONS[side].offsetY, z - ForgeDirection.VALID_DIRECTIONS[side].offsetZ)) {
//	        if (iba.getBlockMetadata(x, y, z) != iba.getBlockMetadata(x - Facing.offsetsXForSide[side], y - Facing.offsetsYForSide[side], z - Facing.offsetsZForSide[side])) {      //TODO: use ForgeDirection
>>>>>>> master
	            return true;
	        }
	
	        if (block == this) {
	            return false;
	        }
	
<<<<<<< HEAD
	        return block == this ? false : super.shouldSideBeRendered(p_149646_1_, p_149646_2_, p_149646_3_, p_149646_4_, p_149646_5_);
        }
        else {
        	return super.shouldSideBeRendered(p_149646_1_, p_149646_2_, p_149646_3_, p_149646_4_, p_149646_5_);
        }
    }
=======
	        return block == this ? false : super.shouldSideBeRendered(iba, x, y, z, side);
        }
        else {
        	return super.shouldSideBeRendered(iba, x, y, z, side);
        }
    }

	public static void getBlockVariant() {
		System.out.println("block variant");
	}
>>>>>>> master
}
