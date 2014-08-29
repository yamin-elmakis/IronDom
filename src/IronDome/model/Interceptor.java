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
		this(md, target, Utils.rand.nextInt(7) + 5);
	}

	public Interceptor(MissileDestructor md, Missile target, int destructAfterLaunch) {
		this.md = md;
		this.target = target;
		this.destructAfterLaunch = 1000*destructAfterLaunch; // turn to millisecond
	}

	public void registerAllWar(IAllWar allWar) {
		this.allWar = allWar;
	}
	
	@Override
	public void run(){
		allWar.interceptorNotification(this, ComponentStatus.launched);
		
		long interceptability = (target.getFlightTime() - (System.currentTimeMillis() - target.getLaunchTime()));
		if (destructAfterLaunch < interceptability && Utils.bool80PercentTrue() && target.isAlive()){
			try {
				sleep(destructAfterLaunch);
			} catch (InterruptedException e) {			}
			target.interrupt();
			allWar.interceptorNotification(this, ComponentStatus.hit);
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

	@Override
	public String toString() {
		return "Interceptor [targetID=" + target.getMissileId() + ", destructAfterLaunch="
				+ destructAfterLaunch + "]";
	}
}
