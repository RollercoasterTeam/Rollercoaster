package robomuss.rc.block.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import robomuss.rc.block.container.slot.SlotStallMerchantCamouflage;

public class ContainerStallMerchantCamouflage extends Container {
	private SlotStallMerchantCamouflage slotCamouflage;
	private InventoryPlayer player;
	public int wide;
	public int tall;
	public int sizeX;
	public int sizeY;
	public int xPos;
	public int yPos;

	public ContainerStallMerchantCamouflage(InventoryPlayer player, int id, int x, int y) {
		this.slotCamouflage = new SlotStallMerchantCamouflage(player, id, x, y);
		this.addSlotToContainer(slotCamouflage);
		this.player = player;
		this.xPos = x;
		this.yPos = y;

		x -= 80;
		y -= 36;

		for (int slotRow = 0; slotRow < 3; slotRow++) {
			for (int slotColumn = 0; slotColumn < 9; slotColumn++) {
				this.addSlotToContainer(new Slot(player, slotColumn + slotRow * 9 + 9, x + (8 + slotColumn * 18), y + (84 + slotRow * 18)));
			}
		}

		for (int slotHotbar = 0; slotHotbar < 9; slotHotbar++) {
			this.addSlotToContainer(new Slot(player, slotHotbar, x + (8 + slotHotbar * 18), y + 142));
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return true;
	}

	public ItemStack transferStackInSlot(EntityPlayer player, int slotID) {
		ItemStack toStack = null;
		Slot fromSlot = (Slot) this.inventorySlots.get(slotID);

		if (fromSlot != null && fromSlot.getHasStack()) {
			ItemStack fromStack = fromSlot.getStack();
			toStack = fromStack.copy();

			if (slotID == 0) {
				if (!this.mergeItemStack(fromStack, 1, 37, true)) {
					return null;
				}

				fromSlot.onSlotChange(fromStack, toStack);
			} else {
				if (slotCamouflage.isItemValid(fromStack)) {
					if (!this.mergeItemStack(fromStack, 0, 0, false)) {
						return null;
					}
				} else if (slotID >= 1 && slotID < 28) {
					if (!this.mergeItemStack(fromStack, 28, 37, false)) {
						return null;
					}
				} else if (slotID >= 28 && slotID < 37) {
					if (!this.mergeItemStack(fromStack, 1, 28, false)) {
						return null;
					}
				}
			}

			if (fromStack.stackSize == 0) {
				fromSlot.putStack(null);
			} else {
				fromSlot.onSlotChanged();
			}

			if (fromStack.stackSize == toStack.stackSize) {
				return null;
			}

			fromSlot.onPickupFromSlot(player, fromStack);
		}

		return toStack;
	}
}
