package robomuss.rc.util;

import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public interface IPaintable {
	
	int getPaintMeta(World world, BlockPos blockPos);
}
