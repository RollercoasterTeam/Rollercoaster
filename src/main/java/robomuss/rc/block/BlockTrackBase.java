package robomuss.rc.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import robomuss.rc.RCMod;
import robomuss.rc.block.te.TileEntityTrackBase;
import robomuss.rc.entity.EntityTrainDefault;
import robomuss.rc.item.ItemExtra;
import robomuss.rc.item.ItemTrain;
import robomuss.rc.item.RCItems;
import robomuss.rc.track.TrackHandler;
import robomuss.rc.track.extra.TrackExtra;
import robomuss.rc.track.piece.*;
import robomuss.rc.track.style.TrackStyle;
import robomuss.rc.util.IPaintable;

public class BlockTrackBase extends BlockContainer implements IPaintable, IRotatable {
	public TrackPiece track_type;
	public TrackStyle style;
	public TrackExtra extra;
	public ForgeDirection direction;
	public int colour;
	public boolean converted;
	public boolean dummy;
	public boolean isBig;
	public ChunkPosition position;

	public BlockTrackBase(TrackPiece track_type) {
		super(Material.iron);
		setHardness(1F);
		setResistance(3F);

		this.track_type = track_type;
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess iba, int x, int y, int z) {
		AxisAlignedBB bounds = track_type.getBlockBounds(iba, x, y, z);
		setBlockBounds((float) bounds.minX, (float) bounds.minY, (float) bounds.minZ, (float) bounds.maxX, (float) bounds.maxY, (float) bounds.maxZ);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
//		System.out.println("Track TE created.");
		return new TileEntityTrackBase(this);
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if (!world.isRemote) {
			if (player.getHeldItem() != null) {
				if (player.getHeldItem().getItem() == RCItems.brush) {
					colour = player.getHeldItem().getItemDamage();
					world.markBlockForUpdate(x, y, z);
					return true;
				} else if (player.getHeldItem().getItem() instanceof ItemExtra) {
					extra = TrackHandler.extras.get(((ItemExtra) player.getHeldItem().getItem()).id);
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
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public int damageDropped(int dmg) {
		return dmg;
	}

	@Override
	public int getPaintMeta(World world, int x, int y, int z) {
		return colour;
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack item) {
		if (!world.isRemote) {
			this.position = new ChunkPosition(x, y, z);
			style = TrackHandler.findTrackStyle("corkscrew");
			switch (MathHelper.floor_double((player.rotationYaw * 4.0f / 360.0f) * 0.5D) & 3) {
				case 0:	 direction = ForgeDirection.SOUTH; break;
				case 1:	 direction = ForgeDirection.WEST;  break;
				case 2:  direction = ForgeDirection.NORTH; break;
				case 3:  direction = ForgeDirection.EAST;  break;
				default: direction = ForgeDirection.SOUTH;
			}
			onBlockAdded(world, x, y, z);
		}
	}

	@Override
	public void onBlockAdded(World world, int x, int y, int z) {
		if (!world.isRemote) {
			switch (RCMod.trackManager.getTrackType(this)) {
				case 2:
					//place track as dummy
					break;
				case 3:
					//place track as dummy
					break;
				case 4:
					//place track as dummy
					break;
				default:
					//other things
					break;
			}
			updateRotation(world, x, y, z);
		}
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
		if (!world.isRemote) {
			if (extra != null && extra == TrackHandler.extras.get(3)) {
				if (world.isBlockIndirectlyGettingPowered(x, y, z)) {
					EntityTrainDefault entity = ItemTrain.spawnCart(world, x, y, z);
					world.spawnEntityInWorld(entity);
				}
			}
		}
	}

	@Override
	public void onBlockHarvested(World world, int trackX, int trackY, int trackZ, int meta, EntityPlayer player) {
		if (!world.isRemote) {
			switch (RCMod.trackManager.getTrackType(this)) {
				case 2:
					//break dummy
					break;
				case 3:
					//break dummy
					break;
				case 4:
					//break dummy
					break;
				default:
					//break dummy
					break;
			}
			world.setBlockToAir(trackX, trackY, trackZ);
			world.markBlockForUpdate(trackX, trackY, trackZ);
		}
	}

	/**
	 * isBig is true if the track has at least 1 dummy track, ie. slopes
	 * @param isBig
	 */
	public void setBig(boolean isBig) {
		this.isBig = isBig;
	}

	/**
	 * Returns true if the track has at least 1 dummy track, ie. slopes
	 * @return
	 */
	public boolean getBig() {
		return isBig;
	}

	public boolean canRotate(World world, BlockTrackBase track) {
		if (!world.isRemote) {

		}
		return false;
	}

	public void rotate(World world, BlockTrackBase track) {
		if (canRotate(world, track)) {

		}
	}

	public void updateRotation(World world, int x, int y, int z) {
		if (!world.isRemote) {

		}
	}

	@Override
	public boolean canRotate(World world, BlockTrack2 track) {
		return false;
	}

	@Override
	public void setDirection(ForgeDirection direction) {

	}

	@Override
	public ForgeDirection getDirection() {
		return null;
	}
}
