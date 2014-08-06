package IronDome.Listeners;

public interface IWarModelEventsListener {
	
	void addMissileDestructor(String id);
	void addMissileLauncheDestructor(int id, String type);
	void addLauncher(boolean isHidden);
	void addMissile(String id, String Destination);

}
