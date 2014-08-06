package IronDome.Listeners;

public interface IWarViewEventsListener {

	void missileFired(String id);
	void missileDetructed(String id);
	void addedLauncher();
	void addedMissileLauncherDestructor();
	void addedMissileDestructor();
}
