package robomuss.rc.client.renderer;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

import robomuss.rc.block.model.ModelFooter;

public class ItemRenderFooter implements IItemRenderer {

	private ModelFooter model = new ModelFooter();
	
	private ResourceLocation footerTextures = new ResourceLocation("rc:textures/models/footer.png");
	private ResourceLocation textures = new ResourceLocation("rc:textures/models/colour_0.png");
	
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
        GL11.glTranslatef((float) 0 + 0.5F, (float) 0 + 1.35F, (float) 0 + 0.5F);
        GL11.glRotatef(180, 1, 0, 0);
        Minecraft.getMinecraft().renderEngine.bindTexture(textures);
        
        this.model.middle.render(0.0625F);  
        this.model.middle2.render(0.0625F); 
        
        this.model.flange1.render(0.0625F);  
        this.model.flange2.render(0.0625F);
        
        Minecraft.getMinecraft().renderEngine.bindTexture(footerTextures);

        this.model.footer.render(0.0625F);  

        GL11.glPopMatrix();
	}
}