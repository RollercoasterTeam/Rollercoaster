package robomuss.rc.util;

import robomuss.rc.item.RCItems;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.IMerchant;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;

public class StallMerchant implements IMerchant {

	public EntityPlayer customer;
	
	public StallMerchant(EntityPlayer customer) {
		this.customer = customer;
	}
	
	@Override
	public void setCustomer(EntityPlayer p_70932_1_) {

	}

	@Override
	public EntityPlayer getCustomer() {
		return customer;
	}

	@Override
	public MerchantRecipeList getRecipes(EntityPlayer p_70934_1_) {
		MerchantRecipeList list = new MerchantRecipeList();
		for(FoodType food : RCItems.food) {
			list.add(new MerchantRecipe(new ItemStack(RCItems.coin, 4), food.item));
		}
		return list;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void setRecipes(MerchantRecipeList p_70930_1_) {
		
	}

	@Override
	public void useRecipe(MerchantRecipe recipe) {
		
	}

	@Override
	public void func_110297_a_(ItemStack p_110297_1_) {
		
	}

}
