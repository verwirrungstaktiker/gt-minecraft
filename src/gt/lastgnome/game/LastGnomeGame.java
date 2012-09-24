/*******************************************************************************
 * Projektpraktikum: Game Technology 2012
 * Minecraft-Modifikation f�r kollaboratives Spielen
 * 
 * Sebastian Fahnenschreiber (sebastian.fahnenschreiber@stud.tu-darmstadt.de)
 * Roman Ne� (roman.ness@stud.tu-darmstadt.de)
 * Philipp Pascal Battenberg (philipp.battenberg@stud.tu-darmstadt.de)
 ******************************************************************************/
package gt.lastgnome.game;

import static com.google.common.collect.Lists.newArrayList;
import static org.bukkit.ChatColor.GREEN;
import static org.bukkit.ChatColor.RED;
import static org.bukkit.ChatColor.YELLOW;
import gt.general.RespawnManager;
import gt.general.Vote;
import gt.general.character.Hero;
import gt.general.character.HeroManager;
import gt.general.character.Team;
import gt.general.character.ZombieManager;
import gt.general.gui.GameScoreOverlay;
import gt.general.gui.GuiElementType;
import gt.lastgnome.GnomeItem;
import gt.lastgnome.gui.SpeedBar;
import gt.lastgnome.scoring.Highscore;
import gt.lastgnome.scoring.HighscoreEntry;
import gt.lastgnome.scoring.ScoreManager;
import gt.plugin.helloworld.HelloWorld;
import gt.plugin.meta.Hello;

import org.bukkit.World;
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
	
	private final GnomeItem gnome;

	
	/** so that e.g. Zombies know who the Gnome-Bearer is */
	private Hero gnomeBearer;
	
	private ScoreManager scoreManager;
	
	private RespawnManager respawnManager;

	private ZombieManager zombieManager;
	
	
	private boolean isWon = false;
	
	/**
	 * initiates a new Last Gnome Game
	 *
	 * @param team the Team playing the game
	 */
	public LastGnomeGame(final Team team) {
		super(team);
		
		gnome = new GnomeItem(this);
	}

	/**
	 * @return the corresponding ScoreManager
	 */
	public ScoreManager getScoreManager() {
		return scoreManager;
	}

	/**
	 * @param respawnManager the new RespawnManager
	 */
	public void setRespawnManager(final RespawnManager respawnManager) {
		this.respawnManager = respawnManager;
	}

	/**
	 * @param scoreManager the new ScoreManager
	 */
	public void setScoreManager(final ScoreManager scoreManager) {
		this.scoreManager = scoreManager;
	}

	/**
	 * @param hero whose gui to be upgraded
	 */
	public void upgradeGui(final Hero hero) {
		SpeedBar speedBar = new SpeedBar();
		hero.getGui().addGuiElement(GuiElementType.SPEEDBAR, speedBar);
	}
	
	/**
	 * handles passing of items to another player
	 *
	 * @param event player interact (right click on entity)
	 */
	@EventHandler
	public void handleItemTransfering(final PlayerInteractEntityEvent event) {
		Entity target = event.getRightClicked();
		if (target instanceof Player) {	
			Hero sender = HeroManager.getHero(event.getPlayer());
			Hero receiver = HeroManager.getHero((Player) target);
			
			transferItem(sender, receiver);
		}
	}
	
	/**
	 * initiates an item transfer from sender to receiver if possible
	 * 
	 * @param sender
	 *            gives the item
	 * @param receiver
	 *            gets the item
	 */
	void transferItem(final Hero sender, final Hero receiver) {
		// If Player does not belong to the Team, stop here.
		if (getTeam().isMember(receiver) && receiver.canRecieveItem()
				&& sender.canTransferItem()) {

			System.out.println(sender.getPlayer().getName() + " gave an item to " + receiver.getPlayer().getName());
			
			sender.transferActiveItem(receiver);
			
			if (sender.equals(gnomeBearer)) {
				setGnomeBearer(receiver);
			}
		}
	}

	/**
	 * @param newBearer
	 *            the new gnome bearer
	 */
	public void setGnomeBearer(final Hero newBearer) {
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
	public void dispose() {
		getZombieManager().cleanup();

		super.dispose();
		
		if (gnomeBearer != null) {
			gnomeBearer.removeActiveItem();
		}
		
		for(Hero hero : getTeam().getPlayers()) {
			hero.getGui().removeGuiElement(GuiElementType.SPEEDBAR);
		}
		
		respawnManager.dispose();
		
		//MultiListener.unregisterListener(scoreManager);
	}
	
	@Override
	public void finalize() {
		System.out.println("finalizing a gnome game");
	}
	
	@Override
	public void onEnd() {
		dispose();
		
		Vote restartVote = new Vote(getTeam().getPlayers().size(), 1) {

			@Override
			public void onAccept() {
				System.out.println("restarting game");
				
				for(Hero h : getTeam().getPlayers()) {
					h.getGui().closePopup();
				}
				
				Hero randomHero = newArrayList(getTeam().getPlayers()).get(0);
				randomHero.getPlayer().performCommand("gg force");			
			}

			@Override
			public void onReject() {
				System.out.println("back to the lobby");

				World startWorld = Hello.getPlugin().getServer().getWorld("world");
				for (Hero hero : getTeam().getPlayers()) {
					hero.getGui().closePopup();

					
					hero.getPlayer().setBedSpawnLocation(startWorld.getSpawnLocation());
					hero.getPlayer().teleport(startWorld.getSpawnLocation());
				}
				
				getTeam().dispose();
			}
			
		};
		
		HighscoreEntry entry = scoreManager.toHighscoreEntry();
		
		respawnManager.dispose();
		
		String message = (isWon) ? "Victory!" : "Fail!";
		
		for(Hero hero : getTeam().getPlayers()) {
			hero.getGui().popup(new GameScoreOverlay(message, entry, hero, restartVote));
		}
		
		
	}
	
	/**
	 * Detect Player Death
	 * @param event player dies
	 */
	@EventHandler
	public void onPlayerDeath(final PlayerDeathEvent event) {
		Player player = event.getEntity();
		if(gnomeBearer != null && player.equals(gnomeBearer.getPlayer())) {
			scoreManager.stopMonitoring();			
			lose();
		}
	}
	
	/**
	 * Game Losing handling
	 */
	private void lose() {
		for (Hero hero: getTeam().getPlayers()) {
			hero.getPlayer().sendMessage(RED + "The Gnome has been eaten! You lost!");			
		}		
		onEnd();
	}
	
	/**
	 * Game Winning handling
	 */
	private void win() {
		for (Hero hero: getTeam().getPlayers()) {
			hero.getPlayer().sendMessage(GREEN + "The last Gnome on earth has been saved!");			
		}
		isWon = true;
		
		
		Highscore h = HelloWorld.getHighscore(getWorldInstance().getBaseName());
		h.addEntry(scoreManager.toHighscoreEntry());
		
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


	/**
	 * @return the zombieManager
	 */
	public ZombieManager getZombieManager() {
		return zombieManager;
	}


	/**
	 * @param zombieManager the zombieManager to set
	 */
	public void setZombieManager(final ZombieManager zombieManager) {
		this.zombieManager = zombieManager;
	}


	@Override
	public void onPause() {
		getZombieManager().freezeAllZombies();
		getTeam().freezeAllHeros();
	}


	@Override
	public void onResume() {
		getZombieManager().unFreezeAllZombies();
		getTeam().unfreezeAllHeros();
	}

}
