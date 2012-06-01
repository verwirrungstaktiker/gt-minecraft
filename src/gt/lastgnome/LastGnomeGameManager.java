package gt.lastgnome;

import gt.general.GameManager;
import gt.general.Hero;
import gt.general.Team;

import org.bukkit.World;

public class LastGnomeGameManager extends GameManager {

	public LastGnomeGameManager(final World initialWorld) {
		super(initialWorld);
	}

	public LastGnomeGame startGame(final Team team, final Hero initialBearer, final World world) {
		LastGnomeGame lastGnomeGame = new LastGnomeGame(team, initialBearer);
		
		startGame(lastGnomeGame, world);
		
		return lastGnomeGame;
	}
	
}
