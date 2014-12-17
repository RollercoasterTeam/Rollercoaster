package robomuss.rc.block.te;

import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.util.BlockSnapshot;
import robomuss.rc.block.BlockTrackBase;
import robomuss.rc.track.TrackHandler;
import robomuss.rc.track.TrackManager;
import robomuss.rc.track.extra.TrackExtra;
import robomuss.rc.track.piece.TrackPiece;
import robomuss.rc.track.style.TrackStyle;

/*@Optional.InterfaceList(
       @Optional.Interface(iface = "pneumaticCraft.api.tileentity.IPneumaticMachine", modid = "PneumaticCraft")
)*/
public class TileEntityTrackBase extends TileEntity {
	//private IAirHandler airHandler;

	public BlockTrackBase track;
	public int trackMeta;
	public boolean converted = false;
	public boolean isDummy = false;
	public TrackExtra extra;
	public int colour = 0;
	public TrackStyle style;

	private TrackPiece trackPieceListHolder;
	private String trackPieceListID;
	private int trackPieceListIndex = -1;

	public TileEntityTrackBase() {}

	public TileEntityTrackBase(World world, int trackMeta, BlockTrackBase track) {
		this.worldObj = world;
		this.trackMeta = trackMeta;
		this.track = track;
	}

	public void setListTypeAndIndex(TrackPiece type, int index) {
		if (type != null) {
			this.trackPieceListHolder = type;
			this.trackPieceListID = type.unlocalized_name;
			this.trackPieceListIndex = index;
		}
	}

	public TrackPiece getTrackPieceListHolder() {
		return this.trackPieceListHolder;
	}

	public String getTrackPieceListID() {
		return this.trackPieceListID;
	}

	public int getTrackPieceListIndex() {
		return this.trackPieceListIndex;
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		this.colour = compound.getInteger("colour");
		this.converted = compound.getBoolean("converted");
		this.isDummy = compound.getBoolean("dummy");
		this.worldObj = super.getWorld();

		for (TrackStyle style : TrackHandler.styles) {
			if (style.getId().contains(compound.getString("styleName"))) {
				this.style = style;
			}
		}

		int extraID = compound.getInteger("extraID");
		this.extra = extraID == -1 ? null : TrackHandler.extras.get(extraID);
	}

