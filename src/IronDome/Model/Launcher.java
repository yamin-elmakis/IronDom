package IronDome.Model;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.logging.FileHandler;
import java.util.logging.Level;

import sun.launcher.resources.launcher;
import IronDome.Listeners.IAllWar;
import IronDome.Utils.Destination;
import IronDome.Utils.TzoukEitanLogFilter;
import IronDome.Utils.TzoukEitanLogFormatter;
import IronDome.Utils.Utils;

public class Launcher extends Thread {

	private static int launcherIdGenerator = 110;
	private static final long MOVING_TIME = 1500;
	private String launcherId;
	private boolean isRunning; // if the main loop of the thread is running
	private boolean isExposed; // if the launcher Exposed - only when he Exposed
								// he can shoot
	private boolean isHidden; // if the launcher have the ability to hide when
								// he done shooting
	private Queue<Missile> missiles;
	private IAllWar allMissiles;
	FileHandler fileHandler;

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

	public void setLoggerData() throws SecurityException, IOException{
		fileHandler = new FileHandler("Launcher"+launcherId+"Log.txt", false);
		fileHandler.setFormatter(new TzoukEitanLogFormatter());
		fileHandler.setFilter(new TzoukEitanLogFilter(this));
		Utils.myLogger.addHandler(fileHandler);
		Utils.myLogger.log(Level.INFO, toString(), this);
	}

	@Override
	public void run() {
		try {
			setLoggerData();
		} catch (SecurityException | IOException e) {
			e.printStackTrace();
		}
		Utils.myLogger.log(Level.INFO, "Launcher " + launcherId + " enter run", this);

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
		Utils.myLogger.log(Level.INFO, "Launcher " + launcherId + " shooting missile "+ m.getMissileId(), this);
		m.start();
		allMissiles.registerMissile(m); // update TzoukEitan that it shoot a missile
		try {
			m.join();
			Utils.myLogger.log(Level.INFO, "missile " + m.getMissileId() + " exploded", new Object[] {m, this});
		} catch (InterruptedException e) {
			Utils.myLogger.log(Level.INFO, "missile " + m.getMissileId() + " intercepted", new Object[] {m, this});
		} finally {
			allMissiles.unregisterMissile(m);
		}
	}

	public void loadMissile(String missileID, int flightTime, int damage, Destination destination) {
		Utils.myLogger.log(Level.INFO, launcherId + " load missile", this);
		Missile missile = new Missile(missileID, flightTime, damage, destination, this);
		missiles.add(missile);
	}

	public void destroy() {
		Utils.myLogger.log(Level.INFO, "Launcher " + launcherId + " destroy", this);
		isRunning = false;
		// TODO check if the close fileHandler works
		fileHandler.close();
		this.interrupt();
	}

	private void exposedLauncher() {
		if (isHidden && !isExposed)
			try {
				Utils.myLogger.log(Level.INFO, "Launcher " + launcherId + " start exposing", this);
				sleep(MOVING_TIME);
				setExposed(true);
				Utils.myLogger.log(Level.INFO, "Launcher " + launcherId + " exposed", this);
			} catch (InterruptedException e) {
			}
	}

	/**
	 * if the launcher has the ability to hide and it was exposed then start
	 * hiding it, hidden launcher can't shoot
	 */
	private void hideLauncher() {
		if (isHidden && isExposed)
			try {
				Utils.myLogger.log(Level.INFO, "Launcher " + launcherId
						+ " start hiding", this);
				sleep(MOVING_TIME);
				setExposed(false);
				Utils.myLogger.log(Level.INFO, "Launcher " + launcherId
						+ " hidden", this);
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
