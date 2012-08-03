package gt.general.logic.response;

import gt.general.logic.TriggerEvent;
import gt.general.logic.persistance.PersistanceMap;
import gt.general.logic.persistance.exceptions.PersistanceException;
import gt.plugin.meta.CustomBlockType;

import org.bukkit.World;
import org.bukkit.block.Block;

public class TeleportResponse extends BlockResponse {

	private static final String KEY_CONTINGENT = "contingent";
	private int maximumContingent;
	private int currrentContingent;
	
	public TeleportResponse(final Block block) {
		super("teleport", block);

		maximumContingent = -1;
		currrentContingent = -1;
	}
	
	/** */
	public TeleportResponse() {
		super();
	}
	
	@Override
	public void setup(final PersistanceMap values, final World world) throws PersistanceException {
		super.setup(values, world);
		
		maximumContingent = values.getInt(KEY_CONTINGENT);
		currrentContingent = maximumContingent;
		CustomBlockType.TELEPORT_EXIT.place(getBlock());
	}
	
	@Override
	public PersistanceMap dump() {
		PersistanceMap map = super.dump();
		map.put(KEY_CONTINGENT, maximumContingent);
		return map;
	}

	@Override
	public void triggered(final TriggerEvent triggerEvent) {
		
		if(triggerEvent.isActive()) {
			if(currrentContingent-- != 0) {
				triggerEvent
					.getPlayer()
					.teleport(getBlock().getLocation().add(0.0, 1.0, 0.0));
			}
		}
	}
}
