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
		synchronized (this) {
			this.missileCount++;
		}
	}

	public void setInterceptionsCount() {
		synchronized (this) {
			this.interceptionsCount++;
		}
	}

	public void setExplosionsCount() {
		synchronized (this) {
			this.explosionsCount++;
		}
	}

	public void setDestroyedLaunchersCount() {
		synchronized (this) {
			this.destroyedLaunchersCount++;
		}
	}

	public void addToTotalDamage(int totalDamage) {
		synchronized (this) {
			this.totalDamage += totalDamage;
		}
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

