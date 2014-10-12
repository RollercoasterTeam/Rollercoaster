package robomuss.rc.multiblock;

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
