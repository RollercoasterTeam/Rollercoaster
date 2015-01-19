package robomuss.rc.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import robomuss.rc.block.BlockTrackBase;
import robomuss.rc.block.te.TileEntityTrackBase;
import robomuss.rc.entity.EntityTrain2;
import robomuss.rc.entity.EntityTrainDefault2;
import robomuss.rc.track.TrackHandler;

public class ItemTrain2 extends Item {
	public ItemTrain2() {}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
		if (!world.isRemote) {
			if (world.getBlock(x, y, z) instanceof BlockTrackBase) {
				BlockTrackBase trackBase = (BlockTrackBase) world.getBlock(x, y, z);

				if (trackBase.track_type == TrackHandler.Types.HORIZONTAL.type) {
					if (((TileEntityTrackBase) world.getTileEntity(x, y, z)).extra == TrackHandler.Extras.STATION.extra) {
						EntityTrainDefault2 entity = spawnCar(world, x, y, z);

						if (stack.hasDisplayName()) {
							entity.setTrainCarName(stack.getDisplayName());
						}

						entity.selfPowered = true;
						world.spawnEntityInWorld(entity);
						stack.stackSize--;
						return true;
					}
				}
			}
		}

		return false;
	}

	public static EntityTrainDefault2 spawnCar(World world, int x, int y, int z) {
		return EntityTrain2.createTrainCar(world, (double) ((float) x + 0.5f), (double) ((float) y + 0.4f), (double) ((float) z + 0.5f), 0);
	}
}
