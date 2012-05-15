package gt.lastgnome;

import gt.general.Hero;
import gt.general.HeroManager;
import gt.general.Team;
import gt.general.aura.Effect;
import gt.general.aura.GnomeCarrierEffect;

import java.util.Vector;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

/**
 * Game Controller for a Last-Gnome-Scenario
 */
public class LastGnomeGame implements Listener {

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
	 * handles passing of the gnome to another player, as triggered by minecraft
	 *
	 * @param event fired by minecraft
	 */
	@EventHandler
	public void handleGnomPassing(final PlayerInteractEntityEvent event) {

		if (!gnomeBearer.getPlayer().equals(event.getPlayer())) {
			return;
		}

		if (event.getRightClicked() instanceof Player) {

			Hero newBearer = HeroManager.getHero((Player) event
					.getRightClicked());

			giveGnomeTo(newBearer);
		}
	}

	/**
	 * <b>WARNING</b> does not perform any checks
	 *
	 * @param newBearer the new gnomeBearer
	 */
	void giveGnomeTo(final Hero newBearer) {
		// If Player does not belong to the Team, stop here.
		if (team.isMember(newBearer)
				&& newBearer.canRecieveItem()) {

			// switch aura
			gnome.getGnomeAura().setOwner(newBearer);

			// free from slow
			Vector<Effect> effects = gnomeBearer.getEffects();
			for (Effect effect : effects) {
				if (effect instanceof GnomeCarrierEffect) {
					effects.remove(effect);
				}
			}

			// TODO add post gnome slow

			// pass the gnome
			gnomeBearer.transferActiveItem(newBearer);
			gnomeBearer = newBearer;
		}
	}

	/**
	 * <b>WARNING</b> does not perform any checks
	 *
	 * @param hero
	 *            the new gnome bearer
	 */
	void setGnomeBearer(final Hero hero) {
		gnomeBearer.setActiveItem(gnome);
		gnomeBearer = hero;
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
}
