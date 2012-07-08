package gt.general.trigger.response;


import java.util.Map;

import org.bukkit.Effect;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;

// TODO this needs an orientation !
public class SignResponse extends BlockResponse {

	private String untriggeredMessage;
	private String triggeredMessage;
	
	private boolean onWall; // is this inverted? at least in the persistance ...
	private boolean isTriggered = false;
	
	private static final String KEY_UNTRIGGERED_MESSAGE = "untriggered_message";
	private static final String KEY_TRIGGERED_MESSAGE = "triggered_message";
	
	/**
	 * @param signBlock the bukkit block of the sign
	 */
	public SignResponse(final Block signBlock) {
		super("sign", signBlock);

		// TODO this is just for testing
		this.untriggeredMessage = "\n untriggered ";
		this.triggeredMessage = "\n triggered";
	}
	
	public SignResponse() {}

	private void setSignMessage(final Sign sign, final String message) {
		int end=0;
		String line;
		
		
		String rest = message;
		
		for (int i=0; i<4; i++) {			
			if(rest.contains("\n")) {
				end = rest.indexOf("\n");
				line = rest.substring(0, end);
				//set the line on the sign
				sign.setLine(i, line);
				
				rest = rest.substring(end+1);
			} else {
				sign.setLine(i, rest);
				clearSignMessage(sign, ++i);
				break;
			}
		}
		sign.update();		
	}
	
	private void clearSignMessage(final Sign sign, final int startingLine) {
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
		}
	}
	
	@Override
	public void setup(final Map<String, Object> values, final World world) {
		super.setup(values, world);
		
		onWall = (Boolean) values.get("onWall");
		
		untriggeredMessage = (String) values.get(KEY_UNTRIGGERED_MESSAGE);
		triggeredMessage = (String) values.get(KEY_TRIGGERED_MESSAGE);
	}
	
	@Override
	public Map<String, Object> dump() {
		Map<String, Object> map = super.dump();
		
		map.put("onWall", onWall);
		
		map.put(KEY_UNTRIGGERED_MESSAGE, untriggeredMessage);
		map.put(KEY_TRIGGERED_MESSAGE, triggeredMessage);
		
		return map;
	}

}
