package IronDome.Model;

import java.util.logging.Level;

import IronDome.Utils.TzoukEitanLogger;
import IronDome.Utils.Utils;

public class Interseptor extends Thread {
	
	private Missile target;
	private int destructAfterLaunch;
	private MissileDestructor md;
	
	public Interseptor(MissileDestructor md, Missile target) {
		this(md, target, Utils.rand.nextInt(7) + 5);
	}

	public Interseptor(MissileDestructor md, Missile target, int destructAfterLaunch) {
		this.md = md;
		this.target = target;
		this.destructAfterLaunch = destructAfterLaunch;
	}

	@Override
	public void run(){
		long interceptability = target.getFlightTime() - (System.currentTimeMillis() - target.getLaunchTime())/1000;
		if (destructAfterLaunch < interceptability && Utils.bool80PercentTrue() && target.isAlive()){
			target.interrupt();
			TzoukEitanLogger.myLogger.log(Level.INFO, md.getMissileDestructorId() +" intersept "+ target.getMissileId(), new Object[] {target, md, target.getLancher()});
		}
		else{
			TzoukEitanLogger.myLogger.log(Level.INFO, md.getMissileDestructorId() +" missed "+ target.getMissileId(), md);
		}
	}
	
	public String getTargetID() {
		return target.getMissileId();
	}
}
