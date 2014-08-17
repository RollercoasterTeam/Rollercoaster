package robomuss.rc.gui;

import java.io.IOException;

import robomuss.rc.block.RCBlocks;
import robomuss.rc.block.te.TileEntityTrack;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;

public class GuiTrackBuilder extends GuiScreen {

	private EntityPlayer player;
	private World world;
	private int x;
	private int y;
	private int z;
	private int direction;
	
	public GuiTrackBuilder(EntityPlayer player, World world, int x, int y, int z) {
		this.player = player;
		this.world = world;
		this.x = x;
		this.y = y;
		this.z = z;
		//TileEntityTrackBuilder trackbuilder = (TileEntityTrack) world.getTileEntity(x, y, z);
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void initGui() {
		buttonList.clear();
		
		buttonList.add(new GuiButton(0, 10, 10, 20, 20, "|"));
		buttonList.add(new GuiButton(1, 40, 10, 20, 20, "_/"));
		buttonList.add(new GuiButton(2, 70, 10, 20, 20, "/"));
		buttonList.add(new GuiButton(3, 100, 10, 20, 20, "/-"));
		buttonList.add(new GuiButton(4, 130, 10, 20, 20, new String("\\").substring(0, 1) + "_"));
		buttonList.add(new GuiButton(5, 160, 10, 20, 20, new String("\\").substring(0, 1)));
		buttonList.add(new GuiButton(6, 190, 10, 20, 20, new String("-\\").substring(0, 2)));
		buttonList.add(new GuiButton(7, 220, 10, 20, 20, "¬"));
		buttonList.add(new GuiButton(8, 250, 10, 20, 20, "o"));
		
		buttonList.add(new GuiButton(9, 10, 40, 20, 20, "< >"));
		buttonList.add(new GuiButton(10, 40, 40, 50, 20, "Build"));
	}
	
	@Override
	public void drawScreen(int par1, int par2, float par3) {
		super.drawScreen(par1, par2, par3);
		
		drawString(fontRendererObj, "Direction: " + direction, 10, 70, 0xFFFFFF);
	}
	
	@Override
	protected void actionPerformed(GuiButton button) {
		int id = button.id;
		
		Packet packet = new Packet() {
			
			@Override
			public void writePacketData(PacketBuffer pb) throws IOException {
				
			}
			
			@Override
			public void readPacketData(PacketBuffer pb) throws IOException {
				
			}
			
			@Override
			public void processPacket(INetHandler handler) {
				
			}
		};
		
		if(id == 0) {
			//world.setBlock(x, y, z, RCBlocks.flat_track);
			TileEntityTrack track = (TileEntityTrack) world.getTileEntity(x, y, z);
			track.direction = direction;
			world.markBlockForUpdate(x, y, z);
			if(direction == 0) {
				z += 1;
			}
			else if(direction == 1) {
				x += 1;
			}
			else if(direction == 2) {
				z -= 1;
			}
			else if(direction == 3) {
				x -= 1;
			}
		}
		
		if(id == 1 || id == 2 || id == 3) {
			//world.setBlock(x, y, z, (id == 1 ? RCBlocks.transition_track : id == 2 ? RCBlocks.sloped_track : RCBlocks.transition_track_2));
			TileEntityTrack track = (TileEntityTrack) world.getTileEntity(x, y, z);
			track.direction = direction;
			world.markBlockForUpdate(x, y, z);
			if(direction == 0) {
				z += 1;
				y += 1;
			}
			else if(direction == 1) {
				x -= 1;
				y += id > 3 ? -1 : 1;
			}
			else if(direction == 2) {
				z -= 1;
				y += id > 3 ? -1 : 1;
			}
			else if(direction == 3) {
				x -= 1;
				y += id > 3 ? -1 : 1;
			}
		}
		
		if(id == 4 || id == 5 || id == 6) {
			if(id == 6) {
				y -= 1;
			}
			//world.setBlock(x, y, z, (id == 4 ? RCBlocks.transition_track : id == 5 ? RCBlocks.sloped_track : RCBlocks.transition_track_2));
			TileEntityTrack track = (TileEntityTrack) world.getTileEntity(x, y, z);
			track.direction = direction;
			world.markBlockForUpdate(x, y, z);
			if(direction == 0) {
				z += 1;
				y += 1;
			}
			else if(direction == 1) {
				x -= 1;
				y += id > 3 ? -1 : 1;
			}
			else if(direction == 2) {
				z += 1;
				y += id > 3 ? -1 : 1;
			}
			else if(direction == 3) {
				x -= 1;
				y += id > 3 ? -1 : 1;
			}
			if(id == 4) {
				y += 1;
			}	
		}
		
		if(id == 7) {
			//world.setBlock(x, y, z, RCBlocks.curved_track);
			TileEntityTrack track = (TileEntityTrack) world.getTileEntity(x, y, z);
			track.direction = direction;
			world.markBlockForUpdate(x, y, z);
			if(direction == 0) {
				x -= 1;
				direction = 3;
			}
			else if(direction == 1) {
				direction = 1;
				x += 1;
			}
			else if(direction == 2) {
				x += 1;
				direction = 1;
			}
			else if(direction == 3) {
				z += 1;
				direction = 0;
			}
		}
		
		if(id == 8) {
			//world.setBlock(x, y, z, RCBlocks.loop);
			TileEntityTrack track = (TileEntityTrack) world.getTileEntity(x, y, z);
			track.direction = direction;
			world.markBlockForUpdate(x, y, z);
			
			x -= 1;
			z += 1;
		}
		
		if(id == 9) {
			if(direction == 3) {
				direction = 0;
			}
			else {
				direction += 1;
			}
		}
	}

	private void change(int id) {
		/*player.posX = x;
		player.posY = y + 1;
		player.posZ = z;*/
	}
}
