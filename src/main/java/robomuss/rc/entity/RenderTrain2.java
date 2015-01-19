package robomuss.rc.entity;

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
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import org.lwjgl.opengl.GL11;

public class RenderTrain2 extends Render {
	private static final ResourceLocation trainCarTextures = new ResourceLocation("rc:textures/entities/train_classic.png");
	protected ModelBase modelTrain2 = new ModelMinecart();
	protected final RenderBlocks renderBlocks;

	public RenderTrain2() {
		this.shadowSize = 0.5f;
		this.renderBlocks = new RenderBlocks();
	}

	@Override
	public void doRender(Entity entity, double x, double y, double z, float f, float f1) {
		this.doRender((EntityTrain2) entity, x, y, z, f, f1);
	}

	public void doRender(EntityTrain2 entityTrain2, double posX, double posY, double posZ, float f, float f1) {
		GL11.glPushMatrix();
		this.bindEntityTexture(entityTrain2);
		long longID = (long) entityTrain2.getEntityId() * 493286711l;
		longID = longID * longID * 4392167121l + longID * 98761l;
		float translateX = (((float) (longID >> 16 & 7l) + 0.5f) / 8f - 0.5f) * 0.004f;
		float translateY = (((float) (longID >> 20 & 7l) + 0.5f) / 8f - 0.5f) * 0.004f;
		float translateZ = (((float) (longID >> 24 & 7l) + 0.5f) / 8f - 0.5f) * 0.004f;
		GL11.glTranslatef(translateX, translateY, translateZ);
		double nextPosX = entityTrain2.lastTickPosX + (entityTrain2.posX - entityTrain2.lastTickPosX) * (double) f1;
		double nextPosY = entityTrain2.lastTickPosY + (entityTrain2.posY - entityTrain2.lastTickPosY) * (double) f1;
		double nextPosZ = entityTrain2.lastTickPosZ + (entityTrain2.posZ - entityTrain2.lastTickPosZ) * (double) f1;
		double modifier = 0.30000001192092896d;
		Vec3 nextPosVec = entityTrain2.getVector3(nextPosX, nextPosY, nextPosZ);
		float nextPitch = entityTrain2.prevRotationPitch + (entityTrain2.rotationPitch - entityTrain2.prevRotationPitch) * f1;

		if (nextPosVec != null) {
			Vec3 renderPosVec    = entityTrain2.getRenderPos(nextPosX, nextPosY, nextPosZ, modifier);
			Vec3 renderPosVecInv = entityTrain2.getRenderPos(nextPosX, nextPosY, nextPosZ, -modifier);

			if (renderPosVec == null) {
				renderPosVec = nextPosVec;
			}

			if (renderPosVecInv == null) {
				renderPosVecInv = nextPosVec;
			}

			posX += nextPosVec.xCoord - nextPosX;
			posY += (renderPosVec.yCoord + renderPosVecInv.yCoord) / 2d - nextPosY;
			posZ += nextPosVec.zCoord - nextPosZ;
			Vec3 renderPosVecInvAdded = renderPosVecInv.addVector(-renderPosVec.xCoord, -renderPosVec.yCoord, -renderPosVec.zCoord);

			if (renderPosVecInvAdded.lengthVector() != 0d) {
				renderPosVecInvAdded = renderPosVecInvAdded.normalize();
				f = (float) (Math.atan2(renderPosVecInvAdded.zCoord, renderPosVecInvAdded.xCoord) * 180d / Math.PI);
				nextPitch = (float) (Math.atan(renderPosVecInvAdded.yCoord) * 73d);
			}
		}

		GL11.glTranslatef((float) posX, (float) posY, (float) posZ);
		GL11.glRotatef(180f - f, 0, 1, 0);
		GL11.glRotatef(-nextPitch, 0, 0, 1);
		float nextRollingAmplitude = entityTrain2.getRollingAmplitude() - f1;
		float nextDamage = entityTrain2.getDamage() - f1;

		if (nextDamage < 0f) {
			nextDamage = 0f;
		}

		if (nextRollingAmplitude > 0f) {
			GL11.glRotatef(MathHelper.sin(nextRollingAmplitude) * nextRollingAmplitude * nextDamage / 10f * (float) entityTrain2.getRollingDirection(), 1, 0, 0);
		}

		int displayTileOffset = entityTrain2.getDisplayTileOffset();
		Block displayTile = entityTrain2.getDisplayTile();
		int displayTileData = entityTrain2.getDisplayTileData();

		if (displayTile.getRenderType() != -1) {
			GL11.glPushMatrix();
			this.bindTexture(TextureMap.locationBlocksTexture);
			float scale = 0.75f;
			GL11.glScalef(scale, scale, scale);
			GL11.glTranslatef(0, (float) displayTileOffset / 16f, 0);
			this.calculateBrightness(entityTrain2, f1, displayTile, displayTileData);
			GL11.glPopMatrix();
			GL11.glColor4f(1, 1, 1, 1);
			this.bindEntityTexture(entityTrain2);
		}

		GL11.glScalef(-1, -1, 1);
		/*IModelCustom model = AdvancedModelLoader.loadModel(new ResourceLocation("rc:models/misc/train.obj"));
		model.renderAll();*/

		this.modelTrain2.render(entityTrain2, 0, 0, -0.1f, 0, 0, 0.0625f);
		GL11.glPopMatrix();
	}

	public void calculateBrightness(EntityTrain2 entityTrain2, float brightness, Block block, int meta) {
		float trainBrightness = entityTrain2.getBrightness(brightness);
		GL11.glPushMatrix();
		this.renderBlocks.renderBlockAsItem(block, meta, trainBrightness);
		GL11.glPopMatrix();
	}

	@Override
	public ResourceLocation getEntityTexture(Entity entity) {
		return this.getEntityTexture((EntityTrain2) entity);
	}

	public ResourceLocation getEntityTexture(EntityTrain2 entityTrain2) {
		return trainCarTextures;
	}
}
