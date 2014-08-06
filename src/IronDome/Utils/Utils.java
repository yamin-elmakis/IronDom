package IronDome.Utils;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

public class Utils {

	public static final String LOGGER_NAME = "IronDomeLogger";
	public static Logger myLogger = Logger.getLogger(LOGGER_NAME);
	private static FileHandler fileHandler; 
	
	static {
		try {
			fileHandler = new FileHandler("AllLogs.txt");
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		fileHandler.setFormatter(new IronDomeFormatter());
		myLogger.addHandler(fileHandler);
	}
}
