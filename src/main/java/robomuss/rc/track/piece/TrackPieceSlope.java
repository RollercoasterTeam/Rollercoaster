package robomuss.rc.track.piece;

import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.model.IModelCustom;
import org.lwjgl.opengl.GL11;
import robomuss.rc.block.BlockTrackBase;
import robomuss.rc.block.te.TileEntityTrackBase;
import robomuss.rc.entity.EntityTrainDefault;
import robomuss.rc.track.style.TrackStyle;
import robomuss.rc.util.IInventoryRenderSettings;

//import robomuss.rc.camouflage.te.TileEntityTrack;

public class TrackPieceSlope extends TrackPiece implements IInventoryRenderSettings {
	public static final String partName = "horizontal_large";
	public static final String TYPE_NAME = "slope";

	public TrackPieceSlope(int id, String unlocalized_name, int crafting_cost, int render_stage, int number_of_dummies) {
		super(id, unlocalized_name, crafting_cost, render_stage, number_of_dummies);
	}

	@Override
	public void renderItem(int render_stage, IItemRenderer.ItemRenderType render_type, TrackStyle style, BlockTrackBase blockTrack, World world, int x , int y , int z) {
		IModelCustom model = style.getModel();

		GL11.glTranslatef(getInventoryX(), getInventoryY(), getInventoryZ());
//		GL11.glRotatef(180, 0, 0, 1);
		GL11.glScalef(0.625f, 0.625f, 0.625f);
		if (render_type == IItemRenderer.ItemRenderType.EQUIPPED) {
			GL11.glPushMatrix();
//			GL11.glRotatef(45, 0, 1, 0);
//			GL11.glRotatef(20, 0, 0, -1);
//			GL11.glTranslatef(-9.25f, -13.75f, 0.55f);
			GL11.glTranslatef(-13.5f, 1.5f, -0.5f);
			GL11.glScalef(1.55f, 1.55f, 1.55f);
			this.render(render_stage, model);
			GL11.glPopMatrix();
		} else if (render_type == IItemRenderer.ItemRenderType.EQUIPPED_FIRST_PERSON) {
			GL11.glPushMatrix();
//			GL11.glRotatef(45, 0, -1, 0);
//			GL11.glRotatef(30, 0, 0, 1);
//			GL11.glScalef(getInventoryScale(), getInventoryScale(), getInventoryScale());
			GL11.glScalef(1.5f, 1.5f, 1.5f);
			GL11.glTranslatef(3, 0, 1);
			this.render(render_stage, model);
			GL11.glPopMatrix();
		} else if (render_type == IItemRenderer.ItemRenderType.INVENTORY) {
			GL11.glPushMatrix();
			RenderHelper.enableGUIStandardItemLighting();
			GL11.glTranslatef(0, 8, -8);
			GL11.glScalef(1.5f, 1.5f, 1.5f);
			this.render(render_stage, model);
			GL11.glPopMatrix();
		} else if (render_type == IItemRenderer.ItemRenderType.ENTITY) {
			GL11.glPushMatrix();
			GL11.glTranslatef(-5, 0, 0);
			this.render(render_stage, model);
			GL11.glPopMatrix();
		}
	}

	public void render(int render_stage, IModelCustom model) {
		model.renderPart(partName);
	}

	@Override
	public void renderTileEntity(int render_stage, TrackStyle style, TileEntityTrackBase teTrack, World world, int x , int y , int z) {
		rotate(teTrack, world, x, y, z);

		IModelCustom model = style.getModel();

		if (!teTrack.isDummy) {
			GL11.glRotatef(45f, 0f, 0f, 1f);
			model.renderPart(partName);
		}
	}
	
	@Override
	public float getX(int render_stage, double x, TileEntityTrackBase teTrack, World world, int lx , int ly , int lz) {
		int currentFacing = teTrack.getWorldObj().getBlockMetadata(teTrack.xCoord, teTrack.yCoord, teTrack.zCoord);

		currentFacing = currentFacing > 11 ? currentFacing - 10 : currentFacing;
		switch (currentFacing) {
			case 4:  return (float) (x - 0.5F);
			case 5:  return (float) (x + 1.5F);
			default: return (float) (x + 0.5F);
		}
	}
	
	@Override
	public float getY(int render_stage, double y, TileEntityTrackBase teTrack, World world, int lx , int ly , int lz) {
//        TileEntityTrackBase tileEntityTrackBase = (TileEntityTrackBase) world.getTileEntity(lx, ly, lz);
		return (float) (y + 2F);
	}
	
	@Override
	public float getZ(int render_stage, double z, TileEntityTrackBase teTrack, World world, int lx , int ly , int lz) {
		int currentFacing = teTrack.getWorldObj().getBlockMetadata(teTrack.xCoord, teTrack.yCoord, teTrack.zCoord);

		currentFacing = currentFacing > 11 ? currentFacing - 10 : currentFacing;
		switch (currentFacing) {
			case 2:  return (float) (z - 0.5F);
			case 3:  return (float) (z + 1.5F);
			case 4:  return (float) (z + 0.5F);
			case 5:  return (float) (z + 0.5F);
			default: return (float) z;
		}
	}
	
	@Override
	public AxisAlignedBB getRenderBoundingBox(World world, int xCoord, int yCoord, int zCoord) {
		return AxisAlignedBB.getBoundingBox(xCoord - 1, yCoord, zCoord - 1, xCoord + 2, yCoord + 2, zCoord + 2);
	}
	
	@Override
	public void moveTrain(BlockTrackBase track, EntityTrainDefault entity, TileEntityTrackBase teTrack) {
		int meta = teTrack.getWorldObj().getBlockMetadata(teTrack.xCoord, teTrack.yCoord, teTrack.zCoord);
		int heading = MathHelper.floor_double((entity.rotationYaw * 4.0f / 360.0f) + 0.5d) & 3;
		int facing = heading == 0 ? 3 : heading == 1 ? 4 : heading == 2 ? 2 : heading == 3 ? 5 : 2;

		if(entity.riddenByEntity != null && entity.riddenByEntity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) entity.riddenByEntity;
			player.swingProgressInt = 90;
		}

		switch (meta) {
			case 2:
				if (facing == 2) {
					entity.changePositionRotationSpeed(0, -1, 1, false, 0, entity.rotationYaw, true, 0, false);
				} else if (facing == 3) {
					entity.changePositionRotationSpeed(0, 1, -1, false, 0, entity.rotationYaw, true, 0, false);
				}
				break;
			case 3:
				if (facing == 2) {
					entity.changePositionRotationSpeed(0, 1, 1, false, 0, entity.rotationYaw, true, 0, false);
				} else if (facing == 3) {
					entity.changePositionRotationSpeed(0, -1, -1, false, 0, entity.rotationYaw, true, 0, false);
				}
				break;
			case 4:
				if (facing == 4) {
					entity.changePositionRotationSpeed(1, -1, 0, false, 0, entity.rotationYaw, true, 0, false);
				} else if (facing == 5) {
					entity.changePositionRotationSpeed(-1, 1, 0, false, 0, entity.rotationYaw, true, 0, false);
				}
				break;
			case 5:
				if (facing == 4) {
					entity.changePositionRotationSpeed(1, 1, 0, false, 0, entity.rotationYaw, true, 0, false);
				} else if (facing == 5) {
					entity.changePositionRotationSpeed(-1, -1, 0, false, 0, entity.rotationYaw, true, 0, false);
				}
				break;
		}
	}

	@Override
	public float getInventoryX() {
		return 0.6f;
	}

	@Override
	public float getInventoryY() {
		return 0.45f;
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
