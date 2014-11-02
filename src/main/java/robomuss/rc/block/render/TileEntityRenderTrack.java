package robomuss.rc.block.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import org.lwjgl.opengl.GL11;
import robomuss.rc.block.BlockTrackBase;
import robomuss.rc.block.te.TileEntityTrackBase;
import robomuss.rc.track.TrackHandler;
import robomuss.rc.track.piece.TrackPiece;
import robomuss.rc.track.style.TrackStyle;

import java.nio.FloatBuffer;


public class TileEntityRenderTrack extends TileEntitySpecialRenderer {
	private static FloatBuffer colorBuffer = GLAllocation.createDirectFloatBuffer(16);
	private static final Vec3 vector = Vec3.createVectorHelper(0.20000000298023224D, 1.0D, -0.699999988079071D).normalize();
	private static final Vec3 vector1 = Vec3.createVectorHelper(-0.20000000298023224D, 1.0D, -0.699999988079071D).normalize();

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
		int colour = teTrack.colour;
		
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
//				enableTrackLighting();
//				RenderHelper.enableStandardItemLighting();
				if (teTrack.getWorldObj().getBlockMetadata(teTrack.xCoord, teTrack.yCoord, teTrack.zCoord) <= 11) {
//				if (!teTrack.isDummy) {
					type.renderTileEntity(style, teTrack, teTrack.getWorldObj(), teTrack.xCoord, teTrack.yCoord, teTrack.zCoord);
				}
//				GL11.glDisable(GL11.GL_LIGHT2);
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
				GL11.glDisable(GL11.GL_LIGHTING);
//				GL11.glTranslatef(type.getSpecialX(i, x, teTrack, teTrack.getWorldObj(), teTrack.xCoord, teTrack.yCoord, teTrack.zCoord), type.getSpecialY(i, y, teTrack, teTrack.getWorldObj(), teTrack.xCoord, teTrack.yCoord, teTrack.zCoord) - 1.5f, type.getSpecialZ(i, z, teTrack, teTrack.getWorldObj(), teTrack.xCoord, teTrack.yCoord, teTrack.zCoord));
//				GL11.glScalef(0.0625f, 0.0625f, 0.0625f);
				GL11.glScalef(0.0625f, 0.0625f, 0.0625f);
				GL11.glPushMatrix();
				if(type.inverted) {
					GL11.glRotatef(180, 1, 0, 0);
				}
//				enableTrackLighting();
//				RenderHelper.enableStandardItemLighting();
				if (teTrack.getWorldObj().getBlockMetadata(teTrack.xCoord, teTrack.yCoord, teTrack.zCoord) <= 11) {
//				if (!teTrack.isDummy) {
//					teTrack.isDummy = true;
					type.renderSpecialTileEntity(i, style, teTrack, teTrack.getWorldObj(), teTrack.xCoord, teTrack.yCoord, teTrack.zCoord);
				}
				GL11.glEnable(GL11.GL_LIGHTING);
				GL11.glPopMatrix();
				GL11.glPopMatrix();
			}
			
			if(teTrack.extra != null && teTrack.extra.allowedTrackTypes.contains(type)) {
				Minecraft.getMinecraft().renderEngine.bindTexture(teTrack.extra.texture);
				
				GL11.glPushMatrix();
				GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F,(float) z + 0.5F);
				GL11.glPushMatrix();
				if (!teTrack.isDummy) {
					type.rotate(teTrack, teTrack.getWorldObj(), teTrack.xCoord, teTrack.yCoord, teTrack.zCoord);
					teTrack.extra.render(type);
				}
				GL11.glPopMatrix();
				GL11.glPopMatrix();
				
				for(int i = 0; i < teTrack.extra.special_render_stages; i++) {
					GL11.glPushMatrix();
					GL11.glTranslatef(teTrack.extra.getSpecialX(i, x, track), teTrack.extra.getSpecialY(i, y, track), teTrack.extra.getSpecialZ(i, z, track));
					GL11.glPushMatrix();
					if (!teTrack.isDummy) {
						type.rotate(teTrack, teTrack.getWorldObj(), te.xCoord, te.yCoord, te.zCoord);
						teTrack.extra.renderSpecial(i, type, track);
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
		float f = 0.4F;
		float f1 = 0.6F;
		float f2 = 0.0F;
//		GL11.glLight(GL11.GL_LIGHT0, GL11.GL_POSITION, setColorBuffer(vector.xCoord, vector.yCoord, vector.zCoord, 0.0D));
//		GL11.glLight(GL11.GL_LIGHT0, GL11.GL_DIFFUSE, setColorBuffer(f1, f1, f1, 1.0F));
//		GL11.glLight(GL11.GL_LIGHT0, GL11.GL_AMBIENT, setColorBuffer(0.0F, 0.0F, 0.0F, 1.0F));
//		GL11.glLight(GL11.GL_LIGHT0, GL11.GL_SPECULAR, setColorBuffer(f2, f2, f2, 1.0F));
//		GL11.glLight(GL11.GL_LIGHT1, GL11.GL_POSITION, setColorBuffer(vector1.xCoord, vector1.yCoord, vector1.zCoord, 0.0D));
//		GL11.glLight(GL11.GL_LIGHT1, GL11.GL_DIFFUSE, setColorBuffer(f1, f1, f1, 1.0F));
//		GL11.glLight(GL11.GL_LIGHT1, GL11.GL_AMBIENT, setColorBuffer(0.0F, 0.0F, 0.0F, 1.0F));
//		GL11.glLight(GL11.GL_LIGHT1, GL11.GL_SPECULAR, setColorBuffer(f2, f2, f2, 1.0F));
		GL11.glLight(GL11.GL_LIGHT2, GL11.GL_POSITION, setColorBuffer(-0.5D, 1.0D, -0.5D, 0.0D));
		GL11.glLight(GL11.GL_LIGHT2, GL11.GL_DIFFUSE, setColorBuffer(f1, f1, f1, 1.0F));
		GL11.glLight(GL11.GL_LIGHT2, GL11.GL_AMBIENT, setColorBuffer(0.0F, 0.0F, 0.0F, 1.0F));
		GL11.glLight(GL11.GL_LIGHT2, GL11.GL_SPECULAR, setColorBuffer(f2, f2, f2, 1.0F));
		GL11.glShadeModel(GL11.GL_FLAT);
		GL11.glLightModel(GL11.GL_LIGHT_MODEL_AMBIENT, setColorBuffer(f, f, f, 1.0F));
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
