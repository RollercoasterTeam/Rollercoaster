package robomuss.rc.track.piece;

import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.client.model.IModelCustom;

import net.minecraftforge.common.util.ForgeDirection;
import org.lwjgl.opengl.GL11;

import robomuss.rc.block.BlockTrackBase;
//import robomuss.rc.block.te.TileEntityTrack;
import robomuss.rc.block.te.TileEntityTrackBase;
import robomuss.rc.entity.EntityTrainDefault;
import robomuss.rc.track.style.TrackStyle;
import robomuss.rc.util.IInventoryRenderSettings;

public class TrackPieceSlopeUp extends TrackPiece implements IInventoryRenderSettings {
	public static final String[] partNames = {"horizontal_extended", "horizontal"};

	public TrackPieceSlopeUp(String unlocalized_name, int crafting_cost, int i) {
		super(unlocalized_name, crafting_cost, i);
	}

	@Override
	public void renderSpecialTileEntity(int special_render_stage, TrackStyle style, TileEntityTrackBase teTrack, World world, int x, int y, int z) {         //renders angled portion of slope
		rotate(teTrack, world, x, y, z);
		
//		IModelCustom model = style.getStandardModel();
		IModelCustom model = style.getModel();

		if(special_render_stage == 0) {                                 //render rotated model
			GL11.glRotatef(45f, 0f, 0f, 1f);
//			model.renderAll();
			model.renderPart(partNames[0]);
		}
	
		if(special_render_stage == 1) {                                 //render flat model
			GL11.glPushMatrix();
//			model.renderAll();
			model.renderPart(partNames[1]);
			GL11.glPopMatrix();
		}
	}
	
	@Override
	public float getSpecialX(int renderStage, double x, TileEntityTrackBase teTrack, World world, int lx , int ly , int lz) {
		int currentFacing = teTrack.getWorldObj().getBlockMetadata(teTrack.xCoord, teTrack.yCoord, teTrack.zCoord);

		if(renderStage == 0) {
			switch (currentFacing) {
				case 2: return (float) (x + 0.5F);
				case 3: return (float) (x + 0.5F);
				case 4: return (float) (x - 0.5F);
				case 5: return (float) (x + 1.5F);
			}
		} else if(renderStage == 1) {
			switch (currentFacing) {
				case 4:
				case 5: return (float) (x + 0.5F);
				default: return super.getSpecialX(renderStage, x, teTrack, world, lx, ly, lz);
			}
		} else {
			return super.getSpecialX(renderStage, x, teTrack, world, lx, ly, lz);
		}
		return super.getSpecialX(renderStage, x, teTrack, world, lx, ly, lz);
	}
	
	@Override
	public float getSpecialY(int renderStage, double y, TileEntityTrackBase teTrack, World world, int lx , int ly , int lz) {
		if(renderStage == 1) {
			return (float) (y + 1.5F);
		} else {
			return super.getSpecialY(renderStage, y + 1.5F, teTrack, world, lx, ly ,lz);
		}
	}
	
	@Override
	public float getSpecialZ(int renderStage, double z, TileEntityTrackBase teTrack, World world, int lx , int ly , int lz) {
		int currentFacing = teTrack.getWorldObj().getBlockMetadata(teTrack.xCoord, teTrack.yCoord, teTrack.zCoord);

		if (renderStage == 1) {
			switch (currentFacing) {
				case 2:
				case 3: return (float) (z + 0.5F);
				default: return super.getSpecialZ(renderStage, z, teTrack, world, lx, ly, lz);
			}
		} else {
			switch (currentFacing) {
				case 2: return (float) (z - 0.5F);
				case 3: return (float) (z + 1.5F);
				case 4: return (float) (z + 0.5F);
				case 5: return (float) (z + 0.5F);
				default: return (float) (z + 0.5F);
			}
		}
	}
	
	@Override
	public AxisAlignedBB getRenderBoundingBox(World world, int xCoord, int yCoord, int zCoord) {
		return AxisAlignedBB.getBoundingBox(xCoord - 1, yCoord, zCoord - 1, xCoord + 2, yCoord + 2, zCoord + 2);
	}

	@Override
	public void moveTrain(BlockTrackBase track, EntityTrainDefault entity, TileEntityTrackBase teTrack) {
		int currentFacing = teTrack.getWorldObj().getBlockMetadata(teTrack.xCoord, teTrack.yCoord, teTrack.zCoord);

		//if track facing north, then if train facing north, add 1F to z and y and rotate 45 degrees

		if (currentFacing == 2) {
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

		if (currentFacing == 4) {
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

		if (currentFacing == 3) {
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

		if (currentFacing == 5) {
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
		return true;
	}
}
