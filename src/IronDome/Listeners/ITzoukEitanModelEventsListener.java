package IronDome.Listeners;
import IronDome.Model.Missile;

public interface ITzoukEitanModelEventsListener {
	
	void addMissileDestructor(String id);
	void addMissileLauncheDestructor(String id, String type);
	void addLauncher(boolean isHidden);
	void addMissile(String id, String Destination);
	void shootMissile(Missile missile);
	

}
