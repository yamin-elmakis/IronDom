package IronDome.Model;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.logging.Level;

import IronDome.Utils.TzoukEitanLogger;
import IronDome.Utils.Utils;

public class MissileDestructor extends Thread {

	private static final String LOGS_FOLDER_PREFIX = "missileDestructors/Destructor";
	private boolean isShooting, isRunning;
	private static int MissileDestructorIdGenerator = 210;
	private String missileDestructorId;
	private Queue<Missile> missilesDestructors;
	
	public MissileDestructor(){
		this(generateMissileDestructorId(), new ArrayDeque<Missile>());
	}
	
	public MissileDestructor(String id, ArrayDeque<Missile> missilesDestructors){
		this.missileDestructorId = id;
		this.missilesDestructors = missilesDestructors;
		isShooting = false;
		isRunning = true;
	}
	
	@Override
	public void run(){
		String logFilePath = LOGS_FOLDER_PREFIX + missileDestructorId;
		TzoukEitanLogger.addFileHandler(logFilePath, this);
		TzoukEitanLogger.myLogger.log(Level.INFO, toString() , this);
		
		while (isRunning) {
			
		}		
	}
	
	public void intersept(final Missile missile) {
		Thread t = new Thread() {
		    public void run() {
		        int destructAfterLaunch = Utils.rand.nextInt(7) + 5;
		        TzoukEitanLogger.myLogger.log(Level.INFO,"missile Destructor "+ missileDestructorId +" gooing after "+ missile.getMissileId(), new Object[] {missile, this});
		        long interceptability = (System.currentTimeMillis() - missile.getLaunchTime())/1000 + missile.getFlightTime();
				try {
					if (destructAfterLaunch < interceptability && Utils.bool80PercentTrue()){
						missile.interrupt();
						TzoukEitanLogger.myLogger.log(Level.INFO, missileDestructorId +" intersept "+ missile.getMissileId(), new Object[] {missile, this, missile.getLancher()});
					}
					else{
						TzoukEitanLogger.myLogger.log(Level.INFO, missileDestructorId +" missed "+ missile.getMissileId(), new Object[] {missile, this, missile.getLancher()});
					}
				} catch (Exception e) {
					
				}
		    }
		};
		t.start();
	
	}
	public void addMissiles(String id, int destructAfterLaunch) {
//		 = new mi
	}
	
	public static String generateMissileDestructorId(){
		return "D"+MissileDestructorIdGenerator++;
	}

	@Override
	public String toString() {
		return "MissileDestructor [missileDestructorId=" + missileDestructorId + "]";
	}
	
}
