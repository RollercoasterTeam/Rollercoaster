package robomuss.rc.event;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.item.Item;
import robomuss.rc.item.ItemBlockTrack;

public class RCTickHandler {
	public static boolean disableTrackItemAnimation = false;

	@SubscribeEvent
	public void receiveTick(TickEvent.PlayerTickEvent event) {
		/* This is all to disable the downward animation on the current equipped item when switching to a different hotbar slot, if that item is a track.
		 * I used this to help align the models during rendering.
		 * To prevent this from executing, set the above disableTrackItemAnimation value to false.
		 */
		if (disableTrackItemAnimation) {
			ItemRenderer itemRenderer = Minecraft.getMinecraft().entityRenderer.itemRenderer;

			if (event.player != null && event.player.getCurrentEquippedItem() != null) {
				if (event.player.getCurrentEquippedItem().getItem() != null) {
					Item item = event.player.getCurrentEquippedItem().getItem();

					if (item instanceof ItemBlockTrack) {
						if (itemRenderer.equippedItemSlot != -1) {
							itemRenderer.prevEquippedProgress = 0f;
							itemRenderer.equippedProgress = 0f;
							itemRenderer.updateEquippedItem();
							itemRenderer.prevEquippedProgress = 1f;
							itemRenderer.equippedProgress = 1f;
						}
					}
				}
			}
		}
	}
}
