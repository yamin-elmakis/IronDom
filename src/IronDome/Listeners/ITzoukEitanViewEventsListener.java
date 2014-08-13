package IronDome.Listeners;

import IronDome.Utils.Destination;

public interface ITzoukEitanViewEventsListener {

	void missileFired(String id, Destination dest, int damage);
	void missileDetructed(String id);
	void addedLauncher();
	void addedMissileLauncherDestructor();
	void addedMissileDestructor();
}
