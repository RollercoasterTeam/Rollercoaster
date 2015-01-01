package robomuss.rc.multiblock;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.*;

public abstract class MultiBlockStructure {
	private List<Block> structure = new ArrayList<Block>();
	private List<Block> rotated1  = new ArrayList<Block>();
	private List<Block> rotated2  = new ArrayList<Block>();
	private List<Block> rotated3  = new ArrayList<Block>();
	private List<Block> aliases;
	private int[]       aliasIndices;
	private int[]       aliasIndices1;
	private int[]       aliasIndices2;
	private int[]       aliasIndices3;
	private int[]       layersWithAliases;
	private int[]       dummies;
	private boolean[] rotationAxis = new boolean[] {false, false, false};
	private String        structureName;
	private ChunkPosition masterPos;
	private char          masterChar;
	private Block         masterBlock;
	private int masterIndex = 0;
	private int masterLocation;
	private boolean hasMaster = false;
	private int     numRows;
	private int     numLayers;
	private int     numColumns;
	private boolean containsDummies;
	private boolean hasBlockAliases;

	public MultiBlockStructure() {}

	public String getStructureName() {
		return this.structureName;
	}

	public void setStructureName(String structureName) {
		this.structureName = structureName;
	}

	public List<? extends Block> getStructure() {
		return structure;
	}

	public List<? extends Block> getLayer(int layer) {
		if (this.structure != null && this.structure.size() > (numLayers * numColumns * numRows)) {
			if (0 <= layer && layer < numLayers) {
				int startIndex = getTemplateIndex(0, layer, 0);
				int endIndex = (layer + 1 != numLayers) ? getTemplateIndex(0, layer + 1, 0) + 1 : numLayers;
//				return Arrays.copyOfRange(this.structure, startIndex, endIndex);
				return this.structure.subList(startIndex, endIndex);
			}
		}

		return null;
	}

	public int getNumRows() {
		return numRows;
	}

	public int getNumLayers() {
		return numLayers;
	}

	public int getNumColumns() {
		return numColumns;
	}

	private void setHasBlockAliases(boolean hasBlockAliases) {
		this.hasBlockAliases = hasBlockAliases;
	}

	public boolean getHasBlockAliases() {
		return this.hasBlockAliases;
	}

	public void setAliases(List<Block> aliases, int[] layers, int[] aliasIndices) {
		setHasBlockAliases(true);
		this.aliases = aliases;
		this.layersWithAliases = layers;
		this.aliasIndices = aliasIndices;
	}

	public List<? extends Block> getAliases() {
		if (getHasBlockAliases()) {
			return this.aliases;
		}

		return null;
	}

	public int getNumAliases(Block block, int layer) {
		if (getHasBlockAliases()) {
			boolean layerHasAliases = false;

			for (int n : this.layersWithAliases) {
				if (layer == n) {
					layerHasAliases = true;
				}
			}

			if (layerHasAliases) {
				if (block != null && this.aliases.contains(block)) {
					return this.aliases.size();
				}
			}
//			if (Arrays.asList(this.layersWithAliases).contains(layer)) {
//				if (block != null && this.aliases.contains(block)) {
//					return this.aliases.size() - 1;
//				}
//			}
		}

		return 0;
	}

	public Block[] getAliases(Block block, int layer) {
		if (block != null && this.aliases.contains(block)) {
			boolean layerHasAliases = false;

			for (int n : this.layersWithAliases) {
				if (layer == n) {
					layerHasAliases = true;
				}

				if (layerHasAliases) {
					List<Block> aliasList = new ArrayList<Block>();

					for (Block block1 : this.aliases) {
						if (!Block.isEqualTo(block, block1)) {
							aliasList.add(block1);
						}
					}

					Block[] aliasArray = new Block[aliasList.size()];

					for (int i = 0; i < aliasList.size(); i++) {
						aliasArray[i] = aliasList.get(i);
					}

					return aliasArray;
				}
			}
		}

		return null;
	}

