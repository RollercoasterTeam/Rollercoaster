package robomuss.rc.client.renderer;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

import robomuss.rc.block.model.ModelConveyor;

public class ItemRenderConveyor implements IItemRenderer {
	private ModelConveyor model = new ModelConveyor();

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		return true;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
		return true;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		GL11.glPushMatrix();
		GL11.glTranslatef((float) 0 + 0.5F, (float) 0 + 1.4F, (float) 0 + 0.5F);
		GL11.glRotatef(180, 0, 0, 0);
		if (type == ItemRenderType.INVENTORY) {

		}

		ResourceLocation textures = (new ResourceLocation("rc:textures/models/conveyor.png"));
		Minecraft.getMinecraft().renderEngine.bindTexture(textures);

		this.model.side1.render(0.0625F);
		this.model.side2.render(0.0625F);
		this.model.conveyor.render(0.0625F);

		GL11.glPopMatrix();
	}
}
