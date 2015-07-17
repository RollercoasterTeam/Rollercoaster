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
import robomuss.rc.block.model.ModelCartBody;
import robomuss.rc.util.ColourUtil;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderTrain extends Render {
	
	private static final ResourceLocation texture = new ResourceLocation("rc:textures/models/cart.png");
	private static final ResourceLocation skin = new ResourceLocation("rc:textures/models/cartSkin.png");
	private ModelCartBody model = new ModelCartBody();

	public RenderTrain() {
		this.shadowSize = 0.5F;
	}

	public void doRender(EntityTrain entity, double x, double y, double z, float p_76986_8_, float p_76986_9_) {
		GL11.glPushMatrix();
        this.bindEntityTexture(entity);
        long i = (long)entity.getEntityId() * 493286711L;
        i = i * i * 4392167121L + i * 98761L;
        float f2 = (((float)(i >> 16 & 7L) + 0.5F) / 8.0F - 0.5F) * 0.004F;
        float f3 = (((float)(i >> 20 & 7L) + 0.5F) / 8.0F - 0.5F) * 0.004F;
        float f4 = (((float)(i >> 24 & 7L) + 0.5F) / 8.0F - 0.5F) * 0.004F;
        GL11.glTranslatef(f2, f3, f4);
        double d3 = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double)p_76986_9_;
        double d4 = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double)p_76986_9_;
        double d5 = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double)p_76986_9_;
        double d6 = 0.30000001192092896D;
        Vec3 vec3 = entity.func_70489_a(d3, d4, d5);
        float f5 = entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * p_76986_9_;

        if (vec3 != null)
        {
            Vec3 vec31 = entity.func_70495_a(d3, d4, d5, d6);
            Vec3 vec32 = entity.func_70495_a(d3, d4, d5, -d6);

            if (vec31 == null)
            {
                vec31 = vec3;
            }

            if (vec32 == null)
            {
                vec32 = vec3;
            }

            x += vec3.xCoord - d3;
            y += (vec31.yCoord + vec32.yCoord) / 2.0D - d4;
            z += vec3.zCoord - d5;
            Vec3 vec33 = vec32.addVector(-vec31.xCoord, -vec31.yCoord, -vec31.zCoord);

            if (vec33.lengthVector() != 0.0D)
            {
                vec33 = vec33.normalize();
                p_76986_8_ = (float)(Math.atan2(vec33.zCoord, vec33.xCoord) * 180.0D / Math.PI);
                f5 = (float)(Math.atan(vec33.yCoord) * 73.0D);
            }
        }

        GL11.glTranslatef((float)x, (float)y + 1.55F, (float)z);
        GL11.glRotatef(360.0F - p_76986_8_, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(-f5, 0.0F, 0.0F, 1.0F);
        float f7 = (float)entity.getRollingAmplitude() - p_76986_9_;
        float f8 = entity.getDamage() - p_76986_9_;

        if (f8 < 0.0F)
        {
            f8 = 0.0F;
        }

        if (f7 > 0.0F)
        {
            GL11.glRotatef(MathHelper.sin(f7) * f7 * f8 / 10.0F * (float)entity.getRollingDirection(), 1.0F, 0.0F, 0.0F);
        }

        int k = entity.getDisplayTileOffset();
        Block block = entity.func_145820_n();
        int j = entity.getDisplayTileData();

        if (block.getRenderType() != -1)
        {
            GL11.glPushMatrix();
            this.bindTexture(TextureMap.locationBlocksTexture);
            float f6 = 0.75F;
            GL11.glScalef(f6, f6, f6);
            GL11.glTranslatef(0.0F, (float)k / 16.0F, 0.0F);
            this.func_147910_a(entity, p_76986_9_, block, j);
            GL11.glPopMatrix();
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            this.bindEntityTexture(entity);
        }

        GL11.glScalef(-1.0F, -1.0F, 1.0F);
        /*IModelCustom model = AdvancedModelLoader.loadModel(new ResourceLocation("rc:models/misc/train.obj"));
        model.renderAll();*/
        
        //GL11.glTranslatef((float)x, (float)y + 1.5F, (float)z);
        Minecraft.getMinecraft().renderEngine.bindTexture(texture);
        
        this.model.base.render(0.0625F);
        
        this.model.side1.render(0.0625F);
        this.model.side2.render(0.0625F);
        this.model.side3.render(0.0625F);
        this.model.side4.render(0.0625F);
        
        Minecraft.getMinecraft().renderEngine.bindTexture(skin);
        
        this.model.head.render(0.0625F);
        
        this.model.arm1.render(0.0625F);
        this.model.arm2.render(0.0625F);
        
        GL11.glPopMatrix();
	}

	private ResourceLocation getEntityTexture(EntityTrain p_110775_1_) {
		return texture;
	}

	private void func_147910_a(EntityTrain entity, float f, Block block, int i) {
		float f1 = entity.getBrightness(f);
        GL11.glPushMatrix();
        //this.renderBlocks.renderBlockAsItem(block, i, f1);
        GL11.glPopMatrix();
	}

	protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
		return this.getEntityTexture((EntityTrain) p_110775_1_);
	}

	public void doRender(Entity entity, double x, double y, double z, float p_76986_8_, float p_76986_9_) {
		this.doRender((EntityTrainDefault) entity, x, y, z, p_76986_8_, p_76986_9_);
	}
}