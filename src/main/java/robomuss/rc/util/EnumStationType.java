package robomuss.rc.util;

import robomuss.rc.block.RCBlocks;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;

public enum EnumStationType {
	WOODEN("Wooden", Blocks.planks, Blocks.fence, RCBlocks.woodenSupport),
	NETHER("Nether", Blocks.nether_brick, Blocks.nether_brick_fence, Blocks.nether_brick),
	IRON("Iron", Blocks.iron_block, Blocks.iron_bars, Blocks.iron_block),
	LOG("Log", Blocks.log, Blocks.log, Blocks.log),
	SAND("Sand", Blocks.sandstone, Blocks.sand, Blocks.sand);
	
	public String name;
	public Block block, fence, support;
	
	EnumStationType(String name, Block block, Block fence, Block support) {
		this.name = name;
		
		this.block = block;
		this.fence = fence;
		this.support = support;
	}
}
