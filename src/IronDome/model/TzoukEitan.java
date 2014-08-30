package IronDome.model;

import java.util.ArrayDeque;
import java.util.Calendar;
import java.util.Vector;
import java.util.logging.Level;

import sun.util.calendar.LocalGregorianCalendar.Date;
import IronDome.listeners.IAllWar;
import IronDome.listeners.ITzoukEitanModelEventsListener;
import IronDome.utils.ComponentStatus;
import IronDome.utils.DestructorType;
import IronDome.utils.Statistics;
import IronDome.utils.TzoukEitanLogger;
import IronDome.utils.Utils;

public class TzoukEitan implements IAllWar {

	private Hamas hamas;
	private IDF idf;
	private Vector<ITzoukEitanModelEventsListener> listeners;
	private Vector<Missile> allMissiles;
	private Vector<Launcher> allLaunchers;
	private Statistics statistics;
	 
	public TzoukEitan() {
		// init the Hamas and IDF
		hamas = new Hamas();
		idf = new IDF();
		statistics = new Statistics();
		listeners = new Vector<ITzoukEitanModelEventsListener>();
		allMissiles = new Vector<Missile>();
		allLaunchers = new Vector<Launcher>(); 
		
		hamas.registerAllWar(this);
		idf.registerAllWar(this);
	}

	////////////////////////////////////////////
	//////////// work the model  ///////////////
	////////////////////////////////////////////
	public void registerListener(ITzoukEitanModelEventsListener listener){
		listeners.add(listener);
	}
	
	public void addMissileDestructor(String missileDestructorId, ArrayDeque<Interceptor> interceptors) {
		idf.addMissileDestructor(missileDestructorId, interceptors);
	}

	public void addMissileLauncheDestructor(DestructorType type) {
		idf.addMissileLauncherDestructor(type);
	}
	
	public void interceptMissile(String missileId){
		interceptMissile(missileId, Utils.interceptorLaunchTime());
	}

	public void interceptMissile(String missileId, int destructAfterLaunch){
		Missile temp = new Missile(missileId);
		if (allMissiles.contains(temp)) {
			Missile missile = allMissiles.get(allMissiles.indexOf(temp));
			idf.destroyMissile(missile, destructAfterLaunch);
		}
		else{
			// the missile already exploded	
			userNotificaton("the missile "+missileId+" is no longer in the air.");
		}
	}
	
	public void destroyLauncher(String launcherId) {
		Launcher temp = new Launcher(launcherId);
		if (allLaunchers.contains(temp)){
			Launcher launcher = allLaunchers.get(allLaunchers.indexOf(temp));
			idf.destroyLauncher(launcher);
		}
		else {
			// the launcher already destroyed
			userNotificaton("the launcher " +launcherId+ " is already destroyed.");
		}
	}
	
	public void addLauncher(String id, boolean isHidden) {
		hamas.addMissileLauncher(new Launcher(id, isHidden));
	}

	public void launchMissile(int flightTime, int damage, String destination) {
		if (allLaunchers.size() < 1){
			userNotificaton("can't launch Missile - no launchers found");
			return;
		}	
		String launcherId = allLaunchers.get(Utils.rand.nextInt(allLaunchers.size())).getLauncherId();
		String missileID = Missile.generateMissileId();
		launchMissile(launcherId, missileID, flightTime, damage, destination);
	}
	
	public void launchMissile(String launcherId, String missileID, int flightTime, int damage, String destination) {
		if (allLaunchers.contains(new Launcher(launcherId))){
			hamas.loadMissile(launcherId, missileID, flightTime, damage, destination);
		}				
	}
	
	////////////////////////////////////////////
	/////////// notify the controller  /////////
	////////////////////////////////////////////
	public String showStatistics(){
		return statistics.toString();
	}
	
	public Vector<Missile> getAllMissiles() {
		return allMissiles;
	}

	public Vector<Launcher> getAllLaunchers() {
		Vector<Launcher> exposedLaunchers = new Vector<Launcher>();
		for (Launcher launcher : allLaunchers) {
			if (launcher.isExposed())
				exposedLaunchers.add(launcher);
		}
		return exposedLaunchers;
	}
	
	private void fireMissileDestructorJoinedEvent(String id){
		for (ITzoukEitanModelEventsListener listener: listeners) {
			listener.missileDestructorAdded(id);
		}
	}
	
