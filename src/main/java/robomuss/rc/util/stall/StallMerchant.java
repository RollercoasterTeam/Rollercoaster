package robomuss.rc.util.stall;

import java.util.ArrayList;

import robomuss.rc.block.RCBlocks;
import robomuss.rc.item.RCItems;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class StallMerchant implements IStallMerchant {
	public EntityPlayer customer;
	private int x, y, z;
	
	public StallMerchant(EntityPlayer customer, int x, int y, int z) {
		this.customer = customer;
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	@Override
	public void setCustomer(EntityPlayer player) {
		this.customer = player;
	}

	@Override
	public EntityPlayer getCustomer() {
		return customer;
	}

	@Override
	public StallMerchantRecipeList getRecipes(EntityPlayer player) {
		StallMerchantRecipeList list = new StallMerchantRecipeList();
		
		ArrayList<StallItem> array = this.customer.worldObj.getBlock(x, y, z) == RCBlocks.food_stall ? RCItems.food.items : RCItems.merch.items;
		
		for(StallItem food : array) {
			list.add(new StallMerchantRecipe(new ItemStack(RCItems.coin, 4), food.item));
		}

		return list;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void setRecipes(StallMerchantRecipeList recipes) {}

	@Override
	public void useRecipe(StallMerchantRecipe recipe) {}

	@Override
	public void verifySellingItem(ItemStack itemStack) {}
}
