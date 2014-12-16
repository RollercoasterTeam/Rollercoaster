package robomuss.rc.multiblock;

<<<<<<< HEAD
import robomuss.rc.block.RCBlocks;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class MultiblockTrackFabricator {
	
	public static boolean isMultiBlockStructure(World world, int x, int y, int z) {
		if (checkNorth(world, x, y, z))/* North */
			return true;
		if (checkEast(world, x, y, z))/* East */
			return true;
		if (checkSouth(world, x, y, z))/* South */
			return true;
		if (checkWest(world, x, y, z))/* West */
			return true;
		return false;
	}

	private static boolean checkNorth(World world, int x, int y, int z) {
		if (world.getBlock(x + -1, y + 0, z + 0) == RCBlocks.track_fabricator_casing) {
			if (isValidSide(world.getBlock(x + -1, y + 0, z + -1))) {
				if (world.getBlock(x + -1, y + 0, z + -2) == RCBlocks.track_fabricator_casing) {
					if (world.getBlock(x + -1, y + 1, z + 0) == RCBlocks.track_fabricator_casing) {
						if (world.getBlock(x + -1, y + 1, z + -1) == RCBlocks.track_fabricator_glass) {
							if (world.getBlock(x + -1, y + 1, z + -2) == RCBlocks.track_fabricator_casing) {
								if (world.getBlock(x + -1, y + 2, z + 0) == RCBlocks.track_fabricator_casing) {
									if (world.getBlock(x + -1, y + 2, z + -1) == RCBlocks.track_fabricator_casing) {
										if (world.getBlock(x + -1, y + 2, z
												+ -2) == RCBlocks.track_fabricator_casing) {
											if (world.getBlock(x + 0, y + 0,
													z + -1) == RCBlocks.track_fabricator_casing) {
												if (isValidSide(world.getBlock(x + 0,
														y + 0, z + -2))) {
													if (world.getBlock(x + 0,
															y + 1, z + 0) == RCBlocks.track_fabricator_glass) {
														if (world.getBlock(
																x + 0, y + 1, z
																		+ -1) == Blocks.air) {
															if (world
																	.getBlock(
																			x + 0,
																			y + 1,
																			z
																					+ -2) == RCBlocks.track_fabricator_glass) {
																if (world
																		.getBlock(
																				x + 0,
																				y + 2,
																				z + 0) == RCBlocks.track_fabricator_casing) {
																	if (world
																			.getBlock(
																					x + 0,
																					y + 2,
																					z
																							+ -1) == RCBlocks.track_fabricator_casing) {
																		if (world
																				.getBlock(
																						x + 0,
																						y + 2,
																						z
																								+ -2) == RCBlocks.track_fabricator_casing) {
																			if (world
																					.getBlock(
																							x + 1,
																							y + 0,
																							z + 0) == RCBlocks.track_fabricator_casing) {
																				if (isValidSide(world
																						.getBlock(
																								x + 1,
																								y + 0,
																								z
																										+ -1))) {
																					if (world
																							.getBlock(
																									x + 1,
																									y + 0,
																									z
																											+ -2) == RCBlocks.track_fabricator_casing) {
																						if (world
																								.getBlock(
																										x + 1,
																										y + 1,
																										z + 0) == RCBlocks.track_fabricator_casing) {
																							if (world
																									.getBlock(
																											x + 1,
																											y + 1,
																											z
																													+ -1) == RCBlocks.track_fabricator_glass) {
																								if (world
																										.getBlock(
																												x + 1,
																												y + 1,
																												z
																														+ -2) == RCBlocks.track_fabricator_casing) {
																									if (world
																											.getBlock(
																													x + 1,
																													y + 2,
																													z + 0) == RCBlocks.track_fabricator_casing) {
																										if (world
																												.getBlock(
																														x + 1,
																														y + 2,
																														z
																																+ -1) == RCBlocks.track_fabricator_casing) {
																											if (world
																													.getBlock(
																															x + 1,
																															y + 2,
																															z
																																	+ -2) == RCBlocks.track_fabricator_casing) {
																												return true;
																											}
																										}
																									}
																								}
																							}
																						}
																					}
																				}
																			}
																		}
																	}
																}
															}
														}
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		return false;
	}

	private static boolean checkEast(World world, int x, int y, int z) {
		if (world.getBlock(x + 0, y + 0, z + -1) == RCBlocks.track_fabricator_casing) {
			if (isValidSide(world.getBlock(x + 1, y + 0, z + -1))) {
				if (world.getBlock(x + 2, y + 0, z + -1) == RCBlocks.track_fabricator_casing) {
					if (world.getBlock(x + 0, y + 1, z + -1) == RCBlocks.track_fabricator_casing) {
						if (world.getBlock(x + 1, y + 1, z + -1) == RCBlocks.track_fabricator_glass) {
							if (world.getBlock(x + 2, y + 1, z + -1) == RCBlocks.track_fabricator_casing) {
								if (world.getBlock(x + 0, y + 2, z + -1) == RCBlocks.track_fabricator_casing) {
									if (world.getBlock(x + 1, y + 2, z + -1) == RCBlocks.track_fabricator_casing) {
										if (world.getBlock(x + 2, y + 2, z
												+ -1) == RCBlocks.track_fabricator_casing) {
											if (world.getBlock(x + 1, y + 0,
													z + 0) == RCBlocks.track_fabricator_casing) {
												if (isValidSide(world.getBlock(x + 2,
														y + 0, z + 0))) {
													if (world.getBlock(x + 0,
															y + 1, z + 0) == RCBlocks.track_fabricator_glass) {
														if (world.getBlock(
																x + 1, y + 1,
																z + 0) == Blocks.air) {
															if (world
																	.getBlock(
																			x + 2,
																			y + 1,
																			z + 0) == RCBlocks.track_fabricator_glass) {
																if (world
																		.getBlock(
																				x + 0,
																				y + 2,
																				z + 0) == RCBlocks.track_fabricator_casing) {
																	if (world
																			.getBlock(
																					x + 1,
																					y + 2,
																					z + 0) == RCBlocks.track_fabricator_casing) {
																		if (world
																				.getBlock(
																						x + 2,
																						y + 2,
																						z + 0) == RCBlocks.track_fabricator_casing) {
																			if (world
																					.getBlock(
																							x + 0,
																							y + 0,
																							z + 1) == RCBlocks.track_fabricator_casing) {
																				if (isValidSide(world
																						.getBlock(
																								x + 1,
																								y + 0,
																								z + 1))) {
																					if (world
																							.getBlock(
																									x + 2,
																									y + 0,
																									z + 1) == RCBlocks.track_fabricator_casing) {
																						if (world
																								.getBlock(
																										x + 0,
																										y + 1,
																										z + 1) == RCBlocks.track_fabricator_casing) {
																							if (world
																									.getBlock(
																											x + 1,
																											y + 1,
																											z + 1) == RCBlocks.track_fabricator_glass) {
																								if (world
																										.getBlock(
																												x + 2,
																												y + 1,
																												z + 1) == RCBlocks.track_fabricator_casing) {
																									if (world
																											.getBlock(
																													x + 0,
																													y + 2,
																													z + 1) == RCBlocks.track_fabricator_casing) {
																										if (world
																												.getBlock(
																														x + 1,
																														y + 2,
																														z + 1) == RCBlocks.track_fabricator_casing) {
																											if (world
																													.getBlock(
																															x + 2,
																															y + 2,
																															z + 1) == RCBlocks.track_fabricator_casing) {
																												return true;
																											}
																										}
																									}
																								}
																							}
																						}
																					}
																				}
																			}
																		}
																	}
																}
															}
														}
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		return false;
	}

	private static boolean checkSouth(World world, int x, int y, int z) {
		if (world.getBlock(x + 1, y + 0, z + 0) == RCBlocks.track_fabricator_casing) {
			if (isValidSide(world.getBlock(x + 1, y + 0, z + 1))) {
				if (world.getBlock(x + 1, y + 0, z + 2) == RCBlocks.track_fabricator_casing) {
					if (world.getBlock(x + 1, y + 1, z + 0) == RCBlocks.track_fabricator_casing) {
						if (world.getBlock(x + 1, y + 1, z + 1) == RCBlocks.track_fabricator_glass) {
							if (world.getBlock(x + 1, y + 1, z + 2) == RCBlocks.track_fabricator_casing) {
								if (world.getBlock(x + 1, y + 2, z + 0) == RCBlocks.track_fabricator_casing) {
									if (world.getBlock(x + 1, y + 2, z + 1) == RCBlocks.track_fabricator_casing) {
										if (world.getBlock(x + 1, y + 2,
												z + 2) == RCBlocks.track_fabricator_casing) {
											if (world.getBlock(x + 0, y + 0,
													z + 1) == RCBlocks.track_fabricator_casing) {
												if (isValidSide(world.getBlock(x + 0,
														y + 0, z + 2))) {
													if (world.getBlock(x + 0,
															y + 1, z + 0) == RCBlocks.track_fabricator_glass) {
														if (world.getBlock(
																x + 0, y + 1,
																z + 1) == Blocks.air) {
															if (world
																	.getBlock(
																			x + 0,
																			y + 1,
																			z + 2) == RCBlocks.track_fabricator_glass) {
																if (world
																		.getBlock(
																				x + 0,
																				y + 2,
																				z + 0) == RCBlocks.track_fabricator_casing) {
																	if (world
																			.getBlock(
																					x + 0,
																					y + 2,
																					z + 1) == RCBlocks.track_fabricator_casing) {
																		if (world
																				.getBlock(
																						x + 0,
																						y + 2,
																						z + 2) == RCBlocks.track_fabricator_casing) {
																			if (world
																					.getBlock(
																							x
																									+ -1,
																							y + 0,
																							z + 0) == RCBlocks.track_fabricator_casing) {
																				if (isValidSide(world
																						.getBlock(
																								x
																										+ -1,
																								y + 0,
																								z + 1))) {
																					if (world
																							.getBlock(
																									x
																											+ -1,
																									y + 0,
																									z + 2) == RCBlocks.track_fabricator_casing) {
																						if (world
																								.getBlock(
																										x
																												+ -1,
																										y + 1,
																										z + 0) == RCBlocks.track_fabricator_casing) {
																							if (world
																									.getBlock(
																											x
																													+ -1,
																											y + 1,
																											z + 1) == RCBlocks.track_fabricator_glass) {
																								if (world
																										.getBlock(
																												x
																														+ -1,
																												y + 1,
																												z + 2) == RCBlocks.track_fabricator_casing) {
																									if (world
																											.getBlock(
																													x
																															+ -1,
																													y + 2,
																													z + 0) == RCBlocks.track_fabricator_casing) {
																										if (world
																												.getBlock(
																														x
																																+ -1,
																														y + 2,
																														z + 1) == RCBlocks.track_fabricator_casing) {
																											if (world
																													.getBlock(
																															x
																																	+ -1,
																															y + 2,
																															z + 2) == RCBlocks.track_fabricator_casing) {
																												return true;
																											}
																										}
																									}
																								}
																							}
																						}
																					}
																				}
																			}
																		}
																	}
																}
															}
														}
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		return false;
	}

	private static boolean checkWest(World world, int x, int y, int z) {
		if (world.getBlock(x + 0, y + 0, z + 1) == RCBlocks.track_fabricator_casing) {
			if (isValidSide(world.getBlock(x + -1, y + 0, z + 1))) {
				if (world.getBlock(x + -2, y + 0, z + 1) == RCBlocks.track_fabricator_casing) {
					if (world.getBlock(x + 0, y + 1, z + 1) == RCBlocks.track_fabricator_casing) {
						if (world.getBlock(x + -1, y + 1, z + 1) == RCBlocks.track_fabricator_glass) {
							if (world.getBlock(x + -2, y + 1, z + 1) == RCBlocks.track_fabricator_casing) {
								if (world.getBlock(x + 0, y + 2, z + 1) == RCBlocks.track_fabricator_casing) {
									if (world.getBlock(x + -1, y + 2, z + 1) == RCBlocks.track_fabricator_casing) {
										if (world.getBlock(x + -2, y + 2,
												z + 1) == RCBlocks.track_fabricator_casing) {
											if (world.getBlock(x + -1, y + 0,
													z + 0) == RCBlocks.track_fabricator_casing) {
												if (isValidSide(world.getBlock(x + -2,
														y + 0, z + 0))) {
													if (world.getBlock(x + 0,
															y + 1, z + 0) == RCBlocks.track_fabricator_glass) {
														if (world.getBlock(x
																+ -1, y + 1,
																z + 0) == Blocks.air) {
															if (world
																	.getBlock(
																			x
																					+ -2,
																			y + 1,
																			z + 0) == RCBlocks.track_fabricator_glass) {
																if (world
																		.getBlock(
																				x + 0,
																				y + 2,
																				z + 0) == RCBlocks.track_fabricator_casing) {
																	if (world
																			.getBlock(
																					x
																							+ -1,
																					y + 2,
																					z + 0) == RCBlocks.track_fabricator_casing) {
																		if (world
																				.getBlock(
																						x
																								+ -2,
																						y + 2,
																						z + 0) == RCBlocks.track_fabricator_casing) {
																			if (world
																					.getBlock(
																							x + 0,
																							y + 0,
																							z
																									+ -1) == RCBlocks.track_fabricator_casing) {
																				if (isValidSide(world
																						.getBlock(
																								x
																										+ -1,
																								y + 0,
																								z
																										+ -1))) {
																					if (world
																							.getBlock(
																									x
																											+ -2,
																									y + 0,
																									z
																											+ -1) == RCBlocks.track_fabricator_casing) {
																						if (world
																								.getBlock(
																										x + 0,
																										y + 1,
																										z
																												+ -1) == RCBlocks.track_fabricator_casing) {
																							if (world
																									.getBlock(
																											x
																													+ -1,
																											y + 1,
																											z
																													+ -1) == RCBlocks.track_fabricator_glass) {
																								if (world
																										.getBlock(
																												x
																														+ -2,
																												y + 1,
																												z
																														+ -1) == RCBlocks.track_fabricator_casing) {
																									if (world
																											.getBlock(
																													x + 0,
																													y + 2,
																													z
																															+ -1) == RCBlocks.track_fabricator_casing) {
																										if (world
																												.getBlock(
																														x
																																+ -1,
																														y + 2,
																														z
																																+ -1) == RCBlocks.track_fabricator_casing) {
																											if (world
																													.getBlock(
																															x
																																	+ -2,
																															y + 2,
																															z
																																	+ -1) == RCBlocks.track_fabricator_casing) {
																												return true;
																											}
																										}
																									}
																								}
																							}
																						}
																					}
																				}
																			}
																		}
																	}
																}
															}
														}
													}
												}
=======
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import robomuss.rc.block.RCBlocks;
import robomuss.rc.block.te.TileEntityTrackFabricator;

import java.util.ArrayList;
import java.util.List;

public class MultiBlockTrackFabricator extends MultiBlockManager {
	private static MultiBlockStructure structure = new MultiBlockStructure();
	private boolean hasStructureBeenRegistered;

	public void registerStructure() {
		if (!hasStructureBeenRegistered) {
			structure.addLayer(0, RCBlocks.track_fabricator, "#O#", "O#O", "#F#", '#', RCBlocks.track_fabricator_casing, 'F', RCBlocks.track_fabricator, 'O', RCBlocks.track_fabricator_output);
			structure.addLayer(1, null, "#G#", "G G", "#G#", '#', RCBlocks.track_fabricator_casing, 'G', RCBlocks.track_fabricator_glass);
			structure.addLayer(2, null, "###", "###", "###", '#', RCBlocks.track_fabricator_casing);

			List<Block> aliasList = new ArrayList<Block>();
			aliasList.add(RCBlocks.track_fabricator_casing);
			aliasList.add(RCBlocks.track_fabricator_output);
//			aliasList.add(RCBlocks.track_fabricator_glass);

			structure.getLayer(0).setAliasList(aliasList);
			hasStructureBeenRegistered = true;
		} else {
			return;
		}
	}

	public boolean isStructureFormed(TileEntity tileEntity) {
		if (tileEntity instanceof TileEntityTrackFabricator) {
			TileEntityTrackFabricator teFab = (TileEntityTrackFabricator) tileEntity;
			return checkStructure(teFab.getWorldObj(), teFab.xCoord, teFab.yCoord, teFab.zCoord, teFab.direction);
		} else {
			return false;
		}
	}

	private static boolean checkStructure(World world, int x, int y, int z, ForgeDirection direction) {
		//NORTH: -Z, SOUTH: +Z, WEST: -X, EAST: +X
		int offsetX = 0;
		int offsetY = 0;
		int offsetZ = 0;

		structure.setMasterPos(x, y, z);

		for (int layer = 0; layer < structure.getLayers().size(); layer++) {
			for (int row = 0; row < structure.getLayer(layer).layerWidth; row++) {
				for (int column = 0; column < structure.getLayer(layer).layerDepth; column++) {

					offsetY = layer;

					switch (direction) {
						case NORTH:
							switch (row) {
								case 0: offsetZ = -2; break;
								case 1: offsetZ = -1; break;
								case 2: offsetZ =  0; break;
							}
							switch (column) {
								case 0: offsetX = -1; break;
								case 1: offsetX =  0; break;
								case 2: offsetX =  1; break;
							}
							break;
						case SOUTH:
							switch (row) {
								case 0: offsetZ = 2; break;
								case 1: offsetZ = 1; break;
								case 2: offsetZ = 0; break;
							}
							switch (column) {
								case 0: offsetX =  1; break;
								case 1: offsetX =  0; break;
								case 2: offsetX = -1; break;
							}
							break;
						case WEST:
							switch (row) {
								case 0: offsetX = 2; break;
								case 1: offsetX = 1; break;
								case 2: offsetX = 0; break;
							}
							switch (column) {
								case 0: offsetZ = -1; break;
								case 1: offsetZ =  0; break;
								case 2: offsetZ =  1; break;
							}
							break;
						case EAST:
							switch (row) {
								case 0: offsetX = -2; break;
								case 1: offsetX = -1; break;
								case 2: offsetX =  0; break;
							}
							switch (column) {
								case 0: offsetZ =  1; break;
								case 1: offsetZ =  0; break;
								case 2: offsetZ = -1; break;
							}
							break;
					}

					Block foundBlock = world.getBlock(x + offsetX, y + offsetY, z + offsetZ);
					Block templateBlock = structure.getLayer(layer).getBlock(row, column);

					boolean foundEqualsTemplate = foundBlock.getUnlocalizedName().equalsIgnoreCase(templateBlock.getUnlocalizedName());

					if (!foundEqualsTemplate) {
						if (structure.getLayer(layer).getHasBlockAliases()) {
							if ((row == 0 && (column == 1)) || (row == 1 && (column == 0 || column == 2))) {
								if (structure.getLayer(layer).getNumAliases(templateBlock) == 1) {
									Block aliasBlock = structure.getLayer(layer).getAlias(templateBlock, structure.getLayer(layer).getNumAliases(templateBlock) - 1);

									boolean foundEqualsAlias = foundBlock.getUnlocalizedName().equalsIgnoreCase(aliasBlock.getUnlocalizedName());

									if (!foundEqualsTemplate && !foundEqualsAlias) {
										return false;
									}
								} else if (structure.getLayer(layer).getNumAliases(templateBlock) > 1) {
									List<Block> aliasBlocks = structure.getLayer(layer).getAliases(templateBlock);
									if (aliasBlocks != null) {
										for (int i = 0; i < aliasBlocks.size(); i++) {
											boolean foundEqualsAlias = foundBlock.getUnlocalizedName().equalsIgnoreCase(aliasBlocks.get(i).getUnlocalizedName());

											if (!foundEqualsAlias) {
												if (i == aliasBlocks.size() - 1) {
													return false;
												} else {
													i++;
												}
											} else {
												return true;
>>>>>>> master
											}
										}
									}
								}
<<<<<<< HEAD
							}
=======
							} else {
								return false;
							}
						} else {
							return false;
>>>>>>> master
						}
					}
				}
			}
		}
<<<<<<< HEAD
		return false;
	}

	private static boolean isValidSide(Block block) {
		return block == RCBlocks.track_fabricator_casing || block == RCBlocks.track_fabricator_output;
=======
		return true;
>>>>>>> master
	}
}
