package IronDome.model;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.logging.Level;

import IronDome.listeners.IAllWar;
import IronDome.utils.ComponentStatus;
import IronDome.utils.TzoukEitanLogger;
import IronDome.utils.Utils;

public class MissileDestructor extends Thread implements Comparable<MissileDestructor> {

	private static final String LOGS_FOLDER_PREFIX = "missileDestructors/Destructor";
	private boolean isShooting, isRunning;
	private static int MissileDestructorIdGenerator = 400;
	private String missileDestructorId;
	private Queue<Interceptor> interceptors;
	private IAllWar allWar;
	
	public MissileDestructor(){
		this(generateMissileDestructorId());
	}
	
	public MissileDestructor(String missileDestructorId) {
		this(missileDestructorId, new ArrayDeque<Interceptor>());
	}

	public MissileDestructor(String missileDestructorId, ArrayDeque<Interceptor> interceptors){
		this.missileDestructorId = missileDestructorId;
		this.interceptors = interceptors;
		isShooting = false;
		isRunning = true;
	}
	
	public void registerAllWar(IAllWar allWar) {
		this.allWar = allWar;
	}
	
	@Override
	public void run(){
		String logFilePath = LOGS_FOLDER_PREFIX + missileDestructorId;
		TzoukEitanLogger.addFileHandler(logFilePath, this);
		TzoukEitanLogger.myLogger.log(Level.INFO, toString() +" entered to Tzouk Eitan" , this);
		
		allWar.missileDestructorNotification(missileDestructorId, ComponentStatus.launched);
		while (isRunning) {
			if (!interceptors.isEmpty()) {
				isShooting = true;
				intercept();
			} else {
				isShooting = false;
			}
		}		
	}
	
	public void intercept(){
		Interceptor interceptor = interceptors.poll();
		TzoukEitanLogger.myLogger.log(Level.INFO,"missile Destructor "+ missileDestructorId +" going after "+ interceptor.getTargetID() +" with "+ interceptor, this);
		interceptor.start();
		try {
			interceptor.join();
		} catch (InterruptedException e) {		}
	}

	public void addInterseptor(Missile target, int destructAfterLaunch) {
		Interceptor interceptor = new Interceptor(this, target, destructAfterLaunch);
		interceptor.registerAllWar(allWar);
		interceptors.add(interceptor);
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
		return "MissileDestructor [missileDestructorId=" + missileDestructorId 
				+" isShooting= "+ isShooting + "]";
	}

	/** the object that is not shooting will be first */
	@Override
	public int compareTo(MissileDestructor arg0) {
		if (this.isShooting && !arg0.isShooting)
			return 1;
		else if (arg0.isShooting && !this.isShooting )
			return -1;
		return 0;
	}
}
