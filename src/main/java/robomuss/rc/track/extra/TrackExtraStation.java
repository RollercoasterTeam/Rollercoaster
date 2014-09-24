package robomuss.rc.track.extra;

import net.minecraft.client.model.ModelBase;
import robomuss.rc.block.te.TileEntityTrack;
import robomuss.rc.entity.EntityTrainDefault;
import robomuss.rc.track.piece.TrackPiece;

public class TrackExtraStation extends TrackExtra {

	public TrackExtraStation(String name, ModelBase model, Object[] recipe, int amount, TrackPiece... allowedTrackTypes) {
		super(name, model, recipe, amount, allowedTrackTypes);
	}

	@Override
	public void applyEffectToTrain(TileEntityTrack te, EntityTrainDefault entity) {
		if(entity.speed < 0.1f) {
			entity.speed = 0.1f;
		}
	}
}
