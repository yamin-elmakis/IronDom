package IronDome.Controller;

import java.io.IOException;
import java.util.InputMismatchException;

import sun.launcher.resources.launcher;
import IronDome.Listeners.ITzoukEitanModelEventsListener;
import IronDome.Listeners.ITzoukEitanViewEventsListener;
import IronDome.Model.Launcher;
import IronDome.Model.Missile;
import IronDome.Model.TzoukEitan;
import IronDome.Utils.Destination;
import IronDome.Utils.Type;
import IronDome.Utils.Utils;
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
	public void destroyMissile() {
		try {
			consoleView.showMissilelist(tzoukEitan.getAllMissiles());
		} catch (InputMismatchException e) {
			destroyMissile();
		}
	}

	////////////////////////////////////////////
	////////////// notify the view  ////////////
	////////////////////////////////////////////
	@Override
	public void missileDestructed() {
		// TODO write missileDestructed
	}
	
	@Override
	public void launcherDestructed() {
		// TODO write launcherDestructed
		
	}

	@Override
	public void launcherAdded(String id) {
		consoleView.addedLauncher(id);
	}

	@Override
	// TODO write the event missile Added to view
	public void missileAdded(String id, String Destination) {
		
	}

	@Override
	public void missileFired(String id, Destination dest, int damage) {
		consoleView.missileFired(id, dest, damage);	
	}

	@Override
	public void missileDestructorAdded(String id) {
		
	}

	@Override
	public void missileLauncheDestructorAdded(String id, Type type) {
		
	}

	@Override
	public void getMissilesList() {
		consoleView.showMissilelist(tzoukEitan.getAllMissiles());
	}

	////////////////////////////////////////////
	////////////// notify the model  ///////////
	////////////////////////////////////////////
	@Override
	public void destroyMissile(Missile missile) {
		tzoukEitan.interceptMissile(missile);
	}
	
	@Override
	public void addLauncher() {
		addLauncher(Launcher.generateLauncherID(), Utils.rand.nextBoolean());
	}
	@Override
	public void addLauncher(String id, boolean ishidden) {
		tzoukEitan.addLauncher(id, ishidden);
	}

	@Override
	public void addMissileLauncherDestructor() {
		tzoukEitan.addMissileLauncheDestructor();
	}

	@Override
	public void addMissileDestructor() {
		tzoukEitan.addMissileDestructor();
	}

	@Override
	public void LaunchMissile() {
		String missileID = Missile.generateMissileId();
		int flightTime = Utils.rand.nextInt(10) + 7;
		int damage = Utils.rand.nextInt(5000) + 7000;
		Destination destination = Destination.values()[Utils.rand.nextInt(Destination.values().length)];
		tzoukEitan.launchMissile(missileID, flightTime, damage, destination);
	}


	
}
