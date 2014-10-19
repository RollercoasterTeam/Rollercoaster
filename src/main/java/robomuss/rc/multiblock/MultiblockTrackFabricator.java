package robomuss.rc.multiblock;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import robomuss.rc.block.BlockTrackFabricator;
import robomuss.rc.block.RCBlocks;

public class MultiblockTrackFabricator {
	
	public static boolean isMultiBlockStructure(World world, int x, int y, int z, ForgeDirection direction) {
//		return checkStructure(world, x, y, z, direction);
		boolean temp = checkStructure(world, x, y, z, direction);

//		if (checkNorth(world, x, y, z))/* North */
//			return true;
//		if (checkEast(world, x, y, z))/* East */
//			return true;
//		if (checkSouth(world, x, y, z))/* South */
//			return true;
//		if (checkWest(world, x, y, z))/* West */
//			return true;
		return checkNorth(world, x, y, z) || checkEast(world, x, y, z) || checkSouth(world, x, y, z) || checkWest(world, x, y, z);
	}

	private static boolean checkStructure(World world, int x, int y, int z, ForgeDirection direction) {
		//NORTH: -Z, SOUTH: +Z, WEST: -X, EAST: +X
		int offsetX = 0;
		int offsetY = 0;
		int offsetZ = 0;

		int checkX;
		int checkY;
		int checkZ;

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				for (int k = 0; k < 3; k++) {
					offsetY = i;

					switch (direction) {
						case NORTH:
							switch (k) {
								case 1:
									offsetX = 1;
									break;
								case 2:
									offsetX = -1;
									break;
								default:
									offsetX = 0;
							}

							switch (j) {
								case 0:
									offsetZ = -2;
									break;
								case 1:
									offsetZ = -1;
									break;
								default:
									offsetZ = 0;
							}
							break;
					}

					checkX = x + offsetX;
					checkY = y + offsetY;
					checkZ = z + offsetZ;

//					switch (k) {
//						case 0:
//							if (direction == ForgeDirection.NORTH) {
//								offsetX = 0;
//							} else {
//								offsetX = 0;
//							}
//							break;
//						case 1:
//							if (direction == ForgeDirection.NORTH) {
//								offsetX = 1;
//							} else {
//								offsetX = 0;
//							}
//							break;
//						case 2:
//							if (direction == ForgeDirection.NORTH) {
//								offsetX = -1;
//							} else {
//								offsetX = 0;
//							}
//							break;
//					}

					if (direction == ForgeDirection.NORTH) {
						if (!(world.getBlock(checkX, checkY, checkZ) instanceof BlockTrackFabricator)) {
							if (checkX == x && checkY == y + 1 && checkZ == z - 1) {                 //if checking the block in the middle of the structure
//								if (!world.isAirBlock(x + offsetX, y + offsetY, z + offsetZ)) {
//									return false;
//								}
								world.setBlockToAir(checkX, checkY, checkZ);
							}

							if (checkY == y || checkY == y + 2) {                                         //if checking the bottom or top layers
//								world.setBlock(checkX, checkY, checkZ, RCBlocks.track_fabricator_casing);
								if (!(world.getBlock(checkX, checkY, checkZ).equals(RCBlocks.track_fabricator_casing))) {
									return false;
								}

//								if ((x + offsetX == x - 1 && z + offsetZ == z + 1) || (x + offsetX == x + 1 && z + offsetZ == z + 1) || (z + offsetZ == z + 2)) {
								if (checkY == y) {
									if ((checkX == x - 1 && checkZ == z - 1) || (checkX == x + 1 && checkZ == z - 1) || (checkX == x && checkZ == z - 2)) {
//									world.setBlock(x + offsetX, y + offsetY, z + offsetZ, RCBlocks.track_fabricator_output);
										if (!(world.getBlock(checkX, checkY, checkZ).equals(RCBlocks.track_fabricator_casing) || world.getBlock(checkX, checkY, checkZ).equals(RCBlocks.track_fabricator_output))) {
											return false;
										}
									}

//									if ((checkX == x + 1 && checkZ == z - 1)) {
//										world.setBlock(x + offsetX, y + offsetY, z + offsetZ, RCBlocks.track_fabricator_output);
//									}
//
//									if ((checkX == x && checkZ == z - 2)) {
//										world.setBlock(x + offsetX, y + offsetY, z + offsetZ, RCBlocks.track_fabricator_output);
//									}
//								if (!world.getBlock(x + offsetX, y + offsetY, z + offsetZ).equals(RCBlocks.track_fabricator_casing)) {
//									return false;
//								}
								}
							}

							if (y + offsetY == 1) {

							}
						}
					}
				}
			}
		}
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

	private static boolean isValidSide(Block block) {
		return block == RCBlocks.track_fabricator_casing || block == RCBlocks.track_fabricator_output;
	}
}
