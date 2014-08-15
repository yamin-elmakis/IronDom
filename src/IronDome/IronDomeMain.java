package IronDome;

import java.io.IOException;
import java.util.ArrayDeque;

import IronDome.Controller.TzoukEitanController;
import IronDome.Listeners.ITzoukEitanViewEventsListener;
import IronDome.Model.Hamas;
import IronDome.Model.Launcher;
import IronDome.Model.Missile;
import IronDome.Model.TzoukEitan;
import IronDome.Utils.Utils;
import IronDome.View.ConsoleView;
import IronDome.View.ITzoukEitanView;

public class IronDomeMain {

	public static void main(String[] args) {
		// the model
		TzoukEitan tzoukEitan = new TzoukEitan();
		
		//the view
		ConsoleView tzoukEitanView = new ConsoleView();
		
		// the controller
		TzoukEitanController tzoukEitanController = new TzoukEitanController(tzoukEitan, tzoukEitanView);
		
//		tzoukEitanView.runMenu();
		
		// tester
//		try {
//			tzoukEitan.addLauncher(true);
//			tzoukEitan.launchMissile();
//			tzoukEitan.launchMissile();

//			hamas.addMissileLauncher(new Launcher("L101", true, new ArrayDeque<Missile>()));
//			System.out.println("after add Missile Launcher");
//			hamas.loadMissile("L101");
//			hamas.loadMissile("L101");
//			hamas.
//		} catch (SecurityException e) {
//			System.out.println("SecurityException");
//			e.printStackTrace();
//		} catch (IOException e) {
//			System.out.println("IOException");
//			e.printStackTrace();
//		}
	}

}
