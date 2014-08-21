package IronDome.Listeners;

import IronDome.Model.Missile;

public interface ITzoukEitanViewEventsListener {

	void getMissilesList();
	void missileDestructed();
	void launcherDestructed();
	void destroyMissile();
	void destroyMissile(Missile missile);
	void addLauncher();
	void addLauncher(String id, boolean ishidden);
	void addMissileLauncherDestructor();
	void addMissileDestructor();
	void LaunchMissile();
}
