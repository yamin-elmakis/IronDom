package IronDome.Model;

public class Missile {

	private int launchTime, flyTime, damage;
	private String id, destination;
	
	public Missile(String id, int launchTime, int flyTime, int damage, String destination) {
		this.id = id;
		this.launchTime = launchTime;
		this.flyTime = flyTime;
		this.damage = damage;
		this.destination = destination;
	}
	
	
}
