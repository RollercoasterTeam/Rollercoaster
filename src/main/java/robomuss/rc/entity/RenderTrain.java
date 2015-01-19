package robomuss.rc.entity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
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

@SideOnly(Side.CLIENT)
public class RenderTrain extends Render {
    private static final ResourceLocation minecartTextures = new ResourceLocation("rc:textures/entities/train_classic.png");
    /** instance of ModelMinecart for rendering */
    protected ModelBase modelMinecart = new ModelMinecart();
    protected final RenderBlocks renderBlocks;

    public RenderTrain() {
        this.shadowSize = 0.5F;
        this.renderBlocks = new RenderBlocks();
    }

    /**
     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
     * handing it off to a worker function which does the actual work. In all probability, the class Render is generic
     * (Render<T extends Entity) and this method has signature public void func_76986_a(T entity, double d, double d1,
     * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
     */
    public void doRender(EntityTrain entityTrain, double d, double d1, double d2, float f, float f1) {
        GL11.glPushMatrix();
        this.bindEntityTexture(entityTrain);
        long i = (long) entityTrain.getEntityId() * 493286711L;
        i = i * i * 4392167121L + i * 98761L;
        float f2 = (((float) (i >> 16 & 7L) + 0.5F) / 8.0F - 0.5F) * 0.004F;
        float f3 = (((float) (i >> 20 & 7L) + 0.5F) / 8.0F - 0.5F) * 0.004F;
        float f4 = (((float) (i >> 24 & 7L) + 0.5F) / 8.0F - 0.5F) * 0.004F;
        GL11.glTranslatef(f2, f3, f4);
        double d3 = entityTrain.lastTickPosX + (entityTrain.posX - entityTrain.lastTickPosX) * (double)f1;
        double d4 = entityTrain.lastTickPosY + (entityTrain.posY - entityTrain.lastTickPosY) * (double)f1;
        double d5 = entityTrain.lastTickPosZ + (entityTrain.posZ - entityTrain.lastTickPosZ) * (double)f1;
        double d6 = 0.30000001192092896D;
        Vec3 vec3 = entityTrain.func_70489_a(d3, d4, d5);
        float f5 = entityTrain.prevRotationPitch + (entityTrain.rotationPitch - entityTrain.prevRotationPitch) * f1;

        if (vec3 != null) {
            Vec3 vec31 = entityTrain.func_70495_a(d3, d4, d5, d6);
            Vec3 vec32 = entityTrain.func_70495_a(d3, d4, d5, -d6);

            if (vec31 == null) {
                vec31 = vec3;
            }

            if (vec32 == null) {
                vec32 = vec3;
            }

            d += vec3.xCoord - d3;
            d1 += (vec31.yCoord + vec32.yCoord) / 2.0D - d4;
            d2 += vec3.zCoord - d5;
            Vec3 vec33 = vec32.addVector(-vec31.xCoord, -vec31.yCoord, -vec31.zCoord);

            if (vec33.lengthVector() != 0.0D) {
                vec33 = vec33.normalize();
                f = (float)(Math.atan2(vec33.zCoord, vec33.xCoord) * 180.0D / Math.PI);
                f5 = (float)(Math.atan(vec33.yCoord) * 73.0D);
            }
        }

        GL11.glTranslatef((float)d, (float)d1, (float)d2);
        GL11.glRotatef(180.0F - f, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(-f5, 0.0F, 0.0F, 1.0F);
        float f7 = (float)entityTrain.getRollingAmplitude() - f1;
        float f8 = entityTrain.getDamage() - f1;

        if (f8 < 0.0F) {
            f8 = 0.0F;
        }

        if (f7 > 0.0F) {
            GL11.glRotatef(MathHelper.sin(f7) * f7 * f8 / 10.0F * (float)entityTrain.getRollingDirection(), 1.0F, 0.0F, 0.0F);
        }

        int k = entityTrain.getDisplayTileOffset();
        Block block = entityTrain.getDisplayTile();
        int j = entityTrain.getDisplayTileData();

        if (block.getRenderType() != -1) {
            GL11.glPushMatrix();
            this.bindTexture(TextureMap.locationBlocksTexture);
            float f6 = 0.75F;
            GL11.glScalef(f6, f6, f6);
            GL11.glTranslatef(0.0F, (float)k / 16.0F, 0.0F);
            this.func_147910_a(entityTrain, f1, block, j);
            GL11.glPopMatrix();
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            this.bindEntityTexture(entityTrain);
        }

        GL11.glScalef(-1.0F, -1.0F, 1.0F);
        /*IModelCustom model = AdvancedModelLoader.loadModel(new ResourceLocation("rc:models/misc/train.obj"));
        model.renderAll();*/
        
        this.modelMinecart.render(entityTrain, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
        GL11.glPopMatrix();
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(EntityTrain entityTrain) {
        return minecartTextures;
    }

    protected void func_147910_a(EntityTrain entityTrain, float brightness, Block block, int meta) {
        float f1 = entityTrain.getBrightness(brightness);
        GL11.glPushMatrix();
        this.renderBlocks.renderBlockAsItem(block, meta, f1);
        GL11.glPopMatrix();
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(Entity entityTrain) {
        return this.getEntityTexture((EntityTrain) entityTrain);
    }

    /**
     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
     * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity) and this method has signature public void func_76986_a(T entity, double d, double d1,
     * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
     */
    public void doRender(Entity entity, double d, double d1, double d2, float f, float f1) {
        this.doRender((EntityTrain) entity, d, d1, d2, f, f1);
    }
}