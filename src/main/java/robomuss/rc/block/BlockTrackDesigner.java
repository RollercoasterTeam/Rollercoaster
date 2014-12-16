package robomuss.rc.block;

<<<<<<< HEAD

=======
import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
>>>>>>> master
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;

import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.internal.FMLNetworkHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import robomuss.rc.RCMod;
import robomuss.rc.block.te.TileEntityTrackDesigner;

public class BlockTrackDesigner extends BlockContainer {

	public BlockTrackDesigner() {
		super(Material.rock);
		setHardness(1F);
		setResistance(3F);
	}

	@Override
<<<<<<< HEAD
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		//if(!world.isRemote) {
			FMLNetworkHandler.openGui(player, RCMod.instance, 0, world, x, y, z);
			return true;
		///}
		//else {
			//return false;
		//}
=======
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		FMLNetworkHandler.openGui(player, RCMod.instance, 0, world, x, y, z);
		return true;
>>>>>>> master
	}

    //TODO things for blocks
//	@SideOnly(Side.CLIENT)
//	public IIcon other;
//	@SideOnly(Side.CLIENT)
//	public IIcon side;
	
<<<<<<< HEAD
//	@Override
//	@SideOnly(Side.CLIENT)
//	public void registerBlockIcons(IIconRegister icon) {
//		other = icon.registerIcon("rc:track_designer_other");
//		side = icon.registerIcon("rc:track_designer_side");
//	}
//
//	@Override
//	@SideOnly(Side.CLIENT)
//	public IIcon getIcon(int face, int meta) {
//		if(face == 0 || face == 1) {
//			return other;
//		}
//		else {
//			return side;
//		}
//	}
=======
	@SideOnly(Side.CLIENT)
	public IIcon other;
	@SideOnly(Side.CLIENT)
	public IIcon side;
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister icon) {
		other = icon.registerIcon("rc:track_designer_other");
		side = icon.registerIcon("rc:track_designer_side");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int face, int meta) {
		if(face == 0 || face == 1) {
			return other;
		} else {
			return side;
		}
	}
>>>>>>> master

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileEntityTrackDesigner();
    }
}
