package IronDome.model;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.logging.FileHandler;
import java.util.logging.Level;

import IronDome.utils.DestructorType;
import IronDome.utils.TzoukEitanLogFilter;
import IronDome.utils.TzoukEitanLogFormatter;
import IronDome.utils.TzoukEitanLogger;
import IronDome.utils.Utils;

public class IDF {
	// the object that is not shooting will be first 
	PriorityQueue<MissileDestructor> ironDomes;
	PriorityQueue<MissileLauncherDestructor> destrucors;
	
	public IDF() {
		ironDomes = new PriorityQueue<MissileDestructor>();
		destrucors = new PriorityQueue<MissileLauncherDestructor>();
	}
	
	public IDF(ArrayList<MissileDestructor> ironDomes, ArrayList<MissileLauncherDestructor> destrucors) {
		ironDomes.addAll(ironDomes);
		destrucors.addAll(destrucors);
	}
	
	public void addMissileDestructor(String missileDestructorId, ArrayDeque<Interceptor> interceptors){
		MissileDestructor missileDestructor = new MissileDestructor(missileDestructorId, interceptors);
		ironDomes.add(missileDestructor);
		missileDestructor.start();
	}
	
	public void addMissileLauncherDestructor(String MldID, DestructorType type){
		MissileLauncherDestructor mld = new MissileLauncherDestructor(MldID, type);
		destrucors.add(mld);
		mld.start();
	}
	
	public void destroyMissile(Missile missile) {
		if (ironDomes.size() < 1){
			TzoukEitanLogger.myLogger.log(Level.INFO, "no kipot in storage", this);
			return;
		}			
		try { 
			// re-enter the missileDestructor to the Priority Queue in a sorted manner
			MissileDestructor missileDestructor = ironDomes.poll();
			missileDestructor.addInterseptor(missile, Utils.rand.nextInt(7) + 12);
			ironDomes.add(missileDestructor);
		} catch (Exception e) {		}
	}
	
	public void destroyLauncher(Launcher launcher){
		if (destrucors.size() < 1){
			TzoukEitanLogger.myLogger.log(Level.INFO, "no destrucors in storage", this);
			return;
		}			
		try { 
			// re-enter the missileDestructor to the Priority Queue in a sorted manner
			MissileLauncherDestructor missileLauncherDestructor = destrucors.poll();
			missileLauncherDestructor.addBomer(launcher, Utils.rand.nextInt(7) + 2);
			destrucors.add(missileLauncherDestructor);
		} catch (Exception e) {		}	
	}

}
