package IronDome.View;

import IronDome.Listeners.ITzoukEitanViewEventsListener;
import IronDome.Utils.Destination;

public interface ITzoukEitanView {

	void registerController(ITzoukEitanViewEventsListener listener);
	void missileFired(String id, Destination dest, int damage);
	void missileDetructed(String id);
	void addedLauncher();
	void addedMissileLauncherDestructor();
	void addedMissileDestructor();
}
