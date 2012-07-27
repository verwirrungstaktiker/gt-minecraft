package gt.lastgnome;

import gt.general.PortableItem;
import gt.general.aura.Aura;
import gt.general.aura.Effect;
import gt.general.aura.EffectFactory;
import gt.general.aura.GnomeCarrierEffect;
import gt.general.aura.GnomeSlowEffect;
import gt.general.character.Hero;
import gt.plugin.meta.Hello;

import java.util.Iterator;

import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.getspout.spoutapi.inventory.SpoutItemStack;

/**
 * Item-class for Gnome.
 */
public final class GnomeItem extends PortableItem {

	/** Aura associated with Gnome --> adds the slow stacks. */
	private final Aura gnomeAura;

	/** Effect associated with Gnome --> e.g. cannot jump */
	private final Effect gnomeEffect;

	private final ItemStack itemStack;

	/** The ID that is replaced by this new item (diamond). **/
	public static final int RAWID = 264;

	/**
	 * Creates a new Gnome.
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
				return new GnomeSlowEffect();
			}
		},
		Aura.OWNER_ONLY,
		Aura.INFINITE_DURATION,
		20);
		
		gnomeEffect = new GnomeCarrierEffect();
		itemStack = new SpoutItemStack(this);

		setTransferable(true);
		setDropable(false);
		setTool(false);
	}

	/**
	 * Creates a new Gnome
	 * @param plugin the plugin we run
	 */
	public GnomeItem(final Plugin plugin) {
		this(plugin, "GnomeItem", "http://dl.dropbox.com/u/29386658/gt/textures/gnome2_16x16.png");
	}

	/**
	 * Creates a new Gnome
	 */
	public GnomeItem() {
		this(Hello.getPlugin());
	}

	/**
	 * @return the Gnome aura
	 */
	public Aura getGnomeAura() {
		return gnomeAura;
	}

	/**
	 * @return the Gnome effect
	 */
	public Effect getGnomeEffect() {
		return gnomeEffect;
	}

	@Override
	public ItemStack getItemStack() {
		return itemStack;
	}

	@Override
	public void onAttachHero(final Hero hero) {
		System.out.println("attach gnome");
		
		hero.addEffect(getGnomeEffect());
		getGnomeAura().setOwner(hero);
	}

	@Override
	public void onDetachHero(final Hero hero) {
		System.out.println("detach gnome");
		getGnomeAura().setOwner(null);
		
		// remove effects - slow and misc
		Iterator<Effect> it = hero.getEffects().iterator();
		while(it.hasNext()) {
		    if (it.next() instanceof GnomeSlowEffect) {
		        it.remove();
		    }
		}
		
		hero.removeEffect(getGnomeEffect());
	}

}