	public Block getAlias(Block block, int layer, int aliasIndex) {
		if (getHasBlockAliases()) {
			if (getAliases(block, layer) != null) {
				if (0 <= aliasIndex && aliasIndex < getAliases(block, layer).length) {
					return getAliases(block, layer)[aliasIndex];
				}
			}
		}

		return null;
	}

	public int[] getLayersWithAliases() {
		return this.layersWithAliases;
	}

	public int[] getAliasIndices() {
		return this.aliasIndices;
	}

	public boolean setStructureDimensions(int numRows, int numLayers, int numColumns) {
		if (numRows > 0 && numLayers > 0 && numColumns > 0) {
			this.numRows = numRows;
			this.numLayers = numLayers;
			this.numColumns = numColumns;
			return true;
		}

		return false;
	}

	public void setRotationAxis(boolean x, boolean y, boolean z) {
		this.rotationAxis[0] = x;
		this.rotationAxis[1] = y;
		this.rotationAxis[2] = z;
	}

	public boolean[] getRotationAxis() {
		return this.rotationAxis;
	}

	public Block getMasterBlock() {
		return this.masterBlock;
	}

	public void setMasterPos(int x, int y, int z) {
		this.masterPos = new ChunkPosition(x, y, z);
	}

	public ChunkPosition getMasterPos() {
		return this.masterPos;
	}

	public int getMasterIndex() {
		return this.masterIndex;
	}

	public char getMasterChar() {
		return this.masterChar;
	}

	public int[] getTemplateMatrixLocation(int templateIndex, MultiBlockStructure structure) {
		if (0 <= templateIndex && templateIndex < structure.getStructure().size()) {
			int layer = templateIndex / (numRows * numColumns);
			templateIndex -= (numRows * numColumns) * layer;
			int column = templateIndex / numRows;
			templateIndex -= numRows * column;
			int row = templateIndex;
			return new int[] {layer, column, row};
		}

		return null;
	}

	public int getTemplateIndex(int layer, int column, int row) {
		if (0 <= row && row < numRows && 0 <= layer && layer < numLayers && 0 <= column && column < numColumns) {
			return ((numColumns * numRows) * layer) + ((numColumns * row) + column);
		}

		return 0;
	}

	public Block getTemplateBlock(int layer, int column, int row) {
		int templateIndex = getTemplateIndex(layer, column, row);
		return this.structure.get(templateIndex);
	}

	public ChunkPosition up(ChunkPosition pos, int n) {
		return offset(pos, ForgeDirection.UP, n);
	}

	public ChunkPosition down(ChunkPosition pos, int n) {
		return offset(pos, ForgeDirection.DOWN, n);
	}

	public ChunkPosition north(ChunkPosition pos, int n) {
		return offset(pos, ForgeDirection.NORTH, n);
	}

	public ChunkPosition south(ChunkPosition pos, int n) {
		return offset(pos, ForgeDirection.SOUTH, n);
	}

	public ChunkPosition west(ChunkPosition pos, int n) {
		return offset(pos, ForgeDirection.WEST, n);
	}

	public ChunkPosition east(ChunkPosition pos, int n) {
		return offset(pos, ForgeDirection.EAST, n);
	}

	public ChunkPosition offset(ChunkPosition pos, ForgeDirection dir, int n) {
		return new ChunkPosition(pos.chunkPosX + dir.offsetX * n, pos.chunkPosY + dir.offsetY * n, pos.chunkPosZ + dir.offsetZ * n);
	}

	public ChunkPosition add(ChunkPosition posA, ChunkPosition posB) {
		return new ChunkPosition(posA.chunkPosX + posB.chunkPosX, posA.chunkPosY + posB.chunkPosY, posA.chunkPosZ + posB.chunkPosZ);
	}

