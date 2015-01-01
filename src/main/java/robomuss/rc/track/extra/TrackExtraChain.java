package robomuss.rc.track.extra;

import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;
import robomuss.rc.block.model.ModelChainExtended;
import robomuss.rc.track.TrackHandler;
import robomuss.rc.track.piece.TrackPiece;

public class TrackExtraChain extends TrackExtra {
	public TrackExtraChain(int id, String name, ModelBase model, int renderStages, int amount, Object[] recipe, TrackHandler.Types ... allowedTrackTypes) {
		super(id, name, model, renderStages, amount, recipe, allowedTrackTypes);
	}

	//TODO:!!!
	@Override
	public void render(TrackPiece type) {
//		if(type == TrackHandler.findTrackType("horizontal")) {
		if (type == TrackHandler.Types.HORIZONTAL.type) {
			model.render((Entity) null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
		}

//		if(type == TrackHandler.findTrackType("slope_up")) {
		if (type == TrackHandler.Types.SLOPE_UP.type) {
			model.render((Entity) null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
			GL11.glRotatef(45f, 0, 0, 1);
			new ModelChainExtended().render((Entity) null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
		}

//		if(type == TrackHandler.findTrackType("slope")) {
		if (type == TrackHandler.Types.SLOPE.type) {
			GL11.glRotatef(45f, 0, 0, 1);
			new ModelChainExtended().render((Entity) null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
		}

//		if(type == TrackHandler.findTrackType("slope_down")) {
		if (type == TrackHandler.Types.SLOPE_DOWN.type) {
			GL11.glRotatef(45f, 0, 0, 1);
			new ModelChainExtended().render((Entity) null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
		}
	}
}
