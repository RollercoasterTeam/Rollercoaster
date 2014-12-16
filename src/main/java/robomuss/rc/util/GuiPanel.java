package robomuss.rc.util;

import net.minecraft.util.ResourceLocation;

public class GuiPanel {

	public String name;
	public String displayName;
	
	public int width;
	public int height;
	
	public ResourceLocation texture;
	
	public GuiPanel(String name, String displayName, int width, int height) {
		this.name = name;
		this.displayName = displayName;
		
		this.width = width;
		this.height = height;
		
		this.texture = new ResourceLocation("rc", "textures/gui/trackDesigner/" + name + ".png");
	}

}
