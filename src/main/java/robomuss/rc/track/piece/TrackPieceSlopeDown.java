package robomuss.rc.track.piece;

import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;
import robomuss.rc.block.BlockTrackBase;
import robomuss.rc.block.te.TileEntityTrackBase;
import robomuss.rc.entity.EntityTrainDefault;
import robomuss.rc.track.style.TrackStyle;
import robomuss.rc.util.IInventoryRenderSettings;

public class TrackPieceSlopeDown extends TrackPiece implements IInventoryRenderSettings {
	public static final String[] partNames = {"horizontal_extended", "horizontal"};
	public BlockTrackBase lonelyTrack;
	private BlockPos partnerPos;

	public TrackPieceSlopeDown(String unlocalized_name, int crafting_cost, int render_stage) {
		super(unlocalized_name, crafting_cost, render_stage);
	}

	public void setPartnerPos(BlockPos partnerPos) {
		this.partnerPos = partnerPos;
	}

	public BlockPos getPartnerPos() {
		return this.partnerPos;
	}

//	@Override
//	public void renderItem(int render_stage, IItemRenderer.ItemRenderType renderType, TrackStyle style, BlockTrackBase track, World world, int x, int y, int z) {
//		IModelCustom model = style.getModel();
//
//		GL11.glTranslatef(getInventoryX(), getInventoryY(), getInventoryZ());
//		GL11.glRotatef(180, 0, 0, 1);
//		GL11.glScalef(0.625f, 0.625f, 0.625f);
//		if (renderType == IItemRenderer.ItemRenderType.EQUIPPED) {
//			GL11.glPushMatrix();
//			GL11.glRotatef(45, 0, 1, 0);
//			GL11.glRotatef(26, 0, 0, 1);
//			GL11.glTranslatef(-9, -13.5f, 0);
//			GL11.glScalef(getInventoryScale(), getInventoryScale(), getInventoryScale());
//			this.render(render_stage, model);
//			GL11.glPopMatrix();
//		} else if (renderType == IItemRenderer.ItemRenderType.EQUIPPED_FIRST_PERSON) {
//			GL11.glPushMatrix();
//			GL11.glRotatef(135, 0, 1, 0);
//			GL11.glRotatef(16, 0, 0, 1);
//			GL11.glScalef(0.7f, 0.7f, 0.7f);
//			GL11.glTranslatef(-30, -23.8f, 2);
//			this.render(render_stage, model);
//			GL11.glPopMatrix();
//		} else if (renderType == IItemRenderer.ItemRenderType.INVENTORY) {
//			GL11.glPushMatrix();
//			RenderHelper.enableGUIStandardItemLighting();
//			GL11.glTranslatef(3, 0, 0.5f);
//			GL11.glRotatef(180, 0, 1, 0);
//			GL11.glScalef(0.7f, 0.7f, 0.7f);
//			this.render(render_stage, model);
//			GL11.glPopMatrix();
//		} else if (renderType == IItemRenderer.ItemRenderType.ENTITY) {
//			GL11.glPushMatrix();
//			this.render(render_stage, model);
//			GL11.glPopMatrix();
//		}
//	}

	@Override
	public void renderTileEntity(int render_stage, TrackStyle style, TileEntityTrackBase teTrack, World world, BlockPos pos) {
//		rotate(teTrack, world, x, y, z);
//		IModelCustom model = style.getModel();
//
//		if (!teTrack.isDummy) {
//			if (render_stage == 0) {                 //render rotated track
//				GL11.glTranslatef(0, 16, 0);
//				GL11.glRotatef(45f, 0f, 0f, 1f);
//				model.renderPart(partNames[0]);
//			}
//
//			if (render_stage == 1) {                //render flat track
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
			case NORTH: return render_stage == 0 ? new BlockPos(x + 0.5f, y + 1.5f, z - 0.5f) : (render_stage == 1 ? new BlockPos(x + 0.5f, y + 2.5f, z - 1.5f) : super.getRenderPos(render_stage, x, y, z, teTrack, world, lx, ly, lz));
			case SOUTH: return render_stage == 0 ? new BlockPos(x + 0.5f, y + 1.5f, z + 1.5f) : (render_stage == 1 ? new BlockPos(x + 0.5f, y + 2.5f, z + 2.5f) : super.getRenderPos(render_stage, x, y, z, teTrack, world, lx, ly, lz));
			case WEST:  return render_stage == 0 ? new BlockPos(x - 0.5f, y + 1.5f, z + 0.5f) : (render_stage == 1 ? new BlockPos(x - 1.5f, y + 2.5f, z + 0.5f) : super.getRenderPos(render_stage, x, y, z, teTrack, world, lx, ly, lz));
			case EAST:  return render_stage == 0 ? new BlockPos(x + 1.5f, y + 1.5f, z + 0.5f) : (render_stage == 1 ? new BlockPos(x + 2.5f, y + 2.5f, z + 0.5f) : super.getRenderPos(render_stage, x, y, z, teTrack, world, lx, ly, lz));
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
		boolean isDummy = (Boolean) teTrack.getWorld().getBlockState(teTrack.getPos()).getValue(BlockTrackBase.DUMMY);

		if(entity.riddenByEntity != null && entity.riddenByEntity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) entity.riddenByEntity;
			player.swingProgressInt = 90;
		}

		switch (trackFacing) {
			case NORTH:
				switch (entityFacing) {
					case NORTH: entity.changePositionRotationSpeed(new BlockPos(0, -2, 3), false, -45, entity.rotationYaw, true, 0.1f, false); break;
					case SOUTH: entity.changePositionRotationSpeed(new BlockPos(0, 1, -3), false, 0, entity.rotationYaw, true, 0, false);      break;
				}
				break;
			case SOUTH:
				switch (entityFacing) {
					case NORTH: entity.changePositionRotationSpeed(new BlockPos(0, 1, 3), false, 0, entity.rotationYaw, true, 0.1f, false);  break;
					case SOUTH: entity.changePositionRotationSpeed(new BlockPos(0, -2, -3), false, -45, entity.rotationYaw, true, 0, false); break;
				}
				break;
			case WEST:
				switch (entityFacing) {
					case WEST: entity.changePositionRotationSpeed(new BlockPos(3, -2, 0), false, -45, entity.rotationYaw, true, 0.1f, false); break;
					case EAST: entity.changePositionRotationSpeed(new BlockPos(-3, 1, 0), false, 0, entity.rotationYaw, true, 0, false);      break;
				}
				break;
			case EAST:
				switch (entityFacing) {
					case WEST: entity.changePositionRotationSpeed(new BlockPos(3, 1, 0), false, 0, entity.rotationYaw, true, 0.1f, false);  break;
					case EAST: entity.changePositionRotationSpeed(new BlockPos(-3, -2, 0), false, -45, entity.rotationYaw, true, 0, false); break;
				}
				break;
		}
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
