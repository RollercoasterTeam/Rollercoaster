package robomuss.rc.track;

import net.minecraft.client.Minecraft;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import robomuss.rc.block.BlockTrackBase;
import net.minecraft.block.Block;
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
	private static World world;
	private List neighbors = new ArrayList();
//	private int focusX;
//	private int focusY;
//	private int focusZ;
//	private int focusType;
//	private ForgeDirection focusDirection;
//	private BlockTrackBase focusBlock;

	public TrackManager() {
		this.world = Minecraft.getMinecraft().theWorld;
//		this.focusX = focusX;
//		this.focusY = focusY;
//		this.focusZ = focusZ;
//		this.focusBlock = BlockTrackBase.getTrackAtCoords(this.world, this.focusX, this.focusY, this.focusZ);
//		this.focusDirection = focusBlock.direction;
//		this.focusType = BlockTrackBase.getTrackType(focusBlock);
//		this.findNeighbors(focusDirection, focusType);
	}

	public static final boolean isBlockAtCoordsTrack(ChunkPosition position) {
		return isTrack(world.getBlock(position.chunkPosX, position.chunkPosY, position.chunkPosZ));
	}

	public static final boolean isBlockAtCoordsTrack(int x, int y, int z) {
		return isTrack(world.getBlock(x, y, z));
	}

	public static final boolean isTrack(Block block) {
		return block instanceof BlockTrackBase;
	}

	public static final BlockTrackBase getTrackAtCoords(int x, int y, int z) {
		return isBlockAtCoordsTrack(x, y, z) ? (BlockTrackBase) world.getBlock(x, y, z) : null;
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

	private void findNeighbors(int x, int y, int z, ForgeDirection direction, int track_type) {
		this.neighbors.clear();

		switch (track_type) {
			case 0:                                                                                 //Horizontal
				switch (direction) {
					case NORTH:
					case SOUTH:
						this.neighbors.add(new ChunkPosition(x, y, z - 1));          //NORTH
						this.neighbors.add(new ChunkPosition(x, y, z + 1));          //SOUTH
						break;
					case WEST:
					case EAST:
						this.neighbors.add(new ChunkPosition(x - 1, y, z));          //WEST
						this.neighbors.add(new ChunkPosition(x + 1, y, z));          //EAST
						break;
				}
				break;
			case 1:                                                                                 //Corner
				switch (direction) {
					case WEST:
						this.neighbors.add(new ChunkPosition(x + 1, y, z));          //EAST
						this.neighbors.add(new ChunkPosition(x, y, z - 1));          //NORTH
						break;
					case EAST:
						this.neighbors.add(new ChunkPosition(x - 1, y, z));          //WEST
						this.neighbors.add(new ChunkPosition(x, y, z + 1));          //SOUTH
						break;
					case NORTH:
						this.neighbors.add(new ChunkPosition(x + 1, y, z));          //EAST
						this.neighbors.add(new ChunkPosition(x, y, z + 1));          //SOUTH
						break;
					case SOUTH:
						this.neighbors.add(new ChunkPosition(x - 1, y, z));          //WEST
						this.neighbors.add(new ChunkPosition(x, y, z - 1));          //NORTH
						break;
				}
				break;
			case 2:                                                                                 //Slope Up
				switch (direction) {
					case EAST:
						this.neighbors.add(new ChunkPosition(x - 1, y, z));          //adjacent corner/horizontal track WEST
						this.neighbors.add(new ChunkPosition(x + 1, y, z));          //dummy SlopeUp track EAST
						break;
					case WEST:
						this.neighbors.add(new ChunkPosition(x + 1, y, z));          //adjacent corner/horizontal track EAST
						this.neighbors.add(new ChunkPosition(x - 1, y, z));          //dummy SlopeUp track WEST
						break;
					case NORTH:
						this.neighbors.add(new ChunkPosition(x, y, z + 1));          //adjacent corner/horizontal track SOUTH
						this.neighbors.add(new ChunkPosition(x, y, z - 1));          //dummy SlopeUp track NORTH
						break;
					case SOUTH:
						this.neighbors.add(new ChunkPosition(x, y, z - 1));          //adjacent corner/horizontal track NORTH
						this.neighbors.add(new ChunkPosition(x, y, z + 1));          //dummy SlopeUp track SOUTH
						break;
				}
				break;
			case 3:                                                                                 //Slope
				switch (direction) {
					case EAST:
						this.neighbors.add(new ChunkPosition(x - 1, y, z));          //adjacent corner/horizontal track WEST
						this.neighbors.add(new ChunkPosition(x + 1, y, z));          //dummy Slope track EAST
						break;
					case WEST:
						this.neighbors.add(new ChunkPosition(x + 1, y, z));          //adjacent corner/horizontal track EAST
						this.neighbors.add(new ChunkPosition(x - 1, y, z));          //dummy Slope track WEST
						break;
					case NORTH:
						this.neighbors.add(new ChunkPosition(x, y, z + 1));          //adjacent corner/horizontal track SOUTH
						this.neighbors.add(new ChunkPosition(x, y, z - 1));          //dummy Slope track NORTH
						break;
					case SOUTH:
						this.neighbors.add(new ChunkPosition(x, y, z - 1));          //adjacent corner/horizontal track NORTH
						this.neighbors.add(new ChunkPosition(x, y, z + 1));          //dummy Slope track SOUTH
						break;
				}
				break;
			case 4:                                                                                 //Slope Down TODO: might have different dummy placements, TBD.
				switch (direction) {
					case EAST:
						this.neighbors.add(new ChunkPosition(x - 1, y, z));          //adjacent corner/horizontal track WEST
						this.neighbors.add(new ChunkPosition(x + 1, y, z));          //dummy SlopeDown track EAST
						break;
					case WEST:
						this.neighbors.add(new ChunkPosition(x + 1, y, z));          //adjacent corner/horizontal track EAST
						this.neighbors.add(new ChunkPosition(x - 1, y, z));          //dummy SlopeDown track WEST
						break;
					case NORTH:
						this.neighbors.add(new ChunkPosition(x, y, z + 1));          //adjacent corner/horizontal track SOUTH
						this.neighbors.add(new ChunkPosition(x, y, z - 1));          //dummy SlopeDown track NORTH
						break;
					case SOUTH:
						this.neighbors.add(new ChunkPosition(x, y, z - 1));          //adjacent corner/horizontal track NORTH
						this.neighbors.add(new ChunkPosition(x, y, z + 1));          //dummy SlopeDown track SOUTH
						break;
				}
				break;
			default:
				break;
		}
	}

	private void cleanNeighborsList() {
		for (int i = 0; i < this.neighbors.size(); i++) {
			BlockTrackBase track = this.getTrackFromChunkPosition((ChunkPosition) this.neighbors.get(i));

			if (track != null && this.isTrackInNeighborList(track)) {
				this.neighbors.set(i, new ChunkPosition(((ChunkPosition) this.neighbors.get(i)).chunkPosX, ((ChunkPosition) this.neighbors.get(i)).chunkPosY, ((ChunkPosition) this.neighbors.get(i)).chunkPosZ));
			} else {
				this.neighbors.remove(i--);
			}
		}
	}

	private boolean checkForTrack(int x, int y, int z) {
		return isBlockAtCoordsTrack(x, y, z);
	}

	private boolean checkForTrack(ChunkPosition position) {
		return isBlockAtCoordsTrack(position);
	}

	private boolean checkForTrackAboveAndBelow(int x, int y, int z) {
		return isBlockAtCoordsTrack(x, y, z) ? true : (isBlockAtCoordsTrack(x, y + 1, z) ? true : isBlockAtCoordsTrack(x, y - 1, z));
	}

	private boolean checkForTrackAboveAndBelow(ChunkPosition position) {
		return isBlockAtCoordsTrack(position) ? true : (isBlockAtCoordsTrack(position.chunkPosX, position.chunkPosY + 1, position.chunkPosZ) ? true : isBlockAtCoordsTrack(position.chunkPosX, position.chunkPosY - 1, position.chunkPosZ));
	}

	private BlockTrackBase getTrackFromChunkPosition(ChunkPosition position) {
		return isBlockAtCoordsTrack(position) ? (BlockTrackBase) world.getBlock(position.chunkPosX, position.chunkPosY, position.chunkPosZ) : null;
	}

	private boolean isTrackInNeighborList(BlockTrackBase track) {
		for (int i = 0; i < this.neighbors.size(); i++) {
			ChunkPosition position = (ChunkPosition) this.neighbors.get(i);

			if (position.chunkPosX == track.position.chunkPosX && position.chunkPosY == track.position.chunkPosY && position.chunkPosZ == track.position.chunkPosZ) {
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

	protected int getNumberOfAdjacentTracks(BlockTrackBase track) {
		int i = 0;
		if (this.checkForTrack(track.position.chunkPosX, track.position.chunkPosY, track.position.chunkPosZ - 1)) {
			i++;
		}

		if (this.checkForTrack(track.position.chunkPosX, track.position.chunkPosY, track.position.chunkPosZ + 1)) {
			i++;
		}

		if (this.checkForTrack(track.position.chunkPosX - 1, track.position.chunkPosY, track.position.chunkPosZ)) {
			i++;
		}

		if (this.checkForTrack(track.position.chunkPosX + 1, track.position.chunkPosY, track.position.chunkPosZ)) {
			i++;
		}

		return i;
	}

	private boolean isTrackStillNeighbor(BlockTrackBase track) {
		return this.isTrackInNeighborList(track) ? true : (this.neighbors.size() == 2 ? false : (this.neighbors.isEmpty() ? true : true));
	}

	private boolean isTrackStillNeighbor(int x, int y, int z) {
		BlockTrackBase track = this.getTrackFromChunkPosition(new ChunkPosition(x, y, z));

		if (track == null) {
			return false;
		} else {
			this.cleanNeighborsList();
			return this.isTrackStillNeighbor(track);
		}
	}

	private void setDirection(BlockTrackBase track) {
		this.neighbors.add(new ChunkPosition(track.position.chunkPosX, track.position.chunkPosY, track.position.chunkPosZ));

		boolean flagNorth = this.isTrackInNeighborList(track.position.chunkPosX, track.position.chunkPosY, track.position.chunkPosZ - 1);        //NORTH
		boolean flagSouth = this.isTrackInNeighborList(track.position.chunkPosX, track.position.chunkPosY, track.position.chunkPosZ + 1);        //SOUTH
		boolean flagWest  = this.isTrackInNeighborList(track.position.chunkPosX - 1, track.position.chunkPosY, track.position.chunkPosZ);        //WEST
		boolean flagEast  = this.isTrackInNeighborList(track.position.chunkPosX + 1, track.position.chunkPosY, track.position.chunkPosZ);        //EAST

		ChunkPosition posNorth = new ChunkPosition(track.position.chunkPosX, track.position.chunkPosY, track.position.chunkPosZ - 1);             //NORTH
		ChunkPosition posSouth = new ChunkPosition(track.position.chunkPosX, track.position.chunkPosY, track.position.chunkPosZ + 1);             //SOUTH
		ChunkPosition posWest  = new ChunkPosition(track.position.chunkPosX - 1, track.position.chunkPosY, track.position.chunkPosZ);             //WEST
		ChunkPosition posEast  = new ChunkPosition(track.position.chunkPosX + 1, track.position.chunkPosY, track.position.chunkPosZ);             //EAST

		byte byteDirection = -1;

		//TODO: rearrange these!
		switch (getTrackType(track)) { //type of track being rotated
			case 0: //Horizontal
				switch (track.direction) { //direction of track being rotated
					case NORTH:
						if (flagNorth) { //if there is a track to the north of the track being rotated
							switch (getTrackType(getTrackFromChunkPosition(posNorth))) { //type of track to the north
								case 0: //Horizontal
									switch (getTrackFromChunkPosition(posNorth).direction) { //direction of track to the north
										case NORTH: byteDirection = 0; break; //byteDirection will be the NEW direction of the track being rotated, 0N,1S,2W,3E
										case SOUTH: byteDirection = 1; break;
									}
									break;
								case 1: //Corner
									switch (getTrackFromChunkPosition(posNorth).direction) {
										case NORTH:
										case EAST: byteDirection = 0; break;
									}
									break;
								case 2: //Slope Up
									switch (getTrackFromChunkPosition(posNorth).direction) {
										case NORTH: byteDirection = 0; break;
									}
									break;
								case 4: //Slope Down
									//TBD
									break;
							}
						}

						if (flagSouth) {
							switch (getTrackType(getTrackFromChunkPosition(posSouth))) {
								case 0: //Horizontal
									switch (getTrackFromChunkPosition(posSouth).direction) {
										case NORTH: byteDirection = 0; break;
										case SOUTH: byteDirection = 1; break;
									}
									break;
								case 1: //Corner
									switch (getTrackFromChunkPosition(posSouth).direction) {
										case SOUTH:
										case WEST: byteDirection = 1; break;
									}
									break;
								case 2: //Slope Up
									switch (getTrackFromChunkPosition(posSouth).direction) {
										case SOUTH: byteDirection = 1; break;
									}
									break;
								case 4: //Slope Down
									//TBD
									break;
							}
						}

						if (flagWest) {
							switch (getTrackType(getTrackFromChunkPosition(posWest))) {
								case 0: //Horizontal
									switch (getTrackFromChunkPosition(posWest).direction) {
										case WEST: byteDirection = 2; break;
										case EAST: byteDirection = 3; break;
									}
									break;
								case 1: //Corner
									switch (getTrackFromChunkPosition(posWest).direction) {
										case NORTH:
										case WEST: byteDirection = 2; break;
									}
									break;
								case 2: //Slope Up
									switch (getTrackFromChunkPosition(posWest).direction) {
										case WEST: byteDirection = 2; break;
									}
									break;
								case 4: //Slope Down
									//TBD
									break;
							}
						}

						if (flagEast) {
							switch (getTrackType(getTrackFromChunkPosition(posEast))) {
								case 0: //Horizontal
									switch (getTrackFromChunkPosition(posEast).direction) {
										case WEST: byteDirection = 2; break;
										case EAST: byteDirection = 3; break;
									}
									break;
								case 1: //Corner
									switch (getTrackFromChunkPosition(posEast).direction) {
										case SOUTH:
										case EAST: byteDirection = 3; break;
									}
									break;
								case 2: //Slope Up
									switch (getTrackFromChunkPosition(posEast).direction) {
										case EAST: byteDirection = 3; break;
									}
									break;
								case 4: //Slope Down
									//TBD
									break;
							}
						}
						break;
					case SOUTH:
						if (flagNorth) {
							switch (getTrackType(getTrackFromChunkPosition(posNorth))) {
								case 0: //Horizontal
									switch (getTrackFromChunkPosition(posNorth).direction) {
										case NORTH: byteDirection = 0; break;
										case SOUTH: byteDirection = 1; break;
									}
									break;
								case 1: //Corner
									switch (getTrackFromChunkPosition(posNorth).direction) {
										case NORTH:
										case EAST: byteDirection = 0; break;
									}
									break;
								case 2: //Slope Up
									switch (getTrackFromChunkPosition(posNorth).direction) {
										case NORTH: byteDirection = 0; break;
									}
									break;
								case 4: //Slope Down
									//TBD
									break;
							}
						}

						if (flagSouth) {
							switch (getTrackType(getTrackFromChunkPosition(posSouth))) {
								case 0: //Horizontal
									switch (getTrackFromChunkPosition(posSouth).direction) {
										case NORTH: byteDirection = 0; break;
										case SOUTH: byteDirection = 1; break;
									}
									break;
								case 1: //Corner
									switch (getTrackFromChunkPosition(posSouth).direction) {
										case SOUTH:
										case WEST: byteDirection = 1; break;
									}
									break;
								case 2: //Slope Up
									switch (getTrackFromChunkPosition(posSouth).direction) {
										case SOUTH: byteDirection = 1; break;
									}
									break;
								case 4: //Slope Down
									//TBD
									break;
							}
						}

						if (flagWest) {
							switch (getTrackType(getTrackFromChunkPosition(posWest))) {
								case 0: //Horizontal
									switch (getTrackFromChunkPosition(posWest).direction) {
										case WEST: byteDirection = 2; break;
										case EAST: byteDirection = 3; break;
									}
									break;
								case 1: //Corner
									switch (getTrackFromChunkPosition(posWest).direction) {
										case NORTH:
										case WEST: byteDirection = 2; break;
									}
									break;
								case 2: //Slope Up
									switch (getTrackFromChunkPosition(posWest).direction) {
										case WEST: byteDirection = 2; break;
									}
									break;
								case 4: //Slope Down
									//TBD
									break;
							}
						}

						if (flagEast) {
							switch (getTrackType(getTrackFromChunkPosition(posEast))) {
								case 0: //Horizontal
									switch (getTrackFromChunkPosition(posEast).direction) {
										case WEST: byteDirection = 2; break;
										case EAST: byteDirection = 3; break;
									}
									break;
								case 1: //Corner
									switch (getTrackFromChunkPosition(posEast).direction) {
										case SOUTH:
										case EAST: byteDirection = 3; break;
									}
									break;
								case 2: //Slope Up
									switch (getTrackFromChunkPosition(posEast).direction) {
										case EAST: byteDirection = 3; break;
									}
									break;
								case 4: //Slope Down
									//TBD
									break;
							}
						}
						break;
					case WEST:
						if (flagNorth) {
							switch (getTrackType(getTrackFromChunkPosition(posNorth))) {
								case 0: //Horizontal
									switch (getTrackFromChunkPosition(posNorth).direction) {
										case NORTH: byteDirection = 0; break;
										case SOUTH: byteDirection = 1; break;
									}
									break;
								case 1: //Corner
									switch (getTrackFromChunkPosition(posNorth).direction) {
										case NORTH:
										case EAST: byteDirection = 0; break;
									}
									break;
								case 2: //Slope Up
									switch (getTrackFromChunkPosition(posNorth).direction) {
										case NORTH: byteDirection = 0; break;
									}
									break;
								case 4: //Slope Down
									//TBD
									break;
							}
						}

						if (flagSouth) {
							switch (getTrackType(getTrackFromChunkPosition(posSouth))) {
								case 0: //Horizontal
									switch (getTrackFromChunkPosition(posSouth).direction) {
										case NORTH: byteDirection = 0; break;
										case SOUTH: byteDirection = 1; break;
									}
									break;
								case 1: //Corner
									switch (getTrackFromChunkPosition(posSouth).direction) {
										case SOUTH:
										case WEST: byteDirection = 1; break;
									}
									break;
								case 2: //Slope Up
									switch (getTrackFromChunkPosition(posSouth).direction) {
										case SOUTH: byteDirection = 1; break;
									}
									break;
								case 4: //Slope Down
									//TBD
									break;
							}
						}

						if (flagWest) {
							switch (getTrackType(getTrackFromChunkPosition(posWest))) {
								case 0: //Horizontal
									switch (getTrackFromChunkPosition(posWest).direction) {
										case WEST: byteDirection = 2; break;
										case EAST: byteDirection = 3; break;
									}
									break;
								case 1: //Corner
									switch (getTrackFromChunkPosition(posWest).direction) {
										case NORTH:
										case WEST: byteDirection = 2; break;
									}
									break;
								case 2: //Slope Up
									switch (getTrackFromChunkPosition(posWest).direction) {
										case WEST: byteDirection = 2; break;
									}
									break;
								case 4: //Slope Down
									//TBD
									break;
							}
						}

						if (flagEast) {
							switch (getTrackType(getTrackFromChunkPosition(posEast))) {
								case 0: //Horizontal
									switch (getTrackFromChunkPosition(posEast).direction) {
										case WEST: byteDirection = 2; break;
										case EAST: byteDirection = 3; break;
									}
									break;
								case 1: //Corner
									switch (getTrackFromChunkPosition(posEast).direction) {
										case SOUTH:
										case EAST: byteDirection = 3; break;
									}
									break;
								case 2: //Slope Up
									switch (getTrackFromChunkPosition(posEast).direction) {
										case EAST: byteDirection = 3; break;
									}
									break;
								case 4: //Slope Down
									//TBD
									break;
							}
						}
						break;
					case EAST:
						if (flagNorth) {
							switch (getTrackType(getTrackFromChunkPosition(posNorth))) {
								case 0: //Horizontal
									switch (getTrackFromChunkPosition(posNorth).direction) {
										case NORTH: byteDirection = 0; break;
										case SOUTH: byteDirection = 1; break;
									}
									break;
								case 1: //Corner
									switch (getTrackFromChunkPosition(posNorth).direction) {
										case NORTH:
										case EAST: byteDirection = 0; break;
									}
									break;
								case 2: //Slope Up
									switch (getTrackFromChunkPosition(posNorth).direction) {
										case NORTH: byteDirection = 0; break;
									}
									break;
								case 4: //Slope Down
									//TBD
									break;
							}
						}

						if (flagSouth) {
							switch (getTrackType(getTrackFromChunkPosition(posSouth))) {
								case 0: //Horizontal
									switch (getTrackFromChunkPosition(posSouth).direction) {
										case NORTH: byteDirection = 0; break;
										case SOUTH: byteDirection = 1; break;
									}
									break;
								case 1: //Corner
									switch (getTrackFromChunkPosition(posSouth).direction) {
										case SOUTH:
										case WEST: byteDirection = 1; break;
									}
									break;
								case 2: //Slope Up
									switch (getTrackFromChunkPosition(posSouth).direction) {
										case SOUTH: byteDirection = 1; break;
									}
									break;
								case 4: //Slope Down
									//TBD
									break;
							}
						}

						if (flagWest) {
							switch (getTrackType(getTrackFromChunkPosition(posWest))) {
								case 0: //Horizontal
									switch (getTrackFromChunkPosition(posWest).direction) {
										case WEST: byteDirection = 2; break;
										case EAST: byteDirection = 3; break;
									}
									break;
								case 1: //Corner
									switch (getTrackFromChunkPosition(posWest).direction) {
										case NORTH:
										case WEST: byteDirection = 2; break;
									}
									break;
								case 2: //Slope Up
									switch (getTrackFromChunkPosition(posWest).direction) {
										case WEST: byteDirection = 2; break;
									}
									break;
								case 4: //Slope Down
									//TBD
									break;
							}
						}

						if (flagEast) {
							switch (getTrackType(getTrackFromChunkPosition(posEast))) {
								case 0: //Horizontal
									switch (getTrackFromChunkPosition(posEast).direction) {
										case WEST: byteDirection = 2; break;
										case EAST: byteDirection = 3; break;
									}
									break;
								case 1: //Corner
									switch (getTrackFromChunkPosition(posEast).direction) {
										case SOUTH:
										case EAST: byteDirection = 3; break;
									}
									break;
								case 2: //Slope Up
									switch (getTrackFromChunkPosition(posEast).direction) {
										case EAST: byteDirection = 3; break;
									}
									break;
								case 4: //Slope Down
									//TBD
									break;
							}
						}
				}
		}
//		if (getTrackType(track) == 0) {
//			if (flags0) {
//				if (getTrackType(getTrackFromChunkPosition(posNorth)) == 0) {
//				}
//
//				if (getTrackFromChunkPosition(posNorth).direction == ForgeDirection.NORTH) {
//					byteDirection = (byte) (ForgeDirection.NORTH.ordinal() - 2);
//				}
//			}
//
//			if (flags1) {
//				if (getTrackFromChunkPosition(posSouth).direction == ForgeDirection.SOUTH) {
//					byteDirection = (byte) (ForgeDirection.SOUTH.ordinal() - 2);
//				}
//			}
//
//			if (flags2) {
//				if (getTrackFromChunkPosition(posWest).direction == ForgeDirection.WEST) {
//					byteDirection = (byte) (ForgeDirection.WEST.ordinal() - 2);
//				}
//			}
//
//			if (flags3) {
//				if (getTrackFromChunkPosition(posEast).direction == ForgeDirection.EAST) {
//					byteDirection = (byte) (ForgeDirection.EAST.ordinal() - 2);
//				}
//			}
//		}
//
//		if (getTrackType(track) == 1) {
//			if (flags1 && flags3 && !flags0 && !flags2) {
//				byteDirection =
//			}
//
//			if (flags1 && flags2) {
//
//			}
//
//			if (flags0 && flags2 && !flags1 && !flags3) {
//
//			}
//
//			if (flags0 && flags3 && !flags1 && !flags2) {
//
//			}
//		}
//
//
//		if (BlockTrackBase.isSloped(focusType)) {                                                         //if this is a slope
////				byteType = (byte) focusType;
////
////				switch (focusType) {
////					case 2:
////						if (BlockTrackBase.isBlockAtCoordsTrack(this.world, this.focusX, this.focusY, this.focusZ + 1)) {
////							BlockTrackBase blockSouth = BlockTrackBase.getTrackAtCoords(this.world, this.focusX, this.focusY, this.focusZ + 1);
////
////							if (BlockTrackBase.getTrackType(blockSouth) == 0) {
////								if (blockSouth.focusDirection == ForgeDirection.NORTH) {
////									byteDirection = 0;
////								}
////							}
////
////							if ()
////						}
////				}
//
//			if (BlockTrackBase.isBlockAtCoordsTrack(this.world, this.focusX, this.focusY + 1, this.focusZ - 1)) {          //TODO: double check this
//				byteType = 4;
//			}
//
//			if (BlockTrackBase.isBlockAtCoordsTrack(this.world, this.focusX, this.focusY + 1, this.focusZ + 1)) {          //TODO: double check this
//				byteType = 5;
//			}
//		}
//
//		if (byteType == 1 && (focusType == 2 || focusType == 3 || focusType == 4)) {
//			if (BlockTrackBase.isBlockAtCoordsTrack(this.world, this.focusX + 1, this.focusY + 1, this.focusZ)) {          //TODO: double check this
//				byteType = 2;
//			}
//
//			if (BlockTrackBase.isBlockAtCoordsTrack(this.world, this.focusX - 1, this.focusY + 1, this.focusZ)) {          //TODO: double check this
//				byteType = 3;
//			}
//		}
//
//		if (byteType < 0) {
//			byteType = 0;
//		}
//
//		int i = byteType;
//
//		if (this.focusType == 1) {
//			i = 8 | byteType;
//		}
//
//		//TODO: set focusDirection using i
//	}
//
//	public void setDirection(boolean flag1, boolean flag2) {
//		boolean flagNorth = this.isTrackStillNeighbor(this.focusX, this.focusY, this.focusZ - 1);
//		boolean flagSouth = this.isTrackStillNeighbor(this.focusX, this.focusY, this.focusZ + 1);
//		boolean flagWest  = this.isTrackStillNeighbor(this.focusX - 1, this.focusY, this.focusZ);
//		boolean flagEast  = this.isTrackStillNeighbor(this.focusX + 1, this.focusY, this.focusZ);
//		byte b0 = -1;
//
//		if ((flagNorth || flagSouth) && !flagWest && !flagEast) {
//			b0 = 0;
//		}
//
//		if ((flagWest || flagEast) && !flagNorth && !flagSouth) {
//			b0 = 1;
//		}
//
//		if (this.focusType != 2 && this.focusType != 3 && this.focusType != 4) {
//			if (flagSouth && flagEast && !flagNorth && !flagWest) {
//				b0 = 6;
//			}
//
//			if (flagSouth && flagWest && !flagNorth && !flagEast) {
//				b0 = 7;
//			}
//
//			if (flagNorth && flagWest && !flagSouth && !flagEast) {
//				b0 = 8;
//			}
//
//			if (flagNorth && flagEast && !flagSouth && !flagWest) {
//				b0 = 9;
//			}
//		}
//
//		if (b0 == -1) {
//			if (flagNorth || flagSouth) {
//				b0 = 0;
//			}
//
//			if (flagWest || flagEast) {
//				b0 = 1;
//			}
//
//			if (this.focusType != 2 && this.focusType != 3 && this.focusType != 4) {
//				if (flag1) {
//					if (flagSouth && flagEast) {
//						b0 = 6;
//					}
//
//					if (flagWest && flagSouth) {
//						b0 = 7;
//					}
//
//					if (flagEast && flagNorth) {
//						b0 = 9;
//					}
//
//					if (flagNorth && flagWest) {
//						b0 = 8;
//					}
//				} else {
//					if (flagNorth && flagWest) {
//						b0 = 8;
//					}
//
//					if (flagEast && flagNorth) {
//						b0 = 9;
//					}
//
//					if (flagWest && flagSouth) {
//						b0 = 7;
//					}
//
//					if (flagSouth && flagEast) {
//						b0 = 6;
//					}
//				}
//			}
//		}
//
//		if (b0 == 0 && (this.focusType == 2 || this.focusType == 3 || this.focusType == 4)) {
//			if (BlockTrackBase.isBlockAtCoordsTrack(this.world, this.focusX, this.focusY + 1, this.focusZ - 1)) {          //TODO: double check this
//				b0 = 4;
//			}
//
//			if (BlockTrackBase.isBlockAtCoordsTrack(this.world, this.focusX, this.focusY + 1, this.focusZ + 1)) {          //TODO: double check this
//				b0 = 5;
//			}
//		}
//
//		if (b0 == 1 && (this.focusType == 2 || this.focusType == 3 || this.focusType == 4)) {
//			if (BlockTrackBase.isBlockAtCoordsTrack(this.world, this.focusX + 1, this.focusY + 1, this.focusZ)) {          //TODO: double check this
//				b0 = 2;
//			}
//
//			if (BlockTrackBase.isBlockAtCoordsTrack(this.world, this.focusX - 1, this.focusY + 1, this.focusZ)) {          //TODO: double check this
//				b0 = 3;
//			}
//		}
//
//		if (b0 < 0) {
//			b0 = 0;
//		}
//
//		this.findNeighbors(focusDirection, b0);                                                                  //TODO: redesign this method
//		int i = b0;
//
//		if (this.focusType == 1) {                                                                        //corner check
//			i = 8 | b0;
//		}
//
//		if (flag2 || focusDirection != ForgeDirection.UNKNOWN) {                                                 //TODO: FIX THIS CHECK! line 751
//			//set focusDirection/shape
//
//			for (int j = 0; j < this.neighbors.size(); j++) {
//				TrackManager trackManager = this.getTrackFromChunkPosition((ChunkPosition) this.neighbors.get(j));
//
//				if (track != null) {
//					track.cleanNeighborsList();
//
//					if (track.shouldAddToNeighborsList(this)) {
//						track.setDirection(this);
//					}
//				}
//			}
//		}
	}
}
