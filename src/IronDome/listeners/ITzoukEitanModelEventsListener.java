package IronDome.listeners;

public interface ITzoukEitanModelEventsListener {
	
	void missileDestructorAdded (String id);
	void interceptionFailed(String InterceptorId, String targetId);
	void missileLauncheDestructorAdded (String type);
	void launcherAdded (String launcherId);
	void missileFired (String missileId, String dest, int damage);
	void missileIntercepted (String missileId);
	void missileExploded (String missileId, String dest, int damage);
	void LauncherDestroyed (String mldId, String launcherId);
	void notifyUser (String text);
}
