package robomuss.rc.track.piece;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.client.model.IModelCustom;
import org.lwjgl.opengl.GL11;
import robomuss.rc.block.BlockTrackBase;
import robomuss.rc.block.te.TileEntityTrackBase;
import robomuss.rc.entity.EntityTrainDefault;
import robomuss.rc.track.style.TrackStyle;
import robomuss.rc.util.IInventoryRenderSettings;

public class TrackPieceSlopeDown extends TrackPiece implements IInventoryRenderSettings {
	public static final String[] partNames = {"horizontal_extended", "horizontal"};

	public TrackPieceSlopeDown(String unlocalized_name, int crafting_cost, int i) {
		super(unlocalized_name, crafting_cost, i);
	}
	
	@Override
	public void renderSpecialTileEntity(int special_render_stage, TrackStyle style, TileEntityTrackBase teTrack, World world, int x, int y, int z) {
		rotate(teTrack, world, x, y, z);
//		if(renderStage == 0) {
//			IModelCustom model = type.getExtendedModel();
//
//			GL11.glRotatef(45f, 0f, 0f, 1f);
//			model.renderAll();
//		}
//		else if(renderStage == 1) {
//			IModelCustom model = type.getStandardModel();
//
//			model.renderAll();
//		}
//		IModelCustom model = style.getStandardModel();
		IModelCustom model = style.getModel();

//		if (teTrack.getWorldObj().getBlockMetadata(teTrack.xCoord, teTrack.yCoord, teTrack.zCoord) <= 11) {
//			teTrack.isDummy = false;
		if (!teTrack.isDummy) {
			if (special_render_stage == 0) {                 //render rotated track
				GL11.glRotatef(45f, 0f, 0f, 1f);
//			model.renderAll();
				model.renderPart(partNames[0]);
			}

			if (special_render_stage == 1) {                //render flat track
				GL11.glPushMatrix();
//			model.renderAll();
				model.renderPart(partNames[1]);
				GL11.glPopMatrix();
			}
		}
	}
	
	@Override
	public float getSpecialX(int renderStage, double x, TileEntityTrackBase teTrack, World world, int lx , int ly , int lz) {
		int currentFacing = world.getBlockMetadata(teTrack.xCoord, teTrack.yCoord, teTrack.zCoord);

		currentFacing = currentFacing > 11 ? currentFacing - 10 : currentFacing;
		if (renderStage == 0) {
			switch(currentFacing) {
				case 4:  return (float) (x - 0.5F);
				case 5:  return (float) (x + 1.5F);
				default: return (float) (x + 0.5F);
			}
		} else if(renderStage == 1) {
			switch (currentFacing) {
				case 4:  return (float) (x - 1.5F);
				case 5:  return (float) (x + 2.5F);
				default: return super.getSpecialX(renderStage, x, teTrack, world, lx, ly, lz);
			}
		} else {
			return super.getSpecialX(renderStage, x, teTrack, world, lx, ly, lz);
		}
	}
	
	@Override
	public float getSpecialY(int renderStage, double y, TileEntityTrackBase teTrack, World world, int lx , int ly , int lz) {
		if(renderStage == 1) {
			return (float) (y + 2.5F);
		} else {
			return super.getSpecialY(renderStage, y + 1.5f, teTrack, world, lx, ly, lz);
		}
	}
	
	@Override
	public float getSpecialZ(int renderStage, double z, TileEntityTrackBase teTrack, World world, int lx , int ly , int lz) {
		int currentFacing = teTrack.getWorldObj().getBlockMetadata(teTrack.xCoord, teTrack.yCoord, teTrack.zCoord);

		currentFacing = currentFacing > 11 ? currentFacing - 10 : currentFacing;
		if(renderStage == 1) {
			switch (currentFacing) {
				case 2:  return (float) (z - 1.5F);
				case 3:  return (float) (z + 2.5F);
				default: return super.getSpecialZ(renderStage, z, teTrack, world, lx, ly, lz);
			}
		} else {
			switch (currentFacing) {
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
		int heading = MathHelper.floor_double((entity.rotationYaw * 4.0f / 360.0f) + 0.5D) & 3;
		int facing = heading == 0 ? 3 : heading == 1 ? 4 : heading == 2 ? 2 : heading == 3 ? 5 : 2;

		meta = meta > 11 ? meta - 10 : meta;
		if(entity.riddenByEntity != null && entity.riddenByEntity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) entity.riddenByEntity;
			player.swingProgressInt = 90;
		}

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
		}
//		if(te.direction == ForgeDirection.SOUTH) {
//			if(entity.direction.ordinal() - 2 == 0) {
//				entity.posY += 1f;
//				entity.posZ += 3f;
//				entity.rotationPitch = 0f;
//				entity.speed += 0.1f;
//			}
//			if(entity.direction.ordinal() - 2 == 2) {
//				entity.posY -= 2f;
//				entity.posZ -= 3f;
//				entity.rotationPitch = -45f;
//			}
//		}
//		if(te.direction == ForgeDirection.WEST) {
//			if(entity.direction.ordinal() - 2 == 1) {
//				entity.posY -= 2f;
//				entity.posX += 3f;
//				entity.rotationPitch = -45f;
//				entity.speed += 0.1f;
//			}
//			if(entity.direction.ordinal() - 2 == 3) {
//				entity.posY += 1f;
//				entity.posX -= 3f;
//				entity.rotationPitch = 0f;
//			}
//		}
//		if(te.direction == ForgeDirection.NORTH) {
//			if(entity.direction.ordinal() - 2 == 0) {
//				entity.posY -= 2f;
//				entity.posZ += 3f;
//				entity.rotationPitch = -45f;
//				entity.speed += 0.1f;
//			}
//			if(entity.direction.ordinal() - 2 == 2) {
//				entity.posY += 1f;
//				entity.posZ -= 3f;
//				entity.rotationPitch = 0f;
//			}
//		}
//		if(te.direction == ForgeDirection.EAST) {
//			if(entity.direction.ordinal() - 2 == 1) {
//				entity.posY += 1f;
//				entity.posX += 3f;
//				entity.rotationPitch = 0f;
//				entity.speed += 0.1f;
//			}
//			if(entity.direction.ordinal() - 2 == 3) {
//				entity.posY -= 2f;
//				entity.posX -= 3f;
//				entity.rotationPitch = -45f;
//			}
//		}
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
