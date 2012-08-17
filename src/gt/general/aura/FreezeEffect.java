package gt.general.aura;

import gt.general.character.Character;
import gt.general.character.CharacterAttributes;

public class FreezeEffect extends AbstractEffect {
	
	private FreezeCause cause;
	
	public static enum FreezeCause {
		PAUSE,
		FREEZE
	}
	
	/**
	 * @param cause cause of the freeze effect
	 */
	public FreezeEffect(final FreezeCause cause) {
		this.cause = cause;
	}

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

	/**
	 * @return what caused the freeze
	 */
	public FreezeCause getCause() {
		return cause;
	}
}
