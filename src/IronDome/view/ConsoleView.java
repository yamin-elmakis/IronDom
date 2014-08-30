package IronDome.view;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;

import IronDome.listeners.ITzoukEitanViewEventsListener;
import IronDome.model.Launcher;
import IronDome.model.Missile;
import IronDome.utils.DestructorType;
import IronDome.utils.TzoukEitanConsoleFormatter;
import IronDome.utils.TzoukEitanLogFilter;
import IronDome.utils.TzoukEitanLogger;

public class ConsoleView implements ITzoukEitanView {

	public static final String LOG_FILE_NAME = "consoleViewLog.txt";
	private List<ITzoukEitanViewEventsListener> allListeners;
	private Scanner scan;
	private boolean isRunning;
	private int choice;
	private final String MAIN_MANU = "\nMENU\n___________\npress:\n1.add Launcher.\n2.Launch missile.\n3.add Missile Launcher Destructor.\n4.add Missile Destructor.\n5.destroy Missile.\n6.destroy Launcher.\n7.show statistics.\n8.EXIT & show statistics.";
	
	public ConsoleView() throws SecurityException, IOException {
		allListeners = new LinkedList<ITzoukEitanViewEventsListener>();
		scan = new Scanner(System.in);
		isRunning = true;
		
		TzoukEitanLogger.addFileHandler(LOG_FILE_NAME , this);
		ConsoleHandler consolHandler = new ConsoleHandler(); 
		consolHandler.setFormatter(new TzoukEitanConsoleFormatter());
		consolHandler.setFilter(new TzoukEitanLogFilter(this));
		TzoukEitanLogger.myLogger.addHandler(consolHandler);
		TzoukEitanLogger.myLogger.log(Level.INFO, "Console View Log", this);
	}

	@Override
	public void runMenu(){
		while (isRunning) {
			TzoukEitanLogger.myLogger.log(Level.INFO, MAIN_MANU, this);
			try {
				choice = scan.nextInt();
			}catch (InputMismatchException e) {
				scan.nextLine();
				choice = -1;
			}
			catch (Exception e) {
				scan.nextLine();
				choice = -1;
			}
			switch (choice) {
			case 1:
				TzoukEitanLogger.myLogger.log(Level.INFO, "add Launcher pressed", this);
				fireAddLauncherEvent();
				break;
			case 2: 
				TzoukEitanLogger.myLogger.log(Level.INFO, "launch missile pressed", this);
				fireLaunchMissileEvent();
				break;
			case 3:
				TzoukEitanLogger.myLogger.log(Level.INFO, "add Missile Launcher Destructor Pressed", this);
				fireAddLauncherDestructorEvent();
				break;
			case 4:
				TzoukEitanLogger.myLogger.log(Level.INFO, "add Missile Destructor Pressed", this);
				fireAddMissileDestructorEvent();
				break;
			case 5:
				TzoukEitanLogger.myLogger.log(Level.INFO, "destroy Missile Pressed", this);
				fireDestroyMissileEvent();
				break;
			case 6:
				TzoukEitanLogger.myLogger.log(Level.INFO, "destroy Launcher Pressed", this);
				fireDestroyLauncherEvent();
				break;
			case 7:
				TzoukEitanLogger.myLogger.log(Level.INFO, "show statistics", this);
				fireShowStatisticsEvent();
				break;
			case 8:
				TzoukEitanLogger.myLogger.log(Level.INFO, "EXIT & show statistics ", this);
				fireShowStatisticsEvent();
				exitTheView();
				break;
			default:
				break;
			}
		}
	}
	
	private void fireShowStatisticsEvent() {
		for (ITzoukEitanViewEventsListener listener : allListeners) {
			listener.showStatistics();
		}
	}

	private void fireLaunchMissileEvent() {
		for (ITzoukEitanViewEventsListener listener : allListeners) {
			listener.LaunchMissile();
		}	
	}

	/** call function when the user pressed Destroy Missile.
	 * the user don't know it which missile to destroy yet*/
	private void fireDestroyMissileEvent() {
		for (ITzoukEitanViewEventsListener listener : allListeners) {
			listener.destroyMissile();
		}
	}
	
	/** call function when the user pressed Destroy launcher.
	 * the user don't know it which launcher to destroy yet*/
	private void fireDestroyLauncherEvent() {
		for (ITzoukEitanViewEventsListener listener : allListeners) {
			listener.destroyLauncher();
		}
	}
	
	/** call function when the user choose a missile to destroy. */
	private void fireDestroyMissileEvent(String missileId) {
		for (ITzoukEitanViewEventsListener listener : allListeners) {
			listener.destroyMissile(missileId);
		}
	}
	
	/** call function when the user choose a launcher to destroy. */
	private void fireDestroyLauncherEvent(String launcherId) {
		for (ITzoukEitanViewEventsListener listener : allListeners) {
			listener.destroyLauncher(launcherId);
		}
	}

	private void fireAddMissileDestructorEvent() {
		for (ITzoukEitanViewEventsListener listener : allListeners) {
			listener.addMissileDestructor();
		}
	}

	private void fireAddLauncherDestructorEvent() {
		for (ITzoukEitanViewEventsListener listener : allListeners) {
			listener.addMissileLauncherDestructor();
		}
	}

	private void fireAddLauncherEvent(){
		//TODO add parameters for adding launcher
		for (ITzoukEitanViewEventsListener listener : allListeners) {
			listener.addLauncher();
		}
	}
	
	@Override
	public void missileFired(String id, String dest, int damage) {
		TzoukEitanLogger.myLogger.log(Level.INFO, "missile "+id+" with damage of " + damage +" fired at "+dest, this);
	}

