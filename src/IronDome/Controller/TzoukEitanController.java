package IronDome.Controller;

import java.io.IOException;

import IronDome.Listeners.ITzoukEitanModelEventsListener;
import IronDome.Listeners.ITzoukEitanViewEventsListener;
import IronDome.Model.Launcher;
import IronDome.Model.Missile;
import IronDome.Model.TzoukEitan;
import IronDome.Utils.Destination;
import IronDome.Utils.Type;
import IronDome.View.ConsoleView;
import IronDome.View.ITzoukEitanView;

public class TzoukEitanController implements ITzoukEitanModelEventsListener, ITzoukEitanViewEventsListener{
	
	private TzoukEitan tzoukEitan;
	private ITzoukEitanView consoleView;
	
	public TzoukEitanController(TzoukEitan tzoukEitan, ITzoukEitanView consoleView) {
		this.tzoukEitan = tzoukEitan;
		this.consoleView = consoleView;
		
		tzoukEitan.registerListener(this);
		consoleView.registerController(this);
	}

	@Override
	public void missileDestructed() {
		
	}
	@Override
	public void missileFired(String id, Destination dest, int damage) {
		this.consoleView.missileFired(id, dest, damage);
		
	}

	@Override
	public void addedLauncher() {
		tzoukEitan.addLauncher();
		System.out.println("controller add launcher TEST");
	}

	@Override
	public void addedMissileLauncherDestructor() {
		tzoukEitan.addMissileDestructor();
	}

	@Override
	public void addedMissileDestructor() {
		tzoukEitan.addMissileDestructor();
	}

	@Override
	public void addMissileDestructor(String id, Type type) {
		
	}

	@Override
	public void addMissileLauncheDestructor() {
		tzoukEitan.addMissileLauncheDestructor();
	}

	// TODO write the method addLauncher
	@Override
	public void addLauncher(Launcher launcher) {
		consoleView.addedLauncher(launcher);
	}
	
	@Override
	// TODO write the method addMissile
	public void addMissile(String id, String Destination) {
		
	}
	@Override
	public void shootMissile(Missile missile) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void getMissilesList() {
		consoleView.showMissilelist(tzoukEitan.getAllMissiles());
	}

	@Override
	public void LaunchMissile() {
		//TODO yamin - take care of the launch missiles.. thanks :)
		try {
			tzoukEitan.launchMissile();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
