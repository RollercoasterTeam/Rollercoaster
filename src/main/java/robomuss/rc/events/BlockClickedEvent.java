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
import cpw.mods.fml.common.FMLLog;
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
						FMLLog.info("Sorry, feature disabled as it would be a massive griefing tool!");
						//NetworkHandler.killAll();
					}
				}
			}
		}
		if(!event.world.isRemote) {
			if(event.action == PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK) {
				if(event.entityPlayer.getHeldItem() != null && event.entityPlayer.getHeldItem().getItem() == RCItems.hammer) {
					TileEntity tileentity = event.world.getTileEntity(event.x, event.y, event.z);
					
					if(event.entityPlayer.getCurrentEquippedItem().stackTagCompound == null) {
						event.entityPlayer.getCurrentEquippedItem().stackTagCompound = new NBTTagCompound();
						event.entityPlayer.getCurrentEquippedItem().stackTagCompound.setInteger("mode", 0);
					}
					
					ItemHammer.modes[event.entityPlayer.getCurrentEquippedItem().stackTagCompound.getInteger("mode")].onRightClick(tileentity, event);
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
