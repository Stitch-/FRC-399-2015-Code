package org.usfirst.frc.team9399.systems;

import org.usfirst.frc.team9399.util.SubSystem;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.AnalogInput;

public class Wings extends SubSystem{
	int leftState,rightState,threshold;
	Victor leftWing,rightWing;
	Solenoid leftHook,rightHook;
	double motorSpeed;
	AnalogInput[] hal; //0 is left, 2 is right, odds are extended, refer to index
	
	public Wings(int left,int right,int leftSol,int rightSol,int threshold,int pcmID,double speed,int[] buttonChannels){
		this.threshold=threshold;
		leftWing=new Victor(left);
		rightWing=new Victor(right);
		leftHook=new Solenoid(pcmID,leftSol);
		rightHook=new Solenoid(pcmID,rightSol);
		leftHook.set(false);
		rightHook.set(false);
		hal=new AnalogInput[4];
		motorSpeed=speed;
		for(int i=0;i<4;i++){
			hal[i]=new AnalogInput(buttonChannels[i]);
		}
	}
	
	public class states{
		public static final int RETRACTED=0;
		public static final int EXTENDED=1;
	}
	
	public class wings{
		public static final boolean LEFT=true;
		public static final boolean RIGHT=false;
	}
	
	private class index{
		public static final int LEFT_RETRACTED=0;
		public static final int	LEFT_EXTENDED=1;
		public static final int RIGHT_RETRACTED=2;
		public static final int RIGHT_EXTENDED=3;
	}
	
	
	public int getState(boolean wing){
		if(wing){
			return leftState;
		}else{
			return rightState;
		}
	}
	
	public void setState(int state){
		leftState=state;
		rightState=state;
	}
	
	public void setState(boolean wing,int state){
		if(wing){
			leftState=state;
		}else{
			rightState=state;
		}
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
		switch(leftState){
			case(states.EXTENDED):
				if(hal[index.LEFT_EXTENDED].getValue()<threshold){
					leftWing.set(motorSpeed);
				}
			break;
			case(states.RETRACTED):
				if(hal[index.LEFT_RETRACTED].getValue()<threshold){
					leftWing.set(-motorSpeed);
				}
			break;
		}
		
		switch(rightState){
			case(states.EXTENDED):
				if(hal[index.RIGHT_EXTENDED].getValue()<threshold){
					rightWing.set(-motorSpeed);
				}
			break;
			case(states.RETRACTED):
				if(hal[index.RIGHT_RETRACTED].getValue()<threshold){
					rightWing.set(motorSpeed);
				}
			break;
		}
	}
}