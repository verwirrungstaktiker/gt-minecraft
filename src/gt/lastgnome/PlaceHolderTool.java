package gt.lastgnome;

import gt.general.PortableItem;
import gt.general.character.Hero;
import gt.plugin.Hello;

import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.getspout.spoutapi.inventory.SpoutItemStack;

/**
 * Item-class for Gnome.
 */
public final class PlaceHolderTool extends PortableItem {

	private final ItemStack itemStack;

	/** The ID that is replaced by this new item (shears). **/
	public static final int RAWID = 359;

	/**
	 * Creates a new Gnome.
	 * @param plugin  the plugin we run
	 * @param name the name of the tool
	 * @param texture texture of the tool
	 */
	public PlaceHolderTool(final Plugin plugin, final String name, final String texture) {
		super(plugin, name, texture);

		itemStack = new SpoutItemStack(this);
		setTool(true);
		setDropable(true);

		setTransferable(false);
	}

	/**
	 * Creates a new tool
	 * @param plugin the plugin we run
	 */
	public PlaceHolderTool(final Plugin plugin) {
		this(plugin, "PlaceHolder", "https://dl.dropbox.com/u/29386658/gt/textures/placeholder_16x16.png");
	}

	/**
	 * Creates a new tool
	 */
	public PlaceHolderTool() {
		this(Hello.getPlugin());
	}

	@Override
	public ItemStack getItemStack() {
		return itemStack;
	}

	@Override
	public void onAttachHero(final Hero hero) {
		System.out.println("picked up tool.");
	}

	@Override
	public void onDetachHero(final Hero hero) {
		System.out.println("dropped tool.");
	}

}
