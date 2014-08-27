package IronDome.listeners;

import IronDome.utils.Destination;
import IronDome.utils.DestructorType;

public interface ITzoukEitanViewEventsListener {

	void getMissilesList();
	void missileDestructed(String Mid);
	void launcherDestructed();
	void destroyMissile();
	void destroyMissile(String missileId);
	void destroyMissile(String missileId, String missileDestructorId, int destructAfterLaunch);
	void destroyLauncher();
	void destroyLauncher(String launcherId);
	void destroyLauncher(String launcherId, DestructorType type, int destructTime);
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
