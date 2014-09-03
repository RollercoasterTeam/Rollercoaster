package robomuss.rc.events;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

import org.lwjgl.input.Keyboard;

import robomuss.rc.block.te.TileEntityTrack;
import robomuss.rc.item.RCItems;
import robomuss.rc.network.NetworkHandler;
import robomuss.rc.tracks.TrackHandler;
import robomuss.rc.util.IPaintable;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class BlockClickedEvent {
	
	@SubscribeEvent
	public void onBlockClicked(PlayerInteractEvent event) {
		if(event.world.isRemote) {
			if(event.entityPlayer.capabilities.isCreativeMode) {
				if(event.action == PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK) {
					if(event.entityPlayer.worldObj.getBlock(event.x, event.y, event.z) instanceof IPaintable) {
						if(Keyboard.isKeyDown(Keyboard.KEY_P)) {
							int meta = ((IPaintable) event.entityPlayer.worldObj.getBlock(event.x, event.y, event.z)).getPaintMeta(event.entityPlayer.worldObj, event.x, event.y, event.z);
							NetworkHandler.changePaintColour(meta);
						}
					}
					if(Keyboard.isKeyDown(Keyboard.KEY_K)) {
						System.out.println("Test");
						NetworkHandler.killAll();
					}
				}
			}
		}
		if(!event.world.isRemote) {
			if(event.action == PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK) {
				if(event.entityPlayer.isSneaking()) {
					if(event.entityPlayer.getHeldItem().getItem() != null && event.entityPlayer.getHeldItem().getItem() == RCItems.hammer) {
						TileEntity tileentity = event.world.getTileEntity(event.x, event.y, event.z);
						if(tileentity instanceof TileEntityTrack) {
							TileEntityTrack te = (TileEntityTrack) tileentity;
							int id = 100;
							for(int i = 0; i < TrackHandler.types.size(); i++) {
								//System.out.println("Looping: " + TrackHandler.types.get(i).getId());
								if(te.type != null && TrackHandler.types.get(i).getId() == te.type.getId()) {
									//System.out.println("Match of: " + te.type.getId() + " and " + TrackHandler.types.get(i).getId());
									id = i;
								}
							}
							te.type = TrackHandler.types.get(id + 1);
							event.world.markBlockRangeForRenderUpdate(event.x, event.y, event.z, event.x, event.y, event.z);
						}
					}
				}
			}
		}
	}
}
