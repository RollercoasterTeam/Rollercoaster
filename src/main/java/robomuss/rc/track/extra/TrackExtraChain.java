package robomuss.rc.track.extra;

import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;

import org.lwjgl.opengl.GL11;

import robomuss.rc.block.model.ModelChainExtended;
import robomuss.rc.track.TrackHandler;
import robomuss.rc.track.TrackPiece;

public class TrackExtraChain extends TrackExtra {

	public TrackExtraChain(String name, ModelBase model, Object[] recipe, int amount, TrackPiece... allowedTrackTypes) {
		super(name, model, recipe, amount, 2, allowedTrackTypes);
	}
	@Override
	public void render(TrackPiece track) {
		if(track == TrackHandler.findTrackType("horizontal")) {
			model.render((Entity) null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
		}
		if(track == TrackHandler.findTrackType("slope_up")) {
			model.render((Entity) null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
			GL11.glRotatef(45f, 0, 0, 1);
			new ModelChainExtended().render((Entity) null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
		}
		if(track == TrackHandler.findTrackType("slope")) {
			GL11.glRotatef(45f, 0, 0, 1);
			new ModelChainExtended().render((Entity) null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
		}
		if(track == TrackHandler.findTrackType("slope_down")) {
			GL11.glRotatef(45f, 0, 0, 1);
			new ModelChainExtended().render((Entity) null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
		}
	}
}
