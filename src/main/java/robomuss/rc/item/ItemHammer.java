package robomuss.rc.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import robomuss.rc.block.te.*;
import robomuss.rc.track.TrackHandler;
import robomuss.rc.util.hammer.HammerMode;

import java.util.List;

//import robomuss.rc.block.BlockTrack;

public class ItemHammer extends Item {
	public static HammerMode[] modes = {
		new HammerMode("Rotate") {
			@Override
			public void onRightClick(TileEntity tileentity, PlayerInteractEvent event) {
				if (tileentity instanceof TileEntityTrackBase) {
					if (event.entityPlayer.isSneaking()) {
						((TileEntityTrackBase) tileentity).rotate(true);
					} else {
						((TileEntityTrackBase) tileentity).rotate(false);
					}
				}

				if (tileentity instanceof TileEntityRideFence) {
					TileEntityRideFence terf = (TileEntityRideFence) event.world.getTileEntity(event.x, event.y, event.z);
					terf.direction = terf.direction == 3 ? 0 : terf.direction + 1;
					event.world.markBlockForUpdate(event.x, event.y, event.z);
				}

				if (tileentity instanceof TileEntityWoodenSupport) {
					TileEntityWoodenSupport tews = (TileEntityWoodenSupport) event.world.getTileEntity(event.x, event.y, event.z);
					tews.direction = tews.direction == 3 ? 0 : tews.direction + 1;
					event.world.markBlockForUpdate(event.x, event.y, event.z);
				}

				if (tileentity instanceof TileEntityConveyor) {
					TileEntityConveyor tec = (TileEntityConveyor) event.world.getTileEntity(event.x, event.y, event.z);
					tec.direction = tec.direction == 3 ? 0 : tec.direction + 1;
					event.world.markBlockForUpdate(event.x, event.y, event.z);
				}
			}
		},
			new HammerMode("Change Style") {
				@Override
				public void onRightClick(TileEntity tileentity, PlayerInteractEvent event) {
					if (tileentity instanceof TileEntityTrackBase) {
						TileEntityTrackBase te = (TileEntityTrackBase) tileentity;
						int id = 0;

						for (int i = 0; i < TrackHandler.styles.size(); i++) {
							if (te.style != null && TrackHandler.styles.get(i).getId().equals(te.style.getId())) {
								id = i;
							}
						}

						if (id < TrackHandler.styles.size() - 1) {
							te.style = TrackHandler.styles.get(id + 1);
						} else {
							te.style = TrackHandler.styles.get(0);
						}

						event.world.markBlockForUpdate(event.x, event.y, event.z);
					}
				}
			},
			new HammerMode("Adjustment") {
				@Override
				public void onRightClick(TileEntity tileentity, PlayerInteractEvent event) {
					if (tileentity instanceof TileEntityFooter) {
						TileEntityFooter te = (TileEntityFooter) tileentity;
						te.forceConnection = !te.forceConnection;
						event.world.markBlockForUpdate(event.x, event.y, event.z);
					}

					if (tileentity instanceof TileEntitySupport) {
						TileEntitySupport te = (TileEntitySupport) tileentity;
						te.flange = !te.flange;
						event.world.markBlockForUpdate(event.x, event.y, event.z);
					}
				}
			}
	};
	
	public ItemHammer() {
		setMaxStackSize(1);
		setMaxDamage(100);
	}

	@SideOnly(Side.CLIENT)
	public boolean isFull3D() {
		return true;
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
	
	@SuppressWarnings({"unchecked", "rawtypes"})
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean val) {
		if (stack.stackTagCompound == null) {
			stack.stackTagCompound = new NBTTagCompound();
			stack.stackTagCompound.setInteger("mode", 0);
		}

		list.add(modes[stack.stackTagCompound.getInteger("mode")].name + " Mode");
	}
}
