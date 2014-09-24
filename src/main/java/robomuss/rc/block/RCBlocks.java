package robomuss.rc.block;


import net.minecraft.block.Block;
import robomuss.rc.RCMod;
import robomuss.rc.block.te.TileEntityFooter;
import robomuss.rc.block.te.TileEntityRideFence;
import robomuss.rc.block.te.TileEntitySupport;
import robomuss.rc.block.te.TileEntityTrack;
import robomuss.rc.block.te.TileEntityTrackDesigner;
import robomuss.rc.block.te.TileEntityTrackFabricator;
import robomuss.rc.block.te.TileEntityTrackStorage;
import robomuss.rc.block.te.TileEntityWoodenSupport;
import robomuss.rc.item.ItemBlockPath;
import robomuss.rc.track.TrackHandler;
import robomuss.rc.track.TrackPiece;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

public class RCBlocks {

	public static int last_track_id;
	public static Block support, woodenSupport, path, railings, picket, ride_fence, ride_fence_corner, ride_fence_triangle, ride_fence_square, ride_fence_gate, track_designer, track_fabricator, track_storage;
	public static Block footer;
	
	public static void init() {
		for(TrackPiece track: TrackHandler.pieces) {
			track.block = new BlockTrack(track).setBlockName(track.unlocalized_name + "_track").setBlockTextureName("rc:tracks/" + track.unlocalized_name).setCreativeTab(RCMod.track);
			GameRegistry.registerBlock(track.block, track.unlocalized_name + "_track");
		}
		GameRegistry.registerTileEntity(TileEntityTrack.class, "te_track");
        
		support = new BlockSupport().setBlockName("support").setBlockTextureName("rc:support").setCreativeTab(RCMod.track);
		woodenSupport = new BlockWoodenSupport().setBlockName("wooden_support").setBlockTextureName("rc:wooden_support").setCreativeTab(RCMod.track);
		track_designer = new BlockTrackDesigner().setBlockName("track_designer").setBlockTextureName("rc:track_designer").setCreativeTab(RCMod.track);
        track_fabricator = new BlockTrackFabricator().setBlockName("track_fabricator").setCreativeTab(RCMod.track);
        track_storage = new BlockTrackStorage().setBlockName("track_storage").setCreativeTab(RCMod.track);
		
        path = new BlockPath().setBlockName("path").setCreativeTab(RCMod.decor);
		railings = new BlockRailings("railings").setBlockName("railings").setCreativeTab(RCMod.decor);
		picket = new BlockRailings("picket").setBlockName("picket").setCreativeTab(RCMod.decor);
		
		ride_fence = new BlockRideFence().setBlockName("ride_fence").setBlockTextureName("rc:ride_fence").setCreativeTab(RCMod.decor);
		ride_fence_corner = new BlockRideFence().setBlockName("ride_fence_corner").setBlockTextureName("rc:ride_fence_corner").setCreativeTab(RCMod.decor);
		ride_fence_triangle = new BlockRideFence().setBlockName("ride_fence_triangle").setBlockTextureName("rc:ride_fence_triangle").setCreativeTab(RCMod.decor);
		ride_fence_square = new BlockRideFence().setBlockName("ride_fence_square").setBlockTextureName("rc:ride_fence_square").setCreativeTab(RCMod.decor);
		ride_fence_gate = new BlockRideFence().setBlockName("ride_fence_gate").setBlockTextureName("rc:ride_fence_gate").setCreativeTab(RCMod.decor);
		
		footer = new BlockFooter().setBlockName("footer").setBlockTextureName("rc:footer").setCreativeTab(RCMod.track);
		
        GameRegistry.registerBlock(support, "support");
        GameRegistry.registerBlock(woodenSupport, "woodenSupport");
        GameRegistry.registerBlock(track_designer, "track_designer");
        GameRegistry.registerBlock(track_fabricator, "track_fabricator");
        GameRegistry.registerBlock(track_storage, "track_storage");
        
        GameRegistry.registerBlock(path, ItemBlockPath.class, "path");
		GameRegistry.registerBlock(railings, "railings");
		GameRegistry.registerBlock(picket, "picket");
		
		GameRegistry.registerBlock(ride_fence, "ride_fence");
		GameRegistry.registerBlock(ride_fence_corner, "ride_fence_corner");
		GameRegistry.registerBlock(ride_fence_triangle, "ride_fence_triangle");
		GameRegistry.registerBlock(ride_fence_square, "ride_fence_square");
		GameRegistry.registerBlock(ride_fence_gate, "ride_fence_gate");
		
		GameRegistry.registerBlock(footer, "footer");
        
        GameRegistry.registerTileEntity(TileEntityTrackDesigner.class, "te_track_designer");
        GameRegistry.registerTileEntity(TileEntityTrackFabricator.class, "te_track_fabricator");
        GameRegistry.registerTileEntity(TileEntityTrackStorage.class, "te_track_storage");
        
		GameRegistry.registerTileEntity(TileEntityRideFence.class, "te_ride_fence");
		
		GameRegistry.registerTileEntity(TileEntitySupport.class, "te_support");
		GameRegistry.registerTileEntity(TileEntityWoodenSupport.class, "te_woodenSupport");
		
		GameRegistry.registerTileEntity(TileEntityFooter.class, "te_footer");
	}

}