	private void fireMissileInterceptionFailedEvent(String InterceptorId, String targetId){
		for (ITzoukEitanModelEventsListener listener: listeners) {
			listener.interceptionFailed(InterceptorId, targetId);
		}
	}
	
	private void fireMissileLauncheDestructorJoinedEvent(String id, DestructorType type){
		for (ITzoukEitanModelEventsListener listener: listeners) {
			listener.missileLauncheDestructorAdded(type);
		}
	}
	
	private void fireAddLauncherEvent(String launcherId){
		for (ITzoukEitanModelEventsListener listener: listeners) {
			listener.launcherAdded(launcherId);
		}
	}
	
	private void fireLauncherDestroyedEvent(String mldId, String launcherId){
		statistics.setDestroyedLaunchersCount();
		for (ITzoukEitanModelEventsListener listener: listeners) {
			listener.LauncherDestroyed(mldId, launcherId);
		}
	}
	
	private void fireMissilefiredEvent(String id, String Destination, int damage){
		statistics.setMissileCount();
		for (ITzoukEitanModelEventsListener listener: listeners) {
			listener.missileFired(id, Destination, damage);
		}
	}
	
	private void fireMissileInterceptedEvent(String missileId){
		statistics.setInterceptionsCount();
		for (ITzoukEitanModelEventsListener listener: listeners) {
			listener.missileIntercepted(missileId);
		}
	}
	
	private void fireMissileExplodedEvent(String missileId, String dest, int damage){
		statistics.setExplosionsCount();
		statistics.addToTotalDamage(damage);
		for (ITzoukEitanModelEventsListener listener: listeners) {
			listener.missileExploded(missileId, dest, damage);
		}
	}

	private void fireUserNotification(String text) {
		for (ITzoukEitanModelEventsListener listener: listeners) {
			listener.notifyUser(text);
		}
	}

	////////////////////////////////////////////
	/////////// all war interface  /////////////
	////////////////////////////////////////////
	@Override
	public void missileNotification(Missile missile, ComponentStatus status) {
		switch (status) {
		case launched:
			allMissiles.add(missile);
			fireMissilefiredEvent(missile.getMissileId(), missile.getDestination(), missile.getDamage());
			break;
		case hit:
			allMissiles.remove(missile);
			fireMissileExplodedEvent(missile.getMissileId(), missile.getDestination(), missile.getDamage());	
			break;
		case miss:
			allMissiles.remove(missile);
			// if the missile miss then the damage is 0
			fireMissileExplodedEvent(missile.getMissileId(), missile.getDestination(), 0);	
			break;
		case destroyed:
			allMissiles.remove(missile);
			fireMissileInterceptedEvent(missile.getMissileId());
			break;
		}
		
	}
	
	@Override
	public void launcherNotification(Launcher launcher, ComponentStatus status) {
		switch (status) {
			case launched:
				allLaunchers.add(launcher);
				fireAddLauncherEvent(launcher.getLauncherId());
				break;
			case destroyed:
				allLaunchers.remove(launcher);
				break;
		}
	}

	@Override
	public void missileDestructorNotification(String mdId, ComponentStatus status) {
		switch (status) {
			case launched:
				fireMissileDestructorJoinedEvent(mdId);
				break;			
		}
	}

	@Override
	public void missileLauncherDestructorNotification(String mldId, DestructorType type, ComponentStatus status) {
		switch (status) {
		case launched:
			fireMissileLauncheDestructorJoinedEvent(mldId, type);
			break;
		}
	}

	@Override
	public void bomberNotification(Bomber bomber, ComponentStatus status) {
		switch (status) {
		case launched:
			userNotificaton("Missile Launcher Destructor "+ bomber.getDestructor().getDestructorId()  +" bombing "+ bomber.getTarget().getLauncherId());
			break;
		case hit:
			fireLauncherDestroyedEvent(bomber.getDestructor().getDestructorId(), bomber.getTarget().getLauncherId());
			break;
		case miss:
			userNotificaton(bomber.getDestructor().getDestructorId() +" missed "+ bomber.getTarget().getLauncherId());
			break;
		}
	}

	@Override
	public void interceptorNotification(Interceptor interceptor, ComponentStatus status) {
		switch (status) {
		case miss:
			fireMissileInterceptionFailedEvent(interceptor.getDestructorID(), interceptor.getTargetID());
			break;
		}
	}

	@Override
	public void userNotificaton(String text) {
		fireUserNotification(text);
	}
}
