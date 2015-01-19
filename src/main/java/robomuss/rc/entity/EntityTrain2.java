package robomuss.rc.entity;

import com.sun.org.apache.xml.internal.dtm.ref.DTMDefaultBaseIterators;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import robomuss.rc.block.BlockTrackBase;
import robomuss.rc.block.te.TileEntityTrackBase;
import robomuss.rc.item.RCItems;
import robomuss.rc.track.TrackHandler;
import robomuss.rc.track.TrackManager;

public abstract class EntityTrain2 extends Entity {
	//TODO
	private static final int[][][]                 offsetMatrix     = new int[][][] {
			{{0, 0, -1}, {0, 0, 1}},                //0: North, South
			{{-1, 0, 0}, {1, 0, 0}},               //1: West, East
			{{-1, -1, 0}, {1, 0, 0}},              //2: West Down, East
			{{-1, 0, 0}, {1, -1, 0}},              //3: West, East Down
			{{0, 0, -1}, {0, -1, 1}},               //4: North, South Down
			{{0, -1, -1}, {0, 0, 1}},               //5: North Down, South
			{{0, 0, 1}, {1, 0, 0}},                 //6: South, East
			{{0, 0, 1}, {-1, 0, 0}},               //7: South, West
			{{0, 0, -1}, {-1, 0, 0}},              //8: North, West
			{{0, 0, -1}, {1, 0, 0}}                 //9: North, East
	};
	private static       ITrainCarCollisionHandler collisionHandler = null;
	private String carName;
	private int    turnProgress;
	private double trainCarX;
	private double trainCarY;
	private double trainCarZ;
	private double trainCarYaw;
	private double trainCarPitch;
	@SideOnly(Side.CLIENT)
	private double velocityX;
	@SideOnly(Side.CLIENT)
	private double velocityY;
	@SideOnly(Side.CLIENT)
	private double velocityZ;

	public        boolean canUseTrack                = true;
	public        boolean canBePushed                = true;
	public static float   defaultMaxSpeedAirLateral  = 0.4f;
	public static float   defaultMaxSpeedAirVertical = -1f;
	public static double  defaultDragAir             = 0.94999998807907104d;
	public        float   maxSpeedAirLateral         = defaultMaxSpeedAirLateral;
	public        float   maxSpeedAirVertical        = defaultMaxSpeedAirVertical;
	public        double  dragAir                    = defaultDragAir;

	public EntityTrain2(World world) {
		super(world);
		this.preventEntitySpawning = true;
		this.setSize(0.98f, 0.7f);
		this.yOffset = this.height / 2.0f;
	}

	public EntityTrain2(World world, double prevPosX, double prevPosY, double prevPosZ) {
		this(world);
		this.setPosition(prevPosX, prevPosY, prevPosZ);
		this.motionX = 0d;
		this.motionY = 0d;
		this.motionZ = 0d;
		this.prevPosX = prevPosX;
		this.prevPosY = prevPosY;
		this.prevPosZ = prevPosZ;
	}

	public static EntityTrainDefault2 createTrainCar(World world, double x, double y, double z, int type) {
		return new EntityTrainDefault2(world, x, y, z);
	}

	@Override
	public boolean canTriggerWalking() {
		return false;
	}

	@Override
	public void entityInit() {
		this.dataWatcher.addObject(17, 0);              //rolling amplitude
		this.dataWatcher.addObject(18, 1);              //rolling direction
		this.dataWatcher.addObject(19, 0f);             //damage
		this.dataWatcher.addObject(20, 0);              //display tile id
		this.dataWatcher.addObject(21, 6);              //display tile offset
		this.dataWatcher.addObject(22, (byte) 0);       //has display tile
	}

	/**
	 * Sets the rolling amplitude the train car rolls when being attacked.
	 * @param amplitude
	 */
	public void setRollingAmplitude(int amplitude) {
		this.dataWatcher.updateObject(17, amplitude);
	}

	/**
	 * Gets the rolling amplitude the train car rolls when being attacked.
	 * @return
	 */
	public int getRollingAmplitude() {
		return this.dataWatcher.getWatchableObjectInt(17);
	}

	/**
	 * Sets the rolling direction the train car rolls while being attacked. Can be 1 or -1.
	 * @param direction
	 */
	public void setRollingDirection(int direction) {
		this.dataWatcher.updateObject(18, direction);
	}

