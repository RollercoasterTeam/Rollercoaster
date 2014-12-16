package robomuss.rc.track.piece;

import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.model.IModelCustom;
import org.lwjgl.opengl.GL11;
import robomuss.rc.block.BlockTrackBase;
import robomuss.rc.block.te.TileEntityTrackBase;
import robomuss.rc.track.style.TrackStyle;
import robomuss.rc.util.IInventoryRenderSettings;

//import robomuss.rc.block.te.TileEntityTrack;

public class TrackPieceHeartlineRoll extends TrackPiece implements IInventoryRenderSettings {
	public static final String partName = "heartline_roll";

	public TrackPieceHeartlineRoll(String unlocalized_name, int crafting_cost, int render_stage) {
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
		/*if(render_stage <= 9) {
			GL11.glRotatef(-3f * render_stage, 0, 1, 0);
		}
		else {
			GL11.glRotatef(3f * render_stage, 0, 1, 0);
		}*/
//		GL11.glRotatef(20f * render_stage, 1, 0, 0);
//		IModelCustom model = type.getStandardModel();
		IModelCustom model = style.getModel();
//		model.renderAll();
		GL11.glPushMatrix();
		if (true) {
			GL11.glTranslatef(0f, -8f, 0f);
		} else {
			GL11.glTranslatef(24f, -8f, 0f);
		}
//		GL11.glTranslatef(0f, -8f, 0f);
		GL11.glScalef(1f, 1f, 1f);
		GL11.glPushMatrix();
//		GL11.glScalef(0.5f,0.5f, 0.5f);
		model.renderPart(partName);
		GL11.glPopMatrix();
		GL11.glPopMatrix();
	}
	
	@Override
	public float getX(int render_stage, double x, TileEntityTrackBase teTrack, World world, int lx , int ly , int lz) {
		if (teTrack != null && teTrack.track != null) {
			int meta = world.getBlockMetadata(teTrack.xCoord, teTrack.yCoord, teTrack.zCoord);
//			meta = meta >= 11 ? meta - 10 : meta;

			if (meta == 4 || meta == 5) {
				return (float) (x + 0.5f);
			} else {
				return (float) (x + 0.5f);
			}
		} else {
			return (float) (x + 0.5f);
		}
	}

	@Override
	public float getY(int render_stage, double y, TileEntityTrackBase teTrack, World world, int lx, int ly, int lz) {
		return (float) (y + 1f);
	}
	
	@Override
	public float getZ(int render_stage, double z, TileEntityTrackBase teTrack, World world, int lx , int ly , int lz) {
		if (teTrack != null && teTrack.track != null) {
			int meta = world.getBlockMetadata(teTrack.xCoord, teTrack.yCoord, teTrack.zCoord);
//			meta = meta >= 11 ? meta - 10 : meta;

			if (meta == 2 || meta == 3) {
				return (float) (z + 0.5f);
			} else {
				return (float) (z + 0.5f);
			}
		} else {
			return (float) (z + 0.5f);
		}
	}
	
	@Override
	public AxisAlignedBB getRenderBoundingBox(World world, int xCoord, int yCoord, int zCoord) {
		return AxisAlignedBB.getBoundingBox(xCoord, yCoord, zCoord, xCoord + 10, yCoord + 3, zCoord + 10);
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
