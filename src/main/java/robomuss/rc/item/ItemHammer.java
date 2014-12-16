package robomuss.rc.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
<<<<<<< HEAD
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
=======
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
>>>>>>> master
import robomuss.rc.block.BlockTrack;
import robomuss.rc.block.te.TileEntityConveyor;
import robomuss.rc.block.te.TileEntityFooter;
import robomuss.rc.block.te.TileEntityRideFence;
import robomuss.rc.block.te.TileEntitySupport;
import robomuss.rc.block.te.TileEntityTrack;
import robomuss.rc.block.te.TileEntityWoodenSupport;
import robomuss.rc.track.TrackHandler;
import robomuss.rc.track.piece.TrackPieceSlopeUp;
import robomuss.rc.util.hammer.HammerMode;

import java.util.List;

public class ItemHammer extends Item {

	public static HammerMode[] modes = {
		new HammerMode("Rotate") {
			@Override
			public void onRightClick(TileEntity tileentity, PlayerInteractEvent event) {
				if(tileentity instanceof TileEntityTrack) {
									 TileEntityTrack teTrack = (TileEntityTrack) tileentity;
									 if (teTrack.track instanceof BlockTrack) {
										 if (teTrack.hasSlope(teTrack.track)) {
											 if (teTrack.direction != ForgeDirection.EAST) {
												 teTrack.direction = ForgeDirection.VALID_DIRECTIONS[teTrack.direction.ordinal() + 1];
											 } else if (teTrack.direction == ForgeDirection.EAST) {
												 teTrack.direction = ForgeDirection.NORTH;
											 }
											 teTrack.track.updateRotation(event.world, teTrack.xCoord, teTrack.yCoord, teTrack.zCoord);
										 } else if (teTrack.direction == ForgeDirection.EAST) {
											 teTrack.direction = ForgeDirection.NORTH;
										 } else {
											 teTrack.direction = ForgeDirection.VALID_DIRECTIONS[teTrack.direction.ordinal() + 1];
										 }
									 }
				 //					if (event.world.getBlock(tet.xCoord, tet.yCoord, tet.zCoord) instanceof BlockTrack) {
				 //						BlockTrack track = (BlockTrack) event.world.getBlock(tet.xCoord, tet.yCoord, tet.zCoord);
				 //						if (track.track_type instanceof TrackPieceSlopeUp) {
				 //							if (tet.direction != 3) {
				 //								tet.direction++;
				 //							} else if (tet.direction == 3) {
				 //								tet.direction = 0;
				 //							}
				 //							track.updateRotation(event.world, tet.xCoord, tet.yCoord, tet.zCoord, tet);
				 //						} else if(tet.direction == 3) {
				 //							tet.direction = 0;
				 //						} else {
				 //							tet.direction++;
				 //						}
				 //					}
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
			@Override
			public void onRightClick(TileEntity tileentity, PlayerInteractEvent event) {
				if(tileentity instanceof TileEntityTrack) {
					TileEntityTrack te = (TileEntityTrack) tileentity;
					int id = 0;
					for(int i = 0; i < TrackHandler.styles.size(); i++) {
						if(te.style != null && TrackHandler.styles.get(i).getId() == te.style.getId()) {
							id = i;
						}
					}
					if(id < TrackHandler.styles.size() - 1) {
						te.style = TrackHandler.styles.get(id + 1);
					}
					else {
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
	public boolean hasContainerItem() {
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
