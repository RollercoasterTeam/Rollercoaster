package robomuss.rc.track.piece;

import net.minecraft.block.Block;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import robomuss.rc.block.BlockTrackBase;
import robomuss.rc.block.RCBlocks;
import robomuss.rc.block.te.TileEntityTrackBase;
import robomuss.rc.entity.EntityTrainDefault;
import robomuss.rc.track.TrackManager;
import robomuss.rc.track.style.TrackStyle;

import java.util.ArrayList;
import java.util.List;

public class TrackPiece {
	public int    id;
	public int    crafting_cost;
	public int    render_stage;
	public String unlocalized_name;
	public Block  block;
	public boolean inverted = false;

	private List<TileEntityTrackBase> teList = new ArrayList<TileEntityTrackBase>();

	public TrackPiece(String unlocalized_name, int crafting_cost, int render_stage) {
		this.id = RCBlocks.last_track_id++;
		this.unlocalized_name = unlocalized_name;
		this.crafting_cost = crafting_cost;
		this.render_stage = render_stage;
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
		return this.teList.size() > index && index >= 0 ? this.teList.get(index) : null;
	}

	public void renderTileEntity(int render_stage, TrackStyle style, TileEntityTrackBase teTrack, World world, BlockPos pos) {}

	//TODO: models!
//	public void renderItem(int render_stage, IItemRenderer.ItemRenderType render_type, TrackStyle style, BlockTrackBase blockTrack, World world, int x , int y , int z) {}

	public BlockTrackBase getTrack() {
		return TrackManager.isTrack(block) ? (BlockTrackBase) block : null;
	}

	//TODO: figure out blockStates!
	public void rotate(TileEntityTrackBase teTrack, World world, BlockPos pos) {
		//rotate based on property
	}

	public BlockPos getRenderPos(int render_stage, double x, double y, double z, TileEntityTrackBase teTrack, World world, int lx, int ly, int lz) {
		return new BlockPos(x + 0.5f, y + 1.5f, z + 0.5f);
	}

	public AxisAlignedBB getRenderBoundingBox(World world, BlockPos pos) {
		return AxisAlignedBB.fromBounds(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 1, pos.getY() + 1, pos.getZ() + 1);
	}

	public AxisAlignedBB getBlockBounds(IBlockAccess iba, BlockPos pos) {
		return AxisAlignedBB.fromBounds(0, 0, 0, 1, 1, 1);
	}

    public void moveTrain(BlockTrackBase track, EntityTrainDefault entity, TileEntityTrackBase teTrack) {}
    
    public TrackPiece invertTrack() {
    	inverted = true;
		return this;
	}
}
