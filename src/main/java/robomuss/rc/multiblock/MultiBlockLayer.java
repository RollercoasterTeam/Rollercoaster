package robomuss.rc.multiblock;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;

public class MultiBlockLayer {
	public final int layerWidth;
	public final int layerDepth;
	public final Block[] blocks;
	private int layerID;
	private boolean hasBlockAliases;
	private List<Block> aliasList;

	public MultiBlockLayer(int layerID, int layerWidth, int layerDepth, Block[] blocks) {
		this.layerID = layerID;
		this.layerWidth = layerWidth;
		this.layerDepth = layerDepth;
		this.blocks = blocks;
	}

	private void setHasBlockAliases(boolean hasBlockAliases) {
		this.hasBlockAliases = hasBlockAliases;
		this.aliasList = new ArrayList<Block>();
	}

	public boolean getHasBlockAliases() {
		return this.hasBlockAliases;
	}

	public void setAliasList(List<Block> aliasList) {
		setHasBlockAliases(true);

		if (getHasBlockAliases()) {
			for (Block block : aliasList) {
				if (block != null) {
					this.aliasList.add(block);
				}
			}
		}
	}

	public List getAliasList() {
		if (getHasBlockAliases()) {
			if (!this.aliasList.isEmpty()) {
				return this.aliasList;
			}
		}
		return null;
	}

	public int getNumAliases(Block block) {
		if (getHasBlockAliases()) {
			if (block != null && this.aliasList.contains(block)) {
				return this.aliasList.size() - 1;
			}
		}
		return 0;
	}

	public List<Block> getAliases(Block block) {
		if (block != null && this.aliasList.contains(block)) {
			List<Block> aliases = new ArrayList<Block>();
			for (Block block1 : this.aliasList) {
				if (!block1.getUnlocalizedName().equalsIgnoreCase(block.getUnlocalizedName())) {
					aliases.add(block1);
				}
			}
			return aliases;
		}
		return null;
	}

	public Block getAlias(Block block, int aliasIndex) {
		if (getHasBlockAliases()) {
			if (getAliases(block) != null) {
				if (aliasIndex >= 0 && aliasIndex < getAliases(block).size()) {
					return getAliases(block).get(aliasIndex);
				}
			}
		}
		return null;
	}

	public Block[] getBlocks() {
		return this.blocks;
	}

	public Block getBlock(int row, int column) {
		return blocks[column + (row * 3)];
	}

	public int getLayerWidth() {
		return this.layerWidth;
	}

	public int getLayerDepth() {
		return this.layerDepth;
	}

	public int getLayerSize() {
		return this.layerDepth * this.layerWidth;
	}

	public int getLayerID() {
		return this.layerID;
	}
}
