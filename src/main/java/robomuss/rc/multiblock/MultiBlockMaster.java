package robomuss.rc.multiblock;

import java.util.ArrayList;

public class MultiBlockMaster {
	int numRows, numColumns, numLayers;

	private ArrayList<String[][]> structure;

	public MultiBlockMaster(int numLayers, int numRows, int numColumns) {
		structure = new ArrayList<String[][]>(numLayers);

		for (int i = 0; i < numRows; i++) {
			structure.add(i, new String[numRows][numColumns]);
		}
	}

	public void setNumRows(int rows) {
		this.numRows = rows;
	}

	public void setNumColumns(int columns) {
		this.numColumns = columns;
	}

	public void setNumLayers(int layers) {
		this.numLayers = layers;
	}

	public int getNumRows() {
		return numRows;
	}

	public int getNumColumns() {
		return numColumns;
	}

	public int getNumLayers() {
		return numLayers;
	}

	public void setStructure(int layer, Object ... objects) {
		if (structure.size() > layer) {
			if (objects.length < numRows) {

			}
		}
	}
}
