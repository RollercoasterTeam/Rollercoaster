package robomuss.rc.block.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;
<<<<<<< HEAD
import robomuss.rc.block.BlockRideFence;
=======

>>>>>>> origin/One8PortTake2
import robomuss.rc.block.RCBlocks;
import robomuss.rc.block.model.ModelRideFence;
import robomuss.rc.block.model.ModelRideFenceCorner;
import robomuss.rc.block.model.ModelRideFenceGate;
import robomuss.rc.block.model.ModelRideFenceGateOpen;
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
	
	public static TileEntityRenderRideFence instance = new TileEntityRenderRideFence();
	private ModelBase model;

	public TileEntityRenderRideFence() {}

	@Override
	public void renderTileEntityAt(TileEntity te, double posX , double posZ, double posY, float scale, int i) {
		GL11.glPushMatrix();
		int colour = ((TileEntityRideFence) te).colour;
		GL11.glTranslatef((float) posX + 0.5f, (float) posY + 1.5f, (float) posZ + 0.5f);

		ResourceLocation textures = (new ResourceLocation("rc:textures/models/colour_" + colour + ".png"));

		Minecraft.getMinecraft().renderEngine.bindTexture(textures);

		EnumFacing facing = (EnumFacing) te.getWorld().getBlockState(te.getPos()).getValue(BlockRideFence.FACING);

		GL11.glPushMatrix();
		switch (facing) {
			case NORTH: GL11.glRotatef(180, -180, 0, 180); break;
			case SOUTH: GL11.glRotatef(180, 180, 0, 180);  break;
			case WEST:  GL11.glRotatef(180, 0, 0, 180);    break;
			case EAST:  GL11.glRotatef(180, -180, 0, 0);    break;
		}

		if(te.getBlockType() == RCBlocks.ride_fence) {
			model = normal;
		} else if(te.getBlockType() == RCBlocks.ride_fence_corner) {
			model = corner;
		} else if(te.getBlockType() == RCBlocks.ride_fence_triangle) {
			model = triangle;
		} else if(te.getBlockType() == RCBlocks.ride_fence_square) {
			model = square;
		} else if(te.getBlockType() == RCBlocks.ride_fence_gate) {
			model = ((TileEntityRideFence) te).open ? gate_open : gate;
		}
		
		this.model.render(null, 0, 0, -0.1f, 0, 0, 0.0625f);

		GL11.glPopMatrix();
		GL11.glPopMatrix();
	}
}
