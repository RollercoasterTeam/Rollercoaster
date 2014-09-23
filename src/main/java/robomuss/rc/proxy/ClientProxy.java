package robomuss.rc.proxy;

import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;
import robomuss.rc.block.RCBlocks;
import robomuss.rc.block.render.TileEntityRenderFooter;
import robomuss.rc.block.render.TileEntityRenderRideFence;
import robomuss.rc.block.render.TileEntityRenderSupport;
import robomuss.rc.block.render.TileEntityRenderTrack;
import robomuss.rc.block.render.TileEntityRenderTrackFabricator;
import robomuss.rc.block.render.TileEntityRenderWoodenSupport;
import robomuss.rc.block.te.TileEntityFooter;
import robomuss.rc.block.te.TileEntityRideFence;
import robomuss.rc.block.te.TileEntitySupport;
import robomuss.rc.block.te.TileEntityTrack;
import robomuss.rc.block.te.TileEntityTrackFabricator;
import robomuss.rc.block.te.TileEntityWoodenSupport;
import robomuss.rc.client.renderer.ItemRenderFence;
import robomuss.rc.client.renderer.ItemRenderFooter;
import robomuss.rc.client.renderer.ItemRenderSupport;
import robomuss.rc.client.renderer.ItemRenderTrack;
import robomuss.rc.client.renderer.ItemRenderTrackFabricator;
import robomuss.rc.client.renderer.ItemRenderWoodenSupport;
import robomuss.rc.entity.EntityTrain;
import robomuss.rc.entity.RenderTrain;
import robomuss.rc.track.TrackHandler;
import robomuss.rc.track.TrackType;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy {
	
	@SuppressWarnings("unused")
	@Override
	public void initRenderers() {
		for(TrackType track : TrackHandler.tracks) {
			ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTrack.class, new TileEntityRenderTrack());
		}
        
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityRideFence.class, new TileEntityRenderRideFence());
		
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySupport.class, new TileEntityRenderSupport());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityWoodenSupport.class, new TileEntityRenderWoodenSupport());
		
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityFooter.class, new TileEntityRenderFooter());
		
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTrackFabricator.class, new TileEntityRenderTrackFabricator());
		
        RenderingRegistry.registerEntityRenderingHandler(EntityTrain.class, new RenderTrain());
        
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(RCBlocks.track_fabricator), new ItemRenderTrackFabricator());
        
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(RCBlocks.support), new ItemRenderSupport());
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(RCBlocks.woodenSupport), new ItemRenderWoodenSupport());
        
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(RCBlocks.footer), new ItemRenderFooter());
        
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(RCBlocks.ride_fence), new ItemRenderFence());
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(RCBlocks.ride_fence_corner), new ItemRenderFence());
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(RCBlocks.ride_fence_triangle), new ItemRenderFence());
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(RCBlocks.ride_fence_square), new ItemRenderFence());
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(RCBlocks.ride_fence_gate), new ItemRenderFence());
        
        for(TrackType track : TrackHandler.tracks) {
        	MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(track.block), new ItemRenderTrack());
        }
	}
}
