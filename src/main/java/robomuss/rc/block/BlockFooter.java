package robomuss.rc.block;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import robomuss.rc.block.te.TileEntityFooter;
import robomuss.rc.block.te.TileEntitySupport;
import robomuss.rc.item.RCItems;
import robomuss.rc.util.IPaintable;

public class BlockFooter extends BlockContainer implements IPaintable {
    
	public BlockFooter() {
        super(Material.iron);
        setHardness(1F);
		setResistance(3F);
    }

    @Override
    public TileEntity createNewTileEntity(World var1, int var2) {
        return new TileEntityFooter();
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
					TileEntityFooter tes = (TileEntityFooter) world.getTileEntity(x, y, z);
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
}