	/**
	 * Gets the rolling direction the train car rolls while being attacked. Can be 1 or -1.
	 * @return
	 */
	public int getRollingDirection() {
		return this.dataWatcher.getWatchableObjectInt(18);
	}

	/**
	 * Sets the current amount of damage the train car has taken. Decreases over time. The train car breaks when this is over 40.
	 * @param damage
	 */
	public void setDamage(float damage) {
		this.dataWatcher.updateObject(19, damage);
	}

	/**
	 * Gets the current amount of damage the train car has taken. Decreases over time. The train car breaks when this is over 40.
	 * @return
	 */
	public float getDamage() {
		return this.dataWatcher.getWatchableObjectFloat(19);
	}

	public Block getDefaultDisplayTile() {
		return Blocks.air;
	}

	public int getDefaultDisplayTileData() {
		return 0;
	}

	public int getDefaultDisplayTileOffset() {
		return 6;
	}

	public void setDisplayTile(int id) {
		this.dataWatcher.updateObject(20, id & 65535 | this.getDisplayTileData() << 16);
		this.setHasDisplayTile(true);
	}

	public Block getDisplayTile() {
		if (!this.hasDisplayTile()) {
			return this.getDefaultDisplayTile();
		} else {
			int id = this.dataWatcher.getWatchableObjectInt(20) & 65535;
			return Block.getBlockById(id);
		}
	}

	public void setDisplayTileData(int data) {
		this.dataWatcher.updateObject(20, Block.getIdFromBlock(this.getDisplayTile()) & 65535 | data << 16);
		this.setHasDisplayTile(true);
	}

	public int getDisplayTileData() {
		return !this.hasDisplayTile() ? this.getDefaultDisplayTileData() : this.dataWatcher.getWatchableObjectInt(20) >> 16;
	}

	public void setDisplayTileOffset(int offset) {
		this.dataWatcher.updateObject(21, offset);
		this.setHasDisplayTile(true);
	}

	public int getDisplayTileOffset() {
		return !this.hasDisplayTile() ? this.getDefaultDisplayTileOffset() : this.dataWatcher.getWatchableObjectInt(21);
	}

	public void setHasDisplayTile(boolean hasDisplayTile) {
		this.dataWatcher.updateObject(22, (byte) (hasDisplayTile ? 1 : 0));
	}

	public boolean hasDisplayTile() {
		return this.dataWatcher.getWatchableObjectByte(22) == 1;
	}

	public static void setCollisionHandler(ITrainCarCollisionHandler handler) {
		collisionHandler = handler;
	}

	public static ITrainCarCollisionHandler getCollisionHandler() {
		return collisionHandler;
	}

	@Override
	public AxisAlignedBB getCollisionBox(Entity entity) {
		if (getCollisionHandler() != null) {
			return getCollisionHandler().getCollisionBox(this, entity);
		}

		return entity.canBePushed() ? entity.boundingBox : null;
	}

	@Override
	public AxisAlignedBB getBoundingBox() {
		if (getCollisionHandler() != null) {
			return getCollisionHandler().getBoundingBox(this);
		}

		return null;
	}

	@Override
	public boolean canBePushed() {
		return canBePushed;
	}

	@Override
	public double getMountedYOffset() {
		return (double) this.height * (0.0d - 0.30000001192092896d);
	}

	@Override
	public boolean attackEntityFrom(DamageSource damageSource, float amount) {
		if (!this.worldObj.isRemote && !this.isDead) {
			if (this.isEntityInvulnerable()) {
				return false;
			} else {
				this.setRollingDirection(-this.getRollingDirection());
				this.setRollingAmplitude(10);
				this.setBeenAttacked();
				this.setDamage(this.getDamage() + amount * 10.0f);
				boolean flag = damageSource.getEntity() instanceof EntityPlayer && ((EntityPlayer) damageSource.getEntity()).capabilities.isCreativeMode;

				if (flag || this.getDamage() > 40.0f) {
					if (this.riddenByEntity != null) {
						this.riddenByEntity.mountEntity(this);
					}

					if (flag && !this.hasCustomInventoryName()) {
						this.setDead();
					} else {
						this.killTrainCar(damageSource);
					}
				}

				return true;
			}
		} else {
			return true;
		}
	}

