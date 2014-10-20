package robomuss.rc.block.render;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import robomuss.rc.RCMod;
import robomuss.rc.block.BlockTrackBase;
import robomuss.rc.block.te.TileEntityTrackBase;
import robomuss.rc.track.TrackHandler;
import robomuss.rc.track.TrackManager;
import robomuss.rc.track.piece.TrackPiece;
import robomuss.rc.track.style.TrackStyle;


public class TileEntityRenderTrack extends TileEntitySpecialRenderer {
	
	public static TileEntityRenderTrack instance = new TileEntityRenderTrack();
	private TrackStyle style;
	
	int animation;

	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z, float scale) {
		TileEntityTrackBase teTrack = (TileEntityTrackBase) te;
		BlockTrackBase track = teTrack.track;
		style = track.style;
		if(style == null) {
			style = TrackHandler.findTrackStyle("corkscrew");
		}
		int colour = track.colour;
		
		ResourceLocation textures = (new ResourceLocation("rc:textures/models/colour_" + colour + ".png"));

		Minecraft.getMinecraft().renderEngine.bindTexture(textures);

//		Block block = te.getWorldObj().getBlock(te.xCoord, te.yCoord, te.zCoord);
//		Block block = TrackManager.getTrackAtCoords(te.xCoord, te.yCoord, te.zCoord);
		TrackPiece track_type = TrackHandler.findTrackTypeFull(track.getUnlocalizedName());
//		TrackPiece track_type = TrackHandler.findTrackTypeFull(((TileEntityTrack) te).track.getUnlocalizedName());

		if(track_type != null) {
	        //GL11.glEnable(GL11.GL_LIGHTING);

			if(track_type.special_render_stages == 0) {
				GL11.glPushMatrix();
				GL11.glDisable(GL11.GL_LIGHTING);
				GL11.glTranslatef(track_type.getX(x, track), track_type.getY(y, track) - 1.5f, track_type.getZ(z, track));
				GL11.glScalef(0.0625f, 0.0625f, 0.0625f);
				GL11.glPushMatrix();
				if(track_type.inverted) {
					GL11.glRotatef(180, 1, 0, 0);
				}
				track_type.render(style, ((TileEntityTrackBase) te).track);
				GL11.glEnable(GL11.GL_LIGHTING);
				GL11.glPopMatrix();
				GL11.glPopMatrix();
			}
			
			for(int i = 0; i < track_type.special_render_stages; i++) { 
				GL11.glPushMatrix();
				GL11.glTranslatef(track_type.getSpecialX(i, x, track), track_type.getSpecialY(i, y, track) - 1.5f, track_type.getSpecialZ(i, z, track));
				GL11.glScalef(0.0625f, 0.0625f, 0.0625f);
				GL11.glPushMatrix();
				if(track_type.inverted) {
					GL11.glRotatef(180, 1, 0, 0);
				}
				track_type.renderSpecial(i, style, track);
				GL11.glPopMatrix();
				GL11.glPopMatrix();
			}
			
			if(track.extra != null && track.extra.allowedTrackTypes.contains(track_type)) {
				Minecraft.getMinecraft().renderEngine.bindTexture(track.extra.texture);
				
				GL11.glPushMatrix();
				GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F,(float) z + 0.5F);
				GL11.glPushMatrix();
				TrackPiece.rotate(track);
				track.extra.render(track_type);
				GL11.glPopMatrix();
				GL11.glPopMatrix();
				
				for(int i = 0; i < track.extra.special_render_stages; i++) {
					GL11.glPushMatrix();
					GL11.glTranslatef(track.extra.getSpecialX(i, x, track), track.extra.getSpecialY(i, y, track), track.extra.getSpecialZ(i, z, track));
					GL11.glPushMatrix();
					TrackPiece.rotate(track);
					track.extra.renderSpecial(i, track_type, track);
					GL11.glPopMatrix();
					GL11.glPopMatrix();
				}
			}
		}
	}
}
