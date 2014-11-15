package robomuss.rc.track.piece;

import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.IModelCustom;
import org.lwjgl.opengl.GL11;
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
	public void renderItem(TrackStyle style, BlockTrackBase blockTrack, World world, int x , int y , int z) {
		IModelCustom model = style.getModel();

		GL11.glPushMatrix();
		RenderHelper.enableGUIStandardItemLighting();
		model.renderPart(partName);
		GL11.glPopMatrix();
	}

	@Override
	public void renderTileEntity(TrackStyle style, TileEntityTrackBase teTrack, World world, int x , int y , int z) {
		rotate(teTrack, world, x, y, z);

		IModelCustom model = style.getModel();
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
