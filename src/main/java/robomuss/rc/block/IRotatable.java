package robomuss.rc.block;

import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public interface IRotatable {
	void updateRotation(World world, int x, int y, int z);
	boolean canRotate(World world, BlockTrack2 track);
	void setDirection(ForgeDirection direction);
	ForgeDirection getDirection();
}
