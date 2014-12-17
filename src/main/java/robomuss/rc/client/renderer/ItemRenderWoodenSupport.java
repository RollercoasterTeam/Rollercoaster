package robomuss.rc.client.renderer;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;
import robomuss.rc.block.model.ModelWoodenSupport;

public class ItemRenderWoodenSupport implements IItemRenderer {

	private ModelWoodenSupport model = new ModelWoodenSupport();
	
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
        GL11.glRotatef(180, 1, 0, 0);
        ResourceLocation textures = (new ResourceLocation("rc:textures/models/wooden_support.png"));
        Minecraft.getMinecraft().renderEngine.bindTexture(textures);

        this.model.pillar1.render(0.0625F);  
        this.model.pillar2.render(0.0625F);   
        this.model.pillar3.render(0.0625F);  
        this.model.pillar4.render(0.0625F);   
        
        this.model.beam1.render(0.0625F);  
        this.model.beam2.render(0.0625F);      

        GL11.glPopMatrix();
	}
}