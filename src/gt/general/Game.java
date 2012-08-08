package gt.general;

import gt.general.character.Hero;
import gt.general.character.HeroManager;
import gt.general.character.Team;
import gt.general.gui.HeroGui;
import gt.general.world.WorldInstance;
import gt.plugin.meta.Hello;
import gt.plugin.meta.MultiListener;

import java.util.HashMap;
import java.util.Vector;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public abstract class Game implements Listener {
	
	/** The team playing this game */
	private final Team team;
	
	/** The instance where the game is played */
	private WorldInstance world;
	
	private Vector<Listener> listeners;
	private Vector<Integer> tasks;
	
	/** Heros, which are currently offline */
	//private final HashMap<Player,Hero> disconnectedHeros;
	
	/**
	 * @param team The team playing this game
	 */
	public Game(final Team team) {
		super();
		this.team = team;
		listeners = new Vector<Listener>();
		tasks = new Vector<Integer>();
		team.setGame(this);
		
		//disconnectedHeros = new HashMap<Player, Hero>();
	}

	/**
	 * Looks if the player belonged to this game, before a disconnect
	 * @param player a player
	 * @return the Hero if it is associated with the player, otherwise null 
	 */
	/*public Hero getDisconnectedHero(final Player player) {
		for (Player p : disconnectedHeros.keySet()) {
			if (p.getName().equals(player.getName())) {
				return disconnectedHeros.get(p); 
			}
		}
		return null;
	}*/
	
	/**
	 * saves hero for reconnect, removes him otherwise
	 * @param hero the hero which disconnects
	 */
	public void disconnectHero(final Hero hero) {
		//disconnectedHeros.put(hero.getPlayer(), hero);
		team.getPlayers().remove(hero);
	}
	
	/**
	 * restores a previous disconnected Hero
	 * @param hero Hero to be restored
	 */
	public void restoreHero(final Hero hero) {
		Player player = hero.getPlayer();
		//disconnectedHeros.remove(player);
		team.getPlayers().add(hero);
		hero.setTeam(team);
		
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
		for (Listener listener : listeners) {
			MultiListener.unregisterListener(listener);			
		}
		
		for (Integer taskId : tasks) {
			Hello.cancelScheduledTask(taskId);
		}
		
		getWorldInstance().dispose();
		
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
	public WorldInstance getWorldInstance() {
		return world;
	}
	
	/**
	 * @param world the WorldInstance to be set
	 */
	public void setWorldInstance(final WorldInstance world) {
		this.world = world;
	}
	
	public void registerListener(Listener listener) {
		MultiListener.registerListener(listener);
		listeners.add(listener);
	}
	
	public void registerAsyncTask(Runnable task, int initial, int ticks) {
		tasks.add(Hello.scheduleAsyncTask(task, initial, ticks));
	}
	
	public void registerSyncTask(Runnable task, int initial, int ticks) {
		tasks.add(Hello.scheduleSyncTask(task, initial, ticks));
	}

}

