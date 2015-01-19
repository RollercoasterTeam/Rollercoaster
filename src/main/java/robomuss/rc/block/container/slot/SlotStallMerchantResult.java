package robomuss.rc.block.container.slot;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import robomuss.rc.util.stall.IStallMerchant;
import robomuss.rc.util.stall.StallMerchantInventory;
import robomuss.rc.util.stall.StallMerchantRecipe;

public class SlotStallMerchantResult extends Slot {
	private final StallMerchantInventory merchantInventory;
	private EntityPlayer player;
	private int amountSold;
	private final IStallMerchant merchant;

	public SlotStallMerchantResult(EntityPlayer player, IStallMerchant merchant, StallMerchantInventory merchantInventory, int x, int y, int z) {
		super(merchantInventory, x, y, z);
		this.player = player;
		this.merchant = merchant;
		this.merchantInventory = merchantInventory;
	}

	@Override
	public boolean isItemValid(ItemStack itemStack) {
		return false;
	}

	@Override
	public ItemStack decrStackSize(int amount) {
		if (this.getHasStack()) {
			this.amountSold += Math.min(amount, this.getStack().stackSize);
		}

		return super.decrStackSize(amount);
	}

	@Override
	public void onCrafting(ItemStack itemStack, int amount) {
		this.amountSold += amount;
		this.onCrafting(itemStack);
	}

	@Override
	public void onCrafting(ItemStack itemStack) {
		itemStack.onCrafting(this.player.worldObj, this.player, this.amountSold);
		this.amountSold = 0;
	}

	@Override
	public void onPickupFromSlot(EntityPlayer player, ItemStack itemStack) {
		this.onCrafting(itemStack);
		StallMerchantRecipe recipe = this.merchantInventory.getCurrentRecipe();

		if (recipe != null) {
			ItemStack itemToBuy = this.merchantInventory.getStackInSlot(0);
			ItemStack secondItemToBuy = this.merchantInventory.getStackInSlot(1);

			if (this.compareRecipeStacks(recipe, itemToBuy, secondItemToBuy) || this.compareRecipeStacks(recipe, secondItemToBuy, itemToBuy)) {
				this.merchant.useRecipe(recipe);

				if (itemToBuy != null && itemToBuy.stackSize <= 0) {
					itemToBuy = null;
				}

				if (secondItemToBuy != null && secondItemToBuy.stackSize <= 0) {
					secondItemToBuy = null;
				}

				this.merchantInventory.setInventorySlotContents(0, itemToBuy);
				this.merchantInventory.setInventorySlotContents(1, secondItemToBuy);
			}
		}
	}

	private boolean compareRecipeStacks(StallMerchantRecipe recipe, ItemStack itemToBuy, ItemStack secondItemToBuy) {
		ItemStack recipeItemToBuy = recipe.getItemToBuy();
		ItemStack recipeSecondItemToBuy = recipe.getSecondItemToBuy();

		if (itemToBuy != null && itemToBuy.getItem() == recipeItemToBuy.getItem()) {
			if (recipeSecondItemToBuy != null && secondItemToBuy != null && recipeSecondItemToBuy.getItem() == secondItemToBuy.getItem()) {
				itemToBuy.stackSize -= recipeItemToBuy.stackSize;
				secondItemToBuy.stackSize -= recipeSecondItemToBuy.stackSize;
				return true;
			}

			if (recipeSecondItemToBuy == null && secondItemToBuy == null) {
				itemToBuy.stackSize -= recipeItemToBuy.stackSize;
				return true;
			}
		}

		return false;
	}
}
