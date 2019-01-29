package eventBus.events;

public class ProgressEvent  implements Event  {
	public final int val;
	
	public ProgressEvent (int val) {
		this.val = val;
	}

	@Override
	public void debug() {
		System.out.println("ProgressEvent : " + val);
	}
}
