package robomuss.rc.rollercoaster;

import robomuss.rc.block.model.ModelCorkscrewCoaster;
import robomuss.rc.block.model.ModelCorkscrewCoasterCorner;
import robomuss.rc.block.model.ModelCorkscrewCoasterExtended;
import robomuss.rc.block.model.ModelCorkscrewCoasterLarge;
import net.minecraft.client.model.ModelBase;

public class RollercoasterType {
	
	public RollercoasterType() {
		
	}
	
	public ModelBase getStandardModel() {
		return new ModelCorkscrewCoaster();
	}
	
	public ModelBase getLargeModel() {
		return new ModelCorkscrewCoasterLarge();
	}
	
	public ModelBase getExtendedModel() {
		return new ModelCorkscrewCoasterExtended();
	}

	public ModelBase getCornerModel() {
		return new ModelCorkscrewCoasterCorner();
	}
}
