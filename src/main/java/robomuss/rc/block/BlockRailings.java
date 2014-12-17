package robomuss.rc.block;

<<<<<<< HEAD
=======
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
>>>>>>> FETCH_HEAD
import net.minecraft.block.BlockPane;
import net.minecraft.block.material.Material;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockRailings extends BlockPane {
	public BlockRailings(String string) {
		super(Material.iron, true);
		setHardness(1f);
		setResistance(3f);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getBlockColor() {
		return 0;
	}
}
