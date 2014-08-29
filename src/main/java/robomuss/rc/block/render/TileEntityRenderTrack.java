package robomuss.rc.block.render;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import robomuss.rc.block.model.ModelCorkscrewCoaster;
import robomuss.rc.block.model.ModelCorkscrewCoasterCorner;
import robomuss.rc.block.model.ModelCorkscrewCoasterExtended;
import robomuss.rc.block.model.ModelCorkscrewCoasterExtended2;
import robomuss.rc.block.te.TileEntityTrack;
import robomuss.rc.tracks.TrackHandler;
import robomuss.rc.tracks.TrackType;


public class TileEntityRenderTrack extends TileEntitySpecialRenderer {

	public static ModelBase[] models = {new ModelCorkscrewCoaster(), new ModelCorkscrewCoasterExtended(), new ModelCorkscrewCoasterExtended2(), new ModelCorkscrewCoasterCorner()};
	private ModelBase model;

	public TileEntityRenderTrack() {
		
	}
	
	int animation;

	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z, float scale) {
		this.model = models[((TileEntityTrack) te).model];
		
		int colour = ((TileEntityTrack) te).colour;

		ResourceLocation textures = (new ResourceLocation("rc:textures/models/colour_" + colour + ".png"));

		Minecraft.getMinecraft().renderEngine.bindTexture(textures);

		
		
		
		Block block = te.getWorldObj().getBlock(te.xCoord, te.yCoord, te.zCoord);
		TrackType track_type = TrackHandler.findTrackTypeFull(block.getUnlocalizedName());
		if(track_type != null) {
			if(track_type.special_render_stages == 0) {
				GL11.glPushMatrix();
				GL11.glTranslatef(track_type.getX(x, (TileEntityTrack) te), track_type.getY(y, (TileEntityTrack) te), track_type.getZ(z, (TileEntityTrack) te));
				GL11.glPushMatrix();
				if(track_type.inverted) {
					GL11.glRotatef(180, 1, 0, 0);
				}
				track_type.render(model, (TileEntityTrack) te);
				GL11.glPopMatrix();
				GL11.glPopMatrix();
			}
			
			for(int i = 0; i < track_type.special_render_stages; i++) { 
				GL11.glPushMatrix();
				GL11.glTranslatef(track_type.getSpecialX(i, x, (TileEntityTrack) te), track_type.getSpecialY(i, y, (TileEntityTrack) te), track_type.getSpecialZ(i, z, (TileEntityTrack) te));
				GL11.glPushMatrix();
				if(track_type.inverted) {
					GL11.glRotatef(180, 1, 0, 0);
				}
				track_type.renderSpecial(i, model, (TileEntityTrack) te);
				GL11.glPopMatrix();
				GL11.glPopMatrix();
			}
			
			if(((TileEntityTrack) te).extra != null && ((TileEntityTrack) te).extra.allowedTrackTypes.contains(track_type)) {
				Minecraft.getMinecraft().renderEngine.bindTexture(((TileEntityTrack) te).extra.texture);
				
				GL11.glPushMatrix();
				GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F,(float) z + 0.5F);
				GL11.glPushMatrix();
				TrackType.rotate((TileEntityTrack) te);
				((TileEntityTrack) te).extra.render(track_type);
				GL11.glPopMatrix();
				GL11.glPopMatrix();
				
				for(int i = 0; i < ((TileEntityTrack) te).extra.special_render_stages; i++) {
					GL11.glPushMatrix();
					GL11.glTranslatef(((TileEntityTrack) te).extra.getSpecialX(i, x, (TileEntityTrack) te), ((TileEntityTrack) te).extra.getSpecialY(i, y, (TileEntityTrack) te), ((TileEntityTrack) te).extra.getSpecialZ(i, z, (TileEntityTrack) te));
					GL11.glPushMatrix();
					TrackType.rotate((TileEntityTrack) te);
					((TileEntityTrack) te).extra.renderSpecial(i, track_type, (TileEntityTrack) te);
					GL11.glPopMatrix();
					GL11.glPopMatrix();
				}
			}
		}
	}
}
