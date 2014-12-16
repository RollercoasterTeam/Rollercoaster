package robomuss.rc.multiblock;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.ChunkPosition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MultiBlockStructure {
	private List layers = new ArrayList();
	private String structureID;
	private ChunkPosition masterPos;
	private char masterChar;
	private Block masterBlock;
	private int masterIndex;

	public MultiBlockStructure() {}

	public String getStructureID() {
		return this.structureID;
	}

	public void setStructureID(String structureID) {
		this.structureID = structureID;
	}

	public List getLayers() {
		return this.layers;
	}

	public MultiBlockLayer getLayer(int layerID) {
		if (layers.size() <= layerID) {
			return null;
		} else {
			return (MultiBlockLayer) layers.get(layerID);
		}
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

	public MultiBlockLayer addLayer(int layerID, Block master, Object ... objs) {
		String s = "";
		int i = 0;
		int j = 0;
		int k = 0;

		/* Parse Template Strings */
		if (objs[i] instanceof String[]) {
			String[] strs = (String[]) objs[i++];

			for (int l = 0; l < strs.length; l++) {
				String s1 = strs[l];
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

			if (master != null) {
				if (block != null && block.getUnlocalizedName().equals(master.getUnlocalizedName())) {
					this.masterBlock = block;
					this.masterChar = character;
				}
			}

			if (Character.isSpaceChar(character)) {
				block = Blocks.air;
			}

			hashMap.put(character, block);
		}

		/* Create Layer Using Block from HashMap */
		Block[] blocks = new Block[j * k];

		for (int i1 = 0; i1 < j * k; i1++) {
			char c0 = s.charAt(i1);

			if (hashMap.containsKey(Character.valueOf(c0))) {
				if (block != null) {
					if (block.getUnlocalizedName().equals(this.masterBlock.getUnlocalizedName()) || c0 == this.masterChar) {
//					if (c0 == this.masterChar || hashMap.containsValue(this.masterBlock)) {
						this.masterIndex = i1;
					}
				} else {
					this.masterIndex = -1;
				}

				blocks[i1] = (Block) hashMap.get(Character.valueOf(c0));
			} else {
				blocks[i1] = Blocks.air;
			}
		}

		MultiBlockLayer layer = new MultiBlockLayer(layerID, 3, 3, blocks);
		this.layers.add(layer);

		return layer;
	}
}
