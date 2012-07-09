package gt.general.logic.trigger;

import gt.general.logic.persistance.PersistanceMap;
import gt.general.world.ObservableCustomBlock;
import gt.plugin.meta.CustomBlockType;

import java.util.Collection;
import java.util.Vector;

import org.bukkit.World;
import org.bukkit.block.Block;
import org.getspout.spoutapi.SpoutManager;

public class RandomLeverTrigger extends LeverRedstoneTrigger{
	
	public static final String KEY_SIGNALS = "signals";
	
	private Collection<Block> signals;
	
	/**
	 * @param trigger the lever to be used as trigger
	 * @param against against which block the player placed the trigger
	 */
	public RandomLeverTrigger(final Block trigger, final Block against) {
		super(trigger, against);
		signals = new Vector<Block>();
	}
	
	/** to be used for persistence */
	public RandomLeverTrigger() {}

	@Override
	public PersistanceMap dump() {
		PersistanceMap map = super.dump();
		map.put(KEY_SIGNALS, signals);
		return map;
	}
	
	@Override
	public void setup(final PersistanceMap values, final World world) {
		super.setup(values, world);
		
		signals = values.getBlocks(KEY_SIGNALS, world);
				
		ObservableCustomBlock signalBlock;
		
		long rnd = Math.round(Math.random());
		if (rnd == 1) {
			toggleInvert();
			signalBlock = CustomBlockType.RED_SIGNAL.getCustomBlock();
		} else {
			signalBlock = CustomBlockType.GREEN_SIGNAL.getCustomBlock();			
		}
		
		
		for(Block signal : signals) {
			SpoutManager.getMaterialManager().overrideBlock(signal, signalBlock);
		}
	}

}
