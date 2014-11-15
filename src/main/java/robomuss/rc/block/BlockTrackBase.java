package robomuss.rc.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.BlockSnapshot;
import net.minecraftforge.event.world.BlockEvent;
import robomuss.rc.RCMod;
import robomuss.rc.block.te.TileEntityTrackBase;
import robomuss.rc.entity.EntityTrainDefault;
import robomuss.rc.item.ItemExtra;
import robomuss.rc.item.ItemTrain;
import robomuss.rc.item.RCItems;
import robomuss.rc.network.packets.PacketChangePaintColour;
import robomuss.rc.track.TrackHandler;
import robomuss.rc.track.TrackManager;
import robomuss.rc.track.piece.TrackPiece;

import java.util.List;

public class BlockTrackBase extends BlockContainer {
	public TrackPiece track_type;

	public BlockTrackBase(TrackPiece track_type) {
		super(Material.iron);
		setHardness(1F);
		setResistance(3F);
//		setLightOpacity(128);   //TODO: experiment with the results of this
		this.track_type = track_type;
//		hammerOverlay = new GuiHammerOverlay(Minecraft.getMinecraft());
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess iba, int x, int y, int z) {
//		this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.5f, 1.0f);
		AxisAlignedBB bounds = track_type.getBlockBounds(iba, x, y, z);
		this.setBlockBounds((float) bounds.minX, (float) bounds.minY, (float) bounds.minZ, (float) bounds.maxX, (float) bounds.maxY, (float) bounds.maxZ);
	}

