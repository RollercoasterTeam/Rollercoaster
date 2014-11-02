package robomuss.rc;

import java.io.IOException;

import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.BlockEvent;
import robomuss.rc.block.RCBlocks;
import robomuss.rc.chat.ChatHandler;
import robomuss.rc.client.gui.GuiHammerOverlay;
import robomuss.rc.client.gui.GuiHandler;
import robomuss.rc.entity.RCEntitys;
import robomuss.rc.event.BlockClickedEvent;
import robomuss.rc.event.CraftingEvent;
import robomuss.rc.event.TrackPlaceEventHandler;
import robomuss.rc.exception.TrackStyleModelNotFoundException;
import robomuss.rc.item.RCItems;
import robomuss.rc.json.JSONHandler;
import robomuss.rc.network.PacketPipeline;
import robomuss.rc.proxy.CommonProxy;
import robomuss.rc.recipe.RecipeHandler;
import robomuss.rc.track.TrackHandler;

import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import robomuss.rc.track.TrackManager;

@Mod(modid = RCMod.MODID, name = RCMod.NAME, version = RCMod.VERSION)
public class RCMod {
	
	public static final String MODID = "rc";
	public static final String NAME = "Rollercoaster Mod";
	public static final String VERSION = "v1.4_beta2";

	@Instance
	public static RCMod instance;
	
	@SidedProxy(clientSide="robomuss.rc.proxy.ClientProxy", serverSide="robomuss.rc.proxy.CommonProxy")
	public static CommonProxy proxy;

	public static CreativeTabs decor, track, tools, other;

    public static final PacketPipeline packetPipeline = new PacketPipeline();

	public static TrackManager trackManager;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) throws IOException, TrackStyleModelNotFoundException, JsonIOException, JsonSyntaxException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		JSONHandler.loadTrackStyles();
        proxy.registerKeybindings();
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event) {
		decor = new CreativeTabs("tab.decor") {
			@Override
			@SideOnly(Side.CLIENT)
			public Item getTabIconItem() {
				return null;
			}
			
			@Override
			@SideOnly(Side.CLIENT)
			public ItemStack getIconItemStack() {
				return new ItemStack(RCBlocks.path, 1, 3);
			}
		};
		track = new CreativeTabs("tab.track") {
			@Override
			@SideOnly(Side.CLIENT)
			public Item getTabIconItem() {
				return Item.getItemFromBlock(TrackHandler.pieces.get(0).block);
			}
		};
		tools = new CreativeTabs("tab.tools") {
			@Override
			@SideOnly(Side.CLIENT)
			public Item getTabIconItem() {
				return RCItems.hammer;
			}
		};
		other = new CreativeTabs("tab.other") {
			@Override
			@SideOnly(Side.CLIENT)
			public Item getTabIconItem() {
				return RCItems.ticket;
			}
		};

		TrackHandler.registerTracks();
		
		RCItems.init();
		RCBlocks.init();
        RCEntitys.init();
		new RecipeHandler();
		new GuiHandler();
		FMLCommonHandler.instance().bus().register(new CraftingEvent());
		FMLCommonHandler.instance().bus().register(new ChatHandler());
//		FMLCommonHandler.instance().bus().register(new TrackPlaceEventHandler());
		MinecraftForge.EVENT_BUS.register(new BlockClickedEvent());
		MinecraftForge.EVENT_BUS.register(new TrackPlaceEventHandler());
        packetPipeline.initalize();
        proxy.initRenderers();
		proxy.initNetwork();
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		this.trackManager = new TrackManager();
        packetPipeline.postInitialise();
        MinecraftForge.EVENT_BUS.register(new GuiHammerOverlay(Minecraft.getMinecraft()));
	}
}
