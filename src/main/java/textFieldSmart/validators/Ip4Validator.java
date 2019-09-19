package textFieldSmart.validators;

public class Ip4Validator implements Validator{

	@Override
	public boolean isValid(String s) {

			String octets[] = s.split("\\."); //split(".");
			if (octets.length != 4) return false;
			for(String x : octets) {
				int d = 0;
				try {
					d = Integer.parseInt(x);
				}
				catch (NumberFormatException e) {
					return false;
				}
				if ((d < 0) ||(d > 255))return false;
			}
			return true;
	}

}
