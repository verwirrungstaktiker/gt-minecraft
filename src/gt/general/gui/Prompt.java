/*******************************************************************************
 * Projektpraktikum: Game Technology 2012
 * Minecraft-Modifikation für kollaboratives Spielen
 * 
 * Sebastian Fahnenschreiber (sebastian.fahnenschreiber@stud.tu-darmstadt.de)
 * Roman Neß (roman.ness@stud.tu-darmstadt.de)
 * Philipp Pascal Battenberg (philipp.battenberg@stud.tu-darmstadt.de)
 ******************************************************************************/
package gt.general.gui;

import gt.general.gui.Prompt.PromptCallback.Action;
import gt.plugin.meta.Hello;

import org.bukkit.inventory.ItemStack;
import org.getspout.spoutapi.event.screen.ButtonClickEvent;
import org.getspout.spoutapi.gui.ContainerType;
import org.getspout.spoutapi.gui.GenericButton;
import org.getspout.spoutapi.gui.GenericContainer;
import org.getspout.spoutapi.gui.GenericLabel;
import org.getspout.spoutapi.gui.GenericPopup;
import org.getspout.spoutapi.gui.GenericTextField;
import org.getspout.spoutapi.gui.WidgetAnchor;

public class Prompt extends GenericPopup {

	
	public interface PromptCallback {
		
		enum Action {
			SUBMIT, ABORT;
		}
		
		/**
		 * @param action what led to the close
		 * @param text what contained the textField at last
		 */
		void onClose(Action action, String text);
	}
	
	
	private final GenericLabel question;
	private final GenericTextField textField;
	private final GenericButton abortButton;
	private final GenericButton submitButton;
	
	private final PromptCallback callback;
	
	/**
	 * @param msg the question of the prompt
	 * @param callback callback
	 */
	public Prompt(final String msg, final PromptCallback callback) {
		
		this.callback = callback;
		
		question = new GenericLabel(msg);
		question.setMaxWidth(200);
		
		textField = new GenericTextField();
		textField.setMargin(2);
		textField.setMinHeight(20);
		
		abortButton = new GenericButton("Abort") {
			
			public void onButtonClick(final ButtonClickEvent event) {
				close(Action.ABORT);
			}
		};
		abortButton.setMargin(2);
		
		submitButton = new GenericButton("Submit") {
			
			public void onButtonClick(final ButtonClickEvent event) {
				close(Action.SUBMIT);
			}
		};
		
		submitButton.setMargin(2);		
		
		GenericContainer buttons = new GenericContainer(abortButton, submitButton);
		buttons.setLayout(ContainerType.HORIZONTAL);
		buttons.setMinHeight(25);
		
		GenericContainer main = new GenericContainer(question, textField, buttons);
		main.setLayout(ContainerType.VERTICAL);
		main.setAnchor(WidgetAnchor.TOP_CENTER);
		main.setWidth(200);
		main.setX(-100).setY(50);
		
		attachWidget(Hello.getPlugin(), main);
		
	}

	/*
	 * handles close on esc
	 */
	@Override
	public void handleItemOnCursor(final ItemStack itemOnCursor) {
		close(Action.ABORT);
	}
	
	/**
	 * @param action what action led to the close
	 */
	private void close(final Action action) {
		callback.onClose(action, textField.getText());
		close();
	}
}
