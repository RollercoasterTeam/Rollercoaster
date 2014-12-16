package robomuss.rc.block.te;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import robomuss.rc.RCMod;

public class TileEntityFooter extends TileEntity {
	public int colour;
	public boolean forceConnection = false;
//	private List<BlockSnapshot> supportStack = new ArrayList<BlockSnapshot>();

	public TileEntityFooter() {}

	public TileEntityFooter(World world, int colour) {
		this.worldObj = world;
		this.colour = colour;
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		colour = compound.getInteger("colour");
		forceConnection = compound.getBoolean("forceConnection");
		RCMod.supportManager.addFooter(this);
//		boolean isStackEmpty = compound.getBoolean("isStackEmpty");
//		int stackSize = compound.getInteger("stackSize");

//		if (!isStackEmpty) {
//			if (compound.hasKey("stackList")) {
//				NBTTagList stackList = compound.getTagList("stackList", compound.getId());
//
//				for (int i = 0; i < stackList.tagCount(); i++) {
//					NBTTagCompound supportCompound = stackList.getCompoundTagAt(i);
//					this.supportStack.clear();
//					this.supportStack.add(BlockSnapshot.readFromNBT(supportCompound));
//				}
//			}
//		}

//		System.out.println("footer nbt read");
//		setSupportFooter();
	}

	@Override
	public void writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setInteger("colour", colour);
		compound.setBoolean("forceConnection", forceConnection);
//		compound.setBoolean("isStackEmpty", isSupportStackEmpty());
//		compound.setInteger("stackSize", isSupportStackEmpty() ? -1 : getNextSupportIndex());

//		if (!isSupportStackEmpty()) {
//			NBTTagList stackList = new NBTTagList();
//
//			for (BlockSnapshot snapshot : this.supportStack) {
//				NBTTagCompound supportCompound = new NBTTagCompound();
//				snapshot.writeToNBT(supportCompound);
//				stackList.appendTag(supportCompound);
//			}
//
//			compound.setTag("stackList", stackList);
//		}
	}

//	private void setSupportFooter() {
//		if (this.worldObj.getBlock(this.xCoord, this.yCoord + 1, this.zCoord) instanceof BlockSupport) {
//			System.out.println("setting support footers");
//			((TileEntitySupport) this.worldObj.getTileEntity(this.xCoord, this.yCoord + 1, this.zCoord)).setSupportFooter(this);
//		}
//	}

//	public int getNextSupportIndex() {
//		return this.supportStack.size();
//	}
//
//	public boolean isSupportStackEmpty() {
//		return this.supportStack.isEmpty();
//	}
	
	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound nbt = new NBTTagCompound();
		this.writeToNBT(nbt);
		return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 1, nbt);
	}

	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity packet) {
		readFromNBT(packet.func_148857_g());
	}
	
	@Override
	public boolean canUpdate() {
		return false;
	}

