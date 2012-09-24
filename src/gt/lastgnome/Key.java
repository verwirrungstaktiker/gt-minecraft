/*******************************************************************************
 * Projektpraktikum: Game Technology 2012
 * Minecraft-Modifikation fuer kollaboratives Spielen
 * 
 * Sebastian Fahnenschreiber (sebastian.fahnenschreiber@stud.tu-darmstadt.de)
 * Roman Ness (roman.ness@stud.tu-darmstadt.de)
 * Philipp Pascal Battenberg (philipp.battenberg@stud.tu-darmstadt.de)
 ******************************************************************************/
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
	 * @param color color of the key
	 */
	public Key(final Plugin plugin, final String name, final String texture, final DispenserItem color) {
		super(plugin, name, texture, UnlockItemType.KEY);

		itemStack = new SpoutItemStack(this);
		keyColor = color;
		
		setVisuals(color);
		
		setTool(false);
		setDropable(false);
		setTransferable(false);
	}

	/**
	 * set the visual corresponding to the keyColor
	 * @param color the color of the key
	 */
	private void setVisuals(final DispenserItem color) {
		switch(color) {
		case BLUE_KEY:
			setTexture("https://raw.github.com/verwirrungstaktiker/gt-minecraft/master/res/texture/key_blue.png");
			setName("blue_key");
			break;
		case RED_KEY:
			setTexture("https://raw.github.com/verwirrungstaktiker/gt-minecraft/master/res/texture/key_red.png");
			setName("red_key");
			break;
		case GREEN_KEY:
			setTexture("https://raw.github.com/verwirrungstaktiker/gt-minecraft/master/res/texture/key_green.png");
			setName("green_key");
			break;
		case YELLOW_KEY:
			setTexture("https://raw.github.com/verwirrungstaktiker/gt-minecraft/master/res/texture/key_yellow.png");
			setName("yellow_key");
			break;
		default:
			break;
		}
	}

	/**
	 * Creates a new key
	 * @param plugin the plugin we run
	 * @param color the color of the key
	 */
	public Key(final Plugin plugin, final DispenserItem color) {
		this(plugin, "PlaceHolder", "https://raw.github.com/verwirrungstaktiker/gt-minecraft/master/res/texture/key_black.png",color);
	}

	/**
	 * Creates a new key
	 * @param color color of the key
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
	
	/**
	 * @return the color of the key
	 */
	public DispenserItem getKeyColor() {
		return keyColor;
	}

}
