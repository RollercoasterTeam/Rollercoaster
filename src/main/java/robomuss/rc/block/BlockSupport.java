package robomuss.rc.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import robomuss.rc.block.te.TileEntitySupport;
import robomuss.rc.item.RCItems;
import robomuss.rc.util.IPaintable;

public class BlockSupport extends BlockContainer implements IPaintable {
	public BlockSupport() {
        super(Material.iron);
        setHardness(1f);
		setResistance(3f);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileEntitySupport(world);
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public int getRenderType() {
        return 110;
    }
    
    @Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if (player.getHeldItem() != null) {
			Item heldItem = player.getHeldItem().getItem();

			if (heldItem == RCItems.brush || heldItem == Items.water_bucket || heldItem == Item.getItemFromBlock(RCBlocks.support)) {
				return true;
			}
		}

	    return false;
	}
    
    @Override
	public int getPaintMeta(World world, int x, int y, int z) {
		return ((TileEntitySupport) world.getTileEntity(x, y, z)).colour;
	}

    @Override
    public void onBlockAdded(World world, int x, int y, int z) {
    	TileEntity above = world.getTileEntity(x, y + 1, z);

    	if (!(above instanceof TileEntitySupport)) {
    		int gap = 1;

    		for(int currentY = y; currentY > 0; currentY--) {
    			if(world.getTileEntity(x, currentY, z) instanceof TileEntitySupport) {
    				TileEntitySupport te = (TileEntitySupport) world.getTileEntity(x, currentY, z);

				    if(gap == 2) {
    					te.flange = true;
    					gap = 0;
    				} else {
    					te.flange = false;
    					gap++;
    				}

    				world.markBlockForUpdate(x, currentY, z);
    			}
    		}
    	}
    }

	@Override
	public boolean canPlaceBlockOnSide(World world, int x, int y, int z, int side) {
		ForgeDirection direction = ForgeDirection.getOrientation(side).getOpposite();
		Block block = world.getBlock(x + direction.offsetX, y + direction.offsetY, z + direction.offsetZ);
		return block instanceof BlockSupport || block instanceof BlockFooter;
	}
    
    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int side) {
    	int gap = 1;

    	for(int currentY = y - 1; currentY > 0; currentY--) {
    		if(world.getTileEntity(x, currentY, z) instanceof TileEntitySupport) {
    			TileEntitySupport te = (TileEntitySupport) world.getTileEntity(x, currentY, z);

			    if(gap == 2) {
    				te.flange = true;
    				gap = 0;
    			} else {
    				te.flange = false;
    				gap++;
    			}

    			world.markBlockForUpdate(x, currentY, z);
    		}
    	}
    }
}
