package gt.general;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.inventory.SpoutItemStack;
import org.getspout.spoutapi.player.SpoutPlayer;

/**
 * Our backend and wrapper for a Minecraft-Player
 */
public class Hero extends Character implements Listener{

	/** unbuffed default value **/
	public static final double DEFAULT_HERO_SPEED = 1.0;
	/**Team the hero belongs to*/
	private Team team;

	/**associated Minecraft-Player*/
	private final Player player;
	/**Item in hand*/
	private PortableItem activeItem;
	/**Passiv-Slot for tools*/
	private PortableItem passivItem;

	/**
	 * @param player the player to be wrapped
	 */
	public Hero(final Player player) {
		super();

		setAttribute(CharacterAttributes.SPEED, DEFAULT_HERO_SPEED);

		team = Team.NOTEAM;
		this.player = player;
	}

	/**
	 * @return the wrapped Player
	 */
	public Player getPlayer() {
		return player;
	}

	public SpoutPlayer getSpoutPlayer() {
		return SpoutManager.getPlayer(getPlayer());
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
	public void setTeam(final Team team) {
		this.team = team;
	}

	/**
	 * @return the current Item in the active slot
	 */
	public PortableItem getActiveItem() {
		return activeItem;
	}

	/**
	 * @return the current Item in the passive slot
	 */
	public PortableItem getPassivItem() {
		return passivItem;
	}

	/**
	 * @return if true, the Hero can pick up another item
	 */
	public boolean canRecieveItem() {
		if (activeItem == null) {
			return true;
		}

		if (activeItem.isTool() && passivItem == null) {
			return true;
		}

		if (activeItem.isDropable()) {
			return true;
		}

		return false;
	}

	/**
	 * picks up a new item
	 *
	 * @param item the new active item
	 */
	public void setActiveItem(final PortableItem item) {
		if (activeItem == null) {
			activeItem = item;
			getPlayer().getInventory().addItem(item.getItemStack());
			return;
		}

		if (activeItem.isTool() && passivItem == null) {
			passivItem = activeItem;
			activeItem = item;
			getPlayer().getInventory().addItem(item.getItemStack());
			return;
		}

		if (activeItem.isDropable()) {
			dropActiveItem();
			activeItem = item;
			getPlayer().getInventory().addItem(item.getItemStack());
			return;
		}

		throw new RuntimeException();
	}
	
	public void removeActiveItem() {
		getPlayer().getInventory().remove(activeItem.getItemStack());
		activeItem = null;
	}


	/**
	 * transfers the current active item into another inventory
	 *
	 * @param target where to put the inventory
	 */
	public void transferActiveItem(final Hero target) {
		if (activeItem.isTransferable() && target.canRecieveItem()) {

			target.setActiveItem(activeItem);

			// send to minecraft core
			Inventory inventory = getPlayer().getInventory();
			inventory.remove(activeItem.getItemStack());
			activeItem = null;
		}
	}

	/**
	 * Method to drop the active Item
	 * TODO real dropping
	 */
	public void dropActiveItem() {
		if (activeItemDropable()) {
			//creates a new Item, with ItemStack where player stands
			World world = getPlayer().getWorld();
			ItemStack item = activeItem.getItemStack();
			world.dropItem(getLocation(), item);
			//remove Item from MC-Player's inventory
			getPlayer().setItemInHand(null);
			activeItem = null;
		}
		// TODO debug in else branch
	}

	/**
	 * @return true if the currently active item is droppable
	 */
	public boolean activeItemDropable() {
		return activeItem.isDropable();
	}

	@Override
	public void applyEffects() {

		SpoutPlayer sPlayer = getSpoutPlayer();

		sPlayer.sendMessage("speed" + getCurrentSpeed());

		sPlayer.setWalkingMultiplier(getCurrentSpeed());
		sPlayer.setJumpingMultiplier(getAttribute(CharacterAttributes.JUMPMULTIPLIER));
	}

	/**
	 * Implements the Inventory function of not-dropping undropable Items
	 * @param event a PlayerDropItemEvent
	 */
	@EventHandler
	public void handleItemDrop(PlayerDropItemEvent event) {
		/*
		if (event.getPlayer().equals(this.player)) {
			if(inventory.activeItemDropable()) {
				inventory.dropActiveItem();
				event.setCancelled(true);
			}
		}*/
	}

	/**
	 * Handles the Inventory mechanics on picking up an item
	 * @param event PlayerPickupItemEvent
	 */
	@EventHandler
	public void handleItemPickup(PlayerPickupItemEvent event) {
		/*
		if (event.getPlayer().equals(this.player)) {
			PortableItem newItem = new PortableItem(event.getItem().getItemStack());
			event.setCancelled(!inventory.setActiveItem(newItem));
			//ToDo: implement passivItem mechanics
		}*/
	}

	/**
	 * Handles Placing of Blocks/Items
	 * @param event BlockPlaceEvent
	 */
	@EventHandler
	public void handleBlockPlacing(BlockPlaceEvent event) {
		/*if (event.getPlayer().equals(this.player)) {
			if (!inventory.getActiveItem().isPlacable()){
				event.setCancelled(true);
			} else inventory.setActiveItem(null);
		}*/
	}



}
