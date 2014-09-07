package robomuss.rc.tracks;

import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import robomuss.rc.block.model.ModelCorkscrewCoaster;
import robomuss.rc.block.te.TileEntityTrack;
import robomuss.rc.entity.EntityTrain;
import robomuss.rc.entity.EntityTrainDefault;
import robomuss.rc.entity.OldEntityTrain;
import robomuss.rc.rollercoaster.RollercoasterType;

public class TrackTypeHorizontal extends TrackType {

	public TrackTypeHorizontal(String unlocalized_name, int crafting_cost) {
		super(unlocalized_name, crafting_cost);
	}

	@Override
	public void render(RollercoasterType type, TileEntityTrack te) {
		rotate(te);
		ModelBase model = type.getStandardModel();
		model.render((Entity) null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
	}
	
	@Override
	public void moveTrain(TileEntityTrack te, EntityTrainDefault entity) {
		if(te.direction == 0 || te.direction == 2) {
			if(entity.direction == 0) {
				entity.posZ += 0.1f;
				//entity.serverPosZ += 0.1f;
			}
			if(entity.direction == 2) {
				entity.posZ -= 0.1f;
			}
		}
		if(te.direction == 1 || te.direction == 3) {
			if(entity.direction == 1) {
				entity.posX += 0.1f;
			}
			if(entity.direction == 3) {
				entity.posX -= 0.1f;
			}
		}
	}
}
