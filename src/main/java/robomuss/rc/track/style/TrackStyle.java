package robomuss.rc.track.style;

import java.util.ArrayList;

import net.minecraft.client.model.ModelBase;
import robomuss.rc.track.piece.TrackPiece;

public abstract class TrackStyle {
	
	private String name;
	
	public TrackStyle(String name) {
		this.name = name;
	}
 	
	public abstract ModelBase getStandardModel();
	
	public abstract ModelBase getLargeModel();
	
	public abstract ModelBase getExtendedModel();

	public abstract ModelBase getCornerModel();
	
	public abstract ArrayList<TrackPiece> getBlacklistedTrackTypes();
	
	public abstract boolean isRiddenUsingCart();
	
	public String getId() {
		return name;
	}
}
