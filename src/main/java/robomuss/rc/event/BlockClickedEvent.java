package robomuss.rc.event;

import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import robomuss.rc.RCMod;
import robomuss.rc.block.*;
import robomuss.rc.block.te.TileEntityFooter;
import robomuss.rc.block.te.TileEntitySupport;
import robomuss.rc.block.te.TileEntityTrackBase;
import robomuss.rc.block.te.TileEntityTrackFabricator;
import robomuss.rc.item.ItemExtra;
import robomuss.rc.item.ItemHammer;
import robomuss.rc.item.RCItems;
import robomuss.rc.multiblock.MultiBlockManager;
import robomuss.rc.multiblock.MultiBlockTrackFabricator;
import robomuss.rc.network.NetworkHandler;
import robomuss.rc.util.ColourUtil;
import robomuss.rc.util.IPaintable;

public class BlockClickedEvent extends Event {
	private static boolean hasClickedTrackBlock = false;
	private static boolean hasClickedSupportBlock = false;
	private static boolean hasClickedFooterBlock = false;
	private static boolean hasClickedFabricatorBlock = false;

	@SubscribeEvent
	public void onBlockClicked(PlayerInteractEvent event) {
		/* Replace current item with a brush with the color of the camouflage that was clicked if sneaking and if current item is an empty brush or nothing at all */
		if (event.world.isRemote) {
			if (event.entityPlayer.capabilities.isCreativeMode) {
				if (event.action == PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK) {
					if (event.entityPlayer.worldObj.getBlock(event.x, event.y, event.z) instanceof IPaintable) {
						hasClickedSupportBlock = (event.world.getBlock(event.x, event.y, event.z) instanceof BlockSupport);
						hasClickedFooterBlock  = (event.world.getBlock(event.x, event.y, event.z) instanceof BlockFooter);
						hasClickedTrackBlock = (event.world.getBlock(event.x, event.y, event.z) instanceof BlockTrackBase);

						if (event.entityPlayer.isSneaking()) {
							if (event.entityPlayer.getHeldItem() == null || event.entityPlayer.getHeldItem().getItem() == RCItems.empty_brush) {
								int meta = ((IPaintable) event.entityPlayer.worldObj.getBlock(event.x, event.y, event.z)).getPaintMeta(event.entityPlayer.worldObj, event.x, event.y, event.z);
								NetworkHandler.changePaintColour(meta);
							} else if (event.entityPlayer.getHeldItem().getItem() == RCItems.brush) {
								if (hasClickedFooterBlock) {
									RCMod.supportManager.paintFooter((TileEntityFooter) event.world.getTileEntity(event.x, event.y, event.z), ColourUtil.COLORS[event.entityPlayer.getHeldItem().getItemDamage()]);
								} else if (hasClickedSupportBlock) {
									RCMod.supportManager.paintSupport((TileEntitySupport) event.world.getTileEntity(event.x, event.y, event.z), ColourUtil.COLORS[event.entityPlayer.getHeldItem().getItemDamage()]);
								} else if (hasClickedTrackBlock) {
									((TileEntityTrackBase) event.world.getTileEntity(event.x, event.y, event.z)).colour = ColourUtil.COLORS[event.entityPlayer.getHeldItem().getItemDamage()].ordinal();
									NetworkHandler.changePaintColour(((TileEntityTrackBase) event.world.getTileEntity(event.x, event.y, event.z)).colour);
								}
							} else if (event.entityPlayer.getHeldItem().getItem() == Items.water_bucket) {
								if (hasClickedFooterBlock) {
									RCMod.supportManager.paintFooter((TileEntityFooter) event.world.getTileEntity(event.x, event.y, event.z), ColourUtil.WHITE);
								} else if (hasClickedSupportBlock) {
									RCMod.supportManager.paintSupport((TileEntitySupport) event.world.getTileEntity(event.x, event.y, event.z), ColourUtil.WHITE);
								} else if (hasClickedTrackBlock) {
									((TileEntityTrackBase) event.world.getTileEntity(event.x, event.y, event.z)).colour = ColourUtil.WHITE.ordinal();
									NetworkHandler.changePaintColour(ColourUtil.WHITE.ordinal());
								}
							}
						} else {
							if (event.entityPlayer.getHeldItem() != null) {
								if (event.entityPlayer.getHeldItem().getItem() == RCItems.brush) {
									if (hasClickedFooterBlock) {
										RCMod.supportManager.paintSupportStack((TileEntityFooter) event.world.getTileEntity(event.x, event.y, event.z), ColourUtil.COLORS[event.entityPlayer.getHeldItem().getItemDamage()]);
									} else if (hasClickedSupportBlock) {
										RCMod.supportManager.paintSupportStack((TileEntitySupport) event.world.getTileEntity(event.x, event.y, event.z), ColourUtil.COLORS[event.entityPlayer.getHeldItem().getItemDamage()]);
									} else if (hasClickedTrackBlock) {
										((TileEntityTrackBase) event.world.getTileEntity(event.x, event.y, event.z)).colour = ColourUtil.COLORS[event.entityPlayer.getHeldItem().getItemDamage()].ordinal();
										NetworkHandler.changePaintColour(ColourUtil.COLORS[event.entityPlayer.getHeldItem().getItemDamage()].ordinal());
									}
								} else if (event.entityPlayer.getHeldItem().getItem() == Items.water_bucket) {
									if (hasClickedFooterBlock) {
										RCMod.supportManager.paintSupportStack((TileEntityFooter) event.world.getTileEntity(event.x, event.y, event.z), ColourUtil.COLORS[event.entityPlayer.getHeldItem().getItemDamage()]);
									} else if (hasClickedSupportBlock) {
										RCMod.supportManager.paintSupportStack((TileEntitySupport) event.world.getTileEntity(event.x, event.y, event.z), ColourUtil.COLORS[event.entityPlayer.getHeldItem().getItemDamage()]);
									} else if (hasClickedTrackBlock) {
										((TileEntityFooter) event.world.getTileEntity(event.x, event.y, event.z)).colour = ColourUtil.WHITE.ordinal();
										NetworkHandler.changePaintColour(ColourUtil.WHITE.ordinal());
										event.setCanceled(true);
									}
								}
							}
						}
					}
				} else if (event.action == PlayerInteractEvent.Action.RIGHT_CLICK_AIR) {    //this is here to cancel the placement of water because it somehow fires this as well...
					if (event.world.getBlock(event.x, event.y, event.z) instanceof IPaintable) {
						if (event.entityPlayer.getHeldItem() != null) {
							if (event.entityPlayer.getHeldItem().getItem() == Items.water_bucket) {
								event.setCanceled(true);
							}
						}
					}
				}
			}
		} else { /* !event.world.isRemote */
			if (event.action == PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK) {
				if (event.entityPlayer.getHeldItem() != null) {
					hasClickedTrackBlock = (event.world.getBlock(event.x, event.y, event.z) instanceof BlockTrackBase);
					hasClickedFabricatorBlock = (event.world.getBlock(event.x, event.y, event.z) instanceof BlockTrackFabricator);

					if (event.entityPlayer.getHeldItem().getItem() == RCItems.hammer) {
						TileEntity tileEntity = event.world.getTileEntity(event.x, event.y, event.z);

						if (event.entityPlayer.getHeldItem().stackTagCompound == null) {
							event.entityPlayer.getHeldItem().stackTagCompound = new NBTTagCompound();
							event.entityPlayer.getHeldItem().stackTagCompound.setInteger("mode", 0);
						}

						ItemHammer.modes[event.entityPlayer.getHeldItem().stackTagCompound.getInteger("mode")].onRightClick(tileEntity, event);
					} else if (event.entityPlayer.getHeldItem().getItem() == Item.getItemFromBlock(RCBlocks.support)) {
						if (hasClickedSupportBlock) {
							TileEntitySupport teSupport = (TileEntitySupport) event.world.getTileEntity(event.x, event.y, event.z);

							if (teSupport != null) {
								RCMod.supportManager.addSupportToStack(RCMod.supportManager.getFooterFromSupport(teSupport));
							}
						} else if (hasClickedFooterBlock) {
							TileEntityFooter teFooter = (TileEntityFooter) event.world.getTileEntity(event.x, event.y, event.z);

							if (teFooter != null) {
								RCMod.supportManager.addSupportToStack(teFooter);
							}
						}
					} else if (event.entityPlayer.getHeldItem().getItem() == Item.getItemFromBlock(RCBlocks.footer)) {
						ForgeDirection direction = ForgeDirection.getOrientation(event.face);
						int[] footerLoc = new int[] {event.x + direction.offsetX, event.y + direction.offsetY, event.z + direction.offsetZ};
						RCMod.supportManager.placeFooter(event.world, footerLoc[0], footerLoc[1], footerLoc[2]);
					} else if (event.entityPlayer.getHeldItem().getItem() == Items.water_bucket) {
						if (hasClickedTrackBlock) {
							event.setCanceled(true);
						}
					}
				}
			} else if (event.action == PlayerInteractEvent.Action.RIGHT_CLICK_AIR) {
				if (event.entityPlayer.getHeldItem() != null) {
					if (event.entityPlayer.getHeldItem().getItem() == Items.water_bucket) {
						event.setCanceled(true);
					}
				}
			}

			if (!hasClickedTrackBlock) {
				if (event.action == PlayerInteractEvent.Action.RIGHT_CLICK_AIR) {
					if (event.entityPlayer.isSneaking()) {
						if (event.entityPlayer.getHeldItem() != null && event.entityPlayer.getHeldItem().getItem() == RCItems.hammer) {
							if (event.entityPlayer.getHeldItem().stackTagCompound == null) {
								event.entityPlayer.getHeldItem().stackTagCompound = new NBTTagCompound();
								event.entityPlayer.getHeldItem().stackTagCompound.setInteger("mode", 0);
							}

							int mode = event.entityPlayer.getHeldItem().stackTagCompound.getInteger("mode");
							int newMode = mode + 1 < ItemHammer.modes.length ? mode + 1 : 0;
							event.entityPlayer.getHeldItem().stackTagCompound.setInteger("mode", newMode);
						}
					}
				}
			}

			if (hasClickedFabricatorBlock) {
				if (event.entityPlayer.capabilities.isCreativeMode) {                               //TODO: allow this in survival if player has enough blocks to build the structure?
					if (event.action == PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK) {
						BlockTrackFabricator blockTrackFabricator = (BlockTrackFabricator) event.world.getBlock(event.x, event.y, event.z);
						TileEntityTrackFabricator teFab = (TileEntityTrackFabricator) event.world.getTileEntity(event.x, event.y, event.z);
						MultiBlockTrackFabricator struct = (MultiBlockTrackFabricator) MultiBlockManager.getInstance().getStructure(blockTrackFabricator.getUnlocalizedName());

						if (!struct.isStructureFormed(0, teFab)) {
							struct.placeTemplateBlocks(0, event.world, event.x, event.y, event.z, teFab.direction, struct);
						}
					}
				}
			}
		}
	}
}
