package robomuss.rc;

import java.io.File;
import java.io.IOException;

import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import robomuss.rc.block.RCBlocks;
import robomuss.rc.chat.ChatHandler;
import robomuss.rc.chat.command.RCCommandItemAnimation;
import robomuss.rc.client.gui.GuiHammerOverlay;
import robomuss.rc.client.gui.GuiHandler;
import robomuss.rc.entity.RCEntities;
import robomuss.rc.event.BlockClickedEvent;
import robomuss.rc.event.BlockPlacedEvent;
import robomuss.rc.event.CraftingEvent;
import robomuss.rc.event.RCItemTooltipEvent;
import robomuss.rc.event.RCTickHandler;
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

import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

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

	public static TrackManager trackManager;
	public static SupportManager supportManager;

	public static RCOptions rcOptions = new RCOptions(Minecraft.getMinecraft(), new File("options.txt"));

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) throws IOException, TrackStyleModelNotFoundException, JsonIOException, JsonSyntaxException, InstantiationException, IllegalAccessException, ClassNotFoundException {
//		JSONHandler.loadTrackStyles();
        proxy.registerKeybindings();
	}
	
	@Mod.EventHandler
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

		supportManager = new SupportManager();

		RCItems.init();
		RCBlocks.init();
        RCEntities.init();
		new RecipeHandler();
		new GuiHandler();
//		ClientCommandHandler.instance.registerCommand(new RCCommandItemAnimation());
		FMLCommonHandler.instance().bus().register(new CraftingEvent());
		FMLCommonHandler.instance().bus().register(new ChatHandler());
		FMLCommonHandler.instance().bus().register(new RCTickHandler());
		MinecraftForge.EVENT_BUS.register(new BlockClickedEvent());
		MinecraftForge.EVENT_BUS.register(new BlockPlacedEvent());
//		MinecraftForge.EVENT_BUS.register(new RCGuiOpenEvent());
		MinecraftForge.EVENT_BUS.register(new RCItemTooltipEvent());
        packetPipeline.initalize();
        proxy.initRenderers();
		proxy.initNetwork();
	}
	
	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		trackManager = new TrackManager();
        packetPipeline.postInitialise();
        MinecraftForge.EVENT_BUS.register(new GuiHammerOverlay(Minecraft.getMinecraft()));
	}

	@Mod.EventHandler
	public void serverLoad(FMLServerStartingEvent event) {
		event.registerServerCommand(new RCCommandItemAnimation());
	}
}
