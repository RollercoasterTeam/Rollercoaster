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

	public ContainerTrackStorage(InventoryPlayer par1InventoryPlayer, EntityPlayer player, TileEntityTrackStorage te, World par2World, int par3, int par4, int par5) {
		int l;
		int i1;

		for (l = 0; l < 3; ++l) {
			for (i1 = 0; i1 < 9; ++i1) {
				this.addSlotToContainer(new Slot(par1InventoryPlayer, i1 + l
						* 9 + 9, 8 + i1 * 18, 84 + l * 18));
			}
		}

		for (l = 0; l < 9; ++l) {
			this.addSlotToContainer(new Slot(par1InventoryPlayer, l,
					8 + l * 18, 142));
		}

		for(int i = 0; i < 27; i++) {
			this.addSlotToContainer(new SlotTrackStorage(te, i, 8 + (i % 9) * 18, 18 + 18 + (18 * ((i / 9) - 1))));
		}
	}

	public boolean canInteractWith(EntityPlayer par1EntityPlayer) {
		return true;
	}

	/**
	 * Called when a player shift-clicks on a slot. You must override this or
	 * you will crash when someone does that.
	 */
	/*public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2) {
		ItemStack itemstack = null;
		Slot slot = (Slot) this.inventorySlots.get(par2);

		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();
			if(itemstack.getItem() == Items.iron_ingot) {
				if (par2 == 0) {
					if (!this.mergeItemStack(itemstack1, 10, 46, true)) {
						return null;
					}
	
					slot.onSlotChange(itemstack1, itemstack);
				} else if (par2 >= 10 && par2 < 37) {
					if (!this.mergeItemStack(itemstack1, 37, 46, false)) {
						return null;
					}
				} else if (par2 >= 37 && par2 < 46) {
					if (!this.mergeItemStack(itemstack1, 10, 37, false)) {
						return null;
					}
				} else if (!this.mergeItemStack(itemstack1, 10, 46, false)) {
					return null;
				}
	
				if (itemstack1.stackSize == 0) {
					slot.putStack((ItemStack) null);
				} else {
					slot.onSlotChanged();
				}
	
				if (itemstack1.stackSize == itemstack.stackSize) {
					return null;
				}
	
				slot.onPickupFromSlot(par1EntityPlayer, itemstack1);
			}
		}

		return itemstack;
	}*/
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer p_82846_1_, int p_82846_2_) {
		return null;
	}
}