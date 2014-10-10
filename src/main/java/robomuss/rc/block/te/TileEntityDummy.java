package robomuss.rc.block.te;

import net.minecraft.block.state.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import robomuss.rc.track.style.TrackStyle;
import sun.org.mozilla.javascript.internal.ast.Block;

public class TileEntityDummy extends TileEntityTrack {
	TrackStyle type;

	//This is messy but time saving
	public int xCoord = getPos().getX();
	public int yCoord = getPos().getY();
	public int zCoord = getPos().getZ();

	public TileEntityDummy() {
		this.type = super.type;
	}
}
