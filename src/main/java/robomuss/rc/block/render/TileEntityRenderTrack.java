package robomuss.rc.block.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.shader.TesselatorVertexState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import robomuss.rc.block.BlockTrackBase;
import robomuss.rc.block.te.TileEntityTrackBase;
import robomuss.rc.track.TrackHandler;
import robomuss.rc.track.piece.TrackPiece;
import robomuss.rc.track.style.TrackStyle;


public class TileEntityRenderTrack extends TileEntitySpecialRenderer {
	
//	public static TileEntityRenderTrack instance = new TileEntityRenderTrack();
	private TrackStyle style;
	private TrackPiece type;
	private BlockTrackBase track;
	private TileEntityTrackBase teTrack;
//	private boolean drawDebug = false;
	
	int animation;

	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z, float scale) {
		teTrack = (TileEntityTrackBase) te;
		track = teTrack.track;
		type = track.track_type;
		style = teTrack.style;
		if(style == null) {
			style = TrackHandler.findTrackStyle("corkscrew");
		}
		int colour = track.colour;
		
		ResourceLocation textures = (new ResourceLocation("rc:textures/models/colour_" + colour + ".png"));

		Minecraft.getMinecraft().renderEngine.bindTexture(textures);

//		Block block = te.getWorldObj().getBlock(te.xCoord, te.yCoord, te.zCoord);
//		Block block = TrackManager.getTrackAtCoords(te.xCoord, te.yCoord, te.zCoord);
//		TrackPiece track_type = TrackHandler.findTrackTypeFull(track.getUnlocalizedName());
//		TrackPiece track_type = track.track_type != null ? track.track_type : TrackHandler.findTrackTypeFull(track.getUnlocalizedName());
//		TrackPiece track_type = TrackHandler.findTrackTypeFull(((TileEntityTrack) te).track.getUnlocalizedName());

		if(type != null) {
	        //GL11.glEnable(GL11.GL_LIGHTING);

//			if (drawDebug) {
//				Tessellator tess = Tessellator.instance;
//				this.renderDebug(te, x, y, z, tess);
//			}

			if(type.special_render_stages == 0) {
				float xTrans = type.getX(x, teTrack, teTrack.getWorldObj(), teTrack.xCoord, teTrack.yCoord, teTrack.zCoord);
				float yTrans = type.getY(y, teTrack, teTrack.getWorldObj(), teTrack.xCoord, teTrack.yCoord, teTrack.zCoord) - 1.5F;
				float zTrans = type.getZ(z, teTrack, teTrack.getWorldObj(), teTrack.xCoord, teTrack.yCoord, teTrack.zCoord);

				GL11.glPushMatrix();
				GL11.glDisable(GL11.GL_LIGHTING);
				GL11.glTranslatef(xTrans, yTrans, zTrans);
//				GL11.glTranslatef(type.getX(x, teTrack, teTrack.getWorldObj(), teTrack.xCoord, teTrack.yCoord, teTrack.zCoord), type.getY(y, teTrack, teTrack.getWorldObj(), teTrack.xCoord, teTrack.yCoord, teTrack.zCoord) - 1.5f, type.getZ(z, teTrack, teTrack.getWorldObj(), teTrack.xCoord, teTrack.yCoord, teTrack.zCoord));
				GL11.glScalef(0.0625f, 0.0625f, 0.0625f);
//				GL11.glScalef(0.125f, 0.125f, 0.125f);
//				GL11.glScalef(1f, 1f, 1f);
				GL11.glPushMatrix();
				if(type.inverted) {
					GL11.glRotatef(180, 1, 0, 0);
				}
				type.renderTileEntity(style, teTrack, teTrack.getWorldObj(), teTrack.xCoord, teTrack.yCoord, teTrack.zCoord);
				GL11.glEnable(GL11.GL_LIGHTING);
				GL11.glPopMatrix();
				GL11.glPopMatrix();
			}
			
			for(int i = 0; i < type.special_render_stages; i++) {
				float xTrans = type.getSpecialX(i, x, teTrack, teTrack.getWorldObj(), teTrack.xCoord, teTrack.yCoord, teTrack.zCoord);
				float yTrans = type.getSpecialY(i, y, teTrack, teTrack.getWorldObj(), teTrack.xCoord, teTrack.yCoord, teTrack.zCoord) - 1.5F;
				float zTrans = type.getSpecialZ(i, z, teTrack, teTrack.getWorldObj(), teTrack.xCoord, teTrack.yCoord, teTrack.zCoord);

				GL11.glPushMatrix();
				GL11.glTranslatef(xTrans, yTrans, zTrans);
//				GL11.glTranslatef(type.getSpecialX(i, x, teTrack, teTrack.getWorldObj(), teTrack.xCoord, teTrack.yCoord, teTrack.zCoord), type.getSpecialY(i, y, teTrack, teTrack.getWorldObj(), teTrack.xCoord, teTrack.yCoord, teTrack.zCoord) - 1.5f, type.getSpecialZ(i, z, teTrack, teTrack.getWorldObj(), teTrack.xCoord, teTrack.yCoord, teTrack.zCoord));
//				GL11.glScalef(0.0625f, 0.0625f, 0.0625f);
				GL11.glScalef(0.125f, 0.125f, 0.125f);
				GL11.glPushMatrix();
				if(type.inverted) {
					GL11.glRotatef(180, 1, 0, 0);
				}
				type.renderSpecialTileEntity(i, style, teTrack, teTrack.getWorldObj(), teTrack.xCoord, teTrack.yCoord, teTrack.zCoord);
				GL11.glPopMatrix();
				GL11.glPopMatrix();
			}
			
			if(track.extra != null && track.extra.allowedTrackTypes.contains(type)) {
				Minecraft.getMinecraft().renderEngine.bindTexture(track.extra.texture);
				
				GL11.glPushMatrix();
				GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F,(float) z + 0.5F);
				GL11.glPushMatrix();
				type.rotate(teTrack, teTrack.getWorldObj(), teTrack.xCoord, teTrack.yCoord, teTrack.zCoord);
				teTrack.extra.render(type);
				GL11.glPopMatrix();
				GL11.glPopMatrix();
				
				for(int i = 0; i < track.extra.special_render_stages; i++) {
					GL11.glPushMatrix();
					GL11.glTranslatef(teTrack.extra.getSpecialX(i, x, track), teTrack.extra.getSpecialY(i, y, track), teTrack.extra.getSpecialZ(i, z, track));
					GL11.glPushMatrix();
					type.rotate(teTrack, teTrack.getWorldObj(), te.xCoord, te.yCoord, te.zCoord);
					teTrack.extra.renderSpecial(i, type, track);
					GL11.glPopMatrix();
					GL11.glPopMatrix();
				}
			}
		}
	}

	public void renderDebug(TileEntity te, double x, double y, double z, Tessellator tess) {
//		ResourceLocation debugTexture = new ResourceLocation("rc:textures/blocks/direction_debug.png");
//		this.bindTexture(debugTexture);
//
//		tess.startDrawingQuads();
//		{
//			tess.addVertexWithUV(1, 0.25, 1, 1, 1);
//			tess.addVertexWithUV(1, 0.25, 0, 1, 0);
//			tess.addVertexWithUV(0, 0.25, 0, 0, 0);
//			tess.addVertexWithUV(0, 0.25, 1, 0, 1);
//		}
//		tess.draw();
	}
}
