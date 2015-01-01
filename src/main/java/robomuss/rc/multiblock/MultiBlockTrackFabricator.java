package robomuss.rc.multiblock;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import robomuss.rc.RCMod;
import robomuss.rc.block.BlockTrackFabricator;
import robomuss.rc.block.RCBlocks;
import robomuss.rc.block.te.TileEntityTrackFabricator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MultiBlockTrackFabricator extends MultiBlockStructure {
	public static MultiBlockTrackFabricator structure = new MultiBlockTrackFabricator();

	@Override
	public void registerStructure() {
		structure.addLayer(RCBlocks.track_fabricator, "#O#", "O#O", "#F#", '#', RCBlocks.track_fabricator_casing, 'F', RCBlocks.track_fabricator, 'O', RCBlocks.track_fabricator_output);
		structure.addLayer(null, "#G#", "G G", "#G#", '#', RCBlocks.track_fabricator_casing, 'G', RCBlocks.track_fabricator_glass);
		structure.addLayer(null, "###", "###", "###", '#', RCBlocks.track_fabricator_casing);

		structure.setStructureDimensions(3, 3, 3);

		List<Block> aliasList = new ArrayList<Block>();
		aliasList.add(RCBlocks.track_fabricator_casing);
		aliasList.add(RCBlocks.track_fabricator_output);
		structure.setAliases(aliasList, new int[] {0}, new int[] {3, 5, 7});

		structure.setRotationAxis(false, true, false);

		structure.setStructureName(RCBlocks.track_fabricator.getUnlocalizedName());
		RCMod.multiBlockManager.addStructure(structure);
	}

	@Override
	public boolean isStructureFormed(int id, TileEntity tileEntity) {
		if (tileEntity instanceof TileEntityTrackFabricator) {
			TileEntityTrackFabricator teFab = (TileEntityTrackFabricator) tileEntity;
			return checkStructure(0, teFab.getWorldObj(), teFab.xCoord, teFab.yCoord, teFab.zCoord, teFab.direction, MultiBlockManager.getInstance().getStructure(RCBlocks.track_fabricator.getUnlocalizedName()));
		} else {
			return false;
		}
	}

	@Override
	public boolean checkStructure(int id, World world, int x, int y, int z, ForgeDirection direction, MultiBlockStructure structure) {
		ChunkPosition masterPos = new ChunkPosition(x, y, z);
		byte timesToRotate = -1;

		switch (direction) {
			case NORTH: timesToRotate = 1; break;
			case SOUTH: timesToRotate = 3; break;
			case WEST:  timesToRotate = 2; break;
			case EAST:  timesToRotate = 0; break;
		}

		if (timesToRotate > 0) {
			RCMod.multiBlockManager.rotateStructure(structure.getStructureName(), ForgeDirection.UP, true, timesToRotate);
		}

		ForgeDirection left = direction.getRotation(ForgeDirection.DOWN);
		ForgeDirection right = direction.getRotation(ForgeDirection.UP);
		ForgeDirection forward = direction;
		ForgeDirection backward = direction.getOpposite();

		ChunkPosition startPos = structure.offset(structure.offset(masterPos, backward, 2), right, 1);
		ChunkPosition endPos = structure.offset(structure.up(masterPos, 2), left, 1);

		List<ChunkPosition> box = MultiBlockStructure.getAllInBoxList(startPos, endPos);

		for (ChunkPosition pos : box) {
			int layer  = structure.getTemplateMatrixLocation(box.indexOf(pos), structure)[0];
			int column = structure.getTemplateMatrixLocation(box.indexOf(pos), structure)[1];
			int row    = structure.getTemplateMatrixLocation(box.indexOf(pos), structure)[2];

			Block blockFound = world.getBlock(pos.chunkPosX, pos.chunkPosY, pos.chunkPosZ);
			Block templateBlock = timesToRotate > 0 ? structure.getTemplateBlockFromRotated(layer, column, row, timesToRotate) : structure.getTemplateBlock(layer, column, row);

			if (!Block.isEqualTo(blockFound, templateBlock)) {
				if (layer != 0) {
					return false;
				} else {
					if (!Block.isEqualTo(blockFound, RCBlocks.track_fabricator_casing)) {
						return false;
					}
				}
			}
		}

		return true;
	}

	@Override
	public void placeTemplateBlocks(int id, World world, int x, int y, int z, ForgeDirection direction, MultiBlockStructure structure) {
		ChunkPosition masterPos = new ChunkPosition(x, y, z);
		byte timesToRotate = -1;

		switch (direction) {
			case NORTH: timesToRotate = 1; break;
			case SOUTH: timesToRotate = 3; break;
			case WEST:  timesToRotate = 2; break;
			case EAST:  timesToRotate = 0; break;
		}

		if (timesToRotate > 0) {
			RCMod.multiBlockManager.rotateStructure(structure.getStructureName(), ForgeDirection.UP, true, timesToRotate);
		}

		ForgeDirection left = direction.getRotation(ForgeDirection.DOWN);
		ForgeDirection right = direction.getRotation(ForgeDirection.UP);
		ForgeDirection forward = direction;
		ForgeDirection backward = direction.getOpposite();

		ChunkPosition startPos = structure.offset(structure.offset(masterPos, backward, 2), right, 1);
		ChunkPosition endPos = structure.offset(structure.up(masterPos, 2), left, 1);

		List<ChunkPosition> box = MultiBlockStructure.getAllInBoxList(startPos, endPos);

		for (ChunkPosition pos : box) {
			int layer  = structure.getTemplateMatrixLocation(box.indexOf(pos), structure)[0];
			int column = structure.getTemplateMatrixLocation(box.indexOf(pos), structure)[1];
			int row    = structure.getTemplateMatrixLocation(box.indexOf(pos), structure)[2];

			Block blockFound = world.getBlock(pos.chunkPosX, pos.chunkPosY, pos.chunkPosZ);
			Block templateBlock = timesToRotate > 0 ? structure.getTemplateBlockFromRotated(layer, column, row, timesToRotate) : structure.getTemplateBlock(layer, column, row);

			if (!(blockFound instanceof BlockTrackFabricator) && !(templateBlock instanceof BlockTrackFabricator)) {
				world.setBlock(pos.chunkPosX, pos.chunkPosY, pos.chunkPosZ, templateBlock);
			}
		}
	}

	@Override
	public void breakStructure(int id, World world, int x, int y, int z, ForgeDirection direction, MultiBlockStructure structure) {
		ChunkPosition masterPos = new ChunkPosition(x, y, z);
		byte timesToRotate = -1;

		switch (direction) {
			case NORTH: timesToRotate = 1; break;
			case SOUTH: timesToRotate = 3; break;
			case WEST:  timesToRotate = 2; break;
			case EAST:  timesToRotate = 0; break;
		}

		if (timesToRotate > 0) {
			RCMod.multiBlockManager.rotateStructure(structure.getStructureName(), ForgeDirection.UP, true, timesToRotate);
		}

		ForgeDirection left = direction.getRotation(ForgeDirection.DOWN);
		ForgeDirection right = direction.getRotation(ForgeDirection.UP);
		ForgeDirection forward = direction;
		ForgeDirection backward = direction.getOpposite();

		ChunkPosition startPos = structure.offset(structure.offset(masterPos, backward, 2), right, 1);
		ChunkPosition endPos = structure.offset(structure.up(masterPos, 2), left, 1);

		List<ChunkPosition> box = MultiBlockStructure.getAllInBoxList(startPos, endPos);

		for (ChunkPosition pos : box) {
			world.setBlockToAir(pos.chunkPosX, pos.chunkPosY, pos.chunkPosZ);
		}
	}

	@Override
	public void rotateAliasIndices(ForgeDirection axis, boolean clockWise, byte timesToApply) {
//		if (structure.hasRotatedStructureBeenRegistered(timesToApply)) {
//			int[] currentAliasIndices = Arrays.copyOf(structure.getAliasIndices(), structure.getAliasIndices().length);
//			int[] rotatedAliasIndices = new int[currentAliasIndices.length];
//			Block[] currentAliasBlocks = new Block[currentAliasIndices.length];
//			Block[] rotatedAliasBlocks = new Block[currentAliasIndices.length];
//
//			for (int i = 0; i < currentAliasIndices.length; i++) {
//				int[] aliasMatrixLocation = structure.getTemplateMatrixLocation(currentAliasIndices[i], structure);
//				currentAliasBlocks[i] = structure.getTemplateBlockFromRotated(aliasMatrixLocation[0], aliasMatrixLocation[1], aliasMatrixLocation[2], timesToApply);
//				System.out.printf("Alias Indices Before Rotation: %d [%d,%d,%d] %d %s%n", timesToApply, aliasMatrixLocation[0], aliasMatrixLocation[1], aliasMatrixLocation[2], currentAliasIndices[i], currentAliasBlocks[i].getLocalizedName());
//			}
//		}
	}
}
