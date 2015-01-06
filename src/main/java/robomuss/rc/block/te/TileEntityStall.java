package robomuss.rc.block.te;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import robomuss.rc.util.stall.IStallMerchant;
import robomuss.rc.util.stall.StallMerchant;
import robomuss.rc.util.stall.StallMerchantInventory;

public class TileEntityStall extends TileEntity implements IInventory {
	private IStallMerchant merchant;
	private StallMerchantInventory inventoryMerchant;
	public  String name;
	public  ItemStack camouflage;
	public boolean hasStallMerchantBeenInitialized = false;

	public TileEntityStall() {}

	public TileEntityStall(EntityPlayer player, int x, int y, int z, String name) {
		this.initStallMerchant(player, x, y, z, name);
	}

	public void initStallMerchant(EntityPlayer player, int x, int y, int z, String name) {
		this.name = name;
		this.merchant = new StallMerchant(player, x, y, z);
		this.inventoryMerchant = new StallMerchantInventory(player, this.merchant, this.name);
		hasStallMerchantBeenInitialized = true;
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		compound.setBoolean("hasBlock", camouflage != null);

		if (camouflage != null) {
			camouflage = ItemStack.loadItemStackFromNBT(compound);
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound compound) {
		if (compound.hasKey("hasBlock") && compound.getBoolean("hasBlock")) {
			camouflage.writeToNBT(compound);
		}
	}

	@Override
	public int getSizeInventory() {
		return 1;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		return this.camouflage;
	}

	@Override
	public ItemStack decrStackSize(int slotID, int amount) {
		setInventorySlotContents(slotID, null);
		return this.camouflage;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slotID) {
		ItemStack camo = camouflage.copy();
		setInventorySlotContents(slotID, null);
		return camo;
	}

	@Override
	public void setInventorySlotContents(int slotID, ItemStack itemStack) {
		this.camouflage = itemStack;
	}

	@Override
	public String getInventoryName() {
		return name;
	}

	@Override
	public boolean hasCustomInventoryName() {
		return name != null && name.length() > 0;
	}

	@Override
	public int getInventoryStackLimit() {
		return 1;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return true;
	}

	@Override
	public void openInventory() {}

	@Override
	public void closeInventory() {}

	@Override
	public boolean isItemValidForSlot(int slotID, ItemStack itemStack) {
		return false;
	}
}
