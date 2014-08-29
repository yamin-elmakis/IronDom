package IronDome.listeners;

import IronDome.model.Launcher;
import IronDome.model.Missile;
import IronDome.utils.DestructorType;
import IronDome.utils.ComponentStatus;

public interface IAllWar {

	void missileNotification (Missile missile, ComponentStatus status);
	void registerLauncher (Launcher launcher);
	void missileDestructorJoined(String mdId);
	void missileLauncherDestructorJoined(String mldId, DestructorType type);
	void launcherDestroyed(String mldId, Launcher launcher);
}
