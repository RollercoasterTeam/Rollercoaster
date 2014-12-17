package robomuss.rc.block.container.slot;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import robomuss.rc.track.TrackHandler;
import robomuss.rc.track.piece.TrackPiece;

public class SlotTrackStorage extends Slot {
	public SlotTrackStorage(IInventory iInventory, int id, int x, int y) {
		super(iInventory, id, x, y);
	}

	@Override
	public boolean isItemValid(ItemStack itemstack) {
		for(TrackPiece track : TrackHandler.pieces) {
			if(itemstack.getItem() == Item.getItemFromBlock(track.block)) {
				return true;
			}
		}

		return false;
	}
}
