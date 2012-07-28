package gt.editor.gui;

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
