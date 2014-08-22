package IronDome.Model;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;

import sun.launcher.resources.launcher;
import IronDome.Utils.Destination;
import IronDome.Utils.TzoukEitanLogFilter;
import IronDome.Utils.TzoukEitanLogFormatter;
import IronDome.Utils.TzoukEitanLogger;

// TODO what is this class ?? 
public class DestructMissile extends Thread {

	private static int destructMissileIdGenerator = 10;
	private int destructMissile;
	private String destructMissileId;
	
//	public Missile(String missileId, int flightTime, int damage, Destination destination, Launcher l) throws SecurityException, IOException {
//		this(missileId, 0, flightTime, damage, destination, l);
//	}

	public DestructMissile(String destructMissileId) {
		this.destructMissileId = destructMissileId;
//		setLoggerData(l);
	}
	
//	public void setLoggerData(Launcher l) {
//		FileHandler fileHandler;
//		try {
//			fileHandler = new FileHandler("Missile"+missileId+"Log.txt");
//			fileHandler.setFormatter(new TzoukEitanLogFormatter());
//			fileHandler.setFilter(new TzoukEitanLogFilter(this));
//			Utils.myLogger.addHandler(fileHandler);
//		} catch (SecurityException | IOException e) {
//			e.printStackTrace();
//		}
//		Utils.myLogger.log(Level.INFO, toString(), new Object[] { l, this });	
//	}
	//TODO add loggers to 
	
	public String getDestructMissileId() {
		return destructMissileId;
	}

	public void setMissileId(String missileId) {
		this.destructMissileId = destructMissileId;
	}

	public static String generateDestructMissileId(){
		return "M"+destructMissileIdGenerator++;
	}

	@Override
	public String toString() {
		return "Missile [ missileId=" + destructMissileId + "]";
	}
}
