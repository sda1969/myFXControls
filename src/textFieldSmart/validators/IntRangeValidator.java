package textFieldSmart.validators;

public class IntRangeValidator implements Validator{
	private int lo;
	private int hi;

	public IntRangeValidator(int lo, int hi) {
		this.lo = lo;
		this.hi = hi;
	}
	
	@Override
	public boolean isValid(String s) {
		int val;
		try {
			val = Integer.parseInt(s);
		}
		catch (NumberFormatException e) {
			return false;
		}
		return ((val >= lo) && (val <= hi));
		
	}

}
