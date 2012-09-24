/*******************************************************************************
 * Projektpraktikum: Game Technology 2012
 * Minecraft-Modifikation fuer kollaboratives Spielen
 * 
 * Sebastian Fahnenschreiber (sebastian.fahnenschreiber@stud.tu-darmstadt.de)
 * Roman Ness (roman.ness@stud.tu-darmstadt.de)
 * Philipp Pascal Battenberg (philipp.battenberg@stud.tu-darmstadt.de)
 ******************************************************************************/
package gt.general.logic.response;

import gt.general.RespawnManager;
import gt.general.RespawnManager.RespawnPoint;
import gt.general.logic.TriggerEvent;
import gt.general.logic.persistence.PersistenceMap;
import gt.general.logic.persistence.exceptions.PersistenceException;
import gt.plugin.meta.CustomBlockType;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class TeleportResponse extends CustomBlockResponse implements RespawnPoint {

	private static final CustomBlockType BLOCK = CustomBlockType.TELEPORT_EXIT;
	
	
	private RespawnManager respawnManager;
	
	/**
	 * @param block bukkit block
	 */
	public TeleportResponse(final Block block) {
		super("teleport", block, BLOCK);
	}
	
	/** */
	public TeleportResponse() {
		super(BLOCK);
	}
	
	@Override
	public void setup(final PersistenceMap values, final World world) throws PersistenceException {
		super.setup(values, world);
		
		//For some reason, generic version in parent Class does not work 
		CustomBlockType.TELEPORT_EXIT.place(getBlock());
	}
	
	@Override
	public PersistenceMap dump() {
		PersistenceMap map = super.dump();
		return map;
	}

	@Override
	public void triggered(final TriggerEvent triggerEvent) {
		
		if(triggerEvent.isActive()) {
			
				Player player = triggerEvent.getPlayer();
				
				// there is no respawn manager in the editor
				if(respawnManager != null) {
					respawnManager.registerRespawnPoint(player, this);
				}
				
				teleportPlayer(player);
			
		}
	}

	@Override
	public void registerRespawnManager(final RespawnManager respawnManager) {
		this.respawnManager = respawnManager;
	}

	/**
	 * teleport the player to the teleport block
	 * @param player bukkit player that is teleported
	 */
	private void teleportPlayer(final Player player) {
		player.teleport(getBlock().getLocation().add(0.5, 1.0, 0.5));
	}

	@Override
	public Location getRespawnLocation() {
		return getBlock().getLocation().add(0.5, 1.0, 0.5);
	}
}
