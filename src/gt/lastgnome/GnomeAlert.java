package gt.lastgnome;

import gt.general.character.CharacterAttributes;
import gt.general.character.Hero;
import gt.lastgnome.game.LastGnomeGame;
import gt.plugin.meta.Hello;

import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.sound.SoundEffect;


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
			activated = false;
			return;
		} else if (!activated) {
			activated = true;
			SpoutManager.getSoundManager().playGlobalSoundEffect(SoundEffect.WOLF_HOWL, bearer.getLocation());
		}
		
		
	}


}
