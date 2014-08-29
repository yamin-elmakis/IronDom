package IronDome.utils;

import java.util.Random;

public class Utils {

	public static Random rand;

	static{
		rand = new Random(System.currentTimeMillis());
	}
	
	/** 80% chance to return TRUE */
	public static boolean bool80PercentTrue(){
		if (rand.nextDouble() < 0.8)
			return true;
		return false;
	}

	/** 60% chance to return TRUE */
	public static boolean bool60PercentTrue(){
		if (rand.nextDouble() < 0.6)
			return true;
		return false;
	}

	/** returns an int between 20 and 35 */
	public static int missileLaunchTime(){
		return rand.nextInt(16) + 20;
	}
	
	/** returns an int between 5000 and 10000 */
	public static int missileDamage(){
		return rand.nextInt(5001) + 5000;
	}
	
	/** returns an int between 2000 and 4000 */
	public static int launcherMovingTime(){
		return rand.nextInt(2001) + 2000;
	}
	
	/** returns an int between 5 and 15 */
	public static int interceptorLaunchTime(){
		return rand.nextInt(11) + 5;
	}

	/** returns a Destination from the Destination enum */
	public static Destination missileDestination(){
		return Destination.values()[rand.nextInt(Destination.values().length)];
	}
	
	/** returns a DestructorType from the DestructorType enum */
	public static DestructorType destructorType(){
		return DestructorType.values()[Utils.rand.nextInt(DestructorType.values().length)];
	}
}
