package robomuss.rc.entity;

import java.util.UUID;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class EntityBalloon extends Entity {
	private boolean isCurrentlyHeld;
	private EntityLivingBase holder;
	private String entityName;
	private int colorIndex;
	private double posX;
	private double posY;
	private double posZ;
	private double velocityX;
	private double velocityY;
	private double velocityZ;
	private double yaw;
	private double pitch;
	private double roll;
	protected boolean canBePushed = true;

	public EntityBalloon(World world) {
		super(world);
		this.preventEntitySpawning = true;
		this.setSize(1.0f, 1.0f);
	}

	@Override
	protected void entityInit() {}

	@Override
	protected void readEntityFromNBT(NBTTagCompound compound) {
		this.isCurrentlyHeld = compound.getBoolean("held");
//		this.holder = !this.isCurrentlyHeld ? null : this.worldObj.getPlayerEntityByName(compound.getString("holder"));
		this.holder = this.worldObj.func_152378_a(UUID.fromString(compound.getString("holder")));
		this.entityName = compound.getString("name");
		this.colorIndex = compound.getInteger("colorIndex");
		this.posX = compound.getDouble("posX");
		this.posY = compound.getDouble("posY");
		this.posZ = compound.getDouble("posZ");
		this.velocityX = compound.getDouble("velocityX");
		this.velocityY = compound.getDouble("velocityY");
		this.velocityZ = compound.getDouble("velocityZ");
		this.yaw = compound.getDouble("yaw");
		this.pitch = compound.getDouble("pitch");
		this.roll = compound.getDouble("roll");
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound compound) {
		compound.setBoolean("held", this.isCurrentlyHeld);
		compound.setString("holder", this.isCurrentlyHeld ? (this.holder != null ? this.holder.getCommandSenderName() : null) : null);
		compound.setString("name", this.entityName);
		compound.setInteger("colorIndex", this.colorIndex);
		compound.setDouble("posX", this.posX);
		compound.setDouble("posY", this.posY);
		compound.setDouble("posZ", this.posZ);
		compound.setDouble("velocityX", this.velocityX);
		compound.setDouble("velocityY", this.velocityY);
		compound.setDouble("velocityZ", this.velocityZ);
		compound.setDouble("yaw", this.yaw);
		compound.setDouble("pitch", this.pitch);
		compound.setDouble("roll", this.roll);
	}

	public void setCurrentlyHeld(boolean currentlyHeld) {
		this.isCurrentlyHeld = currentlyHeld;
	}

	public boolean getCurrentlyHeld() {
		return this.isCurrentlyHeld;
	}

	public EntityLivingBase getHolder() {
		return this.holder;
	}

	public void setHolder(EntityLivingBase holder) {
		this.holder = holder;
		setCurrentlyHeld(holder != null);
	}
}
