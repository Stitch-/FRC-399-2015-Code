package org.usfirst.frc.team399.util;

public class Toggler {
	boolean val,isBeingToggled=false;
	
	public void set(boolean in){
		if(!isBeingToggled && in){
			if(val){
				val=false;
			}else{
				val=true;
			}
			isBeingToggled=true;
		}else if(!in){
			isBeingToggled=false;
		}
	}
	
	public boolean get(){
		return val;
	}
	
}
