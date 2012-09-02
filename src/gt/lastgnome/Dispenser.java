package gt.lastgnome;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import gt.general.character.Hero;
import gt.general.character.HeroManager;
import gt.general.world.BlockObserver;
import gt.general.world.ObservableCustomBlock;
import gt.general.world.ObservableCustomBlock.BlockEvent;
import gt.general.world.ObservableCustomBlock.BlockEventType;
import gt.plugin.meta.CustomBlockType;

/**
 * Provides BlockTools on interact
 * @author Roman
 *
 */
public abstract class Dispenser implements BlockObserver {
	
	private int contingent;
	private Block block;
	private CustomBlockType customBlockType;
	private DispenserItem itemType;
	
	/**
	 * @param block the bukkit block that holds the dispenser
	 * @param type CustomBlockType
	 * @param contingent how many items can be given out at the same time
	 */
	public Dispenser(final Block block, final DispenserItem type, final int contingent) {
		this.block = block;
		this.contingent = contingent;
		itemType = type;

		switch(type) {
		case BLOCKTOOL:
			customBlockType = CustomBlockType.BLOCKTOOL_DISPENSER;
			break;
		case BLUE_KEY:
			customBlockType = CustomBlockType.BLUE_KEY_DISPENSER;
			break;
		case RED_KEY:
			customBlockType = CustomBlockType.RED_KEY_DISPENSER;
			break;
		case GREEN_KEY:
			customBlockType = CustomBlockType.GREEN_KEY_DISPENSER;
			break;
		case YELLOW_KEY:
			customBlockType = CustomBlockType.YELLOW_KEY_DISPENSER;
			break;
		default:
			System.out.println("Something went wrong when reading the DispenserItem type in Dispenser. Made a BlockToolDispenser");
			break;
		}
		
		customBlockType.place(block);
		
		registerWithSubject();
	}
	
	/**
	 * @param block the bukkit block that holds the dispenser
	 * @param type CustomBlockType
	 */
	public Dispenser(final Block block, final DispenserItem type) {
		this(block,type,1);
	}
	
	/**
	 * registers this dispenser
	 */
	public void registerWithSubject() {
		ObservableCustomBlock oBlock = customBlockType.getCustomBlock();
		oBlock.addObserver(this, block.getWorld());
	}
	
	/**
	 * unregisters this dispenser
	 */
	public void unregisterFromSubject() {
		ObservableCustomBlock oBlock = customBlockType.getCustomBlock();
		oBlock.removeObserver(this, block.getWorld());
	}
	
	/**
	 * destroy the dispenser
	 */
	public void dispose() {
		unregisterFromSubject();
		block.setType(Material.AIR);
	}
	
	/**
	 * @return block that holds the dispenser
	 */
	public Block getBlock() {
		return block;
	}

	@Override
	public void onBlockEvent(final BlockEvent blockEvent) {
		if(blockEvent.getBlock().equals(block) && blockEvent.getBlockEventType() == BlockEventType.BLOCK_INTERACT) {
			Player player = blockEvent.getPlayer();
			Hero hero = HeroManager.getHero(player);
			
			if(hero!=null) {
				if(contingent>0) {
					if(hero.canRecieveItem()) {
						giveItem(hero);
					} else {
						// can not receive
						player.sendMessage(ChatColor.YELLOW + "You already carry enough.");
					}
				} else {
					// no contingent left
					player.sendMessage(ChatColor.YELLOW + "The Item is already in use.");
				}
			} else {
				// no hero (editor mode)
				player.sendMessage(ChatColor.YELLOW + "The Item is not available in the current game mode.");
			}
		}
		
	}
	
	/**
	 * An item is given out to a hero
	 * @param hero the hero of the player that receives the item
	 */
	protected abstract void giveItem(Hero hero);

	/**
	 * @return the maximum contingent
	 */
	public int getContingent() {
		return contingent;
	}

	/**
	 * @param contingent the new contingent
	 */
	public void setContingent(final int contingent) {
		this.contingent = contingent;
	}
	
	/**
	 * increase the contingent by 1
	 */
	public void increaseContingent() {
		this.contingent++;
	}

	public DispenserItem getItemType() {
		return itemType;
	}

	public void setItemType(DispenserItem itemType) {
		this.itemType = itemType;
	}
}