	public List<? extends Block> addLayer(Block masterBlock, Object ... objs) {
		String s = "";
		int i, k, j;
		i = k = j = 0;

		/* Parse Template Strings */
		if (objs[i] instanceof String[]) {
			String[] strings = (String[]) objs[i++];

			for (int l = 0; l < strings.length; l++) {
				String s1 = strings[l];
				++k;
				j = s1.length();
				s = s + s1;
			}
		} else {
			while (objs[i] instanceof String) {
				String s2 = (String) objs[i++];
				++k;
				j = s2.length();
				s = s + s2;
			}
		}

		/* Parse and Assign Characters With Block Instances */
		HashMap hashMap;
		Block block = null;

		for (hashMap = new HashMap(); i < objs.length; i += 2) {
			Character character = (Character) objs[i];

			if (objs[i + 1] instanceof Block) {
				block = (Block) objs[i + 1];
			}

			if (masterBlock != null) {
				if (this.masterBlock == null) {
					this.hasMaster = false;

					if (block != null && Block.isEqualTo(block, masterBlock)) {
						this.hasMaster = true;
						this.masterBlock = block;
						this.masterChar = character;
					}
				} else {
					this.hasMaster = true;
				}
			}

			if (Character.isSpaceChar(character)) {
				block = Blocks.air;
			}

			hashMap.put(character, block);
		}

		/* Create Layer Using Block from HashMap */
		Block[] blocks = new Block[j * k];
		boolean containsDummies = false;
		char dummyChar = '\0'; //Unicode 0
		int[] dummies = new int[blocks.length];

		for (int i1 = 0; i1 < j * k; i1++) {
			char c0 = s.charAt(i1);

			if (s.charAt(i1) == '*' && s.charAt(i1 + 1) == '*') {
				containsDummies = true;
				c0 = dummyChar = s.charAt(i1 + 2);
				i1 += 2;
			}

			if (hashMap.containsKey(Character.valueOf(c0))) {
				if (block != null) {
					if (Block.isEqualTo(block, this.masterBlock) || (c0 == this.masterChar && c0 != dummyChar)) {
						if (!this.hasMaster) {
							this.masterIndex = 0;
						} else {
							this.masterIndex = i1;
						}
					}
				} else {
					if (!this.hasMaster) {
						this.masterIndex = 0;
					}
				}

				dummies[i1] = (c0 == dummyChar) ? i1 : -1;
				blocks[i1] = (Block) hashMap.get(Character.valueOf(c0));
			} else {
				blocks[i1] = Blocks.air;
			}
		}

		if (!containsDummies) {
			this.structure = this.structure != null ? this.structure : new ArrayList<Block>();
			this.structure.addAll(Arrays.asList(blocks));
			return this.structure;
		} else {
			this.dummies = dummies;
			this.containsDummies = true;
			this.structure = this.structure != null ? this.structure : new ArrayList<Block>();
			this.structure.addAll(Arrays.asList(blocks));
			return this.structure;
		}
	}

	public static List<ChunkPosition> getAllInBoxList(ChunkPosition from, ChunkPosition to) {
		final ChunkPosition minPos = new ChunkPosition(Math.min(from.chunkPosX, to.chunkPosX), Math.min(from.chunkPosY, to.chunkPosY), Math.min(from.chunkPosZ, to.chunkPosZ));
		final ChunkPosition maxPos = new ChunkPosition(Math.max(from.chunkPosX, to.chunkPosX), Math.max(from.chunkPosY, to.chunkPosY), Math.max(from.chunkPosZ, to.chunkPosZ));

		int sizeX = (maxPos.chunkPosX - minPos.chunkPosX) + 1;
		int sizeY = (maxPos.chunkPosY - minPos.chunkPosY) + 1;
		int sizeZ = (maxPos.chunkPosZ - minPos.chunkPosZ) + 1;

		List<ChunkPosition> result = new ArrayList<ChunkPosition>();

		int i = minPos.chunkPosX;
		int j = minPos.chunkPosY;
		int k = minPos.chunkPosZ;

		for (int f = 0; f < (sizeX * sizeY * sizeZ); f++) {
			if (f == 0) {
				result.add(0, new ChunkPosition(i, j, k));
			} else if (i < maxPos.chunkPosX) {
				i++;
			} else if (k < maxPos.chunkPosZ) {
				i = minPos.chunkPosX;
				k++;
			} else if (j < maxPos.chunkPosY) {
				i = minPos.chunkPosX;
				k = minPos.chunkPosZ;
				j++;
			}

			result.add(f, new ChunkPosition(i, j, k));
		}

		return result;
	}

