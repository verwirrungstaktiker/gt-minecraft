package gt.lastgnome;

import gt.general.PortableItem;
import gt.general.character.Hero;
import gt.general.logic.trigger.UnlockItemType;
import gt.plugin.meta.Hello;

import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.getspout.spoutapi.inventory.SpoutItemStack;

/**
 * @author Roman
 * The Key item
 */
public class Key extends PortableItem {

	private final ItemStack itemStack;
	private final DispenserItem keyColor;

	/**
	 * Creates a new Key.
	 * @param plugin  the plugin we run
	 * @param name the name of the tool
	 * @param texture texture of the tool
	 */
	public Key(final Plugin plugin, final String name, final String texture, final DispenserItem color) {
		super(plugin, name, texture, UnlockItemType.KEY);

		itemStack = new SpoutItemStack(this);
		keyColor = color;
		
		setTool(true);
		setDropable(true);
		setTransferable(false);
	}

	/**
	 * Creates a new key
	 * @param plugin the plugin we run
	 */
	public Key(final Plugin plugin, final DispenserItem color) {
		this(plugin, "PlaceHolder", "http://www.upedu.org/applet/images/key_concept.gif",color);
	}

	/**
	 * Creates a new key
	 */
	public Key(final DispenserItem color) {
		this(Hello.getPlugin(),color);
	}

	@Override
	public ItemStack getItemStack() {
		return itemStack;
	}

	@Override
	public void onAttachHero(final Hero hero) {
		System.out.println("picked up key.");
	}

	@Override
	public void onDetachHero(final Hero hero) {
		System.out.println("dropped key.");
	}
	
	public DispenserItem getKeyColor() {
		return keyColor;
	}

}
