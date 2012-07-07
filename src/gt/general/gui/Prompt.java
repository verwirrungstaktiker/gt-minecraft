package gt.general.gui;

import gt.general.character.Hero;
import gt.plugin.meta.Hello;

import org.bukkit.inventory.ItemStack;
import org.getspout.spoutapi.event.screen.ButtonClickEvent;
import org.getspout.spoutapi.gui.Color;
import org.getspout.spoutapi.gui.ContainerType;
import org.getspout.spoutapi.gui.GenericButton;
import org.getspout.spoutapi.gui.GenericContainer;
import org.getspout.spoutapi.gui.GenericLabel;
import org.getspout.spoutapi.gui.GenericPopup;
import org.getspout.spoutapi.gui.GenericTextField;
import org.getspout.spoutapi.gui.PopupScreen;
import org.getspout.spoutapi.gui.WidgetAnchor;

public class Prompt {

	
	public interface PromptResponse {
		
	}
	
	private GenericPopup promptPopup = new GenericPopup() {
		@Override
		public void handleItemOnCursor(ItemStack itemOnCursor) {
			super.handleItemOnCursor(itemOnCursor);
			
			System.out.println("CLOSE");
		}
	};
	private GenericLabel question = new GenericLabel();
	
	Hero hero;
	
	public Prompt(final Hero holder) {
		hero = holder;
		
		GenericTextField textField = new GenericTextField();
		
		GenericButton abortButton = new GenericButton("Abort") {
			
			public void onButtonClick(ButtonClickEvent event) {
				System.out.println("CLICK abort");
			}
		};
		abortButton.setMargin(2);
		
		GenericButton submitButton = new GenericButton("Submit") {
			
			public void onButtonClick(ButtonClickEvent event) {
				System.out.println("CLICK submit");
				
				System.out.println(promptPopup.getWidth());
				System.out.println(promptPopup.getHeight());
			}
		};
		submitButton.setMargin(2);
		
		
		GenericContainer buttons = new GenericContainer(abortButton, submitButton);
		buttons.setLayout(ContainerType.HORIZONTAL);
		buttons.setWidth(200).setHeight(20);
		
		promptPopup.attachWidget(Hello.getPlugin(), buttons);
		
		GenericContainer main = new GenericContainer(question, textField, buttons);
		main.setLayout(ContainerType.VERTICAL);
		main.setAnchor(WidgetAnchor.TOP_CENTER);
		
		promptPopup.attachWidget(Hello.getPlugin(), main);
		
		
		System.out.println("!");
		
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
