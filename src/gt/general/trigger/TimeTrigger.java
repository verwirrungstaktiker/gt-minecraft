package gt.general.trigger;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.bukkit.World;
import org.bukkit.block.Block;

/**
 * Trigger which is called in an interval
 */
public class TimeTrigger extends Trigger{

	protected int count,interval;

	/**
	 *
	 */
	public TimeTrigger() {
		/*super("time");
		
		//super(repeat,callback,tm);
		this.interval = interval;
		this.count = interval;*/
	}

	/*
	@Override
	public void checkTrigger() {
		--count;
		if (count == 0) {
			callback.run();
			if (repeat) count = interval;
			else tm.deregisterTrigger(this);
		}

	}*/

	@Override
	public void setup(Map<String, Object> values, World world) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Map<String, Object> dump() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Set<Block> getBlocks() {
		return new HashSet<Block>();
	}

}
