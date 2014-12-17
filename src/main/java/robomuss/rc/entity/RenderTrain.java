package robomuss.rc.entity;

<<<<<<< HEAD
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
=======
>>>>>>> origin/One8PortTake2
import net.minecraft.block.Block;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelMinecart;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class RenderTrain extends Render {
    private static final ResourceLocation minecartTextures = new ResourceLocation("rc:textures/entities/train_classic.png");
    /** instance of ModelMinecart for rendering */ //TODO: will probably make custom model in the future
    protected ModelBase modelMinecart = new ModelMinecart();
    protected final RenderManager renderManager;

    public RenderTrain(RenderManager renderManager) {
        super(renderManager);
        this.shadowSize = 0.5F;
        this.renderManager = renderManager;
    }

    /**
     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
     * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity></T>) and this method has signature public void func_76986_a(T entity, double d, double d1,
     * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
     */
    public void doRender(EntityTrain train, double x, double y, double z, float pitch, float yaw) {
        GL11.glPushMatrix();
        this.bindEntityTexture(train);
        long i = (long)train.getEntityId() * 493286711L;
        i = i * i * 4392167121L + i * 98761L;
        float f2 = (((float)(i >> 16 & 7L) + 0.5F) / 8.0F - 0.5F) * 0.004F;
        float f3 = (((float)(i >> 20 & 7L) + 0.5F) / 8.0F - 0.5F) * 0.004F;
        float f4 = (((float)(i >> 24 & 7L) + 0.5F) / 8.0F - 0.5F) * 0.004F;
        GL11.glTranslatef(f2, f3, f4);
        double d3 = train.lastTickPosX + (train.posX - train.lastTickPosX) * (double)yaw;
        double d4 = train.lastTickPosY + (train.posY - train.lastTickPosY) * (double)yaw;
        double d5 = train.lastTickPosZ + (train.posZ - train.lastTickPosZ) * (double)yaw;
        double d6 = 0.30000001192092896D;
        Vec3 vec3 = train.func_70489_a(d3, d4, d5);
        float f5 = train.prevRotationPitch + (train.rotationPitch - train.prevRotationPitch) * yaw;

        if (vec3 != null) {
            Vec3 vec31 = train.func_70495_a(d3, d4, d5, d6);
            Vec3 vec32 = train.func_70495_a(d3, d4, d5, -d6);

            if (vec31 == null) {
                vec31 = vec3;
            }

            if (vec32 == null) {
                vec32 = vec3;
            }

            x += vec3.xCoord - d3;
            y += (vec31.yCoord + vec32.yCoord) / 2.0D - d4;
            z += vec3.zCoord - d5;
            Vec3 vec33 = vec32.addVector(-vec31.xCoord, -vec31.yCoord, -vec31.zCoord);

            if (vec33.lengthVector() != 0.0D) {
                vec33 = vec33.normalize();
                pitch = (float)(Math.atan2(vec33.zCoord, vec33.xCoord) * 180.0D / Math.PI);
                f5 = (float)(Math.atan(vec33.yCoord) * 73.0D);
            }
        }

        GL11.glTranslatef((float)x, (float)y, (float)z);
        GL11.glRotatef(180.0F - pitch, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(-f5, 0.0F, 0.0F, 1.0F);
        float f7 = (float)train.getRollingAmplitude() - yaw;
        float f8 = train.getDamage() - yaw;

        if (f8 < 0.0F) {
            f8 = 0.0F;
        }

        if (f7 > 0.0F) {
            GL11.glRotatef(MathHelper.sin(f7) * f7 * f8 / 10.0F * (float)train.getRollingDirection(), 1.0F, 0.0F, 0.0F);
        }

        int k = train.getDisplayTileOffset();
        Block block = train.func_145820_n();
        int j = train.getDisplayTileData();

        if (block.getRenderType() != -1) {
            GL11.glPushMatrix();
            this.bindTexture(TextureMap.locationBlocksTexture);
            float f6 = 0.75F;
            GL11.glScalef(f6, f6, f6);
            GL11.glTranslatef(0.0F, (float)k / 16.0F, 0.0F);
//            this.func_147910_a(train, yaw, block, j);
            GL11.glPopMatrix();
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            this.bindEntityTexture(train);
        }

        GL11.glScalef(-1.0F, -1.0F, 1.0F);
        /*IModelCustom model = AdvancedModelLoader.loadModel(new ResourceLocation("rc:models/misc/train.obj"));
        model.renderAll();*/
        
        this.modelMinecart.render(train, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
        GL11.glPopMatrix();
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(EntityTrain p_110775_1_)
    {
        return minecartTextures;
    }

    /* No idea what this does... */
//    protected void func_147910_a(EntityTrain train, float f, Block block, int i)
//    {
//        float f1 = train.getBrightness(f);
//        GL11.glPushMatrix();
//        this.renderManager.renderBlockAsItem(block, i, f1);
//        GL11.glPopMatrix();
//    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(Entity entity) {
        return this.getEntityTexture((EntityTrain) entity);
    }

    /**
     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
     * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity) and this method has signature public void func_76986_a(T entity, double d, double d1,
     * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
     */
    public void doRender(Entity entity, double x, double y, double z, float pitch, float yaw) {
        this.doRender((EntityTrain) entity, x, y, z, pitch, yaw);
    }
}