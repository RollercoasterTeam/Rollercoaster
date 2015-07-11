package robomuss.rc.client.renderer;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

import robomuss.rc.block.model.ModelSupport;
import robomuss.rc.item.ItemExtra;
import robomuss.rc.track.TrackHandler;

public class ItemRenderExtra implements IItemRenderer {

	private ModelSupport model = new ModelSupport();
	
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
        GL11.glTranslatef((float) 0 + 0.5F, (float) 0 + 1.5F, (float) 0 + 0.5F);
        GL11.glRotatef(180, 1, 0, 0);
        Minecraft.getMinecraft().renderEngine.bindTexture(TrackHandler.extras.get(((ItemExtra) item.getItem()).id).texture);

        TrackHandler.extras.get(((ItemExtra) item.getItem()).id).model.render(null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F); 

        GL11.glPopMatrix();
	}
}