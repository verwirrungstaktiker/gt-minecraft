package gt.plugin.helloworld;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.getspout.spoutapi.event.input.KeyPressedEvent;
import org.getspout.spoutapi.keyboard.Keyboard;

public class KeyPressListener implements Listener {
	
	/** 
	 * prints the location of the target block in player chat 
	 * works for OP players only
	 * @param event client key press event
	 **/
	@EventHandler
	public void showCoordsOnSpecialKeypress (final KeyPressedEvent event) {
		if (event.getKey() == Keyboard.KEY_N && event.getPlayer().isOp()) {
			Location loc = event.getPlayer().getTargetBlock(null, 100).getLocation();
			String message = ChatColor.YELLOW + "x:" + loc.getBlockX() + " y:" + loc.getBlockY() + " z:" + loc.getBlockZ();
			event.getPlayer().sendMessage(message);
		}
	}

}
