package robomuss.rc.tracks;

import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

import robomuss.rc.block.te.TileEntityTrack;
import robomuss.rc.entity.OldEntityTrain;

public class TrackTypeSlopeUp extends TrackType {

	public TrackTypeSlopeUp(String unlocalized_name, int crafting_cost) {
		super(unlocalized_name, crafting_cost);
	}

	@Override
	public void render(ModelBase model, TileEntityTrack te) {
		rotate(te);
		
		GL11.glRotatef(45f, 0f, 0f, 1f);
		model.render((Entity) null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
	
		GL11.glPushMatrix();
		GL11.glRotatef(-45f, 0f, 0f, 1f);
		model.render((Entity) null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
		GL11.glPopMatrix();
	}
	
	@Override
	public AxisAlignedBB getRenderBoundingBox(World world, int xCoord, int yCoord, int zCoord) {
		return AxisAlignedBB.getBoundingBox(xCoord - 1, yCoord, zCoord - 1, xCoord + 2, yCoord + 2, zCoord + 2);
	}

    @Override
    public void onTrainCollidedWithTrack(World world, int x, int y, int z, OldEntityTrain train) {
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
            train.motionY += 0.1;
        }
    }

}
