package robomuss.rc.block;

import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.BlockSnapshot;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.event.world.BlockEvent;
import org.lwjgl.opengl.GL11;
import robomuss.rc.RCMod;
import robomuss.rc.block.te.TileEntityTrackBase;
import robomuss.rc.chat.ChatHandler;
import robomuss.rc.client.gui.GuiHammerOverlay;
import robomuss.rc.entity.EntityTrainDefault;
import robomuss.rc.event.TrackPlaceEventHandler;
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

public class BlockTrackBase extends BlockContainer {
	public TrackPiece track_type;

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

//	@Override
//	public boolean onBlockEventReceived(World world, int x, int y, int z, int p_149696_5_, int p_149696_6_) {
//		return super.onBlockEventReceived(p_149696_1_, p_149696_2_, p_149696_3_, p_149696_4_, p_149696_5_, p_149696_6_);
//	}

//	@Override
//	public int onBlockPlaced(World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int meta) {
////		super.onBlockPlaced(world, x, y, z, side, hitX, hitY, hitZ, meta);
////		if (!world.isRemote) {
////			this.track_type.block = this;
////			this.position = new ChunkPosition(x, y, z);
////			style = TrackHandler.findTrackStyle("corkscrew");
//////			direction = TrackManager.getDirectionFromPlayerFacing(Minecraft.getMinecraft().thePlayer);
////			direction = ForgeDirection.getOrientation(meta);
////		}
//		this.style = TrackHandler.findTrackStyle("corkscrew");
////		this.position = new ChunkPosition(x, y, z);
////		this.direction = ForgeDirection.getOrientation(meta + 2);
//		meta = (MathHelper.floor_double((double)(Minecraft.getMinecraft().thePlayer.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3) + 2;
////		meta = TrackManager.getPlayerFacing(Minecraft.getMinecraft().thePlayer) + 2;
////		System.out.println(meta);
////		ChatHandler.broadcastChatMessageToPlayers("Track Meta: " + meta);
//
//		TileEntityTrackBase teTrack = TrackManager.getTrackTileAtCoords(x, y, z);
//
//		switch (meta) {
//			case 2: teTrack.direction = ForgeDirection.NORTH; break;
//			case 3: teTrack.direction = ForgeDirection.SOUTH; break;
//			case 4: teTrack.direction = ForgeDirection.WEST;  break;
//			case 5: teTrack.direction = ForgeDirection.EAST;  break;
//
////                case 2: entityTrackBase.direction = ForgeDirection.SOUTH; break;
////                case 3: entityTrackBase.direction = ForgeDirection.WEST;  break;
////                case 4: entityTrackBase.direction = ForgeDirection.NORTH; break;
////                case 5: entityTrackBase.direction = ForgeDirection.EAST;  break;
//		}
//
//		world.setBlockMetadataWithNotify(x, y, z, teTrack.direction.ordinal(), 2);
//
////        TileEntity entity = world.getTileEntity(x, y, z);
////        if(entity instanceof TileEntityTrackBase){
////            TileEntityTrackBase entityTrackBase = (TileEntityTrackBase) entity;
//////            System.out.println(meta);
////	        switch (meta) {
////	            case 2: entityTrackBase.direction = ForgeDirection.NORTH; break;
////	            case 3: entityTrackBase.direction = ForgeDirection.SOUTH; break;
////	            case 4: entityTrackBase.direction = ForgeDirection.WEST;  break;
////	            case 5: entityTrackBase.direction = ForgeDirection.EAST;  break;
////
//////                case 2: entityTrackBase.direction = ForgeDirection.SOUTH; break;
//////                case 3: entityTrackBase.direction = ForgeDirection.WEST;  break;
//////                case 4: entityTrackBase.direction = ForgeDirection.NORTH; break;
//////                case 5: entityTrackBase.direction = ForgeDirection.EAST;  break;
////            }
////
////	        world.setBlockMetadataWithNotify(x, y, z, entityTrackBase.direction.ordinal(), 2);
////        }
//
////		System.out.println(meta);
//		return meta;
////		return teTrack.direction.ordinal();
//		return meta;
//	}

	@Override
	public void onBlockAdded(World world, int x, int y, int z) {
//		int dir = world.getBlockMetadata(x, y, z);
//
//		switch (dir) {
//			case 0: System.out.println(String.format("onBlockAdded: dir: %d", dir)); break;
//			case 1: System.out.println(String.format("onBlockAdded: dir: %d", dir)); break;
//			case 2: System.out.println(String.format("onBlockAdded: dir: %d", dir)); break;
//			case 3: System.out.println(String.format("onBlockAdded: dir: %d", dir)); break;
//		}
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
}
