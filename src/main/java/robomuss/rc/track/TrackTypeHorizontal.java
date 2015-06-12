package robomuss.rc.track;

import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import robomuss.rc.block.model.ModelCorkscrewCoaster;
import robomuss.rc.block.te.TileEntityTrack;
import robomuss.rc.entity.EntityTrainDefault;
import robomuss.rc.rollercoaster.RollercoasterType;

public class TrackTypeHorizontal extends TrackType {

	public TrackTypeHorizontal(String unlocalized_name, int crafting_cost) {
		super(unlocalized_name, crafting_cost);
	}

	@Override
	public void render(RollercoasterType type, TileEntityTrack te) {
		rotate(te);
		//ModelBase model = type.getStandardModel();
		ModelCorkscrewCoaster model = (ModelCorkscrewCoaster) type.getStandardModel();
		
		model.Base.render(0.0625F);
		
		model.Side_1.render(0.0625F);
		model.Side_2.render(0.0625F);
		model.Side_3.render(0.0625F);
		model.Side_4.render(0.0625F);
		model.Side_5.render(0.0625F);
		model.Side_6.render(0.0625F);
		model.Side_7.render(0.0625F);
		model.Side_8.render(0.0625F);
		
		model.Part_1.render(0.0625F);
		model.Part_2.render(0.0625F);
		model.Part_3.render(0.0625F);
		model.Part_4.render(0.0625F);
		model.Part_5.render(0.0625F);
		model.Part_6.render(0.0625F);
		model.Part_7.render(0.0625F);
		model.Part_8.render(0.0625F);
		
		model.Top_1.render(0.0625F);
		model.Top_2.render(0.0625F);
	}
	
	@Override
	public void moveTrain(TileEntityTrack te, EntityTrainDefault entity) {
		if(te.direction == 0 || te.direction == 2) {
			if(entity.direction == 0) {
				entity.posZ += entity.speed;
			}
			if(entity.direction == 2) {
				entity.posZ -= entity.speed;
			}
		}
		if(te.direction == 1 || te.direction == 3) {
			if(entity.direction == 1) {
				entity.posX += entity.speed;
			}
			if(entity.direction == 3) {
				entity.posX -= entity.speed;
			}
		}
	}
	
	@Override
	public AxisAlignedBB getBlockBounds(IBlockAccess iba, int x, int y, int z) {
		return AxisAlignedBB.getBoundingBox(0, 0, 0, 1, 0.4F, 1);
	}
}
