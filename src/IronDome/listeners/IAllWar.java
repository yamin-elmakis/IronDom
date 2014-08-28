package IronDome.listeners;

import IronDome.model.Launcher;
import IronDome.model.Missile;

public interface IAllWar {

	void registerMissile (Missile missile);
	void missileHitTheGround (Missile missile);
	void missileInterceptedInTheAir (Missile missile);
	void registerLauncher (Launcher launcher);
	void unregisterLauncher (Launcher launcher);
	
}
