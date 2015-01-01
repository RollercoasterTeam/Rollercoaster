package robomuss.rc.track.extra;

import net.minecraft.client.model.ModelBase;
import robomuss.rc.block.BlockTrackBase;
import robomuss.rc.entity.EntityTrainDefault;
import robomuss.rc.entity.EntityTrainDefault2;
import robomuss.rc.track.TrackHandler;

public class TrackExtraStation extends TrackExtra {
	public TrackExtraStation(int id, String name, ModelBase model, int renderStages, int amount, Object[] recipe, TrackHandler.Types ... allowedTrackTypes) {
		super(id, name, model, renderStages, amount, recipe, allowedTrackTypes);
	}

	@Override
	public void applyEffectToTrain(BlockTrackBase track, EntityTrainDefault entity) {
		if(entity.speed < 0.1f) {
			entity.speed = 0.1f;
		}
	}

	@Override
	public void applyEffectToTrain(BlockTrackBase track, EntityTrainDefault2 entity) {
		if (entity.speed < 0.1f) {
			entity.speed = 0.1f;
		}
	}
}
