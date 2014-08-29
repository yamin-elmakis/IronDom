package IronDome.model;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;

import IronDome.listeners.IAllWar;
import IronDome.utils.DestructorType;
import IronDome.utils.Utils;

public class IDF {
	// PriorityQueue - the object that is not shooting will be first 
	private PriorityQueue<MissileDestructor> ironDomes;
	private PriorityQueue<MissileLauncherDestructor> destrucors;
	private IAllWar allWar;
	
	public IDF() {
		this(new ArrayList<MissileDestructor>(), new ArrayList<MissileLauncherDestructor>());
	}
	
	public IDF(ArrayList<MissileDestructor> ironDomes, ArrayList<MissileLauncherDestructor> destrucors) {
		this.ironDomes = new PriorityQueue<MissileDestructor>(ironDomes);
		this.destrucors = new PriorityQueue<MissileLauncherDestructor>(destrucors);
	}
	
	public void registerAllWar(IAllWar allWar){
		this.allWar = allWar;
	}
	
	public void addMissileDestructor(String missileDestructorId, ArrayDeque<Interceptor> interceptors){
		MissileDestructor missileDestructor = new MissileDestructor(missileDestructorId, interceptors);
		ironDomes.add(missileDestructor);
		missileDestructor.registerAllWar(allWar);
		missileDestructor.start();
	}
	
	public void addMissileLauncherDestructor(String MldID, DestructorType type){
		MissileLauncherDestructor mld = new MissileLauncherDestructor(MldID, type);
		destrucors.add(mld);
		mld.registerAllWar(allWar);
		mld.start();
	}
	
	public void destroyMissile(Missile missile) {
		if (ironDomes.size() < 1){
			allWar.userNotificaton("no kipot in storage");
			return;
		}
		MissileDestructor missileDestructor= ironDomes.poll();
		destroyMissile(missileDestructor, missile);
	}

	public void destroyMissile(String missileDestructorId, Missile missile) {
		if (!ironDomes.contains(new MissileDestructor(missileDestructorId))){
			allWar.userNotificaton("MissileDestructor "+missileDestructorId+" not exist");
			return;
		}
		// TODO yael - check this method with the xml, i hope it works... :)
		MissileDestructor missileDestructor = (MissileDestructor) ironDomes.toArray()[Arrays.binarySearch(ironDomes.toArray(),new MissileDestructor(missileDestructorId))];
		ironDomes.remove(missileDestructor);
		destroyMissile(missileDestructor, missile);
	}
	
	public void destroyMissile(MissileDestructor missileDestructor, Missile missile) {
		missileDestructor.addInterseptor(missile, Utils.interceptorLaunchTime());
		// re-enter the missileDestructor to the Priority Queue in a sorted manner
		ironDomes.add(missileDestructor);
	}
	
	public void destroyLauncher(Launcher launcher){
		if (destrucors.size() < 1){
			allWar.userNotificaton("no destrucors in storage");
			return;
		}			
		// re-enter the missileDestructor to the Priority Queue in a sorted manner
		MissileLauncherDestructor missileLauncherDestructor = destrucors.poll();
		missileLauncherDestructor.addBomer(launcher, Utils.interceptorLaunchTime());
		destrucors.add(missileLauncherDestructor);
	}
}
