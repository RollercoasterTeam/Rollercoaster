package robomuss.rc.event;

import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraftforge.common.util.BlockSnapshot;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.WorldEvent;
import robomuss.rc.block.BlockTrackBase;
import robomuss.rc.block.te.TileEntityTrackBase;
import robomuss.rc.track.TrackManager;
import robomuss.rc.track.piece.TrackPiece;

public class TrackPlaceEventHandler {
//	@SubscribeEvent
//	public void trackPlacedMulti(BlockEvent.MultiPlaceEvent event) {
//		System.out.println("track placed");
//	}

	@SubscribeEvent
	public void trackPlaced(BlockEvent.MultiPlaceEvent event) {
		int facing = MathHelper.floor_double((double) (event.player.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
		int meta = facing == 0 ? 3 : facing == 1 ? 4 : facing == 2 ? 2 : facing == 3 ? 5 : 2;
//		event.setCanceled(false);
		if (!event.isCanceled()) {
//			if (event.blockMetadata == 2 && !event.world.isAirBlock(event.x, event.y, event.z - 1)) {
//				event.setCanceled(true);
//			} else if (event.blockMetadata == 3 && !event.world.isAirBlock(event.x, event.y, event.z + 1)) {
//				event.setCanceled(true);
//			} else if (event.blockMetadata == 4 && !event.world.isAirBlock(event.x - 1, event.y, event.z)) {
//				event.setCanceled(true);
//			} else if (event.blockMetadata == 5 && !event.world.isAirBlock(event.x + 1, event.y, event.z)) {
//				event.setCanceled(true);
//			}
//
			for (BlockSnapshot snapshot : event.getReplacedBlockSnapshots()) {
				if (!snapshot.getReplacedBlock().equals(Blocks.air)) {
//					System.out.println("canceled, not air");
					event.setCanceled(true);
					snapshot.restore();
				}

				if (snapshot.getTileEntity() instanceof TileEntityTrackBase) {
					if (((TileEntityTrackBase) snapshot.getTileEntity()).isDummy) {
//						System.out.println("replaced dummy");
						event.setCanceled(true);
						snapshot.restore();
					}
				}
			}

//			if (meta == 2 && !event.world.isAirBlock(event.x, event.y, event.z - 1)) {
////				event.setCanceled(true);
//			} else if (meta == 3 && !event.world.isAirBlock(event.x, event.y, event.z + 1)) {
////				event.setCanceled(true);
//			} else if (meta == 4 && !event.world.isAirBlock(event.x - 1, event.y, event.z)) {
////				event.setCanceled(true);
//			} else if (meta == 5 && !event.world.isAirBlock(event.x + 1, event.y, event.z)) {
////				event.setCanceled(true);
//			}
		}

//		if (event.isCanceled()) {
//			System.out.println("canceled");
////			event.blockSnapshot.restore();
////			for (BlockSnapshot snapshot : event.getReplacedBlockSnapshots()) {
////				System.out.println(snapshot.getReplacedBlock().getUnlocalizedName());
////			}
//		}
	}

	@SubscribeEvent
	public void trackDestroyed(BlockEvent.BreakEvent event) {
		if (event.world.getTileEntity(event.x, event.y, event.z) instanceof TileEntityTrackBase) {
			TileEntityTrackBase teTrack = (TileEntityTrackBase) event.world.getTileEntity(event.x, event.y, event.z);
			if (TrackManager.isSloped(TrackManager.getTrackType(teTrack.track))) {
				if (teTrack.getWorldObj().getBlockMetadata(teTrack.xCoord, teTrack.yCoord, teTrack.zCoord) > 11) {
//			if (teTrack.isDummy) {
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
//		event.world.setBlockToAir(event.x, event.y, event.z);
	}
}
