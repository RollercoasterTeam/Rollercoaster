package robomuss.rc;

import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
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
=======
import robomuss.rc.chat.ChatHandler;
import robomuss.rc.client.gui.GuiHammerOverlay;
>>>>>>> master
import robomuss.rc.client.gui.GuiHandler;
import robomuss.rc.entity.RCEntitys;
import robomuss.rc.event.CraftingEvent;
import robomuss.rc.exception.TrackStyleModelNotFoundException;
import robomuss.rc.item.RCItems;
import robomuss.rc.json.JSONHandler;
import robomuss.rc.network.PacketPipeline;
import robomuss.rc.proxy.CommonProxy;
import robomuss.rc.recipe.RecipeHandler;
import robomuss.rc.track.TrackHandler;

import java.io.IOException;

@Mod(modid = RCMod.MODID, name = RCMod.NAME, version = RCMod.VERSION)
public class RCMod {
	
	public static final String MODID = "rc";
	public static final String NAME = "Rollercoaster Mod";
	public static final String VERSION = "v1.4_beta2";

	@Mod.Instance
	public static RCMod instance;
	
	@SidedProxy(clientSide="robomuss.rc.proxy.ClientProxy", serverSide="robomuss.rc.proxy.CommonProxy")
	public static CommonProxy proxy;
	
	public static CreativeTabs decor, track, tools, other;

    public static final PacketPipeline packetPipeline = new PacketPipeline();
	
	@Mod.EventHandler
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
		
		RCItems.init();
		RCBlocks.init();
        RCEntitys.init();
		new RecipeHandler();
		new GuiHandler();
		FMLCommonHandler.instance().bus().register(new CraftingEvent());
<<<<<<< HEAD
        //TODO forge
		//MinecraftForge.EVENT_BUS.register(new BlockClickedEvent());
=======
		FMLCommonHandler.instance().bus().register(new ChatHandler());
		MinecraftForge.EVENT_BUS.register(new BlockClickedEvent());
>>>>>>> master
        packetPipeline.initalise();
        proxy.initRenderers();
		proxy.initNetwork();
	}
	
	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event) {
        packetPipeline.postInitialise();
        //TODO forge
       // MinecraftForge.EVENT_BUS.register(new GuiHammerOverlay(Minecraft.getMinecraft()));
	}
}
