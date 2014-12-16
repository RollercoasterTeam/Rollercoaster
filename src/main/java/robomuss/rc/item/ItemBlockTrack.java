package robomuss.rc.item;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockTrack extends ItemBlock {
	Block block;
	public ItemBlockTrack(Block block) {
		super(block);
		this.block = block;
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return block.getUnlocalizedName();
	}
	
	@Override
	public int getMetadata(int meta) {
		return 0;
	}
}
