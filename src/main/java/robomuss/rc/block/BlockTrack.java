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
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import robomuss.rc.block.te.TileEntityTrack;
import robomuss.rc.entity.EntityTrainDefault;
import robomuss.rc.item.ItemExtra;
import robomuss.rc.item.ItemTrain;
import robomuss.rc.item.RCItems;
import robomuss.rc.track.TrackHandler;
import robomuss.rc.track.piece.TrackPiece;
import robomuss.rc.util.IPaintable;

public class BlockTrack extends BlockContainer implements IPaintable {
	public TrackPiece track_type;
	public TileEntityTrack teTrack;
	public Block[] neighbors = new Block[4];

	Block blockWest, blockEast, blockNorth, blockSouth, blockSlope;

	public BlockTrack(TrackPiece type) {
		super(Material.iron);
		setHardness(1F);
		setResistance(3F);

		this.track_type = type;
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess iba, BlockPos pos) {
		AxisAlignedBB bounds = track_type.getBlockBounds(iba, pos);
		setBlockBounds((float) bounds.minX, (float) bounds.minY, (float) bounds.minZ, (float) bounds.maxX, (float) bounds.maxY, (float) bounds.maxZ);
	}



	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ) {
		if (!world.isRemote) {
			if (player.getHeldItem() != null) {
				if (player.getHeldItem().getItem() == RCItems.brush) {
					TileEntityTrack tet = (TileEntityTrack) world.getTileEntity(pos);
					tet.colour = player.getHeldItem().getItemDamage();
					world.markBlockForUpdate(pos);
					return true;
				} else if (player.getHeldItem().getItem() instanceof ItemExtra) {
					TileEntityTrack tet = (TileEntityTrack) world.getTileEntity(pos);
					tet.extra = TrackHandler.extras.get(((ItemExtra) player.getHeldItem().getItem()).id);
					world.markBlockForUpdate(pos);
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
	public TileEntity createNewTileEntity(World world, int meta) {
		TileEntityTrack teTrack = new TileEntityTrack();
		teTrack.track_type = this.track_type;
		teTrack.track = this;
		return teTrack;
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if (!world.isRemote) {
			if (player.getHeldItem() != null) {
				if (player.getHeldItem().getItem() == RCItems.brush) {
					TileEntityTrack teTrack = (TileEntityTrack) world.getTileEntity(x, y, z);
					teTrack.colour = player.getHeldItem().getItemDamage();
					world.markBlockForUpdate(x, y, z);
					return true;
				} else if (player.getHeldItem().getItem() instanceof ItemExtra) {
					TileEntityTrack teTrack = (TileEntityTrack) world.getTileEntity(x, y, z);
					teTrack.extra = TrackHandler.extras.get(((ItemExtra) player.getHeldItem().getItem()).id);
					world.markBlockForUpdate(x, y, z);
					return true;
				}
			}
		}
		return false;
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
							dummySouth.onBlockHarvested(world, trackX, trackY, trackZ + 1, player);
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
							dummyNorth.onBlockHarvested(world, trackX, trackY, trackZ - 1, player);
							world.markBlockForUpdate(new BlockPos(trackX, trackY, trackZ - 1));
						}
						break;
					case 3:
						if (world.getBlockState(new BlockPos(trackX + 1, trackY, trackZ)).getBlock() instanceof BlockDummy) {
							BlockDummy dummyEast = (BlockDummy) world.getBlockState(new BlockPos(trackX + 1, trackY, trackZ)).getBlock();
							dummyEast.setBreakSlope(false);
							dummyEast.onBlockHarvested(world, trackX + 1, trackY, trackZ, player);
							world.markBlockForUpdate(new BlockPos(trackX + 1, trackY, trackZ));
						}
						break;
				}
			}
		}
	}

	@Override
	public int damageDropped(int dmg) {
		return dmg;
	}

	@Override
	public int getPaintMeta(World world, int x, int y, int z) {
		return ((TileEntityTrack) world.getTileEntity(x, y, z)).colour;
	}

	@Override
	public void onBlockAdded(World world, int x, int y, int z) {
		if (world.getTileEntity(x, y, z) != null && world.getTileEntity(x, y, z) instanceof TileEntityTrack) {
			this.teTrack = (TileEntityTrack) world.getTileEntity(x, y, z);
			teTrack.style = TrackHandler.findTrackStyle("corkscrew");

			findNeighborBlocks(world, x, y, z);

			if (teTrack.hasSlope(this)) {
				if (neighbors[teTrack.direction.ordinal() - 2] instanceof BlockDummy) {
					teTrack.placeDummy(world, x + teTrack.direction.offsetX, y + teTrack.direction.offsetY, z + teTrack.direction.offsetZ, (BlockDummy) neighbors[teTrack.direction.ordinal()], true);
>>>>>>> master
				}
				world.setBlockToAir(new BlockPos(trackX, trackY, trackZ));
				world.markBlockForUpdate(new BlockPos(trackX, trackY, trackZ));
			}
<<<<<<< HEAD
		}
//		}
=======

			updateRotation(world, x, y, z);
		}
