package robomuss.rc.block.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import robomuss.rc.block.model.ModelTrackFabricator;
import robomuss.rc.block.te.TileEntityRideFence;
import robomuss.rc.block.te.TileEntityTrackFabricator;


public class TileEntityRenderTrackFabricator extends TileEntitySpecialRenderer {

	private ModelBase model;

	public TileEntityRenderTrackFabricator() {
	
	}

	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z, float scale) {
		GL11.glPushMatrix();
		GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);

		ResourceLocation textures = (new ResourceLocation("rc:textures/models/track_fabricator.png"));

		Minecraft.getMinecraft().renderEngine.bindTexture(textures);

		GL11.glPushMatrix();
		GL11.glRotatef(180f, -180f, 0f, 180f);
		model = new ModelTrackFabricator();
		
		this.model.render((Entity) null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);

		GL11.glPopMatrix();
		GL11.glPopMatrix();
	}
}
