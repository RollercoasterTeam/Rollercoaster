package robomuss.rc.block.container;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import robomuss.rc.block.container.slot.SlotStallMerchantCamouflage;
import robomuss.rc.block.container.slot.SlotStallMerchantResult;
import robomuss.rc.util.stall.IStallMerchant;
import robomuss.rc.util.stall.StallMerchantInventory;

public class ContainerStallMerchant extends Container {
	private IStallMerchant merchant;
	private InventoryPlayer player;
	private StallMerchantInventory inventoryMerchant;
	private SlotStallMerchantCamouflage slotCamouflage;
	private final World world;
	public String name;
	public boolean shouldDropItemsOnClose = true;

	public ContainerStallMerchant(InventoryPlayer inventoryPlayer, IStallMerchant merchant, World world, String name) {
		this.merchant = merchant;
		this.player = inventoryPlayer;
		this.world = world;
		this.inventoryMerchant = new StallMerchantInventory(inventoryPlayer.player, merchant, name);
		this.name = name != null && name.length() >= 1 ? name : "Stall Merchant";

		this.addSlotToContainer(new Slot(this.inventoryMerchant, 0, 36, 53));
		this.addSlotToContainer(new Slot(this.inventoryMerchant, 1, 62, 53));
		this.addSlotToContainer(new SlotStallMerchantResult(inventoryPlayer.player, merchant, this.inventoryMerchant, 2, 120, 53));

		for (int slotRow = 0; slotRow < 3; slotRow++) {
			for (int slotColumn = 0; slotColumn < 9; slotColumn++) {
				this.addSlotToContainer(new Slot(inventoryPlayer, slotColumn + slotRow * 9 + 9, 8 + slotColumn * 18, 84 + slotRow * 18));
			}
		}

		for (int slotHotbar = 0; slotHotbar < 9; slotHotbar++) {
			this.addSlotToContainer(new Slot(inventoryPlayer, slotHotbar, 8 + slotHotbar * 18, 142));
		}
	}

	public ContainerStallMerchant(InventoryPlayer inventoryPlayer, IStallMerchant merchant, World world) {
		this(inventoryPlayer, merchant, world, "Stall Merchant");
	}

	public StallMerchantInventory getMerchantInventory() {
		return this.inventoryMerchant;
	}

	@Override
	public void addCraftingToCrafters(ICrafting crafting) {
		super.addCraftingToCrafters(crafting);
	}

	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
	}

	@Override
	public void onCraftMatrixChanged(IInventory inventory) {
		this.inventoryMerchant.resetRecipeAndSlots();
		super.onCraftMatrixChanged(inventory);
	}

	public void setCurrentRecipeIndex(int index) {
		this.inventoryMerchant.setCurrentRecipeIndex(index);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int i, int j) {}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return this.merchant.getCustomer() == player;
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotID) {
		ItemStack toStack = null;
		Slot slot = (Slot)this.inventorySlots.get(slotID);

		if (slot != null && slot.getHasStack()) {
			ItemStack fromStack = slot.getStack();
			toStack = fromStack.copy();

			if (slotID == 2) {
				if (!this.mergeItemStack(fromStack, 3, 39, true)) {
					return null;
				}

				slot.onSlotChange(fromStack, toStack);
			} else if (slotID != 0 && slotID != 1) {
				if (slotID >= 3 && slotID < 30) {
					if (!this.mergeItemStack(fromStack, 30, 39, false)) {
						return null;
					}
				} else if (slotID >= 30 && slotID < 39 && !this.mergeItemStack(fromStack, 3, 30, false)) {
					return null;
				}
			} else if (!this.mergeItemStack(fromStack, 3, 39, false)) {
				return null;
			}

			if (fromStack.stackSize == 0) {
				slot.putStack(null);
			} else {
				slot.onSlotChanged();
			}

			if (fromStack.stackSize == toStack.stackSize) {
				return null;
			}

			slot.onPickupFromSlot(player, fromStack);
		}

		return toStack;
	}

	@Override
	public void onContainerClosed(EntityPlayer player) {
		super.onContainerClosed(player);
		this.merchant.setCustomer(null);
		super.onContainerClosed(player);

		if (!this.world.isRemote) {
			ItemStack itemStack = this.inventoryMerchant.getStackInSlotOnClosing(0);

			if (itemStack != null) {
				player.dropPlayerItemWithRandomChoice(itemStack, false);
			}

			itemStack = this.inventoryMerchant.getStackInSlotOnClosing(1);

			if (itemStack != null) {
				player.dropPlayerItemWithRandomChoice(itemStack, false);
			}
		}
	}
}
