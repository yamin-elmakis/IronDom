package IronDome.View;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;

import IronDome.Listeners.ITzoukEitanModelEventsListener;
import IronDome.Listeners.ITzoukEitanViewEventsListener;
import IronDome.Model.Launcher;
import IronDome.Model.Missile;
import IronDome.Utils.Destination;
import IronDome.Utils.Type;
import IronDome.Utils.TzoukEitanLogFilter;
import IronDome.Utils.TzoukEitanLogFormatter;
import IronDome.Utils.TzoukEitanLogger;

//TODO create abstract class and the console will implement it

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
		FileHandler fileHandler = new FileHandler(TzoukEitanLogger.LOGS_FOLDER+LOG_FILE_NAME, false);
		fileHandler.setFormatter(new TzoukEitanLogFormatter());
		fileHandler.setFilter(new TzoukEitanLogFilter(this));
		ConsoleHandler consolHandler = new ConsoleHandler(); 
		consolHandler.setFormatter(new TzoukEitanLogFormatter());
		consolHandler.setFilter(new TzoukEitanLogFilter(this));
		TzoukEitanLogger.myLogger.addHandler(fileHandler);
		TzoukEitanLogger.myLogger.addHandler(consolHandler);
		TzoukEitanLogger.myLogger.log(Level.INFO, "Console View Log", this);
	}

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
				fireAddedLauncherEvent();
				break;
			case 2: 
				TzoukEitanLogger.myLogger.log(Level.INFO, "launch missile pressed", this);
				fireLaunchMissileEvent();
				break;
			case 3:
				TzoukEitanLogger.myLogger.log(Level.INFO, "add Missile Launcher Destructor Pressed", this);
				fireAddedLauncherDestructorEvent();
				break;
			case 4:
				TzoukEitanLogger.myLogger.log(Level.INFO, "add Missile Destructor Pressed", this);
				fireAddedMissileDestructorEvent();
				break;
			case 5:
				TzoukEitanLogger.myLogger.log(Level.INFO, "destroy Missile Pressed", this);
				fireDestroyMissileEvent();
				break;
			case 6:
				TzoukEitanLogger.myLogger.log(Level.INFO, "destroy Launcher Pressed", this);
				break;
			case 7:
				TzoukEitanLogger.myLogger.log(Level.INFO, "show statistics", this);
				// TODO add statistics class.
				break;
			case 8:
				TzoukEitanLogger.myLogger.log(Level.INFO, "EXIT & show statistics ", this);
				exitTheView();
				break;
			default:
				break;
			}
		}
	}
	
	private Type chooseTypeOfMissileLauncherDestructor() {
		// TODO Auto-generated method stub
		return null;
	}

	private void fireLaunchMissileEvent() {
		for (ITzoukEitanViewEventsListener listener : allListeners) {
			listener.LaunchMissile();
		}	
	}

	private void fireDestroyMissileEvent() {
		for (ITzoukEitanViewEventsListener listener : allListeners) {
			listener.destroyMissile();
		}
	}
	
	private void fireDestroyMissileEvent(Missile missile) {
		for (ITzoukEitanViewEventsListener listener : allListeners) {
			listener.destroyMissile(missile);
		}
	}

	private void fireAddedMissileDestructorEvent() {
		for (ITzoukEitanViewEventsListener listener : allListeners) {
			listener.addMissileDestructor();
		}
	}

	private void fireAddedLauncherDestructorEvent() {
		for (ITzoukEitanViewEventsListener listener : allListeners) {
			listener.addMissileLauncherDestructor();
		}
	}

	private void fireAddedLauncherEvent(){
		//TODO add parameters for adding launcher
		for (ITzoukEitanViewEventsListener listener : allListeners) {
			listener.addLauncher();
		}
	}
	@Override
	public void missileDestructed(String id) {
		TzoukEitanLogger.myLogger.log(Level.INFO, "missile "+id+" destructed");
	}

	@Override
	public void missileFired(String id, Destination dest, int damage) {
		TzoukEitanLogger.myLogger.log(Level.INFO, "missile "+id+" with damage of " + damage +" fired at "+dest, this);
	}

	@Override
	public void addedLauncher(String id) {
		TzoukEitanLogger.myLogger.log(Level.INFO, "Launcher "+id+" added", this);
	}

	@Override
	public void addedMissileLauncherDestructor(String id) {
		TzoukEitanLogger.myLogger.log(Level.INFO, "Missile Launcher Destructor " + id + " added", this);
	}

	@Override
	public void addedMissileDestructor(String id) {
		TzoukEitanLogger.myLogger.log(Level.INFO, "Missile Destructor "+id+" added", this);
	}

	@Override
	public void registerController(ITzoukEitanViewEventsListener listener) {
		allListeners.add(listener);
		runMenu();
	}

	@Override
	public void showMissilelist(Vector<Missile> allMissiles) throws InputMismatchException {
		int i = 0;
		int missileIndex = 0;
		int BUFFER_SIZE = 512;
		StringBuffer text = new StringBuffer(BUFFER_SIZE);
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
		if (missileIndex > i+1 || missileIndex < 1){ // index out of bound - refresh
			throw new InputMismatchException();
		}
		Missile missile;
		try {
			missile = allMissiles.get(missileIndex-1);
			TzoukEitanLogger.myLogger.log(Level.INFO, "missile " + missile.getMissileId() +" chosen for interception", this);
			fireDestroyMissileEvent(missile);
		} catch (Exception e) { // the missile already exploded	
			TzoukEitanLogger.myLogger.log(Level.INFO, "missile number "+missileIndex+" is no longer in the air.", this);
		}
		
	}
	
	private void exitTheView(){
		isRunning = false;
		Handler[] handlers = TzoukEitanLogger.myLogger.getHandlers();
		for (Handler handler : handlers) {
			handler.close();
		}
	}
}
