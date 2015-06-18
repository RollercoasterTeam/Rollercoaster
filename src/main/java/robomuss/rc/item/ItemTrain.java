package robomuss.rc.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import robomuss.rc.block.BlockTrack;
import robomuss.rc.block.te.TileEntityTrack;
import robomuss.rc.entity.EntityTrain;
import robomuss.rc.entity.EntityTrainDefault;
import robomuss.rc.track.TrackHandler;

public class ItemTrain extends Item {

	public ItemTrain() {

	}

	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
		if(!world.isRemote) {
			if(world.getBlock(x, y, z) instanceof BlockTrack) {
				BlockTrack block = (BlockTrack) world.getBlock(x, y, z);
				if(block.track_type == TrackHandler.findTrackType("horizontal")) {
					if(((TileEntityTrack) world.getTileEntity(x, y, z)).extra == TrackHandler.extras.get(3)) {
						EntityTrainDefault entity = spawnCart(world, x, y, z, Integer.parseInt(stack.getUnlocalizedName().substring(stack.getUnlocalizedName().lastIndexOf("_") + 1)));
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
	
	public static EntityTrainDefault spawnCart(World world, int x, int y, int z, int colour) {
		return EntityTrain.createMinecart(world,(double) ((float) x), (double) ((float) y), (double) ((float) z), colour);
	}
}