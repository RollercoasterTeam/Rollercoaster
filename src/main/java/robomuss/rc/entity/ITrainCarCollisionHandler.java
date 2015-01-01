package robomuss.rc.entity;

import net.minecraft.util.AxisAlignedBB;
import net.minecraft.entity.Entity;

public interface ITrainCarCollisionHandler {
	public void onEntityCollision(EntityTrain2 train, Entity other);
	public void onEntityCollision(EntityTrain train, Entity other);
	public AxisAlignedBB getCollisionBox(EntityTrain2 train, Entity other);
	public AxisAlignedBB getCollisionBox(EntityTrain train, Entity other);
	public AxisAlignedBB getTrainCollisionBox(EntityTrain2 train);
	public AxisAlignedBB getTrainCollisionBox(EntityTrain train);
	public AxisAlignedBB getBoundingBox(EntityTrain2 train);
	public AxisAlignedBB getBoundingBox(EntityTrain train);
}
