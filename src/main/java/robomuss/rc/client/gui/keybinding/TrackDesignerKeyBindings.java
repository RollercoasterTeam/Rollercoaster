package robomuss.rc.client.gui.keybinding;

import org.lwjgl.input.Keyboard;

import robomuss.rc.RCMod;
import robomuss.rc.util.OSUtil;

public class TrackDesignerKeyBindings {
    public static final String CATEGORY = "key.categories.rc.trackdesigner";
	public static final String CONFLICTS = "key.trackdesigner.conflicts";
    public static final String UP = "key.trackdesigner.up";
    public static final String DOWN = "key.trackdesigner.down";
    public static final String LOOK_LEFT = "key.trackdesigner.lookleft";
    public static final String LOOK_RIGHT = "key.trackdesigner.lookright";
	public static final String FORWARD = "key.trackdesigner.forward";
	public static final String BACKWARD = "key.trackdesigner.backward";
	public static final String LEFT = "key.trackdesigner.left";
	public static final String RIGHT = "key.trackdesigner.right";

	public static RCKeyBinding up        = new RCKeyBinding(UP,         Keyboard.KEY_NONE, CATEGORY, Keyboard.KEY_NONE);
	public static RCKeyBinding down      = new RCKeyBinding(DOWN,       Keyboard.KEY_NONE, CATEGORY, Keyboard.KEY_NONE);
	public static RCKeyBinding lookLeft  = new RCKeyBinding(LOOK_LEFT,  Keyboard.KEY_NONE, CATEGORY, Keyboard.KEY_NONE);
	public static RCKeyBinding lookRight = new RCKeyBinding(LOOK_RIGHT, Keyboard.KEY_NONE, CATEGORY, Keyboard.KEY_NONE);
	public static RCKeyBinding forward   = new RCKeyBinding(FORWARD,    Keyboard.KEY_NONE, CATEGORY, Keyboard.KEY_NONE);
	public static RCKeyBinding backward  = new RCKeyBinding(BACKWARD,   Keyboard.KEY_NONE, CATEGORY, Keyboard.KEY_NONE);
	public static RCKeyBinding left      = new RCKeyBinding(LEFT,       Keyboard.KEY_NONE, CATEGORY, Keyboard.KEY_NONE);
	public static RCKeyBinding right     = new RCKeyBinding(RIGHT,      Keyboard.KEY_NONE, CATEGORY, Keyboard.KEY_NONE);

	public static boolean haveKeyBindsBeenInitialized = false;

	private static RCKeyBinding[] rcKeys = {up, down, lookLeft, lookRight, forward, backward, left, right};
//	private static List<RCKeyBinding> rcKeybinds = Arrays.asList(rcKeys);

	public static void registerBlankKeys() {
		for (RCKeyBinding rcKeyBinding : rcKeys) {
			rcKeyBinding.setRCEnteredCode(Keyboard.KEY_NONE);
		}
		RCKeyBinding.registerRCKeyBindings(rcKeys);
	}

    public static void init() {
	    for (RCKeyBinding rcKeyBinding : rcKeys) {
		    if (rcKeyBinding.getRCEnteredCode() != Keyboard.KEY_NONE) {
			    rcKeyBinding.setRCKeyCode(rcKeyBinding.getRCEnteredCode());
		    }
	    }
		haveKeyBindsBeenInitialized = true;
    }

	private static void setDefaultKeyBindings() {
		rcKeys[0].setRCKeyCode(OSUtil.isMac() ? Keyboard.KEY_UP : Keyboard.KEY_PRIOR);
		rcKeys[1].setRCKeyCode(OSUtil.isMac() ? Keyboard.KEY_DOWN : Keyboard.KEY_NEXT);
		rcKeys[2].setRCKeyCode(Keyboard.KEY_Q);
		rcKeys[3].setRCKeyCode(Keyboard.KEY_E);
		rcKeys[4].setRCKeyCode(Keyboard.KEY_W);
		rcKeys[5].setRCKeyCode(Keyboard.KEY_S);
		rcKeys[6].setRCKeyCode(Keyboard.KEY_A);
		rcKeys[7].setRCKeyCode(Keyboard.KEY_D);
	}

	public static void unBindDesignerKeys() {
		for (RCKeyBinding rcKeyBinding : rcKeys) {
			rcKeyBinding.setRCKeyCode(Keyboard.KEY_NONE);
		}
		RCMod.rcOptions.saveRCOptions();
		haveKeyBindsBeenInitialized = false;
	}

	public static RCKeyBinding[] getRCKeyBinds() {
		return rcKeys;
	}
}
