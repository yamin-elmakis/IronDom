package IronDome.View;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;
import java.util.logging.Level;

import IronDome.Listeners.ITzoukEitanModelEventsListener;
import IronDome.Listeners.ITzoukEitanViewEventsListener;
import IronDome.Model.Launcher;
import IronDome.Model.Missile;
import IronDome.Utils.Destination;
import IronDome.Utils.Type;
import IronDome.Utils.Utils;

//TODO create abstract class and the console will implement it

public class ConsoleView implements ITzoukEitanView{

	private List<ITzoukEitanViewEventsListener> allListeners;
	private Scanner scan;
	private boolean isRunning;
	private int choice;
	private final String MAIN_MANU = "\nMENU\n-------------\npress:\n1.add Launcher.\n2.Launch missile.\n3.add Missile Launcher Destructor.\n4.add Missile Destructor.\n5.destroy Missile.\n6.destroy Launcher ";
	
	public ConsoleView() {
		allListeners = new LinkedList<ITzoukEitanViewEventsListener>();
		scan = new Scanner(System.in);
		isRunning = true;
	}
	
	public void runMenu(){
		while (isRunning) {
			System.out.println(MAIN_MANU);
			try {
				choice = scan.nextInt();
			} catch (Exception e) {
				choice = -1;
			}
			switch (choice) {
			case 1:
				System.out.println("add Launcher Pressed");
				fireAddedLauncherEvent();
				break;
			case 2: 
				System.out.println("launch missile");
				fireLaunchMissileEvent();
				break;
			case 3:
				System.out.println("add Missile Launcher Destructor Pressed");
				fireAddedLauncherDestructorEvent();
				break;
			case 4:
				System.out.println("add Missile Destructor Pressed");
				
				fireAddedMissileDestructorEvent();
				break;
			case 5:
				System.out.println("destroy Missile Pressed");
				fireDestroyMissileEvent();
				break;
			case 6:
				System.out.println("destroy Launcher Pressed");
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
			listener.missileDestructed();
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
		System.out.println("add Missile Launcher Destructor");
	}

	@Override
	public void addedMissileDestructor() {
		// TODO write addedMissileDestructor function in the view
		System.out.println("add Missile Destructor ");
	}

	public void registerListener(ITzoukEitanViewEventsListener listener) {
		allListeners.add(listener);
	}

	@Override
	public void registerController(ITzoukEitanViewEventsListener listener) {
		allListeners.add(listener);
	}

	@Override
	public void showMissilelist(Vector<Missile> allMissiles) {
		System.out.println("choose a missile you'd like to destroy:");
		int i = 0;
		for(Missile missile : allMissiles){
			System.out.println(i++ + ". " + missile.getId());
		}
		int missileInsdex = scan.nextInt();	
	}
}
