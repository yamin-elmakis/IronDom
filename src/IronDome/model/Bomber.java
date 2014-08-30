package IronDome.model;

import java.util.logging.Level;

import IronDome.listeners.IAllWar;
import IronDome.utils.ComponentStatus;
import IronDome.utils.TzoukEitanLogger;
import IronDome.utils.Utils;

public class Bomber extends Thread {

	private Launcher target;
	private int destructTime;
	private MissileLauncherDestructor mld;
	private IAllWar allWar;
	
	public Bomber(MissileLauncherDestructor mld, Launcher target) {
		this(mld, target, Utils.interceptorLaunchTime());
	}

	public Bomber(MissileLauncherDestructor mld, Launcher target, int destructTime) {
		this.mld = mld;
		this.target = target;
		this.destructTime = 1000 * destructTime; // turn to millisecond
	}

	public void registerAllWar(IAllWar allWar) {
		this.allWar = allWar;
	}
	
	@Override
	public void run() {
		allWar.bomberNotification(this, ComponentStatus.launched);
		if (target.isAlive() && target.isExposed()){
			try {
				sleep(destructTime);
			} catch (InterruptedException e) {	}
			if (target.isAlive() && target.isExposed() && Utils.bool80PercentTrue()){
				target.setRunning(false);
				allWar.bomberNotification(this, ComponentStatus.hit);
				allWar.launcherNotification(target, ComponentStatus.destroyed);
				TzoukEitanLogger.myLogger.log(Level.INFO, mld.getDestructorId() +" destroyed "+ target.getLauncherId(), new Object[] {target, mld});
			}
			else {
				allWar.bomberNotification(this, ComponentStatus.miss);
				TzoukEitanLogger.myLogger.log(Level.INFO, mld.getDestructorId() +" missed "+ target.getLauncherId(), mld);
			}
		}
		else 
			TzoukEitanLogger.myLogger.log(Level.INFO, mld.getDestructorId() +" can't bomb "+ target.getLauncherId(), mld);
	}

	public Launcher getTarget(){
		return target;
	}
	
	public MissileLauncherDestructor getDestructor(){
		return mld;
	}
	
	@Override
	public String toString() {
		return "Bomber [targetID=" + target.getLauncherId() + ", destructTime=" + destructTime
				+ "]";
	}
}
