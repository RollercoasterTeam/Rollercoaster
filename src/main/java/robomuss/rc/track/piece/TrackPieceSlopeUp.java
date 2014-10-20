package robomuss.rc.track.piece;

import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.client.model.IModelCustom;

import net.minecraftforge.common.util.ForgeDirection;
import org.lwjgl.opengl.GL11;

import robomuss.rc.block.BlockTrackBase;
//import robomuss.rc.block.te.TileEntityTrack;
import robomuss.rc.entity.EntityTrainDefault;
import robomuss.rc.track.style.TrackStyle;
import robomuss.rc.util.IInventoryRenderSettings;

public class TrackPieceSlopeUp extends TrackPiece implements IInventoryRenderSettings {

	public TrackPieceSlopeUp(String unlocalized_name, int crafting_cost, int i) {
		super(unlocalized_name, crafting_cost, i);
	}

	@Override
	public void renderSpecial(int special_render_stage, TrackStyle style, BlockTrackBase track) {         //renders angled portion of slope
		rotate(track);
		
		IModelCustom model = style.getStandardModel();
		if(special_render_stage == 0) {                                 //render rotated model
			GL11.glRotatef(45f, 0f, 0f, 1f);
			model.renderAll();
		}
	
		if(special_render_stage == 1) {                                 //render flat model
			GL11.glPushMatrix();
			model.renderAll();
			GL11.glPopMatrix();
		}
	}
	
	@Override
	public float getSpecialX(int renderStage, double x, BlockTrackBase track) {                           //X coord to render sloped track at
		if(renderStage == 0) {
			switch(track.direction) {                //N,S,W,E
				case NORTH: return (float) (x + 0.5F);
				case SOUTH: return (float) (x + 0.5F);
				case WEST:  return (float) (x - 0.5F);
				case EAST:  return (float) (x + 1.5F);
				default:    return (float) (x - 0.5F);

			}
		} else if(renderStage == 1) {
			switch(track.direction) {                //N,S,W,E
				case WEST :                                        //WEST?
				case EAST : return (float) (x + 0.5F);             //EAST?
				default : return super.getSpecialX(renderStage, x, track);
			}
		} else {
			return super.getSpecialX(renderStage, x, track);
		}
	}
	
	@Override
	public float getSpecialY(int renderStage, double y, BlockTrackBase track) {
		if(renderStage == 1) {
			return (float) (y + 1.5F);
		} else {
			return super.getSpecialY(renderStage, y + 0.5f, track);
		}
	}
	
	@Override
	public float getSpecialZ(int renderStage, double z, BlockTrackBase track) {
		if(renderStage == 1) {
			switch(track.direction) {                //N,S,W,E
				case NORTH:                                        //NORTH?
				case SOUTH: return (float) (z + 0.5F);             //SOUTH?
				default : return super.getSpecialZ(renderStage, z, track);
			}
		} else {
			switch(track.direction) {                //N,S,W,E
				case NORTH: return (float) (z - 0.5F);             //NORTH?
				case SOUTH: return (float) (z + 1.5F);             //EAST?
				case WEST: return (float) (z + 0.5F);             //SOUTH?
				case EAST: return (float) (z + 0.5F);
				default: return (float) (z + 0.5F);             //WEST?

			}
		}
	}
	
	@Override
	public AxisAlignedBB getRenderBoundingBox(World world, int xCoord, int yCoord, int zCoord) {
		return AxisAlignedBB.getBoundingBox(xCoord - 1, yCoord, zCoord - 1, xCoord + 2, yCoord + 2, zCoord + 2);
	}

	@Override
	public void moveTrain(BlockTrackBase track, EntityTrainDefault entity) {
		//if track facing north, then if train facing north, add 1F to z and y and rotate 45 degrees
		if (track.direction == ForgeDirection.NORTH) {
			if (entity.direction == ForgeDirection.NORTH) {
				entity.posZ += 1f;
				entity.rotationPitch = 45f;
				entity.posY += 1f;
			}

			if (entity.direction == ForgeDirection.SOUTH) {
				entity.posZ -= 1f;
				entity.rotationPitch = 0f;
			}
		}

		if (track.direction == ForgeDirection.WEST) {
			if (entity.direction == ForgeDirection.WEST) {
				entity.posX += 1f;
				entity.rotationPitch = 0f;
			}

			if (entity.direction == ForgeDirection.EAST) {
				entity.posX -= 1f;
				entity.rotationPitch = 45f;
				entity.posY += 1f;
			}
		}

		if (track.direction == ForgeDirection.SOUTH) {
			if (entity.direction == ForgeDirection.SOUTH) {
				entity.posZ -= 1f;
				entity.rotationPitch = 45f;
				entity.posY += 1f;
			}

			if (entity.direction == ForgeDirection.NORTH) {
				entity.posZ += 1f;
				entity.rotationPitch = 0f;
			}
		}

		if (track.direction == ForgeDirection.EAST) {
			if (entity.direction == ForgeDirection.EAST) {
				entity.posX += 1f;
				entity.rotationPitch = 45f;
				entity.posY += 1f;
			}

			if (entity.direction == ForgeDirection.WEST) {
				entity.posX -= 1f;
				entity.rotationPitch = 0f;
			}
		}
		/*if(te.direction == 0) {
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
		}*/
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
