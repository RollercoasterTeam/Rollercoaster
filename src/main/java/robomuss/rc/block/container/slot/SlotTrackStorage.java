package robomuss.rc.block.container.slot;

import robomuss.rc.block.RCBlocks;
import robomuss.rc.tracks.TrackHandler;
import net.minecraft.block.BlockChest;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityChest;

public class SlotTrackStorage extends Slot {

	public SlotTrackStorage(IInventory par1iInventory, int id, int x, int y) {
		super(par1iInventory, id, x, y);
	}

	@Override
	public boolean isItemValid(ItemStack itemstack) {
		int id = slotNumber - 36;
		if(id == 0 && TrackHandler.findTrackType(itemstack.getItem()).unlocalized_name == "horizontal") {
			return true;
		}
		if(id == 1 && TrackHandler.findTrackType(itemstack.getItem()).unlocalized_name == "slope_up") {
			return true;
		}
		if(id == 2 && TrackHandler.findTrackType(itemstack.getItem()).unlocalized_name == "slope") {
			return true;
		}
		if(id == 3 && TrackHandler.findTrackType(itemstack.getItem()).unlocalized_name == "slope_down") {
			return true;
		}
		if(id == 4 && TrackHandler.findTrackType(itemstack.getItem()).unlocalized_name == "curve") {
			return true;
		}
		if(id == 5 && TrackHandler.findTrackType(itemstack.getItem()).unlocalized_name == "loop") {
			return true;
		}
		if(id == 6 && itemstack.getItem() == Item.getItemFromBlock(RCBlocks.support)) {
			return true;
		}
		return false;
	}
}
