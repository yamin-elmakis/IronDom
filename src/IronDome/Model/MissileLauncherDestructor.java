package IronDome.Model;

import java.awt.font.NumericShaper.Range;
import java.util.Random;

import IronDome.Utils.Type;
import IronDome.Utils.Utils;

public class MissileLauncherDestructor extends Thread{

	private int getId = 105;
	private Type type;
	private String destructorId;
	
	public MissileLauncherDestructor(){
		this.type = Utils.rand.nextInt(2)+1 == 1 ? Type.Plane: Type.Ship;
		this.destructorId ="L"+ getId++;
	}
	public Type getType() {
		return type;
	}
	public void setType(Type type) {
		this.type = type;
	}
	public String getDestructorId() {
		return destructorId;
	}
	public void setDestructorId(String destructorId) {
		this.destructorId = destructorId;
	}
}
