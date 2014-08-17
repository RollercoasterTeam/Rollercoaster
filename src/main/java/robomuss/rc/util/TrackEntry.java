package robomuss.rc.util;

import net.minecraft.block.Block;

public class TrackEntry {
	
	public Block track;
	public int cost;
	
	public TrackEntry(Block track, int cost) {
		this.track = track;
		this.cost = cost;
	}
}
