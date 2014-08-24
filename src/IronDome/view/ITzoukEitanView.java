package IronDome.view;

import java.util.Vector;

import IronDome.listeners.ITzoukEitanViewEventsListener;
import IronDome.model.Launcher;
import IronDome.model.Missile;
import IronDome.utils.Destination;

public interface ITzoukEitanView {

	void registerController(ITzoukEitanViewEventsListener listener);
	void missileFired(String id, Destination dest, int damage);
	void missileDestructed(String id);
	void addedLauncher(String id);
	void addedMissileLauncherDestructor(String id);
	void addedMissileDestructor(String id);
	void showMissilelist(Vector<Missile> allMissiles);
}
