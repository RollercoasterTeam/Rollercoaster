package robomuss.rc.client.gui.exList;

import robomuss.rc.track.TrackHandler;
import robomuss.rc.track.style.TrackStyle;

public class ExpandableListNodeRollercoasters extends ExpandableListNode {

	public ExpandableListNodeRollercoasters(String name, ExpandableListNode parent, ExpandableListNode[] children) {
		super(name, parent, children);
	}
	
	@Override
	public ExpandableListNode[] getChildren() {
		ExpandableListNode[] children = new ExpandableListNode[TrackHandler.styles.size()];
		for(int i = 0; i < TrackHandler.styles.size(); i++) {
			TrackStyle style = TrackHandler.styles.get(i);
			children[i] = new ExpandableListNode(style.localizedName, this, null);
		}
		return children;
	}
}
