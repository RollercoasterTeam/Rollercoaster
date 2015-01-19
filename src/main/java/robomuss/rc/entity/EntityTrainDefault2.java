package robomuss.rc.entity;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import robomuss.rc.block.BlockTrackBase;
import robomuss.rc.block.te.TileEntityTrackBase;
import robomuss.rc.track.TrackHandler;
import robomuss.rc.track.TrackManager;

public class EntityTrainDefault2 extends EntityTrain2 {
	private boolean firstTick = false;
	public boolean selfPowered = true;
	public float speed = 0;

	public EntityTrainDefault2(World world) {
		super(world);
	}

	public EntityTrainDefault2(World world, double x, double y, double z) {
		super(world, x, y, z);
	}

	@Override
	public void onUpdate() {
//		super.onUpdate();

//		Block blockIn = worldObj.getBlock((int) posX, (int) posY, (int) posZ);
//		Block blockUnder = worldObj.getBlock((int) posX, (int) posY - 1, (int) posZ);

//		TileEntity teIn = worldObj.getTileEntity((int) posX, (int) posY, (int) posZ);
//		TileEntity teUnder = worldObj.getTileEntity((int) posX, (int) posY - 1, (int) posZ);

		BlockTrackBase trackIn = TrackManager.isBlockAtCoordsTrack(this.worldObj, (int) posX, (int) posY, (int) posZ) ? TrackManager.getTrackAtCoords(this.worldObj, (int) posX, (int) posY, (int) posZ) : null;

//		BlockTrackBase trackIn    = TrackManager.isTrack(blockIn) ? (BlockTrackBase) blockIn : null;
//		BlockTrackBase trackUnder = TrackManager.isTrack(blockUnder) ? (BlockTrackBase) blockUnder : null;

//		TileEntityTrackBase teTrackIn = trackIn != null && teIn != null ? (TileEntityTrackBase) teIn : null;
//		TileEntityTrackBase teTrackUnder = trackUnder != null && teUnder != null ? (TileEntityTrackBase) teUnder : null;

		if (selfPowered) {
			speed = 0.1f;
		}

		if (!firstTick) {
			speed = 0.1f;
			rotateOnPlace(this.worldObj.getTileEntity((int) posX, (int) posY, (int) posZ));
			firstTick = true;
			this.setPosition(this.posX, this.posY, this.posZ);
		}

		if (firstTick) {
			if (trackIn != null) {
				TileEntityTrackBase teTrackIn = (TileEntityTrackBase) this.worldObj.getTileEntity((int) posX, (int) posY, (int) posZ);

				if (teTrackIn != null && teTrackIn.extra != null) {
					teTrackIn.extra.applyEffectToTrain(teTrackIn.track != null ? teTrackIn.track : trackIn, this);
				}
			}

			if (selfPowered || speed > 0) {
				if (trackIn != null) {
					TileEntityTrackBase teTrackIn = (TileEntityTrackBase) this.worldObj.getTileEntity((int) posX, (int) posY, (int) posZ);

					if (teTrackIn != null && teTrackIn.track != null && teTrackIn.track.track_type != null) {
						teTrackIn.track.track_type.moveTrain(teTrackIn.track, this, teTrackIn);
					} else if (teTrackIn != null && trackIn.track_type != null) {
						trackIn.track_type.moveTrain(trackIn, this, teTrackIn);
					}
				} else {

				}
			}
		}
	}

	private void rotateOnPlace(TileEntity tileEntity) {
		if (TrackManager.isTrack(tileEntity)) {
			TileEntityTrackBase teTrack = (TileEntityTrackBase) tileEntity;
			BlockTrackBase track = teTrack.track != null ? teTrack.track : (BlockTrackBase) this.worldObj.getBlock(teTrack.xCoord, teTrack.yCoord, teTrack.zCoord);

			if (track.track_type == TrackHandler.Types.HORIZONTAL.type) {
				int meta = this.worldObj.getBlockMetadata((int) posX, (int) posY, (int) posZ);
				meta = meta >= 11 ? meta - 10 : meta;

				switch (meta) {
					case 2: this.rotationYaw = 90f;  break;
					case 3: this.rotationYaw = 270f; break;
					case 4: this.rotationYaw = 180f; break;
					case 5: this.rotationYaw = 0f;   break;
				}

				double yawWrapped = (double) MathHelper.wrapAngleTo180_float(this.rotationYaw);

				if (yawWrapped < -170d || yawWrapped >= 170d) {
					this.rotationYaw += 180f;
				}

				this.setRotation(this.rotationYaw, this.rotationPitch);
			}
		}
	}

	@Override
	public int getTrainCarType() {
		return 0;
	}

	@Override
	public void entityInit() {
		super.entityInit();
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound compound) {
		super.readEntityFromNBT(compound);
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound compound) {
		super.readEntityFromNBT(compound);
	}

	public void changePositionRotationSpeed(float posX, float posY, float posZ, boolean setPosition, float rotationYaw, float rotationPitch, boolean setRotation, float speed, boolean setSpeed) {
		if (setPosition) {
			this.setPosition(posX, posY, posZ);
		} else {
			this.setPosition(this.posX + posX, this.posY + posY, this.posZ + posZ);
		}

		if (setRotation) {
			float yawWrapped = MathHelper.wrapAngleTo180_float(rotationYaw);
			this.setRotation(yawWrapped, rotationPitch);
		} else {
			float yawWrapped = MathHelper.wrapAngleTo180_float(this.rotationYaw + rotationYaw);
			this.setRotation(yawWrapped, rotationPitch);
		}

		if (setSpeed) {
			this.speed = speed;
		} else {
			this.speed += speed;
		}
	}
}
