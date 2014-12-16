package robomuss.rc.multiblock;

import net.minecraft.tileentity.TileEntity;

import java.util.HashMap;

public class MultiBlockManager {
	private static final MultiBlockManager instance = new MultiBlockManager();

	private HashMap structMap = new HashMap();

	public static final MultiBlockManager getInstance() {
		return instance;
	}

	public MultiBlockManager() {}

	/**
	 * Returns true if the structure was successfully added.
	 * Unsuccessful attempts return false, and occur when attempting to add a structure that is already contained in the structure map.
	 */
	public boolean addStructure(MultiBlockStructure structure) {
		if (structMap.containsValue(structure)) {
			return false;
		} else {
			structMap.put(structure.getStructureID(), structure);
			return true;
		}
	}

	public MultiBlockStructure getStructure(String structID) {
		if (structMap.containsKey(structID)) {
			return (MultiBlockStructure) structMap.get(structID);
		} else {
			return null;
		}
	}

	public HashMap getStructures() {
		return structMap;
	}

	public boolean isStructureFormed(TileEntity tileEntity) {
		return false;
	}
}
