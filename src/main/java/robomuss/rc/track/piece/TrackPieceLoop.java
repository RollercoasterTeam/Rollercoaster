package robomuss.rc.track.piece;

import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.client.IItemRenderer;
<<<<<<< HEAD
=======

>>>>>>> origin/One8PortTake2
import org.lwjgl.opengl.GL11;

import robomuss.rc.block.BlockTrackBase;
import robomuss.rc.block.te.TileEntityTrackBase;
import robomuss.rc.track.style.TrackStyle;
import robomuss.rc.util.IInventoryRenderSettings;

public class TrackPieceLoop extends TrackPiece implements IInventoryRenderSettings {
	public static final String partName = "loop";

	public TrackPieceLoop(String unlocalized_name, int crafting_cost, int render_stage) {
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
//		rotate(teTrack, world, x, y, z);
//
//		if(render_stage == 0) {
//			GL11.glRotatef(5f, 1f, 0f, 0f);
//			GL11.glRotatef(-12f, 0f, 1f, 0f);
//			GL11.glScalef(1.05f, 1.05f, 1.05f);
//			IModelCustom model = style.getModel();
//			model.renderPart(partName);
//		}
	}

	@Override
	public BlockPos getRenderPos(int render_stage, double x, double y, double z, TileEntityTrackBase teTrack, World world, int lx, int ly, int lz) {
		if (render_stage == 0) {
			if (teTrack != null && teTrack.track != null) {
				EnumFacing trackFacing = (EnumFacing) world.getBlockState(teTrack.getPos()).getValue(BlockTrackBase.FACING);

				switch (trackFacing) {
					case NORTH: return new BlockPos(x + 0.52f, y + 1.528f, z + 1);
					case SOUTH: return new BlockPos(x + 0.5f,  y + 1.528f, z - 0.5f);
					case WEST:  return new BlockPos(x + 1.5f,  y + 1.528f, z + 0.5f);
					case EAST:  return new BlockPos(x - 0.5f,  y + 1.528f, z + 0.5f);
				}
			}
		}

		return new BlockPos(x + 0.5f, y + 1.528f, z + 0.5f);
	}

	@Override
	public float getInventoryX() {
		return -0.5f;
	}

	@Override
	public float getInventoryY() {
		return -1.05f;
	}

	@Override
	public float getInventoryZ() {
		return 0;
	}

	@Override
	public float getInventoryScale() {
		return 0.4f;
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
