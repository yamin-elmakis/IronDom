package IronDome.model;

import java.util.HashMap;

import IronDome.listeners.IAllWar;
import IronDome.utils.Destination;

public class Hamas {

	private HashMap<String, Launcher> missileLaunchers;
	private IAllWar allWar;
	
	public Hamas() {
		this(new HashMap<String, Launcher>());
	}

	public Hamas(HashMap<String, Launcher> missileLaunchers) {
		this.missileLaunchers = missileLaunchers; 
	}
	
	public void registerAllWar(IAllWar allWar){
		this.allWar = allWar;
	}
	
	public void addMissileLauncher(Launcher launcher){
		missileLaunchers.put(launcher.getLauncherId(), launcher);
		launcher.registerAllWar(allWar);
		launcher.start();
	}
	
	public void removeMissileLauncher(String launcherId){
		missileLaunchers.remove(launcherId);
	}	
	
	public void loadMissile(String launcherId, String missileID, int flightTime, int damage, Destination destination) {
		missileLaunchers.get(launcherId).loadMissile(missileID, flightTime, damage, destination);
	}
	
}
