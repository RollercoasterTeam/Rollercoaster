package robomuss.rc.event;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.block.BlockContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import org.lwjgl.input.Keyboard;
import robomuss.rc.item.ItemHammer;
import robomuss.rc.item.RCItems;
import robomuss.rc.network.NetworkHandler;
import robomuss.rc.util.IPaintable;
import net.minecraft.block.Block;

public class BlockClickedEvent extends Event {
	
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
