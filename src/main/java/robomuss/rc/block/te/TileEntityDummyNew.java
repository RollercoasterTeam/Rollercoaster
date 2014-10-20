package robomuss.rc.block.te;

import net.minecraftforge.common.util.ForgeDirection;
import robomuss.rc.block.BlockDummyNew;
import robomuss.rc.block.BlockTrackBase;
import robomuss.rc.track.piece.TrackPiece;
import robomuss.rc.track.style.TrackStyle;

public class TileEntityDummyNew extends TileEntityTrackBase {
	public TileEntityDummyNew(BlockTrackBase blockTrack) {
		super(blockTrack);
	}
//	public TileEntityTrack teSlope;
//	public BlockDummyNew dummy;
//
//	public TileEntityDummyNew() {
//
//	}
//
//	public TrackStyle getStyle() {
//		return super.style;
//	}
//
//	public ForgeDirection getSlopeDirection() {
//		return super.direction;
//	}
//
//	public void setSlopeDirection(ForgeDirection direction) {
//		if (direction != ForgeDirection.DOWN && direction != ForgeDirection.UP && direction != ForgeDirection.UNKNOWN) {
//			this.teSlope.direction = direction;
//		}
//	}
//
//	public String getHammerDisplayDirection() {
//		switch (this.teSlope.direction) {
//			case NORTH:
//				return "North";
//			case SOUTH:
//				return "South";
//			case WEST:
//				return "West";
//			case EAST:
//				return "East";
//		}
//
//		return "Unknown";
//	}
//
//	public TrackPiece getType() {
//		return super.track_type;
//	}
}
