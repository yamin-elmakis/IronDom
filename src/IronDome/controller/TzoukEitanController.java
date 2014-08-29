package IronDome.controller;

import java.util.ArrayDeque;
import java.util.InputMismatchException;

import IronDome.listeners.ITzoukEitanModelEventsListener;
import IronDome.listeners.ITzoukEitanViewEventsListener;
import IronDome.model.Interceptor;
import IronDome.model.Launcher;
import IronDome.model.MissileDestructor;
import IronDome.model.MissileLauncherDestructor;
import IronDome.model.TzoukEitan;
import IronDome.utils.Destination;
import IronDome.utils.DestructorType;
import IronDome.utils.Utils;
import IronDome.view.ITzoukEitanView;

public class TzoukEitanController implements ITzoukEitanModelEventsListener, ITzoukEitanViewEventsListener{
	
	private TzoukEitan tzoukEitan;
	private ITzoukEitanView consoleView;
	
	public TzoukEitanController(TzoukEitan tzoukEitan, ITzoukEitanView consoleView) {
		this.tzoukEitan = tzoukEitan;
		this.consoleView = consoleView;
		
		this.tzoukEitan.registerListener(this);
		this.consoleView.registerController(this);
	}

	////////////////////////////////////////////
	////////////// notify the view  ////////////
	////////////////////////////////////////////
	@Override
	public void notifyUser(String text) {
		consoleView.notifyUser(text);
	}
	
	@Override
	public void showStatistics() {
		consoleView.showStatistics(tzoukEitan.showStatistics());
	}

	/** get all the missiles that currently in the air and sent them to the view.
	 * if InputMismatchException thrown then send the view an updated data.  */
	@Override
	public void destroyMissile() {
		try {
			consoleView.showMissilelist(tzoukEitan.getAllMissiles());
		} catch (InputMismatchException e) {
			destroyMissile();
		}
	}

	/** get all the missiles that currently in the air and sent them to the view.
	 * if InputMismatchException thrown then send the view an updated data.  */
	@Override
	public void destroyLauncher() {
		try {
			consoleView.showLaunchersList(tzoukEitan.getAllLaunchers());
		} catch (InputMismatchException e) {
			destroyLauncher();
		}
	}

	@Override
	public void LauncherDestroyed(String launcherId) {
		consoleView.launcherDestroyed(launcherId);
	}

	@Override
	public void launcherAdded(String launcherId) {
		consoleView.addedLauncher(launcherId);
	}

	@Override
	public void missileFired(String id, Destination dest, int damage) {
		consoleView.missileFired(id, dest, damage);	
	}

	@Override
	public void missileIntercepted(String missileId) {
		consoleView.missileDestructed(missileId);
	}

	@Override
	public void interceptionFailed(String InterceptorId, String targetId) {
		consoleView.interceptionFailed(InterceptorId, targetId);
	}

	@Override
	public void missileExploded(String missileId, Destination dest, int damage) {
		consoleView.missileExploded(missileId, dest, damage);
	}

	@Override
	public void missileDestructorAdded(String id) {
		consoleView.addedMissileDestructor(id);
	}

	@Override
	public void missileLauncheDestructorAdded(String id, DestructorType type) {
		consoleView.addedMissileLauncherDestructor(id, type);
	}

	@Override
	public void getMissilesList() {
		consoleView.showMissilelist(tzoukEitan.getAllMissiles());
	}

	////////////////////////////////////////////
	////////////// notify the model  ///////////
	////////////////////////////////////////////	
	@Override
	public void destroyMissile(String missileId) {
		tzoukEitan.interceptMissile(missileId);
	}


	@Override
	public void destroyMissile(String missileId, String missileDestructorId, int destructAfterLaunch) {
		// TODO write destroyMissile
		
	}

	@Override
	public void destroyLauncher(String launcherId) {
		tzoukEitan.destroyLauncher(launcherId);
	}


	@Override
	public void destroyLauncher(String launcherId, DestructorType type, int destructTime) {
		// TODO write destroyLauncher
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
		int flightTime = Utils.missileLaunchTime();
		int damage = Utils.missileDamage();
		Destination destination = Utils.missileDestination();
		tzoukEitan.launchMissile(flightTime, damage, destination);
	}

	@Override
	public void LaunchMissile(String Lid, String mid, Destination destination, int launchTime, int flyTime, int damage) {
		tzoukEitan.launchMissile(Lid, mid, flyTime, damage, destination);
	}

	@Override
	public void addMissileDestructor(String mDid) {
		tzoukEitan.addMissileDestructor(mDid, new ArrayDeque<Interceptor>());
	}

	@Override
	public void addMissileLauncherDestructor(String mDid, DestructorType type) {
		tzoukEitan.addMissileLauncheDestructor(mDid, type);
	}
}
