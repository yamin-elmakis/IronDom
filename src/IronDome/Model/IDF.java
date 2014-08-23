package IronDome.Model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.FileHandler;
import java.util.logging.Level;

import IronDome.Utils.TzoukEitanLogFilter;
import IronDome.Utils.TzoukEitanLogFormatter;
import IronDome.Utils.TzoukEitanLogger;
import IronDome.Utils.Utils;

public class IDF {
	ArrayList<MissileDestructor> kipot;
	ArrayList<MissileLauncherDestructor> destrucors;
	
	public IDF() {
		kipot = new ArrayList<MissileDestructor>();
		destrucors = new ArrayList<MissileLauncherDestructor>();
	}
	
	public IDF(ArrayList<MissileDestructor> kipot, ArrayList<MissileLauncherDestructor> destrucors) {
		this.kipot = kipot;
		this.destrucors = destrucors;
	}
	
	public void setLoggerData() throws SecurityException, IOException{
		FileHandler fileHandler = new FileHandler("IDFLog.txt");
		fileHandler.setFormatter(new TzoukEitanLogFormatter());
		fileHandler.setFilter(new TzoukEitanLogFilter(this));
		TzoukEitanLogger.myLogger.addHandler(fileHandler);
		TzoukEitanLogger.myLogger.log(Level.INFO, toString(), this);	
	}
	
	public void addMissileDestructor(MissileDestructor md){
		kipot.add(md);
		md.start();
	}
	
	public void addMissileLauncherDestructor(MissileLauncherDestructor mld){
		destrucors.add(mld);
	}
	
	public void destroyMissile(Missile missile) {
		if (kipot.size() < 1){
			TzoukEitanLogger.myLogger.log(Level.INFO, "no kipot in storage", this);
			return;
		}			
		try { 
			kipot.get(Utils.rand.nextInt(kipot.size())).addInterseptor(missile, Utils.rand.nextInt(7) + 5);
		} catch (Exception e) {		}
	}
	
	public void destroyMissileLauncher(String id){
			
	}

	
}
