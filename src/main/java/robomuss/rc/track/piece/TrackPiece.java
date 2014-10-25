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
import robomuss.rc.block.te.TileEntityTrackBase;
import robomuss.rc.entity.EntityTrainDefault;
import robomuss.rc.track.TrackManager;
import robomuss.rc.track.style.TrackStyle;

import java.util.ArrayList;
import java.util.List;

public class TrackPiece {
	public int id;
	
	public String unlocalized_name; 
	public int crafting_cost;
	
	public Block block;
//	private BlockTrackBase blockTrack;

	public int special_render_stages;
	
	public boolean inverted = false;

	private List<TileEntityTrackBase> teList = new ArrayList<TileEntityTrackBase>();
	
	public TrackPiece(String unlocalized_name, int crafting_cost) {
		this.id = RCBlocks.last_track_id++;
		System.out.println(this.id);
		this.unlocalized_name = unlocalized_name;
		this.crafting_cost = crafting_cost;
//		if (RCMod.trackManager.isTrack(block)) {
//			blockTrack = (BlockTrackBase) block;
//		}
	}

	public void addTileEntityToList(TrackPiece type, TileEntityTrackBase teTrack) {
		if (teTrack != null) {
			teList.add(teTrack);
			teTrack.setListTypeAndIndex(type, teList.indexOf(teTrack));
		}
	}

	public void addTileEntityToList(TrackPiece type, TileEntityTrackBase teTrack, int index) {
		if (teTrack != null) {
			teList.add(index, teTrack);
			teTrack.setListTypeAndIndex(type, index);
		}
	}

	public TileEntityTrackBase getTileEntityFromList(int index) {
		return this.teList.size() > index ? this.teList.get(index) : null;
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

	public void renderTileEntity(TrackStyle style, TileEntityTrackBase teTrack, World world, int x, int y, int z) {

	}

	public void render(TrackStyle style, BlockTrackBase blockTrack, World world, int x , int y , int z) {

	}

	public BlockTrackBase getTrack() {
		return TrackManager.isTrack(block) ? (BlockTrackBase) block :  null;
	}
	
	public void rotate(TileEntityTrackBase teTrack, World world, int x, int y, int z) {
//        TileEntityTrackBase tileEntity = (TileEntityTrackBase) world.getTileEntity(x, y, z);

		if (teTrack != null && teTrack.direction == null) {
//            tileEntity.direction = ForgeDirection.SOUTH;
			teTrack.direction = ForgeDirection.getOrientation(TrackManager.getTrackMeta(teTrack.xCoord, teTrack.yCoord, teTrack.zCoord));
		}

		if (teTrack != null && teTrack.direction != null) {
			switch (teTrack.direction) {
				case NORTH: GL11.glRotatef(180f, 180f, 0f, 180f);  break;
				case SOUTH: GL11.glRotatef(180f, -180f, 0f, 180f); break;
				case WEST:  GL11.glRotatef(180f, -180f, 0f, 0f);   break;
				case EAST:  GL11.glRotatef(180f, 0f, 0f, 180f);    break;
			}
		}
	}

	public float[] getRotationOffsetsFromDirection(ForgeDirection direction) {
		switch (direction) {
			case NORTH: return new float[] {180f, 180f, 0f, 180f};
			case SOUTH: return new float[] {180f, -180f, 0f, 180f};
			case WEST:  return new float[] {180f, -180f, 0f, 0f};
			case EAST:  return new float[] {180f, 0f, 0f, 180f};
			default:    return new float[] {180f, -180f, 0f, 180f};
		}
	}

	public void unRotate(BlockTrackBase track, World world, int x, int y, int z) {
        TileEntityTrackBase tileEntity = (TileEntityTrackBase) world.getTileEntity(x, y, z);

		if (track != null && tileEntity.direction == null) {
            tileEntity.direction = ForgeDirection.SOUTH;
		}

		if (track != null && tileEntity.direction != null) {
			switch (tileEntity.direction) {
				case NORTH:
					GL11.glRotatef(180f, -180f, 0f, -180f);
					break;
				case SOUTH:
					GL11.glRotatef(180f, 180f, 0f, -180f);
					break;
				case WEST:
					GL11.glRotatef(180f, 180f, 0f, 0f);
					break;
				case EAST:
					GL11.glRotatef(180f, 0f, 0f, -180f);
					break;
			}
		}
	}

	public float getX(double x, TileEntityTrackBase teTrack, World world, int lx, int ly, int l) {
		return (float) (x + 0.5F);
	}

//	public float getX(double x, BlockTrackBase track, World world, int lx , int ly , int l) {
//		return (float) (x + 0.5F);
//	}

	public float getY(double y, TileEntityTrackBase teTrack, World world, int lx, int ly, int l) {
		return (float) (y + 1.5F);
	}

//	public float getY(double y, BlockTrackBase track, World world, int lx , int ly , int l) {
//		return (float) (y + 1.5F);
//	}

	public float getZ(double z, TileEntityTrackBase teTrack, World world, int lx, int ly, int l) {
		return (float) (z + 0.5F);
	}

//	public float getZ(double z, BlockTrackBase track, World world, int lx , int ly , int l) {
//		return (float) (z + 0.5F);
//	}

	public void renderSpecialTileEntity(int renderStage, TrackStyle style, TileEntityTrackBase teTrack, World world, int x, int y, int z) {

	}

//	public void renderSpecialTileEntity(int renderStage, TrackStyle type, BlockTrackBase track, World world, int x , int y , int z) {
//
//	}

	public void renderSpecialItem(int renderStage, TrackStyle style, BlockTrackBase track, World world, int x, int y, int z) {

	}

	public float getSpecialX(int renderStage, double x, TileEntityTrackBase teTrack, World world, int lx, int ly, int lz) {
		return (float) (x + 0.5F);
	}

//	public float getSpecialX(int renderStage, double x, BlockTrackBase track, World world, int lx , int ly , int lz) {
//		return (float) (x + 0.5F);
//	}

	public float getSpecialY(int renderStage, double y, TileEntityTrackBase teTrack, World world, int lx, int ly, int lz) {
		return (float) (y + 0.5F);
	}

//	public float getSpecialY(int renderStage, double y, BlockTrackBase track, World world, int lx , int ly , int lz) {
//		return (float) (y + 1.5F);
//	}

	public float getSpecialZ(int renderStage, double z, TileEntityTrackBase teTrack, World world, int lx, int ly, int lz) {
		return (float) (z + 0.5F);
	}

//	public float getSpecialZ(int renderStage, double z, BlockTrackBase track, World world, int lx , int ly , int lz) {
//		return (float) (z + 0.5F);
//	}

	public AxisAlignedBB getRenderBoundingBox(World world, int xCoord, int yCoord, int zCoord) {
		return AxisAlignedBB.getBoundingBox(xCoord, yCoord, zCoord, xCoord + 1, yCoord + 1, zCoord + 1);
	}

	public AxisAlignedBB getBlockBounds(IBlockAccess iba, int x, int y, int z) {
		return AxisAlignedBB.getBoundingBox(0, 0, 0, 1, 1, 1);
	}

    public void moveTrain(BlockTrackBase track, EntityTrainDefault entity, TileEntityTrackBase teTrack) {

    }
    
    public TrackPiece invertTrack() {
    	inverted = true;
		return this;
	}
}
