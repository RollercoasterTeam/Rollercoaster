package robomuss.rc.block.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import robomuss.rc.block.model.ModelWoodenSupport;
import robomuss.rc.block.te.TileEntityRideFence;
import robomuss.rc.block.te.TileEntityWoodenSupport;

public class TileEntityRenderWoodenSupport extends TileEntitySpecialRenderer {

	public ModelWoodenSupport model = new ModelWoodenSupport();
	
	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z, float scale) {
		GL11.glPushMatrix();
		GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
		GL11.glRotatef(180, 1, 0, 0);
		 
		ResourceLocation textures = (new ResourceLocation("rc:textures/models/wooden_support.png"));

		Minecraft.getMinecraft().renderEngine.bindTexture(textures);
		
		this.model.pillar1.render(0.0625F);
		this.model.pillar2.render(0.0625F);
		this.model.pillar3.render(0.0625F);
		this.model.pillar4.render(0.0625F);
		GL11.glPopMatrix();
		
		GL11.glPushMatrix();
		GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
		switch(((TileEntityWoodenSupport) te).direction){
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
		GL11.glPushMatrix();
		
		this.model.beam1.render(0.0625F);
		this.model.beam2.render(0.0625F);
		
		GL11.glPopMatrix();
		GL11.glPopMatrix();
	}

}
