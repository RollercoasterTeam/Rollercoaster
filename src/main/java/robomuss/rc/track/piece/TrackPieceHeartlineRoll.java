package robomuss.rc.track.piece;

import net.minecraft.client.model.ModelBase;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.client.model.IModelCustom;

import net.minecraftforge.common.util.ForgeDirection;
import org.lwjgl.opengl.GL11;

import robomuss.rc.block.BlockTrackBase;
//import robomuss.rc.block.te.TileEntityTrack;
import robomuss.rc.block.te.TileEntityTrackBase;
import robomuss.rc.track.style.TrackStyle;
import robomuss.rc.util.IInventoryRenderSettings;

public class TrackPieceHeartlineRoll extends TrackPiece implements IInventoryRenderSettings {

	public TrackPieceHeartlineRoll(String unlocalized_name, int crafting_cost, int special_render_stages) {
		super(unlocalized_name, crafting_cost, special_render_stages);
	}

	
	@Override
	public void renderSpecial(int renderStage, TrackStyle type, BlockTrackBase track, World world, int x , int y , int z) {
		rotate(track, world, x, y, z);
		/*if(renderStage <= 9) {
			GL11.glRotatef(-3f * renderStage, 0, 1, 0);
		}
		else {
			GL11.glRotatef(3f * renderStage, 0, 1, 0);
		}*/
		GL11.glRotatef(20f * renderStage, 1, 0, 0);
		IModelCustom model = type.getStandardModel();
		model.renderAll();
	}
	
	@Override
	public float getSpecialX(int renderStage, double x, BlockTrackBase track, World world, int lx , int ly , int lz) {
        TileEntityTrackBase tileEntityTrackBase = (TileEntityTrackBase) world.getTileEntity(lx, ly, lz);
		if(tileEntityTrackBase.direction == ForgeDirection.WEST || tileEntityTrackBase.direction == ForgeDirection.EAST) {
			return (float) (x + 0.5f + (renderStage * 0.5f));
		}
		else {
			return (float) (x + 0.5f);
		}
	}
	
	@Override
	public float getSpecialZ(int renderStage, double z, BlockTrackBase track, World world, int lx , int ly , int lz) {
        TileEntityTrackBase tileEntityTrackBase = (TileEntityTrackBase) world.getTileEntity(lx, ly, lz);
		if(tileEntityTrackBase.direction == ForgeDirection.SOUTH || tileEntityTrackBase.direction == ForgeDirection.NORTH) {
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
