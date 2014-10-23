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
	
//	public static TileEntityRenderTrack instance = new TileEntityRenderTrack();
	private TrackStyle style;
	private TrackPiece type;
	private BlockTrackBase track;
	private TileEntityTrackBase teTrack;
	
	int animation;

	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z, float scale) {
		teTrack = (TileEntityTrackBase) te;
		track = teTrack.track;
		type = track.track_type;
		style = track.style;
		if(style == null) {
			style = TrackHandler.findTrackStyle("corkscrew");
		}
		int colour = track.colour;
		
		ResourceLocation textures = (new ResourceLocation("rc:textures/models/colour_" + colour + ".png"));

		Minecraft.getMinecraft().renderEngine.bindTexture(textures);

//		Block block = te.getWorldObj().getBlock(te.xCoord, te.yCoord, te.zCoord);
//		Block block = TrackManager.getTrackAtCoords(te.xCoord, te.yCoord, te.zCoord);
//		TrackPiece track_type = TrackHandler.findTrackTypeFull(track.getUnlocalizedName());
		TrackPiece track_type = track.track_type != null ? track.track_type : TrackHandler.findTrackTypeFull(track.getUnlocalizedName());
//		TrackPiece track_type = TrackHandler.findTrackTypeFull(((TileEntityTrack) te).track.getUnlocalizedName());

		if(type != null) {
	        //GL11.glEnable(GL11.GL_LIGHTING);

			if(type.special_render_stages == 0) {
				GL11.glPushMatrix();
				GL11.glDisable(GL11.GL_LIGHTING);
				GL11.glTranslatef(type.getX(x, track, te.getWorldObj(), te.xCoord, te.yCoord, te.zCoord), type.getY(y, track, te.getWorldObj(), te.xCoord, te.yCoord, te.zCoord) - 1.5f, type.getZ(z, track, te.getWorldObj(), te.xCoord, te.yCoord, te.zCoord));
				GL11.glScalef(0.0625f, 0.0625f, 0.0625f);
				GL11.glPushMatrix();
				if(type.inverted) {
					GL11.glRotatef(180, 1, 0, 0);
				}
				type.render(style, track, te.getWorldObj(), te.xCoord, te.yCoord, te.zCoord);
				GL11.glEnable(GL11.GL_LIGHTING);
				GL11.glPopMatrix();
				GL11.glPopMatrix();
			}
			
			for(int i = 0; i < type.special_render_stages; i++) {
				GL11.glPushMatrix();
				GL11.glTranslatef(type.getSpecialX(i, x, track, te.getWorldObj(), te.xCoord, te.yCoord, te.zCoord), type.getSpecialY(i, y, track, te.getWorldObj(), te.xCoord, te.yCoord, te.zCoord) - 1.5f, type.getSpecialZ(i, z, track, te.getWorldObj(), te.xCoord, te.yCoord, te.zCoord));
				GL11.glScalef(0.0625f, 0.0625f, 0.0625f);
				GL11.glPushMatrix();
				if(type.inverted) {
					GL11.glRotatef(180, 1, 0, 0);
				}
				type.renderSpecial(i, style, track, te.getWorldObj(), te.xCoord, te.yCoord, te.zCoord);
				GL11.glPopMatrix();
				GL11.glPopMatrix();
			}
			
			if(track.extra != null && track.extra.allowedTrackTypes.contains(type)) {
				Minecraft.getMinecraft().renderEngine.bindTexture(track.extra.texture);
				
				GL11.glPushMatrix();
				GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F,(float) z + 0.5F);
				GL11.glPushMatrix();
				type.rotate(track, te.getWorldObj(), te.xCoord, te.yCoord, te.zCoord);
				track.extra.render(type);
				GL11.glPopMatrix();
				GL11.glPopMatrix();
				
				for(int i = 0; i < track.extra.special_render_stages; i++) {
					GL11.glPushMatrix();
					GL11.glTranslatef(track.extra.getSpecialX(i, x, track), track.extra.getSpecialY(i, y, track), track.extra.getSpecialZ(i, z, track));
					GL11.glPushMatrix();
					type.rotate(track, teTrack.getWorldObj(), te.xCoord, te.yCoord, te.zCoord);
					track.extra.renderSpecial(i, type, track);
					GL11.glPopMatrix();
					GL11.glPopMatrix();
				}
			}
		}
	}
}
