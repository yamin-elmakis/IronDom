package IronDome.utils;

import java.io.IOException;
import java.util.Random;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

import IronDome.model.Launcher;

public class TzoukEitanLogger {

	public static final String LOGGER_NAME = "IronDomeLogger";
	public static final String ALL_LOG_FILE_NAME = "AllLogs.txt";
	public static final String LOGS_FOLDER = "logs/";
	private static final String LOGS_FILE_SUFFIX = "Log.txt";
	public static Logger myLogger;
	private static FileHandler fileHandler; 
	
	static {
		myLogger = Logger.getLogger(LOGGER_NAME);
		myLogger.setUseParentHandlers(false);
		try {
			fileHandler = new FileHandler(LOGS_FOLDER+ALL_LOG_FILE_NAME);
		} catch (SecurityException | IOException e) {		}
		fileHandler.setFormatter(new TzoukEitanLogFormatter());
		myLogger.addHandler(fileHandler);
	}
	
	public static void addFileHandler(String path, Object theObject){
		try {
			FileHandler fileHandler = new FileHandler(LOGS_FOLDER+path+LOGS_FILE_SUFFIX);
			fileHandler.setFormatter(new TzoukEitanLogFormatter());
			fileHandler.setFilter(new TzoukEitanLogFilter(theObject));
			TzoukEitanLogger.myLogger.addHandler(fileHandler);
		} catch (SecurityException | IOException e) {
			e.printStackTrace();
		}
	}
}
