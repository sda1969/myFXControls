package connectButton;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.ImageView;

public class ConnectButton01 extends ConnectButtonController {
	@FXML
	private ImageView myImageView;

	public ConnectButton01() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/connectButton/ConnectButton01.fxml"));

		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
		fireCurrentState();
	}

	@Override
	public void onConnEvent(boolean status) {
		super.onConnEvent(status);
		myImageView.setImage(status ? iconOn : iconOff);
	}
}
