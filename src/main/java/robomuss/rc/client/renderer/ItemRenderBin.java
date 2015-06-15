package robomuss.rc.client.renderer;

import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

import robomuss.rc.block.RCBlocks;
import robomuss.rc.block.model.ModelBin;
import robomuss.rc.block.model.ModelCatwalk;
import robomuss.rc.block.model.ModelSupport;
import robomuss.rc.block.model.ModelWoodenSupport;

public class ItemRenderBin implements IItemRenderer {

	private ModelBin model = new ModelBin();
	
	private ResourceLocation wooden_bin = (new ResourceLocation("rc:textures/models/wooden_bin.png"));
	private ResourceLocation metal_solid_bin = (new ResourceLocation("rc:textures/models/metal_solid_bin.png"));
	private ResourceLocation metal_mesh_bin = (new ResourceLocation("rc:textures/models/metal_mesh_bin.png"));
	
	private ResourceLocation base = (new ResourceLocation("rc:textures/models/base.png"));
	
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
		
		if(item.getItem() == Item.getItemFromBlock(RCBlocks.wooden_bin)) {
			Minecraft.getMinecraft().renderEngine.bindTexture(wooden_bin);
		}
		else if(item.getItem() == Item.getItemFromBlock(RCBlocks.metal_solid_bin)) {
			Minecraft.getMinecraft().renderEngine.bindTexture(metal_solid_bin);
		}
		else if(item.getItem() == Item.getItemFromBlock(RCBlocks.metal_mesh_bin)) {
			Minecraft.getMinecraft().renderEngine.bindTexture(metal_mesh_bin);
		}

		GL11.glPushMatrix();
		GL11.glRotatef(180f, -180f, 0f, 180f);
		
		this.model.base.render(0.0625F);
		
		this.model.side1.render(0.0625F);
		this.model.side2.render(0.0625F);
		this.model.side3.render(0.0625F);
		this.model.side4.render(0.0625F);
		
		Minecraft.getMinecraft().renderEngine.bindTexture(base);
		
		this.model.standBase.render(0.0625F);
		this.model.stand.render(0.0625F);
		
		GL11.glPopMatrix();
		GL11.glPopMatrix();
	}
}