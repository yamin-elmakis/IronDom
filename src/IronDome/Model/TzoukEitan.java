package IronDome.Model;

import java.util.Vector;
import java.util.logging.Level;

import IronDome.Listeners.IAllWar;
import IronDome.Listeners.ITzoukEitanModelEventsListener;
import IronDome.Utils.Destination;
import IronDome.Utils.Utils;

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
	
	public void addMissileDestructor() {
		idf.addMissileDestructor(new MissileDestructor());
	}

	public void addMissileLauncheDestructor() {
		idf.addMissileLauncherDestructor(new MissileLauncherDestructor());
	}
	
	public void interceptMissile(Missile missile){
		if (allMissiles.contains(missile)){
			idf.destroyMissile(missile);
		}
		else{
			
		}
	}
	
	

	public void addLauncher() {
		hamas.addMissileLauncher(new Launcher());
	}
	public void addLauncher(String id, boolean isHidden) {
		hamas.addMissileLauncher(new Launcher(id, isHidden));
	}

	public void launchMissile(String missileID, int flightTime, int damage, Destination destination) {
		if (allLaunchers.size() < 1){
			Utils.myLogger.log(Level.INFO, "can't launch Missile - no launchers found");
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

	private void fireAddMissileDestructorEvent(String id){
		for (ITzoukEitanModelEventsListener listener: listeners) {
			listener.missileDestructorAdded(id, null);
		}
	}
	
	private void fireAddMissileLauncheDestructorEvent(){
		for (ITzoukEitanModelEventsListener listener: listeners) {
			listener.missileLauncheDestructorAdded();
		}
	}
	
	private void fireAddLauncherEvent(Launcher launcher){
		for (ITzoukEitanModelEventsListener listener: listeners) {
			listener.launcherAdded(launcher);
		}
	}
	
	private void fireAddMissileEvent(String id, String Destination){
		for (ITzoukEitanModelEventsListener listener: listeners) {
			listener.missileAdded(id, Destination);
		}
	}
	
	private void fireShootMissileEvent(Missile missile){
		
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
		fireAddLauncherEvent(launcher);
	}

	@Override
	public void unregisterLauncher(Launcher launcher) {
		allLaunchers.remove(launcher);
	}
}
