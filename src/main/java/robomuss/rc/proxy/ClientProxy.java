package robomuss.rc.proxy;

import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;
import robomuss.rc.block.RCBlocks;
import robomuss.rc.block.render.TileEntityRenderConveyor;
import robomuss.rc.block.render.TileEntityRenderFooter;
import robomuss.rc.block.render.TileEntityRenderRideFence;
import robomuss.rc.block.render.TileEntityRenderSupport;
import robomuss.rc.block.render.TileEntityRenderTrack;
import robomuss.rc.block.render.TileEntityRenderTrackFabricator;
import robomuss.rc.block.render.TileEntityRenderWoodenSupport;
import robomuss.rc.block.te.*;
import robomuss.rc.client.Keybindings;
import robomuss.rc.client.renderer.*;
import robomuss.rc.entity.EntityTrain;
import robomuss.rc.entity.RenderTrain;
import robomuss.rc.track.TrackHandler;
import robomuss.rc.track.TrackManager;
import robomuss.rc.track.piece.TrackPiece;
import robomuss.rc.util.IInventoryRenderSettings;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;

import javax.swing.text.JTextComponent;

public class ClientProxy extends CommonProxy {
	
	@SuppressWarnings("unused")
	@Override
	public void initRenderers() {
		for(TrackPiece track : TrackHandler.pieces) {
			ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTrackBase.class, new TileEntityRenderTrack());
		}
        
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityRideFence.class, new TileEntityRenderRideFence());
		
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySupport.class, new TileEntityRenderSupport());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityWoodenSupport.class, new TileEntityRenderWoodenSupport());
		
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityFooter.class, new TileEntityRenderFooter());
		
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTrackFabricator.class, new TileEntityRenderTrackFabricator());
		
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityConveyor.class, new TileEntityRenderConveyor());
		
        RenderingRegistry.registerEntityRenderingHandler(EntityTrain.class, new RenderTrain());
        
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(RCBlocks.track_fabricator), new ItemRenderTrackFabricator());

		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(RCBlocks.conveyor), new ItemRenderConveyor());
        
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(RCBlocks.support), new ItemRenderSupport());
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(RCBlocks.woodenSupport), new ItemRenderWoodenSupport());
        
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(RCBlocks.footer), new ItemRenderFooter());
        
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(RCBlocks.ride_fence), new ItemRenderFence());
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(RCBlocks.ride_fence_corner), new ItemRenderFence());
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(RCBlocks.ride_fence_triangle), new ItemRenderFence());
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(RCBlocks.ride_fence_square), new ItemRenderFence());
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(RCBlocks.ride_fence_gate), new ItemRenderFence());
        
        for(TrackPiece track : TrackHandler.pieces) {

	        //TODO: fix item rendering!!!
        	boolean useIcon = true;
        	if(track instanceof IInventoryRenderSettings) {
        		useIcon = ((IInventoryRenderSettings) track).useIcon();
        	}
        	if(!useIcon) {
				MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(track.block), new ItemRenderTrack());
        	}
        }
	}

//    @Override
//    public void registerKeybindings()
//    {
//        Keybindings.init();
//        ClientRegistry.registerKeyBinding(Keybindings.lookLeft);
//        ClientRegistry.registerKeyBinding(Keybindings.lookRight);
//        ClientRegistry.registerKeyBinding(Keybindings.down);
//        ClientRegistry.registerKeyBinding(Keybindings.up);
//
//    }
}
