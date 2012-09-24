/*******************************************************************************
 * Projektpraktikum: Game Technology 2012
 * Minecraft-Modifikation für kollaboratives Spielen
 * 
 * Sebastian Fahnenschreiber (sebastian.fahnenschreiber@stud.tu-darmstadt.de)
 * Roman Neß (roman.ness@stud.tu-darmstadt.de)
 * Philipp Pascal Battenberg (philipp.battenberg@stud.tu-darmstadt.de)
 ******************************************************************************/
package gt.general;

import static com.google.common.collect.Sets.newHashSet;
import gt.general.character.Hero;
import gt.general.character.Team;
import gt.general.world.WorldInstance;
import gt.plugin.meta.Hello;
import gt.plugin.meta.MultiListener;

import java.util.Set;
import java.util.Vector;

import org.bukkit.ChatColor;
import org.bukkit.event.Listener;

public abstract class Game implements Listener {
	
	/** The team playing this game */
	private final Team team;
	
	/** The instance where the game is played */
	private WorldInstance world;
	
	private Vector<Listener> listeners;
	private Vector<Integer> tasks;
	
	private final Set<Hero> disconnectedHeros = newHashSet();
	private boolean running;

	private boolean cmdPause;
	
	/**
	 * @param team The team playing this game
	 */
	public Game(final Team team) {
		super();
		this.team = team;
		listeners = new Vector<Listener>();
		tasks = new Vector<Integer>();
		team.setGame(this);
		
		running = true;
	}
	
	/**
	 * saves hero for reconnect, removes him otherwise
	 * @param hero the hero which disconnects
	 */
	public void disconnectHero(final Hero hero) {
		disconnectedHeros.add(hero);

		// first disconnect
		if(disconnectedHeros.size() == 1) {
			setRunning(false);
		}
	}	
	/**
	 * restores a previous disconnected Hero
	 * @param hero Hero to be restored
	 */
	public void reconnectHero(final Hero hero) {		
		disconnectedHeros.remove(hero);
		
		// last reconnect
		if(disconnectedHeros.size() == 0) {
			setRunning(true);
		}	
	}

	/**
	 * toggles the game pause
	 */
	public void toggleForcePause() {
		cmdPause = !cmdPause;
		setRunning(running);
	}
	
	/**
	 * sets the running state and calls the appropriate handlers
	 * 
	 * @param running the running to set
	 */
	public void setRunning(final boolean running) {
		this.running = running;
		
		if(running && !cmdPause) {
			team.sendMessage(ChatColor.GREEN + "game resumed");
			onResume();
		} else {
			team.sendMessage(ChatColor.RED + "game paused");
			onPause();
		}
	}
	
	/**
	 * called if game is paused
	 */
	public abstract void onPause();
	
	/**
	 * called if game is resumed
	 */
	public abstract void onResume();
	
	/**
	 * handles the victory or defeat of a game
	 */
	public abstract void onEnd();

	/**
	 * ensures there are no internal dependencies to prevent the game from garbage collection
	 * e.g. removes related tasks from the scheduler
	 */
	public void dispose() {
		
		for (Hero hero : team.getPlayers()) {
			if(hero.hasActiveItem()) {
				hero.removeActiveItem();
			}
		}
		
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
	
	/**
	 * register a new Listener
	 * @param listener Listener to be registered
	 */
	public void registerListener(final Listener listener) {
		MultiListener.registerListener(listener);
		listeners.add(listener);
	}
	
	/**
	 * Shortcut: schedule an async Task (new thread)
	 * @param task work of a task
	 * @param initial intitial wainting time
	 * @param ticks ticks between execution
	 */
	public void registerAsyncTask(final Runnable task, final int initial, final int ticks) {
		tasks.add(Hello.scheduleAsyncTask(task, initial, ticks));
	}
	
	/**
	 * Shortcut: schedule a sync task
	 * @param task work of a task
	 * @param initial intitial wainting time
	 * @param ticks ticks between execution
	 */
	public void registerSyncTask(final Runnable task, final int initial, final int ticks) {
		tasks.add(Hello.scheduleSyncTask(task, initial, ticks));
	}
	
	/**
	 * register multiple Listeners
	 * @param listeners Listeners to be registered
	 */
	public void registerListeners(final Listener... listeners) {
		for (Listener listener : listeners) {
			registerListener(listener);
		}
	}

	/**
	 * @return the running
	 */
	public boolean isRunning() {
		return running;
	}
	
	/**
	 * @param hero a player
	 * @return true if the hero is part of a game
	 */
	public boolean isPlayedBy(final Hero hero) {
		return getTeam().containsHero(hero);
	}

}

