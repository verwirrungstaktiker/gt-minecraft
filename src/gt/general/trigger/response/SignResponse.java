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
		this.untriggeredMessage = "foo\n12\n34\n67";
		this.triggeredMessage = "\nbar\n 45";
	}

	@Override
	public void setup(final Map<String, Object> values, final World world) {
		
		onWall = (Boolean) values.get("onWall");
		
		untriggeredMessage = (String) values.get("untriggered_message");
		triggeredMessage = (String) values.get("triggered_message");
		
		signBlock = blockFromCoordinates(values, world);

		if(onWall) {
			signBlock.setType(Material.SIGN);
		} else {
			signBlock.setType(Material.SIGN_POST);
		}
	}

	private void setSignMessage(Sign sign, String message) {
		int end=0;
		String line;
		
		for (int i=0; i<4; i++) {
			if(message.contains("\n")) {
				end = message.indexOf("\n");
				line = message.substring(0, end);
				//set the line on the sign
				sign.setLine(i, line);
				
				message = message.substring(end+1);
			} else {
				sign.setLine(i, message);
				clearSignMessage(sign, ++i);
				break;
			}
		}
		sign.update();		
	}
	
	private void clearSignMessage(Sign sign, int startingLine) {
		for (int i=startingLine; i<4; i++) {
			sign.setLine(i, "");
		}
	}

	@Override
	public void triggered(final boolean active) {

		Sign sign = (Sign) signBlock.getState();
		
		if(active) {
			setSignMessage(sign, triggeredMessage);
		} else {
			setSignMessage(sign, untriggeredMessage);
		}
		
		System.out.println(sign.getLine(1));
		sign.update();

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
