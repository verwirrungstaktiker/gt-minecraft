package gt.general.aura;

import gt.general.character.Character;
import gt.general.character.CharacterAttributes;
import gt.general.character.ZombieManager;

public class ZombieSpeedEffect extends AbstractEffect {

	private int remainingTicks = ZombieManager.SCHEDULE_RATE;
	
	@Override
	public int getPriorityIndex() {
		return 0;
	}

	@Override
	public void performTick() {
		remainingTicks -= ZombieManager.SCHEDULE_RATE;
	}

	@Override
	public int remainingTicks() {
		return remainingTicks;
	}

	@Override
	public void takeEffect(final Character character) {
		
		character.addToAttribute(CharacterAttributes.SPEED, 20.0);
	}

}
