/*******************************************************************************
 * Projektpraktikum: Game Technology 2012
 * Minecraft-Modifikation für kollaboratives Spielen
 * 
 * Sebastian Fahnenschreiber (sebastian.fahnenschreiber@stud.tu-darmstadt.de)
 * Roman Neß (roman.ness@stud.tu-darmstadt.de)
 * Philipp Pascal Battenberg (philipp.battenberg@stud.tu-darmstadt.de)
 ******************************************************************************/
package gt.general.ingameDisplay;

import org.bukkit.Location;
import org.bukkit.util.Vector;
import org.getspout.spoutapi.player.SpoutPlayer;

public class RightBoundedDisplayString extends DisplayString {

	public RightBoundedDisplayString(String text, float scale, Location location) {
		// reverse the string
		super(new StringBuffer(text).reverse().toString(), scale, location);
	}
	
	
	@Override
	public void spawnForPlayer(final SpoutPlayer spoutPlayer) {

		Location l = location.clone();
			
		for(String ch : text.split("")) {
			spoutPlayer.spawnTextEntity(ch, l.clone(), scale, IngameDisplayManager.REFRESH_RATE+1, new Vector());
			
			l.subtract(l.getDirection().multiply(scale * 0.8f));
		}
	
	}

}
