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

public class TeamRespawnResponse extends RespawnResponse{
	
	@Override
	public void triggered(final TriggerEvent triggerEvent) {
		getRespawnManager().registerTeamRespawnPoint(this);
	}
}
