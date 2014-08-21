package IronDome.Listeners;

import IronDome.Model.Launcher;
import IronDome.Model.Missile;

public interface IAllWar {

	void registerMissile (Missile missile);
	void unregisterMissile (Missile missile);
	void registerLauncher (Launcher launcher);
	void unregisterLauncher (Launcher launcher);
	
}
