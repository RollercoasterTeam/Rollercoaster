package robomuss.rc.block.te;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import robomuss.rc.track.style.TrackStyle;
import sun.org.mozilla.javascript.internal.ast.Block;

public class TileEntityDummy extends TileEntityTrack {
	TrackStyle type;

	public TileEntityDummy() {
		this.type = super.type;
	}
}
