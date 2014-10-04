package robomuss.rc.client.gui.exList;

public class ExpandableListNode {
	
	private String name;
	private ExpandableListNode parent;
	private ExpandableListNode[] children;
	
	public ExpandableListNode(String name, ExpandableListNode parent, ExpandableListNode... children) {
		this.name = name;
		
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
