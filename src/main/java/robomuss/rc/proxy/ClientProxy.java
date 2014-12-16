package robomuss.rc.proxy;

<<<<<<< HEAD

import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import robomuss.rc.block.render.*;
import robomuss.rc.block.te.*;
=======
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
import robomuss.rc.block.te.TileEntityConveyor;
import robomuss.rc.block.te.TileEntityFooter;
import robomuss.rc.block.te.TileEntityRideFence;
import robomuss.rc.block.te.TileEntitySupport;
import robomuss.rc.block.te.TileEntityTrack;
import robomuss.rc.block.te.TileEntityTrackFabricator;
import robomuss.rc.block.te.TileEntityWoodenSupport;
>>>>>>> master
import robomuss.rc.client.Keybindings;
import robomuss.rc.entity.EntityTrain;
import robomuss.rc.entity.RenderTrain;
import robomuss.rc.track.TrackHandler;
import robomuss.rc.track.piece.TrackPiece;
import robomuss.rc.util.IInventoryRenderSettings;

public class ClientProxy extends CommonProxy {
	
	@SuppressWarnings("unused")
	@Override
	public void initRenderers() {
		for(TrackPiece track : TrackHandler.pieces) {
			ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTrack.class, new TileEntityRenderTrack());
		}
        
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityRideFence.class, new TileEntityRenderRideFence());
		
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySupport.class, new TileEntityRenderSupport());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityWoodenSupport.class, new TileEntityRenderWoodenSupport());
		
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityFooter.class, new TileEntityRenderFooter());
		
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTrackFabricator.class, new TileEntityRenderTrackFabricator());
		
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityConveyor.class, new TileEntityRenderConveyor());
		
        RenderingRegistry.registerEntityRenderingHandler(EntityTrain.class, new RenderTrain());

        //TODO forge
//        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(RCBlocks.track_fabricator), new ItemRenderTrackFabricator());
//
//        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(RCBlocks.support), new ItemRenderSupport());
//        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(RCBlocks.woodenSupport), new ItemRenderWoodenSupport());
//
//        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(RCBlocks.footer), new ItemRenderFooter());
//
//        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(RCBlocks.ride_fence), new ItemRenderFence());
//        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(RCBlocks.ride_fence_corner), new ItemRenderFence());
//        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(RCBlocks.ride_fence_triangle), new ItemRenderFence());
//        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(RCBlocks.ride_fence_square), new ItemRenderFence());
//        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(RCBlocks.ride_fence_gate), new ItemRenderFence());
//
        for(TrackPiece track : TrackHandler.pieces) {
        	boolean useIcon = false;
        	if(track instanceof IInventoryRenderSettings) {
        		useIcon = ((IInventoryRenderSettings) track).useIcon();
        	}
        	if(!useIcon) {
                //TODO forge
        		//MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(track.block), new ItemRenderTrack());
        	}
        }
	}

    @Override
    public void registerKeybindings()
    {
        Keybindings.init();
        ClientRegistry.registerKeyBinding(Keybindings.lookLeft);
        ClientRegistry.registerKeyBinding(Keybindings.lookRight);
        ClientRegistry.registerKeyBinding(Keybindings.down);
        ClientRegistry.registerKeyBinding(Keybindings.up);

    }
}
