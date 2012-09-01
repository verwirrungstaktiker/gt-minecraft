package gt.lastgnome;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.getspout.spoutapi.block.SpoutBlock;
import org.getspout.spoutapi.inventory.SpoutItemStack;
import org.getspout.spoutapi.player.SpoutPlayer;

import gt.general.PortableItem;
import gt.general.aura.FreezeEffect.FreezeCause;
import gt.general.character.Hero;
import gt.general.character.HeroManager;
import gt.general.logic.trigger.UnlockItemType;
import gt.plugin.meta.Hello;

/** a single use tool that spawns blocks */
public class BlockTool extends PortableItem{
	
	public static final Material MATERIAL = Material.OBSIDIAN;
	/** the maximum allowed range between dispenser and blocktool */
	private static final double MAX_RANGE = 20.0;
	/** the dispenser that created this blockTool */
	private BlocktoolDispenser dispenser;
	private ItemStack itemStack;
	
	/**
	 * @param plugin plugin that holds the tool
	 * @param name name of the tool
	 * @param texture link to the texture
	 * @param dispenser the dispenser that creates this blockTool
	 */
	public BlockTool(final Plugin plugin, final String name, final String texture, final BlocktoolDispenser dispenser) {
		super(plugin, name, texture, UnlockItemType.BLOCK_TOOL);

		itemStack = new SpoutItemStack(this);
		this.dispenser = dispenser;
		
		setTool(false);
		setDropable(true);
		setTransferable(true);
		
	}
	
	/** 
	 * @param dispenser the dispenser that creates this blockTool
	 */
	public BlockTool(final BlocktoolDispenser dispenser) {
		this(Hello.getPlugin(), "BlockTool", "http://www.mariowiki.com/images/9/95/QuestionMarkBlockNSMB.png", dispenser);
	}

	@Override
	public ItemStack getItemStack() {
		return itemStack;
	}

	@Override
	public void onAttachHero(final Hero hero) {
		hero.freeze();
	}

	@Override
	public void onDetachHero(final Hero hero) {
		hero.resume(FreezeCause.FREEZE);
	}
	
	@Override
	public boolean onItemInteract(final SpoutPlayer player, final SpoutBlock block, final BlockFace face) {

		if(block!=null && block.getType()==MATERIAL) {
			
			Block relBlock = block.getRelative(face);
			
			if(inRange(relBlock.getLocation())) {
				// build
				relBlock.setType(MATERIAL);
				player.sendMessage(ChatColor.GREEN + "The device created a block.");
			} else {
				// recycle
				player.sendMessage(ChatColor.YELLOW + "The device was used too far from its origin. It vanished.");
			}
			HeroManager.getHero(player).removeActiveItem();
			dispenser.increaseContingent();
			return true;
		} else {
			// wrong surface
			player.sendMessage(ChatColor.RED + "Cannot be placed here! (Only on " + MATERIAL + ")");
			return false;
		}
	}
	
	/**
	 * @param blockLoc location of the placed block
	 * @return true if the Block can be placed here
	 */
	private boolean inRange(final Location blockLoc) {
		Location dispenserLoc = dispenser.getBlock().getLocation();
		
		return (dispenserLoc.distance(blockLoc) <= MAX_RANGE);
	}
}
