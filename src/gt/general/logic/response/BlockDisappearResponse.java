/*******************************************************************************
 * Projektpraktikum: Game Technology 2012
 * Minecraft-Modifikation für kollaboratives Spielen
 * 
 * Sebastian Fahnenschreiber (sebastian.fahnenschreiber@stud.tu-darmstadt.de)
 * Roman Neß (roman.ness@stud.tu-darmstadt.de)
 * Philipp Pascal Battenberg (philipp.battenberg@stud.tu-darmstadt.de)
 ******************************************************************************/
package gt.general.logic.response;


import gt.general.logic.TriggerEvent;
import gt.general.logic.persistence.PersistenceMap;
import gt.general.logic.persistence.exceptions.PersistenceException;

import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

public class BlockDisappearResponse extends BlockResponse {

	private boolean inverted = false;		//true if block appears on trigger
	private static final String KEY_INVERTED = "inverted";
	
	/**
	 * do not delete this anonymous constructor
	 */
	public BlockDisappearResponse() {
		super();
	}
	
	/**
	 * @param block bukkit block
	 */
	public BlockDisappearResponse(final Block block) {
		super("appearable_block", block);
	}

	@Override
	public void setup(final PersistenceMap values, final World world) throws PersistenceException {
		super.setup(values, world);
		
		inverted = values.get(KEY_INVERTED);
		
		if(inverted) {
			getBlock().setType(Material.AIR);
		}
	}


	@Override
	public void triggered(final TriggerEvent e) {
		System.out.println("block triggered");
		Block block = getBlock();
		
		if(e.isActive() ^ inverted) {
			// block disappear
			block.setType(Material.AIR);
		} else {
			// block appear
			block.setType(getMaterial());
		}
		// play a fancy effect
		block.getWorld().playEffect(block.getLocation(), Effect.DOOR_TOGGLE, Response.EFFECT_RANGE);
		
	}

	@Override
	public PersistenceMap dump() {
		PersistenceMap map = super.dump();
		
		map.put(KEY_INVERTED, inverted);
		return map;
	}

	/**
	 * @return true if response is inverted
	 */
	public boolean isInverted() {
		return inverted;
	}

	/**
	 * @param inverted true if response is inverted
	 */
	public void setInverted(final boolean inverted) {
		this.inverted = inverted;
	}

}
