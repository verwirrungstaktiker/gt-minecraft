package gt.lastgnome;

import gt.general.PortableItem;
import gt.general.aura.Aura;
import gt.general.aura.Effect;
import gt.general.aura.EffectFactory;
import gt.general.aura.GnomeCarrierEffect;
import gt.plugin.helloworld.HelloWorld;

import org.bukkit.plugin.Plugin;

public class GnomeItem extends PortableItem {
	
	private final Aura gnomeAura;
	
	public GnomeItem(Plugin plugin, String name, String texture) {
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
	
	public GnomeItem(Plugin plugin) {
		this(plugin, "GnomeItem", "res/textures/gnome_16x16.png");
	}
	
	public GnomeItem() {
		this(HelloWorld.getPlugin());
	}

	public Aura getGnomeAura() {
		return gnomeAura;
	}

		
}
