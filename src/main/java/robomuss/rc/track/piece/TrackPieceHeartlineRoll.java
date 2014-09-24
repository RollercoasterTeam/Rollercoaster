package robomuss.rc.track.piece;

import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

import robomuss.rc.block.te.TileEntityTrack;
import robomuss.rc.track.style.RollercoasterType;
import robomuss.rc.util.IInventoryRenderSettings;

public class TrackPieceHeartlineRoll extends TrackPiece implements IInventoryRenderSettings {

	public TrackPieceHeartlineRoll(String unlocalized_name, int crafting_cost, int special_render_stages) {
		super(unlocalized_name, crafting_cost, special_render_stages);
	}

	
	@Override
	public void renderSpecial(int renderStage, RollercoasterType type, TileEntityTrack te) {
		rotate(te);
		/*if(renderStage <= 9) {
			GL11.glRotatef(-3f * renderStage, 0, 1, 0);
		}
		else {
			GL11.glRotatef(3f * renderStage, 0, 1, 0);
		}*/
		GL11.glRotatef(20f * renderStage, 1, 0, 0);
		ModelBase model = type.getStandardModel();
		model.render((Entity) null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
	}
	
	@Override
	public float getSpecialX(int renderStage, double x, TileEntityTrack te) {
		if(te.direction == 1 || te.direction == 3) {
			return (float) (x + 0.5f + (renderStage * 0.5f));
		}
		else {
			return (float) (x + 0.5f);
		}
	}
	
	@Override
	public float getSpecialZ(int renderStage, double z, TileEntityTrack te) {
		if(te.direction == 0 || te.direction == 2) {
			return (float) (z + 0.5f + (renderStage * 0.5f));
		}
		else {
			return (float) (z + 0.5f);
		}
	}
	
	@Override
	public AxisAlignedBB getRenderBoundingBox(World world, int xCoord, int yCoord, int zCoord) {
		return AxisAlignedBB.getBoundingBox(xCoord, yCoord, zCoord, xCoord + 10, yCoord + 3, zCoord + 10);
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
	public boolean useIcon() {
		return true;
	}


	@Override
	public float getInventoryRotation() {
		return 0;
	}
}
