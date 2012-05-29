package gt.general;

import java.util.HashMap;

import gt.general.util.CopyUtil;
import gt.plugin.helloworld.HelloWorld;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

public abstract class Game {
	
	protected final Team team;
	protected World world;
	
	protected HashMap<Player,Hero> disconnectedHeros;
	
	/**
	 * 
	 * @param team
	 * @param world
	 */
	public Game(Team team, World world) {
		super();
		this.team = team;
		team.setGame(this);
		String newWorldFolder = CopyUtil.findnextInstanceFolder(world);
		this.world = CopyUtil.copyWorld(world, newWorldFolder);
		Location spawn = world.getSpawnLocation();
		for (Hero hero : team.getPlayers()) {
			hero.getPlayer().teleport(spawn);
		}
	}
	
	/**
	 * Looks if the player belonged to this game, before a disconnect
	 * @param player a player
	 * @return the Hero if it is associated with the player, otherwise null 
	 */
	public Hero getDisconnectedHero(Player player) {
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
	public void disconnectHero(Hero hero) {
		disconnectedHeros.put(hero.getPlayer(), hero);
		team.getPlayers().remove(hero);
	}
	
	/**
	 * restores a previous disconnected Hero
	 * @param hero Hero to be restored
	 */
	public void restoreHero(Hero hero) {
		Player player = hero.getPlayer();
		disconnectedHeros.remove(player);
		team.getPlayers().add(hero);
		player.teleport(world.getSpawnLocation());	
	}
	
	/**
	 * restores a previous hero and teleports him to another hero
	 * @param hero Hero to be restored
	 * @param dest Hero to teleport tp
	 */
	protected void restoreHero(Hero hero, Hero dest) {
		Player player = hero.getPlayer();
		Player destplayer = dest.getPlayer();
		player.teleport(destplayer);	
	}
	
	abstract public void onEnd();
	

	/**
	 * ensures there are no internal dependencies to prevent the game from garbage collection
	 * e.g. removes related tasks from the scheduler
	 */
	public void dispose() {
		Location ret = HelloWorld.getPlugin().
		getServer().getWorld("world").getSpawnLocation();
		for (Hero member : team.getPlayers()) {
			member.getPlayer().teleport(ret);
		}
		CopyUtil.deleteDirectory(world.getWorldFolder());
	}
	
}
