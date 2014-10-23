package robomuss.rc.entity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
//import robomuss.rc.block.BlockTrack;
import robomuss.rc.block.BlockTrackBase;
//import robomuss.rc.block.te.TileEntityTrack;
import robomuss.rc.block.te.TileEntityTrackBase;
import robomuss.rc.track.TrackHandler;
import robomuss.rc.track.TrackManager;
import robomuss.rc.track.piece.TrackPiece;
import net.minecraft.block.Block;

public class EntityTrainDefault extends EntityTrain {
	//TODO: I may have broke this with the switch to ForgeDirection, double check.
	//North = 0
	//South = 1
	//West  = 2
	//East  = 3
	public ForgeDirection direction = ForgeDirection.NORTH;

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

	//TODO: check that these values evaluate in the proper way!
    @Override
    public void onUpdate() {
        if (selfPowered) {
            speed = 0.1f;
        }
        TileEntity tileentity = worldObj.getTileEntity((int) posX - 1, (int) posY, (int) posZ);

        //altTileEntity = worldObj.getTileEntity((int) posX - 1, (int) posY, (int) posZ);
        if (!firstTick) {
            speed = 0.1f;
            rotateOnPlace((getTrackTypeFromTE(tileentity).block));
            firstTick = true;
            this.setPosition(this.posX, this.posY, this.posZ);
        }
	    if (firstTick) {
		    if (tileentity != null && tileentity instanceof TileEntityTrackBase) {
			    TileEntityTrackBase te = (TileEntityTrackBase) tileentity;
			    if (te.track.extra != null) {
				    te.track.extra.applyEffectToTrain(te.track, this);
			    }
		    }
		    if (selfPowered || (!selfPowered && speed > 0)) {
			    if (tileentity != null && tileentity instanceof TileEntityTrackBase) {
				    BlockTrackBase blockTrack = ((TileEntityTrackBase) tileentity).track;
				    if (blockTrack != null && blockTrack.track_type != null) {
					    blockTrack.track_type.moveTrain(blockTrack, this, (TileEntityTrackBase) tileentity);
				    }
			    }
                      /*else if(altTileEntity != null && altTileEntity instanceof TileEntityTrack) {
					      getTrackTypeFromTE(altTileEntity).moveTrain((TileEntityTrack) altTileEntity, this);
				      }*/
			    else {
				    TileEntity te_direction_0 = worldObj.getTileEntity((int) posX - 1, (int) posY - 1, (int) posZ - 2);
				    if (te_direction_0 != null && te_direction_0 instanceof TileEntityTrackBase) {
					    BlockTrackBase track_direction_0 = ((TileEntityTrackBase) tileentity).track;
					    if (track_direction_0 != null && track_direction_0.track_type != null) {
						    if (track_direction_0.track_type == TrackHandler.findTrackType("slope_down")) {
							    track_direction_0.track_type.moveTrain(track_direction_0, this, (TileEntityTrackBase) tileentity);
						    }
					    }
				    }
//				    TileEntity te_direction_0 = worldObj.getTileEntity((int) posX - 1, (int) posY - 1, (int) posZ - 2);
//				    if ((te_direction_0 != null && te_direction_0 instanceof TileEntityTrack)) {
//					    if (((BlockTrack) te_direction_0.getBlockType()).track_type == TrackHandler.findTrackType("slope_down")) {
//						    getTrackTypeFromTE(te_direction_0).moveTrain((TileEntityTrack) te_direction_0, this);
//					    }
//				    }

				    TileEntity te_direction_1 = worldObj.getTileEntity((int) posX + 1, (int) posY - 1, (int) posZ);
				    if (te_direction_1 != null && te_direction_1 instanceof TileEntityTrackBase) {
					    BlockTrackBase track_direction_1 = ((TileEntityTrackBase) te_direction_1).track;
					    if (track_direction_1 != null && track_direction_1.track_type != null) {
						    if (track_direction_1.track_type == TrackHandler.findTrackType("slope_down")) {
							    track_direction_1.track_type.moveTrain(track_direction_1, this, (TileEntityTrackBase) tileentity);
						    }
					    }
				    }
//				    TileEntity te_direction_1 = worldObj.getTileEntity((int) posX + 1, (int) posY - 1, (int) posZ);
//				    if ((te_direction_1 != null && te_direction_1 instanceof TileEntityTrack)) {
//					    if (((BlockTrack) te_direction_1.getBlockType()).track_type == TrackHandler.findTrackType("slope_down")) {
//						    getTrackTypeFromTE(te_direction_1).moveTrain((TileEntityTrack) te_direction_1, this);
//					    }
//				    }

				    TileEntity te_direction_2 = worldObj.getTileEntity((int) posX + 1, (int) posY - 1, (int) posZ);
				    if (te_direction_2 != null && te_direction_2 instanceof TileEntityTrackBase) {
					    BlockTrackBase track_direction_2 = ((TileEntityTrackBase) te_direction_2).track;
					    if (track_direction_2 != null && track_direction_2.track_type != null) {
						    if (track_direction_2.track_type == TrackHandler.findTrackType("slope_down")) {
							    track_direction_2.track_type.moveTrain(track_direction_2, this, (TileEntityTrackBase) tileentity);
						    }
					    }
				    }
//				    TileEntity te_direction_2 = worldObj.getTileEntity((int) posX - 1, (int) posY - 1, (int) posZ + 2);
//				    if ((te_direction_2 != null && te_direction_2 instanceof TileEntityTrack)) {
//					    if (((BlockTrack) te_direction_2.getBlockType()).track_type == TrackHandler.findTrackType("slope_down")) {
//						    getTrackTypeFromTE(te_direction_2).moveTrain((TileEntityTrack) te_direction_2, this);
//					    }
//				    }

				    TileEntity te_direction_3 = worldObj.getTileEntity((int) posX + 1, (int) posY - 1, (int) posZ);
				    if (te_direction_3 != null && te_direction_3 instanceof TileEntityTrackBase) {
					    BlockTrackBase track_direction_3 = ((TileEntityTrackBase) te_direction_3).track;
					    if (track_direction_3 != null && track_direction_3.track_type != null) {
						    if (track_direction_3.track_type == TrackHandler.findTrackType("slope_down")) {
							    track_direction_3.track_type.moveTrain(track_direction_3, this, (TileEntityTrackBase) tileentity);
						    }
					    }
				    }
//				    TileEntity te_direction_3 = worldObj.getTileEntity((int) posX - 3, (int) posY - 1, (int) posZ);
//				    if ((te_direction_3 != null && te_direction_3 instanceof TileEntityTrack)) {
//					    if (((BlockTrack) te_direction_3.getBlockType()).track_type == TrackHandler.findTrackType("slope_down")) {
//						    getTrackTypeFromTE(te_direction_3).moveTrain((TileEntityTrack) te_direction_3, this);
//					    }
//				    }
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
		return TrackHandler.pieces.get(0);
	}

	private void rotateOnPlace(Block block) {
		TileEntity tileentity = TrackManager.isTrack(block) ? TrackManager.getTileEntityFromTrack((BlockTrackBase) block) : altTileEntity;

//		if (!(worldObj.getTileEntity(trackPos.chunkPosX, trackPos.chunkPosY, trackPos.chunkPosZ) instanceof TileEntityTrackBase)) {
//			TileEntity tileentity = altTileEntity;
//		}

		if (tileentity != null & tileentity instanceof TileEntityTrackBase) {
			TileEntityTrackBase te = (TileEntityTrackBase) tileentity;
			if (getTrackTypeFromTE(tileentity) == TrackHandler.findTrackType("horizontal")) {
				if (te.direction == ForgeDirection.NORTH) {
					this.rotationYaw = 90f;
				} else if (te.direction == ForgeDirection.SOUTH) {
					this.rotationYaw = 270f;
				} else if (te.direction == ForgeDirection.WEST) {
					this.rotationYaw = 180f;
				} else if (te.direction == ForgeDirection.EAST) {
					this.rotationYaw = 0f;              //90?
				}

				switch (te.direction) {
					case NORTH:
						this.direction = ForgeDirection.NORTH;
						break;
					case SOUTH:
						this.direction = ForgeDirection.SOUTH;
						break;
					case WEST:
						this.direction = ForgeDirection.WEST;
						break;
					case EAST:
						this.direction = ForgeDirection.EAST;
						break;
				}
				//                this.direction = te.direction;
				//	            this.direction = ForgeDirection.getOrientation(te.direction.ordinal()).ordinal();
			}
		}
	}

	@Override
	public AxisAlignedBB getBoundingBox() {
		return AxisAlignedBB.getBoundingBox(-1, -1, -1, 3, 3, 3);
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