package robomuss.rc.track.piece;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import robomuss.rc.block.BlockTrackBase;
import robomuss.rc.block.te.TileEntityTrackBase;
import robomuss.rc.entity.EntityTrainDefault;
import robomuss.rc.track.style.TrackStyle;
import robomuss.rc.util.IInventoryRenderSettings;

public class TrackPieceSlope extends TrackPiece implements IInventoryRenderSettings {
	public static final String partName = "horizontal_large";

	public TrackPieceSlope(String unlocalized_name, int crafting_cost, int render_stage) {
		super(unlocalized_name, crafting_cost, render_stage);
	}

//	@Override
//	public void renderItem(int render_stage, IItemRenderer.ItemRenderType render_type, TrackStyle style, BlockTrackBase blockTrack, World world, int x , int y , int z) {
//		IModelCustom model = style.getModel();
//
//		GL11.glTranslatef(getInventoryX(), getInventoryY(), getInventoryZ());
////		GL11.glRotatef(180, 0, 0, 1);
//		GL11.glScalef(0.625f, 0.625f, 0.625f);
//		if (render_type == IItemRenderer.ItemRenderType.EQUIPPED) {
//			GL11.glPushMatrix();
////			GL11.glRotatef(45, 0, 1, 0);
////			GL11.glRotatef(20, 0, 0, -1);
////			GL11.glTranslatef(-9.25f, -13.75f, 0.55f);
//			GL11.glTranslatef(-13.5f, 1.5f, -0.5f);
//			GL11.glScalef(1.55f, 1.55f, 1.55f);
//			this.render(render_stage, model);
//			GL11.glPopMatrix();
//		} else if (render_type == IItemRenderer.ItemRenderType.EQUIPPED_FIRST_PERSON) {
//			GL11.glPushMatrix();
////			GL11.glRotatef(45, 0, -1, 0);
////			GL11.glRotatef(30, 0, 0, 1);
////			GL11.glScalef(getInventoryScale(), getInventoryScale(), getInventoryScale());
//			GL11.glScalef(1.5f, 1.5f, 1.5f);
//			GL11.glTranslatef(3, 0, 1);
//			this.render(render_stage, model);
//			GL11.glPopMatrix();
//		} else if (render_type == IItemRenderer.ItemRenderType.INVENTORY) {
//			GL11.glPushMatrix();
//			RenderHelper.enableGUIStandardItemLighting();
//			GL11.glTranslatef(0, 8, -8);
//			GL11.glScalef(1.5f, 1.5f, 1.5f);
//			this.render(render_stage, model);
//			GL11.glPopMatrix();
//		} else if (render_type == IItemRenderer.ItemRenderType.ENTITY) {
//			GL11.glPushMatrix();
//			GL11.glTranslatef(-5, 0, 0);
//			this.render(render_stage, model);
//			GL11.glPopMatrix();
//		}
//	}

	@Override
	public void renderTileEntity(int render_stage, TrackStyle style, TileEntityTrackBase teTrack, World world, BlockPos pos) {
//		rotate(teTrack, world, x, y, z);
//
//		IModelCustom model = style.getModel();
//
//		if (!teTrack.isDummy) {
//			GL11.glRotatef(45f, 0f, 0f, 1f);
//			model.renderPart(partName);
//		}
	}

	@Override
	public BlockPos getRenderPos(int render_stage, double x, double y, double z, TileEntityTrackBase teTrack, World world, int lx, int ly, int lz) {
		EnumFacing trackFacing = (EnumFacing) world.getBlockState(teTrack.getPos()).getValue(BlockTrackBase.FACING);
		boolean isDummy = (Boolean) world.getBlockState(teTrack.getPos()).getValue(BlockTrackBase.DUMMY);

		switch (trackFacing) {
			case NORTH: return new BlockPos(x + 0.5f, y + 2, z - 0.5f);
			case SOUTH: return new BlockPos(x + 0.5f, y + 2, z + 1.5f);
			case WEST:  return new BlockPos(x - 0.5f, y + 2, z + 0.5f);
			case EAST:  return new BlockPos(x + 1.5f, y + 2, z + 0.5f);
		}

		return new BlockPos(x + 0.5f, y + 2, z);
	}

	@Override
	public AxisAlignedBB getRenderBoundingBox(World world, BlockPos pos) {
		return AxisAlignedBB.fromBounds(pos.west().getX(), pos.getY(), pos.north().getZ(), pos.east(2).getX(), pos.up(2).getY(), pos.south(2).getZ());
	}
	
	@Override
	public void moveTrain(BlockTrackBase track, EntityTrainDefault entity, TileEntityTrackBase teTrack) {
		EnumFacing trackFacing = (EnumFacing) teTrack.getWorld().getBlockState(teTrack.getPos()).getValue(BlockTrackBase.FACING);
		EnumFacing entityFacing = entity.getHorizontalFacing();

		if(entity.riddenByEntity != null && entity.riddenByEntity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) entity.riddenByEntity;
			player.swingProgressInt = 90;
		}

		switch (trackFacing) {
			case NORTH:
				switch (entityFacing) {
					case NORTH: entity.changePositionRotationSpeed(new BlockPos(0, -1, 1), false, 0, entity.rotationYaw, true, 0, false); break;
					case SOUTH: entity.changePositionRotationSpeed(new BlockPos(0, 1, -1), false, 0, entity.rotationYaw, true, 0, false); break;
				}
				break;
			case SOUTH:
				switch (entityFacing) {
					case NORTH: entity.changePositionRotationSpeed(new BlockPos(0, 1, 1), false, 0, entity.rotationYaw, true, 0, false);   break;
					case SOUTH: entity.changePositionRotationSpeed(new BlockPos(0, -1, -1), false, 0, entity.rotationYaw, true, 0, false); break;
				}
				break;
			case WEST:
				switch (entityFacing) {
					case WEST: entity.changePositionRotationSpeed(new BlockPos(1, -1, 0), false, 0, entity.rotationYaw, true, 0, false); break;
					case EAST: entity.changePositionRotationSpeed(new BlockPos(-1, 1, 0), false, 0, entity.rotationYaw, true, 0, false); break;
				}
				break;
			case EAST:
				switch (entityFacing) {
					case WEST: entity.changePositionRotationSpeed(new BlockPos(1, 1, 0), false, 0, entity.rotationYaw, true, 0, false);   break;
					case EAST: entity.changePositionRotationSpeed(new BlockPos(-1, -1, 0), false, 0, entity.rotationYaw, true, 0, false); break;
				}
				break;
		}
	}

	@Override
	public float getInventoryX() {
		return 0.6f;
	}

	@Override
	public float getInventoryY() {
		return 0.45f;
	}

	@Override
	public float getInventoryZ() {
		return 0;
	}

	@Override
	public float getInventoryScale() {
		return 1.55f;
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
