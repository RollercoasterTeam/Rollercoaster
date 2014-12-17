package robomuss.rc.block;

import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import robomuss.rc.RCMod;
import robomuss.rc.block.te.*;
import robomuss.rc.item.ItemBlockPath;
import robomuss.rc.item.ItemBlockTrack;
import robomuss.rc.track.TrackHandler;
import robomuss.rc.track.piece.TrackPiece;

public class RCBlocks {
	public static int last_track_id;
	public static Block support, woodenSupport, footer;
	public static Block path;
	public static Block railings, picket;
	public static Block ride_fence, ride_fence_corner, ride_fence_triangle, ride_fence_square, ride_fence_gate;
	public static Block track_designer, track_fabricator, track_storage;
	public static Block track_fabricator_casing, track_fabricator_glass, track_fabricator_output;
	public static Block conveyor;

	public static void init() {
		for (TrackPiece track : TrackHandler.pieces) {
			track.block = new BlockTrackBase(track).setUnlocalizedName(track.unlocalized_name + "_track").setCreativeTab(RCMod.track);
			GameRegistry.registerBlock(track.block, ItemBlockTrack.class, track.unlocalized_name + "_track");
		}

        GameRegistry.registerTileEntity(TileEntityTrackBase.class, "te_track");

		support = new BlockSupport().setUnlocalizedName("support").setCreativeTab(RCMod.track);
		footer = new BlockFooter().setUnlocalizedName("footer").setCreativeTab(RCMod.track);
		woodenSupport = new BlockWoodenSupport().setUnlocalizedName("wooden_support").setCreativeTab(RCMod.track);
		
        path = new BlockPath().setUnlocalizedName("path").setCreativeTab(RCMod.decor);
	
        railings = new BlockRailings("railings").setUnlocalizedName("railings").setCreativeTab(RCMod.decor);
		picket = new BlockRailings("picket").setUnlocalizedName("picket").setCreativeTab(RCMod.decor);
		
		ride_fence = new BlockRideFence().setUnlocalizedName("ride_fence").setCreativeTab(RCMod.decor);
		ride_fence_corner = new BlockRideFence().setUnlocalizedName("ride_fence_corner").setCreativeTab(RCMod.decor);
		ride_fence_triangle = new BlockRideFence().setUnlocalizedName("ride_fence_triangle").setCreativeTab(RCMod.decor);
		ride_fence_square = new BlockRideFence().setUnlocalizedName("ride_fence_square").setCreativeTab(RCMod.decor);
		ride_fence_gate = new BlockRideFence().setUnlocalizedName("ride_fence_gate").setCreativeTab(RCMod.decor);
        
		track_designer = new BlockTrackDesigner().setUnlocalizedName("track_designer").setCreativeTab(RCMod.track);
        track_fabricator = new BlockTrackFabricator().setUnlocalizedName("track_fabricator").setCreativeTab(RCMod.track);
        track_storage = new BlockTrackStorage().setUnlocalizedName("track_storage").setCreativeTab(RCMod.track);

		track_fabricator_casing = new BlockSimple(Material.iron).setUnlocalizedName("track_fabricator_casing").setCreativeTab(RCMod.track);
		track_fabricator_glass = new BlockSimple(Material.glass).setUnlocalizedName("track_fabricator_glass").setCreativeTab(RCMod.track);
		track_fabricator_output = new BlockSimple(Material.iron).setUnlocalizedName("track_fabricator_output").setCreativeTab(RCMod.track);
		
		conveyor = new BlockConveyor().setUnlocalizedName("conveyor").setCreativeTab(RCMod.track);
		
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
		
		GameRegistry.registerTileEntity(TileEntityConveyor.class, "te_conveyor");
	}
}
