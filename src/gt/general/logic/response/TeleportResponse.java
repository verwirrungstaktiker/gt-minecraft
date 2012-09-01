package gt.general.logic.response;

import gt.general.RespawnManager;
import gt.general.RespawnManager.RespawnPoint;
import gt.general.logic.TriggerEvent;
import gt.general.logic.persistence.PersistenceMap;
import gt.general.logic.persistence.exceptions.PersistenceException;
import gt.plugin.meta.CustomBlockType;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class TeleportResponse extends CustomBlockResponse implements RespawnPoint {

	private static final CustomBlockType BLOCK = CustomBlockType.TELEPORT_EXIT;
	
	
	private static final String KEY_CONTINGENT = "contingent";
	private int maximumContingent;
	private int currentContingent;

	private RespawnManager respawnManager;
	
	/**
	 * @param block bukkit block
	 */
	public TeleportResponse(final Block block) {
		super("teleport", block, BLOCK);

		maximumContingent = -1;
		currentContingent = -1;
	}
	
	/** */
	public TeleportResponse() {
		super(BLOCK);
	}
	
	@Override
	public void setup(final PersistenceMap values, final World world) throws PersistenceException {
		super.setup(values, world);
		
		maximumContingent = values.getInt(KEY_CONTINGENT);
		currentContingent = maximumContingent;
	}
	
	@Override
	public PersistenceMap dump() {
		PersistenceMap map = super.dump();
		map.put(KEY_CONTINGENT, maximumContingent);
		return map;
	}

	@Override
	public void triggered(final TriggerEvent triggerEvent) {
		
		if(triggerEvent.isActive()) {
			if(currentContingent != 0) {
				--currentContingent;
				Player player = triggerEvent.getPlayer();
				
				// there is no respawn manager in the editor - TODO ?
				if(respawnManager != null) {
					respawnManager.registerRespawnPoint(player, this);
				}
				
				teleportPlayer(player);
			}
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
		player.getWorld().playEffect(player.getLocation(), Effect.MOBSPAWNER_FLAMES, 25);
		player.teleport(getBlock().getLocation().add(0.5, 1.0, 0.5));
	}

	@Override
	public Location getRespawnLocation() {
		return getBlock().getLocation().add(0.5, 1.0, 0.5);
	}
}
