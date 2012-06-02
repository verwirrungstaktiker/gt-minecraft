package gt.general;

import gt.general.character.Hero;
import gt.general.character.Team;
import gt.general.world.WorldInstance;

import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public abstract class Game implements Listener {
	
	/** The team playing this game */
	private final Team team;
	
	/** The instance where the game is played */
	private WorldInstance world;
	
	/** Heros, which are currently offline */
	private final HashMap<Player,Hero> disconnectedHeros;
	
	/**
	 * @param team The team playing this game
	 */
	public Game(final Team team) {
		super();
		this.team = team;
		team.setGame(this);
		
		disconnectedHeros = new HashMap<Player, Hero>();
	}


	
	/**
	 * Looks if the player belonged to this game, before a disconnect
	 * @param player a player
	 * @return the Hero if it is associated with the player, otherwise null 
	 */
	public Hero getDisconnectedHero(final Player player) {
		for (Player p : disconnectedHeros.keySet()) {
			if (p.getName().equals(player.getName())) {
				return disconnectedHeros.get(p); 
			}
		}
		return null;
	}
	
	/**
	 * saves hero for reconnect, removes him otherwise
	 * @param hero the hero which disconnects
	 */
	public void disconnectHero(final Hero hero) {
		disconnectedHeros.put(hero.getPlayer(), hero);
		team.getPlayers().remove(hero);
	}
	
	/**
	 * restores a previous disconnected Hero
	 * @param hero Hero to be restored
	 */
	public void restoreHero(final Hero hero) {
		Player player = hero.getPlayer();
		disconnectedHeros.remove(player);
		team.getPlayers().add(hero);
		
		player.teleport(world.getSpawnLocation());	
	}
	
	/**
	 * restores a previous hero and teleports him to another hero
	 * @param hero Hero to be restored
	 * @param dest Hero to teleport to
	 */
	protected void restoreHero(final Hero hero, final Hero dest) {
		Player player = hero.getPlayer();
		Player destplayer = dest.getPlayer();
		player.teleport(destplayer);	
	}
	
	/**
	 * handles the victory or defeat of a game
	 */
	public abstract void onEnd();

	/**
	 * ensures there are no internal dependencies to prevent the game from garbage collection
	 * e.g. removes related tasks from the scheduler
	 */
	public void dispose() {
		
		// this has nothing to do here
		/*
		Server server = HelloWorld.getPlugin().getServer();
		
		DeleteWorldTask dwt = new DeleteWorldTask(world.getWorldFolder()); 
		server.getScheduler().scheduleSyncDelayedTask(HelloWorld.getPlugin(), dwt, 80);
		
		server.unloadWorld(world, true);
		*/		
	}

	/**
	 * @return the team
	 */
	public Team getTeam() {
		return team;
	}
	
	/**
	 * @return the world
	 */
	public abstract WorldInstance getWorldWrapper();	
}
