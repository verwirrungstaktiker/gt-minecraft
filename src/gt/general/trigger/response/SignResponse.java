package gt.general.trigger.response;


import java.util.Map;

import org.bukkit.Effect;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;

public class SignResponse extends BlockResponse {

	private String untriggeredMessage;
	private String triggeredMessage;
	
	private boolean onWall;
	private boolean isTriggered = false;
	
	/**
	 * @param signBlock the bukkit block of the sign
	 */
	public SignResponse(final Block signBlock) {
		super("sign", signBlock);

		/// TODO: this is just for testing
		this.untriggeredMessage = "\n untriggered ";
		this.triggeredMessage = "\n triggered";
	}
	
	public SignResponse() {}

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
		isTriggered  = active;
		
		
		Sign sign = (Sign) getBlock().getState();
		
		if(active) {
			setSignMessage(sign, triggeredMessage);
		} else {
			setSignMessage(sign, untriggeredMessage);
		}
		
		System.out.println(sign.getLine(1));
		sign.update();

		// play a fancy effect
		getBlock().getWorld().playEffect(getBlock().getLocation(), Effect.ENDER_SIGNAL, 10); // we can set the radius here
	}
	

	/**
	 * @param untriggeredMessage the untriggeredMessage to set
	 */
	public void setUntriggeredMessage(final String untriggeredMessage) {
		this.untriggeredMessage = untriggeredMessage;
		
		if(!isTriggered) {
			Sign sign = (Sign) getBlock().getState();
			setSignMessage(sign, untriggeredMessage);
			sign.update();
		}
	}
	
	@Override
	public void setup(final Map<String, Object> values, final World world) {
		super.setup(values, world);
		
		onWall = (Boolean) values.get("onWall");
		
		untriggeredMessage = (String) values.get("untriggered_message");
		triggeredMessage = (String) values.get("triggered_message");
	}
	
	@Override
	public Map<String, Object> dump() {
		Map<String, Object> map = super.dump();
		
		map.put("onWall", onWall);
		
		map.put("untriggeredMessage", untriggeredMessage);
		map.put("triggeredMessage", triggeredMessage);
		
		return map;
	}

}
