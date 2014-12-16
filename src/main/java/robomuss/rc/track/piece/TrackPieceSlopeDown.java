package robomuss.rc.track.piece;

import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.model.IModelCustom;
<<<<<<< HEAD
<<<<<<< HEAD
=======

import net.minecraftforge.common.util.ForgeDirection;
>>>>>>> master
import org.lwjgl.opengl.GL11;
import robomuss.rc.block.te.TileEntityTrack;
=======
import org.lwjgl.opengl.GL11;
import robomuss.rc.block.BlockTrackBase;
import robomuss.rc.block.te.TileEntityTrackBase;
>>>>>>> master
import robomuss.rc.entity.EntityTrainDefault;
import robomuss.rc.track.style.TrackStyle;
import robomuss.rc.util.IInventoryRenderSettings;

public class TrackPieceSlopeDown extends TrackPiece implements IInventoryRenderSettings {
	public static final String[] partNames = {"horizontal_extended", "horizontal"};
	private ChunkPosition partnerPos;
	public BlockTrackBase lonelyTrack;

	public TrackPieceSlopeDown(String unlocalized_name, int crafting_cost, int render_stage) {
		super(unlocalized_name, crafting_cost, render_stage);
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
		GL11.glRotatef(180, 0, 0, 1);
		GL11.glScalef(0.625f, 0.625f, 0.625f);
		if (renderType == IItemRenderer.ItemRenderType.EQUIPPED) {
			GL11.glPushMatrix();
			GL11.glRotatef(45, 0, 1, 0);
			GL11.glRotatef(26, 0, 0, 1);
			GL11.glTranslatef(-9, -13.5f, 0);
			GL11.glScalef(getInventoryScale(), getInventoryScale(), getInventoryScale());
			this.render(render_stage, model);
			GL11.glPopMatrix();
		} else if (renderType == IItemRenderer.ItemRenderType.EQUIPPED_FIRST_PERSON) {
			GL11.glPushMatrix();
			GL11.glRotatef(135, 0, 1, 0);
			GL11.glRotatef(16, 0, 0, 1);
			GL11.glScalef(0.7f, 0.7f, 0.7f);
			GL11.glTranslatef(-30, -23.8f, 2);
			this.render(render_stage, model);
			GL11.glPopMatrix();
		} else if (renderType == IItemRenderer.ItemRenderType.INVENTORY) {
			GL11.glPushMatrix();
			RenderHelper.enableGUIStandardItemLighting();
			GL11.glTranslatef(3, 0, 0.5f);
			GL11.glRotatef(180, 0, 1, 0);
			GL11.glScalef(0.7f, 0.7f, 0.7f);
			this.render(render_stage, model);
			GL11.glPopMatrix();
		} else if (renderType == IItemRenderer.ItemRenderType.ENTITY) {
			GL11.glPushMatrix();
			this.render(render_stage, model);
			GL11.glPopMatrix();
		}
	}

	public void render(int render_stage, IModelCustom model) {
		if (render_stage == 0) {
			GL11.glRotatef(45, 0, 0, -1);
		}

		if (render_stage == 1) {
			GL11.glTranslatef(17f, -9f, 0f);
		}

		model.renderPart(partNames[render_stage]);
	}

