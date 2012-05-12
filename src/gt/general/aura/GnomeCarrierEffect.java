package gt.general.aura;

import gt.general.Character;
import gt.general.CharacterAttributes;
import gt.lastgnome.LastGnomeHero;

public class GnomeCarrierEffect implements Effect {

	@Override
	public void performTick() {	
	}

	@Override
	public int remainingTicks() {
		return 1; // never expire
	}
	
	@Override
	public void takeEffect(final Character character) {
		
		// lowers the speed by 1/8
		if(character instanceof LastGnomeHero) {
			LastGnomeHero hero = (LastGnomeHero)character;
			hero.addToAttribute(CharacterAttributes.SPEED,
								hero.getBaseAttribute(CharacterAttributes.SPEED) * -0.0625);
		}

	}



}
