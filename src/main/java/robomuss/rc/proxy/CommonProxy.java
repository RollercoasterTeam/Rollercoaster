package robomuss.rc.proxy;

import robomuss.rc.client.gui.keybinding.TrackDesignerKeyBindings;

public class CommonProxy {
	public void initRenderers() {}
	
	public void initNetwork() {}

    public void registerKeybindings() {
	    TrackDesignerKeyBindings.registerBlankKeys();
    }
}
