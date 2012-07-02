package gt.general.trigger.response;

import gt.general.character.ZombieManager;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.bukkit.World;
import org.bukkit.block.Block;

public class ZombieControlResponse extends Response {
	
	private ZombieManager zm;
	private double value;
	
	public ZombieControlResponse() {
		super();
		value = 0;
	}
	
	
	
	public void setZm(ZombieManager zm) {
		this.zm = zm;
	}


	@Override
	public void highlight() {
		// TODO How?!
		
	}

	@Override
	public void triggered(boolean active) {
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
	public void setup(Map<String, Object> values, World world) {
		value = (Double) values.get("value");		
	}

}