	public void killTrainCar(DamageSource damageSource) {
		this.setDead();
		ItemStack itemStack = new ItemStack(RCItems.train, 1);

		if (this.carName != null) {
			itemStack.setStackDisplayName(this.carName);
		}

		this.entityDropItem(itemStack, 0f);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void performHurtAnimation() {
		this.setRollingDirection(-this.getRollingDirection());
		this.setRollingAmplitude(10);
		this.setDamage(this.getDamage() + this.getDamage() * 10.0f);
	}

	@Override
	public boolean canBeCollidedWith() {
		return !this.isDead;
	}

	@Override
	public void setDead() {
		super.setDead();
	}

	@Override
	public void onUpdate() {

	}

	/**
	 * func_94088_b
	 * @param limit
	 */
	public void updateMotion(double limit) {
		this.motionX = this.motionX < -limit ? -limit : (this.motionX > limit ? limit : motionX);
		this.motionZ = this.motionZ < -limit ? -limit : (this.motionZ > limit ? limit : motionZ);
		double moveY = this.motionY;

		if (getMaxSpeedAirVertical() > 0 && motionY > getMaxSpeedAirVertical()) {
			moveY = getMaxSpeedAirVertical();

			if (Math.abs(motionX) < 0.3f && Math.abs(motionZ) < 0.3f) {
				motionY = moveY = 0.15f;
			}
		}

		if (this.onGround) {
			this.motionX *= 0.5d;
			this.motionY *= 0.5d;
			this.motionZ *= 0.5d;
		}

		this.moveEntity(this.motionX, moveY, this.motionZ);

		if (!this.onGround) {
			this.motionX *= getDragAir();
			this.motionY *= getDragAir();
			this.motionZ *= getDragAir();
		}
	}

	/**
	 * func_145821_a
	 * @param x
	 * @param y
	 * @param z
	 * @param num0
	 * @param motionChange
	 * @param block
	 * @param meta
	 */
	public void moveTrainCar(int x, int y, int z, double num0, double motionChange, Block block, int meta) {
		this.fallDistance = 0f;
		Vec3 startVec = this.getVector3(this.posX, this.posY, this.posZ);
		this.posY = (double) y;

		BlockTrackBase track = (BlockTrackBase) block;
		TileEntityTrackBase teTrack = (TileEntityTrackBase) worldObj.getTileEntity(x, y, z);
		boolean slopeFlag = TrackManager.isSloped(track.track_type);
		int trackMeta = worldObj.getBlockMetadata(teTrack.xCoord, teTrack.yCoord, teTrack.zCoord);

		if (slopeFlag) {
			switch (trackMeta) { //TODO: check these!
				case 2:  this.motionZ -= motionChange; break;
				case 3:  this.motionZ += motionChange; break;
				case 4:  this.motionX -= motionChange; break;
				case 5:  this.motionX += motionChange; break;
				default: this.posY = (double) (y + 1);
			}
		}

		//TODO: final velocity calculations!!!
		//int[][] offsets = offsets to adjacent track blocks, set this before testing!!!!
//		int[][] offsets = offsetMatrix[meta];
		int[][] offsets;

		if (track.track_type == TrackHandler.Types.HORIZONTAL.type) {
			switch (meta) {
				case 2:  offsets = offsetMatrix[0]; break;
				case 3:  offsets = offsetMatrix[0]; break;
				case 4:  offsets = offsetMatrix[1]; break;
				case 5:  offsets = offsetMatrix[1]; break;
				default: offsets = offsetMatrix[0];
			}
		} else if (track.track_type == TrackHandler.Types.SLOPE_UP.type || track.track_type == TrackHandler.Types.SLOPE.type || track.track_type == TrackHandler.Types.SLOPE_DOWN.type) {
			meta = meta >= 11 ? meta - 10 : meta;

			switch (meta) {
				case 2:  offsets = offsetMatrix[4]; break;
				case 3:  offsets = offsetMatrix[5]; break;
				case 4:  offsets = offsetMatrix[3]; break;
				case 5:  offsets = offsetMatrix[2]; break;
				default: offsets = offsetMatrix[2]; break;
			}
		} else if (track.track_type == TrackHandler.Types.CORNER.type) {
			switch (meta) {
				case 2:  offsets = offsetMatrix[6]; break;
				case 3:  offsets = offsetMatrix[8]; break;
				case 4:  offsets = offsetMatrix[9]; break;
				case 5:  offsets = offsetMatrix[7]; break;
				default: offsets = offsetMatrix[6]; break;
			}
		} else {
			offsets = offsetMatrix[2];
		}

		double deltaX = offsets[1][0] - offsets[0][0];
		double deltaZ = offsets[1][2] - offsets[0][2];
		double displacement = Math.sqrt(deltaX * deltaX + deltaZ * deltaZ);
		double acceleration = this.motionX * deltaX + this.motionZ * deltaZ;

		if (acceleration < 0d) {
		  deltaX = -deltaX;
		  deltaZ = -deltaZ;
		}

		double initialVelocity = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);

		if (initialVelocity > 2d) {
		  initialVelocity = 2d;
		}

		this.motionX = initialVelocity * deltaX / displacement;
		this.motionZ = initialVelocity * deltaZ / displacement;

		double riddenByEntityForwardVelocity;
		double invSinRotationYaw;
		double cosRotationYaw;
		double velocitySq;

		if (this.riddenByEntity != null && this.riddenByEntity instanceof EntityLivingBase) {
			riddenByEntityForwardVelocity = (double) ((EntityLivingBase) this.riddenByEntity).moveForward;

			if (riddenByEntityForwardVelocity > 0d) {
				invSinRotationYaw = -Math.sin((double) (this.riddenByEntity.rotationYaw * (float) Math.PI / 180f));
				cosRotationYaw = Math.cos((double) (this.riddenByEntity.rotationYaw * (float) Math.PI / 180f));
				velocitySq = this.motionX * this.motionX + this.motionZ * this.motionZ;

				if (velocitySq < 0.01d) {
					this.motionX += invSinRotationYaw * 0.1d;
					this.motionZ += cosRotationYaw + 0.1d;
				}
			}
		}

		if (shouldDoTrackFunctions()) {
			velocitySq = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);

			if (velocitySq < 0.03d) {
				this.motionX *= 0d;
				this.motionY *= 0d;
				this.motionZ *= 0d;
			} else {
				this.motionX *= 0.5d;
				this.motionY *= 0.5d;
				this.motionZ *= 0.5d;
			}
		}

