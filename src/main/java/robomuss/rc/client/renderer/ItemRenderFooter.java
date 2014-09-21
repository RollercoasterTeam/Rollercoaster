package robomuss.rc.client.renderer;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

import robomuss.rc.block.model.ModelFooter;

public class ItemRenderFooter implements IItemRenderer {

	private ModelFooter model = new ModelFooter();
	
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
        int colour = 0;
        GL11.glTranslatef((float) 0 + 0.5F, (float) 0 + 1.35F, (float) 0 + 0.5F);
        GL11.glRotatef(180, 1, 0, 0);
        ResourceLocation textures = (new ResourceLocation("rc:textures/models/footer.png"));
        Minecraft.getMinecraft().renderEngine.bindTexture(textures);

        this.model.footer.render(0.0625F);  
        
        textures = (new ResourceLocation("rc:textures/models/colour_" + colour + ".png"));
        Minecraft.getMinecraft().renderEngine.bindTexture(textures);
        
        GL11.glPopMatrix();
	}
}