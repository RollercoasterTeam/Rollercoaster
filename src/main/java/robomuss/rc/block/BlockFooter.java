package robomuss.rc.block;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import robomuss.rc.block.te.TileEntityFooter;
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
        return -1;
    }
    
    @Override
    public boolean onBlockActivated(World world, BlockPos blockPos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ){
		if(!world.isRemote) {
			if(player.getHeldItem() != null) {
				if(player.getHeldItem().getItem() == RCItems.brush) {
					TileEntityFooter tes = (TileEntityFooter) world.getTileEntity(blockPos);
					tes.colour = player.getHeldItem().getItemDamage();
					world.markBlockForUpdate(blockPos);
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
	public int getPaintMeta(World world, BlockPos blockPos) {
		return ((TileEntityFooter) world.getTileEntity(blockPos)).colour;
	}
}
