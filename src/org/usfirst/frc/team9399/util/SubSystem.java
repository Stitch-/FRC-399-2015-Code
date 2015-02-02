package org.usfirst.frc.team9399.util;

public abstract class SubSystem {
	
	// an interface to allow easier implementation of subsystems. 
	//TODO add a system to handle communication between systems.
	
	int state=0;
	
	public abstract void run();
	
	public class states{
		public static final int DISABLED = 0;
	}
	
	public void setState(int in){
		state=in;
	}
	
	public int getState(){
		return state;
	}
	
	
}