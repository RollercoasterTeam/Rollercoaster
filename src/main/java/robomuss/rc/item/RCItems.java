package robomuss.rc.item;

import java.util.ArrayList;

import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import robomuss.rc.RCMod;
import robomuss.rc.track.TrackHandler;
import robomuss.rc.track.extra.TrackExtra;
import robomuss.rc.util.ColourUtil;
import cpw.mods.fml.common.registry.GameRegistry;

public class RCItems {

	public static int last_extra_id;
	
	public static Item hammer, paint, empty_brush, brush, ticket, pass, key, silicon_wafer, IC, pcb, panel, remote, userGuide;
	public static ArrayList<Item> trains = new ArrayList<Item>();
	public static ItemArmor hat_1;
	
	public static void init() {
		for(TrackExtra extra: TrackHandler.extras) {
			extra.source = new ItemExtra(extra.id).setUnlocalizedName(extra.name + "_extra").setTextureName("rc:extras/" + extra.name).setCreativeTab(RCMod.tools);
			GameRegistry.registerItem(extra.source, extra.name + "_extra");
		}
		
		hammer = new ItemHammer().setUnlocalizedName("hammer").setTextureName("rc:hammer").setCreativeTab(RCMod.tools);
		paint = new ItemPaint().setUnlocalizedName("paint").setCreativeTab(RCMod.tools);
		empty_brush = new Item().setUnlocalizedName("empty_brush").setTextureName("rc:brush").setCreativeTab(RCMod.tools);
		brush = new ItemBrush().setUnlocalizedName("brush").setCreativeTab(RCMod.tools);
		ticket = new Item().setUnlocalizedName("ticket").setTextureName("rc:ticket").setCreativeTab(RCMod.other).setMaxStackSize(1);
		pass = new Item().setUnlocalizedName("pass").setTextureName("rc:pass").setCreativeTab(RCMod.other).setMaxStackSize(1);
		key = new Item().setUnlocalizedName("key").setTextureName("rc:key").setCreativeTab(RCMod.other).setMaxStackSize(1);
		hat_1 = (ItemArmor) new ItemArmor(ArmorMaterial.CHAIN, 0, 0).setUnlocalizedName("hat_1").setTextureName("rc:hat_1").setCreativeTab(RCMod.other).setMaxStackSize(1);
		silicon_wafer = new Item().setUnlocalizedName("silicon_wafer").setTextureName("rc:silicon_wafer").setCreativeTab(RCMod.other);
		IC = new Item().setUnlocalizedName("IC").setTextureName("rc:IC").setCreativeTab(RCMod.other).setMaxStackSize(1);
		pcb = new Item().setUnlocalizedName("pcb").setTextureName("rc:pcb").setCreativeTab(RCMod.other).setMaxStackSize(1);
		panel = new Item().setUnlocalizedName("panel").setTextureName("rc:panel").setCreativeTab(RCMod.other).setMaxStackSize(1);
		remote = new ItemRemote().setUnlocalizedName("remote").setTextureName("rc:remote").setCreativeTab(RCMod.other).setMaxStackSize(1);
		userGuide = new ItemUserGuide().setUnlocalizedName("userGuide").setTextureName("rc:userGuide").setCreativeTab(RCMod.other).setMaxStackSize(1);
		
		for(int i = 0; i < ColourUtil.colours.length; i++) {
			trains.add(new ItemTrain().setUnlocalizedName("train_" + i).setTextureName("rc:train").setCreativeTab(RCMod.track));
		}
		
		GameRegistry.registerItem(hammer, "hammer");
		GameRegistry.registerItem(paint, "paint");
		GameRegistry.registerItem(empty_brush, "empty_brush");
		GameRegistry.registerItem(brush, "brush");
		GameRegistry.registerItem(ticket, "ticket");
		GameRegistry.registerItem(pass, "pass");
		GameRegistry.registerItem(key, "key");
		GameRegistry.registerItem(silicon_wafer, "silicon_wafer");
		GameRegistry.registerItem(IC, "IC");
		GameRegistry.registerItem(pcb, "pcb");
		GameRegistry.registerItem(panel, "panel");
		GameRegistry.registerItem(remote, "remote");
		GameRegistry.registerItem(userGuide, "userGuide");
		
		for(int i = 0; i < ColourUtil.colours.length; i++) {
			GameRegistry.registerItem(trains.get(i), "train_" + i);
		}
	}

}
