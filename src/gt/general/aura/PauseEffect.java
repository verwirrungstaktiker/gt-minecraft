package gt.general.aura;

import gt.general.character.Character;
import gt.general.character.CharacterAttributes;

public class PauseEffect extends AbstractEffect {

	@Override
	public void performTick() {}

	@Override
	public int remainingTicks() {
		return 1;
	}

	@Override
	public void takeEffect(final Character character) {

		character.scaleAttribute(CharacterAttributes.SPEED, 0.0);
		
		
		
		character.scaleAttribute(CharacterAttributes.JUMPMULTIPLIER, 0.0);
		character.addToAttribute(CharacterAttributes.JUMPMULTIPLIER, -1);

	}

	@Override
	public int getPriorityIndex() {
		return 100;
	}

}
