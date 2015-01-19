package robomuss.rc.multiblock;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public interface IMultiBlockStructure {
	public boolean isStructureFormed(int id, TileEntity tileEntity);
	public boolean checkStructure(int id, World world, int x, int y, int z, ForgeDirection direction, MultiBlockStructure structure);
	public void registerStructure();
	public MultiBlockStructure getStructure(int id);
}
