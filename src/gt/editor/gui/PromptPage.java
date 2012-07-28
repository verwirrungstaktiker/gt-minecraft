package gt.editor.gui;

import gt.editor.EditorFacade;
import gt.editor.gui.PromptCallback.Action;

import org.getspout.spoutapi.event.screen.ButtonClickEvent;
import org.getspout.spoutapi.gui.ContainerType;
import org.getspout.spoutapi.gui.GenericButton;
import org.getspout.spoutapi.gui.GenericContainer;
import org.getspout.spoutapi.gui.GenericLabel;
import org.getspout.spoutapi.gui.GenericTextField;
import org.getspout.spoutapi.gui.WidgetAnchor;
import org.getspout.spoutapi.player.SpoutPlayer;

public class PromptPage extends OverlayPage{	
	
	private GenericLabel question;
	private GenericTextField textField;
	private GenericButton abortButton;
	private GenericButton submitButton;
	
	private final PromptCallback callback;
	private final String message;
	
	/**
	 * @param message the question of the prompt
	 * @param callback callback
	 */
	PromptPage(final EditorOverlay overlay, 
				final SpoutPlayer player,
				final EditorFacade facade, 
				final String message, 
				final PromptCallback callback) {
		super(overlay, player, facade);
		
		this.callback = callback;
		this.message = message;
	}

	@Override
	protected void setup() {
		System.out.println("SETUP");
		
		
		question = new GenericLabel(message);
		question.setMaxWidth(200);
		
		textField = new GenericTextField();
		textField.setMargin(2);
		textField.setMaximumCharacters(50);
		textField.setMinHeight(20);
		
		abortButton = new GenericButton("Abort") {
			
			public void onButtonClick(final ButtonClickEvent event) {
				callback.onClose(Action.ABORT, textField.getText());
			}
		};
		abortButton.setMargin(2);
		
		submitButton = new GenericButton("Submit") {
			
			public void onButtonClick(final ButtonClickEvent event) {
				callback.onClose(Action.SUBMIT, textField.getText());
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
		
		attachWidget(main);
		
	}

	@Override
	protected void dispose() {}

	@Override
	public boolean closeWithHotkey() {
		return ! textField.isFocus();
	}
}
