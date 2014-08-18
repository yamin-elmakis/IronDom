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

	private static int getId = 10;
	private int launchTime, flyTime, damage;
	private String missileId;
	private Destination destination;
	
	public Missile(String id, int launchTime, int flyTime, int damage, Destination destination, Launcher l) throws SecurityException, IOException {
		this.missileId = id;
		this.launchTime = launchTime;
		this.flyTime = flyTime;
		this.damage = damage;
		this.destination = destination;
		setLoggerData(l);
	}
	
	public Missile(int flyTime, int damage, Destination destination, Launcher l) throws SecurityException, IOException {
		this.missileId = "M" + getId++;
		this.launchTime = 0;
		this.flyTime = flyTime;
		this.damage = damage;
		this.destination = destination;
		setLoggerData(l);
	}

	public void setLoggerData(Launcher l) throws SecurityException, IOException{
		FileHandler fileHandler = new FileHandler("Missile"+missileId+"Log.txt");
		fileHandler.setFormatter(new TzoukEitanLogFormatter());
		fileHandler.setFilter(new TzoukEitanLogFilter(this));
		Utils.myLogger.addHandler(fileHandler);
		Utils.myLogger.log(Level.INFO, toString(), new Object[] { l, this });	
	}
	
	@Override
	public void run(){
//		super.run();
		
//		TODO fireEvent();
		Utils.myLogger.log(Level.INFO, "Missile "+missileId + " flying for " + flyTime, this);
		try {
			sleep(flyTime);
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

	@Override
	public String toString() {
		return "Missile [ missileId=" + missileId
				+ ", flyTime=" + flyTime
				+ ", damage=" + damage 
				+ ", launchTime=" + launchTime 
				+ ", destination=" + destination + "]";
	}
}
