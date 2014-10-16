package robomuss.rc.multiblock;

public interface IMultiBlockComponent {
	void updateModel(int state);
	void updateTexture(int state);
	void setMaster();
	boolean isMaster();
	boolean doAdditionalChecks();
	boolean canOpenGUIFrom();
	boolean canPlayerOpenGUI();
	boolean hasGUI();
	boolean hasInventory();
	boolean canAccessInventoryFrom();

}
