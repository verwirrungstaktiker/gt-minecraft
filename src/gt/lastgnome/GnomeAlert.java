package gt.lastgnome;

import gt.general.character.CharacterAttributes;
import gt.general.character.Hero;
import gt.lastgnome.game.LastGnomeGame;
import gt.plugin.meta.Hello;

import org.getspout.spoutapi.SpoutManager;


public class GnomeAlert implements Runnable {
	
	private final LastGnomeGame game;
	private boolean activated;

	public GnomeAlert(LastGnomeGame game) {
		super();
		this.game = game;
	}
	
	@Override
	public void run() {
		Hero bearer = game.getGnomeBearer();
		
		if (bearer == null) {
			return;
		}
		
		double speed = bearer.getAttribute(CharacterAttributes.SPEED);
		
		if (speed > 0) {
			activated = true;
			return;
		} else if (!activated) {
			activated = false;
			SpoutManager.getSoundManager()
				.playGlobalCustomSoundEffect(Hello.getPlugin(), 
				"https://raw.github.com/verwirrungstaktiker/gt-minecraft/master/res/sound/gnome_alert.wav", 
				false, bearer.getLocation(),75);
		}
		
		
	}


}
