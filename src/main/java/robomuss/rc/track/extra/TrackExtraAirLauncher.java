package robomuss.rc.track.extra;

import net.minecraft.client.model.ModelBase;
import robomuss.rc.block.BlockTrackBase;
import robomuss.rc.entity.EntityTrainDefault;
import robomuss.rc.entity.EntityTrainDefault2;
import robomuss.rc.track.TrackHandler;
import robomuss.rc.track.piece.TrackPiece;

public class TrackExtraAirLauncher extends TrackExtra {
	public TrackExtraAirLauncher(int id, String name, ModelBase model, int renderStages, int amount, Object[] recipe, TrackHandler.Types ... allowedTrackTypes) {
		super(id, name, model, renderStages, amount, recipe, allowedTrackTypes);
	}
	
	@Override
	public void applyEffectToTrain(BlockTrackBase track, EntityTrainDefault entity) {
		entity.speed += 0.3f;
		//track.getAirHandler().addAir(20, ForgeDirection.UNKNOWN);
	}

	@Override
	public void applyEffectToTrain(BlockTrackBase track, EntityTrainDefault2 entity) {
		entity.speed += 0.3f;
		//track.getAirHandler().addAir(20, ForgeDirection.UNKOWN);
	}
}
