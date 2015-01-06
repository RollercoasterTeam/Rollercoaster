package robomuss.rc.util.stall;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public interface IStallMerchant {
	void setCustomer(EntityPlayer customer);
	EntityPlayer getCustomer();
	StallMerchantRecipeList getRecipes(EntityPlayer customer);
	@SideOnly(Side.CLIENT)
	void setRecipes(StallMerchantRecipeList recipes);
	void useRecipe(StallMerchantRecipe recipe);
	void verifySellingItem(ItemStack itemStack);
}
