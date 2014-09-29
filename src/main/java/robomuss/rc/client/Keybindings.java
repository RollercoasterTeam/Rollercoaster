package robomuss.rc.client;


import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;
import robomuss.rc.util.OSUtil;


public class Keybindings {
    public static final String CATEGORY = "key.categories.rc.trackdesigner";
    public static final String UP = "key.trackdesigner.up";
    public static final String DOWN = "key.trackdesigner.down";
    public static final String LEFT = "key.trackdesigner.lookleft";
    public static final String RIGHT = "key.trackdesigner.lookright";

    public static KeyBinding up = new KeyBinding(UP, Keyboard.KEY_PRIOR, CATEGORY);
    public static KeyBinding down = new KeyBinding(DOWN, Keyboard.KEY_NEXT, CATEGORY);
    public static KeyBinding lookLeft = new KeyBinding(LEFT, Keyboard.KEY_Q, CATEGORY);
    public static KeyBinding lookRight = new KeyBinding(RIGHT, Keyboard.KEY_E, CATEGORY);

    public static void init() {
        if (OSUtil.isMac()) {
            //sets the default to up and down on a mac
            up = new KeyBinding(UP, Keyboard.KEY_UP, CATEGORY);
            down = new KeyBinding(DOWN, Keyboard.KEY_DOWN, CATEGORY);
        }
    }


}
