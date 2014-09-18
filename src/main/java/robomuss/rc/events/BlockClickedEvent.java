package robomuss.rc.events;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

import org.lwjgl.input.Keyboard;

import robomuss.rc.block.te.TileEntityFooter;
import robomuss.rc.block.te.TileEntitySupport;
import robomuss.rc.block.te.TileEntityTrack;
import robomuss.rc.item.ItemHammer;
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
						System.out.println("Sorry, feature disabled as it would be a massive griefing tool!");
						//NetworkHandler.killAll();
					}
				}
			}
		}
		if(!event.world.isRemote) {
			if(event.action == PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK) {
				if(event.entityPlayer.isSneaking()) {
					if(event.entityPlayer.getHeldItem() != null && event.entityPlayer.getHeldItem().getItem() == RCItems.hammer) {
						TileEntity tileentity = event.world.getTileEntity(event.x, event.y, event.z);
						if(tileentity instanceof TileEntityTrack) {
							TileEntityTrack te = (TileEntityTrack) tileentity;
							int id = 0;
							for(int i = 0; i < TrackHandler.types.size(); i++) {
								if(te.type != null && TrackHandler.types.get(i).getId() == te.type.getId()) {
									id = i;
								}
							}
							if(id < TrackHandler.types.size() - 1) {
								te.type = TrackHandler.types.get(id + 1);
							}
							else {
								te.type = TrackHandler.types.get(0);
							}
							event.world.markBlockForUpdate(event.x, event.y, event.z);
						}
						if(tileentity instanceof TileEntityFooter) {
							TileEntityFooter te = (TileEntityFooter) tileentity;
							te.forceConnection = te.forceConnection ? false : true;
							event.world.markBlockForUpdate(event.x, event.y, event.z);
						}
						if(tileentity instanceof TileEntitySupport) {
							TileEntitySupport te = (TileEntitySupport) tileentity;
							te.flange = te.flange ? false : true;
							event.world.markBlockForUpdate(event.x, event.y, event.z);
						}
					}
				}
			}
			if(event.action == PlayerInteractEvent.Action.RIGHT_CLICK_AIR)  {
				if(event.entityPlayer.isSneaking()) {
					EntityPlayer player = event.entityPlayer;
					if(player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem() == RCItems.hammer) {
						if(player.getCurrentEquippedItem().stackTagCompound == null) {
							player.getCurrentEquippedItem().stackTagCompound = new NBTTagCompound();
							player.getCurrentEquippedItem().stackTagCompound.setInteger("mode", 0);
						}
						int mode = player.getCurrentEquippedItem().stackTagCompound.getInteger("mode");
						int newMode = mode + 1 < ItemHammer.modes.length ? mode + 1 : 0;
						player.getCurrentEquippedItem().stackTagCompound.setInteger("mode", newMode);
					}
				}
			}
		}
	}
}
