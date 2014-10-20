package robomuss.rc.track.piece;

import net.minecraft.client.Minecraft;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.model.IModelCustom;
import net.minecraftforge.common.util.ForgeDirection;
import robomuss.rc.block.BlockTrackBase;
import robomuss.rc.block.model.ModelRMCTopperCoaster;
//import robomuss.rc.block.te.TileEntityTrack;
import robomuss.rc.entity.EntityTrainDefault;
import robomuss.rc.track.style.TrackStyle;
import robomuss.rc.util.IInventoryRenderSettings;

public class TrackPieceHorizontal extends TrackPiece implements IInventoryRenderSettings {

	public TrackPieceHorizontal(String unlocalized_name, int crafting_cost) {
		super(unlocalized_name, crafting_cost);
	}

	@Override
	public void render(TrackStyle type, BlockTrackBase track) {
		rotate(track);
		
		
		IModelCustom model = (IModelCustom) type.getStandardModel();
		
		
		if(type.name.equals("wooden_hybrid_topper")) {
			ResourceLocation textures = (new ResourceLocation("rc:textures/models/colour_" + track.colour + ".png"));

			Minecraft.getMinecraft().renderEngine.bindTexture(textures);
			
			model.renderPart("topper1");
			model.renderPart("topper2");
			
			ResourceLocation textures2 = (new ResourceLocation("textures/blocks/planks_oak.png"));

			Minecraft.getMinecraft().renderEngine.bindTexture(textures2);
			
			model.renderPart("top1");
			model.renderPart("top2");
			model.renderPart("bottom1");
			model.renderPart("bottom2");
		}
		else {
			model.renderAll();
		}
	}
	
	@Override
	public void moveTrain(BlockTrackBase track, EntityTrainDefault entity) {
//		if(track.direction == ForgeDirection.SOUTH || track.direction == ForgeDirection.NORTH) {
//			if(entity.direction.ordinal() - 2 == 0) {
//				entity.posZ += entity.speed;
//			}
//			if(entity.direction.ordinal() - 2 == 2) {
//				entity.posZ -= entity.speed;
//			}
//		}
//		if(track.direction == ForgeDirection.WEST || track.direction == ForgeDirection.EAST) {
//			if(entity.direction.ordinal() - 2 == 1) {
//				entity.posX += entity.speed;
//			}
//			if(entity.direction.ordinal() - 2 == 3) {
//				entity.posX -= entity.speed;
//			}
//		}
		switch (track.direction) {
			case NORTH:
			case SOUTH:
				switch (entity.direction) {
					case NORTH: entity.posZ -= entity.speed; break;
					case SOUTH: entity.posZ += entity.speed; break;
				}
				break;
			case WEST:
			case EAST:
				switch (entity.direction) {
					case WEST: entity.posX += entity.speed; break;
					case EAST: entity.posX -= entity.speed; break;
				}
		}
	}
	
	@Override
	public AxisAlignedBB getBlockBounds(IBlockAccess iba, int x, int y, int z) {
		return AxisAlignedBB.getBoundingBox(0, 0, 0, 1, 0.4F, 1);
	}

	//TODO: fix item rendering!!!
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
		return false;
	}
}
