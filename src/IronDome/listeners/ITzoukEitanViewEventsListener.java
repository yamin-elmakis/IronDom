package IronDome.listeners;

import IronDome.model.Launcher;
import IronDome.model.Missile;
import IronDome.utils.Destination;
import IronDome.utils.DestructorType;

public interface ITzoukEitanViewEventsListener {

	void getMissilesList();
	void missileDestructed(String Mid);
	void launcherDestructed();
	void destroyMissile();
	void destroyMissile(Missile missile);
	void destroyLauncher();
	void destroyLauncher(Launcher launcher);
	void addLauncher();
	void addLauncher(String Lid, boolean ishidden);
	void addMissileLauncherDestructor();
	void addMissileDestructor();
	void addMissileDestructor(String mDid);
	void LaunchMissile();
	void LaunchMissile(String Lid, String mid, Destination destination, int launchTime,
			int flyTime, int damage);
	void addMissileLauncherDestructor(String mDid, DestructorType type);
	
}
