package robomuss.rc.block;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import robomuss.rc.block.te.TileEntityFooter;
import robomuss.rc.item.RCItems;
import robomuss.rc.util.IPaintable;

public class BlockFooter extends BlockContainer implements IPaintable {
	public BlockFooter() {
        super(Material.iron);
        setHardness(1f);
		setResistance(3f);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileEntityFooter(world, meta);
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
		return ((TileEntityFooter) world.getTileEntity(x, y, z)).colour;
	}
}
