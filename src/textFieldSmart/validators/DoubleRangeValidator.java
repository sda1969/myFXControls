package textFieldSmart.validators;

public class DoubleRangeValidator implements Validator{
	private double lo;
	private double hi;

	public DoubleRangeValidator(double lo, double hi) {
		this.lo = lo;
		this.hi = hi;
	}
	
	@Override
	public boolean isValid(String s) {
		double val;
		try {
			val = Double.parseDouble(s);
		}
		catch (NumberFormatException e) {
			return false;
		}
		return ((val >= lo) && (val <= hi));
		
	}

}