//	public void setStackSnapshot(TileEntitySupport support, NBTTagCompound compound) {
//		if (support != null) {
////			BlockSnapshot supportSnapshot = BlockSnapshot.getBlockSnapshot(support.getWorldObj(), support.xCoord, support.yCoord, support.zCoord);
//			BlockSnapshot supportSnapshot = BlockSnapshot.readFromNBT(compound);
//			this.supportStack.add(supportSnapshot);
//		}
//	}
//
//	public BlockSnapshot getStackSnapshot(int index) {
//		if (index >= 0 && index < this.supportStack.size()) {
//			return this.supportStack.get(index);
//		}
//
//		return null;
//	}
//
//	public void addSupportToStack(EntityPlayer player) {
//		if (player != null) {
//			if (player.getHeldItem() != null && player.getHeldItem().getItem() == Item.getItemFromBlock(RCBlocks.support)) {
//				if (this.supportStack.size() < 66) {
//					if (this.supportStack.size() > 0) {
//						BlockSnapshot topSnapshot = this.supportStack.get(this.supportStack.size() - 1);
//						BlockSnapshot newSnapshot = BlockSnapshot.getBlockSnapshot(topSnapshot.world, topSnapshot.x, topSnapshot.y + 1, topSnapshot.z);
//						int supportColor = ColourUtil.WHITE.color;
//
//						if (this.worldObj.getBlock(topSnapshot.x, topSnapshot.y, topSnapshot.z) instanceof BlockSupport) {
//							supportColor = ((BlockSupport) this.worldObj.getBlock(topSnapshot.x, topSnapshot.y, topSnapshot.z)).getPaintMeta(this.worldObj, topSnapshot.x, topSnapshot.y, topSnapshot.z);
//						}
//
//						if (newSnapshot.getReplacedBlock() != null && newSnapshot.getReplacedBlock() instanceof BlockAir) {
//							this.worldObj.setBlock(newSnapshot.x, newSnapshot.y, newSnapshot.z, RCBlocks.support, supportColor, 3);
////							((TileEntitySupport) this.worldObj.getTileEntity(newSnapshot.x, newSnapshot.y, newSnapshot.z)).footer = this;
//							((TileEntitySupport) this.worldObj.getTileEntity(newSnapshot.x, newSnapshot.y, newSnapshot.z)).setSupportFooter(this);
//							((TileEntitySupport) this.worldObj.getTileEntity(newSnapshot.x, newSnapshot.y, newSnapshot.z)).supportStackIndex = this.supportStack.size();
//							this.supportStack.add(newSnapshot);
//							System.out.println(this.supportStack.size());
//							System.out.printf("Top Support Footer: X: %d, Y: %d, Z: %d%n", newSnapshot.x, newSnapshot.y, newSnapshot.z);
//							if (!player.capabilities.isCreativeMode) {
//								player.inventory.decrStackSize(player.inventory.currentItem, 1);
//							}
//						}
//					} else {
//						BlockSnapshot newSnapshot = BlockSnapshot.getBlockSnapshot(this.worldObj, this.xCoord, this.yCoord + 1, this.zCoord);
//						int footerColor = ((BlockFooter) this.worldObj.getBlock(this.xCoord, this.yCoord, this.zCoord)).getPaintMeta(this.worldObj, this.xCoord, this.yCoord, this.zCoord);
//
//						if (newSnapshot.getReplacedBlock() != null && newSnapshot.getReplacedBlock() instanceof BlockAir) {
//							this.worldObj.setBlock(newSnapshot.x, newSnapshot.y, newSnapshot.z, RCBlocks.support, footerColor, 3);
//							((TileEntitySupport) this.worldObj.getTileEntity(newSnapshot.x, newSnapshot.y, newSnapshot.z)).footer = this;
//							((TileEntitySupport) this.worldObj.getTileEntity(newSnapshot.x, newSnapshot.y, newSnapshot.z)).supportStackIndex = this.supportStack.size();
//							this.supportStack.add(newSnapshot);
//							System.out.println(this.supportStack.size());
//							if (!player.capabilities.isCreativeMode) {
//								player.inventory.decrStackSize(player.inventory.currentItem, 1);
//							}
//						}
//					}
//				}
//			}
//		}
//	}
//
//	public void removeSupportFromStack(int index) {
//		if (index >= 0 && index < this.supportStack.size()) {
//			int lastIndex = this.supportStack.size() - 1;
//			int[] topSupportLoc = new int[]{this.supportStack.get(lastIndex).x, this.supportStack.get(lastIndex).y, this.supportStack.get(lastIndex).z};
//			int[] inputSupportLoc = new int[]{this.supportStack.get(index).x, this.supportStack.get(index).y, this.supportStack.get(index).z};
//
//			for (int i = inputSupportLoc[1]; i < topSupportLoc[1] + 1; i++) {
//				this.worldObj.setBlockToAir(inputSupportLoc[0], i, inputSupportLoc[2]);
//			}
//
//			this.supportStack.subList(index, this.supportStack.size()).clear();
//			System.out.println(this.supportStack.size());
//		}
//	}
//
//	public void removeAllSupports() {
//		int lastIndex = this.supportStack.size() - 1;
//
//		if (lastIndex != -1) {
//			int[] topSupportLoc = new int[] {this.supportStack.get(lastIndex).x, this.supportStack.get(lastIndex).y, this.supportStack.get(lastIndex).z};
//			int[] bottomSupportLoc = new int[] {this.supportStack.get(0).x, this.supportStack.get(0).y, this.supportStack.get(0).z};
//
//			for (int i = bottomSupportLoc[1]; i < topSupportLoc[1] + 1; i++) {
//				this.worldObj.setBlockToAir(bottomSupportLoc[0], i, bottomSupportLoc[2]);
//			}
//
//			this.supportStack.subList(0, this.supportStack.size()).clear();
//			this.supportStack.clear();
//		}
//	}
//
//	public void clearSupportStackColors() {
//		int lastIndex = this.supportStack.size() - 1;
//
//		if (lastIndex != -1) {
//			int[] topSupportLoc = new int[] {this.supportStack.get(lastIndex).x, this.supportStack.get(lastIndex).y, this.supportStack.get(lastIndex).z};
//			int[] bottomSupportLoc = new int[] {this.supportStack.get(0).x, this.supportStack.get(0).y, this.supportStack.get(0).z};
//
//			for (int i = bottomSupportLoc[1]; i < topSupportLoc[1] + 1; i++) {
//				if (this.worldObj.getTileEntity(bottomSupportLoc[0], i, bottomSupportLoc[2]) instanceof TileEntitySupport) {
//					TileEntitySupport teSupport = (TileEntitySupport) this.worldObj.getTileEntity(bottomSupportLoc[0], i, bottomSupportLoc[2]);
//					teSupport.colour = ColourUtil.WHITE.ordinal();
//					teSupport.getWorldObj().markBlockForUpdate(teSupport.xCoord, teSupport.yCoord, teSupport.zCoord);
////					NetworkHandler.changePaintColour(teSupport.colour);
//				}
//			}
//
//			this.colour = ColourUtil.WHITE.ordinal();
//			this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
////			NetworkHandler.changePaintColour(this.colour);
//		}
//	}
}
