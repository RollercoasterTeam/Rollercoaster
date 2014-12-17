package robomuss.rc.client.gui;

import java.util.Arrays;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiListExtended;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.apache.commons.lang3.ArrayUtils;
import org.lwjgl.input.Keyboard;

import robomuss.rc.RCMod;
import robomuss.rc.client.gui.keybinding.RCKeyBinding;
import robomuss.rc.client.gui.keybinding.TrackDesignerKeyBindings;

@SideOnly(Side.CLIENT)
public class GuiRCKeyBindingList extends GuiListExtended {
	private final GuiRCControls controls;
	private final Minecraft mc;
	private final IGuiListEntry[] entries;
	private int horizontal_space = 0;

	public GuiRCKeyBindingList(GuiRCControls controls, Minecraft mc) {
		super(mc, controls.width, controls.height, 63, controls.height - 32, 20);
		this.controls = controls;
		this.mc = mc;
		RCKeyBinding[] keyBindings = ArrayUtils.clone(TrackDesignerKeyBindings.getRCKeyBinds());
		this.entries = new IGuiListEntry[keyBindings.length + RCKeyBinding.getRCKeyBinds().size()];
		Arrays.sort(keyBindings);
		int i = 0;
		String s = null;
		RCKeyBinding[] keyBindingsCopy = keyBindings;
		int j = keyBindings.length;

		for (int k = 0; k < j; ++k) {
			RCKeyBinding keyBinding = keyBindingsCopy[k];
			String category = keyBinding.getRCKeyCategory();

			if (!category.equals(s)) {
				s = category;
				this.entries[i++] = new GuiRCKeyBindingList.RCCategoryEntry(category);
			}

			int width = mc.fontRenderer.getStringWidth(I18n.format(keyBinding.getRCKeyDescription(), new Object[0]));

			if (width > this.horizontal_space) {
				this.horizontal_space = width;
			}

			this.entries[i++] = new GuiRCKeyBindingList.RCKeyEntry(keyBinding);
		}
	}

	@Override
	protected int getSize() {
		return this.entries.length;
	}

	@Override
	public IGuiListEntry getListEntry(int index) {
		return this.entries[index];
	}

	@Override
	protected int getScrollBarX() {
		return super.getScrollBarX() + 15;
	}

	@Override
	public int getListWidth() {
		return super.getListWidth() + 32;
	}

	@SideOnly(Side.CLIENT)
	public class RCCategoryEntry implements IGuiListEntry {
		private final String displayString;
		private final int displayStringWidth;

		public RCCategoryEntry(String displayString) {
			this.displayString = I18n.format(displayString, new Object[0]);
			this.displayStringWidth = GuiRCKeyBindingList.this.mc.fontRenderer.getStringWidth(this.displayString);
		}

		@Override
		public void drawEntry(int a, int x, int y, int width, int height, Tessellator tessellator, int btnWidth, int btnHeight, boolean flag) {
			GuiRCKeyBindingList.this.mc.fontRenderer.drawString(this.displayString, GuiRCKeyBindingList.this.mc.currentScreen.width / 2 - this.displayStringWidth / 2, y + height - GuiRCKeyBindingList.this.mc.fontRenderer.FONT_HEIGHT - 1, 16777215);
		}

		@Override
		public boolean mousePressed(int a, int b, int c, int d, int e, int f) {
			return false;
		}

		@Override
		public void mouseReleased(int a, int b, int c, int d, int e, int f) {}
	}

	@SideOnly(Side.CLIENT)
	public class RCKeyEntry implements IGuiListEntry {
		private final RCKeyBinding rcKeyBinding;
		private final String rcKeyDescription;
		private final GuiButton btnChangeRCKeyBinding;
		private final GuiButton btnReset;

		private RCKeyEntry(RCKeyBinding rcKeyBinding) {
			this.rcKeyBinding = rcKeyBinding;
			this.rcKeyDescription = I18n.format(rcKeyBinding.getRCKeyDescription(), new Object[0]);
			this.btnChangeRCKeyBinding = new GuiButton(0, 0, 0, 75, 18, I18n.format(rcKeyBinding.getRCKeyDescription(), new Object[0]));
			this.btnReset = new GuiButton(0, 0, 0, 50, 18, I18n.format("controls.reset", new Object[0]));
		}

		public void drawEntry(int a, int x, int y, int width, int height, Tessellator tessellator, int btnWidth, int btnHeight, boolean flag) {
			boolean flag1 = GuiRCKeyBindingList.this.controls.buttonID == this.rcKeyBinding;
			GuiRCKeyBindingList.this.mc.fontRenderer.drawString(this.rcKeyDescription, x + 90 - GuiRCKeyBindingList.this.horizontal_space, y + height / 2 - GuiRCKeyBindingList.this.mc.fontRenderer.FONT_HEIGHT / 2, 16777215);
			this.btnReset.xPosition = x + 190;
			this.btnReset.yPosition = y;
			this.btnReset.enabled = this.rcKeyBinding.getRCKeyCode() != Keyboard.KEY_NONE;
			this.btnReset.drawButton(GuiRCKeyBindingList.this.mc, btnWidth, btnHeight);
			this.btnChangeRCKeyBinding.xPosition = x + 105;
			this.btnChangeRCKeyBinding.yPosition = y;
			this.btnChangeRCKeyBinding.displayString = this.rcKeyBinding.getKeyNameUpper();
			boolean flag2 = false;

			if (this.rcKeyBinding.getRCKeyCode() != Keyboard.KEY_NONE) {
				RCKeyBinding[] rcKeyBindings = TrackDesignerKeyBindings.getRCKeyBinds();
				int width1 = rcKeyBindings.length;

				for (int i = 0; i < width1; ++i) {
					RCKeyBinding rcKeyBinding1 = rcKeyBindings[i];

					if (rcKeyBinding1 != this.rcKeyBinding && rcKeyBinding1.getRCKeyCode() == this.rcKeyBinding.getRCKeyCode()) {
						flag2 = true;
						break;
					}
				}
			}

			if (flag1) {
				this.btnChangeRCKeyBinding.displayString = EnumChatFormatting.WHITE + ">" + EnumChatFormatting.YELLOW + this.btnChangeRCKeyBinding.displayString + EnumChatFormatting.WHITE + "<";
			} else if (flag2) {
				this.btnChangeRCKeyBinding.displayString = EnumChatFormatting.RED + this.btnChangeRCKeyBinding.displayString;
			}
			this.btnChangeRCKeyBinding.drawButton(GuiRCKeyBindingList.this.mc, btnWidth, btnHeight);
		}

		public boolean mousePressed(int a, int x, int y, int b, int c, int d) {
			if (this.btnChangeRCKeyBinding.mousePressed(GuiRCKeyBindingList.this.mc, x, y)) {
				GuiRCKeyBindingList.this.controls.buttonID = this.rcKeyBinding;
				return true;
			} else if (this.btnReset.mousePressed(GuiRCKeyBindingList.this.mc, x, y)) {
				RCMod.rcOptions.setRCOptionKeyBinding(this.rcKeyBinding, Keyboard.KEY_NONE, true);
				RCKeyBinding.resetRCKeyBindingArrayAndHash();
				return true;
			} else {
				return false;
			}
		}

		public void mouseReleased(int a, int x, int y, int b, int c, int d) {
			this.btnChangeRCKeyBinding.mouseReleased(x, y);
			this.btnReset.mouseReleased(x, y);
		}
	}
}
