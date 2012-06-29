package gt.lastgnome;

import gt.general.Game;
import gt.general.character.Team;

import org.bukkit.entity.Player;

public abstract class AbstractLastGnomeGame extends Game{

	public AbstractLastGnomeGame(Team team) {
		super(team);
	}
	
	public abstract void onStartSocketInteract(Player player);
	
	
	public abstract void onEndSocketInteract(Player player);

}
