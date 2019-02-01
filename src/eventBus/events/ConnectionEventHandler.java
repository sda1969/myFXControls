package eventBus.events;

import eventBus.EventBus;

final public class ConnectionEventHandler {
	private boolean connEventInverted = false;
	private boolean connEventEnabled = true;
	private boolean isConn = false;

	public interface OnConnEvent {
		void connEvent(boolean status);
	}

	private final OnConnEvent onConnEvent;

	public ConnectionEventHandler(OnConnEvent ev) {
		this.onConnEvent = ev;
		//fireCurrentState(); - there is no listener at this moment!
		//should ask fireCurrentState() from listener constructor
	}

	//signal to callbacks the  actual state
	public final void fireCurrentState() {
		onConnEvent.connEvent(isConn ^ connEventInverted);
	}
	
	public final void setEventBus(EventBus eventBus) {
		eventBus.registerListener((ConnectionEvent event) -> {
			if (connEventEnabled) {
				isConn = event.connected;
				fireCurrentState();
			}
		}, ConnectionEvent.class);
	}

	public final void setConnEventInverted(boolean status) {
		this.connEventInverted = status;
		fireCurrentState();
	}

	public final void setConnEventEnabled(boolean status) {
		this.connEventEnabled = status;
	}
	
	public final boolean isConnected() {
		return isConn;
	}
	
}
