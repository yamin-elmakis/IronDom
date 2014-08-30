package IronDome.model;

import java.util.logging.Level;

import IronDome.listeners.IAllWar;
import IronDome.utils.ComponentStatus;
import IronDome.utils.TzoukEitanLogger;
import IronDome.utils.Utils;

public class Interceptor extends Thread {
	
	private Missile target;
	private int destructAfterLaunch;
	private MissileDestructor md;
	private IAllWar allWar;
	
	public Interceptor(MissileDestructor md, Missile target) {
		this(md, target, Utils.interceptorLaunchTime());
	}

	public Interceptor(MissileDestructor md, Missile target, int destructAfterLaunch) {
		this.md = md;
		this.target = target;
		this.destructAfterLaunch = 1000 * destructAfterLaunch; // turn to millisecond
	}

	public void registerAllWar(IAllWar allWar) {
		this.allWar = allWar;
	}
	
	@Override
	public void run(){
		long interceptability = (target.getFlightTime() - (System.currentTimeMillis() - target.getLaunchTime()));
		if (target.isAlive() && destructAfterLaunch < interceptability && Utils.bool90PercentTrue()){
			try {
				sleep(destructAfterLaunch);
			} catch (InterruptedException e) {			}
			target.interrupt();
			TzoukEitanLogger.myLogger.log(Level.INFO, md.getMissileDestructorId() +" intercept "+ target.getMissileId(), new Object[] {target, md, target.getLancher()});
		}
		else{
			allWar.interceptorNotification(this, ComponentStatus.miss);
			TzoukEitanLogger.myLogger.log(Level.INFO, md.getMissileDestructorId() +" missed "+ target.getMissileId(), md);
		}
	}
	
	public String getTargetID() {
		return target.getMissileId();
	}

	public String getDestructorID() {
		return md.getMissileDestructorId();
	}
	
	@Override
	public String toString() {
		return "Interceptor [targetID=" + target.getMissileId() + ", destructAfterLaunch="
				+ destructAfterLaunch + "]";
	}
}
