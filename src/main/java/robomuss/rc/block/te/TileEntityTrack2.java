package robomuss.rc.block.te;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import robomuss.rc.block.*;
import robomuss.rc.item.RCItems;
import robomuss.rc.track.TrackHandler;
import robomuss.rc.track.extra.TrackExtra;
import robomuss.rc.track.piece.*;
import robomuss.rc.track.style.TrackStyle;
import robomuss.rc.util.IPaintable;

public class TileEntityTrack2 extends TileEntity implements IPaintable, IRotatable {
	private ForgeDirection direction;
	public int colour;
	public TrackExtra extra;
	public TrackStyle style;
	public TrackPiece track_type;

	private boolean converted = false;

	/**
	 * When a track piece is a dummy, it doesn't render, and also changes how the block as a whole rotates.
	 */
	private boolean isDummy;
	private ForgeDirection dummyDirection;
	public BlockTrack2 dummyTrack;
	public BlockTrack2 track;

	//private IAirHandler airHandler;

	public TileEntityTrack2() {
		this.direction = ForgeDirection.SOUTH;
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);

		direction = ForgeDirection.getOrientation(compound.getInteger("direction") + 2);
		colour = compound.getInteger("colour");
		converted = compound.getBoolean("converted");
		isDummy = compound.getBoolean("dummy");
		for (int i = 0; i < TrackHandler.styles.size(); i++) {
			if (TrackHandler.styles.get(i).getId().contains(compound.getString("styleName"))) {
				style = TrackHandler.styles.get(i);
			}
		}

