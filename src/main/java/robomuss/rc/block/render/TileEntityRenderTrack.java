package robomuss.rc.block.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraft.util.Vec3i;
import org.lwjgl.opengl.GL11;
import robomuss.rc.block.BlockTrackBase;
import robomuss.rc.block.te.TileEntityTrackBase;
import robomuss.rc.track.TrackHandler;
import robomuss.rc.track.piece.TrackPiece;
import robomuss.rc.track.piece.TrackPieceHeartlineRoll;
import robomuss.rc.track.piece.TrackPieceLoop;
import robomuss.rc.track.style.TrackStyle;

import java.nio.FloatBuffer;


public class TileEntityRenderTrack extends TileEntitySpecialRenderer {
	private static FloatBuffer colorBuffer = GLAllocation.createDirectFloatBuffer(16);
//	private static final Vec3 vector = Vec3i.createVectorHelper(0.20000000298023224D, 1.0d, -0.699999988079071d).normalize();
//	private static final Vec3 vector1 = Vec3i.createVectorHelper(-0.20000000298023224D, 1.0d, -0.699999988079071d).normalize();

	private TrackStyle          style;
	private TrackPiece          track_type;
	private BlockTrackBase      track;
	private TileEntityTrackBase teTrack;

	int animation;

	@Override
	public void renderTileEntityAt(TileEntity te, double posX, double posZ, double posY, float f, int meta) {
		teTrack = (TileEntityTrackBase) te;
		track = teTrack.track;
		track_type = track.track_type;
		style = teTrack.style == null ? TrackHandler.findTrackStyle("corkscrew") : teTrack.style;
		int colour = teTrack.colour;
		
		ResourceLocation textures = (new ResourceLocation("rc:textures/models/colour_" + colour + ".png"));
		Minecraft.getMinecraft().renderEngine.bindTexture(textures);

		if (track_type != null) {
			//GL11.glEnable(GL11.GL_LIGHTING);

			if (track_type.render_stage == 1 && (!(track_type instanceof TrackPieceLoop) && !(track_type instanceof TrackPieceHeartlineRoll))) {
				BlockPos renderPos = track_type.getRenderPos(track_type.render_stage, posX, posY, posZ, teTrack, teTrack.getWorld(), teTrack.getPos().getX(), teTrack.getPos().getY(), teTrack.getPos().getZ());

				GL11.glPushMatrix();
				GL11.glDisable(GL11.GL_LIGHTING);
				GL11.glTranslatef(renderPos.getX(), renderPos.getY(), renderPos.getZ());
				GL11.glScalef(0.0625f, 0.0625f, 0.0625f);
				GL11.glPushMatrix();

				if(track_type.inverted) {
					GL11.glRotatef(180, 1, 0, 0);
				}

//				enableTrackLighting();
//				RenderHelper.enableStandardItemLighting();

				if (teTrack.getBlockMetadata() <= 11) {
					track_type.renderTileEntity(track_type.render_stage, style, teTrack, teTrack.getWorld(), teTrack.getPos());
				}

				GL11.glEnable(GL11.GL_LIGHTING);
				GL11.glPopMatrix();
				GL11.glPopMatrix();
			} else {
				for (int i = 0; i < track_type.render_stage; i++) {
					GL11.glPushMatrix();
					GL11.glTranslatef(teTrack.getPos().getX(), teTrack.getPos().getY(), teTrack.getPos().getZ());
					GL11.glDisable(GL11.GL_LIGHTING);
					GL11.glScalef(0.0625f, 0.0625f, 0.0625f);
					GL11.glPushMatrix();

					if (track_type.inverted) {
						GL11.glRotatef(180, 1, 0, 0);
					}

//				enableTrackLighting();
//				RenderHelper.enableStandardItemLighting();

					if (teTrack.getBlockMetadata() <= 11) {
						track_type.renderTileEntity(i, style, teTrack, teTrack.getWorld(), teTrack.getPos());
					}

					GL11.glEnable(GL11.GL_LIGHTING);
					GL11.glPopMatrix();
					GL11.glPopMatrix();
				}
			}
			
			if(teTrack.extra != null && teTrack.extra.allowedTrackTypes.contains(track_type)) {
				Minecraft.getMinecraft().renderEngine.bindTexture(teTrack.extra.texture);
				
				GL11.glPushMatrix();
				GL11.glTranslatef((float) posX + 0.5f, (float) posY + 1.5f,(float) posZ + 0.5f);
				GL11.glPushMatrix();

				if (teTrack.getBlockMetadata() <= 11) {
					track_type.rotate(teTrack, teTrack.getWorld(), teTrack.getPos());
					teTrack.extra.render(track_type);
				}

				GL11.glPopMatrix();
				GL11.glPopMatrix();
				
				for(int i = 0; i < teTrack.extra.render_stage; i++) {
					GL11.glPushMatrix();
					GL11.glTranslatef(teTrack.extra.getX(i, posX, teTrack), teTrack.extra.getY(i, posY, teTrack), teTrack.extra.getZ(i, posZ, teTrack));
					GL11.glPushMatrix();

					if (teTrack.getBlockMetadata() <= 11) {
						track_type.rotate(teTrack, teTrack.getWorld(), teTrack.getPos());
						teTrack.extra.render(i, track_type, teTrack);
					}

					GL11.glPopMatrix();
					GL11.glPopMatrix();
				}
			}
		}
	}

