package gt.general.logic.response;

import gt.general.RespawnManager;
import gt.general.RespawnManager.RespawnPoint;
import gt.general.logic.TriggerEvent;
import gt.plugin.meta.CustomBlockType;

import org.bukkit.Location;
import org.bukkit.block.Block;

public class RespawnResponse extends CustomBlockResponse implements RespawnPoint {

	private static final CustomBlockType BLOCK = CustomBlockType.RESPAWN_BLOCK;
	private RespawnManager respawnManager;	
	
	/**
	 * construct new RespawnResponse
	 * @param name name prefix
	 * @param block bukkit block that holds the response
	 * @param type the type of customblock that is displayed
	 */
	public RespawnResponse(final String name, final Block block, final CustomBlockType type) {
		super("respawn", block, BLOCK);
	}
	
	/**
	 * don't delete this anonymous constructor
	 */
	public RespawnResponse() {
		super(BLOCK);
	}

	@Override
	public void registerRespawnManager(final RespawnManager respawnManager) {
		this.respawnManager = respawnManager;
	}
	
	@Override
	public void triggered(final TriggerEvent triggerEvent) {
		respawnManager.registerRespawnPoint(triggerEvent.getPlayer(), this);
	}

	@Override
	public Location getRespawnLocation() {
		return getBlock().getLocation().add(0.5, 1.0, 0.5);
	}
	
	
}
