package robomuss.rc.multiblock;

import net.minecraft.world.World;

import java.util.HashSet;

/**
 * Implemented from BeefCore which is written by erogenousbeef.
 */
public abstract class MultiblockControllerBase {
	public static final short DIMENSION_UNBOUNDED = -1;

	//Multipart stuff - do not mess with
	protected World world;

	protected enum AssemblyState {Disassembled, Assembled, Paused};
	protected AssemblyState assemblyState;

	protected HashSet<IMultiBlockComponent> connectedParts;

	private CoordTriplet referenceCoord;
}
