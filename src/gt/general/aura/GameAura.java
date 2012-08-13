package gt.general.aura;

import gt.general.Game;

public class GameAura extends Aura {

	private final Game game;
	
	public GameAura(EffectFactory effectFactory, int distance, int duration,
			int rate, final Game game) {
		super(effectFactory, distance, duration, rate);
		this.game = game;
	}

	@Override
	protected boolean spreadEffectNow() {
		
		return game.isRunning();
	}

}
