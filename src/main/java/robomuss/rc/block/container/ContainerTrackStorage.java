package robomuss.rc.block.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import robomuss.rc.block.container.slot.SlotTrackStorage;
import robomuss.rc.block.te.TileEntityTrackStorage;

public class ContainerTrackStorage extends Container {
	public ContainerTrackStorage(InventoryPlayer inventoryPlayer, EntityPlayer player, TileEntityTrackStorage te, World world, int x, int y, int z) {
		for (int storageRows = 0; storageRows < 3; storageRows++) {
			for (int storageColumns = 0; storageColumns < 9; storageColumns++) {
				this.addSlotToContainer(new SlotTrackStorage(te, storageColumns + storageRows * 9 + 9, 8 + storageColumns * 18, 84 + storageRows * 18));
			}
		}

//		for(int storageRows = 0; storageRows < 27; storageRows++) {
//			this.addSlotToContainer(new SlotTrackStorage(te, storageRows, 8 + (storageRows % 9) * 18, 18 + 18 + (18 * ((storageRows / 9) - 1))));
//		}

		for (int inventoryRows = 0; inventoryRows < 3; inventoryRows++) {
			for (int inventoryColumns = 0; inventoryColumns < 9; ++inventoryColumns) {
				this.addSlotToContainer(new Slot(inventoryPlayer, inventoryColumns + inventoryRows * 9 + 9, 8 + inventoryColumns * 18, 84 + inventoryRows * 18));
			}
		}

		for (int hotbarSlots = 0; hotbarSlots < 9; hotbarSlots++) {
			this.addSlotToContainer(new Slot(inventoryPlayer, hotbarSlots, 8 + hotbarSlots * 18, 142));
		}

	}

	public boolean canInteractWith(EntityPlayer player) {
		return true;
	}

	/**
	 * Called when a player shift-clicks on a slot. You must override this or
	 * you will crash when someone does that.
	 */
	public ItemStack transferStackInSlot(EntityPlayer player, int slotID) {
		ItemStack toStack = null;
		Slot fromSlot = (Slot) this.inventorySlots.get(slotID);

		if (fromSlot != null && fromSlot.getHasStack()) {
			ItemStack fromStack = fromSlot.getStack();
			toStack = fromStack.copy();

			if (slotID < 27) {
				if (!this.mergeItemStack(fromStack, 27, this.inventorySlots.size(), true)) {
					return null;
				}
			} else if (!this.mergeItemStack(fromStack, 0, 27, false)) {
				return null;
			}

			if (fromStack.stackSize == 0) {
				fromSlot.putStack(null);
			} else {
				fromSlot.onSlotChanged();
			}
		}

		return toStack;
	}
}