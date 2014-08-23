package IronDome.Model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.logging.FileHandler;
import java.util.logging.Level;

import IronDome.Utils.TzoukEitanLogFilter;
import IronDome.Utils.TzoukEitanLogFormatter;
import IronDome.Utils.TzoukEitanLogger;
import IronDome.Utils.Utils;

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
	
	public void addMissileDestructor(MissileDestructor md){
		ironDomes.add(md);
		md.start();
	}
	
	public void addMissileLauncherDestructor(MissileLauncherDestructor mld){
		destrucors.add(mld);
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
	
	public void destroyMissileLauncher(String id){
			
	}

}
