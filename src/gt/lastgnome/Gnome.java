package gt.lastgnome;

import org.bukkit.inventory.ItemStack;

import gt.general.Item;
import gt.general.aura.Aura;
import gt.general.aura.Effect;
import gt.general.aura.EffectFactory;
import gt.general.aura.GnomeCarrierEffect;

public class Gnome extends Item {

	private final Aura gnomeAura;
	
	public Gnome(ItemStack itemStack) {
		super(itemStack);
		name = "Gnome";
		setDropable(false);
		setTool(false);
		setPlacable(false);
		
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

	public Aura getGnomeAura() {
		return gnomeAura;
	}

}
