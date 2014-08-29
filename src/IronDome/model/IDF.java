package IronDome.model;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.logging.Level;

import IronDome.listeners.IAllWar;
import IronDome.utils.ComponentStatus;
import IronDome.utils.DestructorType;
import IronDome.utils.TzoukEitanLogger;
import IronDome.utils.Utils;

public class IDF {
	// the object that is not shooting will be first 
	PriorityQueue<MissileDestructor> ironDomes;
	PriorityQueue<MissileLauncherDestructor> destrucors;
	IAllWar allWar;
	
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
		allWar.missileDestructorNotification(missileDestructorId, ComponentStatus.launched);
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
			TzoukEitanLogger.myLogger.log(Level.INFO, "no kipot in storage", this);
			return;
		}
		MissileDestructor missileDestructor= ironDomes.poll();
		destroyMissile(missileDestructor, missile);
	}

	public void destroyMissile(String missileDestructorId, Missile missile) {
		if (!ironDomes.contains(new MissileDestructor(missileDestructorId))){
			TzoukEitanLogger.myLogger.log(Level.INFO, "MissileDestructor "+missileDestructorId+" not exist", this);
			return;
		}
		// TODO yael - check this method with the xml, i hope it works... :)
		MissileDestructor missileDestructor = (MissileDestructor) ironDomes.toArray()[Arrays.binarySearch(ironDomes.toArray(),new MissileDestructor(missileDestructorId))];
		ironDomes.remove(missileDestructor);
		destroyMissile(missileDestructor, missile);
	}
	
	public void destroyMissile(MissileDestructor missileDestructor, Missile missile) {
		missileDestructor.addInterseptor(missile, Utils.rand.nextInt(7) + 12);
		// re-enter the missileDestructor to the Priority Queue in a sorted manner
		ironDomes.add(missileDestructor);
	}
	
	public void destroyLauncher(Launcher launcher){
		if (destrucors.size() < 1){
			TzoukEitanLogger.myLogger.log(Level.INFO, "no destrucors in storage", this);
			return;
		}			
		// re-enter the missileDestructor to the Priority Queue in a sorted manner
		MissileLauncherDestructor missileLauncherDestructor = destrucors.poll();
		missileLauncherDestructor.addBomer(launcher, Utils.rand.nextInt(7) + 2);
		destrucors.add(missileLauncherDestructor);
	}
}
