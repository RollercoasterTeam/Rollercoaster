package robomuss.rc.track;

import net.minecraft.world.World;
import robomuss.rc.block.BlockSupport;
import robomuss.rc.block.RCBlocks;
import robomuss.rc.block.te.TileEntityFooter;
import robomuss.rc.block.te.TileEntitySupport;
import robomuss.rc.network.NetworkHandler;
import robomuss.rc.util.ColourUtil;

import java.util.ArrayList;
import java.util.List;

public class SupportManager {
	private List<TileEntityFooter> footers = new ArrayList<TileEntityFooter>();

	public SupportManager() {}

	public int getFooterIndex(TileEntityFooter footer) {
		if (footer != null && this.footers.contains(footer)) {
			return this.footers.indexOf(footer);
		}

		return -1;
	}

	public int getSupportIndex(TileEntitySupport support) {
		if (support != null && this.footers.contains(getFooterFromSupport(support))) {
			return getSupportStack(getFooterFromSupport(support)).indexOf(support);
		}

		return -1;
	}

	public TileEntityFooter getFooter(int index) {
		return index >= 0 && index < this.footers.size() ? this.footers.get(index) : null;
	}

	public void addFooter(TileEntityFooter footer) {
		this.footers.add(footer);
	}

	private void removeFooter(TileEntityFooter footer) {
		int index = getFooterIndex(footer);

		if (index != -1) {
			removeSupportFromStack(footer, -1);
			this.footers.remove(footer);
		}
	}

	public void placeFooter(World world, int x, int y, int z) {
		if (world.isAirBlock(x, y, z)) {
			world.setBlock(x, y, z, RCBlocks.footer);
			this.addFooter((TileEntityFooter) world.getTileEntity(x, y, z));
		}
	}

	public void breakFooter(World world, int x, int y, int z) {
		if (world.getTileEntity(x, y, z) instanceof TileEntityFooter) {
			this.removeFooter((TileEntityFooter) world.getTileEntity(x, y, z));
			world.setBlockToAir(x, y, z);
		}
	}

	public List<TileEntitySupport> getSupportStack(int index) {
		return this.getSupportStack(getFooter(index));
	}

	public List<TileEntitySupport> getSupportStack(TileEntityFooter footer) {
		List<TileEntitySupport> supports = new ArrayList<TileEntitySupport>();

		if (getFooterIndex(footer) != -1) {
			int currentX = footer.xCoord;
			int currentY = footer.yCoord;
			int currentZ = footer.zCoord;

			while (footer.getWorldObj().getTileEntity(currentX, currentY + 1, currentZ) instanceof TileEntitySupport) {
				supports.add((TileEntitySupport) footer.getWorldObj().getTileEntity(currentX, currentY + 1, currentZ));
				currentY++;
			}
		}

		return supports;
	}

	public TileEntityFooter getFooterFromSupport(TileEntitySupport support) {
		int currentX = support.xCoord;
		int currentY = support.yCoord;
		int currentZ = support.zCoord;
		TileEntityFooter footer = null;

		do {
			currentY--;

			if (support.getWorldObj().getTileEntity(currentX, currentY, currentZ) instanceof TileEntityFooter) {
				footer = (TileEntityFooter) support.getWorldObj().getTileEntity(currentX, currentY, currentZ);
				break;
			}

		} while (support.getWorldObj().getTileEntity(currentX, currentY, currentZ) instanceof TileEntitySupport);

		return footer;
	}

	public boolean addSupportToStack(int index) {
		return this.addSupportToStack(getFooter(index));
	}

	public boolean addSupportToStack(TileEntityFooter footer) {
		if (getFooterIndex(footer) != -1) {
			int currentX = footer.xCoord;
			int currentY = footer.yCoord;
			int currentZ = footer.zCoord;
			List<TileEntitySupport> supports = getSupportStack(footer);

			if (supports != null) {
				if (!supports.isEmpty()) {
					TileEntitySupport topSupport = supports.size() > 0 ? supports.get(supports.size() - 1) : supports.get(0);

					if (topSupport != null) {
						if (topSupport.getWorldObj().isAirBlock(topSupport.xCoord, topSupport.yCoord + 1, topSupport.zCoord)) {
							topSupport.getWorldObj().setBlock(topSupport.xCoord, topSupport.yCoord + 1, topSupport.zCoord, RCBlocks.support);
							TileEntitySupport teSupport = (TileEntitySupport) topSupport.getWorldObj().getTileEntity(topSupport.xCoord, topSupport.yCoord + 1, topSupport.zCoord);
							teSupport.colour = topSupport.colour;
							return true;
						} else if (topSupport.getWorldObj().getBlock(topSupport.xCoord, topSupport.yCoord + 1, topSupport.zCoord) instanceof BlockSupport) {
							TileEntitySupport teSupport = (TileEntitySupport) topSupport.getWorldObj().getTileEntity(topSupport.xCoord, topSupport.yCoord + 1, topSupport.zCoord);
							teSupport.colour = topSupport.colour;
							return true;
						}
					} else {
						if (footer.getWorldObj().isAirBlock(currentX, currentY + 1, currentZ)) {
							footer.getWorldObj().setBlock(currentX, currentY + 1, currentZ, RCBlocks.support);
							TileEntitySupport teSupport = (TileEntitySupport) footer.getWorldObj().getTileEntity(currentX, currentY + 1, currentZ);
							teSupport.colour = footer.colour;
							return true;
						} else if (footer.getWorldObj().getBlock(currentX, currentY + 1, currentZ) instanceof BlockSupport) {
							TileEntitySupport teSupport = (TileEntitySupport) footer.getWorldObj().getTileEntity(currentX, currentY + 1, currentZ);
							teSupport.colour = footer.colour;
							return true;
						}
					}
				} else { /* supports.isEmpty() */
					if (footer.getWorldObj().isAirBlock(currentX, currentY + 1, currentZ)) {
						footer.getWorldObj().setBlock(currentX, currentY + 1, currentZ, RCBlocks.support);
						TileEntitySupport teSupport = (TileEntitySupport) footer.getWorldObj().getTileEntity(currentX, currentY + 1, currentZ);
						teSupport.colour = footer.colour;
						supports.add(0, teSupport);
						return true;
					} else if (footer.getWorldObj().getBlock(currentX, currentY + 1, currentZ) instanceof BlockSupport) {
						TileEntitySupport teSupport = (TileEntitySupport) footer.getWorldObj().getTileEntity(currentX, currentY + 1, currentZ);
						supports.add(0, teSupport);
						return true;
					}
				}
			}
		}

		return false;
	}

