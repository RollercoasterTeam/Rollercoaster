package robomuss.rc.block;

<<<<<<< HEAD

import net.minecraft.block.BlockPane;
import net.minecraft.block.material.Material;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
=======
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockPane;
import net.minecraft.block.material.Material;
>>>>>>> master

public class BlockRailings extends BlockPane {

	public BlockRailings(String par1) {
		super( Material.iron, true);
		setHardness(1F);
		setResistance(3F);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getBlockColor() {
		return 0;
	}
}
