package robomuss.rc.block.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.tileentity.TileEntityRendererChestHelper;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import robomuss.rc.block.RCBlocks;
import robomuss.rc.block.model.ModelCatwalk;
import robomuss.rc.block.model.ModelTrackDesigner;
import robomuss.rc.block.model.ModelTrackFabricator;
import robomuss.rc.block.model.ModelBin;
import robomuss.rc.block.te.TileEntityCatwalk;
import robomuss.rc.block.te.TileEntityRideFence;
import robomuss.rc.block.te.TileEntityTrackDesigner;
import robomuss.rc.block.te.TileEntityTrackFabricator;


public class TileEntityRenderBin extends TileEntitySpecialRenderer {

	private ModelBin model = new ModelBin();
	
	private ResourceLocation wooden_bin = (new ResourceLocation("rc:textures/models/wooden_bin.png"));
	private ResourceLocation metal_solid_bin = (new ResourceLocation("rc:textures/models/metal_solid_bin.png"));
	private ResourceLocation metal_mesh_bin = (new ResourceLocation("rc:textures/models/metal_mesh_bin.png"));
	
	private ResourceLocation base = (new ResourceLocation("rc:textures/models/base.png"));

	public TileEntityRenderBin() {
	
	}

	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z, float scale) {
		GL11.glPushMatrix();
		GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
		
		if(te.getBlockType() == RCBlocks.wooden_bin) {
			Minecraft.getMinecraft().renderEngine.bindTexture(wooden_bin);
		}
		else if(te.getBlockType() == RCBlocks.metal_solid_bin) {
			Minecraft.getMinecraft().renderEngine.bindTexture(metal_solid_bin);
		}
		else if(te.getBlockType() == RCBlocks.metal_mesh_bin) {
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
