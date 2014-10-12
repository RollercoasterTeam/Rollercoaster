package robomuss.rc.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.Facing;

public class BlockSimple extends Block {

	public BlockSimple(Material material) {
		super(material);
		setHardness(1F);
		setResistance(3F);
		setBlockBounds(0, 0, 0, 1, 1, 1);
	}

	public boolean isOpaqueCube() {
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
	            return true;
	        }
	
	        if (block == this) {
	            return false;
	        }
	
	        return block == this ? false : super.shouldSideBeRendered(p_149646_1_, p_149646_2_, p_149646_3_, p_149646_4_, p_149646_5_);
        }
        else {
        	return super.shouldSideBeRendered(p_149646_1_, p_149646_2_, p_149646_3_, p_149646_4_, p_149646_5_);
        }
    }
}
