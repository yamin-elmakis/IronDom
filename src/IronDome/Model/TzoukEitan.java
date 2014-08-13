package IronDome.Model;

import java.util.Vector;

import IronDome.Listeners.ITzoukEitanModelEventsListener;
import IronDome.Listeners.ITzoukEitanViewEventsListener;

public class TzoukEitan {

	private Hamas hamas;
	private IDF idf;
	private Vector<ITzoukEitanModelEventsListener> listeners;
	
	public TzoukEitan() {
		// init the Hamas and IDF
	}
	
	
	public void registerListener(ITzoukEitanModelEventsListener listener){
		listeners.add(listener);
	}
	
	public void addMissileDestructor(String id) {
//		idf.addMissileDestructor(id);
	}

	public void addMissileLauncheDestructor(String id, String type) {
//		idf.addMissileLauncheDestructor(id, type);
	}

	public void addLauncher(boolean isHidden) {
//		hamas.addLauncher(isHidden);
	}

	public void launchMissile() {
		String launcherId = null;
		
		hamas.launchMissile(launcherId);
	}

	private void fireAddMissileDestructorEvent(String id){
		for (ITzoukEitanModelEventsListener listener: listeners) {
			listener.addMissileDestructor(id);
		}
	}
	
	private void fireAddMissileLauncheDestructorEvent(String id, String type){
		for (ITzoukEitanModelEventsListener listener: listeners) {
			listener.addMissileLauncheDestructor(id, type);
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

}
