package connectButton;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableBooleanValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;

public abstract class ConnectButtonController extends AnchorPane {
	private Runnable connAction;
	private Runnable disconnAction;
	private String offText = "";
	private String onText = "";
	private ObservableBooleanValue connectionStatus = null;
	private boolean isInvertedConnAction = false;

	// стандартные кнопки для отображения статуса
	protected final static Image iconOff = new Image(
			ConnectButtonController.class.getResourceAsStream("button_red.png"));
	protected final static Image iconOn = new Image(
			ConnectButtonController.class.getResourceAsStream("button_green.png"));

	@FXML
	protected Button myButton;

	@FXML
	final protected void onAction() {
		new Thread(() -> {
			if (connectionStatus != null) {
				if (!connectionStatus.get()) {
					if (connAction != null) {
						connAction.run();
					}
				} else {
					if (disconnAction != null) {
						disconnAction.run();
					}
				}
			}
		}).start();
	}

	// designed to be override in subclasses
	protected void onConnEvent(boolean status) {
		myButton.setText(status ? offText : onText);
	}

	private void fireCurrentConnState() {
		if (connectionStatus != null) {
			onConnEvent(connectionStatus.get() ^ isInvertedConnAction); // fire current state
		}
	}
	
	// ----- Common public interface ----------

	/*
	 * BooleanProperty is passed to listen for the connection status changing
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

	final public void setConnAction(Runnable connAction) {
		this.connAction = connAction;
	}

	final public void setDisconnAction(Runnable disconnAction) {
		this.disconnAction = disconnAction;
	}

	final public void setStatesName(String conn, String disconn) {
		this.onText = conn;
		this.offText = disconn;
	}

}
