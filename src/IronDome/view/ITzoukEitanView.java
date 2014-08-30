package IronDome.view;

import java.util.Vector;

import IronDome.listeners.ITzoukEitanViewEventsListener;
import IronDome.model.Launcher;
import IronDome.model.Missile;
import IronDome.utils.Destination;
import IronDome.utils.DestructorType;

public interface ITzoukEitanView {

	void registerController(ITzoukEitanViewEventsListener listener);
	void missileFired(String id, Destination dest, int damage);
	void missileDestructed(String missileId);
	void missileExploded (String missileId, Destination dest, int damage);
	void interceptionFailed(String DestructorId, String targetId);
	void addedLauncher(String launcherId);
	void launcherDestroyed(String launcherId);
	void addedMissileLauncherDestructor(DestructorType type);
	void addedMissileDestructor(String id);
	void showMissilelist(Vector<Missile> allMissiles);
	void showLaunchersList(Vector<Launcher> allLaunchers);
	void notifyUser(String text);
	void showStatistics(String statistics);
}
