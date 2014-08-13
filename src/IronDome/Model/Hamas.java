package IronDome.Model;

import java.util.ArrayList;
import java.util.HashMap;

public class Hamas {

	HashMap<String, Launcher> missileLaunchers;

	
	public Hamas() {
		missileLaunchers = new HashMap<String, Launcher>();
	}

	public Hamas(HashMap<String, Launcher> missileLaunchers) {
		this.missileLaunchers = missileLaunchers;
	}
	
	public void addMissileLauncher(Launcher launcher){
		missileLaunchers.put(launcher.getLauncherId(), launcher);
	}
	
	public void removeMissileLauncher(String launcherId){
		missileLaunchers.remove(launcherId);
	}	
	
	public void launchMissile(String launcherId){
		missileLaunchers.get(launcherId).setCanIShoot(true);
	}
	
}
