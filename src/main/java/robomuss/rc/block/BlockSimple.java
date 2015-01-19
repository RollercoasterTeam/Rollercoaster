package robomuss.rc.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockSimple extends Block {
	@SideOnly(Side.CLIENT)
	private IIcon glassTop;

	public BlockSimple(Material material) {
		super(material);
		setHardness(1F);
		setResistance(3F);
		setBlockBounds(0, 0, 0, 1, 1, 1);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		if (this.getMaterial() == Material.glass) {
			return (side == 0 || side == 1) ? this.glassTop : this.blockIcon;
		} else {
			return this.blockIcon;
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister register) {
		if (this.getMaterial() == Material.glass) {
			this.glassTop = register.registerIcon(this.getTextureName() + "_top");
			this.blockIcon = register.registerIcon(this.getTextureName());
		} else {
			this.blockIcon = register.registerIcon(this.getTextureName());
		}
	}

	public boolean isOpaqueCube() {
		return getMaterial() != Material.glass && super.isOpaqueCube();
    }

    public boolean renderAsNormalBlock() {
	    return getMaterial() != Material.glass && super.renderAsNormalBlock();
    }

    public int getRenderBlockPass() {
	    return getMaterial() == Material.glass ? 0 : super.getRenderBlockPass();
    }

    public boolean shouldSideBeRendered(IBlockAccess iba, int x, int y, int z, int side) {
        if(getMaterial() == Material.glass) {
	    	Block block = iba.getBlock(x, y, z);

	        if (iba.getBlockMetadata(x, y, z) != iba.getBlockMetadata(x - ForgeDirection.VALID_DIRECTIONS[side].offsetX, y - ForgeDirection.VALID_DIRECTIONS[side].offsetY, z - ForgeDirection.VALID_DIRECTIONS[side].offsetZ)) {
	            return true;
	        }
	
	        if (block == this) {
	            return false;
	        }
	
	        return block != this && super.shouldSideBeRendered(iba, x, y, z, side);
        } else {
        	return super.shouldSideBeRendered(iba, x, y, z, side);
        }
    }
}
