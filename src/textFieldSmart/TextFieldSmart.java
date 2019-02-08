package textFieldSmart;

import java.io.IOException;
import java.util.prefs.Preferences;

import eventBus.EventBus;
import eventBus.events.ConnectionEventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import textFieldSmart.validators.Validator;

@SuppressWarnings("serial")
public class TextFieldSmart extends AnchorPane {
	private Validator validator = null;
	/* Every class object has an unique ID defined as the object creation order.
	 * This ID is used as a key when the object is associated with a Preference system storage
	 * Caution: if the code is changed the saved value for the one object may be assigned
	 * to another */
	private static volatile int objectCounter = 0;
	private final String objectIdStr; 
	private Preferences prefs = null;
	
	@FXML
	protected TextField textField;
	//----- Event "Connect" on the eventBus is handled by connEvHdlr
	private final ConnectionEventHandler connEvHdlr = new ConnectionEventHandler((boolean status)->{
		onConnEvent(status);
	});	
	
	public class TFSException extends Exception {
		TFSException(String msg) {
			super(msg);
		}
	}

	public TextFieldSmart() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/textFieldSmart/TextFieldSmart.fxml"));

		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
		
		objectIdStr = "TextFieldSmart" + Integer.toString(objectCounter++);
		//https://stackoverflow.com/questions/30160899/value-change-listener-for-javafxs-textfield
		textField.textProperty().addListener((observable, oldValue, newValue) -> {
			//System.out.println("textfield changed from " + oldValue + " to " + newValue);
			boolean isValid = isTextValid(newValue);
			showValidationResult(isValid);
			if ((prefs != null) && isValid){
					prefs.put(objectIdStr, textField.getText());	
			}
		});	
		connEvHdlr.fireCurrentState();
	}

	final public void setValidator(Validator vldr) {
		this.validator = vldr;
	}

	final private boolean isTextValid(String text) {
		// if validator is not set, then lets report validation fail
		return (validator != null) ? validator.isValid(text) : false;
	}

	// Subclasses may override this method if required
	protected void showValidationResult(boolean status) {
		textField.setStyle(status ? "-fx-background-color: white;" : "-fx-background-color: red;");
	}

	final public String getValidText() throws TFSException {
		if (!isTextValid(textField.getText())) {
			throw new TFSException("Field Validator fails");
		}
		return textField.getText();
	}

	final public void setText(String text) {
		textField.setText(text);
	}

	//designed to be overrided in subclasses
	protected void onConnEvent(boolean status) {
		textField.setEditable(status);
	}

	final public void setEventBus(EventBus eventBus) {
		connEvHdlr.setEventBus(eventBus);
	}

	final public void setConnEventInverted(boolean status) {
		connEvHdlr.setConnEventInverted(status);
	}

	final public void setConnEventEnabled(boolean status) {
		connEvHdlr.setConnEventEnabled(status);
	}
	/* Accociate the object with Preferences.
	 * The valid text which is saved in the Preferences
	 * or the default value(for the very first use) is shown*/
	final public void setPreference(String defaultValue, Preferences prefs) {
		setText(prefs.get(objectIdStr, defaultValue));
		this.prefs = prefs;
	}
	
}
