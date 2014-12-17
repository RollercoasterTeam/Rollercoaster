package robomuss.rc.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import robomuss.rc.block.BlockTrackBase;
import robomuss.rc.block.te.TileEntityTrackBase;
import robomuss.rc.entity.EntityTrain;
import robomuss.rc.entity.EntityTrainDefault;
import robomuss.rc.track.TrackHandler;

public class ItemTrain extends Item {
	public ItemTrain() {}
	
	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {
		if(!world.isRemote) {
			if(world.getBlockState(pos).getBlock() instanceof BlockTrackBase) {
				BlockTrackBase block = (BlockTrackBase) world.getBlockState(pos).getBlock();
				if(block.track_type == TrackHandler.findTrackType("horizontal")) {
					if(((TileEntityTrackBase) world.getTileEntity(pos)).extra == TrackHandler.extras.get(3)) {
						EntityTrainDefault entity = spawnCart(world, pos);

						if(stack.hasDisplayName()) {
							entity.setMinecartName(stack.getDisplayName());
						}

						entity.selfPowered = true;
						world.spawnEntityInWorld(entity);
						--stack.stackSize;
						return true;
					}
				}
			}
		}

		return false;
	}
	
	public static EntityTrainDefault spawnCart(World world, BlockPos pos) {
		return EntityTrain.createMinecart(world, pos.getX(), pos.getY(), pos.getZ(), 0);
	}
}