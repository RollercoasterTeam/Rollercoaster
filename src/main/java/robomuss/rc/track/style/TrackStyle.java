package robomuss.rc.track.style;

import net.minecraftforge.client.model.IModelCustom;
import robomuss.rc.track.piece.TrackPiece;

import java.util.ArrayList;

public class TrackStyle {
	
	public String name;
	
	public IModelCustom standard;
	public IModelCustom large;
	public IModelCustom extended;
	public IModelCustom corner;
	
	public ArrayList<TrackPiece> whitelistedPieces;
	
	public boolean riddenUsingCart;

	public String localizedName;
	
	public TrackStyle(String name) {
		this.name = name;
	}
 	
	public IModelCustom getStandardModel() {
		return standard;
	}
	
	public IModelCustom getLargeModel() {
		return large;
	}
	
	public IModelCustom getExtendedModel() {
		return extended;
	}

	public IModelCustom getCornerModel() {
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
