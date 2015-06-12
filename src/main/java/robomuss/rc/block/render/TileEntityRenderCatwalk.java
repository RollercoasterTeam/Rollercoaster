package robomuss.rc.block.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.tileentity.TileEntityRendererChestHelper;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import robomuss.rc.block.model.ModelCatwalk;
import robomuss.rc.block.model.ModelTrackDesigner;
import robomuss.rc.block.model.ModelTrackFabricator;
import robomuss.rc.block.te.TileEntityCatwalk;
import robomuss.rc.block.te.TileEntityRideFence;
import robomuss.rc.block.te.TileEntityTrackDesigner;
import robomuss.rc.block.te.TileEntityTrackFabricator;


public class TileEntityRenderCatwalk extends TileEntitySpecialRenderer {

	private ModelCatwalk model = new ModelCatwalk();
	
	private ResourceLocation textures = (new ResourceLocation("rc:textures/models/catwalk.png"));
	

	public TileEntityRenderCatwalk() {
	
	}

	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z, float scale) {
		GL11.glPushMatrix();
		GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);

		Minecraft.getMinecraft().renderEngine.bindTexture(textures);

		GL11.glPushMatrix();
		switch(((TileEntityCatwalk) te).direction){
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
		
		this.model.base.render(0.0625F);
		
		int colour = ((TileEntityCatwalk) te).colour;
		ResourceLocation textures2 = (new ResourceLocation("rc:textures/models/colour_" + colour + ".png"));
		Minecraft.getMinecraft().renderEngine.bindTexture(textures2);
		
		this.model.pole1.render(0.0625F);
		this.model.pole2.render(0.0625F);
		
		this.model.beam1.render(0.0625F);
		this.model.beam2.render(0.0625F);
		this.model.beam3.render(0.0625F);

		GL11.glPopMatrix();
		GL11.glPopMatrix();
	}
}
