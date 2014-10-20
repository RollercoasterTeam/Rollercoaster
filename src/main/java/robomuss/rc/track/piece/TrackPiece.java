package robomuss.rc.track.piece;

import net.minecraft.block.Block;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import net.minecraftforge.common.util.ForgeDirection;
import org.lwjgl.opengl.GL11;

import robomuss.rc.RCMod;
//import robomuss.rc.block.BlockTrack;
import robomuss.rc.block.BlockTrackBase;
import robomuss.rc.block.RCBlocks;
//import robomuss.rc.block.te.TileEntityTrack;
import robomuss.rc.entity.EntityTrainDefault;
import robomuss.rc.track.style.TrackStyle;

public class TrackPiece {
	
	public int id;
	
	public String unlocalized_name; 
	public int crafting_cost;
	
	public Block block;
//	private BlockTrackBase blockTrack;

	public int special_render_stages;
	
	public boolean inverted = false;
	
	public TrackPiece(String unlocalized_name, int crafting_cost) {
		this.id = RCBlocks.last_track_id++;
		this.unlocalized_name = unlocalized_name;
		this.crafting_cost = crafting_cost;
//		if (RCMod.trackManager.isTrack(block)) {
//			blockTrack = (BlockTrackBase) block;
//		}
	}
	
	public TrackPiece(String unlocalized_name, int crafting_cost, int special_render_stages) {
		this.id = RCBlocks.last_track_id++;
		this.unlocalized_name = unlocalized_name;
		this.crafting_cost = crafting_cost;
		this.special_render_stages = special_render_stages;
//		if (RCMod.trackManager.isTrack(block)) {
//			blockTrack = (BlockTrackBase) block;
//		}
	}

	public void render(TrackStyle style, BlockTrackBase block) {
		
	}
	
	public BlockTrackBase getTrack() {
		return RCMod.trackManager.isTrack(block) ? (BlockTrackBase) block :  null;
	}
	
	public static void rotate(BlockTrackBase track) {
		if (track != null && track.direction == null) {
			track.direction = ForgeDirection.SOUTH;
		}

		if (track != null && track.direction != null) {
			switch (track.direction) {
				case NORTH:         //NORTH
					GL11.glRotatef(180f, 180f, 0f, 180f);
					break;
				case SOUTH:         //SOUTH
					GL11.glRotatef(180f, -180f, 0f, 180f);
					break;
				case WEST:         //WEST
					GL11.glRotatef(180f, -180f, 0f, 0f);
					break;
				case EAST:         //EAST
					GL11.glRotatef(180f, 0f, 0f, 180f);
					break;
			}
		}
	}

	public float getX(double x, BlockTrackBase track) {
		return (float) (x + 0.5F);
	}

	public float getY(double y, BlockTrackBase track) {
		return (float) (y + 1.5F);
	}

	public float getZ(double z, BlockTrackBase track) {
		return (float) (z + 0.5F);
	}

	public void renderSpecial(int renderStage, TrackStyle type, BlockTrackBase track) {
		
	}

	public float getSpecialX(int renderStage, double x, BlockTrackBase track) {
		return (float) (x + 0.5F);
	}

	public float getSpecialY(int renderStage, double y, BlockTrackBase track) {
		return (float) (y + 1.5F);
	}

	public float getSpecialZ(int renderStage, double z, BlockTrackBase track) {
		return (float) (z + 0.5F);
	}

	public AxisAlignedBB getRenderBoundingBox(World world, int xCoord, int yCoord, int zCoord) {
		return AxisAlignedBB.getBoundingBox(xCoord, yCoord, zCoord, xCoord + 1, yCoord + 1, zCoord + 1);
	}

	public AxisAlignedBB getBlockBounds(IBlockAccess iba, int x, int y, int z) {
		return AxisAlignedBB.getBoundingBox(0, 0, 0, 1, 1, 1);
	}

    public void moveTrain(BlockTrackBase track, EntityTrainDefault entity) {

    }
    
    public TrackPiece invertTrack() {
    	inverted = true;
		return this;
	}
}
