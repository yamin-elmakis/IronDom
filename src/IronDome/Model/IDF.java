package IronDome.Model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.FileHandler;
import java.util.logging.Level;

import IronDome.Utils.TzoukEitanLogFilter;
import IronDome.Utils.TzoukEitanLogFormatter;
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
		Utils.myLogger.addHandler(fileHandler);
		Utils.myLogger.log(Level.INFO, toString(), this);	
	}
	
	public void addMissileDestructor(MissileDestructor md){
		kipot.add(md);
	}
	
	public void addMissileLauncherDestructor(MissileLauncherDestructor mld){
		destrucors.add(mld);
	}
	
	public void destroyMissile(Missile missile){
		if (kipot.size() < 1){
			Utils.myLogger.log(Level.INFO, "no kipot in storage", this);
			return;
		}
			
		kipot.get(kipot.size()-1).intersept(missile);
	}
	
	public void destroyMissileLauncher(String id){
			
	}

	
}
