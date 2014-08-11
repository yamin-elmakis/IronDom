package IronDome.Model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.FileHandler;
import IronDome.Utils.Destination;
import IronDome.Utils.TzoukEitanLogFormatter;
import IronDome.Utils.Utils;

public class Launcher extends Thread{

	private String launcherId;
	private boolean isShooting, isHidden, canIShoot, isRunning;
	private ArrayList<Missile> missiles;

	public Launcher(String id, boolean isShooting, ArrayList<Missile> missiles) throws SecurityException, IOException {
		this.launcherId = id;
		this.isShooting = isShooting;
		this.missiles = missiles;
		canIShoot = false;
		isRunning = true;
		// set the logger data
		FileHandler fileHandler = new FileHandler(id+"Log.txt");
		fileHandler.setFormatter(new TzoukEitanLogFormatter());
//		fileHandler.setFilter(new MyLoggerFilter(name));
		Utils.myLogger.addHandler(fileHandler);
	}

	@Override
	public void run() {
//		super.run();
		System.out.println("launcherId : " + launcherId + " run");	

		while (isRunning) {
			if (canIShoot) {
				try {
					shoot();
				} catch (InterruptedException e) {
					System.out.println("InterruptedException to " + launcherId);
				} finally {
					canIShoot = false;
				}
			}
		}
	}

	private void shoot() throws InterruptedException {
		int flyTime = (int) (Math.random()*15 + 3);
		int damage = (int) (Math.random()*5000 + 1000);
		int destination = (int)(Math.random()*5); 
		Missile m = new Missile(flyTime, damage, Destination.values()[destination]);
		missiles.add(m);
		m.start();
		m.join();
	}
	
	public void destroy(){
		isRunning = false;
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

	public ArrayList<Missile> getMissiles() {
		return missiles;
	}
	
	
}
