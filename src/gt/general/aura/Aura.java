package gt.general.aura;

import gt.general.Character;
import gt.plugin.helloworld.HelloWorld;

import org.bukkit.Bukkit;

public class Aura implements Runnable {

	private Character owner = null;
	private final EffectFactory effectFactory;

	private final int distance;
	private final int rate;
	private int duration;

	private int taskId = -1;

	public static final int INFINITE_DURATION = Integer.MIN_VALUE;
	public static final int EACH_TICK = 1;
	public static final int OWNER_ONLY = 0;

	/**
	 * generates a new Aura
	 * 
	 * @param effectFactory
	 *            a factory for the Effect to be spread
	 * @param distance
	 *            the distance in which the Effect is applied to Entities
	 * @param duration
	 *            after duration Ticks the Aura is discarded
	 * @param rate
	 *            n-th tick which should spread the effect
	 */
	public Aura(final EffectFactory effectFactory, final int distance,
			final int duration, final int rate) {
		this.owner = null;
		this.effectFactory = effectFactory;
		this.distance = distance;
		this.duration = duration;
		this.rate = rate;
	}

	/**
	 * sets the newowner and starts scheduling of the aura
	 * 
	 * @param owner
	 *            the owner of this Aura
	 */
	public void setOwner(final Character owner) {
		ensureUnlinked();

		this.owner = owner;

		taskId = Bukkit.getScheduler().scheduleAsyncRepeatingTask(
				HelloWorld.getPlugin(), this, 0, rate);
	}

	/**
	 * ensures that this is not simulated currently and there is no owner
	 */
	private void ensureUnlinked() {
		if (taskId != -1) {
			Bukkit.getScheduler().cancelTask(taskId);
		}

		if (owner != null) {
			owner.getAuras().remove(this);
		}
	}

	@Override
	public void run() {
		if (dead()) {
			ensureUnlinked();
		}

		if (owner != null) {
			spreadEffect();
		}
	}

	/**
	 * @return true if this aura should be dismissed
	 */
	private boolean dead() {
		if (duration == INFINITE_DURATION) {
			return false;
		} else {
			duration = -rate;
			return duration < 0;
		}
	}

	/**
	 * spreads the effect of this aura to all Characters in distance
	 */
	private void spreadEffect() {

		if (distance == OWNER_ONLY) {
			owner.addEffect(effectFactory.getEffect());
		} else {
			// TODO spead auras
			throw new RuntimeException("not implemented yet");
		}
	}
}
