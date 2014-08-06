package IronDome.Model;

import IronDome.Utils.Type;

public class MissileLauncherDestructor extends Thread{

	private Type type;
	private String destructorId;
	
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
