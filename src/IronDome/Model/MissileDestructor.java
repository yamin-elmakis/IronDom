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
	private Queue<Interseptor> interseptors;
	
	public MissileDestructor(){
		this(generateMissileDestructorId(), new ArrayDeque<Interseptor>());
	}
	
	public MissileDestructor(String id, ArrayDeque<Interseptor> interseptors){
		this.missileDestructorId = id;
		this.interseptors = interseptors;
		isShooting = false;
		isRunning = true;
	}
	
	@Override
	public void run(){
		String logFilePath = LOGS_FOLDER_PREFIX + missileDestructorId;
		TzoukEitanLogger.addFileHandler(logFilePath, this);
		TzoukEitanLogger.myLogger.log(Level.INFO, toString() , this);
		
		while (isRunning) {
			if (!interseptors.isEmpty()) {
				isShooting = true;
				intersept();
			} else {
				isShooting = false;
			}
		}		
	}
	public void intersept(){
		Interseptor interseptor = interseptors.poll();
		TzoukEitanLogger.myLogger.log(Level.INFO,"missile Destructor "+ missileDestructorId +" gooing after "+ interseptor.getTargetID(), this);
		interseptor.start();
		try {
			interseptor.join();
		} catch (InterruptedException e) {		}
	}
	
	public void intersept(final Missile missile) {
		Thread t = new Thread() {
		    public void run() {
		        int destructAfterLaunch = Utils.rand.nextInt(7) + 5;
		        TzoukEitanLogger.myLogger.log(Level.INFO,"missile Destructor "+ missileDestructorId +" gooing after "+ missile.getMissileId(), new Object[] {missile, this});
		        long interceptability = missile.getFlightTime() - (System.currentTimeMillis() - missile.getLaunchTime())/1000;
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
	public void addInterseptor(Missile target, int destructAfterLaunch) {
		interseptors.add(new Interseptor(this, target, destructAfterLaunch));
	}
	
	public static String generateMissileDestructorId(){
		return "D"+MissileDestructorIdGenerator++;
	}

	public String getMissileDestructorId() {
		return missileDestructorId;
	}

	public boolean isShooting() {
		return isShooting;
	}

	@Override
	public boolean equals(Object arg0) {
		MissileDestructor other = (MissileDestructor) arg0;
		return this.missileDestructorId.equals(other.getMissileDestructorId());
	}

	@Override
	public String toString() {
		return "MissileDestructor [missileDestructorId=" + missileDestructorId + "]";
	}
	
}