>>>>>>> master
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
		if (!world.isRemote) {
<<<<<<< HEAD
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
=======
			if (teTrack.extra == TrackHandler.extras.get(3)) {
				if (world.isBlockIndirectlyGettingPowered(x, y, z)) {
					EntityTrainDefault entity = ItemTrain.spawnCart(world, x, y, z);
					world.spawnEntityInWorld(entity);
				}
			}

//			if (teTrack.direction == ForgeDirection.NORTH && !(neighbors[0] instanceof BlockDummy)) {
//
//			}
		}
	}

	@Override
	public void onBlockHarvested(World world, int trackX, int trackY, int trackZ, int meta, EntityPlayer player) {          //TODO: make TileEntityDummy control breaking the slope?
		findNeighborBlocks(world, trackX, trackY, trackZ);
		if (world.getTileEntity(trackX, trackY, trackZ) instanceof TileEntityTrack) {
			TileEntityTrack teTrack = (TileEntityTrack) world.getTileEntity(trackX, trackY, trackZ);
			if (teTrack.hasSlope(this)) {
				if (neighbors[teTrack.direction.ordinal() - 2] instanceof BlockDummy) {
					((BlockDummy) neighbors[teTrack.direction.ordinal()]).setBreakSlope(false);         //tell dummy to not break the slope block
					neighbors[teTrack.direction.ordinal() - 2].onBlockHarvested(world, trackX + teTrack.direction.offsetX, trackY + teTrack.direction.offsetY, trackZ + teTrack.direction.offsetZ, meta, player);
					world.markBlockForUpdate(trackX + teTrack.direction.offsetX, trackY + teTrack.direction.offsetY, trackZ + teTrack.direction.offsetZ);
				}
				world.setBlockToAir(trackX, trackY, trackZ);
				world.markBlockForUpdate(trackX, trackY, trackZ);
			}
		}
	}

	public void findNeighborBlocks(World world, int x, int y, int z) {
		if (!world.isRemote) {
			for (int i = 0; i < neighbors.length - 2; i++) {
				int orientation = ForgeDirection.VALID_DIRECTIONS[i + 2].ordinal();                     //N,S,W,E
				neighbors[i] = world.getBlock(x + ForgeDirection.getOrientation(orientation).offsetX, y + ForgeDirection.getOrientation(orientation).offsetY, z + ForgeDirection.getOrientation(orientation).offsetZ);
			}
		}
	}

	public boolean canRotate(World world, BlockTrack track) {
		if (!world.isRemote) {
			if (this.teTrack.hasSlope(track)) {
				if (track.teTrack.direction == ForgeDirection.NORTH) {
					if (world.isAirBlock(this.teTrack.xCoord + ForgeDirection.EAST.offsetX, this.teTrack.yCoord + ForgeDirection.EAST.offsetY, this.teTrack.zCoord + ForgeDirection.EAST.offsetZ)) {
						return true;
					}
				} else if (track.teTrack.direction == ForgeDirection.SOUTH) {
					if (world.isAirBlock(this.teTrack.xCoord + ForgeDirection.WEST.offsetX, this.teTrack.yCoord + ForgeDirection.WEST.offsetY, this.teTrack.zCoord + ForgeDirection.WEST.offsetZ)) {
						return true;
					}
				} else if (track.teTrack.direction == ForgeDirection.WEST) {
					if (world.isAirBlock(this.teTrack.xCoord + ForgeDirection.NORTH.offsetX, this.teTrack.yCoord + ForgeDirection.NORTH.offsetY, this.teTrack.zCoord + ForgeDirection.NORTH.offsetZ)) {
						return true;
					}
				} else if (track.teTrack.direction == ForgeDirection.EAST) {
					if (world.isAirBlock(this.teTrack.xCoord + ForgeDirection.SOUTH.offsetX, this.teTrack.yCoord + ForgeDirection.SOUTH.offsetY, this.teTrack.zCoord + ForgeDirection.SOUTH.offsetZ)) {
						return true;
>>>>>>> master
					}
				}
			}
		}
		return false;
	}

