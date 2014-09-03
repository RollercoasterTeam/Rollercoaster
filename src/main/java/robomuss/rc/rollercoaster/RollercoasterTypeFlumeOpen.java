package robomuss.rc.rollercoaster;

import java.util.ArrayList;

import net.minecraft.client.model.ModelBase;
import robomuss.rc.block.model.ModelCorkscrewCoaster;
import robomuss.rc.block.model.ModelCorkscrewCoasterCorner;
import robomuss.rc.block.model.ModelCorkscrewCoasterExtended;
import robomuss.rc.block.model.ModelCorkscrewCoasterLarge;
import robomuss.rc.block.model.ModelFlumeTrack;
import robomuss.rc.tracks.TrackType;

public class RollercoasterTypeFlumeOpen extends RollercoasterType {

	private ModelBase standard = new ModelFlumeTrack();
	private ModelBase large = new ModelFlumeTrack();
	private ModelBase extended = new ModelFlumeTrack();
	private ModelBase corner = new ModelFlumeTrack();
	
	public RollercoasterTypeFlumeOpen(int id) {
		super(id);
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
