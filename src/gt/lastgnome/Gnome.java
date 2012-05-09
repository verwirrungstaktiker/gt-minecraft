package gt.lastgnome;

import org.bukkit.inventory.ItemStack;

import gt.general.Item;

public class Gnome extends Item {

	public Gnome(ItemStack itemstack) {
		super(itemstack);
		name = "Gnome";
		setDropable(false);
		setTool(false);
		setPlacable(false);
	}

}
