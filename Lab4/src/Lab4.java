import navigation.Navigator;
import constants.Constants;
import constants.Sensor;
import controller.MotorController;
import odometry.Odometer;
import lcd.LCDInfo;
import lejos.nxt.*;
import lejos.util.Timer;
import localizers.LightLocalizer;
import localizers.LocalizationType;
import localizers.UltrasonicLocalizer;

public class Lab4 {
	static MotorController motors = new MotorController();
	static UltrasonicSensor ultrasonic = new UltrasonicSensor(Sensor.ULTRASONIC);
	static ColorSensor colorSensor = new ColorSensor(Sensor.COLOR);

	public static void main(String[] args) {
		Odometer odometer = new Odometer(motors);
		odometer.start();
		
		LCD.drawString("< US Local | Light Local>", 0, 0);
		int button = Button.waitForAnyPress();
		
		Navigator navigator = new Navigator(motors, odometer);
		startOdometerDisplay(odometer);
		
		if (button == Button.ID_LEFT) {
			ultrasonicLocalization(odometer, navigator);
			lightLocalization(odometer, navigator);
		} else if (button == Button.ID_RIGHT) {
			lightLocalization(odometer, navigator);
		}

		Button.waitForAnyPress();
	}

	private static void ultrasonicLocalization(Odometer odometer,
			Navigator navigator) {
		UltrasonicLocalizer ultrasonicLocalizer = new UltrasonicLocalizer(
				odometer, ultrasonic, navigator);
		ultrasonicLocalizer.doLocalization(LocalizationType.RISING_EDGE);
	}

	private static void lightLocalization(Odometer odometer, Navigator navigator) {
		new LightLocalizer(odometer, colorSensor, navigator).doLocalization();
	}

	private static void startOdometerDisplay(Odometer odometer) {
		LCDInfo lcdInfo = new LCDInfo(odometer);
		new Timer(Constants.REFRESH_PERIOD, lcdInfo).start();
	}
}
