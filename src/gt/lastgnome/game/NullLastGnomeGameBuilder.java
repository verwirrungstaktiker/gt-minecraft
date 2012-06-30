package gt.lastgnome.game;


public class NullLastGnomeGameBuilder extends AbstractLastGnomeGameBuilder {

	private NullLastGnomeGame game;
	
	
	@Override
	public void instantiateGame() {
		game = new NullLastGnomeGame();
	}

	@Override
	public void updateGui() {}
	
	@Override
	public void finalizeGame() {}

	@Override
	protected AbstractLastGnomeGame getAbstractGame() {
		// TODO Auto-generated method stub
		return game;
	}
	
}
