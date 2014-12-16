package robomuss.rc;

import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
<<<<<<< HEAD
=======
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
>>>>>>> master
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import robomuss.rc.block.RCBlocks;
<<<<<<< HEAD
<<<<<<< HEAD
=======
import robomuss.rc.chat.ChatHandler;
=======
import robomuss.rc.chat.ChatHandler;
import robomuss.rc.chat.command.RCCommandItemAnimation;
>>>>>>> master
import robomuss.rc.client.gui.GuiHammerOverlay;
>>>>>>> master
import robomuss.rc.client.gui.GuiHandler;
<<<<<<< HEAD
import robomuss.rc.entity.RCEntitys;
import robomuss.rc.event.CraftingEvent;
=======
import robomuss.rc.entity.RCEntities;
import robomuss.rc.event.*;
>>>>>>> master
import robomuss.rc.exception.TrackStyleModelNotFoundException;
import robomuss.rc.item.RCItems;
import robomuss.rc.json.JSONHandler;
import robomuss.rc.network.PacketPipeline;
import robomuss.rc.proxy.CommonProxy;
import robomuss.rc.recipe.RecipeHandler;
import robomuss.rc.track.SupportManager;
import robomuss.rc.track.TrackHandler;
import robomuss.rc.track.TrackManager;
import robomuss.rc.util.RCOptions;

<<<<<<< HEAD
=======
import java.io.File;
>>>>>>> master
import java.io.IOException;

@Mod(modid = RCMod.MODID, name = RCMod.NAME, version = RCMod.VERSION)
public class RCMod {
	
	public static final String MODID = "rc";
	public static final String NAME = "Roller Coaster Mod";
	public static final String VERSION = "v1.4_beta2";

	@Mod.Instance
	public static RCMod instance;
	
	@SidedProxy(clientSide="robomuss.rc.proxy.ClientProxy", serverSide="robomuss.rc.proxy.CommonProxy")
	public static CommonProxy proxy;

	public static CreativeTabs decor, track, tools, other;

    public static final PacketPipeline packetPipeline = new PacketPipeline();
<<<<<<< HEAD
	
	@Mod.EventHandler
=======

	public static TrackManager trackManager;
	public static SupportManager supportManager;

	public static RCOptions rcOptions = new RCOptions(Minecraft.getMinecraft(), new File("options.txt"));

	@EventHandler
>>>>>>> master
	public void preInit(FMLPreInitializationEvent event) throws IOException, TrackStyleModelNotFoundException, JsonIOException, JsonSyntaxException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		JSONHandler.loadTrackStyles();
        proxy.registerKeybindings();
	}
	
	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {
		decor = new CreativeTabs(0, "tab.decor") {
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
		track = new CreativeTabs(0, "tab.track") {
			@Override
			@SideOnly(Side.CLIENT)
			public Item getTabIconItem() {
				return Item.getItemFromBlock(TrackHandler.pieces.get(0).block);
			}
		};
		tools = new CreativeTabs(0, "tab.tools") {
			@Override
			@SideOnly(Side.CLIENT)
			public Item getTabIconItem() {
				return RCItems.hammer;
			}
		};
		other = new CreativeTabs(0, "tab.other") {
			@Override
			@SideOnly(Side.CLIENT)
			public Item getTabIconItem() {
				return RCItems.ticket;
			}
		};

		TrackHandler.registerTracks();

		supportManager = new SupportManager();

		RCItems.init();
		RCBlocks.init();
        RCEntities.init();
		new RecipeHandler();
		new GuiHandler();
//		ClientCommandHandler.instance.registerCommand(new RCCommandItemAnimation());
		FMLCommonHandler.instance().bus().register(new CraftingEvent());
<<<<<<< HEAD
<<<<<<< HEAD
        //TODO forge
		//MinecraftForge.EVENT_BUS.register(new BlockClickedEvent());
=======
		FMLCommonHandler.instance().bus().register(new ChatHandler());
		MinecraftForge.EVENT_BUS.register(new BlockClickedEvent());
>>>>>>> master
        packetPipeline.initalise();
=======
		FMLCommonHandler.instance().bus().register(new ChatHandler());
		FMLCommonHandler.instance().bus().register(new RCTickHandler());
		MinecraftForge.EVENT_BUS.register(new BlockClickedEvent());
		MinecraftForge.EVENT_BUS.register(new BlockPlacedEvent());
//		MinecraftForge.EVENT_BUS.register(new RCGuiOpenEvent());
		MinecraftForge.EVENT_BUS.register(new RCItemTooltipEvent());
        packetPipeline.initalize();
>>>>>>> master
        proxy.initRenderers();
		proxy.initNetwork();
	}
	
	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		trackManager = new TrackManager();
        packetPipeline.postInitialise();
        //TODO forge
       // MinecraftForge.EVENT_BUS.register(new GuiHammerOverlay(Minecraft.getMinecraft()));
	}

	@EventHandler
	public void serverLoad(FMLServerStartingEvent event) {
		event.registerServerCommand(new RCCommandItemAnimation());
	}
}
