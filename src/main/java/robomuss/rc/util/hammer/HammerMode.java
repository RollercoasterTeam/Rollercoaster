package robomuss.rc.util.hammer;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

public class HammerMode {
	public String name;
	
	public HammerMode(String name) {
		this.name = name;
	}
	
	public void onRightClick(TileEntity tileentity, PlayerInteractEvent event) {}
}
