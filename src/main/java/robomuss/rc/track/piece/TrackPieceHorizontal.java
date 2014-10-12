package robomuss.rc.track.piece;

import net.minecraft.client.Minecraft;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.model.IModelCustom;
import net.minecraftforge.common.util.ForgeDirection;
import robomuss.rc.block.model.ModelRMCTopperCoaster;
import robomuss.rc.block.te.TileEntityTrack;
import robomuss.rc.entity.EntityTrainDefault;
import robomuss.rc.track.style.TrackStyle;

public class TrackPieceHorizontal extends TrackPiece {

	public TrackPieceHorizontal(String unlocalized_name, int crafting_cost) {
		super(unlocalized_name, crafting_cost);
	}

	@Override
	public void render(TrackStyle type, TileEntityTrack te) {
		rotate(te);
		
		
		IModelCustom model = (IModelCustom) type.getStandardModel();
		
		
		if(type.name.equals("wooden_hybrid_topper")) {
			ResourceLocation textures = (new ResourceLocation("rc:textures/models/colour_" + te.colour + ".png"));

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
	public void moveTrain(TileEntityTrack te, EntityTrainDefault entity) {
		if(te.direction == ForgeDirection.SOUTH || te.direction == ForgeDirection.NORTH) {
			if(entity.direction.ordinal() - 2 == 0) {
				entity.posZ += entity.speed;
			}
			if(entity.direction.ordinal() - 2 == 2) {
				entity.posZ -= entity.speed;
			}
		}
		if(te.direction == ForgeDirection.WEST || te.direction == ForgeDirection.EAST) {
			if(entity.direction.ordinal() - 2 == 1) {
				entity.posX += entity.speed;
			}
			if(entity.direction.ordinal() - 2 == 3) {
				entity.posX -= entity.speed;
			}
		}
	}
	
	@Override
	public AxisAlignedBB getBlockBounds(IBlockAccess iba, int x, int y, int z) {
		return AxisAlignedBB.getBoundingBox(0, 0, 0, 1, 0.4F, 1);
	}
}
