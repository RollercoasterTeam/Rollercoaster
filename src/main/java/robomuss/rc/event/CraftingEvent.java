package robomuss.rc.event;


public class CraftingEvent {
	/*@SubscribeEvent
	public void onItemCrafted(ItemCraftedEvent event) {
		if(!event.player.worldObj.isRemote) {
			IInventory craftMatrix = event.craftMatrix;
			for (int i = 0; i < craftMatrix.getSizeInventory(); i++) {
				if(craftMatrix.getStackInSlot(i) != null) {
					ItemStack slot = craftMatrix.getStackInSlot(i);
					if(slot.getItem() != null && slot.getItem() == RCItems.hammer) {
						System.out.println("Hammer Hammer!");
						slot.damageItem(1, event.player);
						//event.craftMatrix.setInventorySlotContents(i, slot);
					}
				}
			}
		}
	}*/
}
