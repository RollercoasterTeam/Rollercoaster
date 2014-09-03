package robomuss.rc.rollercoaster;

import java.util.ArrayList;

import net.minecraft.client.model.ModelBase;
import robomuss.rc.block.model.ModelFlumeEnclosed;
import robomuss.rc.tracks.TrackType;

public class RollercoasterTypeFlumeEnclosed extends RollercoasterType {

	private ModelBase standard = new ModelFlumeEnclosed();
	private ModelBase large = new ModelFlumeEnclosed();
	private ModelBase extended = new ModelFlumeEnclosed();
	private ModelBase corner = new ModelFlumeEnclosed();
	
	public RollercoasterTypeFlumeEnclosed(String name) {
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
