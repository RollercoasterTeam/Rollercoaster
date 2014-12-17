package robomuss.rc.track.piece;

import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
<<<<<<< HEAD
=======
import net.minecraft.world.World;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

>>>>>>> origin/One8PortTake2
import robomuss.rc.block.BlockTrackBase;
import robomuss.rc.block.te.TileEntityTrackBase;
import robomuss.rc.entity.EntityTrainDefault;
import robomuss.rc.util.IInventoryRenderSettings;

public class TrackPieceCorner extends TrackPiece implements IInventoryRenderSettings {
	public static final String partName = "corner";

	public TrackPieceCorner(String unlocalized_name, int crafting_cost, int render_stage) {
		super(unlocalized_name, crafting_cost, render_stage);
	}

//	@Override
//	public void renderItem(int render_stage, IItemRenderer.ItemRenderType render_type, TrackStyle style, BlockTrackBase blockTrack, World world, int x , int y , int z) {
//		IModelCustom model = style.getModel();
//
//		GL11.glTranslatef(getInventoryX(), getInventoryY(), getInventoryZ());
//		GL11.glScalef(0.625f, 0.625f, 0.625f);
//		if (render_type == IItemRenderer.ItemRenderType.EQUIPPED) {
//			GL11.glPushMatrix();
//			GL11.glScalef(1.55f, 1.55f, 1.55f);
//			GL11.glRotatef(90, 0, 1, 0);
//			GL11.glTranslatef(0, 1.15f, -1);
//			this.render(render_stage, model);
//			GL11.glPopMatrix();
//		} else if (render_type == IItemRenderer.ItemRenderType.EQUIPPED_FIRST_PERSON) {
//			GL11.glPushMatrix();
//			GL11.glScalef(1.5f, 1.5f, 1.5f);
//			GL11.glRotatef(90, 0, -1, 0);
//			GL11.glTranslatef(0.75f, 0, -2);
//			this.render(render_stage, model);
//			GL11.glPopMatrix();
//		} else if (render_type == IItemRenderer.ItemRenderType.INVENTORY) {
//			GL11.glPushMatrix();
//			RenderHelper.enableGUIStandardItemLighting();
//			GL11.glScalef(1.5f, 1.5f, 1.5f);
//			GL11.glRotatef(90, 0, -1, 0);
//			this.render(render_stage, model);
//			GL11.glPopMatrix();
//		} else if (render_type == IItemRenderer.ItemRenderType.ENTITY) {
//			GL11.glPushMatrix();
//			GL11.glScalef(1.5f, 1.5f, 1.5f);
//			this.render(render_stage, model);
//			GL11.glPopMatrix();
//		}
////		GL11.glPushMatrix();
////		RenderHelper.enableGUIStandardItemLighting();
////		GL11.glRotatef(90, 0, -1, 0);
////		model.renderPart(partName);
////		GL11.glPopMatrix();
//	}

//	public void render(int render_stage, IModelCustom model) {
//		model.renderPart(partName);
//	}

//	@Override
//	public void renderTileEntity(int render_stage, TrackStyle style, TileEntityTrackBase teTrack, World world, int x , int y , int z) {
//		rotate(teTrack, world, x, y, z);
//
//		IModelCustom model = style.getModel();
//		model.renderPart(partName);
//	}

	@Override
	public void moveTrain(BlockTrackBase track, EntityTrainDefault entity, TileEntityTrackBase teTrack) {
		if (teTrack != null) {
			EnumFacing trackFacing = (EnumFacing) teTrack.getWorld().getBlockState(teTrack.getPos()).getValue(BlockTrackBase.FACING);
			EnumFacing entityFacing = entity.getHorizontalFacing();

			switch (trackFacing) {
				case NORTH:
					switch (entityFacing) {
						case WEST: entity.changePositionRotationSpeed(new BlockPos(1, 0, -0.5f), false, entity.rotationPitch, 0, true, 0, false); break;
						case EAST: entity.changePositionRotationSpeed(new BlockPos(-0.5f, 0, 1), false, entity.rotationPitch, 0, true, 0, false); break;
					}
					break;
				case SOUTH:
					switch (entityFacing) {
						case NORTH: entity.changePositionRotationSpeed(new BlockPos(-1, 0, 0.5f), false, entity.rotationPitch, 180, true, 0, false); break;
						case SOUTH: entity.changePositionRotationSpeed(new BlockPos(0.5f, 0, -1), false, entity.rotationPitch, 270, true, 0, false); break;
					}
					break;
				case WEST:
					switch (entityFacing) {
						case NORTH: entity.changePositionRotationSpeed(new BlockPos(1, 0, 0.5f), false, entity.rotationPitch, 0, true, 0, false);     break;
						case SOUTH: entity.changePositionRotationSpeed(new BlockPos(-0.5f, 0, -1), false, entity.rotationPitch, 270, true, 0, false); break;
					}
					break;
				case EAST:
					switch (entityFacing) {
						case SOUTH: entity.changePositionRotationSpeed(new BlockPos(0.5f, 0, 1), false, entity.rotationPitch, 90, true, 0, false);    break;
						case WEST:  entity.changePositionRotationSpeed(new BlockPos(-1, 0, -0.5f), false, entity.rotationPitch, 180, true, 0, false); break;
					}
					break;
			}
		}
	}

	@Override
	public AxisAlignedBB getBlockBounds(IBlockAccess iba, BlockPos pos) {
		return AxisAlignedBB.fromBounds(0, 0, 0, 1, 0.4F, 1);
	}

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
		return 1.5f;
	}

	@Override
	public float getInventoryRotation() {
		return 180f;
	}

	@Override
	public boolean useIcon() {
		return false;
	}
}
