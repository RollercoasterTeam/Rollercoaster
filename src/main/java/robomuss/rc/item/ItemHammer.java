package robomuss.rc.item;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import robomuss.rc.RCMod;
//import robomuss.rc.block.BlockTrack;
import robomuss.rc.block.BlockTrackBase;
import robomuss.rc.block.RCBlocks;
import robomuss.rc.block.te.*;
import robomuss.rc.track.TrackHandler;
import robomuss.rc.track.TrackManager;
import robomuss.rc.track.piece.TrackPieceSlopeUp;
import robomuss.rc.util.hammer.HammerMode;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemHammer extends Item {

	public static HammerMode[] modes = {
		new HammerMode("Rotate") {
			@Override
			public void onRightClick(TileEntity tileentity, PlayerInteractEvent event) {
				if (tileentity instanceof TileEntityTrackBase) {
					TileEntityTrackBase teTrack = (TileEntityTrackBase) tileentity;
					if (teTrack != null) {
						if (TrackManager.getTrackAtCoords(teTrack.xCoord, teTrack.yCoord, teTrack.zCoord) != null) {
							BlockTrackBase track = TrackManager.getTrackAtCoords(teTrack.xCoord, teTrack.yCoord, teTrack.zCoord);
							if (teTrack.direction != null) {
								if (teTrack.direction == ForgeDirection.WEST) { //debug method...
									Minecraft.getMinecraft().thePlayer.getEntityWorld().setBlock(track.position.chunkPosX + ForgeDirection.WEST.offsetX, track.position.chunkPosY + ForgeDirection.WEST.offsetY, track.position.chunkPosZ + ForgeDirection.WEST.offsetZ, Blocks.stone);
								}
							}
						}
					}
				}
//				if(tileentity instanceof TileEntityTrack) {
//					TileEntityTrack teTrack = (TileEntityTrack) tileentity;
//					if (teTrack.track instanceof BlockTrack) {
//						if (teTrack.hasSlope(teTrack.track)) {
//							teTrack.direction = teTrack.direction.getRotation(ForgeDirection.UP);
//							teTrack.track.updateRotation(event.world, teTrack.xCoord, teTrack.yCoord, teTrack.zCoord);
//						} else {
//							teTrack.direction = teTrack.direction.getRotation(ForgeDirection.UP);
//						}
//					}
//					event.world.markBlockForUpdate(event.x, event.y, event.z);
//				}
				if(tileentity instanceof TileEntityRideFence) {
					TileEntityRideFence terf = (TileEntityRideFence) event.world.getTileEntity(event.x, event.y, event.z);
					if(terf.direction == 3) {
						terf.direction = 0;
					}
					else {
						terf.direction++;
					}
					event.world.markBlockForUpdate(event.x, event.y, event.z);
				}
				if(tileentity instanceof TileEntityWoodenSupport) {
					TileEntityWoodenSupport tews = (TileEntityWoodenSupport) event.world.getTileEntity(event.x, event.y, event.z);
					if(tews.direction == 3) {
						tews.direction = 0;
					}
					else {
						tews.direction++;
					}
					event.world.markBlockForUpdate(event.x, event.y, event.z);
				}
				if(tileentity instanceof TileEntityConveyor) {
					TileEntityConveyor tec = (TileEntityConveyor) event.world.getTileEntity(event.x, event.y, event.z);
					if(tec.direction == 3) {
						tec.direction = 0;
					}
					else {
						tec.direction++;
					}
					event.world.markBlockForUpdate(event.x, event.y, event.z);
				}
			}
		},
		new HammerMode("Change Style") {
//			@Override
//			public void onRightClick(TileEntity tileentity, PlayerInteractEvent event) {
//				if(tileentity instanceof TileEntityTrack) {
//					TileEntityTrack te = (TileEntityTrack) tileentity;
//					int id = 0;
//					for(int i = 0; i < TrackHandler.styles.size(); i++) {
//						if(te.style != null && TrackHandler.styles.get(i).getId() == te.style.getId()) {
//							id = i;
//						}
//					}
//					if(id < TrackHandler.styles.size() - 1) {
//						te.style = TrackHandler.styles.get(id + 1);
//					}
//					else {
//						te.style = TrackHandler.styles.get(0);
//					}
//					event.world.markBlockForUpdate(event.x, event.y, event.z);
//				}
//			}
		},
		new HammerMode("Adjustment") {
			@Override
			public void onRightClick(TileEntity tileentity, PlayerInteractEvent event) {
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
			};
		}
	};
	
	public ItemHammer() {
		setMaxStackSize(1);
		setMaxDamage(100);
	}

	@Override
	public boolean doesContainerItemLeaveCraftingGrid(ItemStack itemStack) {
		return false;
	}

	@Override
	public boolean getShareTag() {
		return true;
	}
	
	@Override
    public ItemStack getContainerItem(ItemStack itemStack) {
        ItemStack copiedStack = itemStack.copy();

        copiedStack.setItemDamage(copiedStack.getItemDamage() + 1);
        copiedStack.stackSize = 1;

        return copiedStack;
    }
	
	@Override
	public boolean hasContainerItem(ItemStack stack) {
		return true;
	}
	
	@Override
	public void onCreated(ItemStack stack, World world, EntityPlayer player) {
		stack.stackTagCompound = new NBTTagCompound();
		stack.stackTagCompound.setInteger("mode", 0);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean val) {
		if(stack.stackTagCompound == null) {
			stack.stackTagCompound = new NBTTagCompound();
			stack.stackTagCompound.setInteger("mode", 0);
		}
		list.add(modes[stack.stackTagCompound.getInteger("mode")].name + " Mode");
	}
}
