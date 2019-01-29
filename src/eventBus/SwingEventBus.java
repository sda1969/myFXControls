package eventBus;

import javax.swing.SwingUtilities;

import eventBus.events.Event;



public class SwingEventBus extends EventBus {

	@Override
	public void post(final Event event) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				_post(event);
			}
		});
	}

}
