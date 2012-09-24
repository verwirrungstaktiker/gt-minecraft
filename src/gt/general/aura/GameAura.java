/*******************************************************************************
 * Projektpraktikum: Game Technology 2012
 * Minecraft-Modifikation für kollaboratives Spielen
 * 
 * Sebastian Fahnenschreiber (sebastian.fahnenschreiber@stud.tu-darmstadt.de)
 * Roman Neß (roman.ness@stud.tu-darmstadt.de)
 * Philipp Pascal Battenberg (philipp.battenberg@stud.tu-darmstadt.de)
 ******************************************************************************/
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
