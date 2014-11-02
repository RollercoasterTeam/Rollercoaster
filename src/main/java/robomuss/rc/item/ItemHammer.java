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
import net.minecraftforge.common.util.BlockSnapshot;
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
import net.minecraft.block.Block;

public class ItemHammer extends Item {

	public static HammerMode[] modes = {
		new HammerMode("Rotate") {
			@Override
			public void onRightClick(TileEntity tileentity, PlayerInteractEvent event) {
				if (tileentity instanceof TileEntityTrackBase) {
					((TileEntityTrackBase) tileentity).rotate();
//					TileEntityTrackBase teTrack = (TileEntityTrackBase) tileentity;
//					int currentFacing = teTrack.getWorldObj().getBlockMetadata(teTrack.xCoord, teTrack.yCoord, teTrack.zCoord);
//					BlockSnapshot snapshot;

//					if (!teTrack.isDummy) {
//						switch (currentFacing) {
//							case 2:
//								Block blockEast = teTrack.getWorldObj().getBlock(teTrack.xCoord + 1, teTrack.yCoord, teTrack.zCoord);
//								int metaEast = teTrack.getWorldObj().getBlockMetadata(teTrack.xCoord + 1, teTrack.yCoord, teTrack.zCoord);
//								snapshot = new BlockSnapshot(teTrack.getWorldObj(), teTrack.xCoord + 1, teTrack.yCoord, teTrack.zCoord, blockEast, metaEast);
//
//								if (!snapshot.world.isAirBlock(snapshot.x + 1, snapshot.y, snapshot.z)) {
//
//								}
//								teTrack.getWorldObj().setBlockMetadataWithNotify(teTrack.xCoord, teTrack.yCoord, teTrack.zCoord, 5, 2);
//								teTrack.getWorldObj().setBlockToAir(teTrack.xCoord, teTrack.yCoord, teTrack.zCoord - 1);
//								teTrack.getWorldObj().setBlock(teTrack.xCoord + 1, teTrack.yCoord, teTrack.zCoord, teTrack.track.track_type.block);
//								teTrack.getWorldObj().setBlockMetadataWithNotify(teTrack.xCoord + 1, teTrack.yCoord, teTrack.zCoord, 5, 2);
//								((TileEntityTrackBase) teTrack.getWorldObj().getTileEntity(teTrack.xCoord + 1, teTrack.yCoord, teTrack.zCoord)).isDummy = true;
//								break;
//							case 3:
//								teTrack.getWorldObj().setBlockMetadataWithNotify(teTrack.xCoord, teTrack.yCoord, teTrack.zCoord, 4, 2);
//								teTrack.getWorldObj().setBlockToAir(teTrack.xCoord, teTrack.yCoord, teTrack.zCoord + 1);
//								teTrack.getWorldObj().setBlock(teTrack.xCoord - 1, teTrack.yCoord, teTrack.zCoord, teTrack.track.track_type.block);
//								teTrack.getWorldObj().setBlockMetadataWithNotify(teTrack.xCoord - 1, teTrack.yCoord, teTrack.zCoord, 4, 2);
//								((TileEntityTrackBase) teTrack.getWorldObj().getTileEntity(teTrack.xCoord - 1, teTrack.yCoord, teTrack.zCoord)).isDummy = true;
//								break;
//							case 4:
//								teTrack.getWorldObj().setBlockMetadataWithNotify(teTrack.xCoord, teTrack.yCoord, teTrack.zCoord, 2, 2);
//								teTrack.getWorldObj().setBlockToAir(teTrack.xCoord - 1, teTrack.yCoord, teTrack.zCoord);
//								teTrack.getWorldObj().setBlock(teTrack.xCoord, teTrack.yCoord, teTrack.zCoord - 1, teTrack.track.track_type.block);
//								teTrack.getWorldObj().setBlockMetadataWithNotify(teTrack.xCoord, teTrack.yCoord, teTrack.zCoord - 1, 2, 2);
//								((TileEntityTrackBase) teTrack.getWorldObj().getTileEntity(teTrack.xCoord, teTrack.yCoord, teTrack.zCoord - 1)).isDummy = true;
//								break;
//							case 5:
//								teTrack.getWorldObj().setBlockMetadataWithNotify(teTrack.xCoord, teTrack.yCoord, teTrack.zCoord, 3, 2);
//								teTrack.getWorldObj().setBlockToAir(teTrack.xCoord + 1, teTrack.yCoord, teTrack.zCoord);
//								teTrack.getWorldObj().setBlock(teTrack.xCoord, teTrack.yCoord, teTrack.zCoord + 1, teTrack.track.track_type.block);
//								teTrack.getWorldObj().setBlockMetadataWithNotify(teTrack.xCoord, teTrack.yCoord, teTrack.zCoord + 1, 3, 2);
//								((TileEntityTrackBase) teTrack.getWorldObj().getTileEntity(teTrack.xCoord, teTrack.yCoord, teTrack.zCoord + 1)).isDummy = true;
//								break;
//						}
//					} else {
//						switch (currentFacing) {
//							case 2:
//								teTrack.getWorldObj().setBlockMetadataWithNotify(teTrack.xCoord, teTrack.yCoord, teTrack.zCoord, 5, 2);
//								teTrack.getWorldObj().setBlockToAir(teTrack.xCoord, teTrack.yCoord, teTrack.zCoord + 1);
//								teTrack.getWorldObj().setBlock(teTrack.xCoord - 1, teTrack.yCoord, teTrack.zCoord, teTrack.track.track_type.block);
//								teTrack.getWorldObj().setBlockMetadataWithNotify(teTrack.xCoord - 1, teTrack.yCoord, teTrack.zCoord, 5, 2);
//								break;
//							case 3:
//								teTrack.getWorldObj().setBlockMetadataWithNotify(teTrack.xCoord, teTrack.yCoord, teTrack.zCoord, 4, 2);
//								teTrack.getWorldObj().setBlockToAir(teTrack.xCoord, teTrack.yCoord, teTrack.zCoord - 1);
//								teTrack.getWorldObj().setBlock(teTrack.xCoord + 1, teTrack.yCoord, teTrack.zCoord, teTrack.track.track_type.block);
//								teTrack.getWorldObj().setBlockMetadataWithNotify(teTrack.xCoord + 1, teTrack.yCoord, teTrack.zCoord, 4, 2);
//								break;
//							case 4:
//								teTrack.getWorldObj().setBlockMetadataWithNotify(teTrack.xCoord, teTrack.yCoord, teTrack.zCoord, 2, 2);
//								teTrack.getWorldObj().setBlockToAir(teTrack.xCoord + 1, teTrack.yCoord, teTrack.zCoord);
//								teTrack.getWorldObj().setBlock(teTrack.xCoord, teTrack.yCoord, teTrack.zCoord + 1, teTrack.track.track_type.block);
//								teTrack.getWorldObj().setBlockMetadataWithNotify(teTrack.xCoord, teTrack.yCoord, teTrack.zCoord + 1, 2, 2);
//								break;
//							case 5:
//								teTrack.getWorldObj().setBlockMetadataWithNotify(teTrack.xCoord, teTrack.yCoord, teTrack.zCoord, 3, 2);
//								teTrack.getWorldObj().setBlockToAir(teTrack.xCoord - 1, teTrack.yCoord, teTrack.zCoord);
//								teTrack.getWorldObj().setBlock(teTrack.xCoord, teTrack.yCoord, teTrack.zCoord - 1, teTrack.track.track_type.block);
//								teTrack.getWorldObj().setBlockMetadataWithNotify(teTrack.xCoord, teTrack.yCoord, teTrack.zCoord - 1, 3, 2);
//								break;
//						}
//					}
				}
				if(tileentity instanceof TileEntityRideFence) {
					TileEntityRideFence terf = (TileEntityRideFence) event.world.getTileEntity(event.x, event.y, event.z);
					if(terf.direction == 3) {
						terf.direction = 0;
					} else {
						terf.direction++;
					}
					event.world.markBlockForUpdate(event.x, event.y, event.z);
				}
				if(tileentity instanceof TileEntityWoodenSupport) {
					TileEntityWoodenSupport tews = (TileEntityWoodenSupport) event.world.getTileEntity(event.x, event.y, event.z);
					if(tews.direction == 3) {
						tews.direction = 0;
					} else {
						tews.direction++;
					}
					event.world.markBlockForUpdate(event.x, event.y, event.z);
				}
				if(tileentity instanceof TileEntityConveyor) {
					TileEntityConveyor tec = (TileEntityConveyor) event.world.getTileEntity(event.x, event.y, event.z);
					if(tec.direction == 3) {
						tec.direction = 0;
					} else {
						tec.direction++;
					}
					event.world.markBlockForUpdate(event.x, event.y, event.z);
				}
			}
		},
		new HammerMode("Change Style") {
			@Override
			public void onRightClick(TileEntity tileentity, PlayerInteractEvent event) {
				if(tileentity instanceof TileEntityTrackBase) {
					TileEntityTrackBase te = (TileEntityTrackBase) tileentity;
					int id = 0;
					for(int i = 0; i < TrackHandler.styles.size(); i++) {
						if(te.style != null && TrackHandler.styles.get(i).getId() == te.style.getId()) {
							id = i;
						}
					}
					if(id < TrackHandler.styles.size() - 1) {
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
