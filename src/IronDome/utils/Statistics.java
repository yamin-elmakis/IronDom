package IronDome.utils;

public class Statistics {

	int missileCount, interceptionsCount, explosionsCount, destroyedLaunchersCount, totalDamage;

	public Statistics() {
		this.missileCount = 0;
		this.interceptionsCount = 0;
		this.explosionsCount = 0;
		this.destroyedLaunchersCount = 0;
		this.totalDamage = 0;
	}

	public void setMissileCount() {
		this.missileCount ++;
	}

	public void setInterceptionsCount() {
		this.interceptionsCount ++;
	}

	public void setExplosionsCount() {
		this.explosionsCount ++;
	}

	public void setDestroyedLaunchersCount() {
		this.destroyedLaunchersCount ++;
	}

	public void addToTotalDamage(int totalDamage) {
		this.totalDamage += totalDamage;
	}

	@Override
	public String toString() {
		return "Statistics:\nmissileCount=" + missileCount
				+ "\ninterceptionsCount=" + interceptionsCount
				+ "\nexplosionsCount=" + explosionsCount
				+ "\ndestroyedLaunchersCount=" + destroyedLaunchersCount
				+ "\ntotalDamage=" + totalDamage;
	}
}

