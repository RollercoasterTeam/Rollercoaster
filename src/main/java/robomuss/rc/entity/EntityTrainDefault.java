package robomuss.rc.entity;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
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
		this.setPosition(posX, posY, posZ);
		this.rotateOnPlace(world.getTileEntity((int) posX, (int) posY, (int) posZ));
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

	private Block blockIn    = worldObj.getBlock((int) posX, (int) posY, (int) posZ);
	private Block blockUnder = worldObj.getBlock((int) posX - 1, (int) posY, (int) posZ);
	private Block blockDir0  = worldObj.getBlock((int) posX - 1, (int) posY - 1, (int) posZ - 2);
	private Block blockDir1  = worldObj.getBlock((int) posX + 1, (int) posY - 1, (int) posZ);
	private Block blockDir2  = worldObj.getBlock((int) posX - 1, (int) posY - 1, (int) posZ + 2);
	private Block blockDir3  = worldObj.getBlock((int) posX - 3, (int) posY - 1, (int) posZ);

	private BlockTrackBase track;
	private BlockTrackBase trackDir0;
	private BlockTrackBase trackDir1;
	private BlockTrackBase trackDir2;
	private BlockTrackBase trackDir3;

	private TileEntityTrackBase teTrack;
	private TileEntityTrackBase teDir0;
	private TileEntityTrackBase teDir1;
	private TileEntityTrackBase teDir2;
	private TileEntityTrackBase teDir3;

	//TODO: check that these values evaluate in the proper way!
    @Override
    public void onUpdate() {
//	    super.onUpdate();

        if (selfPowered) {
            speed = 0.1f;
        }

	    if (TrackManager.isTrack(blockIn)) {
//		    System.out.println("block in is track");
		    this.track = TrackManager.getTrackAtCoords(this.worldObj, (int) posX, (int) posY, (int) posZ);
		    this.teTrack = TrackManager.getTrackTileAtCoords(this.worldObj, (int)posX, (int) posY, (int) posZ);
	    }

	    if (TrackManager.isTrack(blockUnder)) {
		    this.track = TrackManager.getTrackAtCoords(this.worldObj, (int) posX - 1, (int) posY, (int) posZ);
		    this.teTrack = TrackManager.getTrackTileAtCoords(this.worldObj, (int) posX - 1, (int) posY, (int) posZ);
	    }

	    if (TrackManager.isTrack(blockDir0)) {
		    this.trackDir0 = TrackManager.getTrackAtCoords(this.worldObj, (int) posX - 1, (int) posY - 1, (int) posZ - 2);
		    this.teDir0 = TrackManager.getTrackTileAtCoords(this.worldObj, (int) posX - 1, (int) posY - 1, (int) posZ - 2);
	    }

	    if (TrackManager.isTrack(blockDir1)) {
		    this.trackDir0 = TrackManager.getTrackAtCoords(this.worldObj, (int) posX + 1, (int) posY - 1, (int) posZ);
		    this.teDir0 = TrackManager.getTrackTileAtCoords(this.worldObj, (int) posX + 1, (int) posY - 1, (int) posZ);
	    }

	    if (TrackManager.isTrack(blockDir2)) {
		    this.trackDir0 = TrackManager.getTrackAtCoords(this.worldObj, (int) posX - 1, (int) posY - 1, (int) posZ + 2);
		    this.teDir0 = TrackManager.getTrackTileAtCoords(this.worldObj, (int) posX - 1, (int) posY - 1, (int) posZ + 2);
	    }

	    if (TrackManager.isTrack(blockDir3)) {
		    this.trackDir0 = TrackManager.getTrackAtCoords(this.worldObj, (int) posX - 3, (int) posY - 1, (int) posZ);
		    this.teDir0 = TrackManager.getTrackTileAtCoords(this.worldObj, (int) posX - 3, (int) posY - 1, (int) posZ);
	    }

        //altTileEntity = worldObj.getTileEntity((int) posX - 1, (int) posY, (int) posZ);
        if (!firstTick) {
            speed = 0.1f;

	        if (teTrack != null && teTrack.track != null) {
		        rotateOnPlace(teTrack);
	        } else if (teTrack != null) {
		        rotateOnPlace(teTrack);
	        } else {
		        rotateOnPlace(worldObj.getTileEntity((int) posX, (int) posY, (int) posZ));
//		        rotateOnPlace(worldObj.getTileEntity((int) posX - 1, (int) posY, (int) posZ));
	        }

            firstTick = true;
            this.setPosition(this.posX, this.posY, this.posZ);
        }

	    if (firstTick) {
		    if (teTrack != null && teTrack.extra != null) {
			    if (teTrack.track != null) {
				    teTrack.extra.applyEffectToTrain(teTrack.track, this);
			    } else if (track != null) {
				    teTrack.extra.applyEffectToTrain(track, this);
				}
		    }

		    if (selfPowered || speed > 0) {
			    if (teTrack != null) {
				    if (teTrack.track != null && teTrack.track.track_type != null) {
					    teTrack.track.track_type.moveTrain(teTrack.track, this, teTrack);
				    } else if (track != null && track.track_type != null) {
					    track.track_type.moveTrain(track, this, teTrack);
				    }
			    } else {
				    if (teDir0 != null) {
					    if (teDir0.track != null) {
						    if (teDir0.track.track_type == TrackHandler.Types.SLOPE_DOWN.type) {
							    teDir0.track.track_type.moveTrain(teDir0.track, this, teDir0);
						    }
					    } else if (trackDir0 != null && trackDir0.track_type == TrackHandler.Types.SLOPE_DOWN.type) {
						    trackDir0.track_type.moveTrain(trackDir0, this, teDir0);
					    }
				    }

					if (teDir1 != null) {
						if (teDir1.track != null) {
							if (teDir1.track.track_type == TrackHandler.Types.SLOPE_DOWN.type) {
								teDir1.track.track_type.moveTrain(teDir1.track, this, teDir1);
							}
						} else if (trackDir1 != null && trackDir1.track_type == TrackHandler.Types.SLOPE_DOWN.type) {
							trackDir1.track_type.moveTrain(trackDir1, this, teDir1);
						}
					}

				    if (teDir2 != null) {
					    if (teDir2.track != null) {
						    if (teDir2.track.track_type == TrackHandler.Types.SLOPE_DOWN.type) {
							    teDir2.track.track_type.moveTrain(teDir2.track, this, teDir2);
						    }
					    } else if (trackDir2 != null && trackDir2.track_type == TrackHandler.Types.SLOPE_DOWN.type) {
						    trackDir2.track_type.moveTrain(trackDir2, this, teDir2);
					    }
				    }

				    if (teDir3 != null) {
					    if (teDir3.track != null) {
						    if (teDir3.track.track_type == TrackHandler.Types.SLOPE_DOWN.type) {
							    teDir3.track.track_type.moveTrain(teDir3.track, this, teDir3);
						    }
					    } else if (trackDir3 != null && trackDir3.track_type == TrackHandler.Types.SLOPE_DOWN.type) {
						    trackDir3.track_type.moveTrain(trackDir3, this, teDir3);
					    }
				    }
			    }

			    speed -= 0.005f;
			    this.setPosition(this.posX, this.posY, this.posZ);

				      /*if(this.riddenByEntity != null && this.riddenByEntity instanceof EntityPlayer) {
					      EntityPlayer player = (EntityPlayer) this.riddenByEntity;
					      player.updateRiderPosition();
				      }*/
		    }
	    }
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
		return TrackHandler.Types.HORIZONTAL.type;
	}

	private void rotateOnPlace(TileEntity te) {
//		TileEntity tileentity = TrackManager.isTrack(block) ? TrackManager.getTileEntityFromTrack((BlockTrackBase) block) : altTileEntity;

//		if (!(worldObj.getTileEntity(trackPos.chunkPosX, trackPos.chunkPosY, trackPos.chunkPosZ) instanceof TileEntityTrackBase)) {
//			TileEntity tileentity = altTileEntity;
//		}
		if (TrackManager.isTrack(te)) {
//		if (tileentity != null & tileentity instanceof TileEntityTrackBase) {
//			TileEntityTrackBase te = (TileEntityTrackBase) tileentity;
			if (getTrackTypeFromTE(te) == TrackHandler.Types.HORIZONTAL.type) {
				int meta = this.worldObj.getBlockMetadata((int) posX, (int) posY, (int) posZ);

				switch (meta) {
					case 2: this.rotationYaw = 90f;  break;
					case 3: this.rotationYaw = 270f; break;
					case 4: this.rotationYaw = 180f; break;
					case 5: this.rotationYaw = 0f;   break;
				}

				double yawWrapped = (double) MathHelper.wrapAngleTo180_float(this.rotationYaw);

				if (yawWrapped < -170.0d || yawWrapped >= 170.0d) {
					this.rotationYaw += 180.0f;
				}

				this.setRotation(this.rotationYaw, this.rotationPitch);

//				this.facing = meta;



//				if (te.direction == ForgeDirection.NORTH) {
//					this.rotationYaw = 90f;
//				} else if (te.direction == ForgeDirection.SOUTH) {
//					this.rotationYaw = 270f;
//				} else if (te.direction == ForgeDirection.WEST) {
//					this.rotationYaw = 180f;
//				} else if (te.direction == ForgeDirection.EAST) {
//					this.rotationYaw = 0f;              //90?
//				}

//				switch (te.direction) {
//					case NORTH: this.direction = ForgeDirection.NORTH; break;
//					case SOUTH: this.direction = ForgeDirection.SOUTH; break;
//					case WEST:  this.direction = ForgeDirection.WEST;  break;
//					case EAST:  this.direction = ForgeDirection.EAST;  break;
//				}
				//                this.direction = te.direction;
				//	            this.direction = ForgeDirection.getOrientation(te.direction.ordinal()).ordinal();
			}
		}
	}

	@Override
	public AxisAlignedBB getBoundingBox() {
		return AxisAlignedBB.getBoundingBox(0, 0, 0, 3, 3, 3);
	}

	public void changePositionRotationSpeed(float posX, float posY, float posZ, boolean setPosition, float rotationPitch, float rotationYaw, boolean setRotation, float speed, boolean setSpeed) {
		if (setPosition) {
			this.posX = posX;
			this.posY = posY;
			this.posZ = posZ;
		} else {
			this.posX += posX;
			this.posY += posY;
			this.posZ += posZ;
		}

		this.setPosition(this.posX, this.posY, this.posZ);

		//TODO: set facing in here too!
		if (setRotation) {
			this.rotationPitch = rotationPitch;
			this.rotationYaw = rotationYaw;
		} else {
			this.rotationPitch += rotationPitch;
			this.rotationYaw += rotationYaw;
		}

		double yawWrapped = (double) MathHelper.wrapAngleTo180_float(this.rotationYaw);

		if (yawWrapped < -170.0d || yawWrapped >= 170.0d) {
			this.rotationYaw += 180.0f;
		}

		this.setRotation(this.rotationYaw, this.rotationPitch);
//		this.setRotation(this.rotationPitch, this.rotationYaw);

		if (setSpeed) {
			this.speed = speed;
		} else {
			this.speed += speed;
		}
	}
}