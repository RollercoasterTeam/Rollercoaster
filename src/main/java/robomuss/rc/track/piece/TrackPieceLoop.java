package robomuss.rc.track.piece;

import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.world.World;
import net.minecraftforge.client.IItemRenderer;

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

	@Override
	public void renderItem(int render_stage, IItemRenderer.ItemRenderType render_type, TrackStyle style, BlockTrackBase blockTrack, World world, int x , int y , int z) {
		IModelCustom model = style.getModel();

		GL11.glPushMatrix();
		RenderHelper.enableGUIStandardItemLighting();
		model.renderPart(partName);
		GL11.glPopMatrix();
	}

	public void render(int render_stage, IModelCustom model) {}

	@Override
	public void renderTileEntity(int render_stage, TrackStyle style, TileEntityTrackBase teTrack, World world, int x, int y, int z) {
		rotate(teTrack, world, x, y, z);

		if(render_stage == 0) {
			GL11.glRotatef(5f, 1f, 0f, 0f);
			GL11.glRotatef(-12f, 0f, 1f, 0f);
			GL11.glScalef(1.05f, 1.05f, 1.05f);
			IModelCustom model = style.getModel();
			model.renderPart(partName);
		}
	}
	
	//TODO: double check this isn't broken
	@Override
	public float getX(int render_stage, double x, TileEntityTrackBase teTrack, World world, int lx , int ly , int lz) {
		if(render_stage == 0) {
			if (teTrack != null && teTrack.track != null) {
				int trackMeta = world.getBlockMetadata(teTrack.xCoord, teTrack.yCoord, teTrack.zCoord);
				switch (trackMeta) {
					case 2:  return (float) (x + 0.52f);
					case 3:  return (float) (x + 0.5f);
					case 4:  return (float) (x + 1.5f);
					case 5:  return (float) (x - 0.5f);
					default: return super.getX(render_stage, x, teTrack, world, lx, ly, lz);
				}
			}
		}
		return (float) (x + 0.5f);
	}

	@Override
	public float getY(int render_stage, double y, TileEntityTrackBase teTrack, World world, int lx, int ly, int lz) {
		return (float) (y + 1.528f);
	}

	//TODO: double check this isn't broken
	@Override
	public float getZ(int render_stage, double z, TileEntityTrackBase teTrack, World world, int lx , int ly , int lz) {
		if(render_stage == 0) {
			if (teTrack != null && teTrack.track != null) {
				int trackMeta = world.getBlockMetadata(teTrack.xCoord, teTrack.yCoord, teTrack.zCoord);
				switch (trackMeta) {
					case 2:  return (float) (z + 1f);
					case 3:  return (float) (z - 0.5f);
					default: return super.getZ(render_stage, z, teTrack, world, lx, ly, lz);
				}
			}
		}
		return (float) (z + 0.5f);
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
