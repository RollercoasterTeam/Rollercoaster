package robomuss.rc.track.piece;

import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.model.IModelCustom;
import org.lwjgl.opengl.GL11;
import robomuss.rc.block.BlockTrackBase;
import robomuss.rc.block.te.TileEntityTrackBase;
import robomuss.rc.entity.EntityTrainDefault;
import robomuss.rc.track.style.TrackStyle;
import robomuss.rc.util.IInventoryRenderSettings;

//import robomuss.rc.block.te.TileEntityTrack;

public class TrackPieceSlopeUp extends TrackPiece implements IInventoryRenderSettings {
	public static final String[] partNames = {"horizontal", "horizontal"};
	private ChunkPosition partnerPos;
	public BlockTrackBase lonelyTrack;

	public TrackPieceSlopeUp(String unlocalized_name, int crafting_cost, int i) {
		super(unlocalized_name, crafting_cost, i);
	}

	public void setPartnerPos(ChunkPosition partnerPos) {
		this.partnerPos = partnerPos;
	}

	public ChunkPosition getPartnerPos() {
		return this.partnerPos;
	}

	@Override
	public void renderItem(int render_stage, IItemRenderer.ItemRenderType renderType, TrackStyle style, BlockTrackBase track, World world, int x, int y, int z) {
		IModelCustom model = style.getModel();

		GL11.glTranslatef(getInventoryX(), getInventoryY(), getInventoryZ());
		GL11.glRotatef(180f, 0f, 0f, 1f);                       //flip model
		GL11.glScalef(0.625f, 0.625f, 0.625f);
		if (renderType == IItemRenderer.ItemRenderType.EQUIPPED) {
			GL11.glPushMatrix();
			GL11.glRotatef(225, 0, 1, 0);
			GL11.glRotatef(20, 0, 0, 1);
			GL11.glScalef(getInventoryScale(), getInventoryScale(), getInventoryScale());
			GL11.glTranslatef(-9.25f, -16.75f, -0.25f);
			this.render(render_stage, model);
			GL11.glPopMatrix();
		} else if (renderType == IItemRenderer.ItemRenderType.EQUIPPED_FIRST_PERSON) {
			GL11.glPushMatrix();
			GL11.glRotatef(45, 0, -1, 0);
			GL11.glRotatef(30, 0, 0, 1);
			GL11.glScalef(getInventoryScale(), getInventoryScale(), getInventoryScale());
			GL11.glTranslatef(-16, -12, 6.75f);
			this.render(render_stage, model);
			GL11.glPopMatrix();
		} else if (renderType == IItemRenderer.ItemRenderType.INVENTORY) {
			GL11.glPushMatrix();
			RenderHelper.enableGUIStandardItemLighting();
			GL11.glTranslatef(-8, 0, -2);
			GL11.glScalef(0.9f, 0.9f, 0.9f);
			this.render(render_stage, model);
			GL11.glPopMatrix();
		} else if (renderType == IItemRenderer.ItemRenderType.ENTITY) {
			GL11.glPushMatrix();
			GL11.glTranslatef(-16.5f, 0, 0);
			this.render(render_stage, model);
			GL11.glPopMatrix();
		}
	}

	public void render(int render_stage, IModelCustom model) {
		if (render_stage == 0) {
			GL11.glRotatef(45f, 0f, 0f, 1f);
		}

		if (render_stage == 1) {
			GL11.glTranslatef(16.5f, 8f, 0f);
		}

		model.renderPart(partNames[render_stage]);
	}

	@Override
	public void renderTileEntity(int render_stage, TrackStyle style, TileEntityTrackBase teTrack, World world, int x, int y, int z) {
		rotate(teTrack, world, x, y, z);
		
		IModelCustom model = style.getModel();

		if (!teTrack.isDummy) {
			if (render_stage == 0) {                                 //render rotated model
				GL11.glTranslatef(0, 16, 0);
				GL11.glRotatef(45f, 0f, 0f, 1f);
				model.renderPart(partNames[0]);
			}

			if (render_stage == 1) {                                 //render flat model
				GL11.glPushMatrix();
				model.renderPart(partNames[1]);
				GL11.glPopMatrix();
			}
		}
	}
	
