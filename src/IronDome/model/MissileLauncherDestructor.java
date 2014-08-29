package IronDome.model;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.logging.Level;

import sun.launcher.resources.launcher;
import IronDome.listeners.IAllWar;
import IronDome.utils.DestructorType;
import IronDome.utils.TzoukEitanLogger;
import IronDome.utils.Utils;

public class MissileLauncherDestructor extends Thread implements Comparable<MissileLauncherDestructor> {

	private static final String LOGS_FOLDER_PREFIX = "missileLauncherDestructor/MLD";
	private boolean isShooting, isRunning;
	private static int launcherDestructorIdGenerator = 300;
	private DestructorType type;
	private String destructorId;
	private Queue<Bomber> bombers;
	private IAllWar allWar;
	
	public MissileLauncherDestructor(){
		this(DestructorType.values()[Utils.rand.nextInt(DestructorType.values().length)]);
	}
	
	public MissileLauncherDestructor(DestructorType type) {
		this(generateLauncherDestructorID(), type);
	}

	public MissileLauncherDestructor(String destructorId, DestructorType type) {
		this.destructorId = destructorId;
		this.type = type;
		isShooting = false;
		isRunning = true;
		bombers = new ArrayDeque<Bomber>();
	}

	public void registerAllWar(IAllWar allWar) {
		this.allWar = allWar;
		allWar.missileLauncherDestructorJoined(destructorId, type);
	}
	
	@Override
	public void run() {
		String logFilePath = LOGS_FOLDER_PREFIX + destructorId;
		TzoukEitanLogger.addFileHandler(logFilePath, this);
		TzoukEitanLogger.myLogger.log(Level.INFO, toString() +" entered to Tzouk Eitan" , this);
		
		while (isRunning) {
			if (!bombers.isEmpty()) {
				isShooting = true;
				launchBomber();
			} else {
				isShooting = false;
			}
		}
	}

	private void launchBomber() {
		Bomber bomber = bombers.poll();
		TzoukEitanLogger.myLogger.log(Level.INFO,"Missile Launcher Destructor "+ destructorId  +" bombing "+ bomber.getTargetID() +" with "+ bomber, this);
		bomber.start();
		try {
			bomber.join();
		} catch (InterruptedException e) {		}
	}

	public void addBomer(Launcher target, int destructTime) {
		Bomber bomber = new Bomber(this, target, destructTime);
		bomber.registerAllWar(allWar);
		bombers.add(bomber);
	}
	
	public DestructorType getType() {
		return type;
	}
	
	public void setType(DestructorType type) {
		this.type = type;
	}
	
	public String getDestructorId() {
		return destructorId;
	}
	
	public void setDestructorId(String destructorId) {
		this.destructorId = destructorId;
	}
	
	public static String generateLauncherDestructorID(){
		return "LD"+launcherDestructorIdGenerator++;
	}

	
	@Override
	public boolean equals(Object arg0) {
		MissileLauncherDestructor other = (MissileLauncherDestructor) arg0;
		return this.getDestructorId().equals(other.getDestructorId());
	}

	@Override
	public int compareTo(MissileLauncherDestructor arg0) {
		if (this.isShooting && !arg0.isShooting)
			return 1;
		else if (arg0.isShooting && !this.isShooting )
			return -1;
		return 0;
	}
	
	@Override
	public String toString() {
		return "MissileLauncherDestructor ["
				+ "type=" + type 
				+ ", destructorId=" + destructorId 
				+ ", isShooting=" + isShooting 
				+ "]";
	}
}
