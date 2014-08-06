package IronDome.Model;

import java.util.ArrayList;

import IronDome.Utils.Destination;

public class Launcher extends Thread{

	private String launcherId;
	private boolean isShooting, isHidden, canIShoot, isRunning;
	private ArrayList<Missile> missiles;

	public Launcher(String id, boolean isShooting, ArrayList<Missile> missiles) {
		this.launcherId = id;
		this.isShooting = isShooting;
		this.missiles = missiles;
		canIShoot = false;
		isRunning = true;
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
