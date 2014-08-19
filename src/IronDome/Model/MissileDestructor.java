package IronDome.Model;

import java.util.ArrayDeque;
import java.util.Queue;

import IronDome.Utils.Utils;

public class MissileDestructor extends Thread {

	private int getid = 204;
	private String missileDestructorId;
	private Queue<Missile> missilesDestructors;
	
	public MissileDestructor(){
		this.missileDestructorId = "D"+ getid++;
		missilesDestructors = new ArrayDeque<Missile>();
		
	}
	public MissileDestructor(String id, ArrayDeque<Missile> missilesDestructors){
		this.missileDestructorId = id;
		this.missilesDestructors = missilesDestructors;
		
	}
	
	public void intersept(Missile missile) {
		int destructAfterLaunch = Utils.rand.nextInt(7)+2;
	}
}
