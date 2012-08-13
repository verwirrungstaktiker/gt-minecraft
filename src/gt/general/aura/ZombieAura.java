package gt.general.aura;

public class ZombieAura extends Aura {

	public ZombieAura(EffectFactory effectFactory, int distance, int duration,
			int rate) {
		super(effectFactory, distance, duration, rate);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected boolean spreadEffectNow() {
		return false;
	}

}
