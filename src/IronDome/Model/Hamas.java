package IronDome.Model;

import java.util.HashMap;

import IronDome.Listeners.IAllWar;
import IronDome.Utils.Destination;

public class Hamas {

	private HashMap<String, Launcher> missileLaunchers;
	private IAllWar allWar;
	
	public Hamas() {
		this(new HashMap<String, Launcher>());
	}

	public Hamas(HashMap<String, Launcher> missileLaunchers) {
		this.missileLaunchers = missileLaunchers; 
	}

	public void registerAllMissiles(IAllWar allMissiles){
		this.allWar = allMissiles;
	}
	
	public void addMissileLauncher(Launcher launcher){
		launcher.start();
		missileLaunchers.put(launcher.getLauncherId(), launcher);
		launcher.registerAllMissiles(allWar);
		allWar.registerLauncher(launcher);
	}
	
	public void removeMissileLauncher(String launcherId){
		missileLaunchers.remove(launcherId);
	}	
	
	public void loadMissile(String launcherId){
		missileLaunchers.get(launcherId).loadMissile(Destination.Azor, 5);
	}
	
}
