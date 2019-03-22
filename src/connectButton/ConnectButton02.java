package connectButton;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.image.ImageView;

public class ConnectButton02 extends ConnectButtonController {

	public ConnectButton02() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/connectButton/ConnectButton02.fxml"));

		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}

	public void onConnEvent(boolean status) {
		super.onConnEvent(status);
		myButton.setGraphic(new ImageView(status ? iconOn : iconOff));
	}

}