	@Override
	public float getX(int render_stage, double x, TileEntityTrackBase teTrack, World world, int lx , int ly , int lz) {
		int meta = teTrack.getWorldObj().getBlockMetadata(teTrack.xCoord, teTrack.yCoord, teTrack.zCoord);
		meta = meta > 11 ? meta - 10 : meta;

		if(render_stage == 0) {
			switch (meta) {
				case 2: return (float) (x + 0.5F);
				case 3: return (float) (x + 0.5F);
				case 4: return (float) (x - 0.5F);
				case 5: return (float) (x + 1.5F);
			}
		} else if(render_stage == 1) {
			switch (meta) {
				case 4:
				case 5:  return (float) (x + 0.5F);
				default: return super.getX(render_stage, x, teTrack, world, lx, ly, lz);
			}
		}

		return super.getX(render_stage, x, teTrack, world, lx, ly, lz);
	}
	
	@Override
	public float getY(int render_stage, double y, TileEntityTrackBase teTrack, World world, int lx , int ly , int lz) {
		if(render_stage == 1) {
			return (float) (y + 1.5F);
		} else {
			return super.getY(render_stage, y + 1.5F, teTrack, world, lx, ly, lz);
		}
	}
	
	@Override
	public float getZ(int render_stage, double z, TileEntityTrackBase teTrack, World world, int lx , int ly , int lz) {
		int meta = teTrack.getWorldObj().getBlockMetadata(teTrack.xCoord, teTrack.yCoord, teTrack.zCoord);
		meta = meta > 11 ? meta - 10 : meta;

		if (render_stage == 1) {
			switch (meta) {
				case 2:
				case 3:  return (float) (z + 0.5F);
				default: return super.getZ(render_stage, z, teTrack, world, lx, ly, lz);
			}
		} else {
			switch (meta) {
				case 2:  return (float) (z - 0.5F);
				case 3:  return (float) (z + 1.5F);
				case 4:  return (float) (z + 0.5F);
				case 5:  return (float) (z + 0.5F);
				default: return (float) (z + 0.5F);
			}
		}
	}
	
	@Override
	public AxisAlignedBB getRenderBoundingBox(World world, int xCoord, int yCoord, int zCoord) {
		return AxisAlignedBB.getBoundingBox(xCoord - 1, yCoord, zCoord - 1, xCoord + 2, yCoord + 2, zCoord + 2);
	}

	@Override
	public void moveTrain(BlockTrackBase track, EntityTrainDefault entity, TileEntityTrackBase teTrack) {
		int meta = teTrack.getWorldObj().getBlockMetadata(teTrack.xCoord, teTrack.yCoord, teTrack.zCoord);
		meta = meta > 11 ? meta - 10 : meta;
		int heading = MathHelper.floor_double((entity.rotationYaw * 4.0f / 360.0f) + 0.5d) & 3;
		int facing = heading == 0 ? 3 : heading == 1 ? 4 : heading == 2 ? 2 : heading == 3 ? 5 : 2;

		//if track facing north, then if train facing north, add 1F to z and y and rotate 45 degrees

		switch (meta) {
			case 2:
				if (facing == 2) {
					entity.changePositionRotationSpeed(0, 1, 1, false, 45, entity.rotationYaw, true, 0, false);
				} else if (facing == 3) {
					entity.changePositionRotationSpeed(0, 0, -1, false, 0, entity.rotationYaw, true, 0, false);
				}
				break;
			case 3:
				if (facing == 2) {
					entity.changePositionRotationSpeed(0, 0, 1, false, 0, entity.rotationYaw, true, 0, false);
				} else if (facing == 3) {
					entity.changePositionRotationSpeed(0, 1, -1, false, 45, entity.rotationYaw, true, 0, false);
				}
				break;
			case 4:
				if (facing == 4) {
					entity.changePositionRotationSpeed(1, 0, 0, false, 0, entity.rotationYaw, true, 0, false);
				} else if (facing == 5) {
					entity.changePositionRotationSpeed(-1, 1, 0, false, 45, entity.rotationYaw, true, 0, false);
				}
				break;
			case 5:
				if (facing == 4) {
					entity.changePositionRotationSpeed(-1, 0, 0, false, 0, entity.rotationYaw, true, 0, false);
				} else if (facing == 5) {
					entity.changePositionRotationSpeed(1, 1, 0, false, 45, entity.rotationYaw, true, 0, false);
				}
				break;
		}
	}

	@Override
	public float getInventoryX() {
		return 0.3f;
	}

	@Override
	public float getInventoryY() {
		return 0.55f;
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
