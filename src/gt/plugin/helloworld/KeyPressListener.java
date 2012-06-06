package gt.plugin.helloworld;

import java.awt.Color;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.getspout.spoutapi.event.input.KeyPressedEvent;
import org.getspout.spoutapi.keyboard.Keyboard;

public class KeyPressListener implements Listener {
	
	@EventHandler
	public void showCoordsOnSpecialKeypress (final KeyPressedEvent event) {
		if (event.getKey() == Keyboard.KEY_F6) {
//			Location loc = event.getPlayer().getLastClickedLocation();
			Location loc = event.getPlayer().getTargetBlock(null, 100).getLocation();
			String message = Color.YELLOW + "x:" + loc.getBlockX() + " y:" + loc.getBlockY() + " z:" + loc.getBlockZ();
			event.getPlayer().sendMessage(message);
		}
	}
}
