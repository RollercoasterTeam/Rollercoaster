package robomuss.rc.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import robomuss.rc.block.te.TileEntityConveyor;

import javax.swing.*;
import java.util.ArrayList;

public class BlockConveyor extends BlockContainer {
    
	public BlockConveyor() {
        super(Material.iron);
        setHardness(1F);
		setResistance(3F);
		setBlockBounds(0, 0, 0, 1, 0.25f, 1);
    }

    @Override
    public TileEntity createNewTileEntity(World var1, int var2) {
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
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public IIcon getIcon(int side, int meta) {
		return RCBlocks.track_fabricator_casing.getIcon(side, meta);
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack item) {
		int facing = MathHelper.floor_double((double)(player.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;

		if (rotateToNeighbor(world, x, y, z)) {
			switch (facing) {
				case 0: world.setBlockMetadataWithNotify(x, y, z, 3, 2); break;
				case 1: world.setBlockMetadataWithNotify(x, y, z, 4, 2); break;
				case 2: world.setBlockMetadataWithNotify(x, y, z, 2, 2); break;
				case 3: world.setBlockMetadataWithNotify(x, y, z, 5, 2); break;
			}
		}
	}

	private boolean rotateToNeighbor(World world, int x, int y, int z) {
//		Block[] neighbors = new Block[4];
		ArrayList<Block> neighbors = new ArrayList<Block>();
		neighbors.add(world.getBlock(x, y, z - 1));
		neighbors.add(world.getBlock(x, y, z + 1));
		neighbors.add(world.getBlock(x - 1, y, z));
		neighbors.add(world.getBlock(x + 1, y, z));

		int index = -1;

		for (Block block : neighbors) {
			if (block instanceof BlockSimple && block.getMaterial() == Material.iron) {
				index = neighbors.indexOf(block);
			} else if (block instanceof BlockConveyor) {
				index = neighbors.indexOf(block);
			}
		}

		if (index == -1) {
			return true;
		} else {
			switch (index) {
				case 0: world.setBlockMetadataWithNotify(x, y, z, 3, 2); break;
				case 1: world.setBlockMetadataWithNotify(x, y, z, 2, 2); break;
				case 2: world.setBlockMetadataWithNotify(x, y, z, 5, 2); break;
				case 3: world.setBlockMetadataWithNotify(x, y, z, 4, 2); break;
			}
			return false;
		}
	}
}
