package IronDome.View;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import IronDome.Listeners.ITzoukEitanModelEventsListener;
import IronDome.Listeners.ITzoukEitanViewEventsListener;
import IronDome.Model.Missile;
import IronDome.Utils.Destination;
import IronDome.Utils.Utils;

//TODO create abstract class and the console will implement it

public class ConsoleView implements ITzoukEitanView{

	private List<ITzoukEitanViewEventsListener> allListeners;
	private Scanner scan;
	private boolean isRunning;
	private int choice;
	private final String MAIN_MANU = "\nMENU\n-------------\npress:\n1.add Launcher.\n2.add missile.\n3.add Missile Launcher Destructor.\n4.add Missile Destructor.\n5.destroy Missile.\n6.destroy Launcher ";
	
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
				System.out.println("add Launcher");
				//TODO add logger, add consolefile handler

				fireAddedLauncherPressed();
				break;
			case 2:
				System.out.println("add missile");
				break;
			case 3:
				System.out.println("add Missile Launcher Destructor");
				break;
			case 4:
				System.out.println("add Missile Destructor");
				break;
			case 5:
				System.out.println("destroy Missile");
				break;
			case 6:
				System.out.println("destroy Launcher");
				break;
			default:
				break;
			}
		}
	}
	

	@Override
	public void missileDetructed(String id) {
		// TODO write missileDetructed function in the view
		
	}

	@Override
	public void missileFired(String id, Destination dest, int damage) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addedLauncher() {
		// TODO write addedLauncher function in the view
		System.out.println("addedLauncher");
	}

	public void fireAddedLauncherPressed(){
		//TODO add parameters for adding launcher
		for (ITzoukEitanViewEventsListener listener : allListeners) {
//			listener.
		}
	}
	@Override
	public void addedMissileLauncherDestructor() {
		// TODO write addedMissileLauncherDestructor function in the view
		
	}

	@Override
	public void addedMissileDestructor() {
		// TODO write addedMissileDestructor function in the view
		
	}

	public void registerListener(ITzoukEitanViewEventsListener listener) {
		allListeners.add(listener);
	}

	@Override
	public void registerController(ITzoukEitanViewEventsListener listener) {
		allListeners.add(listener);
	}





}
