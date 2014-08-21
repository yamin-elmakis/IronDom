package IronDome.Model;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.logging.Level;

import IronDome.Utils.Utils;

public class MissileDestructor extends Thread {

	private int getid = 204;
	private String missileDestructorId;
	private Queue<Missile> missilesDestructors;
	
	public MissileDestructor(){
		this.missileDestructorId = "D"+ getid++;
		missilesDestructors = new ArrayDeque<Missile>();
		
	}
	public MissileDestructor(String id, ArrayDeque<Missile> missilesDestructors){
		this.missileDestructorId = id;
		this.missilesDestructors = missilesDestructors;
		
	}
	
	public void intersept(final Missile missile) {
		Thread t = new Thread() {
		    public void run() {
		        int destructAfterLaunch = Utils.rand.nextInt(7)+2;
		        Utils.myLogger.log(Level.INFO,"missile Destructor "+ missileDestructorId +" gooing after "+ missile.getMissileId(), new Object[] {missile, this});
		        long interceptability = (System.currentTimeMillis() - missile.getLaunchTime())/1000 + missile.getFlightTime();
				try {
					if (destructAfterLaunch < interceptability && Utils.bool80PercentTrue()){
						missile.interrupt();
						Utils.myLogger.log(Level.INFO, missileDestructorId +" intersept "+ missile.getMissileId(), new Object[] {missile, this});
					}
					else{
						Utils.myLogger.log(Level.INFO, missileDestructorId +" missed "+ missile.getMissileId(), new Object[] {missile, this});
					}
				} catch (Exception e) {
					
				}
		    }
		};
		t.start();
		
	}
}
