package robomuss.rc.block.container.slot;

import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class SlotTrackFabricator extends Slot {
	private boolean isOutput = false;

	public SlotTrackFabricator(IInventory iinventory, int id, int x, int y, boolean isOutput) {
		super(iinventory, id, x, y);
		this.isOutput = isOutput;
	}

	public boolean getIsOutput() {
		return this.isOutput;
	}

	@Override
	public boolean isItemValid(ItemStack itemStack) {
		if (!getIsOutput() && itemStack.getItem() == Items.iron_ingot) {
			return true;
		}

		return false;
	}
}
