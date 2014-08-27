package IronDome.listeners;

import IronDome.utils.DestructorType;

public interface ITzoukEitanViewEventsListener {

	void getMissilesList();
	void missileDestructed();
	void launcherDestructed();
	void destroyMissile();
	void destroyMissile(String missileId);
	void destroyMissile(String missileId, String missileDestructorId, int destructAfterLaunch);
	void destroyLauncher();
	void destroyLauncher(String launcherId);
	void destroyLauncher(String launcherId, DestructorType type, int destructTime);
	void addLauncher();
	void addLauncher(String id, boolean ishidden);
	void addMissileLauncherDestructor();
	void addMissileDestructor();
	void LaunchMissile();
}
