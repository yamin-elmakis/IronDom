package IronDome.View;

import java.util.Vector;

import IronDome.Listeners.ITzoukEitanViewEventsListener;
import IronDome.Model.Missile;
import IronDome.Utils.Destination;

public interface ITzoukEitanView {

	void registerController(ITzoukEitanViewEventsListener listener);
	void missileFired(String id, Destination dest, int damage);
	void missileDestructed();
	void addedLauncher();
	void addedMissileLauncherDestructor();
	void addedMissileDestructor();
	void showMissilelist(Vector<Missile> allMissiles);
	
}
