package robomuss.rc.tracks;

import java.util.Arrays;

import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;
import robomuss.rc.block.render.TileEntityRenderTrack;
import robomuss.rc.block.te.TileEntityTrack;
import robomuss.rc.entity.EntityTrain;


public class TrackTypeCurve extends TrackType {

	public TrackTypeCurve(String unlocalized_name, int crafting_cost, int i) {
		super(unlocalized_name, crafting_cost, i);
	}
	
	@Override
	public void renderSpecial(int renderStage, ModelBase model, TileEntityTrack te) {
		rotate(te);
		
		model = Arrays.asList(TileEntityRenderTrack.models).get(((TileEntityTrack) te).model + 3);
		
		model.render((Entity) null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
		
		/*if(renderStage == 0) {
			model.render((Entity) null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
		}
		else if(renderStage == 1) {
			GL11.glRotatef(45f, 0, 1, 0);
			model.render((Entity) null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
		}
		else if(renderStage == 2) {
			GL11.glRotatef(90f, 0, 1, 0);
			model.render((Entity) null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
		}*/
	}
	
	/*@Override
	public float getSpecialX(int renderStage, double x, TileEntityTrack te) {
		if(renderStage == 1) {
			switch(te.direction) {
				case 1 : return (float) (x - 0.5F);
				case 3 : return (float) (x + 1.5F);
				default: return super.getSpecialX(renderStage, x, te);
			}
		}
		else {
			return super.getSpecialX(renderStage, x, te);
		}
	}
	
	@Override
	public float getSpecialZ(int renderStage, double z, TileEntityTrack te) {
		if(renderStage == 1) {
			switch(te.direction) {
				case 1 : return (float) (z + 0.5F);
				case 3 : return (float) (z + 0.5F);
				default: return super.getSpecialX(renderStage, z, te);
			}
		}
		else {
			return super.getSpecialX(renderStage, z, te);
		}
	}*/


    @Override
    public void onTrainCollidedWithTrack(World world, int x, int y, int z, EntityTrain train) {
        super.onTrainCollidedWithTrack(world, x, y, z, train);
        if(world.isBlockIndirectlyGettingPowered(x, y -1 , z))
            return;
        double m_speed = 0.2D;
        TileEntityTrack tile = (TileEntityTrack) world.getTileEntity(x, y, z);
        int a = tile.direction;
        int[] ax = { 0, 1, 0, -1 };
        int[] az = { -1, 0, 1, 0 };

        if (train != null) {

            if ((ax[a] == 0) && (Math.abs(x + 0.5D - train.posX) < 0.5D) && (Math.abs(x + 0.5D - train.posX) > 0.1D)) {
                train.motionX += Math.signum(x + 0.5D - train.posX) * Math.min(m_speed, Math.abs(x + 0.5D - train.posX)) / 1.2D;
            }

            if ((az[a] == 0) && (Math.abs(z + 0.5D - train.posZ) < 0.5D) && (Math.abs(z + 0.5D - train.posZ) > 0.1D)) {
                train.motionZ += Math.signum(z + 0.5D - train.posZ) * Math.min(m_speed, Math.abs(z + 0.5D - train.posZ)) / 1.2D;
            }

            train.motionX += ax[a] * m_speed;

            train.motionZ += az[a] * m_speed;
            train.posY = y + 0.41;
        }
    }

}
