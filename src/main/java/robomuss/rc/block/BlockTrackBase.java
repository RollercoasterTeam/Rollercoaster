package robomuss.rc.block;

<<<<<<< HEAD
import net.minecraft.block.properties.IProperty;
=======
<<<<<<< HEAD
import java.util.List;

=======

>>>>>>> origin/One8PortTake2
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
>>>>>>> FETCH_HEAD
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.BlockSnapshot;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
<<<<<<< HEAD
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
=======
>>>>>>> FETCH_HEAD
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

public class BlockTrackBase extends BlockContainer implements IPaintable {
	public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
	public static final PropertyBool DUMMY = PropertyBool.create("dummy");

	public TrackPiece track_type;

	public BlockTrackBase(TrackPiece track_type) {
		super(Material.iron);
		setHardness(1f);
		setResistance(3f);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(DUMMY, false));
//		setLightOpacity(128);   //TODO: experiment with the results of this
		this.track_type = track_type;
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		boolean isDummy = meta >= 11;
		EnumFacing facing = EnumFacing.getFront(isDummy ? meta - 10 : meta);

		if (facing.getAxis() == EnumFacing.Axis.Y) {
			facing = EnumFacing.NORTH;
		}

		return this.getDefaultState().withProperty(FACING, facing).withProperty(DUMMY, isDummy);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		EnumFacing facing = (EnumFacing) state.getValue(FACING);
		boolean isDummy = (Boolean) state.getValue(DUMMY);

