package robomuss.rc.item;

<<<<<<< HEAD
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
=======
import java.util.List;

>>>>>>> origin/One8PortTake2
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
<<<<<<< HEAD
import robomuss.rc.block.BlockConveyor;
import robomuss.rc.block.BlockRideFence;
import robomuss.rc.block.BlockWoodenSupport;
import robomuss.rc.block.te.*;
import robomuss.rc.track.TrackHandler;
import robomuss.rc.util.hammer.HammerMode;

import java.util.List;
=======
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import robomuss.rc.block.te.TileEntityConveyor;
import robomuss.rc.block.te.TileEntityFooter;
import robomuss.rc.block.te.TileEntityRideFence;
import robomuss.rc.block.te.TileEntitySupport;
import robomuss.rc.block.te.TileEntityTrackBase;
import robomuss.rc.block.te.TileEntityWoodenSupport;
import robomuss.rc.track.TrackHandler;
import robomuss.rc.util.hammer.HammerMode;

//import robomuss.rc.block.BlockTrack;
>>>>>>> origin/One8PortTake2

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
					IBlockState state = event.world.getBlockState(event.pos);
					EnumFacing facing = (EnumFacing) state.getValue(BlockRideFence.FACING);
					event.world.setBlockState(event.pos, state.withProperty(BlockRideFence.FACING, facing.rotateAround(facing.getAxis())));
					event.world.markBlockForUpdate(event.pos);
				}

				if (tileentity instanceof TileEntityWoodenSupport) {
					IBlockState state = event.world.getBlockState(event.pos);
					EnumFacing facing = (EnumFacing) state.getValue(BlockWoodenSupport.FACING);
					event.world.setBlockState(event.pos, state.withProperty(BlockWoodenSupport.FACING, facing.rotateAround(facing.getAxis())));
					event.world.markBlockForUpdate(event.pos);
				}

				if (tileentity instanceof TileEntityConveyor) {
					IBlockState state = event.world.getBlockState(event.pos);
					EnumFacing facing = (EnumFacing) state.getValue(BlockConveyor.FACING);
					event.world.setBlockState(event.pos, state.withProperty(BlockConveyor.FACING, facing.rotateAround(facing.getAxis())));
					event.world.markBlockForUpdate(event.pos);
				}
			}
		}, new HammerMode("Change Style") {
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

				event.world.markBlockForUpdate(event.pos);
			}
		}
	}, new HammerMode("Adjustment") {
		@Override
		public void onRightClick(TileEntity tileentity, PlayerInteractEvent event) {
			if (tileentity instanceof TileEntityFooter) {
				TileEntityFooter te = (TileEntityFooter) tileentity;
				te.forceConnection = !te.forceConnection;
				event.world.markBlockForUpdate(event.pos);
			}

			if (tileentity instanceof TileEntitySupport) {
				TileEntitySupport te = (TileEntitySupport) tileentity;
				te.flange = !te.flange;
				event.world.markBlockForUpdate(event.pos);
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
		stack.setTagCompound(new NBTTagCompound());
		stack.getTagCompound().setInteger("mode", 0);
	}
	
	@SuppressWarnings({"unchecked", "rawtypes"})
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean val) {
		if (stack.getTagCompound() == null) {
			stack.setTagCompound(new NBTTagCompound());
			stack.getTagCompound().setInteger("mode", 0);
		}

		list.add(modes[stack.getTagCompound().getInteger("mode")].name + " Mode");
	}
}
