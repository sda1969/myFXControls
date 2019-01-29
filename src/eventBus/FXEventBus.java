package eventBus;

import eventBus.events.Event;
import javafx.application.Platform;

public class FXEventBus extends EventBus{

	@Override
	public void post(final Event event) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				_post(event);
			}
		});
		
	}

}
