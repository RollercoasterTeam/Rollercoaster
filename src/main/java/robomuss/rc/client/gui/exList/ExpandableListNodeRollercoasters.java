package robomuss.rc.client.gui.exList;


public class ExpandableListNodeRollercoasters extends ExpandableListNode {

	public ExpandableListNodeRollercoasters(String name, ExpandableListNode parent, ExpandableListNode[] children) {
		super(name, parent, children);
	}
	
	/*@Override
	public ExpandableListNode[] getChildren() {
		ExpandableListNode[] children = new ExpandableListNode[TrackHandler.styles.size()];
		for(int i = 0; i < TrackHandler.styles.size(); i++) {
			TrackStyle style = TrackHandler.styles.get(i);
			children[i] = new ExpandableListNode(style.localizedName, new ResourceLocation("rc", "textures/gui/nodes.blank.png"), this, null);
		}
		return children;
	}*/
	
	@Override
	public void onClicked() {
		
	}
}
