package robomuss.rc.track.piece;

import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.model.IModelCustom;
import org.lwjgl.opengl.GL11;
import robomuss.rc.block.BlockTrackBase;
import robomuss.rc.block.te.TileEntityTrackBase;
import robomuss.rc.entity.EntityTrainDefault;
import robomuss.rc.track.style.TrackStyle;
import robomuss.rc.util.IInventoryRenderSettings;

public class TrackPieceCorner extends TrackPiece implements IInventoryRenderSettings {
	public static final String partName = "corner";

	public TrackPieceCorner(String unlocalized_name, int crafting_cost, int render_stage) {
		super(unlocalized_name, crafting_cost, render_stage);
	}

	@Override
	public void renderItem(int render_stage, IItemRenderer.ItemRenderType render_type, TrackStyle style, BlockTrackBase blockTrack, World world, int x , int y , int z) {
		IModelCustom model = style.getModel();

		GL11.glTranslatef(getInventoryX(), getInventoryY(), getInventoryZ());
		GL11.glScalef(0.625f, 0.625f, 0.625f);
		if (render_type == IItemRenderer.ItemRenderType.EQUIPPED) {
			GL11.glPushMatrix();
			GL11.glScalef(1.55f, 1.55f, 1.55f);
			GL11.glRotatef(90, 0, 1, 0);
			GL11.glTranslatef(0, 1.15f, -1);
			this.render(render_stage, model);
			GL11.glPopMatrix();
		} else if (render_type == IItemRenderer.ItemRenderType.EQUIPPED_FIRST_PERSON) {
			GL11.glPushMatrix();
			GL11.glScalef(1.5f, 1.5f, 1.5f);
			GL11.glRotatef(90, 0, -1, 0);
			GL11.glTranslatef(0.75f, 0, -2);
			this.render(render_stage, model);
			GL11.glPopMatrix();
		} else if (render_type == IItemRenderer.ItemRenderType.INVENTORY) {
			GL11.glPushMatrix();
			RenderHelper.enableGUIStandardItemLighting();
			GL11.glScalef(1.5f, 1.5f, 1.5f);
			GL11.glRotatef(90, 0, -1, 0);
			this.render(render_stage, model);
			GL11.glPopMatrix();
		} else if (render_type == IItemRenderer.ItemRenderType.ENTITY) {
			GL11.glPushMatrix();
			GL11.glScalef(1.5f, 1.5f, 1.5f);
			this.render(render_stage, model);
			GL11.glPopMatrix();
		}
//		GL11.glPushMatrix();
//		RenderHelper.enableGUIStandardItemLighting();
//		GL11.glRotatef(90, 0, -1, 0);
//		model.renderPart(partName);
//		GL11.glPopMatrix();
	}

	public void render(int render_stage, IModelCustom model) {
		model.renderPart(partName);
	}

	@Override
	public void renderTileEntity(int render_stage, TrackStyle style, TileEntityTrackBase teTrack, World world, int x , int y , int z) {
		rotate(teTrack, world, x, y, z);

		IModelCustom model = style.getModel();
		model.renderPart(partName);
	}

	@Override
	public void moveTrain(BlockTrackBase track, EntityTrainDefault entity, TileEntityTrackBase teTrack) {
		if (teTrack != null) {
			int meta = teTrack.getWorldObj().getBlockMetadata(teTrack.xCoord, teTrack.yCoord, teTrack.zCoord);
			int heading = MathHelper.floor_double((entity.rotationYaw * 4.0f / 360.0f) + 0.5d) & 3;
			int facing = heading == 0 ? 3 : heading == 1 ? 4 : heading == 2 ? 2 : heading == 3 ? 5 : 2;

			switch (heading) {
				case 0: facing = 3; break;
				case 1: facing = 4; break;
				case 2: facing = 2; break;
				case 3: facing = 5; break;
			}

			switch (meta) {
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
