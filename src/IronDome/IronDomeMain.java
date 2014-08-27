package IronDome;

import IronDome.controller.TzoukEitanController;

import java.io.IOException;

import IronDome.model.TzoukEitan;
import IronDome.view.ConsoleView;

public class IronDomeMain {

	public static void main(String[] args) throws SecurityException, IOException {
		// the model
		TzoukEitan tzoukEitan = new TzoukEitan();
		
		//the view
		ConsoleView tzoukEitanView = new ConsoleView();
		
		// the controller
		TzoukEitanController tzoukEitanController = new TzoukEitanController(tzoukEitan, tzoukEitanView);
		
		System.exit(0);
		// tester
		/*
		try {
			tzoukEitan.addLauncher();
			tzoukEitan.launchMissile();
//			tzoukEitan.launchMissile();
			tzoukEitan.addLauncher();
			tzoukEitan.launchMissile();
			tzoukEitan.launchMissile();
			tzoukEitan.launchMissile();

//			
		} catch (SecurityException e) {
			System.out.println("SecurityException");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		*/
	}

}
