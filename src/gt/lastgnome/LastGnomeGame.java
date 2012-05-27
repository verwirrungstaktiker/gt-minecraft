package gt.lastgnome;

import gt.general.Game;
import gt.general.Hero;
import gt.general.HeroManager;
import gt.general.Team;
import gt.plugin.helloworld.HelloWorld;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.event.player.PlayerToggleSprintEvent;

/**
 * Game Controller for a Last-Gnome-Scenario
 */
public class LastGnomeGame implements Listener, Game {

	private final Team team;
	private final GnomeItem gnome;

	/** so that e.g. Zombies know who the Gnome-Bearer is */
	private Hero gnomeBearer;

	/**
	 * initiates a new Last Gnome Game
	 *
	 * @param team the Team playing the game
	 */
	public LastGnomeGame(final Team team) {
		this.team = team;
		gnome = new GnomeItem();
	}

	/**
	 * initiates a new Last Gome Game with an initial GnomeBearer
	 * @param team the Team playing the game
	 * @param initialBearer the hero bearing the gnome from the start
	 */
	public LastGnomeGame(final Team team, final Hero initialBearer) {
		this(team);
		
		initialBearer.setActiveItem(gnome);
		setGnomeBearer(initialBearer);
	}

	
	/**
	 * handles passing of the gnome to another player, as triggered by minecraft
	 *
	 * @param event fired by minecraft
	 */
	@EventHandler
	public void handleGnomPassing(final PlayerInteractEntityEvent event) {
		
		if (gnomeBearer.getPlayer().equals(event.getPlayer())) {	
			Entity target = event.getRightClicked();
			
			if (target instanceof Player) {
				Hero hero = HeroManager.getHero((Player) target);
				giveGnomeTo(hero);
			}
		}
	}

	/**
	 * <b>WARNING</b> does not perform any checks
	 *
	 * @param newBearer the new gnomeBearer
	 */
	synchronized void giveGnomeTo(final Hero newBearer) {
		
		// If Player does not belong to the Team, stop here.
		if (team.isMember(newBearer)
				&& newBearer.canRecieveItem()) {

			System.out.println("gnome to: " + newBearer.getPlayer().getName());
			
			// pass the gnome
			gnomeBearer.transferActiveItem(newBearer);
			setGnomeBearer(newBearer);
		}
	}

	/**
	 * <b>WARNING</b> does not perform any checks
	 *
	 * @param newBearer
	 *            the new gnome bearer
	 */
	void setGnomeBearer(final Hero newBearer) {
		gnomeBearer = newBearer;
	}

	/**
	 * @return the gnome
	 */
	public GnomeItem getGnome() {
		return gnome;
	}

	/**
	 * @return the current gnome bearer
	 */
	public Hero getGnomeBearer() {
		return gnomeBearer;
	}

	@Override
	public void dispose() {
		gnomeBearer.removeActiveItem();
		
	}
	
	@Override
	public void finalize() {
		System.out.println("finalizing a gnome game");
	}
}
