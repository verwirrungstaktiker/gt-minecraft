package gt.lastgnome;

import gt.general.Game;
import gt.general.PortableItem;
import gt.general.aura.Aura;
import gt.general.aura.Effect;
import gt.general.aura.EffectFactory;
import gt.general.aura.GameAura;
import gt.general.aura.GnomeCarrierEffect;
import gt.general.aura.GnomeSlowEffect;
import gt.general.character.Hero;
import gt.general.logic.trigger.UnlockItemType;
import gt.plugin.meta.Hello;

import java.util.Iterator;

import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.getspout.spoutapi.inventory.SpoutItemStack;

/**
 * The Gnome Item
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
	 * @param game the corresponding game
	 */
	public GnomeItem(final Plugin plugin, final String name, final String texture, final Game game) {
		super(plugin, name, texture, UnlockItemType.GNOME);

		gnomeAura = new GameAura(new EffectFactory() {
			@Override
			public Effect getEffect() {
				return new GnomeSlowEffect();
			}
		},
		Aura.OWNER_ONLY,
		Aura.INFINITE_DURATION,
		20,
		game);
		
		gnomeEffect = new GnomeCarrierEffect();
		itemStack = new SpoutItemStack(this);

		setTransferable(true);
		setDropable(false);
		setTool(false);
	}

	/**
	 * Creates a new Gnome
	 * @param plugin the plugin we run
	 * @param game the game that holds the Gnome
	 */
	public GnomeItem(final Plugin plugin, final Game game) {
		this(plugin, "GnomeItem", "http://dl.dropbox.com/u/29386658/gt/textures/gnome2_16x16.png", game);
	}

	/**
	 * Creates a new Gnome
	 * @param game the game that holds the Gnome
	 */
	public GnomeItem(final Game game) {
		this(Hello.getPlugin(), game);
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
