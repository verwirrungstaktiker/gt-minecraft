/*******************************************************************************
 * Projektpraktikum: Game Technology 2012
 * Minecraft-Modifikation fuer kollaboratives Spielen
 * 
 * Sebastian Fahnenschreiber (sebastian.fahnenschreiber@stud.tu-darmstadt.de)
 * Roman Ness (roman.ness@stud.tu-darmstadt.de)
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
import org.bukkit.block.BlockFace;
import org.bukkit.material.RedstoneTorch;

public class RedstoneTorchResponse extends BlockResponse {

	private boolean inverted = false;		//true if response is inverted
	private BlockFace orientation;
	
	private static final String KEY_INVERTED = "inverted";
	private static final String KEY_ORIENTATION = "orientation";
	
	/**
	 * @param torchBlock material.RedstoneTorch
	 * @param against	 the block that holds the torch
	 */
	public RedstoneTorchResponse(final Block torchBlock, final Block against) {
		super("redstone_torch", torchBlock);
		
		orientation = against.getFace(torchBlock);
	}
	
	/**
	 * don't delete this anonymous constructor
	 */
	public RedstoneTorchResponse() {}

	@Override
	public void setup(final PersistenceMap values, final World world) throws PersistenceException {
		super.setup(values, world);
		
		inverted = values.get(KEY_INVERTED);
		orientation = values.get(KEY_ORIENTATION);
				
		if( !inverted ) {
			// Feature: Maybe we can find another Material to represent a RedstoneTorch that's not glowing
			getBlock().setType(Material.AIR);
		} else {
			RedstoneTorch torch = (RedstoneTorch) getBlock().getState().getData();
			torch.setFacingDirection(orientation);
			getBlock().setData(torch.getData());
		}
	}


	@Override
	public void triggered(final TriggerEvent e) {
		System.out.println("block triggered");
		
		if(e.isActive() ^ inverted) {
			// torch on
			getBlock().setType(getMaterial());
		} else {
			// torch off
			getBlock().setType(Material.AIR);
		}
		// play a fancy effect
		getBlock().getWorld().playEffect(getBlock().getLocation(), Effect.ENDER_SIGNAL, Response.EFFECT_RANGE);
	}

	@Override
	public PersistenceMap dump() {
		PersistenceMap map = super.dump();
		
		map.put(KEY_INVERTED, inverted);
		map.put(KEY_ORIENTATION, orientation);
		
		return map;
	}

	/**
	 * @return true if the logic of the torch is inverted
	 */
	public boolean isInverted() {
		return inverted;
	}

	/**
	 * @param inverted true if the logic of the torch is inverted (disappears on power)
	 */
	public void setInvert(final boolean inverted) {
		this.inverted = inverted;
	}

}
