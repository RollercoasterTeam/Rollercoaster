package robomuss.rc.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import robomuss.rc.entity.EntityTrain;

public class ItemTrain extends Item {

	public ItemTrain() {

	}

	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
		if (!world.isRemote) {
			EntityTrain entityminecart = EntityTrain.createMinecart(world,(double) ((float) x + 0.5F), (double) ((float) y + 0.4F), (double) ((float) z + 0.5F), 0);
			if(stack.hasDisplayName()) {
				entityminecart.setMinecartName(stack.getDisplayName());
			}
			world.spawnEntityInWorld(entityminecart);
		}

		--stack.stackSize;
		return true;
	}
}