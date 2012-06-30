package gt.lastgnome.game;

import gt.general.Game;
import gt.general.character.Team;
import gt.general.character.ZombieManager;

import org.bukkit.entity.Player;

public abstract class AbstractLastGnomeGame extends Game{

	public AbstractLastGnomeGame(Team team) {
		super(team);
	}
	
	public abstract void onStartSocketInteract(Player player);
	
	
	public abstract void onEndSocketInteract(Player player);

	public void setZombieManager(ZombieManager zombieManager) {
		// TODO Auto-generated method stub
		
	}

}
