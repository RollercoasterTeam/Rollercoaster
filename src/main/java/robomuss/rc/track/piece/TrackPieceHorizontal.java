package robomuss.rc.track.piece;

import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.IModelCustom;
import org.lwjgl.opengl.GL11;
import robomuss.rc.block.BlockTrackBase;
import robomuss.rc.block.te.TileEntityTrackBase;
import robomuss.rc.entity.EntityTrainDefault;
import robomuss.rc.track.style.TrackStyle;
import robomuss.rc.util.IInventoryRenderSettings;
import sun.org.mozilla.javascript.internal.ast.Block;

//import robomuss.rc.block.te.TileEntityTrack;

public class TrackPieceHorizontal extends TrackPiece implements IInventoryRenderSettings {
	public static final String partName = "horizontal";

	private float[] rotationOffsets = new float[] {180f, 0f, 0f, 0f};
//	private List<TileEntityTrackBase> teList = new ArrayList<TileEntityTrackBase>();
	public Block block;

	public TrackPieceHorizontal(String unlocalized_name, int crafting_cost) {
		super(unlocalized_name, crafting_cost);
	}

	public void addTileEntityToList(TileEntityTrackBase teTrack) {
		if (teTrack != null) {
			super.addTileEntityToList(this, teTrack);
		}
	}

	@Override
	public void renderTileEntity(TrackStyle style, TileEntityTrackBase teTrack, World world, int x ,int y, int z) {
//		if (teTrack != null && teTrack.direction != null) {
//			rotationOffsets = this.getRotationOffsetsFromDirection(teTrack.direction);
//		} else if (teTrack != null && teTrack.direction == null) {
//			rotationOffsets = teTrack.track.track_type.getRotationOffsetsFromDirection(ForgeDirection.SOUTH);
////			System.out.println("piece direction null");
//		}
		rotate(teTrack, world, x, y, z);

//		IModelCustom model = (IModelCustom) style.getStandardModel();
//		IModelCustom test  = (IModelCustom) style.getTestModel();
		IModelCustom model = style.getModel();

		GL11.glPushMatrix();
//		RenderHelper.enableStandardItemLighting();
//		GL11.glEnable(GL11.GL_LIGHTING);
//		GL11.glEnable(GL11.GL_BLEND);
//		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
//		GL11.glShadeModel(GL11.GL_SMOOTH);
//		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		GL11.glPushMatrix();

//		GL11.glRotatef(rotationOffsets[0], rotationOffsets[1], rotationOffsets[2], rotationOffsets[3]);
//		if (test != null) {
//			test.renderAll();
//		}

		if(style.name.equals("wooden_hybrid_topper")) {
//			ResourceLocation textures = (new ResourceLocation("rc:textures/models/colour_" + teTrack.track.colour + ".png"));
//
//			Minecraft.getMinecraft().renderEngine.bindTexture(textures);
//
//			model.renderPart("topper1");
//			model.renderPart("topper2");
//
//			ResourceLocation textures2 = (new ResourceLocation("textures/blocks/planks_oak.png"));
//
//			Minecraft.getMinecraft().renderEngine.bindTexture(textures2);
//
//			model.renderPart("top1");
//			model.renderPart("top2");
//			model.renderPart("bottom1");
//			model.renderPart("bottom2");
		} else {
//			if (style.name.equals("test")) {
//				style = TrackHandler.findTrackStyle("corkscrew");
//				teTrack.style = TrackHandler.findTrackStyle("corkscrew");
////				model = style.getStandardModel();
//				test = style.getTestModel();
////				model.renderAll();
////				GL11.glTranslatef(-0.5f, -1.5f, -0.5f);
//				test.renderAll();
////				GL11.glTranslatef(0.5f, 1.5f, -0.5f);
//			}

//			model.renderAll();
//			GL11.glTranslatef(-0.5f, -1.5f, -0.5f);
			model.renderPart(partName);
//			GL11.glTranslatef(0.5f, 1.5f, -0.5f);
//			GL11.glDisable(GL11.GL_LIGHTING);
//			RenderHelper.disableStandardItemLighting();
		}
		GL11.glPopMatrix();
//		GL11.glDisable(GL11.GL_BLEND);
		GL11.glPopMatrix();

//		track.track_type.unRotate(track);
	}
	
	@Override
	public void moveTrain(BlockTrackBase track, EntityTrainDefault entity, TileEntityTrackBase teTrack) {
//		if(track.direction == ForgeDirection.SOUTH || track.direction == ForgeDirection.NORTH) {
//			if(entity.direction.ordinal() - 2 == 0) {
//				entity.posZ += entity.speed;
//			}
//			if(entity.direction.ordinal() - 2 == 2) {
//				entity.posZ -= entity.speed;
//			}
//		}
//		if(track.direction == ForgeDirection.WEST || track.direction == ForgeDirection.EAST) {
//			if(entity.direction.ordinal() - 2 == 1) {
//				entity.posX += entity.speed;
//			}
//			if(entity.direction.ordinal() - 2 == 3) {
//				entity.posX -= entity.speed;
//			}
//		}
//		switch (teTrack.direction) {
//			case NORTH:
//			case SOUTH:
//				switch (entity.direction) {
//					case NORTH: entity.posZ -= entity.speed; break;
//					case SOUTH: entity.posZ += entity.speed; break;
//				}
//				break;
//			case WEST:
//			case EAST:
//				switch (entity.direction) {
//					case WEST: entity.posX += entity.speed; break;
//					case EAST: entity.posX -= entity.speed; break;
//				}
//		}
	}
	
	@Override
	public AxisAlignedBB getBlockBounds(IBlockAccess iba, int x, int y, int z) {
		return AxisAlignedBB.getBoundingBox(0, 0, 0, 1, 0.4F, 1);
	}

	//TODO: fix item rendering!!!
	@Override
	public float getInventoryX() {
		return 0;
	}

	@Override
	public float getInventoryY() {
		return -1f;
	}

	@Override
	public float getInventoryZ() {
		return 0;
	}

	@Override
	public float getInventoryScale() {
		return 1f;
	}

	@Override
	public float getInventoryRotation() {
		return 180f;
	}

	@Override
	public boolean useIcon() {
		return true;
	}
}
