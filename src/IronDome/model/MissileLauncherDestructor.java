package IronDome.model;

import java.util.Queue;
import java.util.logging.Level;

import IronDome.utils.DestructorType;
import IronDome.utils.TzoukEitanLogger;
import IronDome.utils.Utils;

public class MissileLauncherDestructor extends Thread {

	private static final String LOGS_FOLDER_PREFIX = "missileLauncherDestructor/MLD";
	private boolean isShooting, isRunning;
	private static int launcherDestructorIdGenerator = 110;
	private DestructorType type;
	private String destructorId;
	private Queue<Bomber> bombers;
	
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
	}

	@Override
	public void run() {
		String logFilePath = LOGS_FOLDER_PREFIX + destructorId;
		TzoukEitanLogger.addFileHandler(logFilePath, this);
		TzoukEitanLogger.myLogger.log(Level.INFO, toString() +" entered to Tzouk Eitan" , this);
		while (isRunning) {
//			if (!interceptors.isEmpty()) {
//				isShooting = true;
//				intercept();
//			} else {
//				isShooting = false;
//			}
		}
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
	public String toString() {
		return "MissileLauncherDestructor ["
				+ "type=" + type 
				+ ", destructorId=" + destructorId 
				+ ", isShooting=" + isShooting 
				+ "]";
	}
	
}
