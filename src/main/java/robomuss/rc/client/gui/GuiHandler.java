package robomuss.rc.client.gui;

import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import net.minecraft.client.gui.GuiMerchant;
import net.minecraft.entity.IMerchant;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ContainerMerchant;
import net.minecraft.world.World;
import robomuss.rc.RCMod;
import robomuss.rc.block.container.ContainerTrackFabricator;
import robomuss.rc.block.container.ContainerTrackStorage;
import robomuss.rc.block.te.TileEntityStall;
import robomuss.rc.block.te.TileEntityTrackDesigner;
import robomuss.rc.block.te.TileEntityTrackFabricator;
import robomuss.rc.block.te.TileEntityTrackStorage;
import robomuss.rc.util.StallMerchant;

public class GuiHandler implements IGuiHandler {
	public GuiHandler() {
		 NetworkRegistry.INSTANCE.registerGuiHandler(RCMod.instance, this);
	}

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if(ID == 1) {
			TileEntityTrackFabricator te = (TileEntityTrackFabricator) world.getTileEntity(x, y, z);
			return new ContainerTrackFabricator(player.inventory, player, te, world, x, y, z);
		}
		else if(ID == 2) {
			TileEntityTrackStorage te = (TileEntityTrackStorage) world.getTileEntity(x, y, z);
			return new ContainerTrackStorage(player.inventory, player, te, world, x, y, z);
		}
		else if(ID == 3) {
			TileEntityStall te = (TileEntityStall) world.getTileEntity(x, y, z);
			return new ContainerMerchant(player.inventory, new StallMerchant(player), world);
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if(ID == 0) {
			TileEntityTrackDesigner te = (TileEntityTrackDesigner) world.getTileEntity(x, y, z);
			te.guiTrackDesigner = new GuiTrackDesigner(player, world, x, y, z);
			return te.guiTrackDesigner;
		}
		else if(ID == 1) {
			TileEntityTrackFabricator te = (TileEntityTrackFabricator) world.getTileEntity(x, y, z);
			return new GuiTrackFabricator(player.inventory, player, te, world, x, y, z);
		}
		else if(ID == 2) {
			TileEntityTrackStorage te = (TileEntityTrackStorage) world.getTileEntity(x, y, z);
			return new GuiTrackStorage(player.inventory, player, te, world);
		}
		else if(ID == 3) {
			TileEntityStall te = (TileEntityStall) world.getTileEntity(x, y, z);
			return new GuiMerchant(player.inventory, new StallMerchant(player), world, "Food Stall");
		}
		return null;
	}
}
