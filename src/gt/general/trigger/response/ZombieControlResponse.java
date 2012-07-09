package gt.general.trigger.response;

import gt.general.character.ZombieManager;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.bukkit.World;
import org.bukkit.block.Block;

/**
 * Modifies the speed of all zombies if triggered.
 */
public class ZombieControlResponse extends Response {
	
	private ZombieManager zm;
	private double value;
	
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
	public void highlight() {
		// TODO How?!
		
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
	public Map<String, Object> dump() {
		Map<String, Object> map = new HashMap<String,Object>();
		
		map.put("value", value);
		return map;
	}

	@Override
	public Set<Block> getBlocks() {
		return null;
	}

	@Override
	public void setup(final Map<String, Object> values, final World world) {
		value = (Double) values.get("value");
	}

}