	@Override
	public void missileDestructed(String id) {
		TzoukEitanLogger.myLogger.log(Level.INFO, "missile "+id+" destructed", this);
	}

	@Override
	public void interceptionFailed(String DestructorId, String targetId) {
		TzoukEitanLogger.myLogger.log(Level.INFO, "Destructor "+DestructorId+" failed to intercept " + targetId, this);
	}

	@Override
	public void missileExploded(String missileId, String dest, int damage) {
		TzoukEitanLogger.myLogger.log(Level.INFO, "missile "+missileId+" exploded in "+ dest + " caused " + damage + " damage.", this);
	}

	@Override
	public void addedLauncher(String launcherId) {
		TzoukEitanLogger.myLogger.log(Level.INFO, "Launcher "+launcherId+" added to Hamas", this);
	}

	@Override
	public void launcherDestroyed(String mldId, String launcherId) {
		TzoukEitanLogger.myLogger.log(Level.INFO, "MLD "+mldId+" destroyed launcher "+launcherId, this);
	}

	@Override
	public void addedMissileLauncherDestructor(DestructorType type) {
		TzoukEitanLogger.myLogger.log(Level.INFO, "Missile Launcher Destructor "+ type +" added to IDF", this);
	}

	@Override
	public void addedMissileDestructor(String id) {
		TzoukEitanLogger.myLogger.log(Level.INFO, "Missile Destructor "+id+" added to IDF", this);
	}

	@Override
	public void registerController(ITzoukEitanViewEventsListener listener) {
		allListeners.add(listener);
	}

	@Override
	public void showMissilelist(Vector<Missile> allMissiles) throws InputMismatchException {
		int i = 0;
		int missileIndex = 0;
		int BUFFER_SIZE = 512;
		StringBuffer text = new StringBuffer(BUFFER_SIZE);
		Vector<String> missilesId = new Vector<String>();
		// build the missile id menu 
		if (allMissiles.size() < 1){
			TzoukEitanLogger.myLogger.log(Level.INFO, "there is no missiles to intercept.\n", this);
			return;
		}
		else
			text.append("choose a missile you'd like to intercept:\n0.EXIT\n");
		for (Missile missile : allMissiles) {
			float impactTime = (missile.getFlightTime() - (System.currentTimeMillis() - missile.getLaunchTime()))/1000;
			text.append(String.format("%d. missile %s will impact in %.2f seconds\n", ++i, missile.getMissileId(), impactTime));
			missilesId.add(missile.getMissileId());
		}
		TzoukEitanLogger.myLogger.log(Level.INFO, text.toString(), this);
		
		try {
			missileIndex = scan.nextInt();
		} catch (InputMismatchException e) {
			TzoukEitanLogger.myLogger.log(Level.INFO, "wrong input", this);
			scan.nextLine();
			throw new InputMismatchException();
		}
		
		if (missileIndex == 0){ // 0 - EXIT
			return;
		}
		if (missileIndex > i || missileIndex < 1){ // index out of bound - refresh
			throw new InputMismatchException();
		}
		
		String missileId = missilesId.get(missileIndex-1);
		TzoukEitanLogger.myLogger.log(Level.INFO, "missile " + missileId +" chosen for interception", this);
		fireDestroyMissileEvent(missileId);
	}
	
	@Override
	public void showLaunchersList(Vector<Launcher> exposedLaunchers) throws InputMismatchException {
		int i = 0;
		int launcherIndex = 0;
		int BUFFER_SIZE = 512;
		Vector<String> launchersId = new Vector<String>();
		StringBuffer text = new StringBuffer(BUFFER_SIZE);
		// build the launcher id menu 
		if (exposedLaunchers.size() < 1) {
			TzoukEitanLogger.myLogger.log(Level.INFO, "there is no launchers to destroy.", this);
			return;
		}
		else
			text.append("choose a launcher you'd like to destroy:\n0.EXIT\n");
		for (Launcher launcher: exposedLaunchers) {
			text.append(String.format("%d. launcher %s is %s \n", ++i, launcher.getLauncherId(), (launcher.isExposed()) ? "Exposed" : "Hidden" ));
			launchersId.add(launcher.getLauncherId());
		}
		TzoukEitanLogger.myLogger.log(Level.INFO, text.toString(), this);
		
		try {
			launcherIndex = scan.nextInt();
		} catch (InputMismatchException e) {
			TzoukEitanLogger.myLogger.log(Level.INFO, "wrong input", this);
			scan.nextLine();
			throw new InputMismatchException();
		}
		
		if (launcherIndex == 0){ // 0 - EXIT
			return;
		}
		if (launcherIndex > i || launcherIndex < 1) { // index out of bound - refresh
			throw new InputMismatchException();
		}
	
		String launcherId = launchersId.get(launcherIndex-1);
		TzoukEitanLogger.myLogger.log(Level.INFO, "launcher " + launcherId +" chosen for destruction", this);
		fireDestroyLauncherEvent(launcherId);
	}
	
	@Override
	public void notifyUser(String text) {
		TzoukEitanLogger.myLogger.log(Level.INFO, text, this);
	}

	@Override
	public void showStatistics(String statistics) {
		TzoukEitanLogger.myLogger.log(Level.INFO, "\n"+statistics, this);
	}

	private void exitTheView(){
		isRunning = false;
		Handler[] handlers = TzoukEitanLogger.myLogger.getHandlers();
		for (Handler handler : handlers) {
			handler.close();
		}
	}
}
