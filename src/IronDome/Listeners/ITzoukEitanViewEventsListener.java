package IronDome.Listeners;

import IronDome.Model.Missile;

public interface ITzoukEitanViewEventsListener {

	void getMissilesList();
	void missileDestructed();
	void launcherDestructed();
	void destroyMissile();
	void destroyMissile(Missile missile);
	void addLauncher();
	void addMissileLauncherDestructor();
	void addMissileDestructor();
	void LaunchMissile();
}
