package robomuss.rc.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import robomuss.rc.block.te.TileEntitySupport;
import robomuss.rc.item.RCItems;
import robomuss.rc.util.IPaintable;

public class BlockSupport extends BlockContainer implements IPaintable {
    
	public BlockSupport() {
        super(Material.iron);
        setHardness(1F);
		setResistance(3F);
    }

    @Override
    public TileEntity createNewTileEntity(World var1, int var2) {
        return new TileEntitySupport();
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
		if(!world.isRemote) {
			if(player.getHeldItem() != null) {
				if(player.getHeldItem().getItem() == RCItems.brush) {
					TileEntitySupport tes = (TileEntitySupport) world.getTileEntity(x, y, z);
					tes.colour = player.getHeldItem().getItemDamage();
					world.markBlockForUpdate(x, y, z);
					return true;
				}
				else {
					return false;
				}
				
			}
			else {
				return false;
			}
		}
		else {
			return false;
		}
	}
    
    @Override
	public int getPaintMeta(World world, int x, int y, int z) {
		return ((TileEntitySupport) world.getTileEntity(x, y, z)).colour;
	}
    
    @Override
    public void onBlockAdded(World world, int x, int y, int z) {
    	TileEntity above = world.getTileEntity(x, y + 1, z);
    	if(!(above instanceof TileEntitySupport)) {
    		int gap = 2;
    		for(int currentY = y; currentY > 0; currentY--) {
    			if(world.getTileEntity(x, currentY, z) instanceof TileEntitySupport) {
    				TileEntitySupport te = (TileEntitySupport) world.getTileEntity(x, currentY, z);
    				if(gap == 2) {
    					te.flange = true;
    					gap = 0;
    				}
    				else {
    					te.flange = false;
    					gap++;
    				}
    				world.markBlockForUpdate(x, currentY, z);
    			}
    		}
    	}
    }
}
