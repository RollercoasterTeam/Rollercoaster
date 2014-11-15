package robomuss.rc.event;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraftforge.common.util.BlockSnapshot;
import net.minecraftforge.event.world.BlockEvent;
import robomuss.rc.block.BlockTrackBase;
import robomuss.rc.block.te.TileEntityTrackBase;
import robomuss.rc.track.TrackManager;

public class TrackPlaceEventHandler {
//	@SubscribeEvent
//	public void trackPlaced(BlockEvent.PlaceEvent event) {
//		if (event.placedAgainst instanceof BlockTrackFabricator) {
//			if (event.itemInHand != null) {
//				event.setCanceled(true);
//				event.blockSnapshot.restore();
//			}
//		}
//	}

	@SubscribeEvent
	public void trackPlaced(BlockEvent.MultiPlaceEvent event) {
		if (event.placedBlock instanceof BlockTrackBase) {
			if (!event.isCanceled()) {
				for (BlockSnapshot snapshot : event.getReplacedBlockSnapshots()) {
					if (!snapshot.getReplacedBlock().equals(Blocks.air)) {
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
		}
	}

	@SubscribeEvent
	public void trackDestroyed(BlockEvent.BreakEvent event) {
		if (event.world.getTileEntity(event.x, event.y, event.z) instanceof TileEntityTrackBase) {
			TileEntityTrackBase teTrack = (TileEntityTrackBase) event.world.getTileEntity(event.x, event.y, event.z);
			if (TrackManager.isSloped(TrackManager.getTrackType(teTrack.track))) {
				if (teTrack.getWorldObj().getBlockMetadata(teTrack.xCoord, teTrack.yCoord, teTrack.zCoord) > 11) {
					switch (event.blockMetadata - 10) {
						case 2: event.world.setBlockToAir(event.x, event.y, event.z + 1); break;
						case 3: event.world.setBlockToAir(event.x, event.y, event.z - 1); break;
						case 4: event.world.setBlockToAir(event.x + 1, event.y, event.z); break;
						case 5: event.world.setBlockToAir(event.x - 1, event.y, event.z); break;
					}
				} else {
					switch (event.blockMetadata) {
						case 2: event.world.setBlockToAir(event.x, event.y, event.z - 1); break;
						case 3: event.world.setBlockToAir(event.x, event.y, event.z + 1); break;
						case 4: event.world.setBlockToAir(event.x - 1, event.y, event.z); break;
						case 5: event.world.setBlockToAir(event.x + 1, event.y, event.z); break;
					}
				}
			}

			event.world.setBlockToAir(event.x, event.y, event.z);
		}
	}
}
