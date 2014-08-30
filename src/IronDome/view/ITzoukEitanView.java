package IronDome.view;

import java.util.Vector;

import IronDome.listeners.ITzoukEitanViewEventsListener;
import IronDome.model.Launcher;
import IronDome.model.Missile;

public interface ITzoukEitanView {

	void registerController(ITzoukEitanViewEventsListener listener);
	void missileFired(String id, String dest, int damage);
	void missileDestructed(String missileId);
	void missileExploded (String missileId, String dest, int damage);
	void interceptionFailed(String DestructorId, String targetId);
	void addedLauncher(String launcherId);
	void launcherDestroyed(String mldId, String launcherId);
	void addedMissileLauncherDestructor(String type);
	void addedMissileDestructor(String id);
	void showMissilelist(Vector<Missile> allMissiles);
	void showLaunchersList(Vector<Launcher> allLaunchers);
	void notifyUser(String text);
	void showStatistics(String statistics);
	void runMenu();
}
