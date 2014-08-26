package IronDome.model;

import java.io.IOException;
import java.util.HashMap;
import java.util.logging.FileHandler;
import java.util.logging.Level;

import IronDome.listeners.IAllWar;
import IronDome.utils.Destination;
import IronDome.utils.TzoukEitanLogFilter;
import IronDome.utils.TzoukEitanLogFormatter;
import IronDome.utils.TzoukEitanLogger;

public class Hamas {

	private HashMap<String, Launcher> missileLaunchers;
	private IAllWar allWar;
	
	public Hamas() {
		this(new HashMap<String, Launcher>());
	}

	public Hamas(HashMap<String, Launcher> missileLaunchers) {
		this.missileLaunchers = missileLaunchers; 
	}

	public void setLoggerData() throws SecurityException, IOException{
		FileHandler fileHandler = new FileHandler("HamasLog.txt");
		fileHandler.setFormatter(new TzoukEitanLogFormatter());
		fileHandler.setFilter(new TzoukEitanLogFilter(this));
		TzoukEitanLogger.myLogger.addHandler(fileHandler);
		TzoukEitanLogger.myLogger.log(Level.INFO, "Hamas joined the WAR", this);
	}
	
	public void registerAllMissiles(IAllWar allMissiles){
		this.allWar = allMissiles;
	}
	
	public void addMissileLauncher(Launcher launcher){
		missileLaunchers.put(launcher.getLauncherId(), launcher);
		launcher.registerAllMissiles(allWar);
		allWar.registerLauncher(launcher);
		launcher.start();
	}
	
	public void removeMissileLauncher(String launcherId){
		missileLaunchers.remove(launcherId);
	}	
	
	public void loadMissile(String launcherId, String missileID, int flightTime, int damage, Destination destination) {
		missileLaunchers.get(launcherId).loadMissile(missileID, flightTime, damage, destination);
	}
	
}
