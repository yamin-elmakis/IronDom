package IronDome.View;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
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

public class ConsoleView implements ITzoukEitanView{

	private List<ITzoukEitanViewEventsListener> allListeners;
	private Scanner scan;
	private boolean isRunning;
	private int choice;
	private final String MAIN_MANU = "\nMENU\n-------------\npress:\n1.add Launcher.\n2.Launch missile.\n3.add Missile Launcher Destructor.\n4.add Missile Destructor.\n5.destroy Missile.\n6.destroy Launcher.\n7.show statistics & EXIT ";
	
	public ConsoleView() throws SecurityException, IOException {
		allListeners = new LinkedList<ITzoukEitanViewEventsListener>();
		scan = new Scanner(System.in);
		isRunning = true;
		FileHandler fileHandler = new FileHandler("consoleViewLog.txt", false);
		fileHandler.setFormatter(new TzoukEitanLogFormatter());
		fileHandler.setFilter(new TzoukEitanLogFilter(this));
		ConsoleHandler consolHandler = new ConsoleHandler(); 
		consolHandler.setFormatter(new TzoukEitanLogFormatter());
		consolHandler.setFilter(new TzoukEitanLogFilter(this));
		Utils.myLogger.addHandler(fileHandler);
		Utils.myLogger.addHandler(consolHandler);
		Utils.myLogger.log(Level.INFO, "Console View Log", this);
	}
	// TODO switch all the syso to logger-log with "this" parameter in the end 
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
				Utils.myLogger.log(Level.INFO, "show statistics and EXIT", this);
				// TODO add statistic class.
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

	private void fireAddedMissileDestructorEvent() {
		for (ITzoukEitanViewEventsListener listener : allListeners) {
			listener.addedMissileDestructor();
		}
	}

	private void fireAddedLauncherDestructorEvent() {
		for (ITzoukEitanViewEventsListener listener : allListeners) {
			listener.addedMissileLauncherDestructor();
		}
	}

	private void fireAddedLauncherEvent(){
		//TODO add parameters for adding launcher
		for (ITzoukEitanViewEventsListener listener : allListeners) {
			listener.addedLauncher();
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
		int missileInsdex;
		int BUFFER_SIZE = 512;
		StringBuffer text = new StringBuffer(BUFFER_SIZE);
		// build the missile id menu 
		text.append("choose a missile you'd like to intercepte:\n");
		for (Missile missile : allMissiles) {
			text.append(++i + ". " + missile.getMissileId()+"\n");
		}
		Utils.myLogger.log(Level.INFO, text.toString(), this);
		
		try {
			missileInsdex = scan.nextInt();
		} catch (InputMismatchException e) {
			Utils.myLogger.log(Level.INFO, "wrong input", this);
			scan.nextLine();
			throw new InputMismatchException();
		}
		if (missileInsdex > i+1 || missileInsdex < 1){
			throw new InputMismatchException();
		}
			
	}
	
	private void exitTheView(){
		isRunning = false;
	}
}
