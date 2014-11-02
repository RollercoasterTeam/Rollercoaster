package robomuss.rc.track.piece;

import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.IModelCustom;
import robomuss.rc.block.BlockTrackBase;
import robomuss.rc.block.te.TileEntityTrackBase;
import robomuss.rc.entity.EntityTrainDefault;
import robomuss.rc.track.style.TrackStyle;
import robomuss.rc.util.IInventoryRenderSettings;

//import robomuss.rc.block.te.TileEntityTrack;


public class TrackPieceCorner extends TrackPiece implements IInventoryRenderSettings {
	public static final String partName = "corner";

	public TrackPieceCorner(String unlocalized_name, int crafting_cost) {
		super(unlocalized_name, crafting_cost);
	}
	
	@Override
	public void renderTileEntity(TrackStyle style, TileEntityTrackBase teTrack, World world, int x , int y , int z) {
		rotate(teTrack, world, x, y, z);

//		IModelCustom model = type.getCornerModel();
		IModelCustom model = style.getModel();
//		model.renderAll();
		model.renderPart(partName);
	}

	@Override
	public void moveTrain(BlockTrackBase track, EntityTrainDefault entity, TileEntityTrackBase teTrack) {
		if (teTrack != null) {
			int trackMeta = teTrack.getWorldObj().getBlockMetadata(teTrack.xCoord, teTrack.yCoord, teTrack.zCoord);
			int heading = MathHelper.floor_double((entity.rotationYaw * 4.0f / 360.0f) + 0.5d) & 3;
			int facing = heading == 0 ? 3 : heading == 1 ? 4 : heading == 2 ? 2 : heading == 3 ? 5 : 2;

			switch (heading) {
				case 0: facing = 3; break;
				case 1: facing = 4; break;
				case 2: facing = 2; break;
				case 3: facing = 5; break;
			}

			switch (trackMeta) {
				case 2:
					if (facing == 4) {
						entity.changePositionRotationSpeed(1f, 0f, -0.5f, false, entity.rotationPitch, 0f, true, 0, false);
					} else if (facing == 5) {
						entity.changePositionRotationSpeed(-0.5f, 0f, 1f, false, entity.rotationPitch, 0f, true, 0, false);
					}
					break;
				case 3:
					if (facing == 2) {
						entity.changePositionRotationSpeed(-1f, 0f, 0.5f, false, entity.rotationPitch, 180f, true, 0, false);
					} else if (facing == 3) {
						entity.changePositionRotationSpeed(0.5f, 0f, -1f, false, entity.rotationPitch, 270f, true, 0, false);
					}
					break;
				case 4:
					if (facing == 2) {
						entity.changePositionRotationSpeed(1f, 0f, 0.5f, false, entity.rotationPitch, 0f, true, 0, false);
					} else if (facing == 3) {
						entity.changePositionRotationSpeed(-0.5f, 0f, -1f, false, entity.rotationPitch, 270f, true, 0, false);
					}
					break;
				case 5:
					if (facing == 3) {
						entity.changePositionRotationSpeed(0.5f, 0f, 1f, false, entity.rotationPitch, 90f, true, 0, false);
					} else if (facing == 4) {
						entity.changePositionRotationSpeed(-1f, 0f, -0.5f, false, entity.rotationPitch, 180f, true, 0, false);
					}
					break;
			}
		}
//		if(teTrack.direction == ForgeDirection.SOUTH) {
//			if(entity.direction.ordinal() - 2 == 0) {
//				entity.posX -= 1f;
//				entity.posZ += 0.5f;
//				entity.rotationYaw = 180f;
//				entity.direction = ForgeDirection.getOrientation(3 + 2);
//			}
//			if(entity.direction.ordinal() - 2 == 1) {
//				entity.posX += 0.5f;
//				entity.posZ -= 1f;
//				entity.rotationYaw = 270f;
//				entity.direction = ForgeDirection.getOrientation(2 + 2);
//			}
//		}
//		if(teTrack.direction == ForgeDirection.WEST) {
//			if(entity.direction.ordinal() - 2 == 0) {
//				entity.posX += 1f;
//				entity.posZ += 0.5f;
//				entity.rotationYaw = 0f;
//				entity.direction = ForgeDirection.getOrientation(1 + 2);
//			}
//			if(entity.direction.ordinal() - 2 == 3) {
//				entity.posX -= 0.5f;
//				entity.posZ -= 1f;
//				entity.rotationYaw = 270f;
//				entity.direction = ForgeDirection.getOrientation(2 + 2);
//			}
//		}
//		if(teTrack.direction == ForgeDirection.NORTH) {
//			if(entity.direction.ordinal() - 2 == 2) {
//				entity.posX += 1f;
//				entity.posZ -= 0.5f;
//				entity.rotationYaw = 0f;
//				entity.direction = ForgeDirection.getOrientation(1 + 2);
//			}
//			if(entity.direction.ordinal() - 2 == 3) {
//				entity.posX -= 0.5f;
//				entity.posZ += 1f;
//				entity.rotationYaw = 90f;
//				entity.direction = ForgeDirection.getOrientation(2);
//			}
//		}
//		if(teTrack.direction == ForgeDirection.EAST) {
//			if(entity.direction.ordinal() - 2 == 2) {
//				entity.posX -= 1f;
//				entity.posZ -= 0.5f;
//				entity.rotationYaw = 180f;
//				entity.direction = ForgeDirection.getOrientation(3 + 2);
//			}
//
//			if(entity.direction.ordinal() - 2 == 1) {
//				entity.posX += 0.4f;
//				entity.posZ += 1f;
//				entity.rotationYaw = 90f;
//				entity.direction = ForgeDirection.getOrientation(0);
//			}
//		}
	}

	@Override
	public AxisAlignedBB getBlockBounds(IBlockAccess iba, int x, int y, int z) {
		return AxisAlignedBB.getBoundingBox(0, 0, 0, 1, 0.4F, 1);
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