		int extraID = compound.getInteger("extraID");
		extra = extraID == -1 ? null : TrackHandler.extras.get(extraID);
	}

	@Override
	public void writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);

		if (!converted || style == null) {
			style = TrackHandler.findTrackStyle("corkscrew");
			converted = true;
		}

		compound.setInteger("direction", direction.ordinal() - 2);
		compound.setInteger("colour", colour);
		compound.setString("styleName", style.getId());
		compound.setBoolean("converted", converted);
		compound.setBoolean("dummy", isDummy);
		if (extra != null) {
			compound.setInteger("extraID", extra.id);
		} else {
			compound.setInteger("extraID", -1);
		}
	}

	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound compound = new NBTTagCompound();
		this.writeToNBT(compound);
		return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 1, compound);
	}

	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity packet) {
		readFromNBT(packet.func_148857_g());
	}

	@Override
	@SideOnly(Side.CLIENT)
	public AxisAlignedBB getRenderBoundingBox() {
		Block block = this.getWorldObj().getBlock(this.xCoord, this.yCoord, this.zCoord);
		TrackPiece type = TrackHandler.findTrackTypeFull(block.getUnlocalizedName());
		if (type != null) {
			return type.getRenderBoundingBox(this.getWorldObj(), this.xCoord, this.yCoord, this.zCoord);
		} else {
			return AxisAlignedBB.getBoundingBox(0, 0, 0, 1, 1, 1);
		}
	}

	@Override
	public boolean canUpdate() {
		return false;
	}

	public void setIsDummy(boolean isDummy) {
		this.isDummy = isDummy;
	}

	public boolean getIsDummy() {
		return this.isDummy;
	}

	/**
	 * Coords and direction passed in are those of the track placing the new track as a dummy.
	 * @param world
	 * @param x
	 * @param y
	 * @param z
	 * @param direction
	 */
	public void placeTrackAsDummy(World world, int x, int y, int z, ForgeDirection direction, TrackPiece track_type) {
		int dummyX = x + direction.offsetX;
		int dummyY = y + direction.offsetY;
		int dummyZ = z + direction.offsetZ;

		this.track = (BlockTrack2) world.getBlock(x, y, z);

		this.dummyTrack = new BlockTrack2(track_type);
//		track_type.block = new BlockTrack2(track_type);
		world.setBlock(dummyX, dummyY, dummyZ, this.dummyTrack);
//		this.track = (BlockTrack2) track_type.block;
		this.setIsDummy(false);
		this.dummyTrack.teTrack.setIsDummy(true);
		this.dummyTrack.teTrack.setDummyDirection(direction);
//		this.track.teTrack.setIsDummy(true);
//		this.track.teTrack.setTrackDirection(direction);
	}

	public void setDummyDirection(ForgeDirection direction) {
		if (this.isDummy) {
			this.dummyDirection = direction;
		}
	}

	public boolean isHorizontal(Block block) {
		return block instanceof BlockTrack2 && ((BlockTrack2) block).track_type instanceof TrackPieceHorizontal;
	}

	public boolean isCorner(Block block) {
		return block instanceof BlockTrack2 && ((BlockTrack2) block).track_type instanceof TrackPieceCorner;
	}

	public boolean hasSlope(Block block) {
		if (block instanceof BlockTrack) {
			if (((BlockTrack) block).track_type instanceof TrackPieceSlopeUp) {
				return true;
			} else if (((BlockTrack) block).track_type instanceof TrackPieceSlope) {
				return true;
			} else if (((BlockTrack) block).track_type instanceof TrackPieceSlopeDown) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void updateRotation(World world, int x, int y, int z) {
		if (!world.isRemote) {
			Block blockNorth = world.getBlock(xCoord + ForgeDirection.NORTH.offsetX, yCoord + ForgeDirection.NORTH.offsetY, zCoord + ForgeDirection.NORTH.offsetZ);
			Block blockSouth = world.getBlock(xCoord + ForgeDirection.SOUTH.offsetX, yCoord + ForgeDirection.SOUTH.offsetY, zCoord + ForgeDirection.SOUTH.offsetZ);
			Block blockWest = world.getBlock(xCoord + ForgeDirection.WEST.offsetX, yCoord + ForgeDirection.WEST.offsetY, zCoord + ForgeDirection.WEST.offsetZ);
			Block blockEast = world.getBlock(xCoord + ForgeDirection.EAST.offsetX, yCoord + ForgeDirection.EAST.offsetY, zCoord + ForgeDirection.EAST.offsetZ);

			if (canRotate(world, track)) {
				if (isHorizontal(track)) {
					if (isCorner(blockWest)) {
						switch (((BlockTrack2) blockWest).teTrack.getDirection()) {
							case NORTH:
							case WEST:
								setDirection(ForgeDirection.WEST);
								break;
						}
					} else if (isHorizontal(blockWest)) {
						switch (((BlockTrack2) blockWest).teTrack.getDirection()) {
							case WEST:
							case EAST:
								setDirection(((BlockTrack2) blockWest).teTrack.getDirection());
								break;
						}
					}

					if (isCorner(blockEast)) {
						switch (((BlockTrack2) blockEast).teTrack.getDirection()) {
							case SOUTH:
							case EAST:
								setDirection(ForgeDirection.WEST);
								break;
						}
					} else if (isHorizontal(blockEast)) {
						switch (((BlockTrack2) blockEast).teTrack.getDirection()) {
							case WEST:
							case EAST:
								setDirection(((BlockTrack2) blockEast).teTrack.getDirection());
								break;
						}
					}

					if (isCorner(blockNorth)) {
						switch (((BlockTrack2) blockNorth).teTrack.getDirection()) {
							case NORTH:
							case EAST:
								setDirection(ForgeDirection.SOUTH);
								break;
						}
					}

					if (isCorner(blockSouth)) {
						switch (((BlockTrack2) blockSouth).teTrack.getDirection()) {
							case SOUTH:
							case WEST:
								setDirection(ForgeDirection.SOUTH);
						}
					}
				}

				if (isCorner(track)) {
					if (isHorizontal(blockWest)) {
						switch (((BlockTrack2) blockWest).teTrack.getDirection()) {
							case WEST:
							case EAST:
								setDirection(ForgeDirection.EAST);
								break;
						}

						if (isHorizontal(blockNorth)) {
							switch (((BlockTrack2) blockNorth).teTrack.getDirection()) {
								case SOUTH:
								case NORTH:
									setDirection(ForgeDirection.SOUTH);
									break;
							}
						}

						if (isHorizontal(blockSouth)) {
							switch (((BlockTrack2) blockSouth).teTrack.getDirection()) {
								case SOUTH:
								case NORTH:
									setDirection(ForgeDirection.EAST);
									break;
							}
						}
					}

					if (isHorizontal(blockEast)) {
						switch (((BlockTrack2) blockEast).teTrack.getDirection()) {
							case WEST:
							case EAST:
								setDirection(ForgeDirection.WEST);
								break;
						}

						if (isHorizontal(blockNorth)) {
							switch (((BlockTrack2) blockNorth).teTrack.getDirection()) {
								case SOUTH:
								case NORTH:
									setDirection(ForgeDirection.WEST);
									break;
							}
						}

						if (isHorizontal(blockSouth)) {
							switch (((BlockTrack2) blockSouth).teTrack.getDirection()) {
								case SOUTH:
								case NORTH:
									setDirection(ForgeDirection.NORTH);
									break;
							}
						}
					}

					if (isHorizontal(blockNorth)) {
						switch (((BlockTrack2) blockNorth).teTrack.getDirection()) {
							case SOUTH:
							case NORTH:
								setDirection(ForgeDirection.SOUTH);
								break;
						}
					}

					if (isHorizontal(blockSouth)) {
						switch (((BlockTrack2) blockSouth).teTrack.getDirection()) {
							case SOUTH:
							case NORTH:
								setDirection(ForgeDirection.NORTH);
								break;
						}
					}
				}

				if (hasSlope(track)) {
					switch (getDirection()) {
						case WEST:                              //if direction is NOW west, break dummy track to the south
							if (((BlockTrack2) blockSouth).teTrack.getIsDummy()) {
								world.setBlockToAir(((BlockTrack2) blockSouth).teTrack.xCoord, ((BlockTrack2) blockSouth).teTrack.yCoord, ((BlockTrack2) blockSouth).teTrack.zCoord);
								world.markBlockForUpdate(((BlockTrack2) blockSouth).teTrack.xCoord, ((BlockTrack2) blockSouth).teTrack.yCoord, ((BlockTrack2) blockSouth).teTrack.zCoord);
							}
							placeTrackAsDummy(world, x, y, z, getDirection(), track_type);
							break;
						case NORTH:
							if (((BlockTrack2) blockWest).teTrack.getIsDummy()) {
								world.setBlockToAir(((BlockTrack2) blockWest).teTrack.xCoord, ((BlockTrack2) blockWest).teTrack.yCoord, ((BlockTrack2) blockWest).teTrack.zCoord);
								world.markBlockForUpdate(((BlockTrack2) blockWest).teTrack.xCoord, ((BlockTrack2) blockWest).teTrack.yCoord, ((BlockTrack2) blockWest).teTrack.zCoord);
							}
							placeTrackAsDummy(world, x, y, z, getDirection(), track_type);
							break;
						case EAST:
							if (((BlockTrack2) blockNorth).teTrack.getIsDummy()) {
								world.setBlockToAir(((BlockTrack2) blockNorth).teTrack.xCoord, ((BlockTrack2) blockNorth).teTrack.yCoord, ((BlockTrack2) blockNorth).teTrack.zCoord);
								world.markBlockForUpdate(((BlockTrack2) blockNorth).teTrack.xCoord, ((BlockTrack2) blockNorth).teTrack.yCoord, ((BlockTrack2) blockNorth).teTrack.zCoord);
							}
							placeTrackAsDummy(world, x, y, z, getDirection(), track_type);
							break;
						case SOUTH:
							if (((BlockTrack2) blockWest).teTrack.getIsDummy()) {
								world.setBlockToAir(((BlockTrack2) blockWest).teTrack.xCoord, ((BlockTrack2) blockWest).teTrack.yCoord, ((BlockTrack2) blockWest).teTrack.zCoord);
								world.markBlockForUpdate(((BlockTrack2) blockWest).teTrack.xCoord, ((BlockTrack2) blockWest).teTrack.yCoord, ((BlockTrack2) blockWest).teTrack.zCoord);
							}
							placeTrackAsDummy(world, x, y, z, getDirection(), track_type);
							break;
					}
				}
				world.markBlockForUpdate(x, y, z);
			}
		}
	}

	@Override
	public boolean canRotate(World world, BlockTrack2 track) {
		if (!world.isRemote) {
			if (!this.hasSlope(track)) {
				return true;
			} else if (this.hasSlope(track)) {
				switch (direction) {                        //TODO: figure out what to do with SlopeDown tracks
					case NORTH:
						if (this.track.isAir(world, xCoord + ForgeDirection.WEST.offsetX, yCoord + ForgeDirection.WEST.offsetY, zCoord + ForgeDirection.WEST.offsetZ)) {
							return true;
						}
						break;
					case SOUTH:
						if (this.track.isAir(world, xCoord + ForgeDirection.EAST.offsetX, yCoord + ForgeDirection.EAST.offsetY, zCoord + ForgeDirection.EAST.offsetZ)) {
							return true;
						}
						break;
					case WEST:
						if (this.track.isAir(world, xCoord + ForgeDirection.SOUTH.offsetX, yCoord + ForgeDirection.SOUTH.offsetY, zCoord + ForgeDirection.SOUTH.offsetZ)) {
							return true;
						}
						break;
					case EAST:
						if (this.track.isAir(world, xCoord + ForgeDirection.NORTH.offsetX, yCoord + ForgeDirection.NORTH.offsetY, zCoord + ForgeDirection.NORTH.offsetZ)) {
							return true;
						}
						break;
				}
			}
		}
		return false;
	}

	@Override
	public void setDirection(ForgeDirection direction) {
		this.direction = direction == ForgeDirection.DOWN ? ForgeDirection.SOUTH : direction == ForgeDirection.UP ? ForgeDirection.SOUTH : direction;
	}

	@Override
	public ForgeDirection getDirection() {
		return direction;
	}

	@Override
	public int getPaintMeta(World world, int x, int y, int z) {
		return this.colour;
	}

	/*@Optional.Method(modid = "PneumaticCraft")
	@Override
	public IAirHandler getAirHandler() {
		if(airHandler == null) {
			airHandler = AirHandlerSupplier.getAirHandler(5, 7, 50, 2000);
		}
		return airHandler;
	}

	@Optional.Method(modid = "PneumaticCraft")
	@Override
	public boolean isConnectedTo(ForgeDirection side) {
		if(this.extra != null && this.extra instanceof TrackExtraAirLauncher) {
			return true;
		}
		else {
			return false;
		}
	}*/

	/*@Override
	public void updateEntity() {
		super.updateEntity();
		getAirHandler().updateEntityI();
	}

	@Override
	public void validate() {
		super.validate();
		getAirHandler().validateI(this);
	}*/
}
