package robomuss.rc.event;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.client.event.GuiOpenEvent;
import robomuss.rc.client.gui.GuiTrackDesigner;

public class RCGuiOpenEvent {
	@SubscribeEvent
	public boolean canOpenGUI(GuiOpenEvent event) {
		if (event.gui instanceof GuiTrackDesigner) {
			return true;
		}
		return false;
	}
}
