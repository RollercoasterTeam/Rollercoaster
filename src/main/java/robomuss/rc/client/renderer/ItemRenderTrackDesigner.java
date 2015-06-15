package robomuss.rc.client.renderer;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import robomuss.rc.block.model.ModelTrackDesigner;
import robomuss.rc.block.model.ModelTrackFabricator;
import robomuss.rc.block.render.TileEntityRenderTrackFabricator;
import robomuss.rc.block.te.TileEntityTrackFabricator;

public class ItemRenderTrackDesigner implements IItemRenderer {

	private ModelTrackDesigner model = new ModelTrackDesigner();
	
	private ResourceLocation textures = new ResourceLocation("rc:textures/models/track_designer.png");
	
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
        GL11.glTranslatef((float) 0 + 0.5F, (float) 0 + 1.35F, (float) 0 + 0.5F);
        GL11.glRotatef(180, 1, 0, 0);
        Minecraft.getMinecraft().renderEngine.bindTexture(textures);

        this.model.post.render(0.0625F);
        this.model.panel.render(0.0625F);
        
        this.model.up.render(0.0625F);
        this.model.right.render(0.0625F);
        this.model.down.render(0.0625F);
        this.model.left.render(0.0625F);

        GL11.glPopMatrix();
	}
}