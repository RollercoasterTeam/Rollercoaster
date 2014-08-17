package robomuss.rc.tracks;

import java.util.Arrays;

import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

import robomuss.rc.block.render.TileEntityRenderTrack;
import robomuss.rc.block.te.TileEntityTrack;

public class TrackTypeSlopeDown extends TrackType {

	public TrackTypeSlopeDown(String unlocalized_name, int crafting_cost, int i) {
		super(unlocalized_name, crafting_cost, i);
	}
	
	@Override
	public void renderSpecial(int renderStage, ModelBase model, TileEntityTrack te) {
		rotate(te);
		if(renderStage == 0) {
			model = Arrays.asList(TileEntityRenderTrack.models).get(((TileEntityTrack) te).model + 2);
			
			GL11.glRotatef(45f, 0f, 0f, 1f);
			model.render((Entity) null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
		}
		else if(renderStage == 1) {
			model = Arrays.asList(TileEntityRenderTrack.models).get(((TileEntityTrack) te).model);
			
			model.render((Entity) null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
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
}
