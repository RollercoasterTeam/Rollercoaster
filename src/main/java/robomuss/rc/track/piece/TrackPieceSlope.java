package robomuss.rc.track.piece;

import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

import robomuss.rc.block.te.TileEntityTrack;
import robomuss.rc.entity.EntityTrainDefault;
import robomuss.rc.track.style.TrackStyle;
import robomuss.rc.util.IInventoryRenderSettings;

public class TrackPieceSlope extends TrackPiece implements IInventoryRenderSettings {

	public TrackPieceSlope(String unlocalized_name, int crafting_cost) {
		super(unlocalized_name, crafting_cost);
	}

	@Override
	public void render(TrackStyle type, TileEntityTrack te) {
		rotate(te);

		ModelBase model = type.getLargeModel();
		
		GL11.glRotatef(45f, 0f, 0f, 1f);
		model.render((Entity) null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
	}
	
	@Override
	public float getX(double x, TileEntityTrack te) {
		return (float) (x + 0.5F);
	}
	
	@Override
	public AxisAlignedBB getRenderBoundingBox(World world, int xCoord, int yCoord, int zCoord) {
		return AxisAlignedBB.getBoundingBox(xCoord - 1, yCoord, zCoord - 1, xCoord + 2, yCoord + 2, zCoord + 2);
	}
	
	/*@Override
	public AxisAlignedBB getBlockBounds(IBlockAccess iba, int x, int y, int z) {
		TileEntityTrack te = (TileEntityTrack) iba.getTileEntity(x, y, z);
		if(te.direction == 0) {
			return AxisAlignedBB.getBoundingBox(0, 0, 0, 1, 1, 2);
		}
		else if(te.direction == 1) {
			return AxisAlignedBB.getBoundingBox(1, 0, 0, -1, 1, 1);
		}
		else if(te.direction == 2) {
			return AxisAlignedBB.getBoundingBox(0, 0, -1, 1, 1, 1);
		}
		else if(te.direction == 3) {
			return AxisAlignedBB.getBoundingBox(0, 0, 0, 2, 1, 1);
		}
		return super.getBlockBounds(iba, x, y, z);
	}*/
	
	@Override
	public void moveTrain(TileEntityTrack te, EntityTrainDefault entity) {
		if(entity.riddenByEntity != null && entity.riddenByEntity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) entity.riddenByEntity;
			player.swingProgressInt = 90;
		}
		if(te.direction == 0) {
			if(entity.direction == 0) {
				entity.posY += 1f;
				entity.posZ += 1f;
				
			}
			if(entity.direction == 2) {
				entity.posY -= 1f;
				entity.posZ -= 1f;
			}
		}
		if(te.direction == 1) {
			if(entity.direction == 1) {
				entity.posY -= 1f;
				entity.posX += 1f;
			}
			if(entity.direction == 3) {
				entity.posY += 1f;
				entity.posX -= 1f;
			}
		}
		if(te.direction == 2) {
			if(entity.direction == 2) {
				entity.posY += 1f;
				entity.posZ -= 1f;
			}
			if(entity.direction == 0) {
				entity.posY -= 1f;
				entity.posZ += 1f;
			}
		}
		if(te.direction == 3) {
			if(entity.direction == 1) {
				entity.posY += 1f;
				entity.posX += 1f;
			}
			if(entity.direction == 3) {
				entity.posY -= 1f;
				entity.posX -= 1f;
			}
		}
	}

	@Override
	public float getInventoryX() {
		return 0.6f;
	}

	@Override
	public float getInventoryY() {
		return 0.45f;
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
