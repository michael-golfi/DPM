package interfaces;

public interface Navigation {
	void travelTo(double x, double y);
	
	void turnTo(double theta);
	
	boolean isNavigating();
}
