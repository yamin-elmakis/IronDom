package IronDome.utils;

import java.util.Random;

public class Utils {

	public static Random rand;
	public static final int SECONDS = 1000;
	public static String destination[] = {"Beer_Sheva", "Tel_Aviv", "Sderot", "Ofakim", "Gedera", "Azor", "Nahal_Oz", "Netivot", "Ashdod", "Ashkelon"};
	public static String DestructorType[] = {"Plane" , "Ship"};
	static{
		rand = new Random(System.currentTimeMillis());
	}
	
	/** 90% chance to return TRUE */
	public static boolean bool90PercentTrue(){
		if (rand.nextDouble() < 0.9)
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

	/** returns a Destination from the Destination array */
	public static String missileDestination(){
		return destination[rand.nextInt(destination.length)];
	}
	
	/** returns a DestructorType from the DestructorType array */
	public static String destructorType(){
		return DestructorType[Utils.rand.nextInt(DestructorType.length)];
	}
}
