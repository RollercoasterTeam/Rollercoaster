package robomuss.rc.entity;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelMinecart;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;

import org.lwjgl.opengl.GL11;

import robomuss.rc.block.RCBlocks;
import robomuss.rc.block.model.ModelCart;
import robomuss.rc.util.ColourUtil;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderTrain extends Render {
	
	private static final ResourceLocation texture = new ResourceLocation("rc:textures/models/colour_0.png");
	private ModelCart model = new ModelCart();

	public RenderTrain() {
		this.shadowSize = 0.5F;
	}

	public void doRender(EntityTrainDefault entity, double x, double y, double z, float p_76986_8_, float p_76986_9_) {
		GL11.glPushMatrix();
		GL11.glTranslatef((float) x + 0.5F, (float) y + 1.55F, (float) z + 0.5F);
		
		Minecraft.getMinecraft().renderEngine.bindTexture(ColourUtil.textures.get(entity.colour));

		GL11.glPushMatrix();
		GL11.glRotatef(180f, -180f, 0f, 180f);
		
		this.model.render(null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
		
		GL11.glPopMatrix();
		GL11.glPopMatrix();
	}

	private ResourceLocation getEntityTexture(EntityTrain p_110775_1_) {
		return texture;
	}

	private void func_147910_a(EntityTrain entity, float f, Block block, int i) {
		
	}

	protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
		return this.getEntityTexture((EntityTrain) p_110775_1_);
	}

	public void doRender(Entity entity, double x, double y, double z, float p_76986_8_, float p_76986_9_) {
		this.doRender((EntityTrainDefault) entity, x, y, z, p_76986_8_, p_76986_9_);
	}
}