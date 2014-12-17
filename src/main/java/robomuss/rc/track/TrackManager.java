package robomuss.rc.track;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import robomuss.rc.block.BlockTrackBase;
import robomuss.rc.block.te.TileEntityTrackBase;
import robomuss.rc.track.piece.*;

import java.util.ArrayList;
import java.util.List;

/** TRACK MANAGER START
 *
 * Valid rail metadata is defined as follows:
 * 0x0: flat track going North-South
 * 0x1: flat track going West-East
 * 0x2: track ascending to the East
 * 0x3: track ascending to the West
 * 0x4: track ascending to the North
 * 0x5: track ascending to the South
 * 0x6: WestNorth corner (connecting East and South)
 * 0x7: EastNorth corner (connecting West and South)
 * 0x8: EastSouth corner (connecting West and North)
 * 0x9: WestSouth corner (connecting East and North)
 *
 */
public class TrackManager {
	private List neighbors = new ArrayList();
//	private List<TileEntityTrackBase> teList = new ArrayList<TileEntityTrackBase>();

	public TrackManager() {}

	public static final boolean isBlockAtCoordsTrack(World world, BlockPos pos) {
		return isTrack(world.getBlockState(pos).getBlock());
	}

	public static final boolean isTrack(Block block) {
		return block instanceof BlockTrackBase;
	}

	public static final boolean isTrack(TileEntity te) {
		return te instanceof TileEntityTrackBase;
	}

	public static final BlockTrackBase getTrackAtCoords(World world, BlockPos pos) {
		return isBlockAtCoordsTrack(world, pos) ? (BlockTrackBase) world.getBlockState(pos) : null;
	}

    public static final TileEntityTrackBase getTrackTileAtCoords(World world, BlockPos pos) {
        return isBlockAtCoordsTrack(world, pos) ? (TileEntityTrackBase) world.getTileEntity(pos) : null;
    }

	public static final IBlockState getTrackState(World world, BlockPos pos) {
		return world.getBlockState(pos);
	}

	public static final boolean isSloped(TrackPiece track_type) {
		return (track_type == TrackHandler.findTrackType("slope_up") || track_type == TrackHandler.findTrackType("slope") || track_type == TrackHandler.findTrackType("slope_down"));
	}

	public static final boolean isSloped(int track_type) {
		if (track_type == 2 || track_type == 3 || track_type == 4) {
			return true;
		}
		return false;
	}

	/**
	 * Returns an integer based on the type of TrackPiece BlockTrackBase.track_type extends.
	 *
	 *  0: Horizontal
	 *  1: Corner
	 *  2: Slope Up
	 *  3: Slope
	 *  4: Slope Down
	 * -1: None of the above
	 *
	 * @param track
	 * @return
	 */

	public static final int getTrackType(BlockTrackBase track) {
		if (track.track_type instanceof TrackPieceHorizontal) {
			return 0;
		} else if (track.track_type instanceof TrackPieceCorner) {
			return 1;
		} else if (track.track_type instanceof TrackPieceSlopeUp) {
			return 2;
		} else if (track.track_type instanceof TrackPieceSlope) {
			return 3;
		} else if (track.track_type instanceof TrackPieceSlopeDown) {
			return 4;
		} else {
			return -1;
		}
	}

	public static final TileEntityTrackBase getTileEntityFromType(TrackPiece type, int index) {
		if (type != null) {
			TileEntityTrackBase teTrack = type.getTileEntityFromList(index);
			return teTrack;
		}
		return null;
	}

	private boolean checkForTrackAboveAndBelow(World world, BlockPos pos) {
		return isBlockAtCoordsTrack(world, pos) || isBlockAtCoordsTrack(world, pos.up()) || isBlockAtCoordsTrack(world, pos.down());
	}

	private boolean isTrackInNeighborList(TileEntityTrackBase teTrack) {
		for (int i = 0; i < this.neighbors.size(); i++) {
			BlockPos pos = (BlockPos) this.neighbors.get(i);

			if (pos == teTrack.getPos()) {
				return true;
			}
		}

		return false;
	}

	private boolean isTrackInNeighborList(BlockPos pos) {
		for (int i = 0; i < this.neighbors.size(); i++) {
			BlockPos position = (BlockPos) this.neighbors.get(i);

			if (pos == position) {
				return true;
			}
		}

		return false;
	}

	private int getNumberOfAdjacentTracks(World world, TileEntityTrackBase teTrack) {
		int i = 0;

		if (isBlockAtCoordsTrack(world, teTrack.getPos().north())) {
			i++;
		}

		if (isBlockAtCoordsTrack(world, teTrack.getPos().south())) {
			i++;
		}

		if (isBlockAtCoordsTrack(world, teTrack.getPos().west())) {
			i++;
		}

		if (isBlockAtCoordsTrack(world, teTrack.getPos().east())) {
			i++;
		}

		return i;
	}

	private boolean isTrackStillNeighbor(TileEntityTrackBase teTrack) {
		return this.isTrackInNeighborList(teTrack) || (this.neighbors.size() != 2 && this.neighbors.isEmpty() ? true : true);
	}

	private boolean isTrackStillNeighbor(World world, BlockPos pos) {
		TileEntityTrackBase teTrack = (TileEntityTrackBase) world.getTileEntity(pos);
		return teTrack != null && this.isTrackStillNeighbor(teTrack);
	}
}
