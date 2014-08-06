package IronDome.Model;

import java.util.ArrayList;

public class Hamas {

	ArrayList<Launcher> missileLaunchers;

	public Hamas(ArrayList<Launcher> missileLaunchers) {
		this.missileLaunchers = missileLaunchers;
	}
	
	public void addMissileLauncher(Launcher launcher){
		missileLaunchers.add(launcher);
	}
	
	
}
