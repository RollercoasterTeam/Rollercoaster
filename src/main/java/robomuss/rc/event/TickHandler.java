package robomuss.rc.event;

import robomuss.rc.block.render.TileEntityRenderTrack;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;

public class TickHandler {
	
	private static int count;
	
	@SubscribeEvent
	public void onPlayerTick(TickEvent.PlayerTickEvent event) {

	}
	
	@SubscribeEvent
	public void onClientTick(TickEvent.ClientTickEvent event) {
		if(count % 10 == 0) {
			TileEntityRenderTrack.count++;
		}
		count++;
	}
	 
	@SubscribeEvent
	public void onServerTick(TickEvent.ServerTickEvent event) {
		
	}
	 
	@SubscribeEvent
	public void onRenderTick(TickEvent.RenderTickEvent event) {

	}
	 
	@SubscribeEvent
	public void onWorldTick(TickEvent.WorldTickEvent event) {
		 
	}
}
