package robomuss.rc.track;

import net.minecraft.block.BlockPortal;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
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

	public void placeFooter(World world, BlockPos pos) {
		if (world.isAirBlock(pos)) {
			world.setBlockState(pos, RCBlocks.footer.getDefaultState());
			this.addFooter((TileEntityFooter) world.getTileEntity(pos));
		}
	}

	public void breakFooter(World world, BlockPos pos) {
		if (world.getTileEntity(pos) instanceof TileEntityFooter) {
			this.removeFooter((TileEntityFooter) world.getTileEntity(pos));
			world.setBlockToAir(pos);
		}
	}

	public List<TileEntitySupport> getSupportStack(int index) {
		return this.getSupportStack(getFooter(index));
	}

	public List<TileEntitySupport> getSupportStack(TileEntityFooter footer) {
		List<TileEntitySupport> supports = new ArrayList<TileEntitySupport>();

		if (getFooterIndex(footer) != -1) {
			BlockPos currentPos = footer.getPos();

			while (footer.getWorld().getTileEntity(currentPos.up()) instanceof TileEntitySupport) {
				supports.add((TileEntitySupport) footer.getWorld().getTileEntity(currentPos.up()));
				currentPos = currentPos.up();
			}
		}

		return supports;
	}

	public TileEntityFooter getFooterFromSupport(TileEntitySupport support) {
		BlockPos currentPos = support.getPos();
		TileEntityFooter footer = null;

		while (support.getWorld().getTileEntity(currentPos.down()) instanceof TileEntitySupport || support.getWorld().getTileEntity(currentPos.down()) instanceof TileEntityFooter) {
			if (support.getWorld().getTileEntity(currentPos.down()) instanceof TileEntityFooter) {
				footer = (TileEntityFooter) support.getWorld().getTileEntity(currentPos.down());
				break;
			}

			currentPos = currentPos.down();
		}

		return footer;
	}

	public boolean addSupportToStack(int index) {
		return this.addSupportToStack(getFooter(index));
	}

	public boolean addSupportToStack(TileEntityFooter footer) {
		if (getFooterIndex(footer) != -1) {
			BlockPos currentPos = footer.getPos();
			List<TileEntitySupport> supports = getSupportStack(footer);

			if (supports != null) {
				if (!supports.isEmpty()) {
					TileEntitySupport topSupport = supports.size() > 0 ? supports.get(supports.size() - 1) : supports.get(0);

					if (topSupport != null) {
						if (topSupport.getWorld().isAirBlock(topSupport.getPos().up())) {
							topSupport.getWorld().setBlockState(topSupport.getPos().up(), RCBlocks.support.getDefaultState());
							TileEntitySupport teSupport = (TileEntitySupport) topSupport.getWorld().getTileEntity(topSupport.getPos().up());
							teSupport.colour = topSupport.colour;
							return true;
						} else if (topSupport.getWorld().getBlockState(topSupport.getPos().up()).getBlock() instanceof BlockSupport) {
							TileEntitySupport teSupport = (TileEntitySupport) topSupport.getWorld().getTileEntity(topSupport.getPos().up());
							teSupport.colour = topSupport.colour;
							return true;
						}
					} else {
						if (footer.getWorld().isAirBlock(currentPos.up())) {
							footer.getWorld().setBlockState(currentPos.up(), RCBlocks.support.getDefaultState());
							TileEntitySupport teSupport = (TileEntitySupport) footer.getWorld().getTileEntity(currentPos);
							teSupport.colour = footer.colour;
							return true;
						} else if (footer.getWorld().getBlockState(currentPos) instanceof BlockSupport) {
							TileEntitySupport teSupport = (TileEntitySupport) footer.getWorld().getTileEntity(currentPos);
							teSupport.colour = footer.colour;
							return true;
						}
					}
				} else { /* supports.isEmpty() */
					if (footer.getWorld().isAirBlock(currentPos.up())) {
						footer.getWorld().setBlockState(currentPos.up(), RCBlocks.support.getDefaultState());
						TileEntitySupport teSupport = (TileEntitySupport) footer.getWorld().getTileEntity(currentPos.up());
						teSupport.colour = footer.colour;
						supports.add(0, teSupport);
						return true;
					} else if (footer.getWorld().getBlockState(currentPos.up()).getBlock() instanceof BlockSupport) {
						TileEntitySupport teSupport = (TileEntitySupport) footer.getWorld().getTileEntity(currentPos.up());
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
				BlockPos currentPos = footer.getPos();
				List<TileEntitySupport> supports = getSupportStack(footer);

				if (supports != null) {
					if (!supports.isEmpty()) {
						BlockPos topPos = supports.size() > 0 ? supports.get(supports.size() - 1).getPos() : supports.get(0).getPos();

						while (!(currentPos.up().equals(topPos))) {
							currentPos = currentPos.up();

							if (!footer.getWorld().isAirBlock(currentPos) && (footer.getWorld().getBlockState(currentPos).getBlock() instanceof BlockSupport)) {
								footer.getWorld().setBlockToAir(currentPos);
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
								BlockPos currentPos = supports.get(supportIndex).getPos();
								BlockPos topPos = supports.size() > 0 ? supports.get(supports.size() - 1).getPos() : supports.get(0).getPos();

								while (!(currentPos.up().equals(topPos))) {
									currentPos = currentPos.up();

									if (!footer.getWorld().isAirBlock(currentPos) && (footer.getWorld().getBlockState(currentPos).getBlock() instanceof BlockSupport)) {
										footer.getWorld().setBlockToAir(currentPos);
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
			BlockPos currentPos = footer.getPos();
			List<TileEntitySupport> supports = getSupportStack(footer);

			if (supports != null) {
				if (!supports.isEmpty()) {
					BlockPos topPos = supports.size() > 0 ? supports.get(supports.size() - 1).getPos() : supports.get(0).getPos();

					while (!(currentPos.up().equals(topPos))) {
						currentPos = currentPos.up();

						if (footer.getWorld().getTileEntity(currentPos) instanceof TileEntitySupport) {
							TileEntitySupport teSupport = (TileEntitySupport) footer.getWorld().getTileEntity(currentPos);
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
