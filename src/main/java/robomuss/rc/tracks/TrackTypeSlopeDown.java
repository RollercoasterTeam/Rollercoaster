package robomuss.rc.tracks;

import java.util.Arrays;

import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

import robomuss.rc.block.render.TileEntityRenderTrack;
import robomuss.rc.block.te.TileEntityTrack;
import robomuss.rc.entity.EntityTrain;
import robomuss.rc.entity.EntityTrainDefault;
import robomuss.rc.rollercoaster.RollercoasterType;

public class TrackTypeSlopeDown extends TrackType {

	public TrackTypeSlopeDown(String unlocalized_name, int crafting_cost, int i) {
		super(unlocalized_name, crafting_cost, i);
	}
	
	@Override
	public void renderSpecial(int renderStage, RollercoasterType type, TileEntityTrack te) {
		rotate(te);
		if(renderStage == 0) {
			ModelBase model = type.getExtendedModel();
			
			GL11.glRotatef(45f, 0f, 0f, 1f);
			model.render((Entity) null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
		}
		else if(renderStage == 1) {
			ModelBase model = type.getStandardModel();
			
			model.render((Entity) null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
		}
	}
	
	@Override
	public float getSpecialX(int renderStage, double x, TileEntityTrack te) {
		if(renderStage == 0) {
			return (float) (x + 0.5F);
		}
		else if(renderStage == 1) {
			switch(te.direction) {
				case 1 : return (float) (x - 1.5F);
				case 3 : return (float) (x + 2.5F);
				default : return super.getSpecialX(renderStage, x, te);
			}
		}
		else {
			return super.getSpecialX(renderStage, x, te);
		}
	}
	
	@Override
	public float getSpecialY(int renderStage, double y, TileEntityTrack te) {
		if(renderStage == 1) {
			return (float) (y + 2.5F);
		}
		else {
			return super.getSpecialY(renderStage, y, te);
		}
	}
	
	@Override
	public float getSpecialZ(int renderStage, double z, TileEntityTrack te) {
		if(renderStage == 1) {
			switch(te.direction) {
				case 0 : return (float) (z + 2.5F);
				case 2 : return (float) (z - 1.5F);
				default : return super.getSpecialZ(renderStage, z, te);
			}
		}
		else {
			return super.getSpecialZ(renderStage, z, te);
		}
	}
	
	@Override
	public AxisAlignedBB getRenderBoundingBox(World world, int xCoord, int yCoord, int zCoord) {
		return AxisAlignedBB.getBoundingBox(xCoord - 1, yCoord, zCoord - 1, xCoord + 2, yCoord + 2, zCoord + 2);
	}
	
	@Override
	public void moveTrain(TileEntityTrack te, EntityTrainDefault entity) {
		System.out.println("Direction: " + entity.direction);
		if(te.direction == 0) {
			if(entity.direction == 0) {
				entity.posY += 1f;
				entity.posZ += 3f;
				entity.rotationPitch = 0f;
			}
			if(entity.direction == 2) {
				entity.posY -= 2f;
				entity.posZ -= 3f;
				entity.rotationPitch = -45f;
			}
		}
		if(te.direction == 2) {
			
			if(entity.direction == 0) {
				entity.posY -= 2f;
				entity.posZ += 3f;
				entity.rotationPitch = -45f;
			}
			if(entity.direction == 2) {
				entity.posY += 1f;
				entity.posZ -= 3f;
				entity.rotationPitch = 0f;
			}
		}
	}
}
