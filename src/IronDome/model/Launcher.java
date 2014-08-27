package IronDome.model;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.logging.FileHandler;
import java.util.logging.Level;

import IronDome.listeners.IAllWar;
import IronDome.utils.Destination;
import IronDome.utils.TzoukEitanLogFilter;
import IronDome.utils.TzoukEitanLogFormatter;
import IronDome.utils.TzoukEitanLogger;
import IronDome.utils.Utils;

public class Launcher extends Thread {

	private static final String LOGS_FOLDER_PREFIX = "launchers/Launchers";
	private static int launcherIdGenerator = 110;
	private static final long MOVING_TIME = 1500;
	private String launcherId;
	private boolean isRunning; // if the main loop of the thread is running
	private boolean isExposed; // if the launcher Exposed - only when he Exposed he can shoot
	private boolean isHidden; // if the launcher have the ability to hide when he done shooting
	private Queue<Missile> missiles;
	private IAllWar allMissiles;

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
		TzoukEitanLogger.myLogger.log(Level.INFO, toString() + " enter run", this);

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
		// TODO need to understand where to add fireEvent();
		Missile m = missiles.poll();
		TzoukEitanLogger.myLogger.log(Level.INFO, "Launcher " + launcherId + " shooting missile "+ m.getMissileId(), this);
		m.start();
		try {
			// TODO the missile throws exception in the run of the missile class and not in the launcher class
			m.join();
		} catch (InterruptedException e) { } 
	}

	public void loadMissile(String missileID, int flightTime, int damage, Destination destination) {
		TzoukEitanLogger.myLogger.log(Level.INFO, launcherId + " load missile " + missileID, this);
		Missile missile = new Missile(missileID, flightTime, damage, destination, this);
		missile.registerAllMissiles(allMissiles);
		missiles.add(missile);
	}

	public void destroy() {
		TzoukEitanLogger.myLogger.log(Level.INFO, "Launcher " + launcherId + " destroyed", this);
		isRunning = false;
	}

	private void exposedLauncher() {
		if (isHidden && !isExposed)
			try {
				TzoukEitanLogger.myLogger.log(Level.INFO, "Launcher " + launcherId + " start exposing", this);
				sleep(MOVING_TIME);
				setExposed(true);
				TzoukEitanLogger.myLogger.log(Level.INFO, "Launcher " + launcherId + " exposed", this);
			} catch (InterruptedException e) { }
	}

	/**
	 * if the launcher has the ability to hide and it was exposed then start
	 * hiding it, hidden launcher can't shoot
	 */
	private void hideLauncher() {
		if (isHidden && isExposed)
			try {
				TzoukEitanLogger.myLogger.log(Level.INFO, "Launcher " + launcherId + " start hiding", this);
				sleep(MOVING_TIME);
				setExposed(false);
				TzoukEitanLogger.myLogger.log(Level.INFO, "Launcher " + launcherId + " hidden", this);
			} catch (InterruptedException e) {
			}
	}

	public void registerAllMissiles(IAllWar allMissiles) {
		this.allMissiles = allMissiles;
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