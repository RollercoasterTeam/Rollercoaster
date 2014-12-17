package robomuss.rc.track.piece;

import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.client.IItemRenderer;
<<<<<<< HEAD
=======

>>>>>>> origin/One8PortTake2
import org.lwjgl.opengl.GL11;

import robomuss.rc.block.BlockTrackBase;
import robomuss.rc.block.te.TileEntityTrackBase;
import robomuss.rc.entity.EntityTrainDefault;
import robomuss.rc.track.style.TrackStyle;
import robomuss.rc.util.IInventoryRenderSettings;

public class TrackPieceSlopeUp extends TrackPiece implements IInventoryRenderSettings {
	public static final String[] partNames = {"horizontal", "horizontal"};
	public BlockTrackBase lonelyTrack;
	private BlockPos partnerPos;

	public TrackPieceSlopeUp(String unlocalized_name, int crafting_cost, int i) {
		super(unlocalized_name, crafting_cost, i);
	}

	public void setPartnerPos(BlockPos partnerPos) {
		this.partnerPos = partnerPos;
	}

	public BlockPos getPartnerPos() {
		return this.partnerPos;
	}

//	@Override
//	public void renderItem(int render_stage, IItemRenderer.ItemRenderType renderType, TrackStyle style, BlockTrackBase track, World world, BlockPos pos) {
//		IModelCustom model = style.getModel();
//
//		GL11.glTranslatef(getInventoryX(), getInventoryY(), getInventoryZ());
//		GL11.glRotatef(180f, 0f, 0f, 1f);                       //flip model
//		GL11.glScalef(0.625f, 0.625f, 0.625f);
//		if (renderType == IItemRenderer.ItemRenderType.EQUIPPED) {
//			GL11.glPushMatrix();
//			GL11.glRotatef(225, 0, 1, 0);
//			GL11.glRotatef(20, 0, 0, 1);
//			GL11.glScalef(getInventoryScale(), getInventoryScale(), getInventoryScale());
//			GL11.glTranslatef(-9.25f, -16.75f, -0.25f);
//			this.render(render_stage, model);
//			GL11.glPopMatrix();
//		} else if (renderType == IItemRenderer.ItemRenderType.EQUIPPED_FIRST_PERSON) {
//			GL11.glPushMatrix();
//			GL11.glRotatef(45, 0, -1, 0);
//			GL11.glRotatef(30, 0, 0, 1);
//			GL11.glScalef(getInventoryScale(), getInventoryScale(), getInventoryScale());
//			GL11.glTranslatef(-16, -12, 6.75f);
//			this.render(render_stage, model);
//			GL11.glPopMatrix();
//		} else if (renderType == IItemRenderer.ItemRenderType.INVENTORY) {
//			GL11.glPushMatrix();
//			RenderHelper.enableGUIStandardItemLighting();
//			GL11.glTranslatef(-8, 0, -2);
//			GL11.glScalef(0.9f, 0.9f, 0.9f);
//			this.render(render_stage, model);
//			GL11.glPopMatrix();
//		} else if (renderType == IItemRenderer.ItemRenderType.ENTITY) {
//			GL11.glPushMatrix();
//			GL11.glTranslatef(-16.5f, 0, 0);
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
//			if (render_stage == 0) {                                 //render rotated model
//				GL11.glTranslatef(0, 16, 0);
//				GL11.glRotatef(45f, 0f, 0f, 1f);
//				model.renderPart(partNames[0]);
//			}
//
//			if (render_stage == 1) {                                 //render flat model
//				GL11.glPushMatrix();
//				model.renderPart(partNames[1]);
//				GL11.glPopMatrix();
//			}
//		}
	}

	@Override
	public BlockPos getRenderPos(int render_stage, double x, double y, double z, TileEntityTrackBase teTrack, World world, int lx, int ly, int lz) {
		EnumFacing trackFacing = (EnumFacing) world.getBlockState(teTrack.getPos()).getValue(BlockTrackBase.FACING);
		boolean isDummy = (Boolean) world.getBlockState(teTrack.getPos()).getValue(BlockTrackBase.DUMMY);

		switch (trackFacing) {
			case NORTH: return render_stage == 0 ? new BlockPos(x + 0.5f, y + 1.5f, z - 0.5f) : (render_stage == 1 ? new BlockPos(x + 0.5f, y + 1.5f, z + 0.5f) : super.getRenderPos(render_stage, x, y, z, teTrack, world, lx, ly, lz));
			case SOUTH: return render_stage == 0 ? new BlockPos(x + 0.5f, y + 1.5f, z + 1.5f) : (render_stage == 1 ? new BlockPos(x + 0.5f, y + 1.5f, z + 0.5f) : super.getRenderPos(render_stage, x, y, z, teTrack, world, lx, ly, lz));
			case WEST:  return render_stage == 0 ? new BlockPos(x - 0.5f, y + 1.5f, z + 0.5f) : (render_stage == 1 ? new BlockPos(x + 0.5f, y + 1.5f, z + 0.5f) : super.getRenderPos(render_stage, x, y, z, teTrack, world, lx, ly, lz));
			case EAST:  return render_stage == 0 ? new BlockPos(x + 1.5f, y + 1.5f, z + 0.5f) : (render_stage == 1 ? new BlockPos(x + 0.5f, y + 1.5f, z + 0.5f) : super.getRenderPos(render_stage, x, y, z, teTrack, world, lx, ly, lz));
		}

		return super.getRenderPos(render_stage, x, y, z, teTrack, world, lx, ly, lz);
	}
	
	@Override
	public AxisAlignedBB getRenderBoundingBox(World world, BlockPos pos) {
		return AxisAlignedBB.fromBounds(pos.west().getX(), pos.getY(), pos.north().getZ(), pos.east(2).getX(), pos.up(2).getY(), pos.south(2).getZ());
	}

	@Override
	public void moveTrain(BlockTrackBase track, EntityTrainDefault entity, TileEntityTrackBase teTrack) {
		EnumFacing trackFacing = (EnumFacing) teTrack.getWorld().getBlockState(teTrack.getPos()).getValue(BlockTrackBase.FACING);
		EnumFacing entityFacing = entity.getHorizontalFacing();

		//if track facing north, then if train facing north, add 1F to z and y and rotate 45 degrees

		switch (trackFacing) {
			case NORTH:
				switch (entityFacing) {
					case NORTH: entity.changePositionRotationSpeed(new BlockPos(0, 1, 1), false, 45, entity.rotationYaw, true, 0, false); break;
					case SOUTH: entity.changePositionRotationSpeed(new BlockPos(0, 0, -1), false, 0, entity.rotationYaw, true, 0, false); break;
				}
				break;
			case SOUTH:
				switch (entityFacing) {
					case NORTH: entity.changePositionRotationSpeed(new BlockPos(0, 0, 1), false, 0, entity.rotationYaw, true, 0, false);   break;
					case SOUTH: entity.changePositionRotationSpeed(new BlockPos(-1, 1, 0), false, 45, entity.rotationYaw, true, 0, false); break;
				}
				break;
			case WEST:
				switch (entityFacing) {
					case WEST: entity.changePositionRotationSpeed(new BlockPos(1, 0, 0), false, 0, entity.rotationYaw, true, 0, false);   break;
					case EAST: entity.changePositionRotationSpeed(new BlockPos(-1, 1, 0), false, 45, entity.rotationYaw, true, 0, false); break;
				}
				break;
			case EAST:
				switch (entityFacing) {
					case WEST: entity.changePositionRotationSpeed(new BlockPos(-1, 0, 0), false, 0, entity.rotationYaw, true, 0, false); break;
					case EAST: entity.changePositionRotationSpeed(new BlockPos(1, 1, 0), false, 45, entity.rotationYaw, true, 0, false); break;
				}
				break;
		}
	}

	@Override
	public float getInventoryX() {
		return 0.3f;
	}

	@Override
	public float getInventoryY() {
		return 0.55f;
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