		return facing.getIndex() + (isDummy ? 10 : 0);
	}

	@Override
	protected BlockState createBlockState() {
		return new BlockState(this, new IProperty[] {FACING, DUMMY});
	}

	private void setDefaultFacing(World world, BlockPos pos, IBlockState state) {
		if (!world.isRemote) {
			Block blockNorth = world.getBlockState(pos.north()).getBlock();
			Block blockSouth = world.getBlockState(pos.south()).getBlock();
			Block blockWest = world.getBlockState(pos.west()).getBlock();
			Block blockEast = world.getBlockState(pos.east()).getBlock();
			EnumFacing facing = (EnumFacing) state.getValue(FACING);

			if (facing == EnumFacing.NORTH && !(blockNorth instanceof BlockTrackBase) && (blockSouth instanceof BlockTrackBase)) {
				facing = EnumFacing.SOUTH;
			} else if (facing == EnumFacing.SOUTH && !(blockSouth instanceof BlockTrackBase) && (blockNorth instanceof BlockTrackBase)) {
				facing = EnumFacing.NORTH;
			} else if (facing == EnumFacing.WEST && !(blockWest instanceof BlockTrackBase) && (blockEast instanceof BlockTrackBase)) {
				facing = EnumFacing.EAST;
			} else if (facing == EnumFacing.EAST && !(blockEast instanceof BlockTrackBase) && (blockWest instanceof BlockTrackBase)) {
				facing = EnumFacing.WEST;
			}

			world.setBlockState(pos, state.withProperty(FACING, facing), 2);
		}
	}

	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, BlockPos pos) {
		return new ItemStack(GameRegistry.findItem(RCMod.MODID, this.track_type.unlocalized_name + "_track"));
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess iba, BlockPos pos) {
		AxisAlignedBB bounds = track_type.getBlockBounds(iba, pos);
		this.setBlockBounds((float) bounds.minX, (float) bounds.minY, (float) bounds.minZ, (float) bounds.maxX, (float) bounds.maxY, (float) bounds.maxZ);
	}

	/**
	 * Returns the bounding box of the wired rectangular prism to render.
	 */
	@Override
	@SideOnly(Side.CLIENT)
	public AxisAlignedBB getSelectedBoundingBox(World world, BlockPos pos) {
		return this.getCollisionBoundingBox(world, pos, world.getBlockState(pos));
	}

	/**
	 * Adds all intersecting collision boxes to a list. (Be sure to only add boxes to the list if they intersect the
	 * mask.) Parameters: World, X, Y, Z, mask, list, colliding entity
	 */
	@Override
	public void addCollisionBoxesToList(World world, BlockPos pos, IBlockState state, AxisAlignedBB mask, List list, Entity entity) {
		if (TrackManager.isSloped(TrackManager.getTrackType(this))) {
			setSlopedBounds(world, pos, state, mask, list, entity);
		} else {
			setTrackBounds(world, pos, state, mask, list, entity);
		}
	}

	public void setSlopedBounds(World world, BlockPos pos, IBlockState state, AxisAlignedBB mask, List list, Entity entity) {
		if (this.track_type == TrackHandler.findTrackType("slope_up")) {
			if (!((TileEntityTrackBase) world.getTileEntity(pos)).isDummy) {
				this.setBlockBounds(0f, 0f, 0f, 1f, 0.4f, 1f);
				super.addCollisionBoxesToList(world, pos, state, mask, list, entity);
			} else {
				setDummyBounds(world, pos, state, mask, list, entity);
			}
		} else if (this.track_type == TrackHandler.findTrackType("slope")) {
			if (((TileEntityTrackBase) world.getTileEntity(pos)).isDummy) {
				setDummyBounds(world, pos, state, mask, list, entity);
			}
		} else if (this.track_type == TrackHandler.findTrackType("slope_down")) {
			if (((TileEntityTrackBase) world.getTileEntity(pos)).isDummy) {
				setDummyBounds(world, pos, state, mask, list, entity);
				//TODO: figure out how the dummies for slope_down will work...
			}
		}
	}

	public void setDummyBounds(World world, BlockPos pos, IBlockState state, AxisAlignedBB mask, List list, Entity entity) {
		IBlockState dummyState = world.getBlockState(pos);
//		int meta = world.getBlockMetadata(x, y, z);
//		meta = meta > 11 ? meta - 10 : meta;                //meta data for dummy tracks is bound to 12-15
//
//		if (meta == 2) {
//			this.setBlockBounds(0f, 0f, 0f, 1f, 0.5f, 1f);
//			super.addCollisionBoxesToList(world, x, y, z, mask, list, entity);
//			this.setBlockBounds(0f, 0.5f, 0f, 1f, 1f, 0.5f);
//			super.addCollisionBoxesToList(world, x, y, z, mask, list, entity);
//		} else if (meta == 3) {
//			this.setBlockBounds(0f, 0f, 0f, 1f, 0.5f, 1f);
//			super.addCollisionBoxesToList(world, x, y, z, mask, list, entity);
//			this.setBlockBounds(0f, 0.5f, 0.5f, 1f, 1f, 1f);
//			super.addCollisionBoxesToList(world, x, y, z, mask, list, entity);
//		} else if (meta == 4) {
//			this.setBlockBounds(0f, 0f, 0f, 1f, 0.5f, 1f);
//			super.addCollisionBoxesToList(world, x, y, z, mask, list, entity);
//			this.setBlockBounds(0f, 0.5f, 0f, 0.5f, 1f, 1f);
//			super.addCollisionBoxesToList(world, x, y, z, mask, list, entity);
//		} else if (meta == 5) {
//			this.setBlockBounds(0f, 0f, 0f, 1f, 0.5f, 1f);
//			super.addCollisionBoxesToList(world, x, y, z, mask, list, entity);
//			this.setBlockBounds(0.5f, 0.5f, 0f, 1f, 1f, 1f);
//			super.addCollisionBoxesToList(world, x, y, z, mask, list, entity);
//		}
	}

	public void setTrackBounds(World world, BlockPos pos, IBlockState state, AxisAlignedBB mask, List list, Entity entity) {
		AxisAlignedBB bounds = track_type.getBlockBounds(world, pos);
		this.setBlockBounds((float) bounds.minX, (float) bounds.minY, (float) bounds.minZ, (float) bounds.maxX, (float) bounds.maxY, (float) bounds.maxZ);
		super.addCollisionBoxesToList(world, pos, state, mask, list, entity);
	}

	public void setBottomBounds(IBlockAccess iba, BlockPos pos) {
		if (TrackManager.isSloped(TrackManager.getTrackType(this))) {
			this.setBlockBounds(0, 0, 0, 1, 0.5f, 1);    //bottom-half
		}
	}

	public void setTopBounds(IBlockAccess iba, BlockPos pos, IBlockState state, AxisAlignedBB mask, List list, Entity entity) {
//		int meta = iba.getBlockMetadata(x, y, z);
		EnumFacing facing = (EnumFacing) state.getValue(FACING);

		/* minY == 0 is bottom of block, maxY == 1 is top of block */
		float mnY = 0.5f;   //half-Y
		float mxY = 1.0f;   //top-Y
		float mnX = 0.0f;   //west-X (?)
		float mxX = 1.0f;   //east-X (?)
		float mnZ = 0.0f;   //south-Z
		float mxZ = 0.5f;   //half-Z

		if (this.track_type == TrackHandler.findTrackType("slope_up")) {
			if (!((TileEntityTrackBase) iba.getTileEntity(pos)).isDummy) {
				return;
			} else {
				switch (facing) {
					case NORTH: this.setBlockBounds(0, 0.5f, 0, 1, 1, 0.5f); break;
					case SOUTH: this.setBlockBounds(0, 0.5f, 0.5f, 1, 1, 1); break;
					case WEST:  this.setBlockBounds(0, 0.5f, 0, 0.5f, 1, 1); break;
					case EAST:  this.setBlockBounds(0.5f, 0.5f, 0, 1, 1, 1); break;
				}
			}
		} else if (this.track_type == TrackHandler.findTrackType("slope")) {
			if (!((TileEntityTrackBase) iba.getTileEntity(pos)).isDummy) {
				return;
			} else {
				switch (facing) {
					case NORTH: this.setBlockBounds(0, 0.5f, 0, 1, 1, 0.5f); break;
					case SOUTH: this.setBlockBounds(0, 0.5f, 0.5f, 1, 1, 1); break;
					case WEST:  this.setBlockBounds(0, 0.5f, 0, 0.5f, 1, 1); break;
					case EAST:  this.setBlockBounds(0.5f, 0.5f, 0, 1, 1, 1); break;
				}
			}
		} else if (this.track_type == TrackHandler.findTrackType("slope_down")) {
			if (!((TileEntityTrackBase) iba.getTileEntity(pos)).isDummy) {
				return;
			} else {
				switch (facing) {
					case NORTH: this.setBlockBounds(0, 0.5f, 0, 1, 1, 0.5f); break;
					case SOUTH: this.setBlockBounds(0, 0.5f, 0.5f, 1, 1, 1); break;
					case WEST:  this.setBlockBounds(0, 0.5f, 0, 0.5f, 1, 1); break;
					case EAST:  this.setBlockBounds(0.5f, 0.5f, 0, 1, 1, 1); break;
				}
			}
		}
	}

	//TODO: revisit "isDummy" stuff, now that meta data is more obscure
	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		TileEntityTrackBase teTrack = new TileEntityTrackBase(world, meta, this);
