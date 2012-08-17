package gt.general.ingameDisplay;

import net.minecraft.server.Direction;

import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.util.Vector;
import org.getspout.spoutapi.SpoutWorld;
import org.getspout.spoutapi.player.SpoutPlayer;

public class DisplayString {
	
	private final String text;
	private final float scale;
	private final Location location;
	
	private final BlockFace direction;
	
	
	public DisplayString(final String text, final float scale, final Location location, final BlockFace direction) {
		this.text = text;
		this.scale = scale;
		this.location = location;
		this.direction = direction;
	}
	
	
	public Location getLocation() {
		return location;
	}


	public void spawnForPlayer(final SpoutPlayer spoutPlayer) {

		Location l = location.clone();
		
		for(String ch : text.split("")) {
			System.out.println(ch);
			
			spoutPlayer.spawnTextEntity(ch, l.clone(), scale, IngameDisplayManager.REFRESH_RATE -1, new Vector());
			
			switch (direction) {
			case EAST:
				l.add(scale, 0.0, 0.0);
				break;
			case WEST:
				l.add(-scale, 0.0, 0.0);
				break;
			case SOUTH:
				l.add(0.0, 0.0, scale);
				break;
			case NORTH:
				l.add(0.0, 0.0, -scale);
				break;
			default:
				break;
			}
		}
		
	}
}
