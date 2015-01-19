package robomuss.rc.util.stall;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class StallMerchantRecipe {
	private ItemStack itemToBuy;
	private ItemStack secondItemToBuy;
	private ItemStack itemToSell;
	private int toolUses;
	private int maxTradeUses;

	public StallMerchantRecipe(NBTTagCompound compound) {
		this.readFromTags(compound);
	}

	public StallMerchantRecipe(ItemStack itemToBuy, ItemStack secondItemToBuy, ItemStack itemToSell) {
		this.itemToBuy = itemToBuy;
		this.secondItemToBuy = secondItemToBuy;
		this.itemToSell = itemToSell;
		this.maxTradeUses = 7;
	}

	public StallMerchantRecipe(ItemStack itemToBuy, ItemStack itemToSell) {
		this(itemToBuy, null, itemToSell);
	}

	public StallMerchantRecipe(ItemStack itemToBuy, Item itemToSell) {
		this(itemToBuy, new ItemStack(itemToSell));
	}

	public ItemStack getItemToBuy() {
		return this.itemToBuy;
	}

	public ItemStack getSecondItemToBuy() {
		return this.secondItemToBuy;
	}

	public boolean hasSecondItemToBuy() {
		return this.secondItemToBuy != null;
	}

	public ItemStack getItemToSell() {
		return this.itemToSell;
	}

	public boolean hasSameIDsAs(StallMerchantRecipe recipe) {
		boolean first = this.itemToBuy.getItem() == recipe.itemToBuy.getItem();
		boolean second = ((this.secondItemToBuy == null && recipe.secondItemToBuy == null) || (this.secondItemToBuy != null && recipe.secondItemToBuy != null) && this.secondItemToBuy.getItem() == recipe.secondItemToBuy.getItem());
		boolean selling = this.itemToSell.getItem() == recipe.itemToSell.getItem();
		return first && selling && second;
	}

	public boolean hasSameItemsAs(StallMerchantRecipe recipe) {
		return this.hasSameIDsAs(recipe) && (this.itemToBuy.stackSize < recipe.itemToBuy.stackSize || this.secondItemToBuy != null && this.secondItemToBuy.stackSize < recipe.secondItemToBuy.stackSize);
	}

	public void incrementToolUses() {
		this.toolUses++;
	}

	public void addMaxTradeUses(int increment) {
		this.maxTradeUses += increment;
	}

	public boolean isRecipeDisabled() {
		return this.toolUses >= this.maxTradeUses;
	}

	@SideOnly(Side.CLIENT)
	public void disableRecipe() {
		this.toolUses = this.maxTradeUses;
	}

	public void readFromTags(NBTTagCompound compound) {
		NBTTagCompound buyCompound = compound.getCompoundTag("buy");
		this.itemToBuy = ItemStack.loadItemStackFromNBT(buyCompound);
		NBTTagCompound sellCompound = compound.getCompoundTag("sell");
		this.itemToSell = ItemStack.loadItemStackFromNBT(sellCompound);

		if (compound.hasKey("buyB", 10)) {
			this.secondItemToBuy = ItemStack.loadItemStackFromNBT(compound.getCompoundTag("buyB"));
		}

		if (compound.hasKey("uses", 99)) {
			this.toolUses = compound.getInteger("uses");
		}

		if (compound.hasKey("maxUses", 99)) {
			this.maxTradeUses = compound.getInteger("maxUses");
		} else {
			this.maxTradeUses = 7;
		}
	}

	public NBTTagCompound writeToTags() {
		NBTTagCompound compound = new NBTTagCompound();
		compound.setTag("buy", this.itemToBuy.writeToNBT(new NBTTagCompound()));
		compound.setTag("sell", this.itemToSell.writeToNBT(new NBTTagCompound()));

		if (this.secondItemToBuy != null) {
			compound.setTag("buyB", this.secondItemToBuy.writeToNBT(new NBTTagCompound()));
		}

		compound.setInteger("uses", this.toolUses);
		compound.setInteger("maxUses", this.maxTradeUses);
		return compound;
	}
}
