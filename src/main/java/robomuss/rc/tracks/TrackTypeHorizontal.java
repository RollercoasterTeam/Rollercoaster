package robomuss.rc.tracks;

import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;
import robomuss.rc.block.te.TileEntityTrack;

public class TrackTypeHorizontal extends TrackType {

	public TrackTypeHorizontal(String unlocalized_name, int crafting_cost) {
		super(unlocalized_name, crafting_cost);
	}

	@Override
	public void render(ModelBase model, TileEntityTrack te) {
		rotate(te);
		model.render((Entity) null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
	}
}
