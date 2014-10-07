package robomuss.rc.block.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import robomuss.rc.block.RCBlocks;
import robomuss.rc.block.model.*;
import robomuss.rc.block.te.TileEntityRideFence;


public class TileEntityRenderRideFence extends TileEntitySpecialRenderer {

	private ModelRideFence normal = new ModelRideFence();
	private ModelRideFenceCorner corner = new ModelRideFenceCorner();
	private ModelRideFenceTriangle triangle = new ModelRideFenceTriangle();
	private ModelRideFenceSquare square = new ModelRideFenceSquare();
	private ModelRideFenceGate gate = new ModelRideFenceGate();
	private ModelRideFenceGateOpen gate_open = new ModelRideFenceGateOpen();
	
	public static TileEntityRenderRideFence instance = new TileEntityRenderRideFence();
	private ModelBase model;

	public TileEntityRenderRideFence() {
	
	}

	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z, float scale, int i) {
		GL11.glPushMatrix();
		int colour = ((TileEntityRideFence) te).colour;
		GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);

		ResourceLocation textures = (new ResourceLocation("rc:textures/models/colour_" + colour + ".png"));

		Minecraft.getMinecraft().renderEngine.bindTexture(textures);

		GL11.glPushMatrix();
		switch(((TileEntityRideFence) te).direction){
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
		if(te.getBlockType() == RCBlocks.ride_fence) {
			model = normal;
		}
		else if(te.getBlockType() == RCBlocks.ride_fence_corner) {
			model = corner;
		}
		else if(te.getBlockType() == RCBlocks.ride_fence_triangle) {
			model = triangle;
		}
		else if(te.getBlockType() == RCBlocks.ride_fence_square) {
			model = square;
		}
		else if(te.getBlockType() == RCBlocks.ride_fence_gate) {
			if(((TileEntityRideFence) te).open) {
				model = gate_open;
			}
			else {
				model = gate;
			}
		}
		
		this.model.render((Entity) null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);

		GL11.glPopMatrix();
		GL11.glPopMatrix();
	}
}