//		this.track_type.addTileEntityToList(this.track_type, teTrack);

		if (meta >= 11) {
			teTrack.isDummy = true;
		}

		return teTrack;
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ) {
		if (player.getHeldItem() != null) {
			Item heldItem = player.getHeldItem().getItem();

			if (heldItem == RCItems.brush || heldItem == Items.water_bucket || heldItem instanceof ItemExtra) {
				return true;
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
	public int damageDropped(IBlockState state) {
		return 0;
	}

	@Override
	public boolean isBlockSolid(IBlockAccess world, BlockPos pos, EnumFacing side) {
		return false;
	}

	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase player, ItemStack item) {
		EnumFacing facing = player.getHorizontalFacing();

		BlockEvent.PlaceEvent event = new BlockEvent.PlaceEvent(BlockSnapshot.getBlockSnapshot(world, pos), world.getBlockState(pos), (EntityPlayer) player);
		MinecraftForge.EVENT_BUS.post(event);

		world.setBlockState(pos, state.withProperty(FACING, facing));

		if (TrackManager.isSloped(TrackManager.getTrackType(this))) {
			((TileEntityTrackBase) world.getTileEntity(pos)).placeDummy();
		}
	}

	public boolean canPlaceDummyAt(World world, BlockPos pos) {
		return world.isAirBlock(pos);
	}

	@Override
	public void onBlockAdded(World world, BlockPos pos, IBlockState state) {}

	@Override
	@SideOnly(Side.CLIENT)
	public IBlockState getStateForEntityRender(IBlockState state) {
		return this.getDefaultState().withProperty(FACING, EnumFacing.SOUTH);
	}

	@Override
	public void onNeighborBlockChange(World world, BlockPos pos, IBlockState state, Block neighborBlock) {
		if (!world.isRemote) {
			TileEntityTrackBase teTrack = (TileEntityTrackBase) world.getTileEntity(pos);

			if (teTrack != null) {
				if (teTrack.extra != null && teTrack.extra == TrackHandler.extras.get(3)) {
					if (world.isBlockIndirectlyGettingPowered(pos) >= 7) {
						EntityTrainDefault entity = ItemTrain.spawnCart(world, pos);
						world.spawnEntityInWorld(entity);
					}
				}
			}
		}
	}

	@Override
	public void onBlockHarvested(World world, BlockPos pos, IBlockState state, EntityPlayer player) {
		if (!world.isRemote) {
			world.setBlockToAir(pos);
			world.markBlockForUpdate(pos);
		}
	}

	@Override
	public int getPaintMeta(World world, BlockPos pos) {
		return ((TileEntityTrackBase) world.getTileEntity(pos)).colour;
	}
}
