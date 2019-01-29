package eventBus;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import eventBus.events.Event;

public abstract class EventBus {
	public abstract void post(Event event);

	public interface EventBusListener<T extends Event> {
		void onEvent(T event);
	}

	@SuppressWarnings("rawtypes")
	private Map<EventBusListener, Class> listeners = new HashMap<>();

	public void registerListener(EventBusListener<?> listener,
			Class<?> clazz) {
		synchronized (listeners) {
			listeners.put(listener, clazz);
		}
	}

	public void unregisterListener(EventBusListener<?> listener) {
		synchronized (listeners) {
			listeners.remove(listener);
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void _post(Event event) {
		Map<EventBusListener, Class> listeners;
		synchronized (this.listeners) {
			listeners = new HashMap<>(this.listeners);
		}
		
		event.debug();

		for (Entry<EventBusListener, Class> entry : listeners.entrySet()) {
			// if (entry.getValue().isInstance(event)) {
			if (entry.getValue().equals(event.getClass())) {
				try {
					entry.getKey().onEvent(event);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public int size() {
		return listeners.size();
	}
}
