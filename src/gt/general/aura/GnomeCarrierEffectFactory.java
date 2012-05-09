package gt.general.aura;

public class GnomeCarrierEffectFactory implements EffectFactory {

	@Override
	public Effect getEffect() {
		return new GnomeCarrierEffect();
	}

}
