package math;

/**
 * @author Michael Golfi #260552298
 * @author Paul Albert-Lebrun #260507074
 */
public class Round {
	/**
	 * Round a value to a given number of decimal places. From:
	 * http://stackoverflow
	 * .com/questions/2808535/round-a-double-to-2-decimal-places
	 * 
	 * @param value
	 * @param places
	 * @return
	 */
	public static String round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();
		return String.valueOf(Math.round(value));
		/*
		 * return new BigDecimal(value) .setScale(places, RoundingMode.HALF_UP)
		 * .toString();
		 */
	}
}
