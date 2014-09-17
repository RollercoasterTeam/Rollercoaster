package robomuss.rc.tracks.extra;

import net.minecraft.client.model.ModelBase;
import robomuss.rc.block.te.TileEntityTrack;
import robomuss.rc.entity.EntityTrainDefault;
import robomuss.rc.tracks.TrackType;

public class TrackExtraAirLauncher extends TrackExtra {

	public TrackExtraAirLauncher(String name, ModelBase model, Object[] recipe, int amount, TrackType... allowedTrackTypes) {
		super(name, model, recipe, amount, allowedTrackTypes);
	}
	
	private float speed = 0.6f;
	
	@Override
	public void applyEffectToTrain(TileEntityTrack te, EntityTrainDefault entity) {
		if(te.direction == 0 || te.direction == 2) {
			if(entity.direction == 0) {
				entity.posZ += speed;
			}
			if(entity.direction == 2) {
				entity.posZ -= speed;
			}
		}
		if(te.direction == 1 || te.direction == 3) {
			if(entity.direction == 1) {
				entity.posX += speed;
			}
			if(entity.direction == 3) {
				entity.posX -= speed;
			}
		}
	}
}
