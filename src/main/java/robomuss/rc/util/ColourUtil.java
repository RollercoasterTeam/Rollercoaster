package robomuss.rc.util;

import java.util.ArrayList;

import net.minecraft.util.ResourceLocation;


public class ColourUtil {

    public static final String[] colours = {
    	"White", "Orange", "Magenta", "Light Blue", "Yellow", "Lime", "Pink", "Gray", "Light Gray", "Cyan", "Purple", "Blue", "Brown", "Green", "Red", "Black", //15
    	"Iron", "Gold", "Diamond", "Emerald", "Quartz", "Lapis", "Coal", "Redstone", //23
    	"Oak Planks", "Spruce Planks", "Birch Planks", "Jungle Planks", "Acacia Planks", "Dark Oak Planks", //29
    	"Secret!"
    };
	public static int numColours = colours.length;
	
	public static ArrayList<ResourceLocation> textures = new ArrayList<ResourceLocation>();
	
	public static void initTextures() {
		for(int i = 0; i < numColours; i++) {
			textures.add(new ResourceLocation("rc:textures/models/colour_" + i + ".png"));
		}
	}
}
