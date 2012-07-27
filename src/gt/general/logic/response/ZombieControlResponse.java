package gt.general.logic.response;

import gt.general.character.ZombieManager;
import gt.general.logic.persistance.PersistanceMap;
import gt.general.logic.persistance.exceptions.PersistanceException;

import java.util.Set;

import org.bukkit.World;
import org.bukkit.block.Block;

/**
 * Modifies the speed of all zombies if triggered.
 */
public class ZombieControlResponse extends ZombieResponse {
	
	private double value;
	
	private static final String KEY_VALUE = "value";
	
	/**
	 * constructor
	 */
	public ZombieControlResponse() {
		super("zombie_control");
		value = 0;
	}


	@Override
	public void triggered(final boolean active) {
		if (active) {
			getZombieManager().addSpeedAll(value);
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
	public void setup(final PersistanceMap values, final World world) throws PersistanceException {
		value = (Double) values.get(KEY_VALUE);
	}

}
