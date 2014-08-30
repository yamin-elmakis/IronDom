package IronDome.model;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Level;

import sun.launcher.resources.launcher;
import IronDome.listeners.IAllWar;
import IronDome.utils.ComponentStatus;
import IronDome.utils.TzoukEitanLogFilter;
import IronDome.utils.TzoukEitanLogFormatter;
import IronDome.utils.TzoukEitanLogger;
import IronDome.utils.Utils;

public class Missile extends Thread {

	private static final String LOGS_FOLDER_PREFIX = "missiles/Missile";
	private static int missileIdGenerator = 20;
	private int flightTime, damage;
	private long launchTime; 
	private String missileId;
	private String destination;
	private Launcher lancher;
	private IAllWar allWar;
	
//	public Missile(String missileId, int flightTime, int damage, Destination destination, Launcher l) throws SecurityException, IOException {
//		this(missileId, 0, flightTime, damage, destination, l);
//	}

	public Missile(String missileId) {
		this.missileId = missileId;
	}
	
	public Missile(String missileId, int flightTime, int damage, String destination, Launcher l) {
		this.missileId = missileId;
		this.flightTime = 1000 * flightTime; // turn to millisecond
		this.damage = damage;
		this.destination = destination;
		this.lancher = l; 
	}
	
	@Override
	public void run() {
		String logFilePath = LOGS_FOLDER_PREFIX + missileId;
		TzoukEitanLogger.addFileHandler(logFilePath , this);
		
		this.launchTime = System.currentTimeMillis();
		allWar.missileNotification(this, ComponentStatus.launched); // update TzoukEitan about this missile launch
		TzoukEitanLogger.myLogger.log(Level.INFO, toString() + " launched", new Object[] { lancher, this });
		try {
			sleep(flightTime);
			if (Utils.bool60PercentTrue()) {
				TzoukEitanLogger.myLogger.log(Level.INFO, missileId + " exploded", new Object[] { lancher, this });
				allWar.missileNotification(this, ComponentStatus.hit);
			}
			else {
				// if the missile miss then the damage is 0
				this.damage = 0;
				TzoukEitanLogger.myLogger.log(Level.INFO, missileId + " exploded in open space", new Object[] { lancher, this });
				allWar.missileNotification(this, ComponentStatus.miss);
			}
		} catch (InterruptedException e) {
			allWar.missileNotification(this, ComponentStatus.destroyed);
			TzoukEitanLogger.myLogger.log(Level.INFO, missileId + " Interrupted", new Object[] { lancher, this });
		}
	}

	public void registerAllMissiles(IAllWar allMissiles) {
		this.allWar = allMissiles;
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

	public int getDamage() {
		return damage;
	}

	public String getDestination() {
		return destination;
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
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yy HH:mm:ss");
        Date resultdate = new Date(launchTime);
		return "Missile [ missileId=" + missileId
				+ ", launcherId=" + lancher.getLauncherId()
				+ ", flyTime=" + flightTime
				+ ", damage=" + damage 
				+ ", launchTime=" + sdf.format(resultdate) 
				+ ", destination=" + destination + "]";
	}
}
