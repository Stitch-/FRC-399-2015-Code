package org.usfirst.frc.team399.systems;

import org.usfirst.frc.team399.util.SubSystem;

import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Encoder;

public class Lifter extends SubSystem {
	Victor[] motors=new Victor[2];
	Solenoid[] sols = new Solenoid[2];
	Encoder coder;
	int turns;
	double leadScrewConstant,maxHeight,minHeight;
	double speed=0;
	
	public Lifter(int[] motorPorts,int[] solPorts,int[] encoderPorts,double constant,int turn,double max){
		for(int i=0;i<2;i++){
			motors[i]=new Victor(motorPorts[i]);
			sols[i]=new Solenoid(solPorts[i]);
			sols[i].set(false);
		}
		coder=new Encoder(encoderPorts[0],encoderPorts[1],false);
		coder.reset(); //Lifter should be placed in lowest possible positions when starting.
		leadScrewConstant=constant;
		turns=turn;
		maxHeight=max;
		minHeight=0;
	}
	
	public class states{
		public static final int BRAKE=0;
		public static final int ACTIVE=1;
	}
	
	public double getLeadScrewDistance(){
		int count=coder.get();
		double rotations=count/turns;
		double distance=rotations*leadScrewConstant;
		return distance;
	}
	
	public void setSpeed(double in){
		speed=in;
	}
	
	public void actuateClaw(boolean open){
		for(int i=0;i<2;i++){
			sols[i].set(open);
		}
	}
	
	private void runMotors(){
		double distance=getLeadScrewDistance();
		if(speed>0 && distance<maxHeight){
			setMotors(speed);
		}else if(speed<0 && distance>minHeight){
			setMotors(speed);
		}
	}
	
	private void setMotors(double speed){
		for(int i=0;i<2;i++){
			motors[i].set(speed);
		}
	}
	
	public void run(){
		switch(state){
			case states.ACTIVE:
				runMotors();
			break;
			
			case states.BRAKE:
				
			break;
		}
		
	}
	
}