	@Override
	public void writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);

		if (!this.converted || this.style == null) {
			this.style = TrackHandler.findTrackStyle("corkscrew");
			this.converted = true;
		}

		compound.setInteger("colour", this.colour);
		compound.setString("styleName", this.style.getId());
		compound.setBoolean("converted", this.converted);
		compound.setBoolean("dummy", this.isDummy);
		compound.setInteger("extraID", this.extra != null ? this.extra.id : -1);
	}

	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound compound = new NBTTagCompound();
		this.writeToNBT(compound);
		return new S35PacketUpdateTileEntity(this.getPos(), 1, compound);
	}

	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity packet) {
		if (this.getBlockMetadata() > 11) {
			this.isDummy = true;
		}

		readFromNBT(packet.getNbtCompound());
	}

	@SideOnly(Side.CLIENT)
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		Block block = this.getWorld().getBlockState(this.getPos()).getBlock();
		TrackPiece type = TrackHandler.findTrackTypeFull(block.getUnlocalizedName());

		return type != null ? type.getRenderBoundingBox(this.getWorld(), this.getPos()) : AxisAlignedBB.fromBounds(0, 0, 0, 1, 1, 1);
	}

	public void placeDummy() {
		if (!this.getWorld().isRemote) {
			this.track = (BlockTrackBase) this.getWorld().getBlockState(this.getPos()).getBlock();
//			this.trackMeta = this.getBlockMetadata();
			EnumFacing facing = (EnumFacing) this.getWorld().getBlockState(this.getPos()).getValue(BlockTrackBase.FACING);

			switch (facing) {
				case NORTH: this.getWorld().setBlockState(this.getPos().north(), this.track.track_type.block.getDefaultState().withProperty(BlockTrackBase.FACING, EnumFacing.NORTH)); break;
				case SOUTH: this.getWorld().setBlockState(this.getPos().south(), this.track.track_type.block.getDefaultState().withProperty(BlockTrackBase.FACING, EnumFacing.SOUTH)); break;
				case WEST:  this.getWorld().setBlockState(this.getPos().west(),  this.track.track_type.block.getDefaultState().withProperty(BlockTrackBase.FACING, EnumFacing.WEST));  break;
				case EAST:  this.getWorld().setBlockState(this.getPos().east(),  this.track.track_type.block.getDefaultState().withProperty(BlockTrackBase.FACING, EnumFacing.EAST));  break;
			}
		}
	}

	private void reorient(boolean counterClockwise, EnumFacing facing) {
		EnumFacing nextFacing = counterClockwise ? facing.rotateYCCW() : facing.rotateY();
		this.getWorld().setBlockState(this.getPos(), this.getWorld().getBlockState(this.getPos()).withProperty(BlockTrackBase.FACING, nextFacing).withProperty(BlockTrackBase.DUMMY, false), 2);
	}

	private void reorientDummies(boolean counterClockwise, EnumFacing currentFacing) {
		isDummy = (Boolean) this.getWorld().getBlockState(this.pos).getValue(BlockTrackBase.DUMMY);
		EnumFacing nextFacing = counterClockwise ? currentFacing.rotateYCCW() : currentFacing.rotateY();

		BlockPos currentPartner = isDummy ? this.getPos().offset(currentFacing.getOpposite()) : this.getPos().offset(currentFacing);
		BlockPos destPartner    = isDummy ? this.getPos().offset(nextFacing.getOpposite())    : this.getPos().offset(nextFacing);

		IBlockState stateReplaced = this.getWorld().getBlockState(destPartner);
		BlockSnapshot snapReplaced = new BlockSnapshot(this.getWorld(), destPartner, stateReplaced);

		if (!snapReplaced.world.isAirBlock(snapReplaced.pos) && !(snapReplaced.world.getBlockState(snapReplaced.pos).getBlock() instanceof BlockLiquid)) {
			snapReplaced.restore();
		} else {
			IBlockState destPartnerState = snapReplaced.world.getBlockState(currentPartner).withProperty(BlockTrackBase.FACING, nextFacing).withProperty(BlockTrackBase.DUMMY, !isDummy);
			IBlockState destSelfState    = snapReplaced.world.getBlockState(this.pos).withProperty(BlockTrackBase.FACING, nextFacing).withProperty(BlockTrackBase.DUMMY, isDummy);

			snapReplaced.world.setBlockState(this.pos, destSelfState, 2);
			snapReplaced.world.setBlockToAir(currentPartner);
			snapReplaced.world.setBlockState(destPartner, destPartnerState, 2);
		}
	}

	public void rotate(boolean counterClockwise) {
		EnumFacing currentFacing = (EnumFacing) this.worldObj.getBlockState(this.pos).getValue(BlockTrackBase.FACING);

		if (TrackManager.isSloped(TrackManager.getTrackType(this.track))) {
			this.reorientDummies(counterClockwise, currentFacing);
		} else {
			this.reorient(counterClockwise, currentFacing);
		}
	}

	/**
	 * returns the height in blocks of the hill starting or ending at the passed-in coordinates.
	 * if track at coordinates is not a slope up or slope down, will return -1.
	 */
	public int getHeightOfHill(BlockPos inputPos) {
		Block block = this.worldObj.getBlockState(inputPos).getBlock();

		if (!(block instanceof BlockTrackBase)) {
			return -1;
		} else {
			if (((BlockTrackBase) block).track_type != TrackHandler.findTrackType("slope_up") && ((BlockTrackBase) block).track_type != TrackHandler.findTrackType("slope_down")) {
				return -1;
			} else {
				int meta = this.getBlockMetadata();
				boolean isDummy = meta > 11;
				meta = isDummy ? meta - 10 : meta;

			}
		}

		return -1;
	}

	//TODO: the pneumaticcraft code will have to be updated to 1.8!
	/*@Optional.Method(modid = "PneumaticCraft")
	@Override
	public IAirHandler getAirHandler() {
		if(airHandler == null) {
			airHandler = AirHandlerSupplier.getAirHandler(5, 7, 50, 2000);
		}
		return airHandler;
	}

	@Optional.Method(modid = "PneumaticCraft")
	@Override
	public boolean isConnectedTo(ForgeDirection side) {
		if(this.extra != null && this.extra instanceof TrackExtraAirLauncher) {
			return true;
		}
		else {
			return false;
		}
	}*/

	/*@Override
	public void updateEntity() {
		super.updateEntity();
		getAirHandler().updateEntityI();
	}

	@Override
	public void validate() {
		super.validate();
		getAirHandler().validateI(this);
	}*/
}
