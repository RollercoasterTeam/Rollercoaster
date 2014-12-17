package robomuss.rc.client.gui.keybinding;

import net.minecraft.client.resources.I18n;
import net.minecraft.util.IntHashMap;
import org.lwjgl.input.Keyboard;
import robomuss.rc.RCMod;

import java.util.*;

public class RCKeyBinding implements Comparable {
	private static final List rcKeyBindArray = new ArrayList();
	private static final IntHashMap rcHash = new IntHashMap();
	private static final Set rcKeyBindSet = new HashSet();
	private final String rcKeyDescription;
	private final String rcKeyCategory;
	private int rcKeyCode;
	private int rcEnteredCode = Keyboard.KEY_NONE;    //this gets set to the key that the user enters into the Controls GUI, to keep track of it when the switch is made.
	private boolean pressed;
	private int pressTime;
	private String rcKeyName = "none";

	public static void onTick(int keyCode) {
		if (keyCode != 0) {
			RCKeyBinding rcKeyBinding = (RCKeyBinding) rcHash.lookup(keyCode);

			if (rcKeyBinding != null) {
				rcKeyBinding.pressTime++;
			}
		}
	}

	public static void setRCKeyBindState(int keyCode, boolean state) {
		if (keyCode != 0) {
			RCKeyBinding rcKeyBinding = (RCKeyBinding) rcHash.lookup(keyCode);

			if (rcKeyBinding != null) {
				rcKeyBinding.pressed = state;
			}
		}
	}

	public static void unPressAllKeys() {
		Iterator iterator = rcKeyBindArray.iterator();

		while (iterator.hasNext()) {
			RCKeyBinding rcKeyBinding = (RCKeyBinding)iterator.next();
			rcKeyBinding.unPressKey();
		}
	}

	public static void unPressAllKeys(String rcKeyCategory) {
		Iterator iterator = rcKeyBindArray.iterator();

		while (iterator.hasNext()) {
			RCKeyBinding rcKeyBinding = (RCKeyBinding)iterator.next();
			if (rcKeyBinding.rcKeyCategory.equals(rcKeyCategory)) {
				rcKeyBinding.unPressKey();
			}
		}
	}

	public static void resetRCKeyBindingArrayAndHash() {
		rcHash.clearMap();
		Iterator iterator = rcKeyBindArray.iterator();

		while (iterator.hasNext()) {
			RCKeyBinding rcKeyBinding = (RCKeyBinding)iterator.next();
			rcHash.addKey(rcKeyBinding.rcKeyCode, rcKeyBinding);
		}
	}

	public static Set getRCKeyBinds() {
		return rcKeyBindSet;
	}

	public RCKeyBinding(String description, int code, String category) {
		this(description, code, category, Keyboard.KEY_NONE);
	}

	public RCKeyBinding(String description, int code, String category, int enteredCode) {
		this.rcKeyDescription = description;
		this.rcKeyCode = code;
		this.rcKeyCategory = category;
		this.rcEnteredCode = enteredCode;
		this.rcKeyName = Keyboard.getKeyName(code);
		rcKeyBindArray.add(this);
		rcHash.addKey(code, this);
		rcKeyBindSet.add(category);
	}

	public boolean getIsKeyPressed() {
		return this.pressed;
	}

	public String getRCKeyCategory() {
		return this.rcKeyCategory;
	}

	public boolean isPressed() {
		if (this.pressTime == 0) {
			return false;
		} else {
			this.pressTime--;
			return true;
		}
	}

	private void unPressKey() {
		this.pressTime = 0;
		this.pressed = false;
	}

	public String getRCKeyDescription() {
		return this.rcKeyDescription;
	}

	public int getRCEnteredCode() {
		return this.rcEnteredCode;
	}

	public void setRCEnteredCode(int rcEnteredCode) {
		this.rcEnteredCode = rcEnteredCode;
	}

	public void resetEnteredCode() {
		this.rcEnteredCode = Keyboard.KEY_NONE;
	}

	public String getKeyNameUpper() {
		return this.rcKeyName.toUpperCase();
	}

	public String getKeyNameLower() {
		return this.rcKeyName.toLowerCase();
	}

	public String getRCKeyName() {
		return this.rcKeyName;
	}

	public void setRCKeyCode(int code) {
		this.rcKeyCode = code;
		this.rcKeyName = code != Keyboard.KEY_NONE ? Keyboard.getKeyName(code) : "none";
	}

	public int getRCKeyCode() {
		return this.rcKeyCode;
	}

	public static void registerRCKeyBindings(RCKeyBinding[] rcKeyBindings) {
		RCMod.rcOptions.registerRCKeyBindings(rcKeyBindings);
	}

	public int compareTo(RCKeyBinding rcKeyBinding) {
		int i = I18n.format(this.rcKeyCategory, new Object[0]).compareTo(I18n.format(rcKeyBinding.rcKeyCategory, new Object[0]));

		return i == 0 ? I18n.format(this.rcKeyDescription, new Object[0]).compareTo(I18n.format(rcKeyBinding.rcKeyDescription, new Object[0])) : i;
	}

	@Override
	public int compareTo(Object o) {
		return this.compareTo((RCKeyBinding)o);
	}
}
