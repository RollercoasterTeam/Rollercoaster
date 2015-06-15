package robomuss.rc.client.renderer;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

import robomuss.rc.block.model.ModelBenchHalf;
import robomuss.rc.block.model.ModelCatwalk;
import robomuss.rc.block.model.ModelSupport;
import robomuss.rc.block.model.ModelWoodenSupport;
import robomuss.rc.block.te.TileEntityBench;

public class ItemRenderBench implements IItemRenderer {

	private ModelBenchHalf model = new ModelBenchHalf();
	
    private ResourceLocation textures = new ResourceLocation("rc:textures/models/bench_half.png");
	
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
		GL11.glTranslatef((float) 0 + 0.2F, (float) 0 + 1F, (float) 0 + 0.5F);
		GL11.glScalef(0.75F, 0.75F, 0.75F);

		Minecraft.getMinecraft().renderEngine.bindTexture(textures);
		
		GL11.glPushMatrix();
        GL11.glRotatef(180f, -180f, 0f, 180f);
        
		
		this.model.render(null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
		
		GL11.glPopMatrix();
		GL11.glPopMatrix();
	}
}