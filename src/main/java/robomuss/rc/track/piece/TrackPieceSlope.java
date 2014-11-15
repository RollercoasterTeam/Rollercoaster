package robomuss.rc.track.piece;

import net.minecraft.client.renderer.RenderHelper;
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

//import robomuss.rc.block.te.TileEntityTrack;

public class TrackPieceSlope extends TrackPiece implements IInventoryRenderSettings {
	public static final String partName = "horizontal_large";

	public TrackPieceSlope(String unlocalized_name, int crafting_cost) {
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

		if (!teTrack.isDummy) {
			GL11.glRotatef(45f, 0f, 0f, 1f);
			model.renderPart(partName);
		}
	}
	
	@Override
	public float getX(double x, TileEntityTrackBase teTrack, World world, int lx , int ly , int lz) {
		int currentFacing = teTrack.getWorldObj().getBlockMetadata(teTrack.xCoord, teTrack.yCoord, teTrack.zCoord);

		currentFacing = currentFacing > 11 ? currentFacing - 10 : currentFacing;
		switch (currentFacing) {
			case 4:  return (float) (x - 0.5F);
			case 5:  return (float) (x + 1.5F);
			default: return (float) (x + 0.5F);
		}
	}
	
	@Override
	public float getY(double y, TileEntityTrackBase teTrack, World world, int lx , int ly , int lz) {
//        TileEntityTrackBase tileEntityTrackBase = (TileEntityTrackBase) world.getTileEntity(lx, ly, lz);
		return (float) (y + 2F);
	}
	
	@Override
	public float getZ(double z, TileEntityTrackBase teTrack, World world, int lx , int ly , int lz) {
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
	
	/*@Override
	public AxisAlignedBB getBlockBounds(IBlockAccess iba, int x, int y, int z) {
		TileEntityTrack te = (TileEntityTrack) iba.getTileEntity(x, y, z);
		if(te.direction == 0) {
			return AxisAlignedBB.getBoundingBox(0, 0, 0, 1, 1, 2);
		}
		else if(te.direction == 1) {
			return AxisAlignedBB.getBoundingBox(1, 0, 0, -1, 1, 1);
		}
		else if(te.direction == 2) {
			return AxisAlignedBB.getBoundingBox(0, 0, -1, 1, 1, 1);
		}
		else if(te.direction == 3) {
			return AxisAlignedBB.getBoundingBox(0, 0, 0, 2, 1, 1);
		}
		return super.getBlockBounds(iba, x, y, z);
	}*/
	
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
//		if(te.direction == ForgeDirection.SOUTH) {
//			if(entity.direction.ordinal() - 2 == 0) {
//				entity.posY += 1f;
//				entity.posZ += 1f;
//
//			}
//			if(entity.direction.ordinal() - 2 == 2) {
//				entity.posY -= 1f;
//				entity.posZ -= 1f;
//			}
//		}
//		if(te.direction == ForgeDirection.WEST) {
//			if(entity.direction.ordinal() - 2 == 1) {
//				entity.posY -= 1f;
//				entity.posX += 1f;
//			}
//			if(entity.direction.ordinal() - 2 == 3) {
//				entity.posY += 1f;
//				entity.posX -= 1f;
//			}
//		}
//		if(te.direction == ForgeDirection.NORTH) {
//			if(entity.direction.ordinal() - 2 == 2) {
//				entity.posY += 1f;
//				entity.posZ -= 1f;
//			}
//			if(entity.direction.ordinal() - 2 == 0) {
//				entity.posY -= 1f;
//				entity.posZ += 1f;
//			}
//		}
//		if(te.direction == ForgeDirection.EAST) {
//			if(entity.direction.ordinal() - 2 == 1) {
//				entity.posY += 1f;
//				entity.posX += 1f;
//			}
//			if(entity.direction.ordinal() - 2 == 3) {
//				entity.posY -= 1f;
//				entity.posX -= 1f;
//			}
//		}
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
		return 1f;
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
