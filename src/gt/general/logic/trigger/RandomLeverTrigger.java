/*******************************************************************************
 * Projektpraktikum: Game Technology 2012
 * Minecraft-Modifikation fuer kollaboratives Spielen
 * 
 * Sebastian Fahnenschreiber (sebastian.fahnenschreiber@stud.tu-darmstadt.de)
 * Roman Ness (roman.ness@stud.tu-darmstadt.de)
 * Philipp Pascal Battenberg (philipp.battenberg@stud.tu-darmstadt.de)
 ******************************************************************************/
package gt.general.logic.trigger;

import gt.general.logic.persistence.PersistenceMap;
import gt.general.logic.persistence.exceptions.PersistenceException;
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
	private boolean green;
	
	/**
	 * @param trigger the lever to be used as trigger
	 * @param against against which block the player placed the trigger
	 */
	public RandomLeverTrigger(final Block trigger, final Block against) {
		super(trigger, against);
		signals = new Vector<Block>();
		green = true;
	}
	
	/** to be used for persistence */
	public RandomLeverTrigger() {}

	@Override
	public PersistenceMap dump() {
		PersistenceMap map = super.dump();
		map.put(KEY_SIGNALS, signals);
		//map.put(KEY_INVERTED, );
		return map;
	}
	
	@Override
	public void setup(final PersistenceMap values, final World world) throws PersistenceException {
		super.setup(values, world);
		
		signals = values.getBlocks(KEY_SIGNALS, world);
		
		if (values.get(KEY_INVERTED)) {
			setGreen(false);
		} else {
			green = true;
		}
		
		//toggleInvert();
		changeSignals();
	}

	/**
	 * sync the visuals with the internal invert
	 */
	private void changeSignals() {
		ObservableCustomBlock signalBlock;
		if (green) {
			signalBlock = CustomBlockType.GREEN_SIGNAL.getCustomBlock();
		} else {
			signalBlock = CustomBlockType.RED_SIGNAL.getCustomBlock();
		}

		for(Block signal : signals) {
			SpoutManager.getMaterialManager().overrideBlock(signal, signalBlock);
		}
	}
	
	/**
	 * set if the trigger shall be secretly inverted
	 * @param green true if not secretly inverted
	 */
	public void setGreen(final boolean green) {
		if (this.green != green) {
			this.green = green;
			toggleInvert();
			changeSignals();
		}
		
		
	}

}