	public boolean hasRotatedStructureBeenRegistered(byte timesRotatedClockWise) {
		switch (timesRotatedClockWise) {
			case 1:  return !this.rotated1.isEmpty();
			case 2:  return !this.rotated2.isEmpty();
			case 3:  return !this.rotated3.isEmpty();
			default: return false;
		}
	}

	public void registerRotatedStructure(List<Block> rotatedStruct, byte timesRotatedClockWise) {
		if (rotatedStruct.size() == this.structure.size()) {
			switch (timesRotatedClockWise) {
				case 1:  this.rotated1 = rotatedStruct; break;
				case 2:  this.rotated2 = rotatedStruct; break;
				case 3:  this.rotated3 = rotatedStruct; break;
			}
		}
	}

	public void registerRotatedAliasIndices(int[] rotatedIndices, byte timesRotatedClockwise) {
		if (rotatedIndices.length == this.aliasIndices.length) {
			switch (timesRotatedClockwise) {
				case 1: this.aliasIndices1 = Arrays.copyOf(rotatedIndices, rotatedIndices.length); break;
				case 2: this.aliasIndices2 = Arrays.copyOf(rotatedIndices, rotatedIndices.length); break;
				case 3: this.aliasIndices3 = Arrays.copyOf(rotatedIndices, rotatedIndices.length); break;
			}
		}
	}

	public List<Block> getRotatedStructure(byte timesRotatedClockwise) {
		switch (timesRotatedClockwise) {
			case 1:  return this.rotated1;
			case 2:  return this.rotated2;
			case 3:  return this.rotated3;
			default: return structure;
		}
	}

	public Block getTemplateBlockFromRotated(int layer, int column, int row, byte timesRotatedClockwise) {
		int templateIndex = getTemplateIndex(layer, column, row);

		switch (timesRotatedClockwise) {
			case 1:  return templateIndex < this.rotated1.size() ? this.rotated1.get(templateIndex) : this.structure.get(templateIndex);
			case 2:  return templateIndex < this.rotated2.size() ? this.rotated2.get(templateIndex) : this.structure.get(templateIndex);
			case 3:  return templateIndex < this.rotated3.size() ? this.rotated3.get(templateIndex) : this.structure.get(templateIndex);
			default: return this.structure.get(templateIndex);
		}
	}

	public int[] getRotatedAliasIndices(byte timesRotatedClockwise) {
		switch (timesRotatedClockwise) {
			case 1:  return this.aliasIndices1;
			case 2:  return this.aliasIndices2;
			case 3:  return this.aliasIndices3;
			default: return this.aliasIndices;
		}
	}

	public abstract boolean isStructureFormed(int id, TileEntity tileEntity);

	public abstract boolean checkStructure(int id, World world, int x, int y, int z, ForgeDirection direction, MultiBlockStructure structure);

	public abstract void placeTemplateBlocks(int id, World world, int x, int y, int z, ForgeDirection direction, MultiBlockStructure structure);

	public abstract void breakStructure(int id, World world, int x, int y, int z, ForgeDirection direction, MultiBlockStructure structure);

	public abstract void registerStructure();

	public abstract void rotateAliasIndices(ForgeDirection axis, boolean clockWise, byte timesToApply);
}
