package robomuss.rc.recipe;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import robomuss.rc.block.RCBlocks;
import robomuss.rc.item.RCItems;
import robomuss.rc.tracks.TrackHandler;
import cpw.mods.fml.common.registry.GameRegistry;

public class RecipeHandler {

	public RecipeHandler() {
		registerItemRecipes();
		registerBlockRecipes();
	}

	private void registerItemRecipes() {
		GameRegistry.addRecipe(new ItemStack(RCItems.hammer, 1, OreDictionary.WILDCARD_VALUE), new Object[] {
			"X", "Y", 'X', Items.iron_ingot, 'Y', Items.stick
		});
		
		GameRegistry.addRecipe(new ItemStack(RCItems.empty_brush), new Object[] {
			"X", "Y", 'X', new ItemStack(Blocks.wool, 1, 0), 'Y', Items.stick
		});
		
		for(int i = 0; i < 16; i++) {
			GameRegistry.addRecipe(new ItemStack(RCItems.paint, 1, i), new Object[] {
				"X X", "XYX", "XXX", 'X', Items.iron_ingot, 'Y', new ItemStack(Items.dye, 1, 15 - i)
			});
			
			GameRegistry.addShapelessRecipe(new ItemStack(RCItems.brush, 1, i), new Object[] {
				new ItemStack(RCItems.paint, 1, i), RCItems.empty_brush
			});
		}
	}

	private void registerBlockRecipes() {
		GameRegistry.addRecipe(new ItemStack(RCBlocks.ride_fence, 4), new Object[] {
			"XXX", "X X", 'X', Blocks.iron_bars
		});
		
		GameRegistry.addRecipe(new ItemStack(RCBlocks.support, 4), new Object[] {
			" X ", " X ", " X ", 'X', Items.iron_ingot
		});
		
		GameRegistry.addRecipe(new ItemStack(RCBlocks.track_storage),
			"XXX", "XYX", "XXX", 'X', Items.iron_ingot, 'Y', TrackHandler.findTrackType("horizontal").block
		);
		
		GameRegistry.addRecipe(new ItemStack(RCBlocks.track_fabricator),
				"XYX", "XZX", "XAX", 'X', Items.iron_ingot, 'Y', Items.redstone, 'Z', Blocks.furnace, 'A', Items.lava_bucket
		);
		
		GameRegistry.addShapelessRecipe(new ItemStack(RCBlocks.path, 8, 0),
				new ItemStack(Blocks.stained_hardened_clay, 1, 15), new ItemStack(RCItems.hammer, 1, OreDictionary.WILDCARD_VALUE)
		);
	
		GameRegistry.addShapelessRecipe(new ItemStack(RCBlocks.path, 8, 1),
			Blocks.dirt, Blocks.cobblestone, new ItemStack(RCItems.hammer, 1, OreDictionary.WILDCARD_VALUE)
		);
		
		GameRegistry.addShapelessRecipe(new ItemStack(RCBlocks.path, 8, 2),
			Items.paper, new ItemStack(RCItems.hammer, 1, OreDictionary.WILDCARD_VALUE)
		);
		
		GameRegistry.addShapelessRecipe(new ItemStack(RCBlocks.path, 8, 3),
			Blocks.dirt, Blocks.leaves, new ItemStack(RCItems.hammer, 1, OreDictionary.WILDCARD_VALUE)
		);
		
		GameRegistry.addShapelessRecipe(new ItemStack(RCBlocks.path, 8, 3),
			Blocks.dirt, Blocks.leaves2, new ItemStack(RCItems.hammer, 1, OreDictionary.WILDCARD_VALUE)
		);
		
		GameRegistry.addShapelessRecipe(new ItemStack(RCBlocks.path, 8, 4),
			new ItemStack(Blocks.planks, 1, 0), new ItemStack(RCItems.hammer, 1, OreDictionary.WILDCARD_VALUE)
		);
		
		GameRegistry.addShapelessRecipe(new ItemStack(RCBlocks.path, 8, 5),
			new ItemStack(Blocks.planks, 1, 1), new ItemStack(RCItems.hammer, 1, OreDictionary.WILDCARD_VALUE)
		);
		
		GameRegistry.addShapelessRecipe(new ItemStack(RCBlocks.path, 8, 6),
			new ItemStack(Blocks.planks, 1, 2), new ItemStack(RCItems.hammer, 1, OreDictionary.WILDCARD_VALUE)
		);
		
		GameRegistry.addShapelessRecipe(new ItemStack(RCBlocks.path, 8, 7),
			new ItemStack(Blocks.planks, 1, 3), new ItemStack(RCItems.hammer, 1, OreDictionary.WILDCARD_VALUE)
		);
		
		GameRegistry.addShapelessRecipe(new ItemStack(RCBlocks.path, 8, 8), 
			Blocks.sandstone, new ItemStack(RCItems.hammer, 1, OreDictionary.WILDCARD_VALUE)
		);
		
		GameRegistry.addShapelessRecipe(new ItemStack(RCBlocks.path, 8, 9), 
			Blocks.quartz_block, new ItemStack(RCItems.hammer, 1, OreDictionary.WILDCARD_VALUE)
		);	
	}
}
