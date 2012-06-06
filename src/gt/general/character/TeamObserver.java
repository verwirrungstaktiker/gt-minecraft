package gt.general.character;

public interface TeamObserver {

	/**
	 * To be called if the Team is changed
	 * 
	 * @param team The Team to be observed.
	 * @param notification The type of change which happened.
	 */
	void update(Team team, Team.Notification notification);
}
