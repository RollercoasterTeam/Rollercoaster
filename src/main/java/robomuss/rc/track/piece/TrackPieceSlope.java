package robomuss.rc.track.piece;

import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.client.model.IModelCustom;

import net.minecraftforge.common.util.ForgeDirection;
import org.lwjgl.opengl.GL11;

import robomuss.rc.block.BlockTrackBase;
//import robomuss.rc.block.te.TileEntityTrack;
import robomuss.rc.block.te.TileEntityTrackBase;
import robomuss.rc.chat.ChatHandler;
import robomuss.rc.entity.EntityTrainDefault;
import robomuss.rc.track.style.TrackStyle;
import robomuss.rc.util.IInventoryRenderSettings;

public class TrackPieceSlope extends TrackPiece implements IInventoryRenderSettings {

	public TrackPieceSlope(String unlocalized_name, int crafting_cost) {
		super(unlocalized_name, crafting_cost);
	}

	@Override
	public void renderTileEntity(TrackStyle style, TileEntityTrackBase track, World world, int x , int y , int z) {
		rotate(track, world, x, y, z);

		IModelCustom model = style.getLargeModel();
		
		GL11.glRotatef(45f, 0f, 0f, 1f);
		model.renderAll();
	}
	
	@Override
	public float getX(double x, TileEntityTrackBase teTrack, World world, int lx , int ly , int lz) {
//        TileEntityTrackBase tileEntityTrackBase = (TileEntityTrackBase) world.getTileEntity(lx, ly, lz);
//		if(te.direction == ForgeDirection.WEST) {
//			return (float) (x - 0.5f);
//		} else if(te.direction == ForgeDirection.EAST) {
//			return (float) (x + 1.5f);
//		} else {
//			return (float) x + 0.5f;
//		}
		switch (teTrack.direction) {
			case WEST:  return (float) (x - 0.5F);
			case EAST:  return (float) (x + 1.5F);
			default:    return (float) (x + 0.5F);
		}
	}
	
	@Override
	public float getY(double y, TileEntityTrackBase teTrack, World world, int lx , int ly , int lz) {
//        TileEntityTrackBase tileEntityTrackBase = (TileEntityTrackBase) world.getTileEntity(lx, ly, lz);
		return (float) (y + 2f);
	}
	
	@Override
	public float getZ(double z, TileEntityTrackBase teTrack, World world, int lx , int ly , int lz) {
//        TileEntityTrackBase tileEntityTrackBase = (TileEntityTrackBase) world.getTileEntity(lx, ly, lz);
//		if(te.direction == ForgeDirection.SOUTH) {
//			return (float) (z + 1.5f);
//		} else if(te.direction == ForgeDirection.WEST) {
//			return (float) (z + 0.5f);
//		} else if(te.direction == ForgeDirection.NORTH) {
//			return (float) (z - 0.5f);
//		} else if(te.direction == ForgeDirection.EAST) {
//			return (float) (z + 0.5f);
//		} else {
//			return (float) z;
//		}
		switch (teTrack.direction) {
			case NORTH: return (float) (z + 1.5F);
			case SOUTH: return (float) (z + 0.5F);
			case WEST:  return (float) (z - 0.5F);
			case EAST:  return (float) (z + 0.5F);
			default:    return (float) z;
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
		if(entity.riddenByEntity != null && entity.riddenByEntity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) entity.riddenByEntity;
			player.swingProgressInt = 90;
		}

		switch (teTrack.direction) {
			case NORTH:
				switch (entity.direction) {
					case NORTH:
						entity.posY -= 1F;
						entity.posZ += 1F;
						break;
					case SOUTH:
						entity.posY += 1F;
						entity.posZ -= 1F;
						break;
				}
				break;
			case SOUTH:
				switch (entity.direction) {
					case NORTH:
						entity.posY += 1F;
						entity.posZ += 1F;
						break;
					case SOUTH:
						entity.posY -= 1F;
						entity.posZ -= 1F;
						break;
				}
				break;
			case WEST:
				switch (entity.direction) {
					case WEST:
						entity.posY -= 1F;
						entity.posX += 1F;
						break;
					case EAST:
						entity.posY += 1F;
						entity.posX -= 1F;
						break;
				}
				break;
			case EAST:
				switch (entity.direction) {
					case WEST:
						entity.posY += 1F;
						entity.posX += 1F;
						break;
					case EAST:
						entity.posY -= 1F;
						entity.posX -= 1F;
						break;
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
