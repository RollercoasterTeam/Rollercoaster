package robomuss.rc.multiblock;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import robomuss.rc.RCMod;
import robomuss.rc.block.BlockTrackBase;
import robomuss.rc.block.RCBlocks;
import robomuss.rc.block.te.TileEntityTrackBase;
import robomuss.rc.track.TrackHandler;
import robomuss.rc.track.TrackManager;
import robomuss.rc.track.piece.TrackPiece;
import robomuss.rc.track.piece.TrackPieceHorizontal;

import java.util.ArrayList;
import java.util.List;

public class MultiBlockTrackBase extends MultiBlockStructure {
	public static MultiBlockTrackBase horizontalStruct = new MultiBlockTrackBase();
	public static MultiBlockTrackBase cornerStruct = new MultiBlockTrackBase();
	public static MultiBlockTrackBase slopeUpStruct = new MultiBlockTrackBase();
	public static MultiBlockTrackBase slopeStruct = new MultiBlockTrackBase();
	public static MultiBlockTrackBase slopeDownStruct = new MultiBlockTrackBase();
	public static MultiBlockTrackBase loopStruct = new MultiBlockTrackBase();
	public static MultiBlockTrackBase heartLineRollStruct = new MultiBlockTrackBase();

	private int numDummies;

	/* Note: double ** denotes that the next character in the string should be considered a dummy, the parent will never be a dummy */
	/* Temporarily disabled for future review... */
	@Override
	public void registerStructure() {
//		structure.addLayer(TrackHandler.Types.HORIZONTAL.type.block, "T", 'T', TrackHandler.Types.HORIZONTAL);
//		structure.addLayer(TrackHandler.Types.CORNER.type.block, "C", 'C', TrackHandler.Types.CORNER.type.block);
//		structure.addLayer(TrackHandler.Types.SLOPE_UP.type.block, "**DT", 'D', TrackHandler.Types.SLOPE_UP.type.block, 'T', TrackHandler.Types.SLOPE_UP.type.block);
//		structure.addLayer(TrackHandler.Types.SLOPE.type.block, "**DT", 'D', TrackHandler.Types.SLOPE.type.block, 'T', TrackHandler.Types.SLOPE.type.block);
//		structure.addLayer(null, "**DD", 'D', TrackHandler.Types.SLOPE_DOWN.type.block);
//		structure.addLayer(TrackHandler.Types.SLOPE_DOWN.type.block, "**DT", 'D', TrackHandler.Types.SLOPE_DOWN.type.block, 'T', TrackHandler.Types.SLOPE_DOWN.type.block);
//		structure.addLayer(TrackHandler.Types.LOOP.type.block, "T", 'T', TrackHandler.Types.LOOP.type.block);
//		structure.addLayer(TrackHandler.Types.HEART_LINE_ROLL.type.block, "T", 'T', TrackHandler.Types.HEART_LINE_ROLL.type.block);
//
//		structure.setRotationAxis(false, true, false);
//
//		structure.setStructureName("tracks");
//		RCMod.multiBlockManager.addStructure(structure);

//		horizontalStruct.addLayer(TrackHandler.findTrackType("horizontal").block, "T", 'T', TrackHandler.findTrackType("horizontal").block);
//		cornerStruct.addLayer(TrackHandler.findTrackType("curve").block, "C", 'C', TrackHandler.findTrackType("curve").block);
//		slopeUpStruct.addLayer(TrackHandler.findTrackType("slope_up").block, "**DT", 'D', TrackHandler.findTrackType("slope_up").block, 'T', TrackHandler.findTrackType("slope_up").block);
//		slopeStruct.addLayer(TrackHandler.findTrackType("slope").block, "**DT", 'D', TrackHandler.findTrackType("slope").block, 'T', TrackHandler.findTrackType("slope").block);
//		slopeDownStruct.addLayer(TrackHandler.findTrackType("slope_down").block, "**DD", 'D', TrackHandler.findTrackType("slope_down").block);
//		slopeDownStruct.addLayer(null, "**DT", 'D', TrackHandler.findTrackType("slope_down").block, 'T', TrackHandler.findTrackType("slope_down").block);
//		loopStruct.addLayer(TrackHandler.findTrackType("loop").block, "T", 'T', TrackHandler.findTrackType("loop").block);
//		heartLineStruct.addLayer(TrackHandler.findTrackType("heartline_roll").block, "T", 'T', TrackHandler.findTrackType("heartline_roll").block);

//		structures.add(TrackHandler.getTrackTypeID(TrackHandler.findTrackType("horizontal")), horizontalStruct);
//		structures.add(TrackHandler.getTrackTypeID(TrackHandler.findTrackType("curve")), cornerStruct);
//		structures.add(TrackHandler.getTrackTypeID(TrackHandler.findTrackType("slope_up")), slopeUpStruct);
//		structures.add(TrackHandler.getTrackTypeID(TrackHandler.findTrackType("slope")), slopeStruct);
//		structures.add(TrackHandler.getTrackTypeID(TrackHandler.findTrackType("slope_down")), slopeDownStruct);
//		structures.add(TrackHandler.getTrackTypeID(TrackHandler.findTrackType("loop")), loopStruct);
//		structures.add(TrackHandler.getTrackTypeID(TrackHandler.findTrackType("heartline_roll")), heartLineStruct);

//		while (structures.listIterator().hasNext()) {
//			RCMod.multiBlockManager.addStructure(structures.listIterator().next());
//		}
	}

	@Override
	public boolean isStructureFormed(int id, TileEntity tileEntity) {
//		if (tileEntity instanceof TileEntityTrackBase) {
//			TileEntityTrackBase teTrack = (TileEntityTrackBase) tileEntity;
//			int meta = teTrack.getWorldObj().getBlockMetadata(teTrack.xCoord, teTrack.yCoord, teTrack.zCoord);
//			meta = meta > 11 ? meta - 10 : meta;
//			return checkStructure(id, teTrack.getWorldObj(), teTrack.xCoord, teTrack.yCoord, teTrack.zCoord, ForgeDirection.getOrientation(meta), structure.getStructure(id));
//		} else {
//			return false;
//		}

		return true;
	}

	@Override
	public boolean checkStructure(int id, World world, int x, int y, int z, ForgeDirection direction, MultiBlockStructure structure) {
		TileEntity tileEntity = world.getTileEntity(x, y, z);

		if (tileEntity instanceof TileEntityTrackBase) {
			TileEntityTrackBase teTrack = (TileEntityTrackBase) tileEntity;

			if (teTrack.track == null) {
				teTrack.track = (BlockTrackBase) world.getBlock(x, y, z);
			}

			//NORTH: -Z, SOUTH: +Z, WEST: -X, EAST: +X
			int offsetX = 0;
			int offsetY = 0;
			int offsetZ = 0;
			int layerToCheckID = TrackHandler.Types.valueOf(teTrack.track.track_type.unlocalized_name).typeID;

		}

		return false;
	}

	@Override
	public void placeTemplateBlocks(int id, World world, int x, int y, int z, ForgeDirection direction, MultiBlockStructure structure) {}

	@Override
	public void breakStructure(int id, World world, int x, int y, int z, ForgeDirection direction, MultiBlockStructure structure) {}

	@Override
	public void rotateAliasIndices(ForgeDirection axis, boolean clockWise, byte timesToApply) {}
}
