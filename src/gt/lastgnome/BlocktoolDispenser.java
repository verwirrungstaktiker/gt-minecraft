package gt.lastgnome;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import gt.general.character.Hero;

/**
 * Provides BlockTools on interact
 * @author Roman
 *
 */
public class BlocktoolDispenser extends Dispenser {
	/** maximum active blockTools at the same time */
	private static final int DEFAULT_CONTINGENT = 1;
	
	/**
	 * @param block the bukkit block that holds the dispenser
	 * @param contingent amount of blockTools that the dispenser will give out
	 */
	public BlocktoolDispenser(final Block block, final int contingent) {
		super(block, DispenserItem.BLOCKTOOL, contingent);
	}
	
	/**
	 * @param block the bukkit block that holds the dispenser
	 */
	public BlocktoolDispenser(final Block block) {
		this(block, DEFAULT_CONTINGENT);
	}

	@Override
	protected void giveItem(final Hero hero) {
		hero.setActiveItem(new BlockTool(this));
		hero.getPlayer().sendMessage(ChatColor.GREEN + "You obtained an Obsidian Placing Device.");
		
		setContingent(getContingent()-1);
	}

}
