package robomuss.rc.entity;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import robomuss.rc.block.BlockTrackBase;
import robomuss.rc.block.te.TileEntityTrackBase;
import robomuss.rc.track.TrackHandler;
import robomuss.rc.track.TrackManager;
import robomuss.rc.track.piece.TrackPiece;

public class EntityTrainDefault extends EntityTrain {
	//TODO: I may have broke this with the switch to ForgeDirection, double check.
	//North = 2
	//South = 3
	//West  = 4
	//East  = 5
//	public ForgeDirection direction = ForgeDirection.NORTH;
//	public int facing = 2;

	public EntityTrainDefault(World world) {
		super(world);
	}

	public EntityTrainDefault(World world, double posX, double posY, double posZ) {
		super(world, posX, posY, posZ);
	}

	/**
	 * First layer of player interaction
	 */
	public boolean interactFirst(EntityPlayer player) {
		//TODO
		//if(net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.event.entity.minecart.MinecartInteractEvent(this, p_130002_1_))) return true;
		player.mountEntity(this);
		return true;
	}

	public int getMinecartType() {
		return 0;
	}

	private boolean firstTick = false;
	public boolean selfPowered = true;
	public float speed = 0;
	private TileEntity altTileEntity;

	//TODO: i'm not sure these block locations are correct...
//	private BlockPos posUnder = new BlockPos(posX, posY, posZ).west();
//	private BlockPos posDir0  = new BlockPos(posX, posY, posZ).west().down().north(2);
//	private BlockPos posDir1  = new BlockPos(posX, posY, posZ).east().down();
//	private BlockPos posDir2  = new BlockPos(posX, posY, posZ).west().down().south(2);
//	private BlockPos posDir3  = new BlockPos(posX, posY, posZ).west(3).down();
//
//	private Block blockUnder = worldObj.getBlockState(posUnder).getBlock();           //west
//	private Block blockDir0  = worldObj.getBlockState(posDir0).getBlock();            //west, down, 2 north
//	private Block blockDir1  = worldObj.getBlockState(posDir1).getBlock();            //east, down
//	private Block blockDir2  = worldObj.getBlockState(posDir2).getBlock();            //west, down, 2 south
//	private Block blockDir3  = worldObj.getBlockState(posDir3).getBlock();            //3 west, down
//
//	private BlockTrackBase track;
//	private BlockTrackBase trackDir0;
//	private BlockTrackBase trackDir1;
//	private BlockTrackBase trackDir2;
//	private BlockTrackBase trackDir3;
//
//	private TileEntity tileUnder = worldObj.getTileEntity(posUnder);
//	private TileEntity tileDir0  = worldObj.getTileEntity(posDir0);
//	private TileEntity tileDir1  = worldObj.getTileEntity(posDir1);
//	private TileEntity tileDir2  = worldObj.getTileEntity(posDir2);
//	private TileEntity tileDir3  = worldObj.getTileEntity(posDir3);
//
//	private TileEntityTrackBase teTrack;
//	private TileEntityTrackBase teDir0;
//	private TileEntityTrackBase teDir1;
//	private TileEntityTrackBase teDir2;
//	private TileEntityTrackBase teDir3;

