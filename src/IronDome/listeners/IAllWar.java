package IronDome.listeners;

import IronDome.model.Launcher;
import IronDome.model.Missile;
import IronDome.utils.DestructorType;

public interface IAllWar {

	void registerMissile (Missile missile);
	void missileHitTheGround (Missile missile);
	void missileInterceptedInTheAir (Missile missile);
	void registerLauncher (Launcher launcher);
	void missileDestructorJoined(String mdId);
	void missileLauncherDestructorJoined(String mldId, DestructorType type);
	void launcherDestroyed(String mldId, Launcher launcher);
}
