package robomuss.rc.util;

import java.util.ArrayList;

import robomuss.rc.block.RCBlocks;
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
	private int x, y, z;
	
	public StallMerchant(EntityPlayer customer, int x, int y, int z) {
		this.customer = customer;
		this.x = x;
		this.y = y;
		this.z = z;
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
		
		ArrayList<StallItem> array = customer.worldObj.getBlock(x, y, z) == RCBlocks.food_stall ? RCItems.food : RCItems.merch;
		
		for(StallItem food : array) {
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
