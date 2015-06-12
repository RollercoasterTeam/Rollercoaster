package robomuss.rc.proxy;

import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;
import robomuss.rc.block.RCBlocks;
import robomuss.rc.block.render.TileEntityRenderBin;
import robomuss.rc.block.render.TileEntityRenderCatwalk;
import robomuss.rc.block.render.TileEntityRenderFooter;
import robomuss.rc.block.render.TileEntityRenderRideFence;
import robomuss.rc.block.render.TileEntityRenderRideSign;
import robomuss.rc.block.render.TileEntityRenderSupport;
import robomuss.rc.block.render.TileEntityRenderTrack;
import robomuss.rc.block.render.TileEntityRenderTrackDesigner;
import robomuss.rc.block.render.TileEntityRenderTrackFabricator;
import robomuss.rc.block.render.TileEntityRenderWoodenSupport;
import robomuss.rc.block.te.TileEntityBin;
import robomuss.rc.block.te.TileEntityCatwalk;
import robomuss.rc.block.te.TileEntityFooter;
import robomuss.rc.block.te.TileEntityRideFence;
import robomuss.rc.block.te.TileEntityRideSign;
import robomuss.rc.block.te.TileEntitySupport;
import robomuss.rc.block.te.TileEntityTrack;
import robomuss.rc.block.te.TileEntityTrackDesigner;
import robomuss.rc.block.te.TileEntityTrackFabricator;
import robomuss.rc.block.te.TileEntityWoodenSupport;
import robomuss.rc.client.renderer.ItemRenderFence;
import robomuss.rc.client.renderer.ItemRenderFooter;
import robomuss.rc.client.renderer.ItemRenderSupport;
import robomuss.rc.client.renderer.ItemRenderTrackDesigner;
import robomuss.rc.client.renderer.ItemRenderTrackFabricator;
import robomuss.rc.client.renderer.ItemRenderWoodenSupport;
import robomuss.rc.entity.EntityTrain;
import robomuss.rc.entity.RenderTrain;
import robomuss.rc.track.TrackHandler;
import robomuss.rc.track.TrackType;
import robomuss.rc.util.ColourUtil;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy {
	
	@Override
	public void initRenderers() {
		ColourUtil.initTextures();
		
		for(TrackType track : TrackHandler.tracks) {
			ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTrack.class, new TileEntityRenderTrack());
		}
        
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityRideFence.class, new TileEntityRenderRideFence());
		
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySupport.class, new TileEntityRenderSupport());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityWoodenSupport.class, new TileEntityRenderWoodenSupport());
		
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityFooter.class, new TileEntityRenderFooter());
		
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCatwalk.class, new TileEntityRenderCatwalk());
		
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTrackFabricator.class, new TileEntityRenderTrackFabricator());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTrackDesigner.class, new TileEntityRenderTrackDesigner());
		
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityRideSign.class, new TileEntityRenderRideSign());
		
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBin.class, new TileEntityRenderBin());
		
        RenderingRegistry.registerEntityRenderingHandler(EntityTrain.class, new RenderTrain());
        
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(RCBlocks.track_fabricator), new ItemRenderTrackFabricator());
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(RCBlocks.track_designer), new ItemRenderTrackDesigner());
        
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(RCBlocks.support), new ItemRenderSupport());
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(RCBlocks.woodenSupport), new ItemRenderWoodenSupport());
        
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(RCBlocks.footer), new ItemRenderFooter());
        
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(RCBlocks.ride_fence), new ItemRenderFence());
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(RCBlocks.ride_fence_corner), new ItemRenderFence());
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(RCBlocks.ride_fence_triangle), new ItemRenderFence());
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(RCBlocks.ride_fence_square), new ItemRenderFence());
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(RCBlocks.ride_fence_gate), new ItemRenderFence());
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(RCBlocks.ride_fence_panel), new ItemRenderFence());
        
        /*for(TrackType track : TrackHandler.tracks) {
        	boolean useIcon = false;
        	if(track instanceof IInventoryRenderSettings) {
        		useIcon = ((IInventoryRenderSettings) track).useIcon();
        	}
        	if(!useIcon) {
        		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(track.block), new ItemRenderTrack());
        	}
        }*/
	}
}
