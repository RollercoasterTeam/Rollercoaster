package robomuss.rc.block.te;

import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.common.registry.LanguageRegistry;
import robomuss.rc.RCMod;
import robomuss.rc.multiblock.MultiBlockTrackFabricator;

public class TileEntityTrackFabricator extends TileEntity implements IInventory {
	public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
	public ItemStack[] contents = new ItemStack[2];

	public static MultiBlockTrackFabricator multiBlockTrackFabricator = new MultiBlockTrackFabricator();

	public boolean testStruct(EntityPlayer player) {
		multiBlockTrackFabricator.registerStructure();
		if (multiBlockTrackFabricator.isStructureFormed(this)) {
			player.openGui(RCMod.instance, 1, this.worldObj, this.pos.getX(), this.pos.getY(), this.pos.getZ());
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int getSizeInventory() {
		return contents.length;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		return contents[i];
	}

	@Override
	public ItemStack decrStackSize(int i, int count) {
		ItemStack itemstack = getStackInSlot(i);

		if (itemstack != null) {
			if (itemstack.stackSize <= count) {
				setInventorySlotContents(i, null);
			} else {
				itemstack = itemstack.splitStack(count);
			}
		}

		return itemstack;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int i) {
		ItemStack item = getStackInSlot(i);
		setInventorySlotContents(i, null);
		return item;

	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) {
		contents[i] = itemstack;

		if (itemstack != null && itemstack.stackSize > getInventoryStackLimit()) {
			itemstack.stackSize = getInventoryStackLimit();
		}
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return true;
	}

	@Override
	public void openInventory(EntityPlayer player) {}

	@Override
	public void closeInventory(EntityPlayer player) {}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		return itemstack.getItem() == Items.iron_ingot;
	}

	@Override
	public int getField(int id) {
		return 0;
	}

	@Override
	public void setField(int id, int value) {

	}

	@Override
	public int getFieldCount() {
		return 0;
	}

	@Override
	public void clear() {}

	@Override
	public void writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		NBTTagList nbttaglist = new NBTTagList();

		for (int i = 0; i < contents.length; i++) {
			if (contents[i] != null) {
				NBTTagCompound compound1 = new NBTTagCompound();
				compound1.setByte("Slot", (byte) i);
				contents[i].writeToNBT(compound1);
				nbttaglist.appendTag(compound1);
			}
		}

		compound.setTag("Items", nbttaglist);
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		NBTTagList nbttaglist = compound.getTagList("Items", Constants.NBT.TAG_COMPOUND);
		contents = new ItemStack[getSizeInventory()];

		for (int i = 0; i < nbttaglist.tagCount(); i++) {
			NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
			int j = nbttagcompound1.getByte("Slot") & 0xff;

			if (j >= 0 && j < contents.length) {
				contents[j] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
			}
		}
	}

	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound nbtTag = new NBTTagCompound();
		this.writeToNBT(nbtTag);
		return new S35PacketUpdateTileEntity(this.getPos(), 1, nbtTag);
	}

	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity packet) {
		readFromNBT(packet.getNbtCompound());
	}

	@Override
	public String getName() {
		return LanguageRegistry.instance().getStringLocalization("tile.track_fabricator.name");
	}

	@Override
	public boolean hasCustomName() {
		return true;
	}

	@Override
	public IChatComponent getDisplayName() {
		return null;
	}
}
