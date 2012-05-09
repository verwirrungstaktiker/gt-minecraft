package gt.general;

import gt.general.aura.Aura;

import java.util.Vector;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;


public class Hero extends Character implements Listener{
	
	
	/** unbuffed default value **/
	public static final double DEFAULT_HERO_SPEED = 100;
	/** unbuffed default value, range 0-100 **/
	public static final double DEFAULT_HERO_STAMINA = 100;
	
	protected Team team;
	public Inventory inventory;	
	public Vector<Aura> auras;
	
	final private Player player;
	
	public Hero(Player player) {
		super(DEFAULT_HERO_SPEED);
		
		team = Team.NOTEAM;
		this.player = player;
		inventory = new Inventory();
	}
	
	/**
	 * @return the wrapped Player
	 */
	public Player getPlayer() {
		return player;
	}

	@Override
	public Location getLocation() {
		return player.getLocation();
	}
	
	/**
	 * @return the team
	 */
	public Team getTeam() {
		return team;
	}

	/**
	 * @param team the team to set
	 */
	public void setTeam(Team team) {
		this.team = team;
	}	
	
	/**
	 * Sets player's velocity according to hero's speed
	 * @param event a PlayerMoveEvent
	 */
	@EventHandler
	public void handleSpeed(PlayerMoveEvent event) {
		if (event.getPlayer().equals(this.player)) {
			player.setVelocity(player.getVelocity().multiply(currentSpeed/100));
		}
	}
	
	/**
	 * Implements the Inventory function of not-dropping undropable Items
	 * @param event a PlayerDropItemEvent
	 */
	@EventHandler
	public void handleItemDrop(PlayerDropItemEvent event) {
		if (event.getPlayer().equals(this.player)) {
			event.setCancelled(!inventory.dropActiveItem());
		}
	}
	
	/**
	 * Handles the Inventory mechanics on picking up an item
	 * @param event PlayerPickupItemEvent
	 */
	@EventHandler
	public void handleItemPickup(PlayerPickupItemEvent event) {
		if (event.getPlayer().equals(this.player)) {
			Item newItem = new Item(event.getItem().getItemStack());
			event.setCancelled(!inventory.setActiveItem(newItem));
			//ToDo: implement passivItem mechanics
		}
	}
	
	/**
	 * Handles Placing of Blocks/Items
	 * @param event BlockPlaceEvent
	 */
	@EventHandler
	public void handleBlockPlace(BlockPlaceEvent event) {
		if (event.getPlayer().equals(this.player)) {
			if (!inventory.getActiveItem().isPlacable()){
				event.setCancelled(true);				
			} else inventory.setActiveItem(null);
			
			//ToDo: Enforce our inventory state in Minecraft
		}		
	}
	
	
}
