package robomuss.rc.tracks;

import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

import robomuss.rc.block.te.TileEntityTrack;
import robomuss.rc.entity.EntityTrain;
import robomuss.rc.entity.EntityTrainDefault;
import robomuss.rc.entity.OldEntityTrain;
import robomuss.rc.rollercoaster.RollercoasterType;

public class TrackTypeSlopeUp extends TrackType {

	public TrackTypeSlopeUp(String unlocalized_name, int crafting_cost) {
		super(unlocalized_name, crafting_cost);
	}

	@Override
	public void render(RollercoasterType type, TileEntityTrack te) {
		rotate(te);
		
		ModelBase model = type.getStandardModel();
		GL11.glRotatef(45f, 0f, 0f, 1f);
		model.render((Entity) null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
	
		GL11.glPushMatrix();
		GL11.glRotatef(-45f, 0f, 0f, 1f);
		model.render((Entity) null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
		GL11.glPopMatrix();
	}
	
	@Override
	public AxisAlignedBB getRenderBoundingBox(World world, int xCoord, int yCoord, int zCoord) {
		return AxisAlignedBB.getBoundingBox(xCoord - 1, yCoord, zCoord - 1, xCoord + 2, yCoord + 2, zCoord + 2);
	}

	@Override
	public void moveTrain(TileEntityTrack te, EntityTrainDefault entity) {
		if(te.direction == 0) {
			if(entity.direction == 0) {
				entity.posZ += 1f;
				entity.rotationPitch = 45f;
				entity.posY += 1f;
			}
			if(entity.direction == 2) {
				entity.posZ -= 1f;
				entity.rotationPitch = 0f;
				//entity.posY -= 1f;
			}
		}
		
	}
}
