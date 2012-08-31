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
 * Item-class for GnomeSocket
 */
public class BlocktoolDispenser implements BlockObserver {
	
	private Block block;
	private int contingent;
	
	/**
	 * @param block the bukkit block that holds the dispenser
	 */
	public BlocktoolDispenser(final Block block) {
		this.block = block;
		contingent = 1;

		CustomBlockType.BLOCKTOOL_DISPENSER.place(block);
		
		registerWithSubject();
	}
	
	/**
	 * registers this dispenser
	 */
	public void registerWithSubject() {
		ObservableCustomBlock oBlock = CustomBlockType.BLOCKTOOL_DISPENSER.getCustomBlock();
		oBlock.addObserver(this, block.getWorld());
	}
	
	/**
	 * unregisters this dispenser
	 */
	public void unregisterFromSubject() {
		ObservableCustomBlock oBlock = CustomBlockType.BLOCKTOOL_DISPENSER.getCustomBlock();
		oBlock.removeObserver(this, block.getWorld());
	}
	
	public void dispose() {
		unregisterFromSubject();
		block.setType(Material.AIR);
	}
	
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
					hero.setActiveItem(new BlockTool());
					player.sendMessage(ChatColor.GREEN + "You obtained an Obsidian Placing Device.");
					
					contingent--;
				} else {
					player.sendMessage(ChatColor.YELLOW + "The Obsidian Placing Device is already in use.");
				}
			} else {
				player.sendMessage(ChatColor.YELLOW + "The device is not available in the current game mode.");
			}
		}
		
	}

	public int getContingent() {
		return contingent;
	}

	public void setContingent(int contingent) {
		this.contingent = contingent;
	}
}
