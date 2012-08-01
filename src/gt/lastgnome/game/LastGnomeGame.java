package gt.lastgnome.game;

import static org.bukkit.ChatColor.GREEN;
import static org.bukkit.ChatColor.YELLOW;
import static org.bukkit.ChatColor.RED;


import gt.general.character.Hero;
import gt.general.character.HeroManager;
import gt.general.character.Team;
import gt.general.gui.GuiElementType;
import gt.general.world.WorldInstance;
import gt.lastgnome.GnomeItem;
import gt.lastgnome.gui.SpeedBar;
import gt.plugin.meta.MultiListener;

import java.util.Iterator;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;

/**
 * Game Controller for a Last-Gnome-Scenario
 */
public class LastGnomeGame extends AbstractLastGnomeGame implements Listener{
	
	private boolean gameRunning;
	
	private final GnomeItem gnome;

	/** so that e.g. Zombies know who the Gnome-Bearer is */
	private Hero gnomeBearer;
	
	private ScoreManager scoreManager;
		
	/**
	 * initiates a new Last Gnome Game
	 *
	 * @param team the Team playing the game
	 */
	public LastGnomeGame(final Team team) {
		super(team);
		
		gnome = new GnomeItem();
		gameRunning = true;
	}

	
	public ScoreManager getScoreManager() {
		return scoreManager;
	}


	public void setScoreManager(ScoreManager scoreManager) {
		this.scoreManager = scoreManager;
	}


	public void setGameRunning(boolean gameRunning) {
		this.gameRunning = gameRunning;
	}


	/**
	 * TODO downgrade? is here the right place for this stuff?
	 * 		better in game manager?
	 * 
	 * @param hero whose gui to be upgraded
	 */
	public void upgradeGui(final Hero hero) {
		SpeedBar speedBar = new SpeedBar();
		hero.getGui().addGuiElement(GuiElementType.SPEEDBAR, speedBar);
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
		getZombieManager().setTarget(newBearer);
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
		getZombieManager().cleanup();
		gameRunning = false;
		super.dispose();
		
		if (gnomeBearer != null) {
			gnomeBearer.removeActiveItem();
		}
		
		for(Hero hero : getTeam().getPlayers()) {
			hero.getGui().removeGuiElement(GuiElementType.SPEEDBAR);
		}
		
		MultiListener.unregisterListener(scoreManager);
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
		if (getGnomeBearer() != null) {
			super.restoreHero(hero,getGnomeBearer());			
		}
		
		upgradeGui(hero);
	}

	@Override
	public void onEnd() {
		dispose();		
	}
	
	/**
	 * @param worldInstance where to play the game
	 */
	public void setWorldWrapper(final WorldInstance worldInstance) {
		//this.worldInstance = worldInstance;
		
		
	}
	
	/**
	 * Detect Player Death
	 * @param event player dies
	 */
	@EventHandler
	public void onPlayerDeath(final PlayerDeathEvent event) {
		Player player = event.getEntity();
		if (player.equals(gnomeBearer.getPlayer())) {
			lose();
		}
		
		//temporary version for respawn
		if (gnomeBearer != null) {
			player.setHealth(player.getMaxHealth());
			player.teleport(gnomeBearer.getLocation());
		}	
	}
	

	private void lose() {
		for (Hero hero: getTeam().getPlayers()) {
			hero.getPlayer().sendMessage(RED + "The Gnome has been eaten! You lost!");			
		}		
		onEnd();
	}
	
	private void win() {
		for (Hero hero: getTeam().getPlayers()) {
			hero.getPlayer().sendMessage(GREEN + "The last Gnome on earth has been saved!");			
		}
		onEnd();	   	
	}
	
	/**
	 * handles gnome transfer from Start Socket to Player
	 * @param player who clicked the socket
	 */
	@Override
	public void onStartSocketInteract(final Player player) {
		Hero hero = HeroManager.getHero(player);
		
		if (gnomeBearer == null && hero.canRecieveItem()) {

			hero.setActiveItem(gnome);
			setGnomeBearer(hero);
			
			scoreManager.startMonitoring();
			
			player.sendMessage(GREEN + "You obtained the last Gnome on earth");
			
		} else {
			player.sendMessage(YELLOW + "nothing happens..");
		}
	}


	/**
	 * handles gnome transfer from Player to End Socket
	 * @param player who klicked the socket
	 */
	@Override
	public void onEndSocketInteract(final Player player) {
		Hero hero = HeroManager.getHero(player);
		
		if(hero.equals(gnomeBearer)) {	
			
			hero.removeActiveItem();
			setGnomeBearer(null);

			scoreManager.stopMonitoring();
		
			win();
		} else {
			player.sendMessage(YELLOW + "The mighty Gnome Socket demands the Gnome!");
		}
	}
}
