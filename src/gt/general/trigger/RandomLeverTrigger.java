package gt.general.trigger;

import gt.general.world.ObservableCustomBlock;
import gt.plugin.meta.CustomBlockType;

import java.util.Map;
import java.util.Vector;

import org.bukkit.World;
import org.bukkit.block.Block;
import org.getspout.spoutapi.SpoutManager;

public class RandomLeverTrigger extends LeverRedstoneTrigger{
	
	private final Vector<Block> signals;
	
	
	
	public RandomLeverTrigger(Block trigger, Block against) {
		super(trigger, against);
		signals = new Vector<Block>();
	}
	
	public RandomLeverTrigger() {
		super();
		signals = new Vector<Block>();
	}



	@Override
	public Map<String, Object> dump() {
		Map<String, Object> map = super.dump();
		
		map.put("signals", signals.size());
		for (int i = 0;i<signals.size();++i) {
			map.putAll(prefixedCoordinatesFromBlock("s"+i+"_",signals.get(i)));			
		}
		
		return map;
	}
	
	@Override
	public void setup(final Map<String, Object> values, final World world) {
		super.setup(values, world);
		int signalCount = (Integer) values.get("signals");
				
		ObservableCustomBlock signal;
		
		long rnd = Math.round(Math.random());
		if (rnd == 1) {
			toggleInvert();
			signal = CustomBlockType.RED_SIGNAL.getCustomBlock();
		} else {
			signal = CustomBlockType.GREEN_SIGNAL.getCustomBlock();			
		}
		
		for (int i = 0; i < signalCount; ++i) {
			signals.add(blockFromPrefixedCoordinates("s"+i+"_", values, world));
			SpoutManager.getMaterialManager().overrideBlock(signals.get(i), signal);
		}
		
	}

}
