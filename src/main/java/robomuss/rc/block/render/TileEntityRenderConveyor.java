package robomuss.rc.block.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;
<<<<<<< HEAD
import robomuss.rc.block.BlockConveyor;
=======

>>>>>>> origin/One8PortTake2
import robomuss.rc.block.model.ModelConveyor;
import robomuss.rc.block.te.TileEntityConveyor;

public class TileEntityRenderConveyor extends TileEntitySpecialRenderer {
	private ModelBase model;
	
	@Override
	public void renderTileEntityAt(TileEntity tileEntity, double posX, double posZ, double posY, float f, int i) {
		ResourceLocation textures = (new ResourceLocation("rc:textures/models/conveyor.png"));

		GL11.glPushMatrix();
		GL11.glTranslatef((float) posX + 0.5f, (float) posY + 1.5f, (float) posZ + 0.5f);
//		GL11.glRotatef(180f, 180f, 0f, 0f);
		Minecraft.getMinecraft().renderEngine.bindTexture(textures);

		if (tileEntity != null && tileEntity instanceof TileEntityConveyor) {
			TileEntityConveyor teConveyor = (TileEntityConveyor) tileEntity;

			EnumFacing facing = (EnumFacing) teConveyor.getWorld().getBlockState(teConveyor.getPos()).getValue(BlockConveyor.FACING);

			switch (facing) {
				case NORTH: GL11.glRotatef(180f, 0f, 0f, 180f);   break;
				case SOUTH: GL11.glRotatef(180f, 180f, 0f, 0f);   break;
				case WEST:  GL11.glRotatef(180f, 180f, 0f, 180f); break;
				case EAST:  GL11.glRotatef(180f, -180f, 0f, 180f);
 			}
		}

		model = new ModelConveyor();
		this.model.render(null, 0, 0, -0.1f, 0, 0, 0.0625f);

		GL11.glPopMatrix();
	}
}
