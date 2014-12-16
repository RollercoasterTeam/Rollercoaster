package robomuss.rc.client.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import robomuss.rc.client.gui.keybinding.RCKeyBinding;
import robomuss.rc.client.gui.keybinding.TrackDesignerKeyBindings;
import robomuss.rc.util.RCOptions;

@SideOnly(Side.CLIENT)
public class GuiRCControls extends GuiScreen {
	private static final RCOptions.RCOptionsEnum[] rcOptionsEnums = new RCOptions.RCOptionsEnum[] {RCOptions.RCOptionsEnum.INVERT_MOUSE, RCOptions.RCOptionsEnum.SENSITIVITY, RCOptions.RCOptionsEnum.TOUCHSCREEN};
	private GuiScreen parentScreen;
	protected String CONTROLS = "Controls";
	private RCOptions options;
	public RCKeyBinding buttonID = null;
	public long time;
	private GuiRCKeyBindingList rcKeyBindingList;
	private GuiButton button;

	public GuiRCControls(GuiScreen parentScreen, RCOptions options) {
		this.parentScreen = parentScreen;
		this.options = options;
	}

	public void initGui() {
		this.rcKeyBindingList = new GuiRCKeyBindingList(this, this.mc);
		this.buttonList.add(new GuiButton(200, this.width / 2 - 155, this.height - 29, 150, 20, I18n.format("gui.done", new Object[0])));
		this.buttonList.add(this.button = new GuiButton(201, this.width / 2 - 155 + 160, this.height - 29, 150, 20, I18n.format("controls.resetAll", new Object[0])));
		this.CONTROLS = I18n.format("controls.title", new Object[0]);
		int i = 0;
		RCOptions.RCOptionsEnum[] rcOptionEnums = rcOptionsEnums;
		int j = rcOptionEnums.length;

//		for (int k = 0; k < j; k++) {
//			RCOptions.RCOptionsEnum rcOptionsEnum = rcOptionEnums[k];
//
//			if (!rcOptionsEnum.getEnumFloat()) {
//				this.buttonList.add(new GuiRCOptionButton(rcOptionsEnum.returnEnumOrdinal(), this.width / 2 - 155 + i % 2 * 160, 18 + 24 * (i >> 1), rcOptionsEnum, this.options.getRCKeyBinding(rcOptionsEnum)));
//			}
//		}
	}

	protected void actionPerformed(GuiButton button) {
		if (button.id == 200) {
			this.options.saveRCOptions();
			this.mc.displayGuiScreen(this.parentScreen);
		} else if (button.id == 201) {
			RCKeyBinding[] rcKeyBindings = TrackDesignerKeyBindings.getRCKeyBinds();
			int i = rcKeyBindings.length;

			for (int j = 0; j < i; j++) {
				RCKeyBinding rcKeyBinding = rcKeyBindings[j];
				rcKeyBinding.resetEnteredCode();
				rcKeyBinding.setRCKeyCode(rcKeyBinding.getRCEnteredCode());
			}

			RCKeyBinding.resetRCKeyBindingArrayAndHash();
		}
	}

	protected void mouseClicked(int x, int y, int code) {
		if (this.buttonID != null) {
			this.options.setRCOptionKeyBinding(this.buttonID, -100 + code, true);
			this.buttonID = null;
			RCKeyBinding.resetRCKeyBindingArrayAndHash();
		} else if (code != 0 || !this.rcKeyBindingList.func_148179_a(x, y, code)) {
			super.mouseClicked(x, y, code);
		}
	}

	protected void mouseMovedOrUp(int x, int y, int code) {
		if (code != 0 || !this.rcKeyBindingList.func_148181_b(x, y, code)) {
			super.mouseMovedOrUp(x, y, code);
		}
	}

	protected void keyTyped(char key, int code) {
		if (this.buttonID != null) {
			if (code == 1) {
				this.options.setRCOptionKeyBinding(this.buttonID, 0, true);
			} else {
				this.options.setRCOptionKeyBinding(this.buttonID, code, true);
			}

			this.buttonID = null;
			this.time = Minecraft.getSystemTime();
			RCKeyBinding.resetRCKeyBindingArrayAndHash();
		} else {
			super.keyTyped(key, code);
		}
	}

	public void drawScreen(int x, int y, float value) {
		this.drawDefaultBackground();
		this.rcKeyBindingList.drawScreen(x, y, value);
		this.drawCenteredString(this.fontRendererObj, this.CONTROLS, this.width / 2, 8, 16777215);
		boolean flag = true;
		RCKeyBinding[] rcKeyBindings = TrackDesignerKeyBindings.getRCKeyBinds();
		int k = rcKeyBindings.length;

		for (int i = 0; i < k; i++) {
			RCKeyBinding rcKeyBinding = rcKeyBindings[i];
			if (rcKeyBinding.getRCKeyCode() != rcKeyBinding.getRCEnteredCode()) {
				flag = false;
				break;
			}
		}
		this.button.enabled = !flag;
		super.drawScreen(x, y, value);
	}
}
