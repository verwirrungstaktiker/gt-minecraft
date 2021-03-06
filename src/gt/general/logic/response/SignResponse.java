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
import org.bukkit.block.Sign;

public class SignResponse extends BlockResponse {

	private String untriggeredMessage;
	private String triggeredMessage;
	
	private boolean onWall;
	private boolean isTriggered = false;
	
	private BlockFace orientation;

	private static final String KEY_ORIENTATION = "orientation";
	private static final String KEY_UNTRIGGERED_MESSAGE = "untriggered_message";
	private static final String KEY_TRIGGERED_MESSAGE = "triggered_message";
	private static final String KEY_ON_WALL = "on_wall";
	
	/**
	 * @param signBlock the bukkit block of the sign
	 */
	public SignResponse(final Block signBlock) {
		super("sign", signBlock);

		this.untriggeredMessage = "\n untriggered ";
		this.triggeredMessage = "\n triggered";
		
		onWall = (getBlock().getType()==Material.WALL_SIGN);
	}
	
	/**
	 * don't delete anonymous constructor
	 */
	public SignResponse() {}

	/**
	 * @param sign material.Sign
	 * @param message message on sign (may contain newlines)
	 */
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
	
	/**
	 * clears lines of a sign beginning, doesn't update visual
	 * @param sign a mc sign
	 * @param startingLine line from where the clear starts
	 */
	private void clearSignMessage(final Sign sign, final int startingLine) {
		for (int i=startingLine; i<4; i++) {
			sign.setLine(i, "");
		}
	}

	@Override
	public void triggered(final TriggerEvent e) {
		isTriggered  = e.isActive();
		
		Sign sign = (Sign) getBlock().getState();
		
		if(isTriggered) {
			setSignMessage(sign, triggeredMessage);
		} else {
			setSignMessage(sign, untriggeredMessage);
		}

		// play a fancy effect
		getBlock().getWorld().playEffect(getBlock().getLocation(), Effect.ENDER_SIGNAL, Response.EFFECT_RANGE); // we can set the radius here
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
	
	/**
	 * @param triggeredMessage the triggeredMessage to set
	 */
	public void setTriggeredMessage(final String triggeredMessage) {
		this.triggeredMessage = triggeredMessage;
		
		if(isTriggered) {
			Sign sign = (Sign) getBlock().getState();
			setSignMessage(sign, triggeredMessage);
		}
	}
	
	@Override
	public void setup(final PersistenceMap values, final World world) throws PersistenceException {
		super.setup(values, world);
		
		onWall = values.get(KEY_ON_WALL);
		
		untriggeredMessage = values.get(KEY_UNTRIGGERED_MESSAGE);
		triggeredMessage = values.get(KEY_TRIGGERED_MESSAGE);
		
		orientation = values.get(KEY_ORIENTATION);
		updateOrientation();
	}
	
	@Override
	public PersistenceMap dump() {
		PersistenceMap map = super.dump();
		
		map.put(KEY_ON_WALL, onWall);
		
		map.put(KEY_UNTRIGGERED_MESSAGE, untriggeredMessage);
		map.put(KEY_TRIGGERED_MESSAGE, triggeredMessage);
		
		readOrientation();
		map.put(KEY_ORIENTATION, orientation);
		
		return map;
	}
	
	/**
	 * update the orientation of the sign
	 */
	private void updateOrientation() {
		org.bukkit.material.Sign sign = (org.bukkit.material.Sign) getBlock().getState().getData();
		sign.setFacingDirection(orientation);
		
		getBlock().setData(sign.getData());
	}

	/**
	 * fill the attribute "orientation" with the facing direction of the sign block
	 */
	private void readOrientation() {
		org.bukkit.material.Sign sign = (org.bukkit.material.Sign) getBlock().getState().getData();
		orientation = sign.getFacing();
	}
}
