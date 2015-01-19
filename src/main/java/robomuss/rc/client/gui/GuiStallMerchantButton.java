package robomuss.rc.client.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class GuiStallMerchantButton extends GuiRCButton {
	private final boolean clickable;
	private ResourceLocation texture = new ResourceLocation("rc", "textures/gui/stall_main.png");

	public GuiStallMerchantButton(int id, int x, int y, boolean enabled) {
		super(id, x, y, 12, 19, "");
		this.clickable = enabled;
	}

	@Override
	public void drawButton(Minecraft minecraft, int x, int y) {
		if (this.visible) {
			minecraft.getTextureManager().bindTexture(texture);
			GL11.glColor4f(1f, 1f, 1f, 1f);
			boolean hovering = (x >= this.xPosition && y >= this.yPosition) && (x < this.xPosition + this.width && y < this.yPosition + this.height);
			int textureX = 176;
			int textureY = 0;

			if (!this.enabled) {
				textureX += this.width * 2;
			} else if (hovering) {
				textureX += this.width;
			}

			if (!this.clickable) {
				textureY += this.height;
			}

			this.drawTexturedModalRect(this.xPosition, this.yPosition, textureX, textureY, this.width, this.height);
		}
	}
}
