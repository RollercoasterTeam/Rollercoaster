package robomuss.rc.track;

import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

import robomuss.rc.block.te.TileEntityTrack;
import robomuss.rc.entity.EntityTrainDefault;
import robomuss.rc.rollercoaster.RollercoasterType;
import robomuss.rc.util.IInventoryRenderSettings;

public class TrackTypeSlopeUp extends TrackType implements IInventoryRenderSettings {

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
			}
		}
		if(te.direction == 1) {
			if(entity.direction == 1) {
				entity.posX += 1f;
				entity.rotationPitch = 0f;
			}
			if(entity.direction == 3) {
				entity.posX -= 1f;
				entity.rotationPitch = 45f;
				entity.posY += 1f;
			}
		}
		if(te.direction == 2) {
			if(entity.direction == 2) {
				entity.posZ -= 1f;
				entity.rotationPitch = 45f;
				entity.posY += 1f;
			}
			if(entity.direction == 0) {
				entity.posZ += 1f;
				entity.rotationPitch = 0f;
			}
		}
		if(te.direction == 3) {
			if(entity.direction == 1) {
				entity.posX += 1f;
				entity.rotationPitch = 45f;
				entity.posY += 1f;
			}
			if(entity.direction == 3) {
				entity.posX -= 1f;
				entity.rotationPitch = 0f;
			}
		}
	}

	@Override
	public float getInventoryX() {
		return 0;
	}

	@Override
	public float getInventoryY() {
		return 0;
	}

	@Override
	public float getInventoryZ() {
		return 0;
	}

	@Override
	public float getInventoryScale() {
		return 1f;
	}

	@Override
	public float getInventoryRotation() {
		return 180f;
	}

	@Override
	public boolean useIcon() {
		return false;
	}
}