	//TODO: check that these values evaluate in the proper way!
	@Override
	public void onUpdate() {
//		if (selfPowered) {
//			speed = 0.1f;
//		}
//
//		if (TrackManager.isTrack(blockUnder)) {
//			this.track = (BlockTrackBase) blockUnder;
//			this.teTrack = (TileEntityTrackBase) tileUnder;
//		}
//
//		if (TrackManager.isTrack(blockDir0)) {
//			this.trackDir0 = (BlockTrackBase) blockDir0;
//			this.teDir0 = (TileEntityTrackBase) tileDir0;
//		}
//
//		if (TrackManager.isTrack(blockDir1)) {
//			this.trackDir0 = TrackManager.getTrackAtCoords(this.worldObj, (int) posX + 1, (int) posY - 1, (int) posZ);
//			this.teDir0 = TrackManager.getTrackTileAtCoords(this.worldObj, (int) posX + 1, (int) posY - 1, (int) posZ);
//		}
//
//		if (TrackManager.isTrack(blockDir2)) {
//			this.trackDir0 = TrackManager.getTrackAtCoords(this.worldObj, (int) posX - 1, (int) posY - 1, (int) posZ + 2);
//			this.teDir0 = TrackManager.getTrackTileAtCoords(this.worldObj, (int) posX - 1, (int) posY - 1, (int) posZ + 2);
//		}
//
//		if (TrackManager.isTrack(blockDir3)) {
//			this.trackDir0 = TrackManager.getTrackAtCoords(this.worldObj, (int) posX - 3, (int) posY - 1, (int) posZ);
//			this.teDir0 = TrackManager.getTrackTileAtCoords(this.worldObj, (int) posX - 3, (int) posY - 1, (int) posZ);
//		}
//
//		//altTileEntity = worldObj.getTileEntity((int) posX - 1, (int) posY, (int) posZ);
//		if (!firstTick) {
//			speed = 0.1f;
//
//			if (teTrack != null && teTrack.track != null) {
//				rotateOnPlace(teTrack);
//			} else if (teTrack != null) {
//				rotateOnPlace(teTrack);
//			} else {
//				rotateOnPlace(worldObj.getTileEntity((int) posX - 1, (int) posY, (int) posZ));
//			}
//
//			firstTick = true;
//			this.setPosition(this.posX, this.posY, this.posZ);
//		}
//
//		if (firstTick) {
//			if (teTrack != null && teTrack.extra != null) {
//				if (teTrack.track != null) {
//					teTrack.extra.applyEffectToTrain(teTrack.track, this);
//				} else if (track != null) {
//					teTrack.extra.applyEffectToTrain(track, this);
//				}
//			}
//
//			if (selfPowered || speed > 0) {
//				if (teTrack != null) {
//					if (teTrack.track != null && teTrack.track.track_type != null) {
//						teTrack.track.track_type.moveTrain(teTrack.track, this, teTrack);
//				    } else if (track != null && track.track_type != null) {
//					    track.track_type.moveTrain(track, this, teTrack);
//				    }
//			    } else {
//				    if (teDir0 != null) {
//					    if (teDir0.track != null) {
//						    if (teDir0.track.track_type == TrackHandler.findTrackType("slope_down")) {
//							    teDir0.track.track_type.moveTrain(teDir0.track, this, teDir0);
//						    }
//					    } else if (trackDir0 != null && trackDir0.track_type == TrackHandler.findTrackType("slope_down")) {
//						    trackDir0.track_type.moveTrain(trackDir0, this, teDir0);
//					    }
//				    }
//
//					if (teDir1 != null) {
//						if (teDir1.track != null) {
//							if (teDir1.track.track_type == TrackHandler.findTrackType("slope_down")) {
//								teDir1.track.track_type.moveTrain(teDir1.track, this, teDir1);
//							}
//						} else if (trackDir1 != null && trackDir1.track_type == TrackHandler.findTrackType("slope_down")) {
//							trackDir1.track_type.moveTrain(trackDir1, this, teDir1);
//						}
//					}
//
//				    if (teDir2 != null) {
//					    if (teDir2.track != null) {
//						    if (teDir2.track.track_type == TrackHandler.findTrackType("slope_down")) {
//							    teDir2.track.track_type.moveTrain(teDir2.track, this, teDir2);
//						    }
//					    } else if (trackDir2 != null && trackDir2.track_type == TrackHandler.findTrackType("slope_down")) {
//						    trackDir2.track_type.moveTrain(trackDir2, this, teDir2);
//					    }
//				    }
//
//				    if (teDir3 != null) {
//					    if (teDir3.track != null) {
//						    if (teDir3.track.track_type == TrackHandler.findTrackType("slope_down")) {
//							    teDir3.track.track_type.moveTrain(teDir3.track, this, teDir3);
//						    }
//					    } else if (trackDir3 != null && trackDir3.track_type == TrackHandler.findTrackType("slope_down")) {
//						    trackDir3.track_type.moveTrain(trackDir3, this, teDir3);
//					    }
//				    }
//			    }
//
//			    speed -= 0.005f;
//			    this.setPosition(this.posX, this.posY, this.posZ);
//
//				      /*if(this.riddenByEntity != null && this.riddenByEntity instanceof EntityPlayer) {
//					      EntityPlayer player = (EntityPlayer) this.riddenByEntity;
//					      player.updateRiderPosition();
//				      }*/
//		    }
//	    }
    }

	private TrackPiece getTrackTypeFromTE(TileEntity tileentity) {
		TileEntityTrackBase teTrack = (TileEntityTrackBase) tileentity;
		if (teTrack.track != null) {
			return teTrack.track.track_type;
		}
          /*TileEntityTrack te = (TileEntityTrack) tileentity;
          if (te.getBlockType() instanceof BlockTrack) {
              BlockTrack block = (BlockTrack) te.getBlockType();
              return block.track_type;
          }*/
		return TrackHandler.pieces.get(0);
	}

	@Override
	public AxisAlignedBB getBoundingBox() {
		return AxisAlignedBB.fromBounds(-1, -1, -1, 3, 3, 3);
	}

	public void changePositionRotationSpeed(BlockPos pos, boolean setPosition, float rotationPitch, float rotationYaw, boolean setRotation, float speed, boolean setSpeed) {
		if (setPosition) {
			this.posX = pos.getX();
			this.posY = pos.getY();
			this.posZ = pos.getZ();
		} else {
			this.posX += pos.getX();
			this.posY += pos.getY();
			this.posZ += pos.getZ();
		}

		//TODO: set facing in here too!
		if (setRotation) {
			this.rotationPitch = rotationPitch;
			this.rotationYaw = rotationYaw;
		} else {
			this.rotationPitch += rotationPitch;
			this.rotationYaw += rotationYaw;
		}

		if (setSpeed) {
			this.speed = speed;
		} else {
			this.speed += speed;
		}
	}
}