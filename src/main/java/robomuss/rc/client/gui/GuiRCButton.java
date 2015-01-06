package robomuss.rc.client.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class GuiRCButton extends GuiButton {
	public static final ResourceLocation buttonTextures = new ResourceLocation("rc", "textures/gui/widgets.png");
	private int buttonU = 200;
	private int buttonV = 52;
	public int width;
	public int height;
	public int xPosition;
	public int yPosition;
	public String displayString;
	public int id;
	public boolean enabled;
	public boolean visible;
	public boolean hovering;
	public int packedFGColour;

	public GuiRCButton(int id, int xPosition, int yPosition, String displayString) {
		this(id, xPosition, yPosition, 200, 52, displayString);
	}

	public GuiRCButton(int id, int xPosition, int yPosition, int width, int height, String displayString) {
		super(id, xPosition, yPosition, width, height, displayString);
		this.enabled = true;
		this.visible = true;
		this.id = id;
		this.xPosition = xPosition;
		this.yPosition = yPosition;
		this.width = Math.min(this.buttonU, width);
		this.height = Math.min(this.buttonV, height);
		this.displayString = displayString;
	}

	@Override
	public int getHoverState(boolean hovering) {
		return !this.enabled ? 0 : hovering ? 2 : 1;
	}

	@Override
	public void drawButton(Minecraft minecraft, int x, int y) {
		if (this.visible) {
			FontRenderer fontRenderer = minecraft.fontRenderer;
			minecraft.getTextureManager().bindTexture(buttonTextures);
			GL11.glColor4f(1f, 1f, 1f, 1f);
			this.hovering = x >= this.xPosition && y >= this.yPosition && x < this.xPosition + this.width && y < this.yPosition + this.height;
			int hoverState = this.getHoverState(this.hovering);
			GL11.glEnable(GL11.GL_BLEND);
			OpenGlHelper.glBlendFunc(770, 771, 1, 0);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			//Top Left Corner
			this.drawTexturedModalRect(this.xPosition, this.yPosition, 0, hoverState * 52, this.width / 2, this.height / 2);
			//Top Right Corner
			this.drawTexturedModalRect(this.xPosition + (this.width / 2), this.yPosition, buttonU - (this.width / 2), hoverState * 52, this.width / 2, this.height / 2);
			//Bottom Left Corner
			this.drawTexturedModalRect(this.xPosition, this.yPosition + (this.height / 2), 0, ((hoverState + 1) * 52) - (this.height / 2), this.width / 2, this.height / 2);
			//Bottom Right Corner
			this.drawTexturedModalRect(this.xPosition + (this.width / 2), this.yPosition + (this.height / 2), buttonU - (this.width / 2), ((hoverState + 1) * 52) - (this.height / 2), this.width / 2, this.height / 2);
			this.mouseDragged(minecraft, x, y);
			int colour = packedFGColour != 0 ? packedFGColour : !this.enabled ? 10526880 : this.hovering ? 16777120 : 14737632;
			this.drawCenteredString(fontRenderer, this.displayString, this.xPosition + (this.width / 2), this.yPosition + ((this.height - 8) / 2), colour);
		}
	}

	@Override
	public void mouseDragged(Minecraft minecraft, int xPosition, int yPosition) {}

	@Override
	public void mouseReleased(int xPosition, int yPosition) {}

	@Override
	public boolean mousePressed(Minecraft minecraft, int xPosition, int yPosition) {
		return this.enabled && this.visible && xPosition >= this.xPosition && yPosition >= this.yPosition && xPosition < this.xPosition + this.width && yPosition < this.yPosition + this.height;
	}

	@Override
	public boolean func_146115_a() {
		return this.hovering;
	}

	@Override
	public void func_146111_b(int xPosition, int yPosition) {}

	@Override
	public void func_146113_a(SoundHandler soundHandler) {
		soundHandler.playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1f));
	}

	@Override
	public int getButtonWidth() {
		return this.width;
	}

	@Override
	public int func_154310_c() {
		return this.height;
	}
}
