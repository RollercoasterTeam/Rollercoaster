package robomuss.rc.track.piece;

import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;
import net.minecraftforge.client.model.IModelCustom;

import org.lwjgl.opengl.GL11;

import robomuss.rc.block.te.TileEntityTrack;
import robomuss.rc.track.style.TrackStyle;
import robomuss.rc.util.IInventoryRenderSettings;

public class TrackPieceLoop extends TrackPiece implements IInventoryRenderSettings {

	public TrackPieceLoop(String unlocalized_name, int crafting_cost, int special_render_stages) {
		super(unlocalized_name, crafting_cost, special_render_stages);
	}

	@Override
	public void renderSpecial(int renderStage, TrackStyle type, TileEntityTrack te) {
		rotate(te);
		if(renderStage == 0) {
			//GL11.glTranslatef((float) te.xCoord + 0.5F, (float) te.yCoord + 1.5F, (float) te.zCoord + 0.5F);
			GL11.glRotatef(10f, 0f, 0f, -1f);
			for(int i = 0; i < 19; i++) {
				GL11.glRotatef(10f, 0f, 0f, 1f);
				if(i == 10) {
					GL11.glRotatef(20f, 0f, 1f, 0f);
				}
				IModelCustom model = type.getStandardModel();
				model.renderAll();
				GL11.glRotatef(0f, 0f, 0f, 0f);
				GL11.glTranslatef(0f, 0f, 0f);
			}
		}
		if(renderStage == 1) {
			//GL11.glTranslatef((float) te.xCoord + 0.5F, (float) te.yCoord + 1.5F, (float) te.zCoord + 1.5f);
			GL11.glRotatef(10f, 0f, 0f, 1f);
			for(int i = 0; i < 19; i++) {
				GL11.glRotatef(-10f, 0f, 0f, 1f);
				if(i == 10) {
					GL11.glRotatef(20f, 0f, 1f, 0f);
				}
				IModelCustom model = type.getStandardModel();
				model.renderAll();
				GL11.glRotatef(0f, 0f, 0f, 0f);
				GL11.glTranslatef(0f, 0f, 0f);
			}
		}
	}
	
	@Override
	public float getSpecialX(int renderStage, double x, TileEntityTrack te) {
		if(renderStage == 0) {
			switch(te.direction) {
				case 0 : return (float) (x + 1.5f);
				case 2 : return (float) (x - 0.5f);
				default: return super.getSpecialX(renderStage, x, te);
			}
		}
		else {
			return (float) (x + 0.5f);
		}
	}
	
	@Override
	public float getSpecialZ(int renderStage, double z, TileEntityTrack te) {
		if(renderStage == 0) {
			switch(te.direction) {
				case 1 : return (float) (z + 1.5f);
				case 3 : return (float) (z - 0.5f);
				default: return super.getSpecialZ(renderStage, z, te);
			}
		}
		else {
			return (float) (z + 0.5f);
		}
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
