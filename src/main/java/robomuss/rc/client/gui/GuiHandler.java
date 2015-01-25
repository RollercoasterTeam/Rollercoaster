package robomuss.rc.client.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import robomuss.rc.RCMod;
import robomuss.rc.block.container.ContainerTrackFabricator;
import robomuss.rc.block.container.ContainerTrackStorage;
import robomuss.rc.block.te.TileEntityTrackFabricator;
import robomuss.rc.block.te.TileEntityTrackStorage;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.network.NetworkRegistry;

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
		if(ID == 2) {
			TileEntityTrackStorage te = (TileEntityTrackStorage) world.getTileEntity(x, y, z);
			return new ContainerTrackStorage(player.inventory, player, te, world, x, y, z);
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if(ID == 0) {
			return new GuiTrackDesigner(player, world, x, y, z);
		}
		if(ID == 1) {
			TileEntityTrackFabricator te = (TileEntityTrackFabricator) world.getTileEntity(x, y, z);
			return new GuiTrackFabricator(player.inventory, player, te, world, x, y, z);
		}
		if(ID == 2) {
			TileEntityTrackStorage te = (TileEntityTrackStorage) world.getTileEntity(x, y, z);
			return new GuiTrackStorage(player.inventory, player, te, world);
		}
		return null;
	}
}
