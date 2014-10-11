package robomuss.rc.block.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.tileentity.TileEntityRendererChestHelper;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

import org.lwjgl.opengl.GL11;

import robomuss.rc.block.model.ModelTrackFabricator;
import robomuss.rc.block.te.TileEntityTrackFabricator;


public class TileEntityRenderTrackFabricator extends TileEntitySpecialRenderer {

	//private ModelBase model;
	public static TileEntityRenderTrackFabricator instance = new TileEntityRenderTrackFabricator();

	public TileEntityRenderTrackFabricator() {
	
	}

	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z, float scale) {
		ResourceLocation textures = (new ResourceLocation("rc:textures/models/track_fabricator.png"));

		GL11.glPushMatrix();
		GL11.glTranslatef((float) x + 0.5F, (float) y, (float) z + 0.5F);
		Minecraft.getMinecraft().renderEngine.bindTexture(textures);
		GL11.glScalef(0.0625f, 0.0625f, 0.0625f);
		switch(((TileEntityTrackFabricator) te).direction){
        case 1:
        		GL11.glRotatef(180f, -180f, 0f, 0f);
                break;
        case 2:
                GL11.glRotatef(180f, 180f, 0f, 180f);
                break;
        case 3:
                GL11.glRotatef(180f, 0f, 0f, 180f);
                break;
        default:
        		GL11.glRotatef(180f, -180f, 0f, 180f);
        		break;
		}
		//model = new ModelTrackFabricator();
		
		
		IModelCustom model = AdvancedModelLoader.loadModel(new ResourceLocation("rc", "models/machines/track_fabricator.tcn"));
		model.renderAll();
		
		//this.model.render((Entity) null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);

		GL11.glPopMatrix();
	}
}
