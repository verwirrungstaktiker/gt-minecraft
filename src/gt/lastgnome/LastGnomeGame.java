package gt.lastgnome;

import gt.general.Game;
import gt.general.character.Hero;
import gt.general.character.HeroManager;
import gt.general.character.Team;
import gt.general.world.WorldInstance;
import gt.lastgnome.gui.SpeedBar;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

/**
 * Game Controller for a Last-Gnome-Scenario
 */
public class LastGnomeGame extends Game implements Listener{

	
	private WorldInstance worldWrapper;
	
	private boolean gameRunning;
	
	private final GnomeItem gnome;

	/** so that e.g. Zombies know who the Gnome-Bearer is */
	private Hero gnomeBearer;
	
	private final Map<Hero, SpeedBar> speedBars;
	
	/**
	 * initiates a new Last Gnome Game
	 *
	 * @param team the Team playing the game
	 * @param world 
	 */
	public LastGnomeGame(final Team team) {
		super(team);
		
		gnome = new GnomeItem();
		speedBars = new HashMap<Hero, SpeedBar>();

		gameRunning = true;
	}

	
	/**
	 * @param hero
	 */
	public void upgradeGui(final Hero hero) {
		SpeedBar speedBar = new SpeedBar();
		hero.getGui().addGuiElement(speedBar);
		speedBars.put(hero, speedBar);
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
		
		if (gameRunning && gnomeBearer.getPlayer().equals(event.getPlayer())) {	
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
	void giveGnomeTo(final Hero newBearer) {
		
		// If Player does not belong to the Team, stop here.
		if (getTeam().isMember(newBearer)
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
	public void disconnectHero(final Hero hero) {
		super.disconnectHero(hero);
		if (hero == getGnomeBearer()) {
			
			Iterator<Hero> heros = getTeam().getPlayers().iterator();
			giveGnomeTo(heros.next());
		}
		
	}
	
	@Override
	public void dispose() {
		
		gameRunning = false;
		super.dispose();
		
		gnomeBearer.removeActiveItem();
		
		for(Hero hero : speedBars.keySet()) {
			SpeedBar speedBar = speedBars.get(hero);
			hero.getGui().removeGuiElement(speedBar);
		}
		
	}
	
	@Override
	public void finalize() {
		System.out.println("finalizing a gnome game");
	}
	
	/**
	 * {@inheritDoc}
	 * <br/>
	 * Additionally teleports the restored player to the current gnome bearer.
	 */
	public void restoreHero(final Hero hero) {
		super.restoreHero(hero,getGnomeBearer());
	}

	@Override
	public void onEnd() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public WorldInstance getWorldWrapper() {
		return worldWrapper;
	}
	
	public void setWorldWrapper(final WorldInstance worldWrapper) {
		this.worldWrapper = worldWrapper;
	}
}
