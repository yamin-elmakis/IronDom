package IronDome.Utils;

import java.io.IOException;
import java.util.Random;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

import IronDome.Model.Launcher;

public class Utils {

	public static final String LOGGER_NAME = "IronDomeLogger";
	public static final String ALL_LOG_FILE_NAME = "AllLogs.txt";
	public static final String LOGS_FOLDER = "logs/";
	public static Logger myLogger;
	private static FileHandler fileHandler, launcherHandler; 
	public static Random rand;
	
	static {
		rand = new Random(System.currentTimeMillis());
		myLogger = Logger.getLogger(LOGGER_NAME);
		myLogger.setUseParentHandlers(false);
		try {
			fileHandler = new FileHandler(LOGS_FOLDER+ALL_LOG_FILE_NAME);
		} catch (SecurityException | IOException e) {		}
		fileHandler.setFormatter(new TzoukEitanLogFormatter());
		myLogger.addHandler(fileHandler);
	}
	
	/** 80% chance to return TRUE */
	public static boolean bool80PercentTrue(){
		if (rand.nextDouble() < 0.8)
			return true;
		return false;
	}
	
	/** 60% chance to return TRUE */
	public static boolean bool60PercentTrue(){
		if (rand.nextDouble() < 0.6)
			return true;
		return false;
	}
}
