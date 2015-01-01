package robomuss.rc.block;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
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
import robomuss.rc.track.TrackHandler;
import robomuss.rc.track.TrackManager;
import robomuss.rc.track.piece.TrackPiece;
import robomuss.rc.util.IPaintable;

import java.util.List;

public class BlockTrackBase extends BlockContainer implements IPaintable {
	public TrackPiece track_type;

	public BlockTrackBase(TrackPiece track_type) {
		super(Material.iron);
		setHardness(1F);
		setResistance(3F);
//		setLightOpacity(128);   //TODO: experiment with the results of this
		this.track_type = track_type;
	}

	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z, EntityPlayer player) {
		return new ItemStack(GameRegistry.findItem(RCMod.MODID, this.track_type.unlocalized_name + "_track"));
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess iba, int x, int y, int z) {
		AxisAlignedBB bounds = track_type.getBlockBounds(iba, x, y, z);
		this.setBlockBounds((float) bounds.minX, (float) bounds.minY, (float) bounds.minZ, (float) bounds.maxX, (float) bounds.maxY, (float) bounds.maxZ);
	}

	/**
	 * Returns the bounding box of the wired rectangular prism to render.
	 */
	@Override
	@SideOnly(Side.CLIENT)
	public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int x, int y, int z) {
		return this.getCollisionBoundingBoxFromPool(world, x, y, z);
	}

	/**
	 * Adds all intersecting collision boxes to a list. (Be sure to only add boxes to the list if they intersect the
	 * mask.) Parameters: World, X, Y, Z, mask, list, colliding entity
	 */
	@Override
	public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB mask, List list, Entity entity) {
		if (TrackManager.isSloped(this.track_type)) {
			setSlopedBounds(world, x, y, z, mask, list, entity);
		} else {
			setTrackBounds(world, x, y, z, mask, list, entity);
		}
	}

	public void setSlopedBounds(World world, int x, int y, int z, AxisAlignedBB mask, List list, Entity entity) {
		if (this.track_type == TrackHandler.Types.SLOPE_UP.type) {
			if (!((TileEntityTrackBase) world.getTileEntity(x, y, z)).isDummy) {
				this.setBlockBounds(0f, 0f, 0f, 1f, 0.4f, 1f);
				super.addCollisionBoxesToList(world, x, y, z, mask, list, entity);
			} else {
				setDummyBounds(world, x, y, z, mask, list, entity);
			}
		} else if (this.track_type == TrackHandler.Types.SLOPE.type) {
			if (((TileEntityTrackBase) world.getTileEntity(x, y, z)).isDummy) {
				setDummyBounds(world, x, y, z, mask, list, entity);
			}
		} else if (this.track_type == TrackHandler.Types.SLOPE_DOWN.type) {
			if (((TileEntityTrackBase) world.getTileEntity(x, y, z)).isDummy) {
				setDummyBounds(world, x, y, z, mask, list, entity);
				//TODO: figure out how the dummies for slope_down will work...
			}
		}
	}

	public void setDummyBounds(World world, int x, int y, int z, AxisAlignedBB mask, List list, Entity entity) {
		int meta = world.getBlockMetadata(x, y, z);
		meta = meta > 11 ? meta - 10 : meta;                //meta data for dummy tracks is bound to 12-15

		TileEntityTrackBase teTrack = (TileEntityTrackBase) world.getTileEntity(x, y, z);

		if (meta == 2) {
			if (teTrack.dummyID == 0) {
				this.setBlockBounds(0f, 0f, 0f, 1f, 0.5f, 1f);
				super.addCollisionBoxesToList(world, x, y, z, mask, list, entity);
				this.setBlockBounds(0f, 0.5f, 0f, 1f, 1f, 0.5f);
				super.addCollisionBoxesToList(world, x, y, z, mask, list, entity);
			} else if (teTrack.dummyID == 2) {
				this.setBlockBounds(0f, 0f, 0f, 1f, 0.4f, 1f);
				super.addCollisionBoxesToList(world, x, y, z, mask, list, entity);
			}
		} else if (meta == 3) {
			if (teTrack.dummyID == 0) {
				this.setBlockBounds(0f, 0f, 0f, 1f, 0.5f, 1f);
				super.addCollisionBoxesToList(world, x, y, z, mask, list, entity);
				this.setBlockBounds(0f, 0.5f, 0.5f, 1f, 1f, 1f);
				super.addCollisionBoxesToList(world, x, y, z, mask, list, entity);
			} else if (teTrack.dummyID == 2) {
				this.setBlockBounds(0f, 0f, 0f, 1f, 0.4f, 1f);
				super.addCollisionBoxesToList(world, x, y, z, mask, list, entity);
			}
		} else if (meta == 4) {
			if (teTrack.dummyID == 0) {
				this.setBlockBounds(0f, 0f, 0f, 1f, 0.5f, 1f);
				super.addCollisionBoxesToList(world, x, y, z, mask, list, entity);
				this.setBlockBounds(0f, 0.5f, 0f, 0.5f, 1f, 1f);
				super.addCollisionBoxesToList(world, x, y, z, mask, list, entity);
			} else if (teTrack.dummyID == 2) {
				this.setBlockBounds(0f, 0f, 0f, 1f, 0.4f, 1f);
				super.addCollisionBoxesToList(world, x, y, z, mask, list, entity);
			}
		} else if (meta == 5) {
			if (teTrack.dummyID == 0) {
				this.setBlockBounds(0f, 0f, 0f, 1f, 0.5f, 1f);
				super.addCollisionBoxesToList(world, x, y, z, mask, list, entity);
				this.setBlockBounds(0.5f, 0.5f, 0f, 1f, 1f, 1f);
				super.addCollisionBoxesToList(world, x, y, z, mask, list, entity);
			} else if (teTrack.dummyID == 2) {
				this.setBlockBounds(0f, 0f, 0f, 1f, 0.4f, 1f);
				super.addCollisionBoxesToList(world, x, y, z, mask, list, entity);
			}
		}
	}

	public void setTrackBounds(World world, int x, int y, int z, AxisAlignedBB mask, List list, Entity entity) {
		AxisAlignedBB bounds = track_type.getBlockBounds(world, x, y, z);
		this.setBlockBounds((float) bounds.minX, (float) bounds.minY, (float) bounds.minZ, (float) bounds.maxX, (float) bounds.maxY, (float) bounds.maxZ);
		super.addCollisionBoxesToList(world, x, y, z, mask, list, entity);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		TileEntityTrackBase teTrack = new TileEntityTrackBase(world, meta, this);

		if (meta >= 11) {
			teTrack.isDummy = true;
		}

		return teTrack;
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if (player.getHeldItem() != null) {
			Item heldItem = player.getHeldItem().getItem();

			if (heldItem == RCItems.brush || heldItem == Items.water_bucket || heldItem instanceof ItemExtra) {
				System.out.println(heldItem.getUnlocalizedName());

				if (heldItem instanceof ItemExtra) {
					TileEntityTrackBase teTrack = (TileEntityTrackBase) world.getTileEntity(x, y, z);
					teTrack.extra = TrackHandler.findTrackExtra(((ItemExtra) heldItem).id);
					world.markBlockForUpdate(x, y, z);
					return true;
				}

				return true;
			}
		}

		return false;



//		if (!world.isRemote) {
//			if (world.getTileEntity(x, y, z) instanceof TileEntityTrackBase) {
//				TileEntityTrackBase teTrack = (TileEntityTrackBase) world.getTileEntity(x, y, z);
//
//				if (teTrack != null) {
//					if (player.getHeldItem() != null) {
//						if (player.getHeldItem().getItem() == RCItems.brush) {
//							teTrack.colour = player.getHeldItem().getItemDamage();
//							world.markBlockForUpdate(x, y, z);
//							return true;
//						} else if (player.getHeldItem().getItem() instanceof ItemExtra) {
//							teTrack.extra = TrackHandler.extras.get(((ItemExtra) player.getHeldItem().getItem()).id);
//							world.markBlockForUpdate(x, y, z);
//							return true;
//						}
//					}
//				}
//			}
//		}
//
//		if (player.getHeldItem() != null && player.getHeldItem().getItem() == Items.water_bucket) {
//			TileEntityTrackBase teTrack = (TileEntityTrackBase) world.getTileEntity(x, y, z);
//			teTrack.colour = ColourUtil.WHITE.ordinal();
//
//			if (!player.capabilities.isCreativeMode) {
//				player.inventory.setInventorySlotContents(player.inventory.currentItem, new ItemStack(Items.bucket));
//			}
//
//			return true;
//		}
//
//		return false;
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
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack itemStack) {
		MovingObjectPosition viewEntityTrace = player.rayTrace(20, 20);
		int viewX = viewEntityTrace.blockX;
		int viewY = viewEntityTrace.blockY;
		int viewZ = viewEntityTrace.blockZ;
		BlockEvent.PlaceEvent event = new BlockEvent.PlaceEvent(BlockSnapshot.getBlockSnapshot(world, x, y, z), world.getBlock(viewX, viewY, viewZ), (EntityPlayer) player);
		MinecraftForge.EVENT_BUS.post(event);

		int playerFacing = MathHelper.floor_double((double) (((EntityPlayer) player).rotationYaw * 4.0f / 360.0f) + 0.5d) & 3;
		int sideHit = viewEntityTrace.sideHit;

//		if (itemStack.getItem() instanceof ItemBlockTrack) {
//			ItemBlockTrack itemBlockTrack = (ItemBlockTrack) itemStack.getItem();
//			String[] splitPeriod = itemBlockTrack.getUnlocalizedName(itemStack).split("tile.");
//			String[] splitUnderscore = splitPeriod[1].split("_");
//			String trackTypeName = splitUnderscore.length == 3 ? splitUnderscore[0] + "_" + splitUnderscore[1] : splitUnderscore[0];
////			System.out.println("track_type: " + trackTypeName);
//			TrackPiece trackPiece = TrackHandler.findTrackType(trackTypeName);
//
//			if (trackPiece instanceof TrackPieceSlopeDown) {
//
//			}
//		}


//		MovingObjectPosition viewEntityTrace = player.rayTrace(20, 20);
//		int viewX = viewEntityTrace.blockX;
//		int viewY = viewEntityTrace.blockY;
//		int viewZ = viewEntityTrace.blockZ;
//		BlockEvent.PlaceEvent event = new BlockEvent.PlaceEvent(BlockSnapshot.getBlockSnapshot(world, x, y, z), world.getBlock(viewX, viewY, viewZ), (EntityPlayer) player);
//		MinecraftForge.EVENT_BUS.post(event);
//
//		int facing = MathHelper.floor_double((double)(player.rotationYaw * 4.0f / 360.0f) + 0.5d) & 3;

		switch (playerFacing) {
			case 0: world.setBlockMetadataWithNotify(x, y, z, 3, 2); break;
			case 1: world.setBlockMetadataWithNotify(x, y, z, 4, 2); break;
			case 2: world.setBlockMetadataWithNotify(x, y, z, 2, 2); break;
			case 3: world.setBlockMetadataWithNotify(x, y, z, 5, 2); break;
		}

		if (TrackManager.isSloped(TrackHandler.findTrackType(this.track_type.unlocalized_name))) {
//		if (TrackManager.isSloped(TrackHandler.Types.valueOf(this.track_type.unlocalized_name).typeID)) {
			((TileEntityTrackBase) world.getTileEntity(x, y, z)).placeDummies(TrackHandler.findTrackType(itemStack.getItem()).type);
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
	public void onBlockAdded(World world, int x, int y, int z) {}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
		if (!world.isRemote) {
			TileEntityTrackBase teTrack = (TileEntityTrackBase) world.getTileEntity(x, y, z);

			if (teTrack != null) {
				if (teTrack.extra != null && teTrack.extra == TrackHandler.Extras.STATION.extra) {
					if (world.isBlockIndirectlyGettingPowered(x, y, z)) {
						EntityTrainDefault entity = ItemTrain.spawnCar(world, x, y, z);
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

	@Override
	public int getPaintMeta(World world, int x, int y, int z) {
		return ((TileEntityTrackBase) world.getTileEntity(x, y, z)).colour;
	}
}
