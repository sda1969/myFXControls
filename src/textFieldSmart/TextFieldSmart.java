package textFieldSmart;

import java.io.IOException;
import java.util.prefs.Preferences;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableBooleanValue;
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
	private boolean isInvertedConnAction = false;
	@FXML
	private TextField textField;

	private ObservableBooleanValue connectionStatus = null;	
	
	
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
		//visualization of the Connection state
		if (status) {
			showValidationResult(isTextValid(textField.getText()));
		}
		else {
			textField.setStyle("-fx-background-color: lightgrey;");
		}
	}
	
	private void fireCurrentConnState() {
		if (connectionStatus != null) {
			onConnEvent(connectionStatus.get() ^ isInvertedConnAction); // fire current state
		}
	}
	
	/* BooleanProperty is passed to listen for the connection status changing
	 * ObservableBooleanValue interface has no methods to change status, listen only 
	 */
	final public void setConnEvent(ObservableBooleanValue eventValue) {
		connectionStatus = eventValue;
		fireCurrentConnState();
		if (connectionStatus != null) {
			connectionStatus.addListener((ChangeListener<Boolean>) (o, oldVal, newVal) -> {
				//to guarantie that executed from FX thread 
				Platform.runLater(()-> {
					fireCurrentConnState();
				});
			});
		}
	}
	
	final public void setConnEventInverted(boolean status) {
		isInvertedConnAction = status;
	}


	/* Accociate the object with Preferences.
	 * The valid text which is saved in the Preferences
	 * or the default value(for the very first use) is shown*/
	final public void setPreference(String defaultValue, Preferences prefs) {
		setText(prefs.get(objectIdStr, defaultValue));
		this.prefs = prefs;
	}
	
}
