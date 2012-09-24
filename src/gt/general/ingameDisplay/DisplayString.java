/*******************************************************************************
 * Projektpraktikum: Game Technology 2012
 * Minecraft-Modifikation für kollaboratives Spielen
 * 
 * Sebastian Fahnenschreiber (sebastian.fahnenschreiber@stud.tu-darmstadt.de)
 * Roman Neß (roman.ness@stud.tu-darmstadt.de)
 * Philipp Pascal Battenberg (philipp.battenberg@stud.tu-darmstadt.de)
 ******************************************************************************/
package gt.general.ingameDisplay;

import static com.google.common.collect.Lists.*;

import java.util.Collection;

import org.bukkit.Location;
import org.bukkit.util.Vector;
import org.getspout.spoutapi.player.SpoutPlayer;

public class DisplayString implements DisplayStringContainer {
	
	protected final String text;
	protected final float scale;
	protected final Location location;	
	
	public DisplayString(final String text, final float scale, final Location location) {
		this.text = text;
		this.scale = scale;
		this.location = location;
	}
	
	
	public Location getLocation() {
		return location;
	}


	public void spawnForPlayer(final SpoutPlayer spoutPlayer) {

		Location l = location.clone();
		
		for(String ch : text.split("")) {	
			spoutPlayer.spawnTextEntity(ch, l.clone(), scale, IngameDisplayManager.REFRESH_RATE+1, new Vector());
			l.add(l.getDirection().multiply(scale * 0.8f));
		}
	}


	@Override
	public Collection<DisplayString> getDisplayStrings() {
		return newArrayList(this);
	}
}