	/**
	 * Returns the bounding box of the wired rectangular prism to render.
	 */
	@Override
	@SideOnly(Side.CLIENT)
	public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int x, int y, int z) {
//		return super.getSelectedBoundingBoxFromPool(world, x, y, z);
		return this.getCollisionBoundingBoxFromPool(world, x, y, z);
	}

	/**
	 * Adds all intersecting collision boxes to a list. (Be sure to only add boxes to the list if they intersect the
	 * mask.) Parameters: World, X, Y, Z, mask, list, colliding entity
	 */
	@Override
	public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB mask, List list, Entity entity) {
		if (TrackManager.isSloped(TrackManager.getTrackType(this))) {
			setSlopedBounds(world, x, y, z, mask, list, entity);
		} else {
			setTrackBounds(world, x, y, z, mask, list, entity);
		}
//		this.setBottomBounds(world, x, y, z);
//		super.addCollisionBoxesToList(world, x, y, z, mask, list, entity);
//		this.setTopBounds(world, x, y, z);
//		super.addCollisionBoxesToList(world, x, y, z, mask, list, entity);
//
//		this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
	}

	public void setSlopedBounds(World world, int x, int y, int z, AxisAlignedBB mask, List list, Entity entity) {
		if (this.track_type == TrackHandler.findTrackType("slope_up")) {
			if (!((TileEntityTrackBase) world.getTileEntity(x, y, z)).isDummy) {
				this.setBlockBounds(0f, 0f, 0f, 1f, 0.4f, 1f);
				super.addCollisionBoxesToList(world, x, y, z, mask, list, entity);
			} else {
				setDummyBounds(world, x, y, z, mask, list, entity);
			}
		} else if (this.track_type == TrackHandler.findTrackType("slope")) {
			if (((TileEntityTrackBase) world.getTileEntity(x, y, z)).isDummy) {
				setDummyBounds(world, x, y, z, mask, list, entity);
			}
		} else if (this.track_type == TrackHandler.findTrackType("slope_down")) {
			if (((TileEntityTrackBase) world.getTileEntity(x, y, z)).isDummy) {
				setDummyBounds(world, x, y, z, mask, list, entity);
				//TODO: figure out how the dummies for slope_down will work...
			}
		}
	}

	public void setDummyBounds(World world, int x, int y, int z, AxisAlignedBB mask, List list, Entity entity) {
//		System.out.println("dummy");
		int meta = world.getBlockMetadata(x, y, z);
		meta = meta > 11 ? meta - 10 : meta;
		if (meta == 2) {
//			System.out.println("2");
			this.setBlockBounds(0f, 0f, 0f, 1f, 0.5f, 1f);
			super.addCollisionBoxesToList(world, x, y, z, mask, list, entity);
			this.setBlockBounds(0f, 0.5f, 0f, 1f, 1f, 0.5f);
			super.addCollisionBoxesToList(world, x, y, z, mask, list, entity);
		} else if (meta == 3) {
//			System.out.println("3");
			this.setBlockBounds(0f, 0f, 0f, 1f, 0.5f, 1f);
			super.addCollisionBoxesToList(world, x, y, z, mask, list, entity);
			this.setBlockBounds(0f, 0.5f, 0.5f, 1f, 1f, 1f);
			super.addCollisionBoxesToList(world, x, y, z, mask, list, entity);
		} else if (meta == 4) {
//			System.out.println("4");
			this.setBlockBounds(0f, 0f, 0f, 1f, 0.5f, 1f);
			super.addCollisionBoxesToList(world, x, y, z, mask, list, entity);
			this.setBlockBounds(0f, 0.5f, 0f, 0.5f, 1f, 1f);
			super.addCollisionBoxesToList(world, x, y, z, mask, list, entity);
		} else if (meta == 5) {
//			System.out.println("5");
			this.setBlockBounds(0f, 0f, 0f, 1f, 0.5f, 1f);
			super.addCollisionBoxesToList(world, x, y, z, mask, list, entity);
			this.setBlockBounds(0.5f, 0.5f, 0f, 1f, 1f, 1f);
			super.addCollisionBoxesToList(world, x, y, z, mask, list, entity);
		}
	}

	public void setTrackBounds(World world, int x, int y, int z, AxisAlignedBB mask, List list, Entity entity) {
		AxisAlignedBB bounds = track_type.getBlockBounds(world, x, y, z);
		this.setBlockBounds((float) bounds.minX, (float) bounds.minY, (float) bounds.minZ, (float) bounds.maxX, (float) bounds.maxY, (float) bounds.maxZ);
		super.addCollisionBoxesToList(world, x, y, z, mask, list, entity);
	}

	public void setBottomBounds(IBlockAccess iba, int x, int y, int z) {
//		if (this.track_type == TrackHandler.findTrackType("slope_up")) {
//			if (!((TileEntityTrackBase) iba.getTileEntity(x, y, z)).isDummy) {
//				this.setBlockBounds(0f, 0f, 0f, 1f, 0.5f, 1f);
//			} else {
//
//			}
//		}
		if (TrackManager.isSloped(TrackManager.getTrackType(this))) {
			this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.5f, 1.0f);    //bottom-half
		}
	}

	public void setTopBounds(IBlockAccess iba, int x, int y, int z) {
		int meta = iba.getBlockMetadata(x, y, z);
//		int facing = meta == 2 ? 2 : meta == 3 ? 0 : meta == 4 ? 1 : meta == 5 ? 3 : 2;

		/* minY == 0 is bottom of block, maxY == 1 is top of block */
		float mnY = 0.5f;   //half-Y
		float mxY = 1.0f;   //top-Y
		float mnX = 0.0f;   //west-X (?)
		float mxX = 1.0f;   //east-X (?)
		float mnZ = 0.0f;   //south-Z
		float mxZ = 0.5f;   //half-Z


		if (meta == 2) {
			if (this.track_type == TrackHandler.findTrackType("slope_up")) {
				if (!((TileEntityTrackBase) iba.getTileEntity(x, y, z)).isDummy) {
					return;
				} else {
					this.setBlockBounds(0f, 0.5f, 0f, 1f, 1f, 0.5f);
				}
			} else if (this.track_type == TrackHandler.findTrackType("slope")) {
				if (!((TileEntityTrackBase) iba.getTileEntity(x, y, z)).isDummy) {
					return;
				} else {
					this.setBlockBounds(0f, 0.5f, 0f, 1f, 1f, 0.5f);
				}
			} else if (this.track_type == TrackHandler.findTrackType("slope_down")) {
				if (!((TileEntityTrackBase) iba.getTileEntity(x, y, z)).isDummy) {
					return;
				} else {
					this.setBlockBounds(0f, 0.5f, 0f, 1f, 1f, 0.5f);
				}
			}
		} else if (meta == 3) {
			if (this.track_type == TrackHandler.findTrackType("slope_up")) {
				if (!((TileEntityTrackBase) iba.getTileEntity(x, y, z)).isDummy) {
					return;
				} else {
					this.setBlockBounds(0f, 0.5f, 0.5f, 1f, 1f, 1f);
				}
			} else if (this.track_type == TrackHandler.findTrackType("slope")) {
				if (!((TileEntityTrackBase) iba.getTileEntity(x, y, z)).isDummy) {
					return;
				} else {
					this.setBlockBounds(0f, 0.5f, 0.5f, 1f, 1f, 1f);
				}
			} else if (this.track_type == TrackHandler.findTrackType("slope_down")) {
				if (!((TileEntityTrackBase) iba.getTileEntity(x, y, z)).isDummy) {
					return;
				} else {
					this.setBlockBounds(0f, 0.5f, 0.5f, 1f, 1f, 1f);
				}
			}
		} else if (meta == 4) {
			if (this.track_type == TrackHandler.findTrackType("slope_up")) {
				if (!((TileEntityTrackBase) iba.getTileEntity(x, y, z)).isDummy) {
					return;
				} else {
					this.setBlockBounds(0f, 0.5f, 0f, 0.5f, 1f, 1f);
				}
			} else if (this.track_type == TrackHandler.findTrackType("slope")) {
				if (!((TileEntityTrackBase) iba.getTileEntity(x, y, z)).isDummy) {
					return;
				} else {
					this.setBlockBounds(0f, 0.5f, 0f, 0.5f, 1f, 1f);
				}
			} else if (this.track_type == TrackHandler.findTrackType("slope_down")) {
				if (!((TileEntityTrackBase) iba.getTileEntity(x, y, z)).isDummy) {
					return;
				} else {
					this.setBlockBounds(0f, 0.5f, 0f, 0.5f, 1f, 1f);
				}
			}
		} else if (meta == 5) {
			if (this.track_type == TrackHandler.findTrackType("slope_up")) {
				if (!((TileEntityTrackBase) iba.getTileEntity(x, y, z)).isDummy) {
					return;
				} else {
					this.setBlockBounds(0.5f, 0.5f, 0f, 1f, 1f, 1f);
				}
			} else if (this.track_type == TrackHandler.findTrackType("slope")) {
				if (!((TileEntityTrackBase) iba.getTileEntity(x, y, z)).isDummy) {
					return;
				} else {
					this.setBlockBounds(0.5f, 0.5f, 0f, 1f, 1f, 1f);
				}
			} else if (this.track_type == TrackHandler.findTrackType("slope_down")) {
				if (!((TileEntityTrackBase) iba.getTileEntity(x, y, z)).isDummy) {
					return;
				} else {
					this.setBlockBounds(0.5f, 0.5f, 0f, 1f, 1f, 1f);
				}
			}
		}


//		if (meta == 2) {
//			if (TrackManager.isSloped(TrackManager.getTrackType(this)) && this.track_type != TrackHandler.findTrackType("slope_down")) {
//				if (!((TileEntityTrackBase) iba.getTileEntity(x, y, z)).isDummy) {
//					mnZ = 0.5f;
//					mxZ = 1.0f;
//				} else {
//					mnY = 0.5f;
//					mxY = 1.0f;
//					mnX = 0.0f;
//					mxX = 1.0f;
//					mnZ = 0.0f;
//					mxZ = 1.0f;
//				}
//			} else if (this.track_type == TrackHandler.findTrackType("slope_down")) {
//				if (!((TileEntityTrackBase) iba.getTileEntity(x, y, z)).isDummy) {
//					mnZ = 0.5f;
//					mxZ = 1.0f;
//				} else {
//					//TODO: implement a "dummyID" system, or some other way of determining if the dummy should be a full block hit box, or a partial one.
//					mnY = 0.5f;
//					mxY = 1.0f;
//					mnX = 0.0f;
//					mxX = 1.0f;
//					mnZ = 0.0f;
//					mxZ = 1.0f;
//				}
//			}
//		} else if (meta == 3) {
//			if (TrackManager.isSloped(TrackManager.getTrackType(this)) && this.track_type != TrackHandler.findTrackType("slope_down")) {
//				if (!((TileEntityTrackBase) iba.getTileEntity(x, y, z)).isDummy) {
//					mnZ = 0.0f;
//					mxZ = 0.5f;
//				} else {
//					mnY = 0.5f;
//					mxY = 1.0f;
//					mnX = 0.0f;
//					mxX = 1.0f;
//					mnZ = 0.0f;
//					mxZ = 1.0f;
//				}
//			} else if (this.track_type == TrackHandler.findTrackType("slope_down")) {
//				if (!((TileEntityTrackBase) iba.getTileEntity(x, y, z)).isDummy) {
//					mnZ = 0.0f;
//					mxZ = 0.5f;
//				} else {
//					//TODO: implement a "dummyID" system, or some other way of determining if the dummy should be a full block hit box, or a partial one.
//					mnY = 0.5f;
//					mxY = 1.0f;
//					mnX = 0.0f;
//					mxX = 1.0f;
//					mnZ = 0.0f;
//					mxZ = 1.0f;
//				}
//			}
//		} else if (meta == 4) {
//			if (TrackManager.isSloped(TrackManager.getTrackType(this)) && this.track_type != TrackHandler.findTrackType("slope_down")) {
//				if (!((TileEntityTrackBase) iba.getTileEntity(x, y, z)).isDummy) {
//					mnX = 0.0f;
//					mxX = 0.5f;
//				} else {
//					mnY = 0.5f;
//					mxY = 1.0f;
//					mnX = 0.0f;
//					mxX = 1.0f;
//					mnZ = 0.0f;
//					mxZ = 1.0f;
//				}
//			} else if (this.track_type == TrackHandler.findTrackType("slope_down")) {
//				if (!((TileEntityTrackBase) iba.getTileEntity(x, y, z)).isDummy) {
//					mnX = 0.0f;
//					mxX = 0.5f;
//				} else {
//					//TODO: implement a "dummyID" system, or some other way of determining if the dummy should be a full block hit box, or a partial one.
//					mnY = 0.5f;
//					mxY = 1.0f;
//					mnX = 0.0f;
//					mxX = 1.0f;
//					mnZ = 0.0f;
//					mxZ = 1.0f;
//				}
//			}
//		} else if (meta == 5) {
//			if (TrackManager.isSloped(TrackManager.getTrackType(this)) && this.track_type != TrackHandler.findTrackType("slope_down")) {
//				if (!((TileEntityTrackBase) iba.getTileEntity(x, y, z)).isDummy) {
//					mnX = 0.5f;
//					mxX = 1.0f;
//				} else {
//					mnY = 0.5f;
//					mxY = 1.0f;
//					mnX = 0.0f;
//					mxX = 1.0f;
//					mnZ = 0.0f;
//					mxZ = 1.0f;
//				}
//			} else if (this.track_type == TrackHandler.findTrackType("slope_down")) {
//				if (!((TileEntityTrackBase) iba.getTileEntity(x, y, z)).isDummy) {
//					mnX = 0.5f;
//					mxX = 1.0f;
//				} else {
//					//TODO: implement a "dummyID" system, or some other way of determining if the dummy should be a full block hit box, or a partial one.
//					mnY = 0.5f;
//					mxY = 1.0f;
//					mnX = 0.0f;
//					mxX = 1.0f;
//					mnZ = 0.0f;
//					mxZ = 1.0f;
//				}
//			}
//		} else {
//			mnY = 0.5f;
//			mxY = 1.0f;
//			mnX = 0.0f;
//			mxX = 1.0f;
//			mnZ = 0.0f;
//			mxZ = 1.0f;
//		}

//		this.setBlockBounds(mnX, mnY, mnZ, mxX, mxY, mxZ);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
//		System.out.println("Track TE created.");
		TileEntityTrackBase teTrack = new TileEntityTrackBase(world, meta, this);
		this.track_type.addTileEntityToList(this.track_type, teTrack);
		if (meta > 11) {
			teTrack.isDummy = true;
		}
		return teTrack;
//		return new TileEntityTrackBase(new BlockTrackBase(this.track_type));
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if (!world.isRemote) {
			if (world.getTileEntity(x, y, z) instanceof TileEntityTrackBase) {
				TileEntityTrackBase teTrack = (TileEntityTrackBase) world.getTileEntity(x, y, z);
				if (teTrack != null) {
					if (player.getHeldItem() != null) {
						if (player.getHeldItem().getItem() == RCItems.brush) {
							teTrack.colour = player.getHeldItem().getItemDamage();
							world.markBlockForUpdate(x, y, z);
							return true;
						} else if (player.getHeldItem().getItem() instanceof ItemExtra) {
							teTrack.extra = TrackHandler.extras.get(((ItemExtra) player.getHeldItem().getItem()).id);
							world.markBlockForUpdate(x, y, z);
							return true;
						}
					}
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
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public int damageDropped(int dmg) {
		return dmg;
	}

	@Override
	public boolean isBlockSolid(IBlockAccess iba, int neighborX, int neighborY, int neighborZ, int side) {
		return false;
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack item) {
//		if (this.track_type != null && TrackManager.isSloped(TrackManager.getTrackType(this))) {
			MovingObjectPosition viewEntityTrace = player.rayTrace(20, 20);
			BlockEvent.PlaceEvent event = new BlockEvent.PlaceEvent(BlockSnapshot.getBlockSnapshot(world, x, y, z), world.getBlock(viewEntityTrace.blockX, viewEntityTrace.blockY, viewEntityTrace.blockZ), (EntityPlayer) player);
			MinecraftForge.EVENT_BUS.post(event);
//		}

		int facing = MathHelper.floor_double((double)(player.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;

		int meta = 0;

		switch (facing) {
			case 0: world.setBlockMetadataWithNotify(x, y, z, 3, 2); meta = 3; break;
			case 1: world.setBlockMetadataWithNotify(x, y, z, 4, 2); meta = 4; break;
			case 2: world.setBlockMetadataWithNotify(x, y, z, 2, 2); meta = 2; break;
			case 3: world.setBlockMetadataWithNotify(x, y, z, 5, 2); meta = 5; break;
		}

		if (TrackManager.isSloped(TrackManager.getTrackType(this))) {
			((TileEntityTrackBase) world.getTileEntity(x, y, z)).placeDummy();
		}
	}

	public boolean canPlaceDummyAt(World world, int x, int y, int z) {
		if (world.isAirBlock(x, y, z)) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void onBlockAdded(World world, int x, int y, int z) {

	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
		if (!world.isRemote) {
			TileEntityTrackBase teTrack = (TileEntityTrackBase) world.getTileEntity(x, y, z);
			if (teTrack != null) {
				if (teTrack.extra != null && teTrack.extra == TrackHandler.extras.get(3)) {
					if (world.isBlockIndirectlyGettingPowered(x, y, z)) {
						EntityTrainDefault entity = ItemTrain.spawnCart(world, x, y, z);
						world.spawnEntityInWorld(entity);
					}
				}
			}
		}
	}

	@Override
	public void onBlockHarvested(World world, int trackX, int trackY, int trackZ, int meta, EntityPlayer player) {
		if (!world.isRemote) {
			world.setBlockToAir(trackX, trackY, trackZ);
			world.markBlockForUpdate(trackX, trackY, trackZ);
		}
	}
}
