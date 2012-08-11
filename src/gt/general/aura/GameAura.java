package gt.general.aura;

import gt.general.character.Character;
import gt.general.character.Hero;

public class GameAura extends Aura {

	public GameAura(EffectFactory effectFactory, int distance, int duration,
			int rate) {
		super(effectFactory, distance, duration, rate);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected boolean spreadEffectNow() {
		
		Character character = getOwner();		
		if(character != null && character instanceof Hero) {
			
			Hero h = (Hero) character;
			
			if(h.inGame()) {
				return h.getGame().isRunning();
			}
		}

		return false;
	}

}
