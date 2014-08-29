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
import IronDome.utils.Destination;
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
		this.flightTime = 500 * flightTime;
		this.damage = damage;
		this.destination = destination;
		this.lancher = l; 
	}
	
	@Override
	public void run() {
		String logFilePath = LOGS_FOLDER_PREFIX + missileId;
		TzoukEitanLogger.addFileHandler(logFilePath , this);
		
		this.launchTime = System.currentTimeMillis();
		TzoukEitanLogger.myLogger.log(Level.INFO, toString() + " launched", new Object[] { lancher, this });
		try {
			sleep(flightTime);
			if (Utils.bool60PercentTrue()) {
				TzoukEitanLogger.myLogger.log(Level.INFO, missileId + " exploded", new Object[] { lancher, this });
			}
			else {
				this.damage = 0;
				TzoukEitanLogger.myLogger.log(Level.INFO, missileId + " exploded in open space", new Object[] { lancher, this });
			}
			allMissiles.missileHitTheGround(this);
		} catch (InterruptedException e) {
			allMissiles.missileInterceptedInTheAir(this);
			TzoukEitanLogger.myLogger.log(Level.INFO, missileId + " Interrupted", new Object[] { lancher, this });
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

	public int getDamage() {
		return damage;
	}

	public Destination getDestination() {
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
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yy HH:mm");
        Date resultdate = new Date(launchTime);
		return "Missile [ missileId=" + missileId
				+ ", flyTime=" + flightTime
				+ ", damage=" + damage 
				+ ", launchTime=" + sdf.format(resultdate) 
				+ ", destination=" + destination + "]";
	}
}
