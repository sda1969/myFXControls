package textFieldSmart.validators;

public class HexStringValidator implements Validator{
	
	private final int digits; 
	private int cnt = 0;
	
	public HexStringValidator(int digits) {
		this.digits = digits;
	}
	
	@Override
	public boolean isValid(String s) {
		String sl = s.toLowerCase();
		cnt = 0;
		for (int i = 0; i < sl.length(); i++) {
			char c = sl.charAt(i);
			if (c == '_') continue;
			if (Character.digit(c, 16) == -1) {
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
