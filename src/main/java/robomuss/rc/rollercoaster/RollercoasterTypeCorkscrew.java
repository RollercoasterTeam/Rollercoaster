package robomuss.rc.rollercoaster;

import java.util.ArrayList;

import robomuss.rc.block.model.ModelCorkscrewCoaster;
import robomuss.rc.block.model.ModelCorkscrewCoasterCorner;
import robomuss.rc.block.model.ModelCorkscrewCoasterExtended;
import robomuss.rc.block.model.ModelCorkscrewCoasterLarge;
import robomuss.rc.tracks.TrackType;
import net.minecraft.client.model.ModelBase;

public class RollercoasterTypeCorkscrew extends RollercoasterType {

	private ModelBase standard = new ModelCorkscrewCoaster();
	private ModelBase large = new ModelCorkscrewCoasterLarge();
	private ModelBase extended = new ModelCorkscrewCoasterExtended();
	private ModelBase corner = new ModelCorkscrewCoasterCorner();
	
	public RollercoasterTypeCorkscrew(String name) {
		super(name);
	}

	@Override
	public ModelBase getStandardModel() {
		return standard;
	}

	@Override
	public ModelBase getLargeModel() {
		return large;
	}

	@Override
	public ModelBase getExtendedModel() {
		return extended;
	}

	@Override
	public ModelBase getCornerModel() {
		return corner;
	}

	@Override
	public ArrayList<TrackType> getBlacklistedTrackTypes() {
		return null;
	}

}
