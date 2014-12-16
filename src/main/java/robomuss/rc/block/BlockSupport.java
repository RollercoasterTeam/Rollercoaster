package robomuss.rc.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
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
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ) {
		if (player.getHeldItem() != null) {
			Item heldItem = player.getHeldItem().getItem();

			if (heldItem == RCItems.brush || heldItem == Items.water_bucket || heldItem == Item.getItemFromBlock(RCBlocks.support)) {
				return true;
			}
		}

	    return false;
	}
    
    @Override
	public int getPaintMeta(World world, BlockPos pos) {
		return ((TileEntitySupport) world.getTileEntity(pos)).colour;
	}

    @Override
    public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
    	TileEntity above = world.getTileEntity(pos.up());

    	if (!(above instanceof TileEntitySupport)) {
    		int gap = 1;

		    //TODO: make sure this doesn't infinite loop!
    		for(BlockPos currentPos = pos; currentPos.getY() > BlockPos.ORIGIN.getY(); currentPos.down()) {
    			if(world.getTileEntity(currentPos) instanceof TileEntitySupport) {
    				TileEntitySupport te = (TileEntitySupport) world.getTileEntity(currentPos.down());

				    if(gap == 2) {
    					te.flange = true;
    					gap = 0;
    				} else {
    					te.flange = false;
    					gap++;
    				}

    				world.markBlockForUpdate(currentPos.down());
    			}
    		}
    	}
    }

	@Override
	public boolean canPlaceBlockOnSide(World world, BlockPos pos, EnumFacing side) {
		EnumFacing direction = side.getOpposite();
		Block block = world.getBlockState(pos.offset(direction)).getBlock();
		return block instanceof BlockSupport || block instanceof BlockFooter;
	}
    
    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
    	int gap = 1;

	    //TODO: check that this doesn't infinite loop!
	    for (BlockPos currentPos = pos.offset(EnumFacing.DOWN); currentPos.getY() > BlockPos.ORIGIN.getY(); currentPos.down()) {
//    	for(int currentY = y - 1; currentY > 0; currentY--) {
    		if(world.getTileEntity(currentPos) instanceof TileEntitySupport) {
    			TileEntitySupport te = (TileEntitySupport) world.getTileEntity(currentPos);

			    if(gap == 2) {
    				te.flange = true;
    				gap = 0;
    			} else {
    				te.flange = false;
    				gap++;
    			}

    			world.markBlockForUpdate(currentPos);
    		}
    	}
    }
}
