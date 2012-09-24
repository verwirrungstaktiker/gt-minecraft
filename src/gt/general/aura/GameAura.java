/*******************************************************************************
 * Projektpraktikum: Game Technology 2012
 * Minecraft-Modifikation fuer kollaboratives Spielen
 * 
 * Sebastian Fahnenschreiber (sebastian.fahnenschreiber@stud.tu-darmstadt.de)
 * Roman Ness (roman.ness@stud.tu-darmstadt.de)
 * Philipp Pascal Battenberg (philipp.battenberg@stud.tu-darmstadt.de)
 ******************************************************************************/
package gt.general.aura;

import gt.general.Game;

public class GameAura extends Aura {

	private final Game game;
	
	/**
	 * construct a new GameAura
	 * @param effectFactory factory that creates the effects
	 * @param distance aura radius
	 * @param duration aura duration
	 * @param rate arua rate
	 * @param game corresponding game
	 */
	public GameAura(final EffectFactory effectFactory, final int distance, final int duration,
			final int rate, final Game game) {
		super(effectFactory, distance, duration, rate);
		this.game = game;
	}

	@Override
	protected boolean spreadEffectNow() {
		
		return game.isRunning();
	}

}
