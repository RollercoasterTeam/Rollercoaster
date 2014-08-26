package robomuss.rc.events;

import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

import org.lwjgl.input.Keyboard;

import robomuss.rc.item.RCItems;
import robomuss.rc.network.NetworkHandler;
import robomuss.rc.util.IPaintable;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class BlockClickedEvent {
	
	@SubscribeEvent
	public void onBlockClicked(PlayerInteractEvent event) {
		if(event.world.isRemote) {
			if(event.entityPlayer.capabilities.isCreativeMode) {
				if(event.action == PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK) {
					if(event.entityPlayer.worldObj.getBlock(event.x, event.y, event.z) instanceof IPaintable) {
						if(Keyboard.isKeyDown(Keyboard.KEY_P)) {
							int meta = ((IPaintable) event.entityPlayer.worldObj.getBlock(event.x, event.y, event.z)).getPaintMeta(event.entityPlayer.worldObj, event.x, event.y, event.z);
							NetworkHandler.changePaintColour(meta);
						}
					}
				}
			}
		}
	}
}
