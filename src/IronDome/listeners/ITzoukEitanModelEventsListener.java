package IronDome.listeners;

import IronDome.utils.Destination;
import IronDome.utils.DestructorType;

public interface ITzoukEitanModelEventsListener {
	
	void missileDestructorAdded (String id);
	void interceptionFailed(String InterceptorId, String targetId);
	void missileLauncheDestructorAdded (DestructorType type);
	void launcherAdded (String launcherId);
	void missileFired (String missileId, Destination dest, int damage);
	void missileIntercepted (String missileId);
	void missileExploded (String missileId, Destination dest, int damage);
	void LauncherDestroyed (String mldId, String launcherId);
	void notifyUser (String text);
}
