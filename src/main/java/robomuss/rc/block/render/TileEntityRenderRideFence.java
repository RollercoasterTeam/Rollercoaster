package robomuss.rc.block.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import robomuss.rc.block.RCBlocks;
import robomuss.rc.block.model.ModelRideFence;
import robomuss.rc.block.model.ModelRideFenceCorner;
import robomuss.rc.block.model.ModelRideFenceGate;
import robomuss.rc.block.model.ModelRideFenceGateOpen;
import robomuss.rc.block.model.ModelRideFencePanel;
import robomuss.rc.block.model.ModelRideFenceSquare;
import robomuss.rc.block.model.ModelRideFenceTriangle;
import robomuss.rc.block.te.TileEntityRideFence;


public class TileEntityRenderRideFence extends TileEntitySpecialRenderer {

	private ModelRideFence normal = new ModelRideFence();
	private ModelRideFenceCorner corner = new ModelRideFenceCorner();
	private ModelRideFenceTriangle triangle = new ModelRideFenceTriangle();
	private ModelRideFenceSquare square = new ModelRideFenceSquare();
	private ModelRideFenceGate gate = new ModelRideFenceGate();
	private ModelRideFenceGateOpen gate_open = new ModelRideFenceGateOpen();
	private ModelRideFencePanel panel = new ModelRideFencePanel();
	
	private ResourceLocation gateTextures = (new ResourceLocation("rc:textures/models/colour_7.png"));
	private ResourceLocation panelTextures = (new ResourceLocation("rc:textures/models/ride_fence_panel.png"));
	
	public static TileEntityRenderRideFence instance = new TileEntityRenderRideFence();
	private ModelBase model;

	public TileEntityRenderRideFence() {
	
	}

	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z, float scale) {
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
		
		if(te.getBlockType() == RCBlocks.ride_fence_panel) {
			this.panel.Shape1.render(0.0625F);
			this.panel.Shape2.render(0.0625F);
			this.panel.Shape3.render(0.0625F);

			Minecraft.getMinecraft().renderEngine.bindTexture(panelTextures);
			
			this.panel.Shape4.render(0.0625F);
			this.panel.Shape5.render(0.0625F);
			this.panel.Shape6.render(0.0625F);
			this.panel.Shape7.render(0.0625F);
		}
		else if(te.getBlockType() == RCBlocks.ride_fence_gate) {
			if(((TileEntityRideFence) te).open) {
				this.gate_open.pole1.render(0.0625F);
				this.gate_open.pole2.render(0.0625F);
				
				Minecraft.getMinecraft().renderEngine.bindTexture(gateTextures);
				
				this.gate_open.bar1.render(0.0625F);
				this.gate_open.bar2.render(0.0625F);
				this.gate_open.bar3.render(0.0625F);
				this.gate_open.bar4.render(0.0625F);
				this.gate_open.bar5.render(0.0625F);
				this.gate_open.bar6.render(0.0625F);
				this.gate_open.bar7.render(0.0625F);
				this.gate_open.bar8.render(0.0625F);
				this.gate_open.bar9.render(0.0625F);
				this.gate_open.bar10.render(0.0625F);
			}
			else {
				this.gate.pole1.render(0.0625F);
				this.gate.pole2.render(0.0625F);
				
				Minecraft.getMinecraft().renderEngine.bindTexture(gateTextures);
				
				this.gate.bar1.render(0.0625F);
				this.gate.bar2.render(0.0625F);
				this.gate.bar3.render(0.0625F);
				this.gate.bar4.render(0.0625F);
				this.gate.bar5.render(0.0625F);
				this.gate.bar6.render(0.0625F);
				this.gate.bar7.render(0.0625F);
				this.gate.bar8.render(0.0625F);
				this.gate.bar9.render(0.0625F);
				this.gate.bar10.render(0.0625F);
			}
		}
		else {
			this.model.render((Entity) null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
		}

		GL11.glPopMatrix();
		GL11.glPopMatrix();
	}
}
