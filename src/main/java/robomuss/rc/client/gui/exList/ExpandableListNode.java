package robomuss.rc.client.gui.exList;

import net.minecraft.util.ResourceLocation;

public class ExpandableListNode {
	
	private String name;
	private ExpandableListNode parent;
	private ExpandableListNode[] children;
	public ResourceLocation texture;
	
	public ExpandableListNode(String name, ExpandableListNode parent, ExpandableListNode... children) {
		this.name = name;
		this.texture = new ResourceLocation("rc", "textures/gui/nodes/" + name.toLowerCase() + ".png");
		
		this.parent = parent;
		this.children = children;
	}
	
	public ExpandableListNode(String name, ResourceLocation loc, ExpandableListNode parent, ExpandableListNode... children) {
		this.name = name;
		this.texture = loc;
		
		this.parent = parent;
		this.children = children;
	}
	
	public String getName() {
		return name;
	}
	
	public ExpandableListNode getParent() {
		return parent;
	}
	
	public ExpandableListNode[] getChildren() {
		return children;
	}
}
