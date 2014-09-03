package robomuss.rc.block.container.slot;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import robomuss.rc.tracks.TrackHandler;
import robomuss.rc.tracks.TrackType;

public class SlotTrackStorage extends Slot {

	public SlotTrackStorage(IInventory par1iInventory, int id, int x, int y) {
		super(par1iInventory, id, x, y);
	}

	@Override
	public boolean isItemValid(ItemStack itemstack) {
		for(TrackType track : TrackHandler.tracks) {
			if(itemstack.getItem() == Item.getItemFromBlock(track.block)) {
				return true;
			}
		}
		return false;
	}
}
