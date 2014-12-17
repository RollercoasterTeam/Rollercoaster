package robomuss.rc.event;

<<<<<<< HEAD
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
=======
>>>>>>> origin/One8PortTake2
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
		if (event.world.getTileEntity(event.pos) instanceof TileEntityTrackBase) {
			TileEntityTrackBase teTrack = (TileEntityTrackBase) event.world.getTileEntity(event.pos);
			EnumFacing facing = (EnumFacing) event.world.getBlockState(teTrack.getPos()).getValue(BlockTrackBase.FACING);
			boolean isDummy = (Boolean) event.world.getBlockState(teTrack.getPos()).getValue(BlockTrackBase.DUMMY);

			if (TrackManager.isSloped(TrackManager.getTrackType(teTrack.track))) {
				if (isDummy) {
					switch (facing) {
						case NORTH: event.world.setBlockToAir(event.pos.south()); break;
						case SOUTH: event.world.setBlockToAir(event.pos.north()); break;
						case WEST:  event.world.setBlockToAir(event.pos.east());  break;
						case EAST:  event.world.setBlockToAir(event.pos.west());  break;
					}
				} else {
					switch (facing) {
						case NORTH: event.world.setBlockToAir(event.pos.north()); break;
						case SOUTH: event.world.setBlockToAir(event.pos.south()); break;
						case WEST:  event.world.setBlockToAir(event.pos.west());  break;
						case EAST:  event.world.setBlockToAir(event.pos.east());  break;
					}
				}
			}

			event.world.setBlockToAir(event.pos);
		} else if (event.world.getTileEntity(event.pos) instanceof TileEntitySupport) {
			TileEntitySupport teSupport = (TileEntitySupport) event.world.getTileEntity(event.pos);

			if (!(event.world.getTileEntity(event.pos.down()) instanceof TileEntityFooter)) {
				if (RCMod.supportManager.getSupportIndex(teSupport) != -1) {
					RCMod.supportManager.removeSupportFromStack(RCMod.supportManager.getFooterFromSupport(teSupport), RCMod.supportManager.getSupportIndex(teSupport));
				}
			} else {
				RCMod.supportManager.removeSupportFromStack((TileEntityFooter) event.world.getTileEntity(event.pos.down()), -1); //-1 tells the SupportManager to break the entire stack
			}
		} else if (event.world.getTileEntity(event.pos) instanceof TileEntityFooter) {
			RCMod.supportManager.breakFooter(event.world, event.pos);
		}
	}
}
