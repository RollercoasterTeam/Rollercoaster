package robomuss.rc.block.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.ForgeDirection;
import org.lwjgl.opengl.GL11;
import robomuss.rc.block.model.ModelTrackFabricator;
import robomuss.rc.block.te.TileEntityTrackFabricator;


public class TileEntityRenderTrackFabricator extends TileEntitySpecialRenderer {
	private ModelBase model;
	public static TileEntityRenderTrackFabricator instance = new TileEntityRenderTrackFabricator();

	public TileEntityRenderTrackFabricator() {}

	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z, float scale) {
		ResourceLocation textures = (new ResourceLocation("rc:textures/models/track_fabricator.png"));

		GL11.glPushMatrix();
		GL11.glTranslatef((float) x + 0.5f, (float) y - 0.5f, (float) z + 0.5f);
		Minecraft.getMinecraft().renderEngine.bindTexture(textures);

		if (((TileEntityTrackFabricator) te).direction == ForgeDirection.SOUTH) {
			GL11.glRotatef(180, 0, 1, 0);
		} else if (((TileEntityTrackFabricator) te).direction == ForgeDirection.EAST) {
			GL11.glRotatef(90, 0, -1, 0);
		} else if (((TileEntityTrackFabricator) te).direction == ForgeDirection.WEST) {
			GL11.glRotatef(90, 0, 1, 0);
		}

		model = new ModelTrackFabricator();
		
		
		/*IModelCustom model = AdvancedModelLoader.loadModel(new ResourceLocation("rc", "models/machines/track_fabricator.tcn"));
		model.renderAll();*/
		
		this.model.render((Entity) null, 0, 0, -0.1f, 0, 0, 0.0625f);

		GL11.glPopMatrix();
	}
}