<<<<<<< HEAD
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
	public void updateRotation(World world, int x, int y, int z) {
		if (!world.isRemote) {
			if (this.teTrack.isHorizontal(this)) {
				if (this.teTrack.isCorner(neighbors[2])) {
					if (((BlockTrack) neighbors[2]).teTrack.direction == ForgeDirection.WEST || ((BlockTrack) neighbors[2]).teTrack.direction == ForgeDirection.NORTH) {
						this.teTrack.direction = ForgeDirection.WEST;
					}
				} else if (teTrack.isHorizontal(neighbors[2])) {
					if (((BlockTrack) neighbors[2]).teTrack.direction == ForgeDirection.WEST || ((BlockTrack) neighbors[2]).teTrack.direction == ForgeDirection.EAST) {
						this.teTrack.direction = ((BlockTrack) neighbors[2]).teTrack.direction;
					}
				}

				if (this.teTrack.isCorner(neighbors[3])) {
					if (((BlockTrack) neighbors[3]).teTrack.direction == ForgeDirection.SOUTH || ((BlockTrack) neighbors[3]).teTrack.direction == ForgeDirection.EAST) {
						this.teTrack.direction = ForgeDirection.WEST;
					}
				} else if (teTrack.isHorizontal(neighbors[3])) {
					if (((BlockTrack) neighbors[3]).teTrack.direction == ForgeDirection.WEST || ((BlockTrack) neighbors[3]).teTrack.direction == ForgeDirection.EAST) {
						this.teTrack.direction = ((BlockTrack) neighbors[3]).teTrack.direction;
					}
				}

				if (this.teTrack.isCorner(neighbors[0])) {
					if (((BlockTrack) neighbors[0]).teTrack.direction == ForgeDirection.NORTH || ((BlockTrack) neighbors[0]).teTrack.direction == ForgeDirection.EAST) {
							this.teTrack.direction = ForgeDirection.SOUTH;
					}
				}

				if (this.teTrack.isCorner(neighbors[1])) {
					if (((BlockTrack) neighbors[1]).teTrack.direction == ForgeDirection.SOUTH || ((BlockTrack) neighbors[1]).teTrack.direction == ForgeDirection.WEST) {
						this.teTrack.direction = ForgeDirection.SOUTH;
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
			if (this.teTrack.isCorner(this)) {
				if (this.teTrack.isHorizontal(neighbors[2])) {
					if (((BlockTrack) neighbors[2]).teTrack.direction == ForgeDirection.WEST || ((BlockTrack) neighbors[2]).teTrack.direction == ForgeDirection.EAST) {
//						this.teTrack.direction = ((BlockTrackNew) neighbors[2]).teTrack.direction;
						this.teTrack.direction = ForgeDirection.EAST;

						if (this.teTrack.isHorizontal(neighbors[0])) {
							if (((BlockTrack) neighbors[0]).teTrack.direction == ForgeDirection.SOUTH || ((BlockTrack) neighbors[0]).teTrack.direction == ForgeDirection.NORTH) {
//								this.teTrack.direction = ((BlockTrackNew) neighbors[0]).teTrack.direction;
								this.teTrack.direction = ForgeDirection.SOUTH;
>>>>>>> master
							}
						}

<<<<<<< HEAD
							if (isBlockTrack(blockSouth)) {
								if (isHorizontal(blockSouth)) {
									TileEntityTrack teSouth = (TileEntityTrack) world.getTileEntity(new BlockPos(x, y, z + 1));
									if (teSouth.direction == 0 || teSouth.direction == 2) {
										track.direction = 3;
									}
								}
=======
						if (this.teTrack.isHorizontal(neighbors[1])) {
							if (((BlockTrack) neighbors[1]).teTrack.direction == ForgeDirection.SOUTH || ((BlockTrack) neighbors[1]).teTrack.direction == ForgeDirection.NORTH) {
								this.teTrack.direction = ForgeDirection.EAST;
>>>>>>> master
							}
						}
					}
				}

<<<<<<< HEAD
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
=======
				if (this.teTrack.isHorizontal(neighbors[3])) {
					if (((BlockTrack) neighbors[3]).teTrack.direction == ForgeDirection.WEST || ((BlockTrack) neighbors[3]).teTrack.direction == ForgeDirection.EAST) {
						this.teTrack.direction = ForgeDirection.WEST;

						if (this.teTrack.isHorizontal(neighbors[0])) {
							if (((BlockTrack) neighbors[0]).teTrack.direction == ForgeDirection.SOUTH || ((BlockTrack) neighbors[0]).teTrack.direction == ForgeDirection.NORTH) {
								this.teTrack.direction = ForgeDirection.WEST;
>>>>>>> master
							}
						}

<<<<<<< HEAD
							if (isBlockTrack(blockSouth)) {
								if (isHorizontal(blockSouth)) {
									TileEntityTrack teSouth = (TileEntityTrack) world.getTileEntity(new BlockPos(x, y, z + 1));
									if (teSouth.direction == 0 || teSouth.direction == 2) {
										track.direction = 2;
									}
								}
=======
						if (this.teTrack.isHorizontal(neighbors[1])) {
							if (((BlockTrack) neighbors[1]).teTrack.direction == ForgeDirection.SOUTH || ((BlockTrack) neighbors[1]).teTrack.direction == ForgeDirection.NORTH) {
								this.teTrack.direction = ForgeDirection.NORTH;
>>>>>>> master
							}
						}
					}
				}

<<<<<<< HEAD
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
=======
				if (this.teTrack.isHorizontal(neighbors[0])) {
					if (((BlockTrack) neighbors[0]).teTrack.direction == ForgeDirection.SOUTH || ((BlockTrack) neighbors[0]).teTrack.direction == ForgeDirection.NORTH) {
						this.teTrack.direction = ForgeDirection.SOUTH;
					}
				}

				if (this.teTrack.isHorizontal(neighbors[1])) {
					if (((BlockTrack) neighbors[1]).teTrack.direction == ForgeDirection.SOUTH || ((BlockTrack) neighbors[1]).teTrack.direction == ForgeDirection.NORTH) {
						this.teTrack.direction = ForgeDirection.NORTH;
>>>>>>> master
					}
				}
			}

			if (this.teTrack.hasSlope(this)) {
				if (this.canRotate(world, this)) {
					if (this.teTrack.direction == ForgeDirection.WEST) {
						if (neighbors[1] instanceof BlockDummy) {
							((BlockDummy) neighbors[1]).setBreakSlope(false);
							neighbors[1].onBlockHarvested(world, this.teTrack.dummyX, this.teTrack.dummyY, this.teTrack.dummyZ, 0, Minecraft.getMinecraft().thePlayer);
							world.markBlockForUpdate(this.teTrack.dummyX, this.teTrack.dummyY, this.teTrack.dummyZ);
						}
						neighbors[1] = RCBlocks.dummy;
						this.teTrack.placeDummy(world, this.teTrack.xCoord + ForgeDirection.WEST.offsetX, this.teTrack.yCoord + ForgeDirection.WEST.offsetY, this.teTrack.zCoord + ForgeDirection.WEST.offsetZ, (BlockDummy) neighbors[1], true);
					} else if (this.teTrack.direction == ForgeDirection.NORTH) {
						if (neighbors[2] instanceof BlockDummy) {
							((BlockDummy) neighbors[2]).setBreakSlope(false);
							neighbors[2].onBlockHarvested(world, this.teTrack.dummyX, this.teTrack.dummyY, this.teTrack.dummyZ, 0, Minecraft.getMinecraft().thePlayer);
							world.markBlockForUpdate(this.teTrack.dummyX, this.teTrack.dummyY, this.teTrack.dummyZ);
						}
						neighbors[2] = RCBlocks.dummy;
						this.teTrack.placeDummy(world, this.teTrack.xCoord + ForgeDirection.NORTH.offsetX, this.teTrack.yCoord + ForgeDirection.NORTH.offsetY, this.teTrack.zCoord + ForgeDirection.NORTH.offsetZ, (BlockDummy) neighbors[2], true);
					} else if (this.teTrack.direction == ForgeDirection.EAST) {
						if (neighbors[0] instanceof BlockDummy) {
							((BlockDummy) neighbors[0]).setBreakSlope(false);
							neighbors[0].onBlockHarvested(world, this.teTrack.dummyX, this.teTrack.dummyY, this.teTrack.dummyZ, 0, Minecraft.getMinecraft().thePlayer);
							world.markBlockForUpdate(this.teTrack.dummyX, this.teTrack.dummyY, this.teTrack.dummyZ);
						}
						neighbors[0] = RCBlocks.dummy;
						this.teTrack.placeDummy(world, this.teTrack.xCoord + ForgeDirection.EAST.offsetX, this.teTrack.yCoord + ForgeDirection.EAST.offsetY, this.teTrack.zCoord + ForgeDirection.EAST.offsetZ, (BlockDummy) neighbors[0], true);
					} else if (this.teTrack.direction == ForgeDirection.SOUTH) {
						if (neighbors[3] instanceof BlockDummy) {
							((BlockDummy) neighbors[3]).setBreakSlope(false);
							neighbors[3].onBlockHarvested(world, this.teTrack.dummyX, this.teTrack.dummyY, this.teTrack.dummyZ, 0, Minecraft.getMinecraft().thePlayer);
							world.markBlockForUpdate(this.teTrack.dummyX, this.teTrack.dummyY, this.teTrack.dummyZ);
						}
						neighbors[3] = RCBlocks.dummy;
						this.teTrack.placeDummy(world, this.teTrack.xCoord + ForgeDirection.SOUTH.offsetX, this.teTrack.yCoord + ForgeDirection.SOUTH.offsetY, this.teTrack.zCoord + ForgeDirection.SOUTH.offsetZ, (BlockDummy) neighbors[3], true);
					}
<<<<<<< HEAD
					TileEntityDummy dummy = (TileEntityDummy) world.getTileEntity(new BlockPos(x, y, z + 1));
					((BlockDummy) blockSouth).setBreakSlope(false);
					blockSouth.onBlockHarvested(world, dummy.getPos(), world.getBlockState(dummy.getPos()), Minecraft.getMinecraft().thePlayer);
					world.markBlockForUpdate(new BlockPos(dummy.xCoord, dummy.yCoord, dummy.zCoord));
					BlockDummy newDummy = (BlockDummy) RCBlocks.dummy;
					placeDummy(world, x - 1, y, z, newDummy, track, track.direction, true);
				} else if (track.direction == 2 && blockWest instanceof BlockDummy) {
					TileEntityDummy dummy = (TileEntityDummy) world.getTileEntity(new BlockPos(x - 1, y, z));
					((BlockDummy) blockWest).setBreakSlope(false);
					blockWest.onBlockHarvested(world, dummy.getPos(), world.getBlockState(dummy.getPos()), Minecraft.getMinecraft().thePlayer);
					world.markBlockForUpdate(new BlockPos(dummy.xCoord, dummy.yCoord, dummy.zCoord));
					BlockDummy newDummy = (BlockDummy) RCBlocks.dummy;
					placeDummy(world, x, y, z - 1, newDummy, track, track.direction, true);
				} else if (track.direction == 3 && blockNorth instanceof BlockDummy) {
					TileEntityDummy dummy = (TileEntityDummy) world.getTileEntity(new BlockPos(x, y, z - 1));
					((BlockDummy) blockNorth).setBreakSlope(false);
					blockNorth.onBlockHarvested(world, dummy.getPos(), world.getBlockState(dummy.getPos()), Minecraft.getMinecraft().thePlayer);
					world.markBlockForUpdate(new BlockPos(dummy.xCoord, dummy.yCoord, dummy.zCoord));
					BlockDummy newDummy = (BlockDummy) RCBlocks.dummy;
					placeDummy(world, x + 1, y, z, newDummy, track, track.direction, true);
				} else if (track.direction == 0 && blockEast instanceof BlockDummy) {
					TileEntityDummy dummy = (TileEntityDummy) world.getTileEntity(new BlockPos(x + 1, y, z));
					((BlockDummy) blockEast).setBreakSlope(false);
					blockEast.onBlockHarvested(world, dummy.getPos(), world.getBlockState(dummy.getPos()), Minecraft.getMinecraft().thePlayer);
					world.markBlockForUpdate(new BlockPos(dummy.xCoord, dummy.yCoord, dummy.zCoord));
					BlockDummy newDummy = (BlockDummy) RCBlocks.dummy;
					placeDummy(world, x, y, z + 1, newDummy, track, track.direction, true);
=======
>>>>>>> master
				}
			}
<<<<<<< HEAD

			world.markBlockForUpdate(new BlockPos(x, y, z));
		}
	}

	public void placeDummy(World world, int dummyX, int dummyY, int dummyZ, BlockDummy dummy, TileEntityTrack teSlope, int slopeDirection, boolean breakSlope) {
		world.setBlockState(new BlockPos(dummyX, dummyY, dummyZ), dummy.getDefaultState(), 3);
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
=======
			world.markBlockForUpdate(x, y, z);
		}
	}
>>>>>>> master
}
