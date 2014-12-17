package robomuss.rc.event;

import net.minecraft.block.BlockLiquid;
import net.minecraft.init.Blocks;
import net.minecraftforge.common.util.BlockSnapshot;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import robomuss.rc.RCMod;
import robomuss.rc.block.BlockFooter;
import robomuss.rc.block.BlockSupport;
import robomuss.rc.block.BlockTrackBase;
import robomuss.rc.block.te.TileEntityFooter;
import robomuss.rc.block.te.TileEntitySupport;
import robomuss.rc.block.te.TileEntityTrackBase;
import robomuss.rc.track.TrackManager;

public class BlockPlacedEvent {
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

			if (TrackManager.isSloped(TrackManager.getTrackType(teTrack.track))) {
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
		}
	}
}
