package IronDome.listeners;

import IronDome.model.Bomber;
import IronDome.model.Interceptor;
import IronDome.model.Launcher;
import IronDome.model.Missile;
import IronDome.utils.ComponentStatus;

public interface IAllWar {

	void missileNotification (Missile missile, ComponentStatus status);
	void launcherNotification (Launcher launcher, ComponentStatus status);
	void missileDestructorNotification(String mdId, ComponentStatus status);
	void missileLauncherDestructorNotification(String mldId, String type, ComponentStatus status);
	void bomberNotification(Bomber bomber, ComponentStatus status);
	void interceptorNotification(Interceptor interceptor, ComponentStatus status);
	void userNotificaton(String text);
}
