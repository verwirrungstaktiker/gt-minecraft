package gt.general.gui;

import gt.general.character.Hero;
import gt.plugin.meta.Hello;

import org.getspout.spoutapi.gui.GenericLabel;
import org.getspout.spoutapi.gui.GenericPopup;
import org.getspout.spoutapi.gui.PopupScreen;

public class Prompt {

	
	public interface PromptResponse {
		
	}
	
	private GenericPopup promptPopup = new GenericPopup();
	private GenericLabel question = new GenericLabel();
	
	Hero hero;
	
	public Prompt(final Hero holder) {
		hero = holder;
	}
	
	public void show(final String Question) {
		
		promptPopup.attachWidget(Hello.getPlugin(), question);
		
		hero.getSpoutPlayer().getMainScreen().attachPopupScreen(promptPopup);
		//hero.getGui().attachWidgets(somePopup);
		
	//	hero.getGui().attachPopupScreen(promptPopup);
	//
	}
	
	public void hide() {
	//	hero.getGui().closePopup(promptPopup);
	}


	public void setMessage(String message) {
		question.setText(message);
	}

	public PopupScreen getPopup() {
		return promptPopup;
	}

}
