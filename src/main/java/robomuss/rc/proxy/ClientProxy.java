package robomuss.rc.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import robomuss.rc.RCMod;
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
import robomuss.rc.block.te.TileEntityTrackBase;
import robomuss.rc.block.te.TileEntityTrackFabricator;
import robomuss.rc.block.te.TileEntityWoodenSupport;
import robomuss.rc.client.gui.keybinding.TrackDesignerKeyBindings;
import robomuss.rc.client.renderer.ItemRenderBalloon;
import robomuss.rc.client.renderer.ItemRenderConveyor;
import robomuss.rc.client.renderer.ItemRenderFence;
import robomuss.rc.client.renderer.ItemRenderFooter;
import robomuss.rc.client.renderer.ItemRenderSupport;
import robomuss.rc.client.renderer.ItemRenderTrack;
import robomuss.rc.client.renderer.ItemRenderTrackFabricator;
import robomuss.rc.client.renderer.ItemRenderWoodenSupport;
import robomuss.rc.entity.EntityTrain;
import robomuss.rc.entity.RenderTrain;
import robomuss.rc.item.RCItems;
import robomuss.rc.track.TrackHandler;
import robomuss.rc.track.piece.TrackPiece;
import robomuss.rc.util.IInventoryRenderSettings;

public class ClientProxy extends CommonProxy {
	
	@SuppressWarnings("unused")
	@Override
	public void initRenderers() {
		/** TRACK PIECES */
		for(TrackPiece track : TrackHandler.pieces) {
			ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTrackBase.class, new TileEntityRenderTrack());
		}

		/** BLOCKS */
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityRideFence.class, new TileEntityRenderRideFence());                   //FENCE
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySupport.class, new TileEntityRenderSupport());                       //SUPPORT
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityWoodenSupport.class, new TileEntityRenderWoodenSupport());           //WOOD SUPPORT
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityFooter.class, new TileEntityRenderFooter());                         //FOOTER
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTrackFabricator.class, new TileEntityRenderTrackFabricator());       //TRACK FABRICATOR
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityConveyor.class, new TileEntityRenderConveyor());                     //CONVEYOR

		/** TRAIN */
        RenderingRegistry.registerEntityRenderingHandler(EntityTrain.class, new RenderTrain());

		/** ITEMS */
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(RCBlocks.ride_fence), new ItemRenderFence());
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(RCBlocks.ride_fence_corner), new ItemRenderFence());
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(RCBlocks.ride_fence_triangle), new ItemRenderFence());
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(RCBlocks.ride_fence_square), new ItemRenderFence());
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(RCBlocks.ride_fence_gate), new ItemRenderFence());

        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(RCBlocks.support), new ItemRenderSupport());
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(RCBlocks.woodenSupport), new ItemRenderWoodenSupport());
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(RCBlocks.footer), new ItemRenderFooter());
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(RCBlocks.track_fabricator), new ItemRenderTrackFabricator());
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(RCBlocks.conveyor), new ItemRenderConveyor());

		MinecraftForgeClient.registerItemRenderer(RCItems.balloon, new ItemRenderBalloon(Minecraft.getMinecraft().thePlayer));

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

//		FMLCommonHandler.instance().bus().register(new RCTickHandler(Minecraft.getMinecraft()));
	}

    @Override
    public void registerKeybindings() {
	    TrackDesignerKeyBindings.registerBlankKeys();
	    TrackDesignerKeyBindings.init();
	    RCMod.rcOptions.loadRCOptions();
//	    Minecraft.getMinecraft().gameSettings.loadOptions();
    }
}
