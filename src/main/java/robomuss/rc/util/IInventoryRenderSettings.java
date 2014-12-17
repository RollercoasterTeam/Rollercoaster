package robomuss.rc.util;

//import net.minecraftforge.client.model.IModelCustom;

public interface IInventoryRenderSettings {
//	public void render(int renderStage, IModelCustom model);

	public float getInventoryX();
	
	public float getInventoryY();
	
	public float getInventoryZ();
	
	public float getInventoryScale();
	
	public float getInventoryRotation();
	
	public boolean useIcon();
}
