package robomuss.rc.track.piece;

import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import robomuss.rc.block.te.TileEntityTrackBase;
import robomuss.rc.track.style.TrackStyle;
import robomuss.rc.util.IInventoryRenderSettings;

public class TrackPieceHeartlineRoll extends TrackPiece implements IInventoryRenderSettings {
	public static final String partName = "heartline_roll";

	public TrackPieceHeartlineRoll(String unlocalized_name, int crafting_cost, int render_stage) {
		super(unlocalized_name, crafting_cost, render_stage);
	}

//	@Override
//	public void renderItem(int render_stage, IItemRenderer.ItemRenderType render_type, TrackStyle style, BlockTrackBase blockTrack, World world, int x , int y , int z) {
//		IModelCustom model = style.getModel();
//
//		GL11.glPushMatrix();
//		RenderHelper.enableGUIStandardItemLighting();
//		model.renderPart(partName);
//		GL11.glPopMatrix();
//	}

	@Override
	public void renderTileEntity(int render_stage, TrackStyle style, TileEntityTrackBase teTrack, World world, BlockPos pos) {
//		rotate(teTrack, world, pos);
//		GL11.glPushMatrix();
//		if (true) {
//			GL11.glTranslatef(0f, -8f, 0f);
//		} else {
//			GL11.glTranslatef(24f, -8f, 0f);
//		}
//		GL11.glScalef(1f, 1f, 1f);
//		GL11.glPushMatrix();
//		model.renderPart(partName);
//		GL11.glPopMatrix();
//		GL11.glPopMatrix();
	}

	@Override
	public BlockPos getRenderPos(int render_stage, double x, double y, double z, TileEntityTrackBase teTrack, World world, int lx, int ly, int lz) {
		if (teTrack != null && teTrack.track != null) {
			return new BlockPos(x + 0.5f, y + 1, z + 0.5f);
		}

		return new BlockPos(x, y, z);
	}
	
	@Override
	public AxisAlignedBB getRenderBoundingBox(World world, BlockPos pos) {
		return AxisAlignedBB.fromBounds(pos.getX(), pos.getY(), pos.getZ(), pos.east(10).getX(), pos.up(3).getY(), pos.south(10).getZ());
	}


	@Override
	public float getInventoryX() {
		return 0;
	}


	@Override
	public float getInventoryY() {
		return 0;
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
		return 0;
	}

	@Override
	public boolean useIcon() {
		return true;
	}
}
