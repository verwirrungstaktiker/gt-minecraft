package gt.general;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;


public class Hero extends Character implements Listener{
	
	
	/** unbuffed default value **/
	public static final double DEFAULT_HERO_SPEED = 1.0;
	/** unbuffed default value, range 0-100 **/
	public static final double DEFAULT_HERO_STAMINA = 100;
	
	private Team team;
	
	private final Inventory inventory;		
	private final Player player;
	
	public Hero(Player player) {
		super();
		
		setAttribute(CharacterAttributes.SPEED, DEFAULT_HERO_SPEED);
		
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
	 * @return the inventory
	 */
	public Inventory getInventory() {
		return inventory;
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
			player.setVelocity(player.getVelocity().multiply(Math.max(getCurrentSpeed(), 0.0)));
		}
	}
	
	/**
	 * Implements the Inventory function of not-dropping undropable Items
	 * @param event a PlayerDropItemEvent
	 */
	@EventHandler
	public void handleItemDrop(PlayerDropItemEvent event) {
		if (event.getPlayer().equals(this.player)) {
			if(inventory.activeItemDropable()) {
				inventory.dropActiveItem();
				event.setCancelled(true);
			}
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
	public void handleBlockPlacing(BlockPlaceEvent event) {
		if (event.getPlayer().equals(this.player)) {
			if (!inventory.getActiveItem().isPlacable()){
				event.setCancelled(true);				
			} else inventory.setActiveItem(null);
			
			//ToDo: Enforce our inventory state in Minecraft if necessary
		}		
	}
	

	
	
}
