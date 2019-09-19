package textFieldSmart.validators;

public class OctStringValidator implements Validator{
	private final int digits; 
	private int cnt = 0;
	
	public OctStringValidator(int digits) {
		this.digits = digits;
	}
	
	@Override
	public boolean isValid(String s) {
		cnt = 0;
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (Character.digit(c, 8) == -1) {
				return false;
			}
			cnt++;
		}
		if (cnt == this.digits) {
			return true;
		}
		return false;
	}
	
	
}
