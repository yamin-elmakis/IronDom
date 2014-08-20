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

	private static int missileIdGenerator = 10;
	private int flightTime, damage;
	private long launchTime; 
	private String missileId;
	private Destination destination;
	
//	public Missile(String missileId, int flightTime, int damage, Destination destination, Launcher l) throws SecurityException, IOException {
//		this(missileId, 0, flightTime, damage, destination, l);
//	}

	public Missile(String missileId, int flightTime, int damage, Destination destination, Launcher l) {
		this.missileId = missileId;
		this.flightTime = 1000 * flightTime;
		this.damage = damage;
		this.destination = destination;
		setLoggerData(l);
	}
	
	public void setLoggerData(Launcher l) {
		FileHandler fileHandler;
		try {
			fileHandler = new FileHandler("Missile"+missileId+"Log.txt");
			fileHandler.setFormatter(new TzoukEitanLogFormatter());
			fileHandler.setFilter(new TzoukEitanLogFilter(this));
			Utils.myLogger.addHandler(fileHandler);
		} catch (SecurityException | IOException e) {
			e.printStackTrace();
		}
		Utils.myLogger.log(Level.INFO, toString(), new Object[] { l, this });	
	}
	
	@Override
	public void run(){
//		TODO fireEvent();
		this.launchTime = System.currentTimeMillis();
		Utils.myLogger.log(Level.INFO, "Missile "+missileId + " flying for " + flightTime, this);
		try {
			sleep(flightTime);
		} catch (InterruptedException e) {
			Utils.myLogger.log(Level.INFO, "Missile "+missileId + " got InterruptedException", this);
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

	public static String generateMissileId(){
		return "M"+missileIdGenerator++;
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
