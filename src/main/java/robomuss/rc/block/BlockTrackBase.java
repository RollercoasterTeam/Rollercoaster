package robomuss.rc.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
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
import org.lwjgl.opengl.GL11;
import robomuss.rc.RCMod;
import robomuss.rc.block.te.TileEntityTrackBase;
import robomuss.rc.client.gui.GuiHammerOverlay;
import robomuss.rc.entity.EntityTrainDefault;
import robomuss.rc.item.ItemExtra;
import robomuss.rc.item.ItemTrain;
import robomuss.rc.item.RCItems;
import robomuss.rc.network.NetworkHandler;
import robomuss.rc.track.TrackHandler;
import robomuss.rc.track.TrackManager;
import robomuss.rc.track.extra.TrackExtra;
import robomuss.rc.track.piece.*;
import robomuss.rc.track.style.TrackStyle;
import robomuss.rc.util.IPaintable;

public class BlockTrackBase extends BlockContainer implements IPaintable {
	public TrackPiece track_type;
	public TrackStyle style;
	public TrackExtra extra;

	public int colour;
	public int meta;
	public boolean converted;
	public boolean dummy;
	public boolean isBig;
	public boolean canRotate = true;
	public ChunkPosition position;
//	public GuiHammerOverlay hammerOverlay;

	public BlockTrackBase(TrackPiece track_type) {
		super(Material.iron);
		setHardness(1F);
		setResistance(3F);

		this.track_type = track_type;
//		hammerOverlay = new GuiHammerOverlay(Minecraft.getMinecraft());
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess iba, int x, int y, int z) {
		AxisAlignedBB bounds = track_type.getBlockBounds(iba, x, y, z);
		setBlockBounds((float) bounds.minX, (float) bounds.minY, (float) bounds.minZ, (float) bounds.maxX, (float) bounds.maxY, (float) bounds.maxZ);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {

//		System.out.println("Track TE created.");
		return new TileEntityTrackBase(world, meta, this);
//		return new TileEntityTrackBase(new BlockTrackBase(this.track_type));
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
//		super.onBlockPlacedBy(world, x, y, z, player, item);
//		this.track_type.block = this;

		if (!world.isRemote) {
//			this.position = new ChunkPosition(x, y, z);
//			style = TrackHandler.findTrackStyle("corkscrew");
//			direction = TrackManager.getDirectionFromPlayerFacing(player) != null ? TrackManager.getDirectionFromPlayerFacing(player) : ForgeDirection.SOUTH;
//			world.setBlockMetadataWithNotify(x, y, z, TrackManager.getPlayerFacing(player) - 2, 2);
//			System.out.println(world.getBlockMetadata(x, y, z) + 2);
//			onBlockAdded(world, x, y, z);
		}
	}

	@Override
	public int onBlockPlaced(World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int meta) {
//		super.onBlockPlaced(world, x, y, z, side, hitX, hitY, hitZ, meta);
//		if (!world.isRemote) {
//			this.track_type.block = this;
//			this.position = new ChunkPosition(x, y, z);
//			style = TrackHandler.findTrackStyle("corkscrew");
////			direction = TrackManager.getDirectionFromPlayerFacing(Minecraft.getMinecraft().thePlayer);
//			direction = ForgeDirection.getOrientation(meta);
//		}
		this.style = TrackHandler.findTrackStyle("corkscrew");
		this.position = new ChunkPosition(x, y, z);
//		this.direction = ForgeDirection.getOrientation(meta + 2);
		meta = MathHelper.floor_double((double)(Minecraft.getMinecraft().thePlayer.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
        TileEntity entity = world.getTileEntity(x, y, z);
        if(entity instanceof TileEntityTrackBase){
            TileEntityTrackBase entityTrackBase = (TileEntityTrackBase) entity;
            switch (meta) {
                case 0: entityTrackBase.direction = ForgeDirection.SOUTH; break;
                case 1: entityTrackBase.direction = ForgeDirection.WEST;  break;
                case 2: entityTrackBase.direction = ForgeDirection.NORTH; break;
                case 3: entityTrackBase.direction = ForgeDirection.EAST;  break;
            }
        }

		System.out.println(meta);
		return meta;
	}

//	@Override
//	public void onBlockAdded(World world, int x, int y, int z) {
//		super.onBlockAdded(world, x, y, z);
//	}

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

	public boolean canRotate(World world, BlockTrackBase track, boolean rotateClockwise) {
		if (!world.isRemote) {

		}
		return false;
	}

	public void rotate(World world, BlockTrackBase track, boolean rotateClockwise) {
		if (canRotate(world, track, rotateClockwise)) {

		}
	}

	public void updateRotation(World world, int x, int y, int z) {
		if (!world.isRemote) {

		}
	}

	public void setDirection(ForgeDirection direction, TileEntityTrackBase tileEntityTrackBase) {
		tileEntityTrackBase.direction = direction != null ? direction : ForgeDirection.SOUTH;
	}

	public ForgeDirection getDirection(TileEntityTrackBase tileEntityTrackBase) {
		return tileEntityTrackBase.direction;
	}
}
