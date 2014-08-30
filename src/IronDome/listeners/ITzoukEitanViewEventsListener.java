package IronDome.listeners;

public interface ITzoukEitanViewEventsListener {

	void getMissilesList();
	void destroyMissile();
	void destroyMissile(String missileId);
	void destroyMissile(String missileId, int destructAfterLaunch);
	void destroyLauncher();
	void destroyLauncher(String launcherId);
	void addLauncher();
	void addLauncher(String Lid, boolean ishidden);
	void addMissileLauncherDestructor();
	void addMissileDestructor();
	void addMissileDestructor(String mDid);
	void LaunchMissile();
	void LaunchMissile(String Lid, String mid, String destination, int flyTime, int damage);
	void addMissileLauncherDestructor(String type);
	void showStatistics();
}
