package connectButton;

import eventBus.EventBus;
import eventBus.events.ConnectionEventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;

public abstract class ConnectButtonController extends AnchorPane{
	private Runnable connAction;
	private Runnable disconnAction;
	private String offText = "";
	private String onText = "";
		
	// стандартные кнопки для отображения статуса
	protected final static Image iconOff = new Image(ConnectButtonController.class.getResourceAsStream("button_red.png"));
	protected final static Image iconOn = new Image(
			ConnectButtonController.class.getResourceAsStream("button_green.png"));
	
	@FXML
	protected Button myButton;
	
	@FXML
	final protected void onAction() { 
	
		new Thread(()->{
			if (!connEvHdlr.isConnected()) {
				if (connAction != null) {
					connAction.run();
				}
			}
			else {
				if (disconnAction != null) {
					disconnAction.run();
				}
			}
		}).start();
	}
	
	//----- Event "Connect" on the eventBus is handled by connEvHdlr
	final private  ConnectionEventHandler connEvHdlr = new ConnectionEventHandler((boolean status)->{
			onConnEvent(status);
	});
	//designed to be overrided in subclasses
	protected void onConnEvent(boolean status) {
		myButton.setText(status ? offText : onText);
	}
	
	protected final void fireCurrentState() {
		connEvHdlr.fireCurrentState();
	}
	//----- Common public interface ----------
	final public void setEventBus(EventBus eventBus) {
		connEvHdlr.setEventBus(eventBus);
	}

	final public void setConnEventInverted(boolean status) {
		connEvHdlr.setConnEventInverted(status);
	}

	final public void setConnEventEnabled(boolean status) {
		connEvHdlr.setConnEventEnabled(status);
	}
	
	final public void setConnAction(Runnable connAction) {
		this.connAction = connAction;
	}

	final public void setDisconnAction(Runnable disconnAction) {
		this.disconnAction = disconnAction;
	}
	
	final public void setStatesName(String conn, String disconn) {
		this.onText = conn;
		this.offText = disconn;
		myButton.setText(connEvHdlr.isConnected() ? offText : onText);
	}
	
}
