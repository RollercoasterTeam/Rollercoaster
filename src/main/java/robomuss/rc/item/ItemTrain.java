package robomuss.rc.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import robomuss.rc.block.BlockTrack;
import robomuss.rc.block.te.TileEntityTrack;
import robomuss.rc.entity.EntityTrain;
import robomuss.rc.entity.EntityTrainDefault;
import robomuss.rc.tracks.TrackHandler;
import robomuss.rc.tracks.TrackType;

public class ItemTrain extends Item {

	public ItemTrain() {

	}

	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
		if (!world.isRemote) {
			EntityTrain entity = spawnCart(world, x, y, z);
			if(stack.hasDisplayName()) {
				entity.setMinecartName(stack.getDisplayName());
			}
			world.spawnEntityInWorld(entity);
		}
		--stack.stackSize;
		return true;
	}
	
	public static EntityTrainDefault spawnCart(World world, int x, int y, int z) {
		return EntityTrain.createMinecart(world,(double) ((float) x + 0.5F), (double) ((float) y + 0.4F), (double) ((float) z + 0.5F), 0);
	}
}