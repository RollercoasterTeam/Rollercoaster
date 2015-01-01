package robomuss.rc.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import robomuss.rc.block.te.TileEntityTrackFabricator;
import robomuss.rc.multiblock.MultiBlockManager;
import robomuss.rc.multiblock.MultiBlockTrackFabricator;

public class BlockTrackFabricator extends BlockContainer {
	public BlockTrackFabricator() {
		super(Material.iron);
		setHardness(1f);
		setResistance(3f);
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if(!world.isRemote) {
			TileEntityTrackFabricator teFab = (TileEntityTrackFabricator) world.getTileEntity(x, y, z);
			return teFab.testStruct(player);
		}

		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister icon) {
		blockIcon = icon.registerIcon("rc:track_fabricator_casing");
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		return new TileEntityTrackFabricator();
	}
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}
	
	@Override
	public int getRenderType() {
		return -1;
	}
	
	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}
	
	@Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack stack) {
        int l = MathHelper.floor_double((double) (((EntityPlayer) player).rotationYaw * 4.0f / 360.0f) + 0.5d) & 3;

		TileEntityTrackFabricator teFab = (TileEntityTrackFabricator) world.getTileEntity(x, y, z);

		switch (l) {
			case 0: teFab.direction = ForgeDirection.NORTH; break;
			case 1: teFab.direction = ForgeDirection.EAST;  break;
			case 2: teFab.direction = ForgeDirection.SOUTH; break;
			case 3: teFab.direction = ForgeDirection.WEST;  break;
		}
    }
}
