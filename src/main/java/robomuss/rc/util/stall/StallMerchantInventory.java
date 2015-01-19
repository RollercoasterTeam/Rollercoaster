package robomuss.rc.util.stall;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import robomuss.rc.RCMod;
import robomuss.rc.block.RCBlocks;

public class StallMerchantInventory implements IInventory {
	private final IStallMerchant merchant;
	private ItemStack[] inventory = new ItemStack[3];
	private final EntityPlayer player;
	private StallMerchantRecipe currentRecipe;
	private int currentRecipeIndex;
	public String name = "Stall Merchant";

	public StallMerchantInventory(EntityPlayer player, IStallMerchant merchant, String name) {
		this.player = player;
		this.merchant = merchant;
		this.name = name != null && name.length() >= 1 ? name : "Stall Merchant";
	}

	@Override
	public int getSizeInventory() {
		return this.inventory.length;
	}

	@Override
	public ItemStack getStackInSlot(int slotID) {
		return this.inventory[slotID];
	}

	@Override
	public ItemStack decrStackSize(int fromSlotID, int amount) {
		if (this.inventory[fromSlotID] != null) {
			ItemStack itemStack;

			if (fromSlotID == 2) {
				itemStack = this.inventory[fromSlotID];
				this.inventory[fromSlotID] = null;
				return itemStack;
			} else if (this.inventory[fromSlotID].stackSize <= amount) {
				itemStack = this.inventory[fromSlotID];
				this.inventory[fromSlotID] = null;

				if (this.inventoryResetNeededOnSlotChange(fromSlotID)) {
					this.resetRecipeAndSlots();
				}

				return itemStack;
			} else {
				itemStack = this.inventory[fromSlotID].splitStack(amount);

				if (this.inventory[fromSlotID].stackSize == 0) {
					this.inventory[fromSlotID] = null;
				}

				if (this.inventoryResetNeededOnSlotChange(fromSlotID)) {
					this.resetRecipeAndSlots();
				}

				return itemStack;
			}
		} else {
			return null;
		}
	}

	private boolean inventoryResetNeededOnSlotChange(int slotID) {
		return slotID == 0 || slotID == 1;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slotID) {
		if (this.inventory[slotID] != null) {
			ItemStack itemStack = this.inventory[slotID];
			this.inventory[slotID] = null;
			return itemStack;
		} else {
			return null;
		}
	}

	@Override
	public void setInventorySlotContents(int slotID, ItemStack itemStack) {
		this.inventory[slotID] = itemStack;

		if (itemStack != null && itemStack.stackSize > this.getInventoryStackLimit()) {
			itemStack.stackSize = this.getInventoryStackLimit();
		}

		if (this.inventoryResetNeededOnSlotChange(slotID)) {
			this.resetRecipeAndSlots();
		}
	}

	@Override
	public String getInventoryName() {
		return this.name;
	}

	@Override
	public boolean hasCustomInventoryName() {
		return false;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public void markDirty() {
		this.resetRecipeAndSlots();
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return this.merchant.getCustomer() == player;
	}

	@Override
	public void openInventory() {}

	@Override
	public void closeInventory() {}

	@Override
	public boolean isItemValidForSlot(int slotID, ItemStack itemStack) {
		return true;
	}

	public void resetRecipeAndSlots() {
		this.currentRecipe = null;
		ItemStack itemToBuy = this.inventory[0];
		ItemStack secondItemToBuy = this.inventory[1];

		if (itemToBuy == null) {
			itemToBuy = secondItemToBuy;
			secondItemToBuy = null;
		}

		if (itemToBuy == null) {
			this.setInventorySlotContents(2, null);
		} else {
			StallMerchantRecipeList recipes = this.merchant.getRecipes(this.player);

			if (recipes != null) {
				StallMerchantRecipe recipe = recipes.canRecipeBeUsed(itemToBuy, secondItemToBuy, this.currentRecipeIndex);

				if (recipe != null && !recipe.isRecipeDisabled()) {
					this.currentRecipe = recipe;
					this.setInventorySlotContents(2, recipe.getItemToSell().copy());
				} else if (secondItemToBuy != null) {
					recipe = recipes.canRecipeBeUsed(secondItemToBuy, itemToBuy, this.currentRecipeIndex);

					if (recipe != null && !recipe.isRecipeDisabled()) {
						this.currentRecipe = recipe;
						this.setInventorySlotContents(2, recipe.getItemToSell().copy());
					} else {
						this.setInventorySlotContents(2, null);
					}
				} else {
					this.setInventorySlotContents(2, null);
				}
			}
		}

		this.merchant.verifySellingItem(this.getStackInSlot(2));
	}

	public StallMerchantRecipe getCurrentRecipe() {
		return this.currentRecipe;
	}

	public void setCurrentRecipeIndex(int index) {
		this.currentRecipeIndex = index;
		this.resetRecipeAndSlots();
	}
}
