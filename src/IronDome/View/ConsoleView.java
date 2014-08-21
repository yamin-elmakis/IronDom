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
import IronDome.Utils.Utils;

//TODO create abstract class and the console will implement it

public class ConsoleView implements ITzoukEitanView {

	public static final String LOG_FILE_NAME = "consoleViewLog.txt";
	private List<ITzoukEitanViewEventsListener> allListeners;
	private Scanner scan;
	private boolean isRunning;
	private int choice;
	private final String MAIN_MANU = "\nMENU\n-------------\npress:\n1.add Launcher.\n2.Launch missile.\n3.add Missile Launcher Destructor.\n4.add Missile Destructor.\n5.destroy Missile.\n6.destroy Launcher.\n7.show statistics.\n8.EXIT & show statistics.";
	
	public ConsoleView() throws SecurityException, IOException {
		allListeners = new LinkedList<ITzoukEitanViewEventsListener>();
		scan = new Scanner(System.in);
		isRunning = true;
		FileHandler fileHandler = new FileHandler(Utils.LOGS_FOLDER+LOG_FILE_NAME, false);
		fileHandler.setFormatter(new TzoukEitanLogFormatter());
		fileHandler.setFilter(new TzoukEitanLogFilter(this));
		ConsoleHandler consolHandler = new ConsoleHandler(); 
		consolHandler.setFormatter(new TzoukEitanLogFormatter());
		consolHandler.setFilter(new TzoukEitanLogFilter(this));
		Utils.myLogger.addHandler(fileHandler);
		Utils.myLogger.addHandler(consolHandler);
		Utils.myLogger.log(Level.INFO, "Console View Log", this);
	}

	public void runMenu(){
		while (isRunning) {
			Utils.myLogger.log(Level.INFO, MAIN_MANU, this);
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
				Utils.myLogger.log(Level.INFO, "add Launcher pressed", this);
				fireAddedLauncherEvent();
				break;
			case 2: 
				Utils.myLogger.log(Level.INFO, "launch missile pressed", this);
				fireLaunchMissileEvent();
				break;
			case 3:
				Utils.myLogger.log(Level.INFO, "add Missile Launcher Destructor Pressed", this);
				fireAddedLauncherDestructorEvent();
				break;
			case 4:
				Utils.myLogger.log(Level.INFO, "add Missile Destructor Pressed", this);
				fireAddedMissileDestructorEvent();
				break;
			case 5:
				Utils.myLogger.log(Level.INFO, "destroy Missile Pressed", this);
				fireDestroyMissileEvent();
				break;
			case 6:
				Utils.myLogger.log(Level.INFO, "destroy Launcher Pressed", this);
				break;
			case 7:
				Utils.myLogger.log(Level.INFO, "show statistics", this);
				// TODO add statistic class.
				break;
			case 8:
				Utils.myLogger.log(Level.INFO, "EXIT & show statistics ", this);
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
		// TODO Auto-generated method stub
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
	public void missileDestructed() {
		// TODO write missileDetructed function in the view
		
	}

	@Override
	public void missileFired(String id, Destination dest, int damage) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addedLauncher(Launcher launcher) {
		// TODO write addedLauncher function in the view
		Utils.myLogger.log(Level.INFO, "Launcher "+launcher.getLauncherId()+" added");
	}

	@Override
	public void addedMissileLauncherDestructor() {
		// TODO write addedMissileLauncherDestructor function in the view
		Utils.myLogger.log(Level.INFO, "add Missile Launcher Destructor", this);
	}

	@Override
	public void addedMissileDestructor() {
		// TODO write addedMissileDestructor function in the view
		Utils.myLogger.log(Level.INFO, "add Missile Destructor ", this);
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
			Utils.myLogger.log(Level.INFO, "there is mo missiles to intercepte.\n", this);
			return;
		}
		else
			text.append("choose a missile you'd like to intercepte:\n");
		for (Missile missile : allMissiles) {
			float impactTime = missile.getFlightTime() - (System.currentTimeMillis() - missile.getLaunchTime())/1000;
			text.append(String.format("%d. missile %s will impact in %.2f seconds\n", ++i, missile.getMissileId(), impactTime));
		}
		Utils.myLogger.log(Level.INFO, text.toString(), this);
		
		try {
			missileIndex = scan.nextInt();
		} catch (InputMismatchException e) {
			Utils.myLogger.log(Level.INFO, "wrong input", this);
			scan.nextLine();
			throw new InputMismatchException();
		}
		
		if (missileIndex > i+1 || missileIndex < 1){
			throw new InputMismatchException();
		}
		Missile missile = allMissiles.get(missileIndex-1);
		Utils.myLogger.log(Level.INFO, "missile " + missile.getMissileId() +" chosen for interception", this);
		fireDestroyMissileEvent(missile);
	}
	
	private void exitTheView(){
		isRunning = false;
		Handler[] handlers = Utils.myLogger.getHandlers();
		for (Handler handler : handlers) {
			handler.close();
		}
	}
}
