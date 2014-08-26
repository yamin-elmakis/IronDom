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

}