	@Override
<<<<<<< HEAD
	public float getSpecialX(int renderStage, double x, TileEntityTrack te) {
		if(renderStage == 0) {
			switch(te.direction.ordinal() - 2) {
				case 0 : return (float) (x + 0.5F);
				case 1 : return (float) (x - 0.5F);
				case 2 : return (float) (x + 0.5F);
				default: return (float) (x + 1.5F);
			}
		}
		else if(renderStage == 1) {
			switch(te.direction.ordinal() - 2) {
				case 1 : return (float) (x - 1.5F);
				case 3 : return (float) (x + 2.5F);
				default : return super.getSpecialX(renderStage, x, te);
=======
	public void renderTileEntity(int render_stage, TrackStyle style, TileEntityTrackBase teTrack, World world, int x, int y, int z) {
		rotate(teTrack, world, x, y, z);
		IModelCustom model = style.getModel();

		if (!teTrack.isDummy) {
			if (render_stage == 0) {                 //render rotated track
				GL11.glTranslatef(0, 16, 0);
				GL11.glRotatef(45f, 0f, 0f, 1f);
				model.renderPart(partNames[0]);
			}

			if (render_stage == 1) {                //render flat track
				GL11.glPushMatrix();
				model.renderPart(partNames[1]);
				GL11.glPopMatrix();
>>>>>>> master
			}
		}
	}
	
	@Override
	public float getX(int render_stage, double x, TileEntityTrackBase teTrack, World world, int lx , int ly , int lz) {
		int currentFacing = world.getBlockMetadata(teTrack.xCoord, teTrack.yCoord, teTrack.zCoord);
		currentFacing = currentFacing > 11 ? currentFacing - 10 : currentFacing;

		if (render_stage == 0) {
			switch(currentFacing) {
				case 4:  return (float) (x - 0.5F);
				case 5:  return (float) (x + 1.5F);
				default: return (float) (x + 0.5F);
			}
		} else if(render_stage == 1) {
			switch (currentFacing) {
				case 4:  return (float) (x - 1.5F);
				case 5:  return (float) (x + 2.5F);
				default: return super.getX(render_stage, x, teTrack, world, lx, ly, lz);
			}
		} else {
			return super.getX(render_stage, x, teTrack, world, lx, ly, lz);
		}
	}
	
	@Override
	public float getY(int render_stage, double y, TileEntityTrackBase teTrack, World world, int lx , int ly , int lz) {
		if(render_stage == 1) {
			return (float) (y + 2.5F);
		} else {
			return super.getY(render_stage, y + 1.5f, teTrack, world, lx, ly, lz);
		}
	}
	
	@Override
<<<<<<< HEAD
	public float getSpecialZ(int renderStage, double z, TileEntityTrack te) {
		if(renderStage == 1) {
			switch(te.direction.ordinal() - 2) {
				case 0 : return (float) (z + 2.5F);
				case 2 : return (float) (z - 1.5F);
				default : return super.getSpecialZ(renderStage, z, te);
			}
		}
		else {
			switch(te.direction.ordinal() - 2) {
			case 0 : return (float) (z + 1.5F);
			case 1 : return (float) (z + 0.5F);
			case 2 : return (float) (z - 0.5F);
			default: return (float) (z + 0.5F);
		}
=======
	public float getZ(int render_stage, double z, TileEntityTrackBase teTrack, World world, int lx , int ly , int lz) {
		int currentFacing = teTrack.getWorldObj().getBlockMetadata(teTrack.xCoord, teTrack.yCoord, teTrack.zCoord);
		currentFacing = currentFacing > 11 ? currentFacing - 10 : currentFacing;

		if(render_stage == 1) {
			switch (currentFacing) {
				case 2:  return (float) (z - 1.5F);
				case 3:  return (float) (z + 2.5F);
				default: return super.getZ(render_stage, z, teTrack, world, lx, ly, lz);
			}
		} else {
			switch (currentFacing) {
				case 2:  return (float) (z - 0.5F);
				case 3:  return (float) (z + 1.5F);
				case 4:  return (float) (z + 0.5F);
				case 5:  return (float) (z + 0.5F);
				default: return (float) (z + 0.5F);
			}
>>>>>>> master
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
		int heading = MathHelper.floor_double((entity.rotationYaw * 4.0f / 360.0f) + 0.5D) & 3;
		int facing = heading == 0 ? 3 : heading == 1 ? 4 : heading == 2 ? 2 : heading == 3 ? 5 : 2;

		if(entity.riddenByEntity != null && entity.riddenByEntity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) entity.riddenByEntity;
			player.swingProgressInt = 90;
		}
<<<<<<< HEAD
		if(te.direction == ForgeDirection.SOUTH) {
			if(entity.direction.ordinal() - 2 == 0) {
				entity.posY += 1f;
				entity.posZ += 3f;
				entity.rotationPitch = 0f;
				entity.speed += 0.1f;
			}
			if(entity.direction.ordinal() - 2 == 2) {
				entity.posY -= 2f;
				entity.posZ -= 3f;
				entity.rotationPitch = -45f;
			}
		}
		if(te.direction == ForgeDirection.WEST) {
			if(entity.direction.ordinal() - 2 == 1) {
				entity.posY -= 2f;
				entity.posX += 3f;
				entity.rotationPitch = -45f;
				entity.speed += 0.1f;
			}
			if(entity.direction.ordinal() - 2 == 3) {
				entity.posY += 1f;
				entity.posX -= 3f;
				entity.rotationPitch = 0f;
			}
		}
		if(te.direction == ForgeDirection.NORTH) {
			if(entity.direction.ordinal() - 2 == 0) {
				entity.posY -= 2f;
				entity.posZ += 3f;
				entity.rotationPitch = -45f;
				entity.speed += 0.1f;
			}
			if(entity.direction.ordinal() - 2 == 2) {
				entity.posY += 1f;
				entity.posZ -= 3f;
				entity.rotationPitch = 0f;
			}
		}
		if(te.direction == ForgeDirection.EAST) {
			if(entity.direction.ordinal() - 2 == 1) {
				entity.posY += 1f;
				entity.posX += 3f;
				entity.rotationPitch = 0f;
				entity.speed += 0.1f;
			}
			if(entity.direction.ordinal() - 2 == 3) {
				entity.posY -= 2f;
				entity.posX -= 3f;
				entity.rotationPitch = -45f;
			}
=======

		switch (meta) {
			case 2:
				if (facing == 2) {
					entity.changePositionRotationSpeed(0, -2, 3, false, -45, entity.rotationYaw, true, 0.1f, false);
				} else if (facing == 3) {
					entity.changePositionRotationSpeed(0, 1, -3, false, 0, entity.rotationYaw, true, 0, false);
				}
				break;
			case 3:
				if (facing == 2) {
					entity.changePositionRotationSpeed(0, 1, 3, false, 0, entity.rotationYaw, true, 0.1f, false);
				} else if (facing == 3) {
					entity.changePositionRotationSpeed(0, -2, -3, false, -45, entity.rotationYaw, true, 0, false);
				}
				break;
			case 4:
				if (facing == 4) {
					entity.changePositionRotationSpeed(3, -2, 0, false, -45, entity.rotationYaw, true, 0.1f, false);
				} else if (facing == 5) {
					entity.changePositionRotationSpeed(-3, 1, 0, false, 0, entity.rotationYaw, true, 0, false);
				}
				break;
			case 5:
				if (facing == 4) {
					entity.changePositionRotationSpeed(3, 1, 0, false, 0, entity.rotationYaw, true, 0.1F, false);
				} else if (facing == 5) {
					entity.changePositionRotationSpeed(-3, -2, 0, false, -45, entity.rotationYaw, true, 0, false);
				}
				break;
>>>>>>> master
		}
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
