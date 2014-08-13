package IronDome.Controller;

import IronDome.Listeners.ITzoukEitanModelEventsListener;
import IronDome.Listeners.ITzoukEitanViewEventsListener;
import IronDome.Model.Missile;
import IronDome.Model.TzoukEitan;
import IronDome.Utils.Destination;
import IronDome.View.ConsoleView;
import IronDome.View.ITzoukEitanView;

public class TzoukEitanController implements ITzoukEitanModelEventsListener, ITzoukEitanViewEventsListener{
	
	private TzoukEitan tzoukEitan;
	private ITzoukEitanView consoleView;
	
	public TzoukEitanController(TzoukEitan tzoukEitan, ITzoukEitanView consoleView) {
		this.tzoukEitan = tzoukEitan;
		this.consoleView = consoleView;
		
		tzoukEitan.registerListener(this);
		consoleView.registerController(this);
	}

	@Override
	public void missileDetructed(String id) {
		
	}
	@Override
	public void missileFired(String id, Destination dest, int damage) {
		this.consoleView.missileFired(id, dest, damage);
		
	}

	@Override
	public void addedLauncher() {
		
	}

	@Override
	public void addedMissileLauncherDestructor() {
		
	}

	@Override
	public void addedMissileDestructor() {
		
	}

	@Override
	public void addMissileDestructor(String id) {
		
	}

	@Override
	public void addMissileLauncheDestructor(String id, String type) {
		
	}

	// TODO write the method addLauncher
	@Override
	public void addLauncher(boolean isHidden) {
		
	}

	@Override
	// TODO write the method addMissile
	public void addMissile(String id, String Destination) {
		
	}


	@Override
	public void shootMissile(Missile missile) {
		// TODO Auto-generated method stub
		
	}



}
