package robomuss.rc.track.piece;

import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.IModelCustom;
import net.minecraftforge.common.util.ForgeDirection;
import robomuss.rc.block.BlockTrackBase;
//import robomuss.rc.block.te.TileEntityTrack;
import robomuss.rc.block.te.TileEntityTrackBase;
import robomuss.rc.entity.EntityTrainDefault;
import robomuss.rc.track.style.TrackStyle;
import robomuss.rc.util.IInventoryRenderSettings;


public class TrackPieceCorner extends TrackPiece implements IInventoryRenderSettings {

	public TrackPieceCorner(String unlocalized_name, int crafting_cost) {
		super(unlocalized_name, crafting_cost);
	}
	
	@Override
	public void renderTileEntity(TrackStyle type, TileEntityTrackBase teTrack, World world, int x , int y , int z) {
		rotate(teTrack, world, x, y, z);

		IModelCustom model = type.getCornerModel();
		model.renderAll();
	}

	@Override
	public void moveTrain(BlockTrackBase track, EntityTrainDefault entity, TileEntityTrackBase teTrack) {
		if(teTrack.direction == ForgeDirection.SOUTH) {
			if(entity.direction.ordinal() - 2 == 0) {
				entity.posX -= 1f;
				entity.posZ += 0.5f;
				entity.rotationYaw = 180f;
				entity.direction = ForgeDirection.getOrientation(3 + 2);
			}
			if(entity.direction.ordinal() - 2 == 1) {
				entity.posX += 0.5f;
				entity.posZ -= 1f;
				entity.rotationYaw = 270f;
				entity.direction = ForgeDirection.getOrientation(2 + 2);
			}
		}
		if(teTrack.direction == ForgeDirection.WEST) {
			if(entity.direction.ordinal() - 2 == 0) {
				entity.posX += 1f;
				entity.posZ += 0.5f;
				entity.rotationYaw = 0f;
				entity.direction = ForgeDirection.getOrientation(1 + 2);
			}
			if(entity.direction.ordinal() - 2 == 3) {
				entity.posX -= 0.5f;
				entity.posZ -= 1f;
				entity.rotationYaw = 270f;
				entity.direction = ForgeDirection.getOrientation(2 + 2);
			}
		}
		if(teTrack.direction == ForgeDirection.NORTH) {
			if(entity.direction.ordinal() - 2 == 2) {
				entity.posX += 1f;
				entity.posZ -= 0.5f;
				entity.rotationYaw = 0f;
				entity.direction = ForgeDirection.getOrientation(1 + 2);
			}
			if(entity.direction.ordinal() - 2 == 3) {
				entity.posX -= 0.5f;
				entity.posZ += 1f;
				entity.rotationYaw = 90f;
				entity.direction = ForgeDirection.getOrientation(0 + 2);
			}
		}
		if(teTrack.direction == ForgeDirection.EAST) {
			if(entity.direction.ordinal() - 2 == 2) {
				entity.posX -= 1f;
				entity.posZ -= 0.5f;
				entity.rotationYaw = 180f;
				entity.direction = ForgeDirection.getOrientation(3 + 2);
			}

			if(entity.direction.ordinal() - 2 == 1) {
				entity.posX += 0.4f;
				entity.posZ += 1f;
				entity.rotationYaw = 90f;
				entity.direction = ForgeDirection.getOrientation(0);
			}
		}
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
