package buttonOnEvent;

import java.io.IOException;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableBooleanValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

public class ButtonOnEvent extends AnchorPane {
	@FXML
	private Button button;
	private ObservableBooleanValue connectionStatus = null;	
	private Runnable btnAction;
	private boolean isInvertedConnAction = false;
	
	public ButtonOnEvent() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/buttonOnEvent/ButtonOnEvent.fxml"));

		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}
	
	@FXML
	final protected void onAction() { 
		new Thread(()->{
			if (btnAction != null) {
				btnAction.run();
			}
		}).start();
	}
	
	//designed to be overrided in subclasses
	protected void onConnEvent(boolean status) {
		button.disableProperty().set(!status);
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
	
	final public void setText(String text) {
		button.setText(text);
	}
	
	final public void setConnEventInverted(boolean status) {
		isInvertedConnAction = status;
	}

	final public void setButtonAction(Runnable btnAction) {
		this.btnAction = btnAction;
	}
}
