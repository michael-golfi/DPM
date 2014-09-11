package utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Round {
	/**
	 * Round a value to a given number of decimal places.
	 * From: http://stackoverflow.com/questions/2808535/round-a-double-to-2-decimal-places
	 * @param value
	 * @param places
	 * @return
	 */
	public static String round(double value, int places) {
	    if (places < 0) 
	    	throw new IllegalArgumentException();

	    return new BigDecimal(value)
	    	.setScale(places, RoundingMode.HALF_UP)
	    	.toString();	    
	}
}
