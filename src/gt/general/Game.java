package gt.general;

import gt.general.util.CopyUtil;
import gt.general.util.DeleteWorldTask;
import gt.plugin.helloworld.HelloWorld;

import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.entity.Player;

public abstract class Game {
	
	/** The team playing this game */
	private final Team team;

	/** The world where the players spawn initially */
	private final World initialWorld;
	
	/** The instance where the game is played */
	private final World world;
	
	/** Heros, which are currently offline */
	private final HashMap<Player,Hero> disconnectedHeros;
	
	/**
	 * @param team The team playing this game
	 * @param initialWorld Which world to instantiate
	 */
	public Game(final Team team, final World initialWorld) {
		super();
		this.team = team;
		team.setGame(this);
		
		this.initialWorld = initialWorld;
		
		String newWorldFolder = CopyUtil.findnextInstanceFolder(initialWorld);
		world = CopyUtil.copyWorld(initialWorld, newWorldFolder);
		
		teleportTeamTo(world.getSpawnLocation());
		
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
		teleportTeamTo(initialWorld.getSpawnLocation());

		Server server = HelloWorld.getPlugin().getServer();
		
		DeleteWorldTask dwt = new DeleteWorldTask(world.getWorldFolder()); 
		server.getScheduler().scheduleSyncDelayedTask(HelloWorld.getPlugin(), dwt, 80);
		
		server.unloadWorld(world, true);
		
	}
	
	/**
	 * @param spawn Where to teleport the whole Team
	 */
	private void teleportTeamTo(final Location spawn) {
		for (Hero hero : team.getPlayers()) {
			hero.getPlayer().teleport(spawn);
		}
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
	public World getWorld() {
		return world;
	}
	
}
