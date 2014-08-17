package robomuss.rc.proxy;

import robomuss.rc.block.RCBlocks;
import robomuss.rc.block.render.TileEntityRenderRideFence;
import robomuss.rc.block.render.TileEntityRenderSupport;
import robomuss.rc.block.render.TileEntityRenderTrack;
import robomuss.rc.block.te.TileEntityRideFence;
import robomuss.rc.block.te.TileEntitySupport;
import robomuss.rc.block.te.TileEntityTrack;
import robomuss.rc.tracks.TrackType;
import cpw.mods.fml.client.registry.ClientRegistry;

public class ClientProxy extends CommonProxy {
	
	@Override
	public void initRenderers() {
		for(TrackType track : RCBlocks.tracks) {
			ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTrack.class, new TileEntityRenderTrack());
		}
        
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySupport.class, new TileEntityRenderSupport());
		
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityRideFence.class, new TileEntityRenderRideFence());
	}
}
