package IronDome.Model;

import java.util.ArrayList;

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
	public void addMissileDestructor(MissileDestructor md){
		kipot.add(md);
	}
	public void addMissileLauncherDestructor(MissileLauncherDestructor mld){
		destrucors.add(mld);
	}
	
	public void destructMissile(String id){
		
	}
	public void destructMissileLauncher(String id){
			
	}

	
}
