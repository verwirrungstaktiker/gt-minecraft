package gt.general.logic.response;

import gt.general.logic.TriggerEvent;

public class TeamRespawnResponse extends RespawnResponse{
	
	@Override
	public void triggered(final TriggerEvent triggerEvent) {
		getRespawnManager().registerTeamRespawnPoint(this);
	}
}
