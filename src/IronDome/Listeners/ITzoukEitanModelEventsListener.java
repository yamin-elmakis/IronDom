package IronDome.Listeners;
import IronDome.Model.Launcher;
import IronDome.Model.Missile;
import IronDome.Utils.Destination;
import IronDome.Utils.Type;

public interface ITzoukEitanModelEventsListener {
	
	void missileDestructorAdded(String id, Type type);
	void missileLauncheDestructorAdded();
	void launcherAdded(Launcher launcher);
	void missileAdded(String id, String Destination);
	void missileFired(String id, Destination dest, int damage);

}
