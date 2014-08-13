package IronDome.View;

import java.util.LinkedList;
import java.util.List;

import IronDome.Listeners.ITzoukEitanModelEventsListener;
import IronDome.Listeners.ITzoukEitanViewEventsListener;
import IronDome.Model.Missile;
import IronDome.Utils.Destination;
import IronDome.Utils.Utils;

public class ConsoleView implements ITzoukEitanView{

	private List<ITzoukEitanViewEventsListener> allListeners;
	
	public ConsoleView() {
		allListeners = new LinkedList<ITzoukEitanViewEventsListener>();
	}
	

	@Override
	public void missileDetructed(String id) {
		// TODO write missileDetructed function in the view
		
	}

	@Override
	public void missileFired(String id, Destination dest, int damage) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addedLauncher() {
		// TODO write addedLauncher function in the view
		System.out.println("addedLauncher");
	}

	public void fireAddedLauncherPressed(){
		for (ITzoukEitanViewEventsListener listener : allListeners) {
//			listener.
		}
	}
	@Override
	public void addedMissileLauncherDestructor() {
		// TODO write addedMissileLauncherDestructor function in the view
		
	}

	@Override
	public void addedMissileDestructor() {
		// TODO write addedMissileDestructor function in the view
		
	}

	public void registerListener(ITzoukEitanViewEventsListener listener) {
		allListeners.add(listener);
	}

	@Override
	public void registerController(ITzoukEitanViewEventsListener listener) {
		allListeners.add(listener);
	}





}
