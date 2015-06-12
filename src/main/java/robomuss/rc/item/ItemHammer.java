package robomuss.rc.item;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import robomuss.rc.block.te.TileEntityFooter;
import robomuss.rc.block.te.TileEntityCatwalk;
import robomuss.rc.block.te.TileEntityRideFence;
import robomuss.rc.block.te.TileEntityRideSign;
import robomuss.rc.block.te.TileEntitySupport;
import robomuss.rc.block.te.TileEntityTrack;
import robomuss.rc.block.te.TileEntityWoodenSupport;
import robomuss.rc.track.TrackHandler;
import robomuss.rc.util.hammer.HammerMode;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemHammer extends Item {

	public static HammerMode[] modes = {
		new HammerMode("Rotate") {
			@Override
			public void onRightClick(TileEntity tileentity, PlayerInteractEvent event) {
				if(tileentity instanceof TileEntityTrack) {
					TileEntityTrack tet = (TileEntityTrack) tileentity;
					if(tet.direction == 3) {
						tet.direction = 0;
					}
					else {
						tet.direction++;
					}
					event.world.markBlockForUpdate(event.x, event.y, event.z);
				}
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
				if(tileentity instanceof TileEntityCatwalk) {
					TileEntityCatwalk terf = (TileEntityCatwalk) event.world.getTileEntity(event.x, event.y, event.z);
					if(terf.direction == 3) {
						terf.direction = 0;
					}
					else {
						terf.direction++;
					}
					event.world.markBlockForUpdate(event.x, event.y, event.z);
				}
				if(tileentity instanceof TileEntityRideSign) {
					TileEntityRideSign ters = (TileEntityRideSign) event.world.getTileEntity(event.x, event.y, event.z);
					if(ters.direction == 3) {
						ters.direction = 0;
					}
					else {
						ters.direction++;
					}
					event.world.markBlockForUpdate(event.x, event.y, event.z);
				}
			}
		},
		new HammerMode("Change Type") {
			@Override
			public void onRightClick(TileEntity tileentity, PlayerInteractEvent event) {
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
			}
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
