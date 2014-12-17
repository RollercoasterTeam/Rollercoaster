package robomuss.rc.block.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import robomuss.rc.block.container.slot.SlotTrackFabricator;
import robomuss.rc.block.te.TileEntityTrackFabricator;

public class ContainerTrackFabricator extends Container {
	private SlotTrackFabricator input;
	private SlotTrackFabricator output;

	public ContainerTrackFabricator(InventoryPlayer inventoryPlayer, EntityPlayer player, TileEntityTrackFabricator te, World world, int x, int y, int z) {
		input = new SlotTrackFabricator(te, 0, 8, 18, false);
		output = new SlotTrackFabricator(te, 1, 8, 54, true);

		this.addSlotToContainer(input);
		this.addSlotToContainer(output);

		for (int slotRow = 0; slotRow < 3; ++slotRow) {
			for (int slotColumn = 0; slotColumn < 9; ++slotColumn) {
				this.addSlotToContainer(new Slot(inventoryPlayer, slotColumn + slotRow * 9 + 9, 8 + slotColumn * 18, 84 + slotRow * 18));
			}
		}

		for (int slotHotbar = 0; slotHotbar < 9; ++slotHotbar) {
			this.addSlotToContainer(new Slot(inventoryPlayer, slotHotbar, 8 + slotHotbar * 18, 142));
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

		System.out.println(slotID);
		if (fromSlot != null && fromSlot.getHasStack()) {
			ItemStack fromStack = fromSlot.getStack();
			toStack = fromStack.copy();

			if (slotID == 1) {                                                                                  //if clicked slot is output
				if (!this.mergeItemStack(fromStack, 2, 38, true)) {                                             //if can't merge to any slot in player's inventory
					return null;
				}
				fromSlot.onSlotChange(fromStack, toStack);                                                      //TODO
			} else if (slotID != 0) {                                                                           //if clicked slot is not the input slot
				if (input.isItemValid(fromStack)) {
					if (!this.mergeItemStack(fromStack, 0, 1, false)) {
						return null;
					}
				} else if (slotID >= 2 && slotID < 29) {                                                        //if clicked slot is in player's inventory (excluding hotbar)
					if (!this.mergeItemStack(fromStack, 29, 38, false)) {                                       //if can't merge from input to player's hotbar
						return null;
					}

//					if (input != null && !this.mergeItemStack(fromStack, 1, 1, false)) {
//						return null;
//					}
				} else if (slotID >= 29 && slotID < 38) {                                                       //if clicked slot is in hotbar
					if (!this.mergeItemStack(fromStack, 2, 29, false)) {                                        //if can't merge to the rest of the player's inventory
						return null;
					}
				} else if (slotID >= 2 && slotID < 38) {
					if (!this.mergeItemStack(fromStack, 0, 1, false)) {
						return null;
					}
				}
			} else if (!this.mergeItemStack(fromStack, 2, 38, false)) {                                         //if clicked slot is the input slot and can't merge to any part of player's inventory
				return null;
			}

			if (fromStack.stackSize == 0) {
				fromSlot.putStack((ItemStack) null);                                                            //remove item stack from the slot by setting it to a null stack
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