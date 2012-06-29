package gt.lastgnome;

import gt.general.Game;
import gt.general.GameBuilder;
import gt.general.character.Hero;
import gt.general.character.Team;
import gt.general.world.WorldInstance;

public class LastGnomeGameBuilder implements GameBuilder{

	private LastGnomeGame game;
	private final Team team;
	
	public LastGnomeGameBuilder(final Team team) {
		this.team = team;
	}
	
	@Override
	public void instantiateGame() {
		game = new LastGnomeGame(team);
	}

	@Override
	public void loadMetadata(final WorldInstance worldInstance) {
		
		GnomeSocketStart start = new GnomeSocketStart(game);
		start.setup(worldInstance.loadMeta("start_socket.yml"),
					worldInstance.getWorld());
		
		GnomeSocketEnd end = new GnomeSocketEnd(game);
		end.setup(worldInstance.loadMeta("end_socket.yml"),
				  worldInstance.getWorld());		
	}

	@Override
	public void updateGui() {
		for(Hero hero : team.getPlayers()) {
			game.upgradeGui(hero);
		}		
	}

	@Override
	public void finalizeGame() {
		// TODO Auto-generated method stub
	}

	@Override
	public Game getGame() {
		// TODO Auto-generated method stub
		return game;
	}
	

}
