package IronDome.Listeners;

public interface ITzoukEitanViewEventsListener {

	void getMissilesList();
	void missileDestructed();
	void launcherDestructed();
	void destroyMissile();
	void destroyMissile(String missileID);
	void addLauncher();
	void addLauncher(String id, boolean ishidden);
	void addMissileLauncherDestructor();
	void addMissileDestructor();
	void LaunchMissile();
}
