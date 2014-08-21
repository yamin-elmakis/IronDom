package IronDome.Model;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;

import sun.launcher.resources.launcher;
import IronDome.Utils.Destination;
import IronDome.Utils.TzoukEitanLogFilter;
import IronDome.Utils.TzoukEitanLogFormatter;
import IronDome.Utils.Utils;

public class Missile extends Thread {

	private static final String LOGS_FOLDER_PREFIX = "missiles/Missile";
	private static final String LOGS_FOLDER_SUFFIX = "Log.txt";
	private static int missileIdGenerator = 10;
	private int flightTime, damage;
	private long launchTime; 
	private String missileId;
	private Destination destination;
	private Launcher lancher;
	
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
	

	public void setLoggerData(Launcher l) {
		FileHandler fileHandler;
		try {
			fileHandler = new FileHandler(Utils.LOGS_FOLDER+LOGS_FOLDER_PREFIX+missileId+LOGS_FOLDER_SUFFIX);
			fileHandler.setFormatter(new TzoukEitanLogFormatter());
			fileHandler.setFilter(new TzoukEitanLogFilter(this));
			Utils.myLogger.addHandler(fileHandler);
		} catch (SecurityException | IOException e) {		}
	}
	
	@Override
	public void run(){
//		TODO fireEvent();
		setLoggerData(lancher);
		this.launchTime = System.currentTimeMillis();
		Utils.myLogger.log(Level.INFO, toString(), new Object[] { lancher, this });
		try {
			sleep(flightTime);
			Utils.myLogger.log(Level.INFO, "missile " + missileId + " exploded", new Object[] {lancher, this});
		} catch (InterruptedException e) {
			Utils.myLogger.log(Level.INFO, "Missile " + missileId + " got intercepted", this);
		}
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
