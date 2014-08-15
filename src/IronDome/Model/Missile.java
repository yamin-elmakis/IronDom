package IronDome.Model;

import java.util.logging.Level;

import IronDome.Utils.Destination;
import IronDome.Utils.Utils;

public class Missile extends Thread {

	private static int getId = 10;
	private int launchTime, flyTime, damage;
	private String missileId;
	private Destination destination;
	
	public Missile(String id, int launchTime, int flyTime, int damage, Destination destination) {
		this.missileId = id;
		this.launchTime = launchTime;
		this.flyTime = flyTime;
		this.damage = damage;
		this.destination = destination;
	}
	
	public Missile(int flyTime, int damage, Destination destination) {
		this.missileId = "M" + getId++;
		this.launchTime = 0;
		this.flyTime = flyTime;
		this.damage = damage;
		this.destination = destination;
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
		return "Missile [launchTime=" + launchTime + ", flyTime=" + flyTime
				+ ", damage=" + damage + ", missileId=" + missileId
				+ ", destination=" + destination + "]";
	}
}
