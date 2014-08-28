package IronDome.listeners;

import IronDome.utils.Destination;
import IronDome.utils.DestructorType;

public interface ITzoukEitanModelEventsListener {
	
	void missileDestructorAdded(String id);
	void missileLauncheDestructorAdded(String id, DestructorType type);
	void launcherAdded (String launcherId);
	void missileFired (String missileId, Destination dest, int damage);
	void missileIntercepted (String missileId);
	void missileExploded (String missileId, Destination dest, int damage);
	void LauncherDestroyed(String launcherId);
}
