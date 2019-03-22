package buttonOnEvent;

import java.io.IOException;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableBooleanValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;

public class ToggleButtonOnEvent extends AnchorPane {
	@FXML
	private ToggleButton button;
	private ObservableBooleanValue connectionStatus = null;
	private Runnable btnPressAction;
	private Runnable btnReleaseAction;
	private boolean isInvertedConnAction = false;

	public ToggleButtonOnEvent() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/buttonOnEvent/ToggleButtonOnEvent.fxml"));

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
		new Thread(() -> {
			if (isBtnSelected()) {
				if (btnPressAction != null) {
					btnPressAction.run();
				}
			} else {
				if (btnReleaseAction != null) {
					btnReleaseAction.run();
				}
			}
		}).start();
	}

	// designed to be overrided in subclasses
	protected void onConnEvent(boolean status) {
		button.disableProperty().set(!status);
	}

	private void fireCurrentConnState() {
		if (connectionStatus != null) {
			onConnEvent(connectionStatus.get() ^ isInvertedConnAction); // fire current state
		}
	}

	/*
	 * BooleanProperty is passed to listen for the connection status changing
	 * ObservableBooleanValue interface has no methods to change status, listen only
	 */
	public final void setConnEvent(ObservableBooleanValue eventValue) {
		connectionStatus = eventValue;
		fireCurrentConnState();
		if (connectionStatus != null) {
			connectionStatus.addListener((ChangeListener<Boolean>) (o, oldVal, newVal) -> {
				// to guarantie that executed from FX thread
				Platform.runLater(() -> {
					fireCurrentConnState();
				});
			});
		}
	}

	public final void setText(String text) {
		button.setText(text);
	}

	public final void setConnEventInverted(boolean status) {
		isInvertedConnAction = status;
	}

	public final void setButtonPressAction(Runnable btnAction) {
		this.btnPressAction = btnAction;
	}

	public final void setButtonReleaseAction(Runnable btnAction) {
		this.btnReleaseAction = btnAction;
	}

	public final boolean isBtnSelected() {
		return button.isSelected();
	}
}
