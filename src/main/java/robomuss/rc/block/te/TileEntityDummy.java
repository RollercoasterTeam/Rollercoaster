package robomuss.rc.block.te;

import robomuss.rc.track.style.TrackStyle;

public class TileEntityDummy extends TileEntityTrack {
	TrackStyle type;

	public TileEntityDummy() {
		this.type = super.style;
	}
}
