package gt.general.trigger.response;

import gt.general.character.ZombieManager;
import gt.general.trigger.persistance.PersistanceMap;

import java.util.Set;

import org.bukkit.World;
import org.bukkit.block.Block;

/**
 * Modifies the speed of all zombies if triggered.
 */
public class ZombieControlResponse extends Response {
	
	private ZombieManager zm;
	private double value;
	
	private static final String KEY_VALUE = "value";
	
	/**
	 * constructor
	 */
	public ZombieControlResponse() {
		super();
		value = 0;
	}
	
	/**
	 * @param zm ZombieManager
	 */
	public void setZombieManager(final ZombieManager zm) {
		this.zm = zm;
	}

	@Override
	public void triggered(final boolean active) {
		if (active) {
			zm.addSpeedAll(value);
		}		
	}

	@Override
	public void dispose() {
		//Well luckyly nothing to dispose here
	}

	@Override
	public PersistanceMap dump() {
		PersistanceMap map = new PersistanceMap();
		
		map.put(KEY_VALUE, value);
		return map;
	}

	@Override
	public Set<Block> getBlocks() {
		return null;
	}

	@Override
	public void setup(final PersistanceMap values, final World world) {
		value = (Double) values.get(KEY_VALUE);
	}

}
