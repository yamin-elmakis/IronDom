package IronDome.model;

import java.util.ArrayDeque;
import java.util.Vector;
import java.util.logging.Level;

import IronDome.listeners.IAllWar;
import IronDome.listeners.ITzoukEitanModelEventsListener;
import IronDome.utils.ComponentStatus;
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
		
		hamas.registerAllWar(this);
		idf.registerAllWar(this);
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
	
	public void interceptMissile(String missileId){
		Missile temp = new Missile(missileId);
		if (allMissiles.contains(temp)){
			Missile missile = allMissiles.get(allMissiles.indexOf(temp));
//			System.out.println("TzoukEitan: interceptMissile: contain");
			idf.destroyMissile(missile);
		}
		else{
			TzoukEitanLogger.myLogger.log(Level.INFO, "the missile "+missileId+" is not in the air.");
		}
	}

	public void destroyLauncher(String launcherId) {
		Launcher temp = new Launcher(launcherId);
		if (allLaunchers.contains(temp)){
			Launcher launcher = allLaunchers.get(allLaunchers.indexOf(temp));
			idf.destroyLauncher(launcher);
		}
		else{
			TzoukEitanLogger.myLogger.log(Level.INFO, "the launcher " +launcherId+ " is already destroyed.");
		}
	}
	
	public void addLauncher(String id, boolean isHidden) {
		hamas.addMissileLauncher(new Launcher(id, isHidden));
	}

	public void launchMissile(int flightTime, int damage, Destination destination) {
		if (allLaunchers.size() < 1){
			TzoukEitanLogger.myLogger.log(Level.INFO, "can't launch Missile - no launchers found");
			return;
		}	
		String launcherId = allLaunchers.get(Utils.rand.nextInt(allLaunchers.size())).getLauncherId();
		String missileID = Missile.generateMissileId();
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
	
	private void fireMissileDestructorJoinedEvent(String id){
		for (ITzoukEitanModelEventsListener listener: listeners) {
			listener.missileDestructorAdded(id);
		}
	}
	
	private void fireMissileLauncheDestructorJoinedEvent(String id, DestructorType type){
		for (ITzoukEitanModelEventsListener listener: listeners) {
			listener.missileLauncheDestructorAdded(id, type);
		}
	}
	
	private void fireAddLauncherEvent(String launcherId){
		for (ITzoukEitanModelEventsListener listener: listeners) {
			listener.launcherAdded(launcherId);
		}
	}
	
	private void fireLauncherDestroyedEvent(String mldId, String launcherId){
		for (ITzoukEitanModelEventsListener listener: listeners) {
			listener.LauncherDestroyed(mldId, launcherId);
		}
	}
	
	private void fireMissilefiredEvent(String id, Destination Destination, int damage){
		for (ITzoukEitanModelEventsListener listener: listeners) {
			listener.missileFired(id, Destination, damage);
		}
	}
	
	private void fireMissileInterceptedEvent(String missileId){
		for (ITzoukEitanModelEventsListener listener: listeners) {
			listener.missileIntercepted(missileId);
		}
	}
	
	private void fireMissileExplodedEvent(String missileId, Destination dest, int damage){
		for (ITzoukEitanModelEventsListener listener: listeners) {
			listener.missileExploded(missileId, dest, damage);
		}
	}

	////////////////////////////////////////////
	/////////// all war interface  /////////////
	////////////////////////////////////////////
	@Override
	public void missileNotification(Missile missile, ComponentStatus status) {
		switch (status) {
		case launched:
			allMissiles.add(missile);
			fireMissilefiredEvent(missile.getMissileId(), missile.getDestination(), missile.getDamage());
			break;
		case hit:
			allMissiles.remove(missile);
			fireMissileExplodedEvent(missile.getMissileId(), missile.getDestination(), missile.getDamage());	
			break;
		case miss:
			allMissiles.remove(missile);
			fireMissileInterceptedEvent(missile.getMissileId());
			break;
		default:
			break;
		}
		
	}
	
	@Override
	public void registerLauncher(Launcher launcher) {
		allLaunchers.add(launcher);
		fireAddLauncherEvent(launcher.getLauncherId());
	}

	@Override
	public void missileDestructorJoined(String mdId) {
		fireMissileDestructorJoinedEvent(mdId);
	}

	@Override
	public void missileLauncherDestructorJoined(String mldId, DestructorType type) {
		fireMissileLauncheDestructorJoinedEvent(mldId, type);
	}

	@Override
	public void launcherDestroyed(String mldId, Launcher launcher) {
		allLaunchers.remove(launcher);
		fireLauncherDestroyedEvent(mldId, launcher.getLauncherId());
	}
}
