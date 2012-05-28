package gt.lastgnome;

import gt.general.Game;
import gt.general.Hero;
import gt.general.HeroManager;
import gt.general.Team;
import gt.general.aura.Effect;
import gt.general.aura.GnomeSlowEffect;

import java.util.Iterator;

import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

/**
 * Game Controller for a Last-Gnome-Scenario
 */
public class LastGnomeGame extends Game implements Listener {

	private final GnomeItem gnome;

	/** so that e.g. Zombies know who the Gnome-Bearer is */
	private Hero gnomeBearer;
	
	/**
	 * initiates a new Last Gome Game with an initial GnomeBearer
	 * @param team the Team playing the game
	 * @param initialBearer the hero bearing the gnome from the start
	 */
	public LastGnomeGame(final Team team, final World world, final Hero initialBearer) {
		super(team, world);
		gnome = new GnomeItem();
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

		System.out.println(gnomeBearer.getPlayer());
		System.out.println(event.getPlayer());
		
		if (!gnomeBearer.getPlayer().equals(event.getPlayer())) {
			return;
		}

		Entity target = event.getRightClicked();
		
		if (target instanceof Player) {
			giveGnomeTo(HeroManager.getHero((Player) target));
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
			
			Hero oldBearer = gnomeBearer;			

			// pass the gnome
			oldBearer.transferActiveItem(newBearer);
			setGnomeBearer(newBearer);
			
			// remove effects - slow and misc
			Iterator<Effect> it = oldBearer.getEffects().iterator();
			while(it.hasNext()) {
		        if (it.next() instanceof GnomeSlowEffect) {
		            it.remove();
		        }
			}
			oldBearer.removeEffect(gnome.getGnomeEffect());
		}
	}

	/**
	 * <b>WARNING</b> does not perform any checks
	 *
	 * @param newBearer
	 *            the new gnome bearer
	 */
	void setGnomeBearer(final Hero newBearer) {
		newBearer.addEffect(gnome.getGnomeEffect());
		gnome.getGnomeAura().setOwner(newBearer);
		
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
	
	public void disconnectHero(Hero hero) {
		super.disconnectHero(hero);
		if (getGnomeBearer()==hero) {
			Hero players[] = null;
			players = team.getPlayers().toArray(players);
			giveGnomeTo(players[0]);
		}
		
		
	}
	
	public void restoreHero(Hero hero) {
		super.restoreHero(hero,getGnomeBearer());
	}

	@Override
	public void onEnd() {
		// TODO Auto-generated method stub
		
	}
}
