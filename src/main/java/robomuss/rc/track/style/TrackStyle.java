package robomuss.rc.track.style;

import java.util.ArrayList;

import net.minecraft.client.model.ModelBase;
import robomuss.rc.track.piece.TrackPiece;

public class TrackStyle {
	
	public String name;
	
	public ModelBase standard;
	public ModelBase large;
	public ModelBase extended;
	public ModelBase corner;
	
	public ArrayList<TrackPiece> whitelistedPieces;
	
	public boolean riddenUsingCart;
	
	public TrackStyle(String name) {
		this.name = name;
	}
 	
	public ModelBase getStandardModel() {
		return standard;
	}
	
	public ModelBase getLargeModel() {
		return large;
	}
	
	public ModelBase getExtendedModel() {
		return extended;
	}

	public ModelBase getCornerModel() {
		return corner;
	}
	
	public ArrayList<TrackPiece> getWhitelistedTrackPieces() {
		return whitelistedPieces;
	}
	
	public boolean isRiddenUsingCart() {
		return riddenUsingCart;
	}
	
	public String getId() {
		return name;
	}
}
