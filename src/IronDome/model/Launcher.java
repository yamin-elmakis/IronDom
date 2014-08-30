package IronDome.model;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.logging.Level;

import IronDome.listeners.IAllWar;
import IronDome.utils.ComponentStatus;
import IronDome.utils.TzoukEitanLogger;
import IronDome.utils.Utils;

public class Launcher extends Thread {

	private static final String LOGS_FOLDER_PREFIX = "launchers/Launchers";
	private static int launcherIdGenerator = 110;
	private String launcherId;
	private boolean isRunning; // if the main loop of the thread is running
	private boolean isExposed; // if the launcher Exposed - only when he Exposed he can shoot
	private boolean isHidden; // if the launcher have the ability to hide when he done shooting
	private Queue<Missile> missiles;
	private IAllWar allWar;

	public Launcher() {
		this(generateLauncherID());
	}
	
	public Launcher(String launcherID) {
		this(launcherID, Utils.rand.nextBoolean());
	}
	
	public Launcher(String id, boolean isHidden) {
		this.launcherId = id;
		this.isHidden = isHidden;
		isExposed = !isHidden;
		this.missiles = new ArrayDeque<Missile>();
		isRunning = true;
	}

	@Override
	public void run() {
		// add a file handler 
		String logFilePath = LOGS_FOLDER_PREFIX + launcherId;
		TzoukEitanLogger.addFileHandler(logFilePath , this);
		TzoukEitanLogger.myLogger.log(Level.INFO, toString() + " entered Tzouk Eitan", this);

		while (isRunning) {
			if (!missiles.isEmpty()) {
				exposedLauncher();
				shoot();
			} else {
				hideLauncher();
			}
		}
	}

	private void shoot() {
		if (!isRunning)
			return;
		Missile m = missiles.poll();
		TzoukEitanLogger.myLogger.log(Level.INFO, "Launcher " + launcherId + " shooting missile "+ m.getMissileId(), this);
		m.start();
		try {
			m.join();
		} catch (InterruptedException e) { } 
	}

	public void loadMissile(String missileID, int flightTime, int damage, String destination) {
		TzoukEitanLogger.myLogger.log(Level.INFO, launcherId + " load missile " + missileID, this);
		Missile missile = new Missile(missileID, flightTime, damage, destination, this);
		missile.registerAllMissiles(allWar);
		missiles.add(missile);
	}

	public void destroy() {
		TzoukEitanLogger.myLogger.log(Level.INFO, "Launcher " + launcherId + " destroyed", this);
		isRunning = false;
	}

	private void exposedLauncher() {
		if (isRunning && isHidden && !isExposed) {
			TzoukEitanLogger.myLogger.log(Level.INFO, "Launcher " + launcherId + " start exposing", this);
			try {
				sleep(Utils.launcherMovingTime());
			} catch (InterruptedException e) {		}
			setExposed(true);
			TzoukEitanLogger.myLogger.log(Level.INFO, "Launcher " + launcherId + " exposed", this);
		}
	}

	/**
	 * if the launcher has the ability to hide and it was exposed then start
	 * hiding it, hidden launcher can't shoot
	 */
	private void hideLauncher() {
		if (isRunning && isHidden && isExposed)
			try {
				TzoukEitanLogger.myLogger.log(Level.INFO, "Launcher " + launcherId + " start hiding", this);
				sleep(Utils.launcherMovingTime());
				setExposed(false);
				TzoukEitanLogger.myLogger.log(Level.INFO, "Launcher " + launcherId + " hidden", this);
			} catch (InterruptedException e) {		}
	}

	public void registerAllWar(IAllWar allWar) {
		this.allWar = allWar;
		allWar.launcherNotification(this, ComponentStatus.launched);
	}

	public String getLauncherId() {
		return launcherId;
	}

	public boolean isExposed() {
		return isExposed;
	}

	/** set the launcher exposed - only when it exposed it can shoot */
	public void setExposed(boolean isExposed) {
		this.isExposed = isExposed;
	}

	public boolean isHidden() {
		return isHidden;
	}

	public void setRunning(boolean isRunning) {
		// only 1 bomber can change the flag at a time
		synchronized (this) {
			this.isRunning = isRunning;
		}
	}

	public void setHidden(boolean isHidden) {
		this.isHidden = isHidden;
	}

	public Queue<Missile> getMissiles() {
		return missiles;
	}
	
	public static String generateLauncherID(){
		return "L"+launcherIdGenerator++;
	}
	@Override
	public boolean equals(Object arg0) {
		Launcher other = (Launcher) arg0;
		return this.launcherId.equals(other.getLauncherId());
	}

	@Override
	public String toString() {
		return "Launcher [launcherId=" + launcherId + ", isHidden=" + isHidden + "]";
	}
	
}
