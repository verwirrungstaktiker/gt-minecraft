/*******************************************************************************
 * Projektpraktikum: Game Technology 2012
 * Minecraft-Modifikation f�r kollaboratives Spielen
 * 
 * Sebastian Fahnenschreiber (sebastian.fahnenschreiber@stud.tu-darmstadt.de)
 * Roman Ne� (roman.ness@stud.tu-darmstadt.de)
 * Philipp Pascal Battenberg (philipp.battenberg@stud.tu-darmstadt.de)
 ******************************************************************************/
package gt.lastgnome;

import gt.general.character.Hero;
import gt.plugin.meta.CustomBlockType;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;

public class KeyDispenser extends Dispenser {

	/**
	 * @param block bukkit block that holds the dispenser
	 * @param color color of keys that are given out
	 */
	public KeyDispenser(final Block block, final DispenserItem color) {
		super(block, color, 1);
	}

	@Override
	protected void giveItem(final Hero hero) {
		hero.setActiveItem(new Key(getItemType()));
		hero.getPlayer().sendMessage(ChatColor.GREEN + "You obtained a Key.");
		
		setContingent(-1);
	}

}
