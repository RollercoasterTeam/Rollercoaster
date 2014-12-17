package robomuss.rc.block.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import robomuss.rc.block.BlockWoodenSupport;
import robomuss.rc.block.model.ModelWoodenSupport;
import robomuss.rc.block.te.TileEntityWoodenSupport;

public class TileEntityRenderWoodenSupport extends TileEntitySpecialRenderer {
	public ModelWoodenSupport model = new ModelWoodenSupport();
	
	@Override
	public void renderTileEntityAt(TileEntity te, double posX, double posZ, double posY, float f, int meta) {
		GL11.glPushMatrix();
		GL11.glTranslatef((float) posX + 0.5f, (float) posY + 1.5f, (float) posZ + 0.5f);
		GL11.glRotatef(180, 1, 0, 0);
		 
		ResourceLocation textures = (new ResourceLocation("rc:textures/models/wooden_support.png"));

		Minecraft.getMinecraft().renderEngine.bindTexture(textures);
		
		this.model.pillar1.render(0.0625f);
		this.model.pillar2.render(0.0625f);
		this.model.pillar3.render(0.0625f);
		this.model.pillar4.render(0.0625f);
		GL11.glPopMatrix();
		
		GL11.glPushMatrix();
		GL11.glTranslatef((float) posX + 0.5f, (float) posY + 1.5f, (float) posZ + 0.5f);

		EnumFacing facing = (EnumFacing) te.getWorld().getBlockState(te.getPos()).getValue(BlockWoodenSupport.FACING);

		switch(facing){
			case NORTH: GL11.glRotatef(180, -180, 0, 180); break;
			case SOUTH: GL11.glRotatef(180, 180, 0, 180);  break;
			case WEST:  GL11.glRotatef(180, 0, 0, 180);    break;
			case EAST:  GL11.glRotatef(180, -180, 0, 0);   break;
		}

		GL11.glPushMatrix();
		
		this.model.beam1.render(0.0625f);
		this.model.beam2.render(0.0625f);
		
		GL11.glPopMatrix();
		GL11.glPopMatrix();
	}

}
