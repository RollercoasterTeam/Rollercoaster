package robomuss.rc.block;


import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import robomuss.rc.RCMod;
import robomuss.rc.block.te.*;
import robomuss.rc.item.ItemBlockPath;
import robomuss.rc.track.TrackHandler;
import robomuss.rc.track.piece.TrackPiece;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

public class RCBlocks {

	public static int last_track_id;
	public static Block support, woodenSupport, footer;
	public static Block path;
	public static Block railings, picket;
	public static Block ride_fence, ride_fence_corner, ride_fence_triangle, ride_fence_square, ride_fence_gate; 
	public static Block track_designer, track_fabricator, track_storage;
//	public static Block dummy;
	public static Block track_fabricator_casing, track_fabricator_glass, track_fabricator_output;
	public static Block conveyor;
	public static Block[] testTrackArray;
	
	public static void init() {
//		for(TrackPiece track: TrackHandler.pieces) {
//			track.block = new BlockTrack(track).setBlockName(track.unlocalized_name + "_track").setBlockTextureName("rc:tracks/" + track.unlocalized_name).setCreativeTab(RCMod.track);
//			GameRegistry.registerBlock(track.block, track.unlocalized_name + "_track");
//		}
		for(TrackPiece track: TrackHandler.pieces) {
			track.block = new BlockTrackBase(track).setBlockName(track.unlocalized_name + "_track").setBlockTextureName("rc:tracks/" + track.unlocalized_name).setCreativeTab(RCMod.track);
			GameRegistry.registerBlock(track.block, track.unlocalized_name + "_track");
		}

//		testTrackArray = new Block[TrackHandler.pieces.size()];
//
//		for (int i = 0; i < TrackHandler.pieces.size(); i++) {
//			TrackPiece track = TrackHandler.pieces.get(i);
//			testTrackArray[i] = new BlockTrackBase(track).setBlockName(track.unlocalized_name + "_track").setBlockTextureName("rc:tracks/" + track.unlocalized_name).setCreativeTab(RCMod.track);
//			TrackHandler.pieces.get(i).block = testTrackArray[i];
//			GameRegistry.registerBlock(testTrackArray[i], track.unlocalized_name + "_track");
//		}

//		GameRegistry.registerTileEntity(TileEntityTrack.class, "te_track");
        GameRegistry.registerTileEntity(TileEntityTrackBase.class, "te_track");

		support = new BlockSupport().setBlockName("support").setBlockTextureName("rc:support").setCreativeTab(RCMod.track);
		footer = new BlockFooter().setBlockName("footer").setBlockTextureName("rc:footer").setCreativeTab(RCMod.track);
		woodenSupport = new BlockWoodenSupport().setBlockName("wooden_support").setBlockTextureName("rc:wooden_support").setCreativeTab(RCMod.track);
		
        path = new BlockPath().setBlockName("path").setCreativeTab(RCMod.decor);
	
        railings = new BlockRailings("railings").setBlockName("railings").setCreativeTab(RCMod.decor);
		picket = new BlockRailings("picket").setBlockName("picket").setCreativeTab(RCMod.decor);
		
		ride_fence = new BlockRideFence().setBlockName("ride_fence").setBlockTextureName("rc:ride_fence").setCreativeTab(RCMod.decor);
		ride_fence_corner = new BlockRideFence().setBlockName("ride_fence_corner").setBlockTextureName("rc:ride_fence_corner").setCreativeTab(RCMod.decor);
		ride_fence_triangle = new BlockRideFence().setBlockName("ride_fence_triangle").setBlockTextureName("rc:ride_fence_triangle").setCreativeTab(RCMod.decor);
		ride_fence_square = new BlockRideFence().setBlockName("ride_fence_square").setBlockTextureName("rc:ride_fence_square").setCreativeTab(RCMod.decor);
		ride_fence_gate = new BlockRideFence().setBlockName("ride_fence_gate").setBlockTextureName("rc:ride_fence_gate").setCreativeTab(RCMod.decor);
        
		track_designer = new BlockTrackDesigner().setBlockName("track_designer").setBlockTextureName("rc:track_designer").setCreativeTab(RCMod.track);
        track_fabricator = new BlockTrackFabricator().setBlockName("track_fabricator").setCreativeTab(RCMod.track);
        track_storage = new BlockTrackStorage().setBlockName("track_storage").setCreativeTab(RCMod.track);

//		dummy = new BlockDummy().setBlockName("dummy").setBlockTextureName("rc:dummy");

		track_fabricator_casing = new BlockSimple(Material.iron).setBlockName("track_fabricator_casing").setBlockTextureName("rc:track_fabricator_casing").setCreativeTab(RCMod.track);
		track_fabricator_glass = new BlockSimple(Material.glass).setBlockName("track_fabricator_glass").setBlockTextureName("rc:track_fabricator_glass").setCreativeTab(RCMod.track);
		track_fabricator_output = new BlockSimple(Material.iron).setBlockName("track_fabricator_output").setBlockTextureName("rc:track_fabricator_output").setCreativeTab(RCMod.track);
		
		conveyor = new BlockConveyor().setBlockName("conveyor").setBlockTextureName("rc:conveyor").setCreativeTab(RCMod.track);
		
        GameRegistry.registerBlock(support, "support");
        GameRegistry.registerBlock(woodenSupport, "woodenSupport");
		GameRegistry.registerBlock(footer, "footer");
		
        GameRegistry.registerBlock(path, ItemBlockPath.class, "path");
		
		GameRegistry.registerBlock(railings, "railings");
		GameRegistry.registerBlock(picket, "picket");
	       
		GameRegistry.registerBlock(ride_fence, "ride_fence");
		GameRegistry.registerBlock(ride_fence_corner, "ride_fence_corner");
		GameRegistry.registerBlock(ride_fence_triangle, "ride_fence_triangle");
		GameRegistry.registerBlock(ride_fence_square, "ride_fence_square");
		GameRegistry.registerBlock(ride_fence_gate, "ride_fence_gate");
        
        GameRegistry.registerBlock(track_designer, "track_designer");
        GameRegistry.registerBlock(track_fabricator, "track_fabricator");
        GameRegistry.registerBlock(track_storage, "track_storage");

//		GameRegistry.registerBlock(dummy, "dummy");
		
		GameRegistry.registerBlock(track_fabricator_casing, "track_fabricator_casing");
		GameRegistry.registerBlock(track_fabricator_glass, "track_fabricator_glass");
		GameRegistry.registerBlock(track_fabricator_output, "track_fabricator_output");

		GameRegistry.registerBlock(conveyor, "conveyor");
		
        GameRegistry.registerTileEntity(TileEntityTrackDesigner.class, "te_track_designer");
        GameRegistry.registerTileEntity(TileEntityTrackFabricator.class, "te_track_fabricator");
        GameRegistry.registerTileEntity(TileEntityTrackStorage.class, "te_track_storage");
        
		GameRegistry.registerTileEntity(TileEntityRideFence.class, "te_ride_fence");
		
		GameRegistry.registerTileEntity(TileEntitySupport.class, "te_support");
		GameRegistry.registerTileEntity(TileEntityWoodenSupport.class, "te_woodenSupport");
		
		GameRegistry.registerTileEntity(TileEntityFooter.class, "te_footer");

//		GameRegistry.registerTileEntity(TileEntityDummy.class, "te_dummy");
		
		GameRegistry.registerTileEntity(TileEntityConveyor.class, "te_conveyor");
	}

}
