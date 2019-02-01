package buttonOnBus;

import java.io.IOException;

import eventBus.EventBus;
import eventBus.events.ConnectionEventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

public class ButtonOnBus extends AnchorPane {
	@FXML
	private Button button;
	//----- Event "Connect" on the eventBus is handled by connEvHdlr
	private final ConnectionEventHandler connEvHdlr = new ConnectionEventHandler((boolean status)->{
		onConnEvent(status);
	});
	private Runnable btnAction;
	
	public ButtonOnBus() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/buttonOnBus/ButtonOnBus.fxml"));

		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
		connEvHdlr.fireCurrentState();
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

	final public void setText(String text) {
		button.setText(text);
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

	final public void setButtonAction(Runnable btnAction) {
		this.btnAction = btnAction;
	}
}
