package IronDome.Utils;

import java.io.IOException;
import java.util.Random;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

public class Utils {

	public static final String LOGGER_NAME = "IronDomeLogger";
	public static Logger myLogger;
	private static FileHandler fileHandler; 
	public static Random rand;
	
	static {
		rand = new Random(System.currentTimeMillis());
		myLogger = Logger.getLogger(LOGGER_NAME);
		myLogger.setUseParentHandlers(false);
		try {
			fileHandler = new FileHandler("AllLogs.txt");
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		fileHandler.setFormatter(new TzoukEitanLogFormatter());
		myLogger.addHandler(fileHandler);
	}
}