		velocitySq = 0d;
		double lowerLocX = (double) x + 0.5d + (double) offsets[0][0] * 0.5d;
		double lowerLocZ = (double) z + 0.5d + (double) offsets[0][2] * 0.5d;
		double upperLocX = (double) x + 0.5d + (double) offsets[1][0] * 0.5d;
		double upperLocZ = (double) z + 0.5d + (double) offsets[1][2] * 0.5d;
		deltaX = upperLocX - lowerLocX;
		deltaZ = upperLocZ - lowerLocZ;
		double distanceX;
		double distanceZ;

		if (deltaX == 0d) {
			this.posX = (double) x + 0.5d;
			velocitySq = this.posZ - (double) z;
		} else if (deltaZ == 0d) {
			this.posZ = (double) z + 0.5d;
			velocitySq = this.posX - (double) x;
		} else {
			distanceX = this.posX - lowerLocX;
			distanceZ = this.posZ - lowerLocZ;
			velocitySq = (distanceX * deltaX + distanceZ * deltaZ) * 2d;
		}

		this.posX = lowerLocX + deltaX * velocitySq;
		this.posZ = lowerLocZ + deltaZ * velocitySq;
		this.setPosition(this.posX, this.posY + (double) this.yOffset, this.posZ);

		moveTrainCarOnTrack(x, y, z, num0);

		if (offsets[0][1] != 0 && MathHelper.floor_double(this.posX) - x == offsets[0][0] && MathHelper.floor_double(this.posZ) - z == offsets[0][2]) {
			this.setPosition(this.posX, this.posY + (double) offsets[0][1], this.posZ);
		} else if (offsets[1][1] != 0 && MathHelper.floor_double(this.posX) - x == offsets[1][0] && MathHelper.floor_double(this.posZ) - z == offsets[1][2]) {
			this.setPosition(this.posX, this.posY + (double) offsets[1][1], this.posZ);
		}

		this.applyDrag();
		Vec3 currentVec = this.getVector3(this.posX, this.posY, this.posZ);

		if (currentVec != null && startVec != null) {
			double deltaY = (startVec.yCoord - currentVec.yCoord) * 0.05d;
			initialVelocity = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);

			if (initialVelocity > 0d) {
				this.motionX = this.motionX / initialVelocity * (initialVelocity + deltaY);
				this.motionZ = this.motionZ / initialVelocity * (initialVelocity + deltaY);
			}

