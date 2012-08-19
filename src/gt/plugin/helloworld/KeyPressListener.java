package gt.plugin.helloworld;

import gt.general.character.Hero;
import gt.general.character.HeroManager;
import gt.general.gui.GameScoreOverlay;
import gt.general.ingameDisplay.DisplayString;
import gt.lastgnome.scoring.HighscoreEntry;
import gt.plugin.meta.Hello;

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
		
		if(event.getKey() == Keyboard.KEY_J) {
			Location loc = event.getPlayer().getLocation();
			event.getPlayer().sendMessage("pitch: " + loc.getPitch() + " yaw: " + loc.getYaw());
		}
		
		
		if(event.getKey() == Keyboard.KEY_B && Hello.getPlugin() instanceof HelloWorld) {
			Hero h = HeroManager.getHero(event.getPlayer());
			
			HelloWorld plugin = (HelloWorld) Hello.getPlugin();
			HighscoreEntry entry = plugin.getHighscore("lastgnome").getScores().get(0);
			
			h.getGui().popup(new GameScoreOverlay(entry));
		}
	}
}
