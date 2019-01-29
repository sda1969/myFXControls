package connectButton;

import eventBus.EventBus;
import eventBus.events.ConnectionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;

public abstract class ConnectButtonController extends AnchorPane {
	private boolean isConn = false;
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
			if (!isConn) {
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

	protected void onConnEvent(boolean status) {
		isConn = status;
		myButton.setText(status ? offText : onText);
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
		myButton.setText(isConn ? offText : onText);
	}
	
	final public void setEventBus(EventBus eventBus) {
		eventBus.registerListener((ConnectionEvent event) -> {
			onConnEvent(event.connected);
		}, ConnectionEvent.class);
	}
}
