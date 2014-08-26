package IronDome.controller;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.InputMismatchException;

import sun.launcher.resources.launcher;
import IronDome.listeners.ITzoukEitanModelEventsListener;
import IronDome.listeners.ITzoukEitanViewEventsListener;
import IronDome.model.Interceptor;
import IronDome.model.Launcher;
import IronDome.model.Missile;
import IronDome.model.MissileDestructor;
import IronDome.model.MissileLauncherDestructor;
import IronDome.model.TzoukEitan;
import IronDome.utils.Destination;
import IronDome.utils.DestructorType;
import IronDome.utils.Utils;
import IronDome.view.ConsoleView;
import IronDome.view.ITzoukEitanView;

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
	public void missileLauncheDestructorAdded(String id, DestructorType type) {
		
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
		String MldID = MissileLauncherDestructor.generateLauncherDestructorID();
		DestructorType type = DestructorType.values()[Utils.rand.nextInt(DestructorType.values().length)];
		tzoukEitan.addMissileLauncheDestructor(MldID, type);
	}

	@Override
	public void addMissileDestructor() {
		String missileDestructorId = MissileDestructor.generateMissileDestructorId();
		ArrayDeque<Interceptor> interceptors = new ArrayDeque<Interceptor>();
		tzoukEitan.addMissileDestructor(missileDestructorId, interceptors);
	}

	@Override
	public void LaunchMissile() {
		String missileID = Missile.generateMissileId();
		int flightTime = Utils.rand.nextInt(10) + 27;
		int damage = Utils.rand.nextInt(5000) + 5000;
		Destination destination = Destination.values()[Utils.rand.nextInt(Destination.values().length)];
		tzoukEitan.launchMissile(missileID, flightTime, damage, destination);
	}

}
