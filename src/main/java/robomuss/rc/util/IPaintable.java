package robomuss.rc.util;

import net.minecraft.world.World;

public interface IPaintable {
	
	int getPaintMeta(World world, int x, int y, int z);
}
