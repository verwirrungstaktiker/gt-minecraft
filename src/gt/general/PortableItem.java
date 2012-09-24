/*******************************************************************************
 * Projektpraktikum: Game Technology 2012
 * Minecraft-Modifikation f�r kollaboratives Spielen
 * 
 * Sebastian Fahnenschreiber (sebastian.fahnenschreiber@stud.tu-darmstadt.de)
 * Roman Ne� (roman.ness@stud.tu-darmstadt.de)
 * Philipp Pascal Battenberg (philipp.battenberg@stud.tu-darmstadt.de)
 ******************************************************************************/
package gt.general;

import gt.general.character.Hero;
import gt.general.logic.trigger.UnlockItemType;

import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.getspout.spoutapi.material.item.GenericCustomItem;

/**
 * class for portable items
 */
public abstract class PortableItem extends GenericCustomItem implements Listener{
	/**dropable items can be dropped to the floor*/
	private boolean dropable;

	/** tools can be put in the secondary slot */
	private boolean tool;

	/** if true the item can be transferred directly into another inventory */
	private boolean transferable;
	
	private UnlockItemType type;

	/**
	 * Creates a new PortableItem
	 * @param plugin the plugin we run
	 * @param name Name of the Item
	 * @param texture Texture for the new Item
	 * @param type the kind of item
	 */
	public PortableItem(final Plugin plugin, final String name, final String texture, final UnlockItemType type) {
		super(plugin, name, texture);

		dropable = false;
		tool = false;
		transferable = false;
		
		this.type = type;
	}
	
	/**
	 * @return true if item can be dropped
	 */
	public boolean isDropable() {
		return dropable;
	}

	/**
	 * Sets if the item can be dropped
	 * @param dropable true, if item can be dropped
	 */
	public void setDropable(final boolean dropable) {
		this.dropable = dropable;
	}
	/**
	 * @return true, if item can be in passivItem-Slot
	 */
	public boolean isTool() {
		return tool;
	}

	/**
	 * Sets if item can be in passivItem-Slot
	 * @param tool true, if item can be in passivItem-Slot
	 */
	public void setTool(final boolean tool) {
		this.tool = tool;
	}

	/**
	 * @return the transferable
	 */
	public boolean isTransferable() {
		return transferable;
	}

	/**
	 * @param transferable the transferable to set
	 */
	public void setTransferable(final boolean transferable) {
		this.transferable = transferable;
	}

	/**
	 * @return the kind of the item
	 */
	public UnlockItemType getType() {
		return type;
	}
	
	/**
	 * @return the ItemStack which represents this item
	 */
	public abstract ItemStack getItemStack();

	/**
	 * @param hero will be called if a hero puts this in his inventory
	 */
	public abstract void onAttachHero(Hero hero);
	
	/**
	 * @param hero will be called if a hero removes this from his inventory
	 */
	public abstract void onDetachHero(Hero hero);
	
}
