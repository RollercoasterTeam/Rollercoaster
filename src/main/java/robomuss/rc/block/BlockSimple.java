package robomuss.rc.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.IBlockAccess;

public class BlockSimple extends Block {

	public BlockSimple(Material material) {
		super(material);
		setHardness(1F);
		setResistance(3F);
		setBlockBounds(0, 0, 0, 1, 1, 1);
	}

	public boolean isOpaqueCube() {
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
	            return true;
	        }
	
	        if (block == this) {
	            return false;
	        }
	
	        return block == this ? false : super.shouldSideBeRendered(iba, x, y, z, side);
        }
        else {
        	return super.shouldSideBeRendered(iba, x, y, z, side);
        }
    }

	public static void getBlockVariant() {
		System.out.println("block variant");
	}
}
