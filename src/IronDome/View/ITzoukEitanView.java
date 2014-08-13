package IronDome.View;

import IronDome.Listeners.ITzoukEitanViewEventsListener;

public interface ITzoukEitanView {

	void registerController(ITzoukEitanViewEventsListener listener);
	void missileFired(String id);
	void missileDetructed(String id);
	void addedLauncher();
	void addedMissileLauncherDestructor();
	void addedMissileDestructor();
}
