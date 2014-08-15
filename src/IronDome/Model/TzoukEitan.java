package IronDome.Model;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;

import IronDome.Listeners.IAllWar;
import IronDome.Listeners.ITzoukEitanModelEventsListener;
import IronDome.Utils.Type;
import IronDome.Utils.Utils;

public class TzoukEitan implements IAllWar {

	private Hamas hamas;
	private IDF idf;
	// TODO i'm not sure if the model should hold a vector of models or views
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

	public Vector<Missile> getAllMissiles() {
		return allMissiles;
	}

	public void registerListener(ITzoukEitanModelEventsListener listener){
		listeners.add(listener);
	}
	
	public void addMissileDestructor() {
		idf.addMissileDestructor(new MissileDestructor());
	}

	public void addMissileLauncheDestructor() {
		idf.addMissileLauncherDestructor(new MissileLauncherDestructor());
	}

	public void addLauncher() {
		try {
			hamas.addMissileLauncher(new Launcher());
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void launchMissile() {
		if (allLaunchers.size() < 1){
			Utils.myLogger.log(Level.INFO, "can't launch Missile - no launchers found", this);
			return;
		}		
		String launcherId = allLaunchers.get(Utils.rand.nextInt(allLaunchers.size())).getLauncherId();		
		hamas.loadMissile(launcherId);
		
	}

	private void fireAddMissileDestructorEvent(String id){
		for (ITzoukEitanModelEventsListener listener: listeners) {
			listener.addMissileDestructor(id, null);
		}
	}
	
	private void fireAddMissileLauncheDestructorEvent(){
		for (ITzoukEitanModelEventsListener listener: listeners) {
			listener.addMissileLauncheDestructor();
		}
	}
	
	private void fireAddLauncherEvent(boolean isHidden){
		for (ITzoukEitanModelEventsListener listener: listeners) {
			listener.addLauncher(isHidden);
		}
	}
	
	private void fireAddMissileEvent(String id, String Destination){
		for (ITzoukEitanModelEventsListener listener: listeners) {
			listener.addMissile(id, Destination);
		}
	}
	
	private void fireShootMissileEvent(Missile missile){
		
	}

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
	}

	@Override
	public void unregisterLauncher(Launcher launcher) {
		allLaunchers.remove(launcher);
	}
}
