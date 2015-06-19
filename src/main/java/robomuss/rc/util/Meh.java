package robomuss.rc.util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.world.World;
import robomuss.rc.block.te.TileEntityTrack;

public class Meh {

	public static boolean sst(World world, TileEntityTrack te, EntityPlayer player) {
		if(player.isPotionActive(Potion.nightVision) && isAwesome(player)) {
			te.colour = ColourUtil.colours.length - 1;
			world.markBlockForUpdate(te.xCoord, te.yCoord, te.zCoord);
			return true;
		}
		else {
			return false;
		}
	}

	private static boolean isAwesome(EntityPlayer player) {
		return player.getGameProfile().getName().equals("roboyobo");
	}

}
