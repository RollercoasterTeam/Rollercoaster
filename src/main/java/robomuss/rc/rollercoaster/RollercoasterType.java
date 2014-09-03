package robomuss.rc.rollercoaster;

import java.util.ArrayList;

import robomuss.rc.block.model.ModelCorkscrewCoaster;
import robomuss.rc.block.model.ModelCorkscrewCoasterCorner;
import robomuss.rc.block.model.ModelCorkscrewCoasterExtended;
import robomuss.rc.block.model.ModelCorkscrewCoasterLarge;
import robomuss.rc.tracks.TrackType;
import net.minecraft.client.model.ModelBase;

public abstract class RollercoasterType {
	
	private String name;
	
	public RollercoasterType(String name) {
		this.name = name;
	}
 	
	public abstract ModelBase getStandardModel();
	
	public abstract ModelBase getLargeModel();
	
	public abstract ModelBase getExtendedModel();

	public abstract ModelBase getCornerModel();
	
	public abstract ArrayList<TrackType> getBlacklistedTrackTypes();
	
	public String getId() {
		return name;
	}
}
