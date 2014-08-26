package IronDome.listeners;
import IronDome.model.Launcher;
import IronDome.model.Missile;
import IronDome.utils.Destination;
import IronDome.utils.DestructorType;

public interface ITzoukEitanModelEventsListener {
	
	void missileDestructorAdded(String id);
	void missileLauncheDestructorAdded(String id, DestructorType type);
	void launcherAdded(String id);
	void missileAdded(String id, String Destination);
	void missileFired(String id, Destination dest, int damage);

}
