package robomuss.rc.event;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.ChunkPosition;
import net.minecraftforge.common.util.BlockSnapshot;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.event.world.BlockEvent;
import robomuss.rc.RCMod;
import robomuss.rc.block.*;
import robomuss.rc.block.te.TileEntityFooter;
import robomuss.rc.block.te.TileEntitySupport;
import robomuss.rc.block.te.TileEntityTrackBase;
import robomuss.rc.block.te.TileEntityTrackFabricator;
import robomuss.rc.multiblock.MultiBlockManager;
import robomuss.rc.multiblock.MultiBlockStructure;
import robomuss.rc.multiblock.MultiBlockTrackFabricator;
import robomuss.rc.track.TrackHandler;
import robomuss.rc.track.TrackManager;

public class BlockPlacedEvent {
	@SubscribeEvent
	public void blockPlaced(BlockEvent.PlaceEvent event) {
		if (event.placedBlock instanceof BlockTrackFabricator) {
			if (event.player.capabilities.isCreativeMode) {                             //TODO: allow this in survival if the player has enough blocks to build the structure?
				int l = MathHelper.floor_double((double) (event.player.rotationYaw * 4.0f / 360.0f) + 0.5d) & 3;

				ForgeDirection forward;

				switch (l) {
					case 0:  forward = ForgeDirection.SOUTH; break;
					case 1:  forward = ForgeDirection.WEST;  break;
					case 2:  forward = ForgeDirection.NORTH; break;
					case 3:  forward = ForgeDirection.EAST;  break;
					default: forward = ForgeDirection.SOUTH; break;
				}

				MultiBlockTrackFabricator struct = (MultiBlockTrackFabricator) MultiBlockManager.getInstance().getStructure(event.placedBlock.getUnlocalizedName());

				TileEntityTrackFabricator teFab = (TileEntityTrackFabricator) event.world.getTileEntity(event.x, event.y, event.z);
				struct.placeTemplateBlocks(0, event.world, event.x, event.y, event.z, teFab.direction, struct);
			}
		}
	}

	@SubscribeEvent
	public void blockPlaced(BlockEvent.MultiPlaceEvent event) {
		if (event.placedBlock instanceof BlockTrackBase) {
			if (!event.isCanceled()) {
				for (BlockSnapshot snapshot : event.getReplacedBlockSnapshots()) {
					if (!snapshot.getReplacedBlock().equals(Blocks.air) && !(snapshot.getReplacedBlock() instanceof BlockLiquid)) {
						event.setCanceled(true);
						snapshot.restore();
					}

					if (snapshot.getTileEntity() instanceof TileEntityTrackBase) {
						if (((TileEntityTrackBase) snapshot.getTileEntity()).isDummy) {
							event.setCanceled(true);
							snapshot.restore();
						}
					}
				}
			}
		} else if (event.placedBlock instanceof BlockSupport) {
			if (!(event.placedAgainst instanceof BlockFooter) && !(event.placedAgainst instanceof BlockSupport)) {
				for (BlockSnapshot snapshot : event.getReplacedBlockSnapshots()) {
					event.setCanceled(true);
					snapshot.restore();
				}
			}
		}
	}

	@SubscribeEvent
	public void blockDestroyed(BlockEvent.BreakEvent event) {
		if (event.world.getTileEntity(event.x, event.y, event.z) instanceof TileEntityTrackBase) {
			TileEntityTrackBase teTrack = (TileEntityTrackBase) event.world.getTileEntity(event.x, event.y, event.z);
			int meta = event.world.getBlockMetadata(teTrack.xCoord, teTrack.yCoord, teTrack.zCoord);

			if (TrackManager.isSloped(((BlockTrackBase) event.block).track_type)) {
				if (meta > 11) {
					switch (meta - 10) {
						case 2: event.world.setBlockToAir(event.x, event.y, event.z + 1); break;
						case 3: event.world.setBlockToAir(event.x, event.y, event.z - 1); break;
						case 4: event.world.setBlockToAir(event.x + 1, event.y, event.z); break;
						case 5: event.world.setBlockToAir(event.x - 1, event.y, event.z); break;
					}
				} else {
					switch (meta) {
						case 2: event.world.setBlockToAir(event.x, event.y, event.z - 1); break;
						case 3: event.world.setBlockToAir(event.x, event.y, event.z + 1); break;
						case 4: event.world.setBlockToAir(event.x - 1, event.y, event.z); break;
						case 5: event.world.setBlockToAir(event.x + 1, event.y, event.z); break;
					}
				}
			}

			event.world.setBlockToAir(event.x, event.y, event.z);
		} else if (event.world.getTileEntity(event.x, event.y, event.z) instanceof TileEntitySupport) {
			TileEntitySupport teSupport = (TileEntitySupport) event.world.getTileEntity(event.x, event.y, event.z);

			if (!(event.world.getTileEntity(event.x, event.y - 1, event.z) instanceof TileEntityFooter)) {
				if (RCMod.supportManager.getSupportIndex(teSupport) != -1) {
					RCMod.supportManager.removeSupportFromStack(RCMod.supportManager.getFooterFromSupport(teSupport), RCMod.supportManager.getSupportIndex(teSupport));
				}
			} else {
				RCMod.supportManager.removeSupportFromStack((TileEntityFooter) event.world.getTileEntity(event.x, event.y - 1, event.z), -1); //-1 tells the SupportManager to break the entire stack
			}
		} else if (event.world.getTileEntity(event.x, event.y, event.z) instanceof TileEntityFooter) {
			RCMod.supportManager.breakFooter(event.world, event.x, event.y, event.z);
		} else if (event.world.getTileEntity(event.x, event.y, event.z) instanceof TileEntityTrackFabricator) {
			TileEntityTrackFabricator teFab = (TileEntityTrackFabricator) event.world.getTileEntity(event.x, event.y, event.z);
			MultiBlockTrackFabricator struct = (MultiBlockTrackFabricator) MultiBlockManager.getInstance().getStructure(event.block.getUnlocalizedName());

			if (struct.isStructureFormed(0, teFab)) {
				struct.breakStructure(0, event.world, event.x, event.y, event.z, teFab.direction, struct);
			}
		}
	}
}
