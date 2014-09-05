package robomuss.rc.tracks;

import java.util.Arrays;

import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import robomuss.rc.block.render.TileEntityRenderTrack;
import robomuss.rc.block.te.TileEntityTrack;
import robomuss.rc.entity.EntityTrainDefault;
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
	}

	@Override
	public void moveTrain(TileEntityTrack te, EntityTrainDefault entity) {
		if(te.direction == 0) {
			if(entity.direction == 0) {
				entity.posX -= 1f;
				entity.posZ += 0.5f;
				entity.rotationYaw = 180f;
				entity.direction = 3;
			}
			if(entity.direction == 1) {
				entity.posX += 0.5f;
				entity.posZ -= 1f;
				entity.rotationYaw = 270f;
				entity.direction = 2;
			}
		}
		if(te.direction == 1) {
			if(entity.direction == 0) {
				entity.posX += 1f;
				entity.posZ += 0.5f;
				entity.rotationYaw = 0f;
				entity.direction = 1;
			}
			if(entity.direction == 3) {
				entity.posX -= 0.5f;
				entity.posZ -= 1f;
				entity.rotationYaw = 270f;
				entity.direction = 2;
			}
		}
		if(te.direction == 2) {
			if(entity.direction == 2) {
				entity.posX += 1f;
				entity.posZ -= 0.5f;
				entity.rotationYaw = 0f;
				entity.direction = 1;
			}
			if(entity.direction == 3) {
				entity.posX -= 0.5f;
				entity.posZ += 1f;
				entity.rotationYaw = 90f;
				entity.direction = 0;
			}
		}
		if(te.direction == 3) {
			if(entity.direction == 2) {
				entity.posX -= 1f;
				entity.posZ -= 0.5f;
				entity.rotationYaw = 180f;
				entity.direction = 3;
			}
			if(entity.direction == 1) {
				entity.posX += 0.5f;
				entity.posZ += 1f;
				entity.rotationYaw = 90f;
				entity.direction = 0;
			}
		}
	}
}
