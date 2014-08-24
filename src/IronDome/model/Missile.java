package IronDome.model;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;

import sun.launcher.resources.launcher;
import IronDome.listeners.IAllWar;
import IronDome.utils.Destination;
import IronDome.utils.TzoukEitanLogFilter;
import IronDome.utils.TzoukEitanLogFormatter;
import IronDome.utils.TzoukEitanLogger;

public class Missile extends Thread {

	private static final String LOGS_FOLDER_PREFIX = "missiles/Missile";
	private static int missileIdGenerator = 10;
	private int flightTime, damage;
	private long launchTime; 
	private String missileId;
	private Destination destination;
	private Launcher lancher;
	private IAllWar allMissiles;
	
//	public Missile(String missileId, int flightTime, int damage, Destination destination, Launcher l) throws SecurityException, IOException {
//		this(missileId, 0, flightTime, damage, destination, l);
//	}


	public Missile(String missileId) {
		this.missileId = missileId;
	}
	
	public Missile(String missileId, int flightTime, int damage, Destination destination, Launcher l) {
		this.missileId = missileId;
		this.flightTime = 1000 * flightTime;
		this.damage = damage;
		this.destination = destination;
		this.lancher = l; 
	}
	
	@Override
	public void run(){
//		TODO fireEvent();
		String logFilePath = LOGS_FOLDER_PREFIX + missileId;
		TzoukEitanLogger.addFileHandler(logFilePath , this);
		this.launchTime = System.currentTimeMillis();
		allMissiles.registerMissile(this); // update TzoukEitan about this missile launch
		TzoukEitanLogger.myLogger.log(Level.INFO, toString() + " launched", new Object[] { lancher, this });
		try {
			sleep(flightTime);
			allMissiles.unregisterMissile(this);
		} catch (InterruptedException e) {
			allMissiles.unregisterMissile(this);
		}
	}

	public void registerAllMissiles(IAllWar allMissiles) {
		this.allMissiles = allMissiles;
	}
	
	public String getMissileId() {
		return missileId;
	}

	public void setMissileId(String missileId) {
		this.missileId = missileId;
	}
	
	public long getLaunchTime() {
		return launchTime;
	}

	public void setLaunchTime(long launchTime) {
		this.launchTime = launchTime;
	}

	public int getFlightTime() {
		return flightTime;
	}

	public void setFlightTime(int flightTime) {
		this.flightTime = flightTime;
	}

	public static String generateMissileId(){
		return "M"+missileIdGenerator++;
	}

	public Launcher getLancher() {
		return lancher;
	}

	@Override
	public boolean equals(Object arg0) {
		Missile other = (Missile)arg0;
		return this.getMissileId().equals(other.missileId);
	}

	@Override
	public String toString() {
		return "Missile [ missileId=" + missileId
				+ ", flyTime=" + flightTime
				+ ", damage=" + damage 
				+ ", launchTime=" + launchTime 
				+ ", destination=" + destination + "]";
	}
}
