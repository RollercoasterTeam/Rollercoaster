package robomuss.rc.block;


import net.minecraft.block.Block;
import robomuss.rc.RCMod;
import robomuss.rc.block.te.TileEntityBin;
import robomuss.rc.block.te.TileEntityCatwalk;
import robomuss.rc.block.te.TileEntityFooter;
import robomuss.rc.block.te.TileEntityRideFence;
import robomuss.rc.block.te.TileEntityRideSign;
import robomuss.rc.block.te.TileEntitySupport;
import robomuss.rc.block.te.TileEntityTrack;
import robomuss.rc.block.te.TileEntityTrackDesigner;
import robomuss.rc.block.te.TileEntityTrackFabricator;
import robomuss.rc.block.te.TileEntityTrackStorage;
import robomuss.rc.block.te.TileEntityWoodenSupport;
import robomuss.rc.item.ItemBlockPath;
import robomuss.rc.track.TrackHandler;
import robomuss.rc.track.TrackType;
import cpw.mods.fml.common.registry.GameRegistry;

public class RCBlocks {

	public static int last_track_id;
	public static Block support, woodenSupport, path, iron_fencing, picket_fencing, ride_fence, ride_fence_corner, ride_fence_triangle, ride_fence_square, ride_fence_gate, ride_fence_panel, track_designer, track_fabricator, track_storage, catwalk;
	public static Block footer, ride_sign, wooden_bin;
	
	public static void init() {
		for(TrackType track: TrackHandler.tracks) {
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
        iron_fencing = new BlockFencing("iron_fencing").setBlockName("iron_fencing").setCreativeTab(RCMod.decor);
        picket_fencing = new BlockFencing("picket_fencing").setBlockName("picket_fencing").setCreativeTab(RCMod.decor);
		
		ride_fence = new BlockRideFence().setBlockName("ride_fence").setBlockTextureName("rc:ride_fence").setCreativeTab(RCMod.decor);
		ride_fence_corner = new BlockRideFence().setBlockName("ride_fence_corner").setBlockTextureName("rc:ride_fence_corner").setCreativeTab(RCMod.decor);
		ride_fence_triangle = new BlockRideFence().setBlockName("ride_fence_triangle").setBlockTextureName("rc:ride_fence_triangle").setCreativeTab(RCMod.decor);
		ride_fence_square = new BlockRideFence().setBlockName("ride_fence_square").setBlockTextureName("rc:ride_fence_square").setCreativeTab(RCMod.decor);
		ride_fence_gate = new BlockRideFence().setBlockName("ride_fence_gate").setBlockTextureName("rc:ride_fence_gate").setCreativeTab(RCMod.decor);
		ride_fence_panel = new BlockRideFence().setBlockName("ride_fence_panel").setBlockTextureName("rc:ride_fence_panel").setCreativeTab(RCMod.decor);
		
		footer = new BlockFooter().setBlockName("footer").setBlockTextureName("rc:footer").setCreativeTab(RCMod.track);
		
		catwalk = new BlockCatwalk().setBlockName("catwalk").setBlockTextureName("rc:catwalk").setCreativeTab(RCMod.track);
		
		ride_sign = new BlockRideSign().setBlockName("ride_sign").setBlockTextureName("rc:ride_sign").setCreativeTab(RCMod.decor);
		
		wooden_bin = new BlockBin().setBlockName("wooden_bin").setBlockTextureName("rc:wooden_bin").setCreativeTab(RCMod.decor);
		
        GameRegistry.registerBlock(support, "support");
        GameRegistry.registerBlock(woodenSupport, "woodenSupport");
        GameRegistry.registerBlock(track_designer, "track_designer");
        GameRegistry.registerBlock(track_fabricator, "track_fabricator");
        GameRegistry.registerBlock(track_storage, "track_storage");
        
        GameRegistry.registerBlock(path, ItemBlockPath.class, "path");
		GameRegistry.registerBlock(iron_fencing, "iron_fencing");
		GameRegistry.registerBlock(picket_fencing, "picket_fencing");
		
		GameRegistry.registerBlock(ride_fence, "ride_fence");
		GameRegistry.registerBlock(ride_fence_corner, "ride_fence_corner");
		GameRegistry.registerBlock(ride_fence_triangle, "ride_fence_triangle");
		GameRegistry.registerBlock(ride_fence_square, "ride_fence_square");
		GameRegistry.registerBlock(ride_fence_gate, "ride_fence_gate");
		GameRegistry.registerBlock(ride_fence_panel, "ride_fence_panel");
		
		GameRegistry.registerBlock(footer, "footer");

		GameRegistry.registerBlock(catwalk, "catwalk");
		
		GameRegistry.registerBlock(ride_sign, "ride_sign");
		
		GameRegistry.registerBlock(wooden_bin, "wooden_bin");
        
        GameRegistry.registerTileEntity(TileEntityTrackDesigner.class, "te_track_designer");
        GameRegistry.registerTileEntity(TileEntityTrackFabricator.class, "te_track_fabricator");
        GameRegistry.registerTileEntity(TileEntityTrackStorage.class, "te_track_storage");
        
		GameRegistry.registerTileEntity(TileEntityRideFence.class, "te_ride_fence");
		
		GameRegistry.registerTileEntity(TileEntitySupport.class, "te_support");
		GameRegistry.registerTileEntity(TileEntityWoodenSupport.class, "te_woodenSupport");
		
		GameRegistry.registerTileEntity(TileEntityFooter.class, "te_footer");
		
		GameRegistry.registerTileEntity(TileEntityCatwalk.class, "te_catwalk");
		
		GameRegistry.registerTileEntity(TileEntityRideSign.class, "te_ride_sign");
		
		GameRegistry.registerTileEntity(TileEntityBin.class, "te_bin");
	}

}
