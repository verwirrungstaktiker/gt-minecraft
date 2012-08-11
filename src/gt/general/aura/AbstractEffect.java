package gt.general.aura;


public abstract class AbstractEffect implements Effect {

	@Override
	public int compareTo(final Effect o) {
		return Integer.valueOf(getPriorityIndex()).compareTo(Integer.valueOf(o.getPriorityIndex()));
	}
}
