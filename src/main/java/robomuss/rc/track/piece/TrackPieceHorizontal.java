package robomuss.rc.track.piece;

import net.minecraft.client.Minecraft;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.model.IModelCustom;
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
		model.renderPart("topper1");
		model.renderPart("topper2");
		
		
		ResourceLocation textures = (new ResourceLocation("textures/blocks/planks_oak.png"));

		Minecraft.getMinecraft().renderEngine.bindTexture(textures);
		model.renderAllExcept("topper1", "topper2");
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
