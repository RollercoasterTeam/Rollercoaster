package robomuss.rc.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import robomuss.rc.block.te.TileEntityTrack;
import robomuss.rc.entity.EntityTrainDefault;
import robomuss.rc.item.ItemExtra;
import robomuss.rc.item.ItemTrain;
import robomuss.rc.item.RCItems;
import robomuss.rc.track.TrackHandler;
import robomuss.rc.track.piece.TrackPiece;
import robomuss.rc.util.IPaintable;

public class BlockTrack extends BlockTrackBase {
	public BlockTrack(TrackPiece track_type) {
		super(track_type);
	}
//	public TrackPiece track_type;
//	public TileEntityTrack teTrack;
//	public Block[] neighbors = new Block[4];
//
//	public BlockTrack(TrackPiece type) {
//		super(Material.iron);
//		setHardness(1F);
//		setResistance(3F);
//
//		this.track_type = type;
//	}
//
//	@Override
//	public void setBlockBoundsBasedOnState(IBlockAccess iba, int x, int y, int z) {
//		AxisAlignedBB bounds = track_type.getBlockBounds(iba, x, y, z);
//		setBlockBounds((float) bounds.minX, (float) bounds.minY, (float) bounds.minZ, (float) bounds.maxX, (float) bounds.maxY, (float) bounds.maxZ);
//	}
//
//	@Override
//	public TileEntity createNewTileEntity(World world, int meta) {
//		TileEntityTrack teTrack = new TileEntityTrack();
//		teTrack.track_type = this.track_type;
//		teTrack.track = this;
//		return teTrack;
//	}
//
//	@Override
//	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
//		if (!world.isRemote) {
//			if (player.getHeldItem() != null) {
//				if (player.getHeldItem().getItem() == RCItems.brush) {
//					TileEntityTrack teTrack = (TileEntityTrack) world.getTileEntity(x, y, z);
//					teTrack.colour = player.getHeldItem().getItemDamage();
//					world.markBlockForUpdate(x, y, z);
//					return true;
//				} else if (player.getHeldItem().getItem() instanceof ItemExtra) {
//					TileEntityTrack teTrack = (TileEntityTrack) world.getTileEntity(x, y, z);
//					teTrack.extra = TrackHandler.extras.get(((ItemExtra) player.getHeldItem().getItem()).id);
//					world.markBlockForUpdate(x, y, z);
//					return true;
//				}
//			}
//		}
//		return false;
//	}
//
//	@Override
//	public boolean isOpaqueCube() {
//		return false;
//	}
//
//	@Override
//	public int getRenderType() {
//		return -1;
//	}
//
//	@Override
//	public boolean renderAsNormalBlock() {
//		return false;
//	}
//
//	@Override
//	public int damageDropped(int dmg) {
//		return dmg;
//	}
//
//	@Override
//	public int getPaintMeta(World world, int x, int y, int z) {
//		return ((TileEntityTrack) world.getTileEntity(x, y, z)).colour;
//	}
//
//	@Override
//	public void onBlockAdded(World world, int x, int y, int z) {
//		if (world.getTileEntity(x, y, z) != null && world.getTileEntity(x, y, z) instanceof TileEntityTrack) {
//			this.teTrack = (TileEntityTrack) world.getTileEntity(x, y, z);
//			teTrack.style = TrackHandler.findTrackStyle("corkscrew");
//
//			findNeighborBlocks(world, x, y, z);
//
//			if (teTrack.hasSlope(this)) {
//				if (neighbors[teTrack.direction.ordinal() - 2] instanceof BlockDummy) {
//					teTrack.placeDummy(world, x + teTrack.direction.offsetX, y + teTrack.direction.offsetY, z + teTrack.direction.offsetZ, (BlockDummy) neighbors[teTrack.direction.ordinal() - 2], true);
//				}
//			}
//
//			findNeighborBlocks(world, x, y, z);
//			updateRotation(world, x, y, z);
//		}
//	}
//
//	@Override
//	public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
//		if (!world.isRemote) {
//			if (teTrack != null && teTrack.extra != null && teTrack.extra == TrackHandler.extras.get(3)) {
//				if (world.isBlockIndirectlyGettingPowered(x, y, z)) {
//					EntityTrainDefault entity = ItemTrain.spawnCart(world, x, y, z);
//					world.spawnEntityInWorld(entity);
//				}
//			}
//
////			if (teTrack.direction == ForgeDirection.NORTH && !(neighbors[0] instanceof BlockDummy)) {
////
////			}
//		}
//	}
//
//	@Override
//	public void onBlockHarvested(World world, int trackX, int trackY, int trackZ, int meta, EntityPlayer player) {          //TODO: make TileEntityDummy control breaking the slope?
//		findNeighborBlocks(world, trackX, trackY, trackZ);
//		if (world.getTileEntity(trackX, trackY, trackZ) instanceof TileEntityTrack) {
//			TileEntityTrack teTrack = (TileEntityTrack) world.getTileEntity(trackX, trackY, trackZ);
//			if (teTrack.hasSlope(this)) {
//				if (neighbors[teTrack.direction.ordinal() - 2] instanceof BlockDummy) {
//					((BlockDummy) neighbors[teTrack.direction.ordinal() - 2]).setBreakSlope(false);         //tell dummy to not break the slope block
//					neighbors[teTrack.direction.ordinal() - 2].onBlockHarvested(world, trackX + teTrack.direction.offsetX, trackY + teTrack.direction.offsetY, trackZ + teTrack.direction.offsetZ, meta, player);
//					world.markBlockForUpdate(trackX + teTrack.direction.offsetX, trackY + teTrack.direction.offsetY, trackZ + teTrack.direction.offsetZ);
//				}
//				world.setBlockToAir(trackX, trackY, trackZ);
//				world.markBlockForUpdate(trackX, trackY, trackZ);
//			}
//		}
//	}
//
//	public void findNeighborBlocks(World world, int x, int y, int z) {
//		if (!world.isRemote) {
//			for (int i = 0; i < neighbors.length; i++) {
//				int orientation = ForgeDirection.VALID_DIRECTIONS[i + 2].ordinal();                     //N,S,W,E
//				neighbors[i] = world.getBlock(x + ForgeDirection.getOrientation(orientation).offsetX, y + ForgeDirection.getOrientation(orientation).offsetY, z + ForgeDirection.getOrientation(orientation).offsetZ);
//			}
//		}
//	}
//
//	public boolean canRotate(World world, BlockTrack track) {
//		if (!world.isRemote) {
//			if (this.teTrack.hasSlope(track)) {
//				if (track.teTrack.direction == ForgeDirection.NORTH) {
//					if (world.isAirBlock(this.teTrack.xCoord + ForgeDirection.EAST.offsetX, this.teTrack.yCoord + ForgeDirection.EAST.offsetY, this.teTrack.zCoord + ForgeDirection.EAST.offsetZ)) {
//						return true;
//					}
//				} else if (track.teTrack.direction == ForgeDirection.SOUTH) {
//					if (world.isAirBlock(this.teTrack.xCoord + ForgeDirection.WEST.offsetX, this.teTrack.yCoord + ForgeDirection.WEST.offsetY, this.teTrack.zCoord + ForgeDirection.WEST.offsetZ)) {
//						return true;
//					}
//				} else if (track.teTrack.direction == ForgeDirection.WEST) {
//					if (world.isAirBlock(this.teTrack.xCoord + ForgeDirection.NORTH.offsetX, this.teTrack.yCoord + ForgeDirection.NORTH.offsetY, this.teTrack.zCoord + ForgeDirection.NORTH.offsetZ)) {
//						return true;
//					}
//				} else if (track.teTrack.direction == ForgeDirection.EAST) {
//					if (world.isAirBlock(this.teTrack.xCoord + ForgeDirection.SOUTH.offsetX, this.teTrack.yCoord + ForgeDirection.SOUTH.offsetY, this.teTrack.zCoord + ForgeDirection.SOUTH.offsetZ)) {
//						return true;
//					}
//				}
//			}
//		}
//		return false;
//	}
//
//	public void updateRotation(World world, int x, int y, int z) {
//		if (!world.isRemote) {
//			if (this.teTrack.isHorizontal(this)) {
//				if (this.teTrack.isCorner(neighbors[2])) {
//					if (((BlockTrack) neighbors[2]).teTrack.direction == ForgeDirection.WEST || ((BlockTrack) neighbors[2]).teTrack.direction == ForgeDirection.NORTH) {
//						this.teTrack.direction = ForgeDirection.WEST;
//					}
//				} else if (this.teTrack.isHorizontal(neighbors[2])) {
//					if (((BlockTrack) neighbors[2]).teTrack.direction == ForgeDirection.WEST || ((BlockTrack) neighbors[2]).teTrack.direction == ForgeDirection.EAST) {
//						this.teTrack.direction = ((BlockTrack) neighbors[2]).teTrack.direction;
//					}
//				}
//
//				if (this.teTrack.isCorner(neighbors[3])) {
//					if (((BlockTrack) neighbors[3]).teTrack.direction == ForgeDirection.SOUTH || ((BlockTrack) neighbors[3]).teTrack.direction == ForgeDirection.EAST) {
//						this.teTrack.direction = ForgeDirection.WEST;
//					}
//				} else if (this.teTrack.isHorizontal(neighbors[3])) {
//					if (((BlockTrack) neighbors[3]).teTrack.direction == ForgeDirection.WEST || ((BlockTrack) neighbors[3]).teTrack.direction == ForgeDirection.EAST) {
//						this.teTrack.direction = ((BlockTrack) neighbors[3]).teTrack.direction;
//					}
//				}
//
//				if (this.teTrack.isCorner(neighbors[0])) {
//					if (((BlockTrack) neighbors[0]).teTrack.direction == ForgeDirection.NORTH || ((BlockTrack) neighbors[0]).teTrack.direction == ForgeDirection.EAST) {
//							this.teTrack.direction = ForgeDirection.SOUTH;
//					}
//				}
//
//				if (this.teTrack.isCorner(neighbors[1])) {
//					if (((BlockTrack) neighbors[1]).teTrack.direction == ForgeDirection.SOUTH || ((BlockTrack) neighbors[1]).teTrack.direction == ForgeDirection.WEST) {
//						this.teTrack.direction = ForgeDirection.SOUTH;
//					}
//				}
//			}
//
//			if (this.teTrack.isCorner(this)) {
//				if (this.teTrack.isHorizontal(neighbors[2])) {
//					if (((BlockTrack) neighbors[2]).teTrack.direction == ForgeDirection.WEST || ((BlockTrack) neighbors[2]).teTrack.direction == ForgeDirection.EAST) {
////						this.teTrack.direction = ((BlockTrackNew) neighbors[2]).teTrack.direction;
//						this.teTrack.direction = ForgeDirection.EAST;
//
//						if (this.teTrack.isHorizontal(neighbors[0])) {
//							if (((BlockTrack) neighbors[0]).teTrack.direction == ForgeDirection.SOUTH || ((BlockTrack) neighbors[0]).teTrack.direction == ForgeDirection.NORTH) {
////								this.teTrack.direction = ((BlockTrackNew) neighbors[0]).teTrack.direction;
//								this.teTrack.direction = ForgeDirection.SOUTH;
//							}
//						}
//
//						if (this.teTrack.isHorizontal(neighbors[1])) {
//							if (((BlockTrack) neighbors[1]).teTrack.direction == ForgeDirection.SOUTH || ((BlockTrack) neighbors[1]).teTrack.direction == ForgeDirection.NORTH) {
//								this.teTrack.direction = ForgeDirection.EAST;
//							}
//						}
//					}
//				}
//
//				if (this.teTrack.isHorizontal(neighbors[3])) {
//					if (((BlockTrack) neighbors[3]).teTrack.direction == ForgeDirection.WEST || ((BlockTrack) neighbors[3]).teTrack.direction == ForgeDirection.EAST) {
//						this.teTrack.direction = ForgeDirection.WEST;
//
//						if (this.teTrack.isHorizontal(neighbors[0])) {
//							if (((BlockTrack) neighbors[0]).teTrack.direction == ForgeDirection.SOUTH || ((BlockTrack) neighbors[0]).teTrack.direction == ForgeDirection.NORTH) {
//								this.teTrack.direction = ForgeDirection.WEST;
//							}
//						}
//
//						if (this.teTrack.isHorizontal(neighbors[1])) {
//							if (((BlockTrack) neighbors[1]).teTrack.direction == ForgeDirection.SOUTH || ((BlockTrack) neighbors[1]).teTrack.direction == ForgeDirection.NORTH) {
//								this.teTrack.direction = ForgeDirection.NORTH;
//							}
//						}
//					}
//				}
//
//				if (this.teTrack.isHorizontal(neighbors[0])) {
//					if (((BlockTrack) neighbors[0]).teTrack.direction == ForgeDirection.SOUTH || ((BlockTrack) neighbors[0]).teTrack.direction == ForgeDirection.NORTH) {
//						this.teTrack.direction = ForgeDirection.SOUTH;
//					}
//				}
//
//				if (this.teTrack.isHorizontal(neighbors[1])) {
//					if (((BlockTrack) neighbors[1]).teTrack.direction == ForgeDirection.SOUTH || ((BlockTrack) neighbors[1]).teTrack.direction == ForgeDirection.NORTH) {
//						this.teTrack.direction = ForgeDirection.NORTH;
//					}
//				}
//			}
//
//			if (this.teTrack.hasSlope(this)) {
//				if (this.canRotate(world, this)) {
//					if (this.teTrack.direction == ForgeDirection.WEST) {
//						if (neighbors[1] instanceof BlockDummy) {
//							((BlockDummy) neighbors[1]).setBreakSlope(false);
//							neighbors[1].onBlockHarvested(world, this.teTrack.dummyX, this.teTrack.dummyY, this.teTrack.dummyZ, 0, Minecraft.getMinecraft().thePlayer);
////							world.setBlockToAir(this.teTrack.dummyX, this.teTrack.dummyY, this.teTrack.dummyZ);
//							world.markBlockForUpdate(this.teTrack.dummyX, this.teTrack.dummyY, this.teTrack.dummyZ);
//						}
//						neighbors[2] = RCBlocks.dummy;
//						this.teTrack.placeDummy(world, this.teTrack.xCoord + ForgeDirection.WEST.offsetX, this.teTrack.yCoord + ForgeDirection.WEST.offsetY, this.teTrack.zCoord + ForgeDirection.WEST.offsetZ, (BlockDummy) neighbors[2], true);
//					} else if (this.teTrack.direction == ForgeDirection.NORTH) {
//						if (neighbors[2] instanceof BlockDummy) {
//							((BlockDummy) neighbors[2]).setBreakSlope(false);
//							neighbors[2].onBlockHarvested(world, this.teTrack.dummyX, this.teTrack.dummyY, this.teTrack.dummyZ, 0, Minecraft.getMinecraft().thePlayer);
//							world.markBlockForUpdate(this.teTrack.dummyX, this.teTrack.dummyY, this.teTrack.dummyZ);
//						}
//						neighbors[0] = RCBlocks.dummy;
//						this.teTrack.placeDummy(world, this.teTrack.xCoord + ForgeDirection.NORTH.offsetX, this.teTrack.yCoord + ForgeDirection.NORTH.offsetY, this.teTrack.zCoord + ForgeDirection.NORTH.offsetZ, (BlockDummy) neighbors[0], true);
//					} else if (this.teTrack.direction == ForgeDirection.EAST) {
//						if (neighbors[0] instanceof BlockDummy) {
//							((BlockDummy) neighbors[0]).setBreakSlope(false);
//							neighbors[0].onBlockHarvested(world, this.teTrack.dummyX, this.teTrack.dummyY, this.teTrack.dummyZ, 0, Minecraft.getMinecraft().thePlayer);
//							world.markBlockForUpdate(this.teTrack.dummyX, this.teTrack.dummyY, this.teTrack.dummyZ);
//						}
//						neighbors[3] = RCBlocks.dummy;
//						this.teTrack.placeDummy(world, this.teTrack.xCoord + ForgeDirection.EAST.offsetX, this.teTrack.yCoord + ForgeDirection.EAST.offsetY, this.teTrack.zCoord + ForgeDirection.EAST.offsetZ, (BlockDummy) neighbors[3], true);
//					} else if (this.teTrack.direction == ForgeDirection.SOUTH) {
//						if (neighbors[3] instanceof BlockDummy) {
//							((BlockDummy) neighbors[3]).setBreakSlope(false);
//							neighbors[3].onBlockHarvested(world, this.teTrack.dummyX, this.teTrack.dummyY, this.teTrack.dummyZ, 0, Minecraft.getMinecraft().thePlayer);
//							world.markBlockForUpdate(this.teTrack.dummyX, this.teTrack.dummyY, this.teTrack.dummyZ);
//						}
//						neighbors[1] = RCBlocks.dummy;
//						this.teTrack.placeDummy(world, this.teTrack.xCoord + ForgeDirection.SOUTH.offsetX, this.teTrack.yCoord + ForgeDirection.SOUTH.offsetY, this.teTrack.zCoord + ForgeDirection.SOUTH.offsetZ, (BlockDummy) neighbors[1], true);
//					}
//				}
//			}
//			world.markBlockForUpdate(x, y, z);
//		}
//	}
}
