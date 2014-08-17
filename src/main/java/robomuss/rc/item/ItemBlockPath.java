package robomuss.rc.item;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockPath extends ItemBlock {

	public ItemBlockPath(Block block) {
		super(block);
		setHasSubtypes(true);
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return getUnlocalizedName() + "_" + stack.getItemDamage();
	}
	
	@Override
	public int getMetadata(int meta) {
		return meta;
	}
}
