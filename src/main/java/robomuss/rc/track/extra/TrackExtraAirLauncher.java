package robomuss.rc.track.extra;

import net.minecraft.client.model.ModelBase;
import robomuss.rc.block.BlockTrackBase;
import robomuss.rc.entity.EntityTrainDefault;
import robomuss.rc.track.piece.TrackPiece;

public class TrackExtraAirLauncher extends TrackExtra {
	public TrackExtraAirLauncher(String name, ModelBase model, Object[] recipe, int amount, TrackPiece... allowedTrackTypes) {
		super(name, model, recipe, amount, allowedTrackTypes);
	}
	
	@Override
	public void applyEffectToTrain(BlockTrackBase track, EntityTrainDefault entity) {
		entity.speed += 0.3f;
		//track.getAirHandler().addAir(20, ForgeDirection.UNKNOWN);
	}
}
