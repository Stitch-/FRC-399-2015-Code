package org.usfirst.frc.team9399.util;

public abstract class PhoenixMath {
	
	/**
	 * Converts rectangular form vectors to angular form vectors
	 * double[] rectangle = {x,y};
	 * double[] angle = {magnitude,angle};
	 * @param vec 
	 */	
	public static double[] toAngular(double[] vec){
		double ang;
		double mag;
		ang = Math.tan(vec[1]/vec[0]);
		ang = Math.toDegrees(ang);
		mag = Math.pow(vec[0],2)+Math.pow(vec[1],2);
		mag = Math.sqrt(mag);
		mag = clamp(-1.0,1.0,mag);
		double[] vecOut = {mag,ang};
		return vecOut;
	}

	

	/**
	 * Converts angular form vectors to rectangular form vectors
	 * double[] rectangle = {x,y};
	 * double[] angle = {magnitude,angle};
	 * @param vec
	 */	
	public static double[] toRectangular(double[] vec){
		double x;
		double y;
		x = Math.acos(Math.toRadians(vec[1]))*vec[0];
		y = Math.asin(Math.toRadians(vec[1]))*vec[0];
		double[] vecOut = {x,y};
		return vecOut;
	}
	
	
	
	/**
	 * Rotates a rectangular form vector by degrees.
	 * @param vec
	 * @param degrees
	 */
	public static double[] rotate(double[] vec, double degrees){
		vec = toAngular(vec);
		vec[1]+=degrees;
		while(vec[1]>360){
			vec[1]-=360;
		}
		while(vec[1]<0){
			vec[1]+=360;
		}
		vec = toRectangular(vec);
		return vec;
	}
	
	
	/**
	 * Clamps a value between a minimum value and a maximum value
	 * @param min
	 * @param max
	 * @param val
	 * @return
	 */
	public static double clamp(double min, double max, double val){
		if(val>max){
			val=max;
		}else if(val<min){
			val=min;
		}
		return val;
	}
	
	
	/**
	 * Clamps all values in array between a minimum value and a maximum value
	 * @param min
	 * @param max
	 * @param val
	 * @return
	 */
	public static double[] clamp(double min, double max, double[] val){
		for(int i=1;i<=val.length;i++){
			val[i-1]=clamp(min,max,val[i-1]);
		}
		return val;
	}
	
	
	
}
