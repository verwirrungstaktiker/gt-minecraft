/*******************************************************************************
 * Projektpraktikum: Game Technology 2012
 * Minecraft-Modifikation fuer kollaboratives Spielen
 * 
 * Sebastian Fahnenschreiber (sebastian.fahnenschreiber@stud.tu-darmstadt.de)
 * Roman Ness (roman.ness@stud.tu-darmstadt.de)
 * Philipp Pascal Battenberg (philipp.battenberg@stud.tu-darmstadt.de)
 ******************************************************************************/
package gt.general.character;

import gt.general.PortableItem;
import gt.general.character.Hero.Notification;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventoryConnector implements HeroObserver {

	@Override
	public void update(final Hero hero, final Notification notification) {
		if (notification == Notification.INVENTORY) {
			updateInventory(hero);
		}
	}

	/**
	 * @param hero
	 *            Whose inventory should be updated
	 */
	public void updateInventory(final Hero hero) {
		Inventory inventory = hero.getPlayer().getInventory();

		ItemStack[] items = inventory.getContents();

		PortableItem active = hero.getActiveItem();
		if (active != null) {
			items[0] = active.getItemStack();
		} else {
			items[0] = null;
		}

//		PortableItem passive = hero.getPassivItem();
//		if (passive != null) {
//			items[1] = passive.getItemStack();
//		} else {
//			items[1] = null;
//		}

		inventory.setContents(items);
	}
}
