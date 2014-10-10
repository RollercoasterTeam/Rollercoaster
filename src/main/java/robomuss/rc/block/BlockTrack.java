package robomuss.rc.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import robomuss.rc.block.te.TileEntityDummy;
import robomuss.rc.block.te.TileEntityTrack;
import robomuss.rc.entity.EntityTrainDefault;
import robomuss.rc.item.ItemExtra;
import robomuss.rc.item.ItemTrain;
import robomuss.rc.item.RCItems;
import robomuss.rc.track.TrackHandler;
import robomuss.rc.track.piece.*;
import robomuss.rc.util.IPaintable;

public class BlockTrack extends BlockContainer implements IPaintable {
	public TrackPiece track_type;

	Block blockWest, blockEast, blockNorth, blockSouth, blockSlope;

	public BlockTrack(TrackPiece track) {
		super(Material.iron);
		setHardness(1F);
		setResistance(3F);

		this.track_type = track;
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess iba, int x, int y, int z) {
		AxisAlignedBB bounds = track_type.getBlockBounds(iba, x, y, z);
		setBlockBounds((float) bounds.minX, (float) bounds.minY, (float) bounds.minZ, (float) bounds.maxX, (float) bounds.maxY, (float) bounds.maxZ);
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		return new TileEntityTrack();
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if (!world.isRemote) {
			if (player.getHeldItem() != null) {
				if (player.getHeldItem().getItem() == RCItems.brush) {
					TileEntityTrack tet = (TileEntityTrack) world.getTileEntity(x, y, z);
					tet.colour = player.getHeldItem().getItemDamage();
					world.markBlockForUpdate(x, y, z);
					return true;
				} else if (player.getHeldItem().getItem() instanceof ItemExtra) {
					TileEntityTrack tet = (TileEntityTrack) world.getTileEntity(x, y, z);
					tet.extra = TrackHandler.extras.get(((ItemExtra) player.getHeldItem().getItem()).id);
					world.markBlockForUpdate(x, y, z);
					return true;
				} else {
					return false;
				}

			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public int getRenderType() {
		return -1;
	}


	@Override
	public int getPaintMeta(World world, BlockPos blockPos) {
		return ((TileEntityTrack) world.getTileEntity(blockPos)).colour;
	}

	@Override
	public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
		if (world.getTileEntity(pos) != null && world.getTileEntity(pos) instanceof TileEntityTrack) {
//		    System.out.println("Track Added!");
			TileEntityTrack te = (TileEntityTrack) world.getTileEntity(pos);
			te.type = TrackHandler.findTrackStyle("corkscrew");
			findNeighborBlocks(world, pos.getX(), pos.getY(), pos.getZ());

			if (isBlockTrack(this)) {
				if (isSlopeUp(this) || isSlope(this) || isSlopeDown(this)) {
					blockSlope = this;
					switch (te.direction) {
						case 0:
							BlockDummy dummySouth = (BlockDummy) RCBlocks.dummy;
							placeDummy(world, pos.getX(), pos.getY(), pos.getZ() + 1, dummySouth, te, te.direction, true);
							break;
						case 1:
							BlockDummy dummyWest = (BlockDummy) RCBlocks.dummy;
							placeDummy(world, pos.getX() -1, pos.getY(), pos.getZ(), dummyWest, te, te.direction, true);
							break;
						case 2:
							BlockDummy dummyNorth = (BlockDummy) RCBlocks.dummy;
							placeDummy(world, pos.getX(), pos.getY(), pos.getZ() -1, dummyNorth, te, te.direction, true);
							break;
						case 3:
							BlockDummy dummyEast = (BlockDummy) RCBlocks.dummy;
							placeDummy(world, pos.getX() + 1, pos.getY(), pos.getZ(), dummyEast, te, te.direction, true);
							break;
					}
				}
			}

			updateRotation(world, pos.getX(), pos.getY(), pos.getZ(), te);
		}
	}

	@Override
	public void onNeighborBlockChange(World world, BlockPos pos, IBlockState state, Block neighborBlock) {
		if (!world.isRemote) {
			TileEntityTrack te = (TileEntityTrack) world.getTileEntity(pos);
			if (te.extra == TrackHandler.extras.get(3)) {
				if (world.isBlockPowered(pos)) {
					EntityTrainDefault entity = ItemTrain.spawnCart(world, pos.getX(), pos.getY(), pos.getZ());
					world.spawnEntityInWorld(entity);
				}
			}

			//TODO: MUST CHECK ALL NEIGHBORS!!!
//	        if (!(world.getBlock(x, y, z + 1) instanceof BlockDummy) && te.direction != 0) {
//		        BlockDummy newDummy = (BlockDummy) RCBlocks.dummy;
//		        placeDummy(world, x, y, z + 1, newDummy, te, te.direction, true);
//	        } else if (!(world.getBlock(x - 1, y, z) instanceof BlockDummy) && te.direction != 1) {
//		        BlockDummy newDummy = (BlockDummy) RCBlocks.dummy;
//		        placeDummy(world, x - 1, y, z, newDummy, te, te.direction, true);
//	        } else if (!(world.getBlock(x, y, z - 1) instanceof BlockDummy) && te.direction != 2) {
//		        BlockDummy newDummy = (BlockDummy) RCBlocks.dummy;
//		        placeDummy(world, x, y, z - 1, newDummy, te, te.direction, true);
//	        } else if (!(world.getBlock(x + 1, y, z) instanceof BlockDummy) && te.direction != 3) {
//		        BlockDummy newDummy = (BlockDummy) RCBlocks.dummy;
//		        placeDummy(world, x + 1, y, z, newDummy, te, te.direction, true);
//	        }
		}
	}

	@Override
	public void onBlockHarvested(World world, BlockPos pos, IBlockState state, EntityPlayer player) {
//		if (player.capabilities.isCreativeMode) {
// TODO: check that this works in survival
		int trackX = pos.getX();
		int trackY = pos.getY();
		int trackZ = pos.getZ();
		if (world.getTileEntity(pos) instanceof TileEntityTrack) {
			TileEntityTrack te = (TileEntityTrack) world.getTileEntity(pos);
			findNeighborBlocks(world, pos.getX(), pos.getY(), pos.getZ());
			if (isSlopeUp(this)) {
				switch (te.direction) {
					case 0:
						if (world.getBlockState(new BlockPos(trackX, trackY, trackZ + 1)).getBlock() instanceof BlockDummy) {                 //check if block z + 1 is a dummy
							BlockDummy dummySouth = (BlockDummy) world.getBlockState(new BlockPos(trackX, trackY, trackZ + 1)).getBlock();    //obtain the dummy
							dummySouth.setBreakSlope(false);                                                    //tell dummy to not attempt to break the slope
							dummySouth.onBlockHarvested(world, trackX, trackY, trackZ + 1,  player);
							world.markBlockForUpdate(new BlockPos(trackX, trackY, trackZ + 1));
						}
						break;
					case 1:
						if (world.getBlockState(new BlockPos(trackX - 1, trackY, trackZ)).getBlock() instanceof BlockDummy) {
							BlockDummy dummyWest = (BlockDummy) world.getBlockState(new BlockPos(trackX - 1, trackY, trackZ)).getBlock();
							dummyWest.setBreakSlope(false);
							dummyWest.onBlockHarvested(world, trackX - 1, trackY, trackZ, player);
							world.markBlockForUpdate(new BlockPos(trackX - 1, trackY, trackZ));
						}
						break;
					case 2:
						if (world.getBlockState(new BlockPos(trackX, trackY, trackZ - 1)).getBlock() instanceof BlockDummy) {
							BlockDummy dummyNorth = (BlockDummy) world.getBlockState(new BlockPos(trackX, trackY, trackZ - 1)).getBlock();
							dummyNorth.setBreakSlope(false);
							dummyNorth.onBlockHarvested(world, trackX, trackY, trackZ - 1,  player);
							world.markBlockForUpdate(new BlockPos(trackX, trackY, trackZ - 1));
						}
						break;
					case 3:
						if (world.getBlockState(new BlockPos(trackX + 1, trackY, trackZ)).getBlock() instanceof BlockDummy) {
							BlockDummy dummyEast = (BlockDummy) world.getBlockState(new BlockPos(trackX + 1, trackY, trackZ)).getBlock();
							dummyEast.setBreakSlope(false);
							dummyEast.onBlockHarvested(world, trackX + 1, trackY, trackZ,  player);
							world.markBlockForUpdate(new BlockPos(trackX + 1, trackY, trackZ));
						}
						break;
				}
				world.setBlockToAir(new BlockPos(trackX, trackY, trackZ));
				world.markBlockForUpdate(new BlockPos(trackX, trackY, trackZ));
			}
		}
//		}
	}

	private void findNeighborBlocks(World world, int x, int y, int z) {
		if (!world.isRemote) {
			blockWest = world.getBlockState(new BlockPos(x - 1, y, z)).getBlock();              //west
			blockEast = world.getBlockState(new BlockPos(x + 1, y, z)).getBlock();              //east
			blockNorth = world.getBlockState(new BlockPos(x, y, z - 1)).getBlock();             //north
			blockSouth = world.getBlockState(new BlockPos(x, y, z + 1)).getBlock();             //south
		}
	}

	/**
	 * Depending on the type of track piece, this handles auto-rotation and/or rotation of complex track pieces (ie. slopes).
	 * Coordinates and the TileEntity passed in are those of the track block being rotated.
	 *
	 * @param world
	 * @param x
	 * @param y
	 * @param z
	 * @param track
	 */
	public void updateRotation(World world, int x, int y, int z, TileEntityTrack track) {          //may be better way of doing this, but it works
		if (!world.isRemote) {
			if (isHorizontal(this)) {
				if (isBlockTrack(blockWest)) {
					if (isCorner(blockWest)) {
						TileEntityTrack teWest = (TileEntityTrack) world.getTileEntity(new BlockPos(x - 1, y, z));
						if (teWest.direction == 1 || teWest.direction == 2) {
							track.direction = 1;
						}
					} else if (isHorizontal(blockWest)) {
						TileEntityTrack teWest = (TileEntityTrack) world.getTileEntity(new BlockPos(x - 1, y, z));
						if (teWest.direction == 1 || teWest.direction == 3) {
							track.direction = teWest.direction;
						}
					}
				}

				if (isBlockTrack(blockEast)) {
					if (isCorner(blockEast)) {
						TileEntityTrack teEast = (TileEntityTrack) world.getTileEntity(new BlockPos(x + 1, y, z));
						if (teEast.direction == 0 || teEast.direction == 3) {
							track.direction = 1;
						}
					} else if (isHorizontal(blockEast)) {
						TileEntityTrack teEast = (TileEntityTrack) world.getTileEntity(new BlockPos(x + 1, y, z));
						if (teEast.direction == 1 || teEast.direction == 3) {
							track.direction = teEast.direction;
						}
					}
				}

				if (isBlockTrack(blockNorth)) {
					if (isCorner(blockNorth)) {
						TileEntityTrack teNorth = (TileEntityTrack) world.getTileEntity(new BlockPos(x, y, z - 1));
						if (teNorth.direction == 2 || teNorth.direction == 3) {
							track.direction = 0;
						}
					}
				}

				if (isBlockTrack(blockSouth)) {
					if (isCorner(blockSouth)) {
						TileEntityTrack teSouth = (TileEntityTrack) world.getTileEntity(new BlockPos(x, y, z + 1));
						if (teSouth.direction == 0 || teSouth.direction == 1) {
							track.direction = 0;
						}
					}
				}
			}

			if (isCorner(this)) {
				if (isBlockTrack(blockWest)) {
					if (isHorizontal(blockWest)) {
						TileEntityTrack teWest = (TileEntityTrack) world.getTileEntity(new BlockPos(x - 1, y, z));

						if (teWest.direction == 1 || teWest.direction == 3) {
							track.direction = 3;

							if (isBlockTrack(blockNorth)) {
								if (isHorizontal(blockNorth)) {
									TileEntityTrack teNorth = (TileEntityTrack) world.getTileEntity(new BlockPos(x, y, z - 1));
									if (teNorth.direction == 0 || teNorth.direction == 2) {
										track.direction = 0;
									}
								}
							}

							if (isBlockTrack(blockSouth)) {
								if (isHorizontal(blockSouth)) {
									TileEntityTrack teSouth = (TileEntityTrack) world.getTileEntity(new BlockPos(x, y, z + 1));
									if (teSouth.direction == 0 || teSouth.direction == 2) {
										track.direction = 3;
									}
								}
							}
						}
					}
				}

				if (isBlockTrack(blockEast)) {
					if (isHorizontal(blockEast)) {
						TileEntityTrack teEast = (TileEntityTrack) world.getTileEntity(new BlockPos(x + 1, y, z));

						if (teEast.direction == 1 || teEast.direction == 3) {
							track.direction = 1;

							if (isBlockTrack(blockNorth)) {
								if (isHorizontal(blockNorth)) {
									TileEntityTrack teNorth = (TileEntityTrack) world.getTileEntity(new BlockPos(x, y, z - 1));
									if (teNorth.direction == 0 || teNorth.direction == 2) {
										track.direction = 1;
									}
								}
							}

							if (isBlockTrack(blockSouth)) {
								if (isHorizontal(blockSouth)) {
									TileEntityTrack teSouth = (TileEntityTrack) world.getTileEntity(new BlockPos(x, y, z + 1));
									if (teSouth.direction == 0 || teSouth.direction == 2) {
										track.direction = 2;
									}
								}
							}
						}
					}
				}

				if (isBlockTrack(blockNorth)) {
					if (isHorizontal(blockNorth)) {
						TileEntityTrack teNorth = (TileEntityTrack) world.getTileEntity(new BlockPos(x, y, z - 1));
						if (teNorth.direction == 0 || teNorth.direction == 2) {
							track.direction = 0;
						}
					}
				}

				if (isBlockTrack(blockSouth)) {
					if (isHorizontal(blockSouth)) {
						TileEntityTrack teSouth = (TileEntityTrack) world.getTileEntity(new BlockPos(x, y, z + 1));
						if (teSouth.direction == 0 || teSouth.direction == 2) {
							track.direction = 2;
						}
					}
				}
			}

			if (isSlopeUp(this)) {
//				System.out.println(track.direction);
				findNeighborBlocks(world, x, y, z);
				if (track.direction == 1 && blockSouth instanceof BlockDummy) {
					if (this.equals(((BlockDummy) blockSouth).getParentSlope())) {
//						System.out.println("Slopes are equal!");
					}
					TileEntityDummy dummy = (TileEntityDummy) world.getTileEntity(new BlockPos(x, y, z + 1));
					((BlockDummy) blockSouth).setBreakSlope(false);
					blockSouth.onBlockHarvested(world, dummy.getPos(), 0, Minecraft.getMinecraft().thePlayer);
					world.markBlockForUpdate(new BlockPos(dummy.xCoord, dummy.yCoord, dummy.zCoord));
					BlockDummy newDummy = (BlockDummy) RCBlocks.dummy;
					placeDummy(world, x - 1, y, z, newDummy, track, track.direction, true);
				} else if (track.direction == 2 && blockWest instanceof BlockDummy) {
					TileEntityDummy dummy = (TileEntityDummy) world.getTileEntity(new BlockPos(x - 1, y, z));
					((BlockDummy) blockWest).setBreakSlope(false);
					blockWest.onBlockHarvested(world, dummy.getPos(), 0, Minecraft.getMinecraft().thePlayer);
					world.markBlockForUpdate(new BlockPos(dummy.xCoord, dummy.yCoord, dummy.zCoord));
					BlockDummy newDummy = (BlockDummy) RCBlocks.dummy;
					placeDummy(world, x, y, z - 1, newDummy, track, track.direction, true);
				} else if (track.direction == 3 && blockNorth instanceof BlockDummy) {
					TileEntityDummy dummy = (TileEntityDummy) world.getTileEntity(new BlockPos(x, y, z - 1));
					((BlockDummy) blockNorth).setBreakSlope(false);
					blockNorth.onBlockHarvested(world, dummy.getPos(), 0, Minecraft.getMinecraft().thePlayer);
					world.markBlockForUpdate(new BlockPos(dummy.xCoord, dummy.yCoord, dummy.zCoord));
					BlockDummy newDummy = (BlockDummy) RCBlocks.dummy;
					placeDummy(world, x + 1, y, z, newDummy, track, track.direction, true);
				} else if (track.direction == 0 && blockEast instanceof BlockDummy) {
					TileEntityDummy dummy = (TileEntityDummy) world.getTileEntity(new BlockPos(x + 1, y, z));
					((BlockDummy) blockEast).setBreakSlope(false);
					blockEast.onBlockHarvested(world, dummy.xCoord, dummy.yCoord, dummy.zCoord, 0, Minecraft.getMinecraft().thePlayer);
					world.markBlockForUpdate(new BlockPos(dummy.xCoord, dummy.yCoord, dummy.zCoord));
					BlockDummy newDummy = (BlockDummy) RCBlocks.dummy;
					placeDummy(world, x, y, z + 1, newDummy, track, track.direction, true);
				}
//				if (!(blockSouth instanceof BlockDummy) && !(blockWest instanceof BlockDummy) && !(blockNorth instanceof BlockDummy) && !(blockEast instanceof BlockDummy)) {
//					BlockDummy dummy = (BlockDummy) RCBlocks.dummy;
//					switch (track.direction) {
//						case 0:
//							placeDummy(world, x, y, z + 1, dummy, track, track.direction, true);
//							break;
//						case 1:
//							placeDummy(world, x - 1, y, z, dummy, track, track.direction, true);
//							break;
//						case 2:
//							placeDummy(world, x, y, z - 1, dummy, track, track.direction, true);
//							break;
//						case 3:
//							placeDummy(world, x + 1, y, z, dummy, track, track.direction, true);
//							break;
//					}
//				}
			}

			world.markBlockForUpdate(new BlockPos(x, y, z));
		}
	}

	public void placeDummy(World world, int dummyX, int dummyY, int dummyZ, BlockDummy dummy, TileEntityTrack teSlope, int slopeDirection, boolean breakSlope) {
		world.setBlock(dummyX, dummyY, dummyZ, dummy);
		teSlope.setDummy(dummy, dummyX, dummyY, dummyZ);
		dummy.setParentSlope(teSlope, this);
//		dummy.setSlopeDirection(slopeDirection);
		dummy.setBreakSlope(breakSlope);
	}

	private boolean isBlockTrack(Block block) {
		return block instanceof BlockTrack;
	}

	private boolean isHorizontal(Block block) {
		return ((BlockTrack) block).track_type instanceof TrackPieceHorizontal;
	}

	private boolean isCorner(Block block) {
		return ((BlockTrack) block).track_type instanceof TrackPieceCorner;
	}

	private boolean isSlopeUp(Block block) {
		return ((BlockTrack) block).track_type instanceof TrackPieceSlopeUp;
	}

	private boolean isSlope(Block block) {
		return ((BlockTrack) block).track_type instanceof TrackPieceSlope;
	}

	private boolean isSlopeDown(Block block) {
		return ((BlockTrack) block).track_type instanceof TrackPieceSlopeDown;
	}
}
