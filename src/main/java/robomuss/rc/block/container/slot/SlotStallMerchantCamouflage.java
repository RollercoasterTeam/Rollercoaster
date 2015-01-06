package robomuss.rc.block.container.slot;

import net.minecraft.entity.IMerchant;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class SlotStallMerchantCamouflage extends Slot {
	public SlotStallMerchantCamouflage(IInventory inventory, int id, int x, int y) {
		super(inventory, id, x, y);
	}

	@Override
	public boolean isItemValid(ItemStack itemStack) {
		return itemStack.getItem() instanceof ItemBlock;
	}

	@Override
	public int getSlotStackLimit() {
		return 1;
	}
}
