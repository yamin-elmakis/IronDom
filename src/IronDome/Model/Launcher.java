package IronDome.Model;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.logging.FileHandler;
import java.util.logging.Level;

import IronDome.Listeners.IAllWar;
import IronDome.Utils.Destination;
import IronDome.Utils.TzoukEitanLogFilter;
import IronDome.Utils.TzoukEitanLogFormatter;
import IronDome.Utils.Utils;

public class Launcher extends Thread{

	private String launcherId;
	private boolean isShooting, isHidden, canIShoot, isRunning;
	private Queue<Missile> missiles;
	private IAllWar allMissiles;

	public Launcher(String id, boolean isShooting, ArrayDeque<Missile> missiles) throws SecurityException, IOException {
		this.launcherId = id;
		this.isShooting = isShooting;
		this.missiles = missiles;
		canIShoot = false;
		isRunning = true;
		// set the logger data
		FileHandler fileHandler = new FileHandler("LauncherLog.txt", true);
		fileHandler.setFormatter(new TzoukEitanLogFormatter());
		fileHandler.setFilter(new TzoukEitanLogFilter(this));
		Utils.myLogger.addHandler(fileHandler);
		Utils.myLogger.log(Level.INFO, "Launcher created ", this);
	}

	@Override
	public void run() {
//		super.run();
//		System.out.println("launcherId : " + launcherId + " run");	
		Utils.myLogger.log(Level.INFO, "Launcher "+launcherId + " enter run", this);

		while (isRunning) {
			if (!missiles.isEmpty()) {
				
				shoot();
			}
		}
	}

	private void shoot() {
		Utils.myLogger.log(Level.INFO, "Launcher "+launcherId + " shooting", this);
		Missile m = missiles.poll();
		m.start();
		try {
			m.join();
			Utils.myLogger.log(Level.INFO, "missile "+m.getId() +" exploded",new Object[]{m, this});
		} catch (InterruptedException e) {
			Utils.myLogger.log(Level.INFO, "missile "+m.getId() +" intersepted", new Object[]{m, this});
		} finally {
			allMissiles.unregisterMissile(m);
		}
	}
	
	public void loadMissile(Destination dest, int flightTime){
		Utils.myLogger.log(Level.INFO, launcherId + " load missile", this);
		Missile missile = new Missile(flightTime, Utils.rand.nextInt(5000)+1000, dest);
		allMissiles.registerMissile(missile);
		missiles.add(missile);
	}
	public void destroy(){
		Utils.myLogger.log(Level.INFO, "Launcher "+launcherId+" destroy", this);
		isRunning = false;
	}

	public void registerAllMissiles(IAllWar allMissiles){
		this.allMissiles = allMissiles;
	}
	
	public boolean isCanIShoot() {
		return canIShoot;
	}

	/** if you want to shoot set canIShoot to true */
	public void setCanIShoot(boolean canIShoot) {
		this.canIShoot = canIShoot;
	}

	public String getLauncherId() {
		return launcherId;
	}

	public boolean isShooting() {
		return isShooting;
	}

	public void setShooting(boolean isShooting) {
		this.isShooting = isShooting;
	}

	public boolean isHidden() {
		return isHidden;
	}

	public void setHidden(boolean isHidden) {
		this.isHidden = isHidden;
	}

	public Queue<Missile> getMissiles() {
		return missiles;
	}
}
