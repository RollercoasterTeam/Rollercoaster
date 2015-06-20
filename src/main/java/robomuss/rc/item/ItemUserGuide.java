package robomuss.rc.item;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import robomuss.rc.RCMod;
import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemUserGuide extends Item {

	public ItemUserGuide() {
		super();
		setMaxStackSize(1);
	}
	
	@SuppressWarnings({ "rawtypes", "static-access", "unchecked" })
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean val) {
		/*if(!Minecraft.getMinecraft().currentScreen.isShiftKeyDown()) {
			list.add(LanguageHandler.getLocalization("atomicraft.shift"));
		}
		else {
			for(Compound compound : CompoundRegistry.compounds) {
				if(compound.group == CompoundGroup.values()[stack.getItemDamage()]) {
					list.add(compound.name);
				}
			}
		}*/
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		FMLNetworkHandler.openGui(player, RCMod.instance, 5, world, (int) player.posX, (int) player.posY, (int) player.posZ);
		return stack;
	}
	
}
