package IronDome.model;

import java.util.ArrayDeque;
import java.util.Vector;
import java.util.logging.Level;

import IronDome.listeners.IAllWar;
import IronDome.listeners.ITzoukEitanModelEventsListener;
import IronDome.utils.Destination;
import IronDome.utils.DestructorType;
import IronDome.utils.TzoukEitanLogger;
import IronDome.utils.Utils;

public class TzoukEitan implements IAllWar {

	private Hamas hamas;
	private IDF idf;
	private Vector<ITzoukEitanModelEventsListener> listeners;
	private Vector<Missile> allMissiles;
	private Vector<Launcher> allLaunchers;
	 
	public TzoukEitan() {
		// init the Hamas and IDF
		hamas = new Hamas();
		idf = new IDF();
		listeners = new Vector<ITzoukEitanModelEventsListener>();
		allMissiles = new Vector<Missile>();
		allLaunchers = new Vector<Launcher>(); 
		
		hamas.registerAllMissiles(this);
	}

	////////////////////////////////////////////
	//////////// work the model  ///////////////
	////////////////////////////////////////////
	public void registerListener(ITzoukEitanModelEventsListener listener){
		listeners.add(listener);
	}
	
	public void addMissileDestructor(String missileDestructorId, ArrayDeque<Interceptor> interceptors) {
		idf.addMissileDestructor(missileDestructorId, interceptors);
	}

	public void addMissileLauncheDestructor(String MldID, DestructorType type) {
		idf.addMissileLauncherDestructor(MldID, type);
	}
	
	public void interceptMissile(Missile missile){
		if (allMissiles.contains(missile)){
//			System.out.println("TzoukEitan: interceptMissile: contain");
			idf.destroyMissile(missile);
		}
		else{
			TzoukEitanLogger.myLogger.log(Level.INFO, "the missile "+missile.getMissileId()+" is not in the air.");
		}
	}

	public void addLauncher(String id, boolean isHidden) {
		hamas.addMissileLauncher(new Launcher(id, isHidden));
	}

	public void launchMissile(String missileID, int flightTime, int damage, Destination destination) {
		if (allLaunchers.size() < 1){
			TzoukEitanLogger.myLogger.log(Level.INFO, "can't launch Missile - no launchers found");
			return;
		}	
		String launcherId = allLaunchers.get(Utils.rand.nextInt(allLaunchers.size())).getLauncherId();		
		launchMissile(launcherId, missileID, flightTime, damage, destination);
	}
	
	public void launchMissile(String launcherId, String missileID, int flightTime, int damage, Destination destination) {
		if (allLaunchers.contains(new Launcher(launcherId))){
			hamas.loadMissile(launcherId, missileID, flightTime, damage, destination);
		}				
	}
	
	////////////////////////////////////////////
	/////////// notify the controller  /////////
	////////////////////////////////////////////
	public Vector<Missile> getAllMissiles() {
		return allMissiles;
	}

	public Vector<Launcher> getAllLaunchers() {
		return allLaunchers;
	}
	
	private void fireAddMissileDestructorEvent(String id){
		for (ITzoukEitanModelEventsListener listener: listeners) {
			listener.missileDestructorAdded(id);
		}
	}
	
	private void fireAddMissileLauncheDestructorEvent(String id, DestructorType type){
		for (ITzoukEitanModelEventsListener listener: listeners) {
			listener.missileLauncheDestructorAdded(id, type);
		}
	}
	
	private void fireAddLauncherEvent(String id){
		for (ITzoukEitanModelEventsListener listener: listeners) {
			listener.launcherAdded(id);
		}
	}
	
	private void fireMissilefiredEvent(String id, Destination Destination, int damage){
		for (ITzoukEitanModelEventsListener listener: listeners) {
			listener.missileFired(id, Destination, damage);
		}
	}

	////////////////////////////////////////////
	/////////// all war interface  /////////////
	////////////////////////////////////////////
	@Override
	public void registerMissile(Missile missile) {
		allMissiles.add(missile);		
	}

	@Override
	public void unregisterMissile(Missile missile) {
		allMissiles.remove(missile);
	}

	@Override
	public void registerLauncher(Launcher launcher) {
		allLaunchers.add(launcher);
		fireAddLauncherEvent(launcher.getLauncherId());
	}

	@Override
	public void unregisterLauncher(Launcher launcher) {
		allLaunchers.remove(launcher);
	}
}
