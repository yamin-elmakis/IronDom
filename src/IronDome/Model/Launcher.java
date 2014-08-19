package IronDome.Model;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.logging.FileHandler;
import java.util.logging.Level;

import IronDome.Listeners.IAllWar;
import IronDome.Utils.Destination;
import IronDome.Utils.TzoukEitanLogFilter;
import IronDome.Utils.TzoukEitanLogFormatter;
import IronDome.Utils.Utils;

public class Launcher extends Thread {

	private static int getId = 104;
	private static final long MOVING_TIME = 1500;
	private String launcherId;
	private boolean isRunning; // if the main loop of the thread is running
	private boolean isExposed; // if the launcher Exposed - only when he Exposed
								// he can shoot
	private boolean isHidden; // if the launcher have the ability to hide when
								// he done shooting
	private Queue<Missile> missiles;
	private IAllWar allMissiles;

	public Launcher(String id, boolean isHidden, ArrayDeque<Missile> missiles) throws SecurityException, IOException{
		this.launcherId = id;
		this.isHidden = isHidden;
		isExposed = !isHidden;
		this.missiles = missiles;
		isRunning = true;
		setLoggerData();
	
	}

	public Launcher() throws SecurityException, IOException {
		this.launcherId = "L" + getId++;
		this.isHidden = Utils.rand.nextBoolean();
		isExposed = !isHidden;
		this.missiles = new ArrayDeque<Missile>();
		isRunning = true;	
		setLoggerData();
	}
	
	public void setLoggerData() throws SecurityException, IOException{
		FileHandler fileHandler = new FileHandler("Launcher"+launcherId+"Log.txt", false);
		fileHandler.setFormatter(new TzoukEitanLogFormatter());
		fileHandler.setFilter(new TzoukEitanLogFilter(this));
		Utils.myLogger.addHandler(fileHandler);
		Utils.myLogger.log(Level.INFO, toString(), this);
	}

	@Override
	public void run() {
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

	public void loadMissile(Destination dest, int flightTime) throws SecurityException, IOException {
		Utils.myLogger.log(Level.INFO, launcherId + " load missile", this);
		Missile missile = new Missile(1000 * flightTime, Utils.rand.nextInt(5000) + 1000, dest, this);
		missiles.add(missile);
	}

	public void destroy() {
		Utils.myLogger.log(Level.INFO, "Launcher " + launcherId + " destroy", this);
		isRunning = false;
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

	@Override
	public String toString() {
		return "Launcher [launcherId=" + launcherId + ", isHidden=" + isHidden + "]";
	}
	
}
