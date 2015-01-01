package robomuss.rc.multiblock;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import robomuss.rc.block.BlockPath;
import scala.tools.cmd.gen.AnyVals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public final class MultiBlockManager {
	private static final MultiBlockManager instance = new MultiBlockManager();
	private static HashMap structMap = new HashMap();
	private boolean haveStructuresBeenRegistered = false;

	public static MultiBlockManager getInstance() {
		return instance;
	}

	private MultiBlockManager() {}

	/**
	 * Returns true if the structure was successfully added.
	 * Unsuccessful attempts return false, and occur when attempting to add a structure that is already contained in the structure map.
	 */
	public boolean addStructure(MultiBlockStructure structure) {
		if (structMap.containsValue(structure)) {
			return false;
		} else {
			structMap.put(structure.getStructureName(), structure);
			return true;
		}
	}

	public MultiBlockStructure getStructure(String structName) {
		return structMap.containsKey(structName) ? (MultiBlockStructure) structMap.get(structName) : null;
	}

	public void rotateStructure(String structName, ForgeDirection axis, boolean clockWise, byte timesToApply) {
		MultiBlockStructure startStruct = this.getStructure(structName);
		int counter = 0;
		int requestedAxis = -1;
		timesToApply &= 3;

		Block[][][] splitTemplate = new Block[startStruct.getNumLayers()][startStruct.getNumColumns()][startStruct.getNumRows()];

		while (counter < startStruct.getStructure().size()) {
			for (int l = 0; l < startStruct.getNumRows(); l++) {
				for (int c = 0; c < startStruct.getNumColumns(); c++) {
					for (int r = 0; r < startStruct.getNumRows(); r++) {
						splitTemplate[l][c][r] = startStruct.getStructure().get(counter);
						counter++;
					}
				}
			}
		}

		if (axis != null && axis != ForgeDirection.UNKNOWN) {
			if (axis == ForgeDirection.WEST || axis == ForgeDirection.EAST) {
				requestedAxis = 0;
			} else if (axis == ForgeDirection.DOWN || axis == ForgeDirection.UP) {
				requestedAxis = 1;
			} else if (axis == ForgeDirection.NORTH || axis == ForgeDirection.SOUTH) {
				requestedAxis = 2;
			}
		}

		if (requestedAxis == 0) {

		} else if (requestedAxis == 1) {
			if (timesToApply == 1) {
				if (clockWise) {
					counter = 0;

					List<Block> rotatedStructList = new ArrayList<Block>();

					while (counter < startStruct.getStructure().size()) {
						for (int l = 0; l < startStruct.getNumLayers(); l++) {
							for (int c = 0; c < startStruct.getNumColumns(); c++) {
								for (int r = startStruct.getNumRows() - 1; r >= 0; r--) {
									rotatedStructList.add(splitTemplate[l][r][c]);
									counter++;
								}
							}
						}
					}

					startStruct.registerRotatedStructure(rotatedStructList, timesToApply);
//					startStruct.rotateAliasIndices(axis, clockWise, timesToApply);
				}
			} else if (timesToApply == 2) {
				if (clockWise) {
					counter = 0;
					int reverseCounter = startStruct.getNumColumns() * startStruct.getNumRows() - 1;

					Block[] blocks = new Block[startStruct.getNumLayers() * startStruct.getNumColumns() * startStruct.getNumRows()];

					while (counter < startStruct.getStructure().size()) {
						for (int l = 0; l < startStruct.getNumLayers(); l++) {
							for (int c = 0; c < startStruct.getNumColumns(); c++) {
								for (int r = 0; r < startStruct.getNumRows(); r++) {
									blocks[reverseCounter] = splitTemplate[l][c][r];
									reverseCounter--;
									counter++;
								}
							}

							reverseCounter = ((startStruct.getNumColumns() * startStruct.getNumRows()) * (l + 2)) - 1;
						}
					}

					startStruct.registerRotatedStructure(Arrays.asList(blocks), timesToApply);
//					startStruct.rotateAliasIndices(axis, clockWise, timesToApply);
				}
			} else if (timesToApply == 3) {
				if (clockWise) {
					counter = 0;
					int reverseCounter = startStruct.getNumColumns() * startStruct.getNumRows() - 1;

					List<Block> rotatedStructList = new ArrayList<Block>();
					List<Block> lList = new ArrayList<Block>();

					while (counter < startStruct.getStructure().size()) {
						for (int l = 0; l < startStruct.getNumLayers(); l++) {
							for (int c = 0; c < startStruct.getNumColumns(); c++) {
								for (int r = startStruct.getNumRows() - 1; r >= 0; r--) {
									lList.add(splitTemplate[l][r][c]);
									counter++;
								}
							}

							while (reverseCounter >= 0) {
								rotatedStructList.add(lList.get(reverseCounter));
								reverseCounter--;
							}

							lList = new ArrayList<Block>();
							reverseCounter = startStruct.getNumColumns() * startStruct.getNumRows() - 1;
						}
					}

					startStruct.registerRotatedStructure(rotatedStructList, timesToApply);
//					startStruct.rotateAliasIndices(axis, clockWise, timesToApply);
				}
			}
		} else if (requestedAxis == 2) {

		}
	}

	public HashMap getStructures() {
		return structMap;
	}

	public void registerStructures() {
		if (!haveStructuresBeenRegistered) {
			MultiBlockTrackFabricator.structure.registerStructure();
//			MultiBlockTrackBase.structure.registerStructure();
			haveStructuresBeenRegistered = true;
		}
	}
}
