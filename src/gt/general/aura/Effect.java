package gt.general.aura;

import gt.general.Character;

/**
 * Effect, that can affect characters
 */
public interface Effect {

	/**
	 * performs calculations on each tick, such as decreasing the remaining time
	 */
	void performTick();

	/**
	 * @return if 0, the effect will be removed
	 */
	int remainingTicks();

	/**
	 * @param character to be manipulated
	 */
	void takeEffect(Character character);
}
