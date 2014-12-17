package robomuss.rc.event;

import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import robomuss.rc.RCMod;
import robomuss.rc.block.BlockFooter;
import robomuss.rc.block.BlockSupport;
import robomuss.rc.block.BlockTrackBase;
import robomuss.rc.block.RCBlocks;
import robomuss.rc.block.te.TileEntityFooter;
import robomuss.rc.block.te.TileEntitySupport;
import robomuss.rc.block.te.TileEntityTrackBase;
import robomuss.rc.item.ItemHammer;
import robomuss.rc.item.RCItems;
import robomuss.rc.network.NetworkHandler;
import robomuss.rc.util.ColourUtil;
import robomuss.rc.util.IPaintable;

public class BlockClickedEvent extends Event {
	private static boolean hasClickedTrackBlock = false;
	private static boolean hasClickedSupportBlock = false;
	private static boolean hasClickedFooterBlock = false;

	@SubscribeEvent
	public void onBlockClicked(PlayerInteractEvent event) {
		/* Replace current item with a brush with the color of the block that was clicked if sneaking and if current item is an empty brush or nothing at all */
		if (event.world.isRemote) {
			if (event.entityPlayer.capabilities.isCreativeMode) {
				if (event.action == PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK) {
					if (event.entityPlayer.worldObj.getBlockState(event.pos).getBlock() instanceof IPaintable) {
						hasClickedSupportBlock = (event.world.getBlockState(event.pos).getBlock() instanceof BlockSupport);
						hasClickedFooterBlock  = (event.world.getBlockState(event.pos).getBlock() instanceof BlockFooter);
						hasClickedTrackBlock   = (event.world.getBlockState(event.pos).getBlock() instanceof BlockTrackBase);

						if (event.entityPlayer.isSneaking()) {
							if (event.entityPlayer.getHeldItem() == null || event.entityPlayer.getHeldItem().getItem() == RCItems.empty_brush) {
								int meta = ((IPaintable) event.entityPlayer.worldObj.getBlockState(event.pos).getBlock()).getPaintMeta(event.entityPlayer.worldObj, event.pos);
								NetworkHandler.changePaintColour(meta);
							} else if (event.entityPlayer.getHeldItem().getItem() == RCItems.brush) {
								if (hasClickedFooterBlock) {
									RCMod.supportManager.paintFooter((TileEntityFooter) event.world.getTileEntity(event.pos), ColourUtil.COLORS[event.entityPlayer.getHeldItem().getItemDamage()]);
								} else if (hasClickedSupportBlock) {
									RCMod.supportManager.paintSupport((TileEntitySupport) event.world.getTileEntity(event.pos), ColourUtil.COLORS[event.entityPlayer.getHeldItem().getItemDamage()]);
								} else if (hasClickedTrackBlock) {
									((TileEntityTrackBase) event.world.getTileEntity(event.pos)).colour = ColourUtil.COLORS[event.entityPlayer.getHeldItem().getItemDamage()].ordinal();
									NetworkHandler.changePaintColour(((TileEntityTrackBase) event.world.getTileEntity(event.pos)).colour);
								}
							} else if (event.entityPlayer.getHeldItem().getItem() == Items.water_bucket) {
								if (hasClickedFooterBlock) {
									RCMod.supportManager.paintFooter((TileEntityFooter) event.world.getTileEntity(event.pos), ColourUtil.WHITE);
								} else if (hasClickedSupportBlock) {
									RCMod.supportManager.paintSupport((TileEntitySupport) event.world.getTileEntity(event.pos), ColourUtil.WHITE);
								} else if (hasClickedTrackBlock) {
									((TileEntityTrackBase) event.world.getTileEntity(event.pos)).colour = ColourUtil.WHITE.ordinal();
									NetworkHandler.changePaintColour(ColourUtil.WHITE.ordinal());
								}
							}
						} else {
							if (event.entityPlayer.getHeldItem() != null) {
								if (event.entityPlayer.getHeldItem().getItem() == RCItems.brush) {
									if (hasClickedFooterBlock) {
										RCMod.supportManager.paintSupportStack((TileEntityFooter) event.world.getTileEntity(event.pos), ColourUtil.COLORS[event.entityPlayer.getHeldItem().getItemDamage()]);
									} else if (hasClickedSupportBlock) {
										RCMod.supportManager.paintSupportStack((TileEntitySupport) event.world.getTileEntity(event.pos), ColourUtil.COLORS[event.entityPlayer.getHeldItem().getItemDamage()]);
									} else if (hasClickedTrackBlock) {
										((TileEntityTrackBase) event.world.getTileEntity(event.pos)).colour = ColourUtil.COLORS[event.entityPlayer.getHeldItem().getItemDamage()].ordinal();
										NetworkHandler.changePaintColour(ColourUtil.COLORS[event.entityPlayer.getHeldItem().getItemDamage()].ordinal());
									}
								} else if (event.entityPlayer.getHeldItem().getItem() == Items.water_bucket) {
									if (hasClickedFooterBlock) {
										RCMod.supportManager.paintSupportStack((TileEntityFooter) event.world.getTileEntity(event.pos), ColourUtil.COLORS[event.entityPlayer.getHeldItem().getItemDamage()]);
									} else if (hasClickedSupportBlock) {
										RCMod.supportManager.paintSupportStack((TileEntitySupport) event.world.getTileEntity(event.pos), ColourUtil.COLORS[event.entityPlayer.getHeldItem().getItemDamage()]);
									} else if (hasClickedTrackBlock) {
										((TileEntityFooter) event.world.getTileEntity(event.pos)).colour = ColourUtil.WHITE.ordinal();
										NetworkHandler.changePaintColour(ColourUtil.WHITE.ordinal());
										event.setCanceled(true);
									}
								}
							}
						}
					}
				} else if (event.action == PlayerInteractEvent.Action.RIGHT_CLICK_AIR) {
					if (event.world.getBlockState(event.pos).getBlock() instanceof IPaintable) {
						hasClickedSupportBlock = (event.world.getBlockState(event.pos).getBlock() instanceof BlockSupport);
						hasClickedFooterBlock  = (event.world.getBlockState(event.pos).getBlock() instanceof BlockFooter);
						hasClickedTrackBlock   = (event.world.getBlockState(event.pos).getBlock() instanceof BlockTrackBase);

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
					hasClickedTrackBlock = (event.world.getBlockState(event.pos).getBlock() instanceof BlockTrackBase);

					if (event.entityPlayer.getHeldItem().getItem() == RCItems.hammer) {
						TileEntity tileEntity = event.world.getTileEntity(event.pos);

						if (event.entityPlayer.getHeldItem().getTagCompound() == null) {
							event.entityPlayer.getHeldItem().setTagCompound(new NBTTagCompound());
							event.entityPlayer.getHeldItem().getTagCompound().setInteger("mode", 0);
						}

						ItemHammer.modes[event.entityPlayer.getHeldItem().getTagCompound().getInteger("mode")].onRightClick(tileEntity, event);
					} else if (event.entityPlayer.getHeldItem().getItem() == Item.getItemFromBlock(RCBlocks.support)) {
						if (hasClickedSupportBlock) {
							TileEntitySupport teSupport = (TileEntitySupport) event.world.getTileEntity(event.pos);

							if (teSupport != null) {
								RCMod.supportManager.addSupportToStack(RCMod.supportManager.getFooterFromSupport(teSupport));
							}
						} else if (hasClickedFooterBlock) {
							TileEntityFooter teFooter = (TileEntityFooter) event.world.getTileEntity(event.pos);

							if (teFooter != null) {
								RCMod.supportManager.addSupportToStack(teFooter);
							}
						}
					} else if (event.entityPlayer.getHeldItem().getItem() == Item.getItemFromBlock(RCBlocks.footer)) {
						BlockPos footerPos = new BlockPos(event.pos).offset(event.face);
						RCMod.supportManager.placeFooter(event.world, footerPos);
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
							if (event.entityPlayer.getHeldItem().getTagCompound() == null) {
								event.entityPlayer.getHeldItem().setTagCompound(new NBTTagCompound());
								event.entityPlayer.getHeldItem().getTagCompound().setInteger("mode", 0);
							}

							int mode = event.entityPlayer.getHeldItem().getTagCompound().getInteger("mode");
							int newMode = mode + 1 < ItemHammer.modes.length ? mode + 1 : 0;
							event.entityPlayer.getHeldItem().getTagCompound().setInteger("mode", newMode);
						}
					}
				}
			}
		}
	}
}
