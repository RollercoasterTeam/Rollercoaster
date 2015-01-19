package robomuss.rc.client.gui;

import robomuss.rc.util.RCOptions;

public class GuiRCOptionButton extends GuiRCButton {
	private final RCOptions.RCOptionsEnum rcOptionsEnum;

	public GuiRCOptionButton(int id, int x, int y, String displayString) {
		this(id, x, y, (RCOptions.RCOptionsEnum)null, displayString);
	}

	public GuiRCOptionButton(int id, int x, int y, int width, int height, String displayString) {
		super(id, x, y, width, height, displayString);
		this.rcOptionsEnum = null;
	}

	public GuiRCOptionButton(int id, int x, int y, RCOptions.RCOptionsEnum rcOptionsEnum, String displayString) {
		super(id, x, y, 150, 20, displayString);
		this.rcOptionsEnum = rcOptionsEnum;
	}

	public RCOptions.RCOptionsEnum returnRCOptionsEnum() {
		return this.rcOptionsEnum;
	}
}
