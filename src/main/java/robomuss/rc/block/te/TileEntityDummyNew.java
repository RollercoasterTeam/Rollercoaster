package robomuss.rc.block.te;

import net.minecraftforge.common.util.ForgeDirection;
import robomuss.rc.block.BlockDummyNew;
import robomuss.rc.track.piece.TrackPiece;
import robomuss.rc.track.style.TrackStyle;

public class TileEntityDummyNew extends TileEntityTrack {
	public TileEntityTrack teSlope;
	public BlockDummyNew dummy;

	public TileEntityDummyNew() {

	}

	public TrackStyle getStyle() {
		return super.style;
	}

	public ForgeDirection getSlopeDirection() {
		return super.direction;
	}

	public TrackPiece getType() {
		return super.track_type;
	}
}
