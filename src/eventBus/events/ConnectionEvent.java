package eventBus.events;

public class ConnectionEvent implements Event{
	public final boolean connected;
	public final String host;
	
	public ConnectionEvent(boolean connected, String host) {
		this.connected = connected;
		this.host = host;
	}

	@Override
	public void debug() {
		System.out.println("ConnectionEvent : " + connected + " host : " + host);
	}
}
