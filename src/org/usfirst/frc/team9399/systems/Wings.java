package org.usfirst.frc.team9399.systems;

import org.usfirst.frc.team9399.util.SubSystem;

import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.DigitalInput;

public class Wings extends SubSystem{
	int threshold; //Threshold for analog switches
	Victor leftWing,rightWing; //Controllers for motors
	Solenoid leftHook,rightHook; //Solenoids for hooks on the wings
	double motorSpeed; //Speed multiplier for motors
	DigitalInput[] limitSwitches; //0 is left, 2 is right, odds are extended, refer to index
	boolean[] commandArray; //0-1 are right, 2-3 are left
	
	/**
	 * @author Aaron Elersich (aelersich1@gmail.com)
	 * @param motorPorts
	 * @param solPorts
	 * @param threshold
	 * @param pcmID
	 * @param speed
	 * @param buttonChannels
	 */
	
	public Wings(int[] motorPorts,int[] solPorts,int threshold,int pcmID,double speed,int[] buttonChannels){
		this.threshold=threshold;
		leftWing=new Victor(motorPorts[0]);
		rightWing=new Victor(motorPorts[1]);
		leftHook=new Solenoid(pcmID,solPorts[0]);
		rightHook=new Solenoid(pcmID,solPorts[1]);
		leftHook.set(false);//set the solenoids down, default position
		rightHook.set(false);
		//instantiate all the controllers 
		limitSwitches=new DigitalInput[4];
		motorSpeed=speed;
		for(int i=0;i<4;i++){
			limitSwitches[i]=new DigitalInput(buttonChannels[i]);
		}
		//organize the switches into an array for easy access
		commandArray=new boolean[4];
	}
	
	/**
	 *An index for the different states 
	 */
	public class states{
		public static final int DISABLED=0;
		public static final int ENABLED=1;
	}
	
	/**
	 * An index for the different wings
	 */
	public class wings{
		public static final boolean LEFT=true;
		public static final boolean RIGHT=false;
	}
	
	/**sets the motor speeds, bypasses state system, for testing ONLY*/
	public void set(double lIn,double rIn){
		leftWing.set(lIn);
		rightWing.set(rIn);
	}
	
	/**sets the input command for the state system*/
	public void setCommand(boolean[] in){
		commandArray=in;
	}
	
	/**actuates the claws base on input values, claws aren't handled by state system*/
	public void actuateHook(boolean wing,boolean down){
		if(wing){
			leftHook.set(down);
		}else{
			rightHook.set(down);
		}
	}
	
	/**returns the hook value, refer to wing index*/
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