package IronDome.Listeners;

import IronDome.Utils.Destination;

public interface ITzoukEitanViewEventsListener {

	void missileFired(String id, Destination dest, int damage);
	void getMissilesList();
	void missileDestructed();
	void destroyMissile();
	void destroyMissile(String missileID);
	void addedLauncher();
	void addedMissileLauncherDestructor();
	void addedMissileDestructor();
	void LaunchMissile();
}
