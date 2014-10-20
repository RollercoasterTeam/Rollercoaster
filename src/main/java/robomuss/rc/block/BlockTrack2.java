package robomuss.rc.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import robomuss.rc.block.te.TileEntityTrack;
import robomuss.rc.block.te.TileEntityTrack2;
import robomuss.rc.entity.EntityTrainDefault;
import robomuss.rc.item.ItemExtra;
import robomuss.rc.item.ItemTrain;
import robomuss.rc.item.RCItems;
import robomuss.rc.track.TrackHandler;
import robomuss.rc.track.piece.TrackPiece;
import robomuss.rc.util.IPaintable;

public class BlockTrack2 extends BlockTrackBase {
	public TrackPiece track_type;
	public TileEntityTrack2 teTrack;

	public BlockTrack2(TrackPiece type) {
		super(type);
		setHardness(1F);
		setResistance(3F);

		this.track_type = type;
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess iba, int x, int y, int z) {
		AxisAlignedBB bounds = track_type.getBlockBounds(iba, x, y, z);
		setBlockBounds((float) bounds.minX, (float) bounds.minY, (float) bounds.minZ, (float) bounds.maxX, (float) bounds.maxY, (float) bounds.maxZ);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		TileEntityTrack2 teTrack = new TileEntityTrack2();
		teTrack.track_type = this.track_type;
		teTrack.track = this;
		return teTrack;
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if (!world.isRemote) {
			if (player.getHeldItem() != null) {
				if (player.getHeldItem().getItem() == RCItems.brush) {
					TileEntityTrack2 teTrack = (TileEntityTrack2) world.getTileEntity(x, y, z);
					teTrack.colour = player.getHeldItem().getItemDamage();
					world.markBlockForUpdate(x, y, z);
					return true;
				} else if (player.getHeldItem().getItem() instanceof ItemExtra) {
					TileEntityTrack2 teTrack = (TileEntityTrack2) world.getTileEntity(x, y, z);
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
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public int damageDropped(int dmg) {
		return dmg;
	}

//	@Override
//	public int getPaintMeta(World world, int x, int y, int z) {
//		return ((TileEntityTrack2) world.getTileEntity(x, y, z).colour);
//	}

	@Override
	public void onBlockAdded(World world, int x, int y, int z) {
		if (world.getTileEntity(x, y, z) != null && world.getTileEntity(x, y, z) instanceof TileEntityTrack2) {
			this.teTrack = (TileEntityTrack2) world.getTileEntity(x, y, z);
			teTrack.style = TrackHandler.findTrackStyle("corkscrew");

			if (teTrack.hasSlope(this)) {
				teTrack.placeTrackAsDummy(world, x, y, z, teTrack.getDirection(), this.track_type);
			}

			teTrack.updateRotation(world, x, y, z);
		}
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
		if (!world.isRemote) {
			if (teTrack != null && teTrack.extra != null && teTrack.extra == TrackHandler.extras.get(3)) {
				if (world.isBlockIndirectlyGettingPowered(x, y, z)) {
					EntityTrainDefault train = ItemTrain.spawnCart(world, x, y, z);
					world.spawnEntityInWorld(train);
				}
			}
		}
	}

	@Override
	public void onBlockHarvested(World world, int trackX, int trackY, int trackZ, int meta, EntityPlayer player) {
		if (world.getTileEntity(trackX, trackY, trackZ) instanceof TileEntityTrack2) {
			TileEntityTrack2 teTrack = (TileEntityTrack2) world.getTileEntity(trackX, trackY, trackZ);

			int dummyX = trackX + teTrack.getDirection().offsetX;
			int dummyY = trackY + teTrack.getDirection().offsetY;
			int dummyZ = trackZ + teTrack.getDirection().offsetZ;

			if (teTrack.hasSlope(this)) {
				if (!this.teTrack.getIsDummy()) {
					if (world.getBlock(dummyX, dummyY, dummyZ).equals(this.teTrack.dummyTrack)) {
						world.setBlockToAir(dummyX, dummyY, dummyZ);
						world.markBlockForUpdate(dummyX, dummyY, dummyZ);
						world.setBlockToAir(trackX, trackY, trackZ);
						world.markBlockForUpdate(trackX, trackY, trackZ);
					} else {
						world.setBlockToAir(trackX, trackY, trackZ);
						world.markBlockForUpdate(trackX, trackY, trackZ);
					}
				}
			}
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
