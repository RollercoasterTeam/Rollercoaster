package robomuss.rc.track;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
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

	public static final boolean isBlockAtCoordsTrack(World world, int x, int y, int z) {
		return isTrack(world.getBlock(x, y, z));
	}

	public static final boolean isTrack(Block block) {
		return block instanceof BlockTrackBase;
	}

	public static final boolean isTrack(TileEntity te) {
		return te instanceof TileEntityTrackBase;
	}

	public static final BlockTrackBase getTrackAtCoords(World world, int x, int y, int z) {
		return isBlockAtCoordsTrack(world, x, y, z) ? (BlockTrackBase) world.getBlock(x, y, z) : null;
	}

    public static final TileEntityTrackBase getTrackTileAtCoords(World world, int x, int y, int z) {
        return isBlockAtCoordsTrack(world, x, y, z) ? (TileEntityTrackBase) world.getTileEntity(x, y, z) : null;
    }

	public static final int getTrackMeta(World world, int x, int y, int z) {
		return world.getBlockMetadata(x, y, z);
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

	public static final int getPlayerFacing(EntityLivingBase player) {
		return MathHelper.floor_double((double)(player.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
	}

	public static final ForgeDirection getDirectionFromPlayerFacing(EntityLivingBase player) {
//		System.out.println("player facing: " + getPlayerFacing(player));
		switch (getPlayerFacing(player)) {
			case 0: return ForgeDirection.SOUTH;
			case 1: return ForgeDirection.WEST;
			case 2: return ForgeDirection.NORTH;
			case 3: return ForgeDirection.EAST;
		}
		return null;
	}

//	//TODO: test this
//	public void findNeighbors(int x, int y, int z, ForgeDirection direction, int track_type) {
//		this.neighbors.clear();
//
//		switch (track_type) {
//			case 0:                                                                                 //Horizontal
//				switch (direction) {
//					case NORTH:
//					case SOUTH:
//						this.neighbors.add(new ChunkPosition(x, y, z - 1));          //NORTH
//						this.neighbors.add(new ChunkPosition(x, y, z + 1));          //SOUTH
//						break;
//					case WEST:
//					case EAST:
//						this.neighbors.add(new ChunkPosition(x - 1, y, z));          //WEST
//						this.neighbors.add(new ChunkPosition(x + 1, y, z));          //EAST
//						break;
//				}
//				break;
//			case 1:                                                                                 //Corner
//				switch (direction) {
//					case WEST:
//						this.neighbors.add(new ChunkPosition(x + 1, y, z));          //EAST
//						this.neighbors.add(new ChunkPosition(x, y, z - 1));          //NORTH
//						break;
//					case EAST:
//						this.neighbors.add(new ChunkPosition(x - 1, y, z));          //WEST
//						this.neighbors.add(new ChunkPosition(x, y, z + 1));          //SOUTH
//						break;
//					case NORTH:
//						this.neighbors.add(new ChunkPosition(x + 1, y, z));          //EAST
//						this.neighbors.add(new ChunkPosition(x, y, z + 1));          //SOUTH
//						break;
//					case SOUTH:
//						this.neighbors.add(new ChunkPosition(x - 1, y, z));          //WEST
//						this.neighbors.add(new ChunkPosition(x, y, z - 1));          //NORTH
//						break;
//				}
//				break;
//			case 2:                                                                                 //Slope Up
//				switch (direction) {
//					case EAST:
//						this.neighbors.add(new ChunkPosition(x - 1, y, z));          //adjacent corner/horizontal track WEST
//						this.neighbors.add(new ChunkPosition(x + 1, y, z));          //dummy SlopeUp track EAST
//						break;
//					case WEST:
//						this.neighbors.add(new ChunkPosition(x + 1, y, z));          //adjacent corner/horizontal track EAST
//						this.neighbors.add(new ChunkPosition(x - 1, y, z));          //dummy SlopeUp track WEST
//						break;
//					case NORTH:
//						this.neighbors.add(new ChunkPosition(x, y, z + 1));          //adjacent corner/horizontal track SOUTH
//						this.neighbors.add(new ChunkPosition(x, y, z - 1));          //dummy SlopeUp track NORTH
//						break;
//					case SOUTH:
//						this.neighbors.add(new ChunkPosition(x, y, z - 1));          //adjacent corner/horizontal track NORTH
//						this.neighbors.add(new ChunkPosition(x, y, z + 1));          //dummy SlopeUp track SOUTH
//						break;
//				}
//				break;
//			case 3:                                                                                 //Slope
//				switch (direction) {
//					case EAST:
//						this.neighbors.add(new ChunkPosition(x - 1, y, z));          //adjacent corner/horizontal track WEST
//						this.neighbors.add(new ChunkPosition(x + 1, y, z));          //dummy Slope track EAST
//						break;
//					case WEST:
//						this.neighbors.add(new ChunkPosition(x + 1, y, z));          //adjacent corner/horizontal track EAST
//						this.neighbors.add(new ChunkPosition(x - 1, y, z));          //dummy Slope track WEST
//						break;
//					case NORTH:
//						this.neighbors.add(new ChunkPosition(x, y, z + 1));          //adjacent corner/horizontal track SOUTH
//						this.neighbors.add(new ChunkPosition(x, y, z - 1));          //dummy Slope track NORTH
//						break;
//					case SOUTH:
//						this.neighbors.add(new ChunkPosition(x, y, z - 1));          //adjacent corner/horizontal track NORTH
//						this.neighbors.add(new ChunkPosition(x, y, z + 1));          //dummy Slope track SOUTH
//						break;
//				}
//				break;
//			case 4:                                                                                 //Slope Down TODO: might have different dummy placements, TBD.
//				switch (direction) {
//					case EAST:
//						this.neighbors.add(new ChunkPosition(x - 1, y, z));          //adjacent corner/horizontal track WEST
//						this.neighbors.add(new ChunkPosition(x + 1, y, z));          //dummy SlopeDown track EAST
//						break;
//					case WEST:
//						this.neighbors.add(new ChunkPosition(x + 1, y, z));          //adjacent corner/horizontal track EAST
//						this.neighbors.add(new ChunkPosition(x - 1, y, z));          //dummy SlopeDown track WEST
//						break;
//					case NORTH:
//						this.neighbors.add(new ChunkPosition(x, y, z + 1));          //adjacent corner/horizontal track SOUTH
//						this.neighbors.add(new ChunkPosition(x, y, z - 1));          //dummy SlopeDown track NORTH
//						break;
//					case SOUTH:
//						this.neighbors.add(new ChunkPosition(x, y, z - 1));          //adjacent corner/horizontal track NORTH
//						this.neighbors.add(new ChunkPosition(x, y, z + 1));          //dummy SlopeDown track SOUTH
//						break;
//				}
//				break;
//			default:
//				break;
//		}
//	}
//	TODO: test this
//	private void cleanNeighborsList() {
//		for (int i = 0; i < this.neighbors.size(); i++) {
////			BlockTrackBase track = this.getTrackFromChunkPosition((ChunkPosition) this.neighbors.get(i));
//			TileEntityTrackBase teTrack =
//			if (track != null && this.isTrackInNeighborList(track)) {
//				this.neighbors.set(i, new ChunkPosition(((ChunkPosition) this.neighbors.get(i)).chunkPosX, ((ChunkPosition) this.neighbors.get(i)).chunkPosY, ((ChunkPosition) this.neighbors.get(i)).chunkPosZ));
//			} else {
//				this.neighbors.remove(i--);
//			}
//		}
//	}

	private boolean checkForTrackAboveAndBelow(World world, int x, int y, int z) {
		return isBlockAtCoordsTrack(world, x, y, z) ? true : (isBlockAtCoordsTrack(world, x, y + 1, z) ? true : isBlockAtCoordsTrack(world, x, y - 1, z));
	}

	//TODO: remove ChunkPositions?
	private boolean isTrackInNeighborList(TileEntityTrackBase teTrack) {
		for (int i = 0; i < this.neighbors.size(); i++) {
			ChunkPosition position = (ChunkPosition) this.neighbors.get(i);

			if (position.chunkPosX == teTrack.xCoord && position.chunkPosY == teTrack.yCoord && position.chunkPosZ == teTrack.zCoord) {
				return true;
			}
		}
		return false;
	}

	private boolean isTrackInNeighborList(int x, int y, int z) {
		for (int i = 0; i < this.neighbors.size(); i++) {
			ChunkPosition position = (ChunkPosition) this.neighbors.get(i);

			if (position.chunkPosX == x && position.chunkPosY == y && position.chunkPosZ == z) {
				return true;
			}
		}
		return false;
	}

	private int getNumberOfAdjacentTracks(World world, TileEntityTrackBase teTrack) {
		int i = 0;
		if (isBlockAtCoordsTrack(world, teTrack.xCoord, teTrack.yCoord, teTrack.zCoord - 1)) {
			i++;
		}

		if (isBlockAtCoordsTrack(world, teTrack.xCoord, teTrack.yCoord, teTrack.zCoord + 1)) {
			i++;
		}

		if (isBlockAtCoordsTrack(world, teTrack.xCoord - 1, teTrack.yCoord, teTrack.zCoord)) {
			i++;
		}

		if (isBlockAtCoordsTrack(world, teTrack.xCoord + 1, teTrack.yCoord, teTrack.zCoord)) {
			i++;
		}

		return i;
	}

	private boolean isTrackStillNeighbor(TileEntityTrackBase teTrack) {
		return this.isTrackInNeighborList(teTrack) || (this.neighbors.size() != 2 && this.neighbors.isEmpty() ? true : true);
	}

	private boolean isTrackStillNeighbor(World world, int x, int y, int z) {
//		BlockTrackBase track = this.getTrackFromChunkPosition(new ChunkPosition(x, y, z));
		TileEntityTrackBase teTrack = (TileEntityTrackBase) world.getTileEntity(x, y, z);
		if (teTrack == null) {
			return false;
		} else {
//			this.cleanNeighborsList();
			return this.isTrackStillNeighbor(teTrack);
		}
	}


}
