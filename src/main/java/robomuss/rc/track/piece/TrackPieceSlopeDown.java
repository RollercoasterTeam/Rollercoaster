package robomuss.rc.track.piece;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.client.model.IModelCustom;

import org.lwjgl.opengl.GL11;

import robomuss.rc.block.te.TileEntityTrack;
import robomuss.rc.entity.EntityTrainDefault;
import robomuss.rc.track.style.TrackStyle;
import robomuss.rc.util.IInventoryRenderSettings;

public class TrackPieceSlopeDown extends TrackPiece implements IInventoryRenderSettings {

	public TrackPieceSlopeDown(String unlocalized_name, int crafting_cost, int i) {
		super(unlocalized_name, crafting_cost, i);
	}
	
	@Override
	public void renderSpecial(int renderStage, TrackStyle type, TileEntityTrack te) {
		rotate(te);
		if(renderStage == 0) {
			IModelCustom model = type.getExtendedModel();
			
			GL11.glRotatef(45f, 0f, 0f, 1f);
			model.renderAll();
		}
		else if(renderStage == 1) {
			IModelCustom model = type.getStandardModel();
			
			model.renderAll();
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
		if(entity.riddenByEntity != null && entity.riddenByEntity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) entity.riddenByEntity;
			player.swingProgressInt = 90;
		}
		if(te.direction == 0) {
			if(entity.direction == 0) {
				entity.posY += 1f;
				entity.posZ += 3f;
				entity.rotationPitch = 0f;
				entity.speed += 0.1f;
			}
			if(entity.direction == 2) {
				entity.posY -= 2f;
				entity.posZ -= 3f;
				entity.rotationPitch = -45f;
			}
		}
		if(te.direction == 1) {
			if(entity.direction == 1) {
				entity.posY -= 2f;
				entity.posX += 3f;
				entity.rotationPitch = -45f;
				entity.speed += 0.1f;
			}
			if(entity.direction == 3) {
				entity.posY += 1f;
				entity.posX -= 3f;
				entity.rotationPitch = 0f;
			}
		}
		if(te.direction == 2) {
			if(entity.direction == 0) {
				entity.posY -= 2f;
				entity.posZ += 3f;
				entity.rotationPitch = -45f;
				entity.speed += 0.1f;
			}
			if(entity.direction == 2) {
				entity.posY += 1f;
				entity.posZ -= 3f;
				entity.rotationPitch = 0f;
			}
		}
		if(te.direction == 3) {
			if(entity.direction == 1) {
				entity.posY += 1f;
				entity.posX += 3f;
				entity.rotationPitch = 0f;
				entity.speed += 0.1f;
			}
			if(entity.direction == 3) {
				entity.posY -= 2f;
				entity.posX -= 3f;
				entity.rotationPitch = -45f;
			}
		}
	}

	@Override
	public float getInventoryX() {
		return 0;
	}

	@Override
	public float getInventoryY() {
		return -1f;
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
		return true;
	}
}