	public static void enableTrackLighting() {
		GL11.glEnable(GL11.GL_LIGHTING);
//		GL11.glEnable(GL11.GL_LIGHT0);
//		GL11.glEnable(GL11.GL_LIGHT1);
		GL11.glEnable(GL11.GL_LIGHT2);
		GL11.glEnable(GL11.GL_COLOR_MATERIAL);
		GL11.glColorMaterial(GL11.GL_FRONT_AND_BACK, GL11.GL_AMBIENT_AND_DIFFUSE);
		float f = 0.4f;
		float f1 = 0.6f;
		float f2 = 0.0f;
//		GL11.glLight(GL11.GL_LIGHT0, GL11.GL_POSITION, setColorBuffer(vector.xCoord, vector.yCoord, vector.zCoord, 0.0D));
//		GL11.glLight(GL11.GL_LIGHT0, GL11.GL_DIFFUSE, setColorBuffer(f1, f1, f1, 1.0F));
//		GL11.glLight(GL11.GL_LIGHT0, GL11.GL_AMBIENT, setColorBuffer(0.0F, 0.0F, 0.0F, 1.0F));
//		GL11.glLight(GL11.GL_LIGHT0, GL11.GL_SPECULAR, setColorBuffer(f2, f2, f2, 1.0F));
//		GL11.glLight(GL11.GL_LIGHT1, GL11.GL_POSITION, setColorBuffer(vector1.xCoord, vector1.yCoord, vector1.zCoord, 0.0D));
//		GL11.glLight(GL11.GL_LIGHT1, GL11.GL_DIFFUSE, setColorBuffer(f1, f1, f1, 1.0F));
//		GL11.glLight(GL11.GL_LIGHT1, GL11.GL_AMBIENT, setColorBuffer(0.0F, 0.0F, 0.0F, 1.0F));
//		GL11.glLight(GL11.GL_LIGHT1, GL11.GL_SPECULAR, setColorBuffer(f2, f2, f2, 1.0F));
		GL11.glLight(GL11.GL_LIGHT2, GL11.GL_POSITION, setColorBuffer(-0.5d, 1.0d, -0.5d, 0.0d));
		GL11.glLight(GL11.GL_LIGHT2, GL11.GL_DIFFUSE, setColorBuffer(f1, f1, f1, 1.0f));
		GL11.glLight(GL11.GL_LIGHT2, GL11.GL_AMBIENT, setColorBuffer(0.0f, 0.0f, 0.0f, 1.0f));
		GL11.glLight(GL11.GL_LIGHT2, GL11.GL_SPECULAR, setColorBuffer(f2, f2, f2, 1.0f));
		GL11.glShadeModel(GL11.GL_FLAT);
		GL11.glLightModel(GL11.GL_LIGHT_MODEL_AMBIENT, setColorBuffer(f, f, f, 1.0f));
	}

	private static FloatBuffer setColorBuffer(double x, double y, double z, double d) {
		return setColorBuffer((float) x, (float) y, (float) z, (float) d);
	}

	private static FloatBuffer setColorBuffer(float x, float y, float z, float d) {
		colorBuffer.clear();
		colorBuffer.put(x).put(y).put(z).put(d);
		colorBuffer.flip();
		return colorBuffer;
	}
}
