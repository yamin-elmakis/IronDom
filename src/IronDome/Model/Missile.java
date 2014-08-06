package IronDome.Model;

import IronDome.Utils.Destination;

public class Missile extends Thread {

	private static int getId = 10;
	private int launchTime, flyTime, damage;
	private String id;
	private Destination destination;
	
	public Missile(String id, int launchTime, int flyTime, int damage, Destination destination) {
		this.id = id;
		this.launchTime = launchTime;
		this.flyTime = flyTime;
		this.damage = damage;
		this.destination = destination;
	}
	
	public Missile(int flyTime, int damage, Destination destination) {
		this.id = "M" + getId++;
		this.launchTime = 0;
		this.flyTime = flyTime;
		this.damage = damage;
		this.destination = destination;
	}

	@Override
	public void run() {
//		super.run();
		System.out.println("id : " + id + " run");	
		try {
			sleep(flyTime);
		} catch (InterruptedException e) {
			System.out.println("InterruptedException to " + id);
		}
	}
	
}
