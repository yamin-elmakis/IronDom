package IronDome.Listeners;
import IronDome.Model.Missile;
import IronDome.Utils.Type;

public interface ITzoukEitanModelEventsListener {
	
	void addMissileDestructor(String id, Type type);
	void addMissileLauncheDestructor();
	void addLauncher(boolean isHidden);
	void addMissile(String id, String Destination);
	void shootMissile(Missile missile);
	

}
