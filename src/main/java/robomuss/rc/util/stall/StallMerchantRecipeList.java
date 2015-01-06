package robomuss.rc.util.stall;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.PacketBuffer;

import java.io.IOException;
import java.util.ArrayList;

public class StallMerchantRecipeList extends ArrayList {
	public StallMerchantRecipeList() {}

	public StallMerchantRecipeList(NBTTagCompound compound) {
		this.readRecipesFromTags(compound);
	}

	public StallMerchantRecipe canRecipeBeUsed(ItemStack itemBuying, ItemStack secondItemBuying, int index) {
		if (index > 0 && index < this.size()) {
			StallMerchantRecipe recipe = (StallMerchantRecipe) this.get(index);
			boolean first = itemBuying.getItem() == recipe.getItemToBuy().getItem() && itemBuying.stackSize >= recipe.getItemToBuy().stackSize;
			boolean second = (secondItemBuying == null && !recipe.hasSecondItemToBuy() || recipe.hasSecondItemToBuy() && secondItemBuying != null && recipe.getSecondItemToBuy().getItem() == secondItemBuying.getItem()) && (!recipe.hasSecondItemToBuy() || secondItemBuying.stackSize >= recipe.getSecondItemToBuy().stackSize);
			return first && second ? recipe : null;
		} else {
			for (int i = 0; i < this.size(); i++) {
				StallMerchantRecipe recipe = (StallMerchantRecipe) this.get(i);

				if (itemBuying.getItem() == recipe.getItemToBuy().getItem() && itemBuying.stackSize >= recipe.getItemToBuy().stackSize && (!recipe.hasSecondItemToBuy() && secondItemBuying == null || recipe.hasSecondItemToBuy() && secondItemBuying != null && recipe.getSecondItemToBuy().getItem() == secondItemBuying.getItem() && secondItemBuying.stackSize >= recipe.getSecondItemToBuy().stackSize)) {
					return recipe;
				}
			}

			return null;
		}
	}

	public void addToListWithCheck(StallMerchantRecipe recipe) {
		for (int i = 0; i < this.size(); i++) {
			StallMerchantRecipe recipe1 = (StallMerchantRecipe) this.get(i);

			if (recipe.hasSameIDsAs(recipe1)) {
				if (recipe.hasSameItemsAs(recipe1)) {
					this.set(i, recipe);
				}

				return;
			}
		}

		this.add(recipe);
	}

	public void writeRecipesToPacket(PacketBuffer buffer) throws IOException {
		buffer.writeByte((byte) (this.size() & 255));

		for (int i = 0; i < this.size(); i++) {
			StallMerchantRecipe recipe = (StallMerchantRecipe) this.get(i);
			buffer.writeItemStackToBuffer(recipe.getItemToBuy());
			buffer.writeItemStackToBuffer(recipe.getItemToSell());
			ItemStack secondItemToBuy = recipe.getSecondItemToBuy();
			buffer.writeBoolean(secondItemToBuy != null);

			if (secondItemToBuy != null) {
				buffer.writeItemStackToBuffer(secondItemToBuy);
			}

			buffer.writeBoolean(recipe.isRecipeDisabled());
		}
	}

	public void readRecipesFromTags(NBTTagCompound compound) {
		NBTTagList tagList = compound.getTagList("Recipes", 10);

		for (int i = 0; i < tagList.tagCount(); i++) {
			NBTTagCompound listCompound = tagList.getCompoundTagAt(i);
			this.add(new StallMerchantRecipe(listCompound));
		}
	}

	public NBTTagCompound getRecipesAsTags() {
		NBTTagCompound compound = new NBTTagCompound();
		NBTTagList tagList = new NBTTagList();

		for (int i = 0; i < this.size(); i++) {
			StallMerchantRecipe recipe = (StallMerchantRecipe) this.get(i);
			tagList.appendTag(recipe.writeToTags());
		}

		compound.setTag("Recipes", tagList);
		return compound;
	}

	@SideOnly(Side.CLIENT)
	public static StallMerchantRecipeList readRecipesFromPacket(PacketBuffer buffer) throws IOException {
		StallMerchantRecipeList recipes = new StallMerchantRecipeList();
		int size = buffer.readByte() & 255;

		for (int i = 0; i < size; i++) {
			ItemStack itemToBuy = buffer.readItemStackFromBuffer();
			ItemStack itemToSell = buffer.readItemStackFromBuffer();
			ItemStack secondItemToBuy = null;

			if (buffer.readBoolean()) {
				secondItemToBuy = buffer.readItemStackFromBuffer();
			}

			boolean isDisabled = buffer.readBoolean();
			StallMerchantRecipe recipe = new StallMerchantRecipe(itemToBuy, secondItemToBuy, itemToSell);

			if (isDisabled) {
				recipe.disableRecipe();
			}

			recipes.add(recipe);
		}

		return recipes;
	}
}
