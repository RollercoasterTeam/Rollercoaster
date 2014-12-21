package robomuss.rc.util;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.ItemFood;
import robomuss.rc.RCMod;

public class FoodType {

	public String displayName;
	public ItemFood item;
	
	public FoodType(String displayName, String textureName) {
		this.displayName = displayName;
		
		item = (ItemFood) new ItemFood(4, false).setUnlocalizedName(textureName).setTextureName("rc:food/" + textureName).setCreativeTab(RCMod.other);
		GameRegistry.registerItem(item, textureName);
	}
}
