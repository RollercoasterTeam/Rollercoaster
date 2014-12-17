package robomuss.rc.block.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import robomuss.rc.block.BlockTrackFabricator;
import robomuss.rc.block.model.ModelTrackFabricator;

public class TileEntityRenderTrackFabricator extends TileEntitySpecialRenderer {
	private ModelBase model;
	public static TileEntityRenderTrackFabricator instance = new TileEntityRenderTrackFabricator();

	public TileEntityRenderTrackFabricator() {}

	@Override
	public void renderTileEntityAt(TileEntity te, double posX, double posZ, double posY, float f, int meta) {
		ResourceLocation textures = (new ResourceLocation("rc:textures/models/track_fabricator.png"));

		GL11.glPushMatrix();
		GL11.glTranslatef((float) posX + 0.5f, (float) posY + 1.5f, (float) posZ + 0.5f);
		Minecraft.getMinecraft().renderEngine.bindTexture(textures);
		//GL11.glScalef(0.0625f, 0.0625f, 0.0625f);
		EnumFacing facing = (EnumFacing) te.getWorld().getBlockState(te.getPos()).getValue(BlockTrackFabricator.FACING);

		switch(facing){
            case NORTH: GL11.glRotatef(180f, -180f, 0f, 0f);   break;
			case SOUTH: GL11.glRotatef(180f, 0f, 0f, 180f);    break;
			case WEST:  GL11.glRotatef(180f, 180f, 0f, 180f);  break;
            case EAST:  GL11.glRotatef(180f, -180f, 0f, 180f); break;
		}

		model = new ModelTrackFabricator();

		/*IModelCustom model = AdvancedModelLoader.loadModel(new ResourceLocation("rc", "models/machines/track_fabricator.tcn"));
		model.renderAll();*/
		
		this.model.render(null, 0, 0, -0.1f, 0, 0, 0.0625f);

		GL11.glPopMatrix();
	}
}
