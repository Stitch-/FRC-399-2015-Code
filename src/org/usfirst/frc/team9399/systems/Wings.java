package org.usfirst.frc.team9399.systems;

import org.usfirst.frc.team9399.util.SubSystem;

import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.DigitalInput;

public class Wings extends SubSystem{
	int threshold;
	Victor leftWing,rightWing;
	Solenoid leftHook,rightHook;
	double motorSpeed;
	DigitalInput[] limitSwitches; //0 is left, 2 is right, odds are extended, refer to index
	boolean[] commandArray; //0-1 are right, 2-3 are left
	
	public Wings(int[] motorPorts,int[] solPorts,int threshold,int pcmID,double speed,int[] buttonChannels){
		this.threshold=threshold;
		leftWing=new Victor(motorPorts[0]);
		rightWing=new Victor(motorPorts[1]);
		leftHook=new Solenoid(pcmID,solPorts[0]);
		rightHook=new Solenoid(pcmID,solPorts[1]);
		leftHook.set(false);
		rightHook.set(false);
		limitSwitches=new DigitalInput[4];
		motorSpeed=speed;
		for(int i=0;i<4;i++){
			limitSwitches[i]=new DigitalInput(buttonChannels[i]);
		}
		commandArray=new boolean[4];
	}
	
	public class states{
		public static final int DISABLED=0;
		public static final int ENABLED=1;
	}
	
	
	public class wings{
		public static final boolean LEFT=true;
		public static final boolean RIGHT=false;
	}
	
	
	public void set(double lIn,double rIn){
		leftWing.set(lIn);
		rightWing.set(rIn);
	}
	
	public void setCommand(boolean[] in){
		commandArray=in;
	}
	
	public void actuateHook(boolean wing,boolean down){
		if(wing){
			leftHook.set(down);
		}else{
			rightHook.set(down);
		}
	}
	
	public boolean getHook(boolean wing){
		boolean out;
		if(wing){
			out=leftHook.get();
		}else{
			out=rightHook.get();
		}
		return out;
	}
	
	
	public void run(){
		switch(state){
			case(states.ENABLED):
				if(commandArray[0] && limitSwitches[3].get()){
					rightWing.set(-motorSpeed);
				}else if(commandArray[1] && limitSwitches[2].get()){
					rightWing.set(motorSpeed);
				}else{
					rightWing.set(0);
				}
				 
				if(commandArray[2] && limitSwitches[1].get()){
					leftWing.set(-motorSpeed);
				}else if(commandArray[3] && limitSwitches[0].get()){
					leftWing.set(motorSpeed);
				}else{
					leftWing.set(0);
				}
				
			break;
			case(states.DISABLED):
				leftWing.set(0);
				rightWing.set(0);
				leftHook.set(false);
				rightHook.set(false);
			break;
			
		}
		
	}
}