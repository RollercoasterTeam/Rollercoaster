package robomuss.rc.block;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import robomuss.rc.block.te.TileEntityConveyor;

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
}