	public boolean removeSupportFromStack(int footerIndex, int supportIndex) {
		return this.removeSupportFromStack(getFooter(footerIndex), supportIndex);
	}

	public boolean removeSupportFromStack(TileEntityFooter footer, int supportIndex) {
		if (getFooterIndex(footer) != -1) {
			if (supportIndex == -1) {
				int currentX = footer.xCoord;
				int currentY = footer.yCoord;
				int currentZ = footer.zCoord;
				List<TileEntitySupport> supports = getSupportStack(footer);

				if (supports != null) {
					if (!supports.isEmpty()) {
						int topSupportY = supports.size() > 0 ? supports.get(supports.size() - 1).yCoord : supports.get(0).yCoord;

						while (currentY < topSupportY) {
							currentY++;

							if (!footer.getWorldObj().isAirBlock(currentX, currentY, currentZ) && (footer.getWorldObj().getBlock(currentX, currentY, currentZ) instanceof BlockSupport)) {
								footer.getWorldObj().setBlockToAir(currentX, currentY, currentZ);
							}
						}

						return true;
					}
				}
			} else {
				List<TileEntitySupport> supports = getSupportStack(footer);

				if (supports != null) {
					if (!supports.isEmpty()) {
						if (supportIndex >= 0 && supportIndex < supports.size()) {
							if (supports.get(supportIndex) != null) {
								int currentX = supports.get(supportIndex).xCoord;
								int currentY = supports.get(supportIndex).yCoord;
								int currentZ = supports.get(supportIndex).zCoord;
								int topSupportY = supports.size() > 0 ? supports.get(supports.size() - 1).yCoord : supports.get(0).yCoord;

								while (currentY < topSupportY) {
									currentY++;

									if (!footer.getWorldObj().isAirBlock(currentX, currentY, currentZ) && (footer.getWorldObj().getBlock(currentX, currentY, currentZ) instanceof BlockSupport)) {
										footer.getWorldObj().setBlockToAir(currentX, currentY, currentZ);
									}
								}

								return true;
							}
						}
					}
				}
			}
		}

		return false;
	}

	public void paintSupportStack(int index, ColourUtil color) {
		this.paintSupportStack(getFooter(index), color);
	}

	public void paintSupportStack(TileEntitySupport support, ColourUtil color) {
		this.paintSupportStack(getFooterFromSupport(support), color);
	}

	public void paintSupportStack(TileEntityFooter footer, ColourUtil color) {
		if (getFooterIndex(footer) != -1) {
			int currentX = footer.xCoord;
			int currentY = footer.yCoord;
			int currentZ = footer.zCoord;
			List<TileEntitySupport> supports = getSupportStack(footer);

			if (supports != null) {
				if (!supports.isEmpty()) {
					int topSupportY = supports.size() > 0 ? supports.get(supports.size() - 1).yCoord : supports.get(0).yCoord;

					while (currentY < topSupportY) {
						currentY++;

						if (footer.getWorldObj().getTileEntity(currentX, currentY, currentZ) instanceof TileEntitySupport) {
							TileEntitySupport teSupport = (TileEntitySupport) footer.getWorldObj().getTileEntity(currentX, currentY, currentZ);
							teSupport.colour = color.ordinal();
							NetworkHandler.changePaintColour(teSupport.colour);
						}
					}
				}

				footer.colour = color.ordinal();
				NetworkHandler.changePaintColour(footer.colour);
			}
		}
	}

	public void paintSupport(TileEntitySupport support, ColourUtil color) {
		if (getSupportIndex(support) != -1) {
			support.colour = color.ordinal();
			NetworkHandler.changePaintColour(support.colour);
		}
	}

	public void paintFooter(TileEntityFooter footer, ColourUtil color) {
		if (getFooterIndex(footer) != -1) {
			footer.colour = color.ordinal();
			NetworkHandler.changePaintColour(footer.colour);
		}
	}
}
