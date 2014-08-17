package robomuss.rc.tracks;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;
import robomuss.rc.block.te.TileEntityTrack;

public class TrackTypeInvertedHorizontal extends TrackType {

	public TrackTypeInvertedHorizontal(String unlocalized_name, int crafting_cost) {
		super(unlocalized_name, crafting_cost);
	}

	@Override
	public void render(ModelBase model, TileEntityTrack te) {
		rotate(te);
		GL11.glRotatef(180f, 0f, 0f, 1f);
		model.render((Entity) null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
	}
	
	@Override
	public float getY(double y, TileEntityTrack te) {
		return (float) (y - 1);
	}
}
