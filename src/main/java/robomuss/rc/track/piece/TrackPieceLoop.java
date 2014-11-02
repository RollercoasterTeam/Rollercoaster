package robomuss.rc.track.piece;

import net.minecraft.world.World;
import net.minecraftforge.client.model.IModelCustom;
import org.lwjgl.opengl.GL11;
import robomuss.rc.block.te.TileEntityTrackBase;
import robomuss.rc.track.style.TrackStyle;
import robomuss.rc.util.IInventoryRenderSettings;

public class TrackPieceLoop extends TrackPiece implements IInventoryRenderSettings {
	public static final String partName = "loop";

	public TrackPieceLoop(String unlocalized_name, int crafting_cost, int special_render_stages) {
		super(unlocalized_name, crafting_cost, special_render_stages);
	}

	@Override
	public void renderSpecialTileEntity(int renderStage, TrackStyle style, TileEntityTrackBase teTrack, World world, int x, int y, int z) {
		rotate(teTrack, world, x, y, z);

		if(renderStage == 0) {
			GL11.glRotatef(5f, 1f, 0f, 0f);
			GL11.glRotatef(-12f, 0f, 1f, 0f);
			GL11.glScalef(1.05f, 1.05f, 1.05f);
			IModelCustom model = style.getModel();
			model.renderPart(partName);

//			//GL11.glTranslatef((float) te.xCoord + 0.5F, (float) te.yCoord + 1.5F, (float) te.zCoord + 0.5F);
//			GL11.glRotatef(10f, 0f, 0f, -1f);
//			for(int i = 0; i < 19; i++) {
//				GL11.glRotatef(10f, 0f, 0f, 1f);
//				if(i == 10) {
//					GL11.glRotatef(20f, 0f, 1f, 0f);
//				}
////				IModelCustom model = type.getStandardModel();
//				IModelCustom model = style.getModel();
////				model.renderAll();
//				model.renderPart(partName);
//				GL11.glRotatef(0f, 0f, 0f, 0f);
//				GL11.glTranslatef(0f, 0f, 0f);
//			}
		}
		if(renderStage == 1) {
//			IModelCustom model = style.getModel();
//			model.renderPart(partName);

//			//GL11.glTranslatef((float) te.xCoord + 0.5F, (float) te.yCoord + 1.5F, (float) te.zCoord + 1.5f);
//			GL11.glRotatef(10f, 0f, 0f, 1f);
//			for(int i = 0; i < 19; i++) {
//				GL11.glRotatef(-10f, 0f, 0f, 1f);
//				if(i == 10) {
//					GL11.glRotatef(20f, 0f, 1f, 0f);
//				}
//				IModelCustom model = type.getStandardModel();
//				model.renderAll();
//				GL11.glRotatef(0f, 0f, 0f, 0f);
//				GL11.glTranslatef(0f, 0f, 0f);
//			}
		}
	}

	//TODO: double check this isn't broken
	@Override
	public float getSpecialX(int renderStage, double x, TileEntityTrackBase teTrack, World world, int lx , int ly , int lz) {
//        TileEntityTrackBase tileEntityTrackBase = (TileEntityTrackBase) world.getTileEntity(lx, ly, lz);
		if(renderStage == 0) {
			if (teTrack != null && teTrack.track != null) {
				int trackMeta = world.getBlockMetadata(teTrack.xCoord, teTrack.yCoord, teTrack.zCoord);
				switch (trackMeta) {
					case 2:  return (float) (x + 0.52f);
					case 3:  return (float) (x + 0.5f);
					case 4:  return (float) (x + 1.5f);
					case 5:  return (float) (x - 0.5f);
					default: return super.getSpecialX(renderStage, x, teTrack, world, lx, ly, lz);
				}
			}
//			switch(teTrack.direction.ordinal() - 2) {
//				case 0 : return (float) (x + 1.5f);
//				case 2 : return (float) (x - 0.5f);
//				default: return super.getSpecialX(renderStage, x, teTrack, world, lx, ly, lz);
//			}
		}
		return (float) (x + 0.5f);
	}

	@Override
	public float getSpecialY(int renderStage, double y, TileEntityTrackBase teTrack, World world, int lx, int ly, int lz) {
		return (float) (y + 1.528f);
	}

	//TODO: double check this isn't broken
	@Override
	public float getSpecialZ(int renderStage, double z, TileEntityTrackBase teTrack, World world, int lx , int ly , int lz) {
////        TileEntityTrackBase tileEntityTrackBase = (TileEntityTrackBase) world.getTileEntity(lx, ly, lz);
		if(renderStage == 0) {
			if (teTrack != null && teTrack.track != null) {
				int trackMeta = world.getBlockMetadata(teTrack.xCoord, teTrack.yCoord, teTrack.zCoord);
				switch (trackMeta) {
					case 2:  return (float) (z + 1f);
					case 3:  return (float) (z - 0.5f);
					default: return super.getSpecialZ(renderStage, z, teTrack, world, lx, ly, lz);
				}
			}
//			switch(teTrack.direction.ordinal() - 2) {
//				case 1 : return (float) (z + 1.5f);
//				case 3 : return (float) (z - 0.5f);
//				default: return super.getSpecialZ(renderStage, z, teTrack, world, lx, ly, lz);
//			}
//		} else {
//			return (float) (z + 0.5f);
		}
		return (float) (z + 0.5f);
	}

	@Override
	public float getInventoryX() {
		return -0.5f;
	}

	@Override
	public float getInventoryY() {
		return -1.05f;
	}

	@Override
	public float getInventoryZ() {
		return 0;
	}

	@Override
	public float getInventoryScale() {
		return 0.4f;
	}
	
	@Override
	public boolean useIcon() {
		return true;
	}

	@Override
	public float getInventoryRotation() {
		return 0;
	}
}
