package robomuss.rc.rollercoaster;

import java.util.ArrayList;

import net.minecraft.client.model.ModelBase;
import robomuss.rc.block.model.ModelFlumeOpen;
import robomuss.rc.track.TrackPiece;

public class RollercoasterTypeFlumeOpen extends RollercoasterType {

	private ModelBase standard = new ModelFlumeOpen();
	private ModelBase large = new ModelFlumeOpen();
	private ModelBase extended = new ModelFlumeOpen();
	private ModelBase corner = new ModelFlumeOpen();
	
	public RollercoasterTypeFlumeOpen(String name) {
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
	public ArrayList<TrackPiece> getBlacklistedTrackTypes() {
		return null;
	}

	@Override
	public boolean isRiddenUsingCart() {
		return false;
	}

}
