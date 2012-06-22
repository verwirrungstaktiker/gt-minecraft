package gt.general.trigger.response;


import java.util.HashMap;
import java.util.Map;

import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;

public class SignResponse extends Response {

	private Block signBlock;
	
	private String untriggeredMessage;
	private String triggeredMessage;
	
	private boolean onWall;
	
	/**
	 * @param signBlock the bukkit block of the sign
	 */
	public SignResponse(final Block signBlock) {
		super("sign");
		
		this.signBlock = signBlock;
		/// TODO: this is just for testing
		this.untriggeredMessage = "foo";
		this.triggeredMessage = "bar";
	}

	@Override
	public void setup(final Map<String, Object> values, final World world) {
		
		boolean onWall = (Boolean) values.get("onWall");
		
		signBlock = blockFromCoordinates(values, world);

		if(onWall) {
			signBlock.setType(Material.SIGN);
		} else {
			signBlock.setType(Material.SIGN_POST);
		}
	}


	@Override
	public void triggered(final boolean active) {

		Sign sign = (Sign) signBlock.getState().getData();
		
		if(active) {
			sign.setLine(1, triggeredMessage);
		} else {
			sign.setLine(1, untriggeredMessage);
		}
		// play a fancy effect
		signBlock.getWorld().playEffect(signBlock.getLocation(), Effect.ENDER_SIGNAL, 10); // we can set the radius here
	}
	

	@Override
	public void  dispose() {
		signBlock.setType(Material.AIR);
	}

	@Override
	public Map<String, Object> dump() {
		Map<String, Object> map = new HashMap<String,Object>();
		
		map.putAll(coordinatesFromPoint(signBlock));
		map.put("onWall", onWall);
		
		map.put("untriggeredMessage", untriggeredMessage);
		map.put("triggeredMessage", triggeredMessage);
		
		return map;
	}

}
