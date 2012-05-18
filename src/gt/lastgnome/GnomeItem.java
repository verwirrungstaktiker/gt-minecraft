package gt.lastgnome;

import gt.general.PortableItem;
import gt.general.aura.Aura;
import gt.general.aura.Effect;
import gt.general.aura.EffectFactory;
import gt.general.aura.GnomeCarrierEffect;
import gt.plugin.helloworld.HelloWorld;

import org.bukkit.plugin.Plugin;

/**
 * Item-class for Gnome
 */
public class GnomeItem extends PortableItem {
	/**Aura associated with Gnome*/
	private final Aura gnomeAura;

	/**
	 * Creates a new Gnome
	 * @param plugin  the plugin we run
	 * @param name the name of the gnome
	 * @param texture texture for the gnome
	 */
	public GnomeItem(final Plugin plugin, final String name, final String texture) {
		super(plugin, name, texture);

		gnomeAura = new Aura(new EffectFactory() {
			@Override
			public Effect getEffect() {
				System.out.println("one more stack of gnome slow");
				return new GnomeCarrierEffect();
			}
		},
		Aura.OWNER_ONLY,
		Aura.INFINITE_DURATION,
		20);
	}

	/**
	 * Creates a new Gnome
	 * @param plugin the plugin we run
	 */
	public GnomeItem(final Plugin plugin) {
		this(plugin, "GnomeItem", "res/textures/gnome_16x16.png");
	}

	/**
	 * Creates a new Gnome
	 */
	public GnomeItem() {
		this(HelloWorld.getPlugin());
	}

	/**
	 * @return the gnomes aura
	 */
	public Aura getGnomeAura() {
		return gnomeAura;
	}


}
