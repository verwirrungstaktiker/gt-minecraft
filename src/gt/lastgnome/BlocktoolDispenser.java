package gt.lastgnome;

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
	
	/**
	 * @param block the bukkit block that holds the dispenser
	 */
	public BlocktoolDispenser(final Block block) {
		this.block = block;

		CustomBlockType.BLOCKTOOL_DISPENSER.place(block);
		
		registerWithSubject();
	}
	
	/**
	 * registers this dispenser
	 */
	public void registerWithSubject() {
		ObservableCustomBlock triggerBlock = CustomBlockType.BLOCKTOOL_DISPENSER.getCustomBlock();
		triggerBlock.addObserver(this, block.getWorld());
	}
	
	/**
	 * unregisters this dispenser
	 */
	public void unregisterFromSubject() {
		ObservableCustomBlock triggerBlock = CustomBlockType.BLOCKTOOL_DISPENSER.getCustomBlock();
		triggerBlock.removeObserver(this, block.getWorld());
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
			
			hero.setActiveItem(new BlockTool());
			player.sendMessage("You obtained an Obsidian Placing Device.");
		}
		
	}
}
