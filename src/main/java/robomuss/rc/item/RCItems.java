package robomuss.rc.item;

import java.util.ArrayList;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemFood;
import robomuss.rc.RCMod;
import robomuss.rc.track.TrackHandler;
import robomuss.rc.track.extra.TrackExtra;
import robomuss.rc.util.StallItem;
import robomuss.rc.util.StallType;
import scala.Array;

public class RCItems {
	public static int last_extra_id;
	
	public static Item hammer, paint, empty_brush, brush, ticket, pass, key, train, balloon, coin;
	public static StallType food, merch;
	public static ItemArmor hat_1;
	
	public static void init() {
		for(TrackExtra extra: TrackHandler.extras) {
			extra.source = new ItemExtra(extra.id).setUnlocalizedName(extra.name + "_extra").setTextureName("rc:extras/" + extra.name).setCreativeTab(RCMod.tools);
			GameRegistry.registerItem(extra.source, extra.name + "_extra");
		}

//		for (ColourUtil color : ColourUtil.COLORS) {
//			String name = String.format("balloon.%s.name", color.unlocalized_name);
//			Item balloon = new ItemBalloon().setUnlocalizedName(name).setTextureName("rc:balloon").setCreativeTab(RCMod.other);
//			GameRegistry.registerItem(balloon, "balloon_" + color.unlocalized_name);
//		}
		
		hammer = new ItemHammer().setUnlocalizedName("hammer").setTextureName("rc:hammer").setCreativeTab(RCMod.tools);
		paint = new ItemPaint().setUnlocalizedName("paint").setCreativeTab(RCMod.tools);
		empty_brush = new Item().setUnlocalizedName("empty_brush").setTextureName("rc:brush").setCreativeTab(RCMod.tools);
		brush = new ItemBrush().setUnlocalizedName("brush").setCreativeTab(RCMod.tools);
		ticket = new Item().setUnlocalizedName("ticket").setTextureName("rc:ticket").setCreativeTab(RCMod.other).setMaxStackSize(1);
		pass = new Item().setUnlocalizedName("pass").setTextureName("rc:pass").setCreativeTab(RCMod.other).setMaxStackSize(1);
		key = new Item().setUnlocalizedName("key").setTextureName("rc:key").setCreativeTab(RCMod.other).setMaxStackSize(1);
		train = new ItemTrain().setUnlocalizedName("train").setTextureName("rc:train").setCreativeTab(RCMod.track);
		balloon = new ItemBalloon().setUnlocalizedName("balloon").setTextureName("rc:balloon").setCreativeTab(RCMod.other);
		coin = new Item().setUnlocalizedName("coin").setTextureName("rc:coin").setCreativeTab(RCMod.other);
		
		food = new StallType();
		food.items.add(new StallItem("Chicken Nuggets", "nuggets")); 
		food.items.add(new StallItem("Chips", "chips")); 
		food.items.add(new StallItem("Popcorn", "popcorn")); 
		food.items.add(new StallItem("Candy Floss", "candy_floss")); 
		
		merch = new StallType();
		merch.items.add(new StallItem("T-Shirt", "t-shirt"));
		
		GameRegistry.registerItem(hammer, "hammer");
		GameRegistry.registerItem(paint, "paint");
		GameRegistry.registerItem(empty_brush, "empty_brush");
		GameRegistry.registerItem(brush, "brush");
		GameRegistry.registerItem(ticket, "ticket");
		GameRegistry.registerItem(pass, "pass");
		GameRegistry.registerItem(key, "key");
		GameRegistry.registerItem(train, "train");
		GameRegistry.registerItem(balloon, "balloon");
		GameRegistry.registerItem(coin, "coin");
	}

}
