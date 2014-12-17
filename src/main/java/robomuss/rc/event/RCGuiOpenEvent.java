package robomuss.rc.event;

<<<<<<< HEAD
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
=======
>>>>>>> origin/One8PortTake2
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
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
