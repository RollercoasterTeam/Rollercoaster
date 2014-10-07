package robomuss.rc.track.piece;

import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.client.model.IModelCustom;
import org.lwjgl.opengl.GL11;
import robomuss.rc.block.te.TileEntityTrack;
import robomuss.rc.entity.EntityTrainDefault;
import robomuss.rc.track.style.TrackStyle;
import robomuss.rc.util.IInventoryRenderSettings;

public class TrackPieceSlopeUp extends TrackPiece implements IInventoryRenderSettings {

	public TrackPieceSlopeUp(String unlocalized_name, int crafting_cost, int i) {
		super(unlocalized_name, crafting_cost, i);
	}

	@Override
	public void renderSpecial(int special_render_stage, TrackStyle type, TileEntityTrack te) {
		rotate(te);
		
		IModelCustom model = type.getStandardModel();
		if(special_render_stage == 0) {
			GL11.glRotatef(45f, 0f, 0f, 1f);
			model.renderAll();
		}
	
		if(special_render_stage == 1) {
			GL11.glPushMatrix();
			model.renderAll();
			GL11.glPopMatrix();
		}
	}
	
	@Override
	public float getSpecialX(int renderStage, double x, TileEntityTrack te) {
		if(renderStage == 0) {
			switch(te.direction) {
				case 0 : return (float) (x + 0.5F);
				case 1 : return (float) (x - 0.5F);
				case 2 : return (float) (x + 0.5F);
				default: return (float) (x + 1.5F);
			}
		}
		else if(renderStage == 1) {
			switch(te.direction) {
				case 1 : return (float) (x + 0.5F);
				case 3 : return (float) (x + 0.5F);
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
			return (float) (y + 1.5F);
		}
		else {
			return super.getSpecialY(renderStage, y + 0.5f, te);
		}
	}
	
	@Override
	public float getSpecialZ(int renderStage, double z, TileEntityTrack te) {
		if(renderStage == 1) {
			switch(te.direction) {
				case 0 : return (float) (z + 0.5F);
				case 2 : return (float) (z + 0.5F);
				default : return super.getSpecialZ(renderStage, z, te);
			}
		}
		else {
			switch(te.direction) {
			case 0 : return (float) (z + 1.5F);
			case 1 : return (float) (z + 0.5F);
			case 2 : return (float) (z - 0.5F);
			default: return (float) (z + 0.5F);
		}
		}
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
		return 0.3f;
	}

	@Override
	public float getInventoryY() {
		return 0.55f;
	}

	@Override
	public float getInventoryZ() {
		return 0;
	}

	@Override
	public float getInventoryScale() {
		return 0.9f;
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
