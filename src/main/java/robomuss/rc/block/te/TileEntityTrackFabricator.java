package robomuss.rc.block.te;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
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
<<<<<<< HEAD
import net.minecraft.util.IChatComponent;
=======
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.ForgeDirection;
import robomuss.rc.RCMod;
import robomuss.rc.multiblock.MultiBlockTrackFabricator;
>>>>>>> master

public class TileEntityTrackFabricator extends TileEntity implements IInventory {
	public ItemStack[] contents = new ItemStack[2];
	public ForgeDirection direction;
	public static MultiBlockTrackFabricator multiBlockTrackFabricator = new MultiBlockTrackFabricator();

	public boolean testStruct(EntityPlayer player) {
//		System.out.println("testing structure");
		multiBlockTrackFabricator.registerStructure();
		if (multiBlockTrackFabricator.isStructureFormed(this)) {
//			System.out.println("test successful");
			return this.openGui(this.worldObj, this.xCoord, this.yCoord, this.zCoord, player);
		} else {
//			System.out.println("test failed");
			return false;
		}
	}

	public boolean openGui(World world, int x, int y, int z, EntityPlayer player) {
		player.openGui(RCMod.instance, 1, world, x, y, z);
		return true;
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

<<<<<<< HEAD
    @Override
    public void openInventory(EntityPlayer playerIn) {

    }

    @Override
    public void closeInventory(EntityPlayer playerIn) {

    }

=======
	@Override
	public void openInventory() {}

	@Override
	public void closeInventory() {}
>>>>>>> master

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
    public void clearInventory() {

    }

    @Override
	public void writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		NBTTagList nbttaglist = new NBTTagList();
		for (int i = 0; i < contents.length; i++) {
			if (contents[i] != null) {
				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.setByte("Slot", (byte) i);
				contents[i].writeToNBT(nbttagcompound1);
				nbttaglist.appendTag(nbttagcompound1);
			}
		}
		compound.setTag("Items", nbttaglist);
		
		compound.setString("direction", direction.name());
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
		
		direction = ForgeDirection.valueOf(compound.getString("direction"));
	}

	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound nbtTag = new NBTTagCompound();
		this.writeToNBT(nbtTag);
		return new S35PacketUpdateTileEntity(this.getPos(), 1, nbtTag);
	}

    @Override
    public String getName() {
        return "Track Fabricator";
    }

    @Override
    public boolean hasCustomName() {
        return false;
    }

    @Override
    public IChatComponent getDisplayName() {
        return null;
    }
}
