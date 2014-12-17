package robomuss.rc.event;

<<<<<<< HEAD
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
=======
import java.lang.reflect.Field;

>>>>>>> origin/One8PortTake2
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import robomuss.rc.item.ItemBlockTrack;

<<<<<<< HEAD
import java.lang.reflect.Field;

=======
>>>>>>> origin/One8PortTake2
public class RCTickHandler {
	public static boolean shouldReflect = false;

	@SubscribeEvent
	public void receiveTick(TickEvent.PlayerTickEvent event) {
		/* This is all to disable the downward animation on the current equipped item when switching to a different hotbar slot, if that item is a track.
		 * I used this to help align the models during rendering.
		 * To prevent this from executing, set the above shouldReflect value to false.
		 * WARNING: this uses reflection, and could cause lag in-game, which is why it is disabled in the main mod class by default
		 */
		if (shouldReflect) {
			Field prevEquippedProgress = null;
			Field equippedProgress = null;
			Field equippedItemSlot = null;

			try {
				ItemRenderer itemRenderer = Minecraft.getMinecraft().entityRenderer.itemRenderer;

				prevEquippedProgress = ItemRenderer.class.getDeclaredField("prevEquippedProgress");
				equippedProgress = ItemRenderer.class.getDeclaredField("equippedProgress");
				equippedItemSlot = ItemRenderer.class.getDeclaredField("equippedItemSlot");

				prevEquippedProgress.setAccessible(true);
				equippedProgress.setAccessible(true);
				equippedItemSlot.setAccessible(true);

				Integer equippedItemSlotValue = (Integer) equippedItemSlot.get(itemRenderer);

				if (event.player != null && event.player.getCurrentEquippedItem() != null) {
					if (event.player.getCurrentEquippedItem().getItem() != null) {
						Item item = event.player.getCurrentEquippedItem().getItem();
						if (item instanceof ItemBlockTrack) {
							if (equippedItemSlotValue != -1) {
								prevEquippedProgress.set(itemRenderer, new Float(0.0));
								equippedProgress.set(itemRenderer, new Float(0.0));
								itemRenderer.updateEquippedItem();
								prevEquippedProgress.set(itemRenderer, new Float(1.0));
								equippedProgress.set(itemRenderer, new Float(1.0));
							}
						}
					}
				}
			} catch (NoSuchFieldException exception) {
				;
			} catch (IllegalAccessException exception) {
				;
			}
		}
	}
}