			this.setPosition(this.posX, currentVec.yCoord, this.posZ);
		}

		int floorX = MathHelper.floor_double(this.posX);
		int floorZ = MathHelper.floor_double(this.posZ);

		if (floorX != x || floorZ != z) {
			initialVelocity = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
			this.motionX = initialVelocity * (double) (floorX - x);
			this.motionZ = initialVelocity * (double) (floorZ - z);
		}

		if (shouldDoTrackFunctions()) {
			double currentVelocity = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);

			if (currentVelocity > 0.01d) {
				double velocityMod = 0.06d;
				this.motionX += this.motionX / currentVelocity * velocityMod;
				this.motionZ += this.motionZ / currentVelocity * velocityMod;
			} else if (meta == 1) {                                                                             //TODO
				if (this.worldObj.getBlock(x - 1, y, z).isNormalCube()) {                                       //TODO: these values move the cart when on a powered rail with a camouflage adjacent to it
					this.motionX = 0.02d;
				} else if (this.worldObj.getBlock(x + 1, y, z).isNormalCube()) {
					this.motionX = -0.02d;
				}
			} else if (meta == 0) {                                                                             //TODO
				if (this.worldObj.getBlock(x, y, z - 1).isNormalCube()) {                                       //TODO: these values move the cart when on a powered rail with a camouflage adjacent to it
					this.motionZ = 0.02d;
				} else if (this.worldObj.getBlock(x, y, z + 1).isNormalCube()) {
					this.motionZ = -0.02d;
				}
			}
		}
	}

	public void applyDrag() {
		if (this.riddenByEntity != null) {
			this.motionX *= 0.996999979019165d;
			this.motionY *= 0d;
			this.motionZ *= 0.996999979019165d;
		} else {
			this.motionX *= 0.9599999785423279d;
			this.motionY *= 0d;
			this.motionZ *= 0.9599999785423279D;
		}
	}

	/**
	 * func_70495_a
	 * @param posX
	 * @param posY
	 * @param posZ
	 * @param num0
	 * @return
	 */
	@SideOnly(Side.CLIENT)
	public Vec3 getRenderPos(double posX, double posY, double posZ, double num0) {
		int floorX = MathHelper.floor_double(posX);
		int floorY = MathHelper.floor_double(posY);
		int floorZ = MathHelper.floor_double(posZ);

		//TODO: might not have to subtract 1 from floorY
		//this checks if the camouflage 1 below is a track camouflage, (ie. slopes)
		if (TrackManager.isBlockAtCoordsTrack(this.worldObj, floorX, floorY - 1, floorZ)) {
			floorY--;
		}

		Block block = this.worldObj.getBlock(floorX, floorY, floorZ);

		if (!TrackManager.isTrack(block)) {
			return null;
		} else {
			BlockTrackBase track = (BlockTrackBase) block;
			int meta = getTrackMetadata(worldObj, this, floorX, floorY, floorZ);

			posY = (double) floorY;

			if (meta >= 2 && meta <= 5) {
				posY = (double) (floorY + 1);
			}

			//TODO
			int[][] offsets;

			if (track.track_type == TrackHandler.Types.HORIZONTAL.type) {
				switch (meta) {
					case 2:  offsets = offsetMatrix[0]; break;
					case 3:  offsets = offsetMatrix[0]; break;
					case 4:  offsets = offsetMatrix[1]; break;
					case 5:  offsets = offsetMatrix[1]; break;
					default: offsets = offsetMatrix[0];
				}
			} else if (track.track_type == TrackHandler.Types.SLOPE_UP.type || track.track_type == TrackHandler.Types.SLOPE.type || track.track_type == TrackHandler.Types.SLOPE_DOWN.type) {
				meta = meta >= 11 ? meta - 10 : meta;

				switch (meta) {
					case 2:  offsets = offsetMatrix[4]; break;
					case 3:  offsets = offsetMatrix[5]; break;
					case 4:  offsets = offsetMatrix[3]; break;
					case 5:  offsets = offsetMatrix[2]; break;
					default: offsets = offsetMatrix[2]; break;
				}
			} else if (track.track_type == TrackHandler.Types.CORNER.type) {
				switch (meta) {
					case 2:  offsets = offsetMatrix[6]; break;
					case 3:  offsets = offsetMatrix[8]; break;
					case 4:  offsets = offsetMatrix[9]; break;
					case 5:  offsets = offsetMatrix[7]; break;
					default: offsets = offsetMatrix[6]; break;
				}
			} else {
				offsets = offsetMatrix[2];
			}

			double deltaX = (double) (offsets[1][0] - offsets[0][0]);
			double deltaZ = (double) (offsets[1][2] - offsets[0][2]);
			double displacement = Math.sqrt(deltaX * deltaX + deltaZ * deltaZ);
			deltaX /= displacement;
			deltaZ /= displacement;
			posX += deltaX * num0;
			posZ += deltaZ * num0;

			if (offsets[0][1] != 0 && MathHelper.floor_double(posX) - floorX == offsets[0][0] && MathHelper.floor_double(posZ) - floorZ == offsets[0][2]) {
				posY += (double) offsets[0][1];
			} else if (offsets[1][1] != 0 && MathHelper.floor_double(posX) - floorX == offsets[1][0] && MathHelper.floor_double(posZ) - floorZ == offsets[1][2]) {
				posY += (double) offsets[1][1];
			}

			return this.getVector3(posX, posY, posZ);
		}
	}

	/**
	 * func_70489_a
	 * @param posX
	 * @param posY
	 * @param posZ
	 * @return
	 */
	public Vec3 getVector3(double posX, double posY, double posZ) {
		int floorX = MathHelper.floor_double(posX);
		int floorY = MathHelper.floor_double(posY);
		int floorZ = MathHelper.floor_double(posZ);

		//TODO: might not have to subtract 1 from floorY
		//this checks if the camouflage 1 below is a track camouflage, (ie. slopes)
		if (TrackManager.isBlockAtCoordsTrack(this.worldObj, floorX, floorY - 1, floorZ)) {
			floorY--;
		}

		Block block = this.worldObj.getBlock(floorX, floorY, floorZ);

		if (TrackManager.isTrack(block)) {
			BlockTrackBase track = (BlockTrackBase) block;
			int meta = getTrackMetadata(worldObj, this, floorX, floorY, floorZ);
			posY = (double) floorY;

			if (meta >= 2 && meta <= 5) {
				posY = (double) (floorY + 1);
			}

			//TODO
//			int[][] offsets = offsetMatrix[meta];
			int[][] offsets;

			if (track.track_type == TrackHandler.Types.HORIZONTAL.type) {
				switch (meta) {
					case 2:  offsets = offsetMatrix[0]; break;
					case 3:  offsets = offsetMatrix[0]; break;
					case 4:  offsets = offsetMatrix[1]; break;
					case 5:  offsets = offsetMatrix[1]; break;
					default: offsets = offsetMatrix[0];
				}
			} else if (track.track_type == TrackHandler.Types.SLOPE_UP.type || track.track_type == TrackHandler.Types.SLOPE.type || track.track_type == TrackHandler.Types.SLOPE_DOWN.type) {
				meta = meta >= 11 ? meta - 10 : meta;

				switch (meta) {
					case 2:  offsets = offsetMatrix[4]; break;
					case 3:  offsets = offsetMatrix[5]; break;
					case 4:  offsets = offsetMatrix[3]; break;
					case 5:  offsets = offsetMatrix[2]; break;
					default: offsets = offsetMatrix[2]; break;
				}
			} else if (track.track_type == TrackHandler.Types.CORNER.type) {
				switch (meta) {
					case 2:  offsets = offsetMatrix[6]; break;
					case 3:  offsets = offsetMatrix[8]; break;
					case 4:  offsets = offsetMatrix[9]; break;
					case 5:  offsets = offsetMatrix[7]; break;
					default: offsets = offsetMatrix[6]; break;
				}
			} else {
				offsets = offsetMatrix[2];
			}

			double modifier = 0d;
			double minX = (double) floorX + 0.5d + (double) offsets[0][0] * 0.5d;
			double minY = (double) floorY + 0.5d + (double) offsets[0][1] * 0.5d;
			double minZ = (double) floorZ + 0.5d + (double) offsets[0][2] * 0.5d;
			double maxX = (double) floorX + 0.5d + (double) offsets[1][0] * 0.5d;
			double maxY = (double) floorY + 0.5d + (double) offsets[1][1] * 0.5d;
			double maxZ = (double) floorZ + 0.5d + (double) offsets[1][2] * 0.5d;
			double deltaX = maxX - minX;
			double deltaY = (maxY - minY) * 2d;
			double deltaZ = maxZ - minZ;

			if (deltaX == 0d) {
				posX = (double) floorX + 0.5d;
				modifier = posZ - (double) floorZ;
			} else if (deltaZ == 0d) {
				posZ = (double) floorZ + 0.5d;
				modifier = posX - (double) floorX;
			} else {
				double nextPosX = posX - minX;
				double nextPosZ = posZ - minZ;
				modifier = (nextPosX * deltaX + nextPosZ * deltaZ);
			}

			posX = minX + deltaX * modifier;
			posY = minY + deltaY * modifier;
			posZ = minZ + deltaZ * modifier;

			if (deltaY < 0d) {
				posY++;
			}

			if (deltaY > 0d) {
				posY += 0.5d;
			}

			return Vec3.createVectorHelper(posX, posY, posZ);
		} else {
			return null;
		}
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound compound) {
		if (compound.getBoolean("CustomDisplayTile")) {
			this.setDisplayTile(compound.getInteger("DisplayTile"));
			this.setDisplayTileData(compound.getInteger("DisplayData"));
			this.setDisplayTileOffset(compound.getInteger("DisplayOffset"));
		}

		if (compound.hasKey("CustomName", 8) && compound.getString("CustomName").length() > 0) {
			this.carName = compound.getString("CustomName");
		}
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound compound) {
		if (this.hasDisplayTile()) {
			compound.setBoolean("CustomDisplayTile", true);
			compound.setInteger("DisplayTile", this.getDisplayTile().getMaterial() == Material.air ? 0 : Block.getIdFromBlock(this.getDisplayTile()));
			compound.setInteger("DisplayData", this.getDisplayTileData());
			compound.setInteger("DisplayOffset", this.getDisplayTileOffset());
		}

		if (this.carName != null && this.carName.length() > 0) {
			compound.setString("CustomName", this.carName);
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public float getShadowSize() {
		return 0f;
	}

	@Override
	public void applyEntityCollision(Entity entity) {
		//TODO
//		MinecraftForge.EVENT_BUS.post(new MinecartCollisionEvent(this, entity));
		if (getCollisionHandler() != null) {
			getCollisionHandler().onEntityCollision(this, entity);
			return;
		}

		if (!this.worldObj.isRemote) {
			if (entity != this.riddenByEntity) {
				if (entity instanceof EntityLivingBase && !(entity instanceof EntityPlayer) && !(entity instanceof EntityIronGolem) && canBeRidden() && this.motionX * this.motionX + this.motionZ * this.motionZ > 0.01d && this.riddenByEntity == null && entity.ridingEntity == null) {
					entity.mountEntity(this);
				}

				double deltaX = entity.posX - this.posX;
				double deltaZ = entity.posZ - this.posZ;
				double distance = deltaX * deltaX + deltaZ * deltaZ;

				if (distance >= 9.999999747378752E-5d) {
					distance = (double) MathHelper.sqrt_double(distance);
					deltaX /= distance;
					deltaZ /= distance;
					double distFraction = 1d / distance;

					if (distFraction > 1d) {
						distFraction = 1d;
					}

					deltaX *= distFraction;
					deltaZ *= distFraction;
					deltaX *= 0.10000000149011612d;
					deltaZ *= 0.10000000149011612d;
					deltaX *= (double) (1f - this.entityCollisionReduction);
					deltaZ *= (double) (1f - this.entityCollisionReduction);
					deltaX *= 0.5d;
					deltaZ *= 0.5d;

					if (entity instanceof EntityTrain2) {
						double entityDistX = entity.posX - this.posX;
						double entityDistZ = entity.posZ - this.posZ;
						Vec3 entityDistVec = Vec3.createVectorHelper(entityDistX, 0d, entityDistZ).normalize();
						Vec3 lookVec = Vec3.createVectorHelper((double) MathHelper.cos(this.rotationYaw * (float) Math.PI / 180f), 0d, (double) MathHelper.sin(this.rotationYaw * (float) Math.PI / 180f)).normalize();
						double dotProduct = Math.abs(entityDistVec.dotProduct(lookVec));

						if (dotProduct < 0.800000011920929d) {
							return;
						}

						double deltaMX = entity.motionX + this.motionX;
						double deltaMZ = entity.motionZ + this.motionZ;

						if (((EntityTrain2) entity).isPoweredCar() && !isPoweredCar()) {
							this.motionX *= 0.20000000298023224d;
							this.motionZ *= 0.20000000298023224d;
							this.addVelocity(entity.motionX - deltaX, 0d, entity.motionZ - deltaZ);
							entity.motionX *= 0.949999988079071d;
							entity.motionZ *= 0.949999988079071d;
						} else if (((EntityTrain2) entity).isPoweredCar() && !isPoweredCar()) {
							this.motionX *= 0.20000000298023224d;
							this.motionZ *= 0.20000000298023224d;
							this.addVelocity(this.motionX + deltaX, 0d, this.motionZ - deltaZ);
							entity.motionX *= 0.949999988079071d;
							entity.motionZ *= 0.949999988079071d;
						} else {
							deltaMX /= 2d;
							deltaMZ /= 2d;
							this.motionX *= 0.20000000298023224d;
							this.motionZ *= 0.20000000298023224d;
							this.addVelocity(deltaMX - deltaX, 0d, deltaMZ - deltaZ);
							entity.motionX *= 0.20000000298023224d;
							entity.motionZ *= 0.20000000298023224d;
							entity.addVelocity(deltaMX + deltaX, 0d, deltaMZ + deltaZ);
						}
					} else {
						this.addVelocity(-deltaX, 0d, -deltaZ);
						entity.addVelocity(deltaX / 4d, 0d, deltaZ / 4d);
					}
				}
			}
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void setPositionAndRotation2(double posX, double posY, double posZ, float yaw, float pitch, int turnProgress) {
		this.trainCarX = posX;
		this.trainCarY = posY;
		this.trainCarZ = posZ;
		this.trainCarYaw = (double) yaw;
		this.trainCarPitch = (double) pitch;
		this.turnProgress = turnProgress + 2;
		this.motionX = this.velocityX;
		this.motionY = this.velocityY;
		this.motionZ = this.velocityZ;
	}

	@SideOnly(Side.CLIENT)
	public void setVelocity(double velocityX, double velocityY, double velocityZ) {
		this.velocityX = this.motionX = velocityX;
		this.velocityY = this.motionY = velocityY;
		this.velocityZ = this.motionZ = velocityZ;
	}

	public abstract int getTrainCarType();

	public void setTrainCarName(String name) {
		this.carName = name;
	}

	public String getTrainCarName() {
		return this.carName;
	}

	@Override
	public String getCommandSenderName() {
		return this.carName != null ? this.carName : super.getCommandSenderName();
	}

	public boolean hasCustomInventoryName() {
		return this.carName != null;
	}

	public void moveTrainCarOnTrack(int x, int y, int z, double limit) {
		double currentMX = this.motionX;
		double currentMZ = this.motionZ;

		if (this.riddenByEntity != null) {
			currentMX *= 0.75d;
			currentMZ *= 0.75d;
		}

		if (currentMX < -limit) {
			currentMX = -limit;
		}

		if (currentMX > limit) {
			currentMX = limit;
		}

		if (currentMZ < -limit) {
			currentMZ = -limit;
		}

		if (currentMZ > limit) {
			currentMZ = limit;
		}

		this.moveEntity(currentMX, 0d, currentMZ);
	}

	public ItemStack getTrainCarItem() {
		return new ItemStack(RCItems.train);
	}

	public void setCanUseTrack(boolean canUseTrack) {
		this.canUseTrack = canUseTrack;
	}

	public boolean canUseTrack() {
		return canUseTrack;
	}

	public boolean shouldDoTrackFunctions() {
		return true;
	}

	//TODO
	public boolean isPoweredCar() {
//		return getTrainCarType() == 2;
		return true;
	}

	public boolean canBeRidden() {
		return true;
	}

	public void setMaxSpeedAirLateral(float speed) {
		this.maxSpeedAirLateral = speed;
	}

	public float getMaxSpeedAirLateral() {
		return this.maxSpeedAirLateral;
	}

	public void setMaxSpeedAirVertical(float speed) {
		this.maxSpeedAirVertical = speed;
	}

	public float getMaxSpeedAirVertical() {
		return this.maxSpeedAirVertical;
	}

	public void setDragAir(double dragAir) {
		this.dragAir = dragAir;
	}

	public double getDragAir() {
		return this.dragAir;
	}

	public double getSlopeAdjustment() {
		return 0.0078125d;
	}

	public int getTrackMetadata(IBlockAccess world, EntityTrain2 train, int x, int y, int z) {
		int meta = world.getBlockMetadata(x, y, z);
		return meta;
	}
}
