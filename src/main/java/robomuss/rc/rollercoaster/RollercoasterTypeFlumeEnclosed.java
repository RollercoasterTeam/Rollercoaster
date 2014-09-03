package robomuss.rc.rollercoaster;

import java.util.ArrayList;

import net.minecraft.client.model.ModelBase;
import robomuss.rc.block.model.ModelFlumeFullTrack;
import robomuss.rc.tracks.TrackType;

public class RollercoasterTypeFlumeEnclosed extends RollercoasterType {

	private ModelBase standard = new ModelFlumeFullTrack();
	private ModelBase large = new ModelFlumeFullTrack();
	private ModelBase extended = new ModelFlumeFullTrack();
	private ModelBase corner = new ModelFlumeFullTrack();
	
	public RollercoasterTypeFlumeEnclosed(int id) {
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
