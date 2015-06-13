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
import robomuss.rc.block.model.ModelBenchHalf;
import robomuss.rc.block.model.ModelCatwalk;
import robomuss.rc.block.model.ModelTrackDesigner;
import robomuss.rc.block.model.ModelTrackFabricator;
import robomuss.rc.block.model.ModelBin;
import robomuss.rc.block.te.TileEntityBench;
import robomuss.rc.block.te.TileEntityCatwalk;
import robomuss.rc.block.te.TileEntityRideFence;
import robomuss.rc.block.te.TileEntityTrackDesigner;
import robomuss.rc.block.te.TileEntityTrackFabricator;


public class TileEntityRenderBench extends TileEntitySpecialRenderer {

	private ModelBenchHalf model = new ModelBenchHalf();
	
	private ResourceLocation bench_half = (new ResourceLocation("rc:textures/models/bench_half.png"));

	public TileEntityRenderBench() {
	
	}

	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z, float scale) {
		GL11.glPushMatrix();
		GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);

		Minecraft.getMinecraft().renderEngine.bindTexture(bench_half);
		
		GL11.glPushMatrix();
		switch(((TileEntityBench) te).direction){
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
		
		this.model.render(null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
		
		GL11.glPopMatrix();
		GL11.glPopMatrix();
	}
}
