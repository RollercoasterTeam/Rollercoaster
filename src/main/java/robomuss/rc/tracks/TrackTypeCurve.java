package robomuss.rc.tracks;

import java.util.Arrays;

import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import robomuss.rc.block.render.TileEntityRenderTrack;
import robomuss.rc.block.te.TileEntityTrack;
import robomuss.rc.entity.OldEntityTrain;
import robomuss.rc.rollercoaster.RollercoasterType;


public class TrackTypeCurve extends TrackType {

	public TrackTypeCurve(String unlocalized_name, int crafting_cost, int i) {
		super(unlocalized_name, crafting_cost, i);
	}
	
	@Override
	public void renderSpecial(int renderStage, RollercoasterType type, TileEntityTrack te) {
		rotate(te);
		
		ModelBase model = type.getCornerModel();
		
		model.render((Entity) null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
		
		/*if(renderStage == 0) {
			model.render((Entity) null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
		}
		else if(renderStage == 1) {
			GL11.glRotatef(45f, 0, 1, 0);
			model.render((Entity) null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
		}
		else if(renderStage == 2) {
			GL11.glRotatef(90f, 0, 1, 0);
			model.render((Entity) null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
		}*/
	}
	
	/*@Override
	public float getSpecialX(int renderStage, double x, TileEntityTrack te) {
		if(renderStage == 1) {
			switch(te.direction) {
				case 1 : return (float) (x - 0.5F);
				case 3 : return (float) (x + 1.5F);
				default: return super.getSpecialX(renderStage, x, te);
			}
		}
		else {
			return super.getSpecialX(renderStage, x, te);
		}
	}
	
	@Override
	public float getSpecialZ(int renderStage, double z, TileEntityTrack te) {
		if(renderStage == 1) {
			switch(te.direction) {
				case 1 : return (float) (z + 0.5F);
				case 3 : return (float) (z + 0.5F);
				default: return super.getSpecialX(renderStage, z, te);
			}
		}
		else {
			return super.getSpecialX(renderStage, z, te);
		}
	}*/

}
