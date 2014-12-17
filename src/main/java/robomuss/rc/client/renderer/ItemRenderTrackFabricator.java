package robomuss.rc.client.renderer;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

import robomuss.rc.block.model.ModelTrackFabricator;

public class ItemRenderTrackFabricator implements IItemRenderer {

	private ModelTrackFabricator model = new ModelTrackFabricator();
	
	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		return true;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
		return true;
	}

	private static float lastRot = 0;
	
	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		GL11.glPushMatrix();
        GL11.glTranslatef((float) 0 + 0.5F, (float) 0 + 1.5F, (float) 0 + 0.5F);
        GL11.glRotatef(180, 1, 0, 0);
        if(type == ItemRenderType.INVENTORY) {
			GL11.glTranslatef(0, 0.1f, 0);
	        GL11.glRotatef(lastRot, 0, 1, 0);
	        lastRot += 0.1F;
        }
        	
        ResourceLocation textures = (new ResourceLocation("rc:textures/models/track_fabricator.png"));
        Minecraft.getMinecraft().renderEngine.bindTexture(textures);

        this.model.bottom.render(0.0625F);
        this.model.panel.render(0.0625F);
        this.model.pillar1.render(0.0625F);
        this.model.pillar2.render(0.0625F);
        this.model.pillar3.render(0.0625F);
        this.model.pillar4.render(0.0625F);
        this.model.top.render(0.0625F);
        this.model.part1.render(0.0625F);
        this.model.glass1.render(0.0625F);
        this.model.glass2.render(0.0625F);
        this.model.glass3.render(0.0625F);
        this.model.glass4.render(0.0625F);

        GL11.glPopMatrix();
	}
}