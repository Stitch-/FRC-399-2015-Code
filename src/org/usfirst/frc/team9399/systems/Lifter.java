package org.usfirst.frc.team9399.systems;

import org.usfirst.frc.team9399.util.SubSystem;
import org.usfirst.frc.team9399.util.PIDLoop;

import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.PowerDistributionPanel;

public class Lifter extends SubSystem {
	VictorSP[] motors=new VictorSP[2];
	Solenoid sol;
	Encoder coder;
	AnalogInput lowerSwitch1,lowerSwitch2, upperSwitch;
	double leadScrewConstant,maxHeight,minHeight,deadband,startingAmp;
	double speed=0;
	int threshold,c1,c2,turns;
	PowerDistributionPanel pdp;
	PIDLoop pidLoop;
	boolean isSwitchClosed=false;
	
	public Lifter(int[] motorPorts,int solPort,int[] encoderPorts,double constant,int turn,double max,
			int[] switchPorts,int threshold,double band,int[] terminals,double[] pidVals){
		for(int i=0;i<2;i++){
			motors[i]=new VictorSP(motorPorts[i]);
		}
		sol=new Solenoid(solPort);
		sol.set(false);
		pdp=new PowerDistributionPanel();
		lowerSwitch1 = new AnalogInput(switchPorts[0]);
		lowerSwitch2 = new AnalogInput(switchPorts[1]);
		upperSwitch = new AnalogInput(switchPorts[2]);
		leadScrewConstant=constant;
		turns=turn;
		maxHeight=max;
		minHeight=0;
		this.threshold=threshold;
		deadband=band;
		c1=terminals[0];
		c2=terminals[1];
		pidLoop=new PIDLoop(pidVals[0],pidVals[1],pidVals[2]);
		startingAmp=getAmperage();
		//coder=new Encoder(encoderPorts[0],encoderPorts[1],false);
		//coder.reset(); //Lifter should be placed in lowest possible positions when starting.
	}
	
	public class states{
		public static final int DISABLED=0;
		public static final int ENABLED=1;
	}
	
	/*public double getLeadScrewDistance(){
		int count=coder.get();
		double rotations=count/turns;
		double distance=rotations*leadScrewConstant;
		return distance;
	}*/
	
	public void setSpeed(double in){
		speed=in;
	}
	
	public void actuateClaw(boolean open){
		sol.set(open);
	}
	
	public boolean getClaw(){
		boolean out=sol.get();
		return out;
	}
	
	public boolean getSwitch(){
		return isSwitchClosed;
	}
	
	private void runMotors(){
		boolean s1=lowerSwitch1.getValue() < threshold;
		boolean s2=lowerSwitch2.getValue() < threshold;
		//System.out.println(s1+","+s2);
		boolean bandVal = speed < deadband && speed > -deadband;
		boolean lowerVal = s1 || s2;
		boolean upperVal = upperSwitch.getValue() < threshold;
		//System.out.println(s1+","+s2+","+upperVal);
		//adjustSpeed();
		double motorSpeed = speed;
		lowerVal = lowerVal && (speed < 0);
		upperVal = upperVal && (speed > 0);
		if(lowerVal||bandVal||upperVal) {
			motorSpeed = 0;
		}
		//System.out.println(lowerVal+","+bandVal+","+upperVal);
		isSwitchClosed=lowerVal||upperVal;
		
		setMotors(motorSpeed);
		/*double distance=getLeadScrewDistance();
		if(speed>0 && distance<maxHeight){
			setMotors(speed);q
		}else if(speed<0 && distance>minHeight){
			setMotors(speed);
		}*/
		
		
	}
	private void adjustSpeed(){
		double displacement=pidLoop.correct(startingAmp,getAmperage());
		speed+=displacement;
	}
	
	private double getAmperage(){
		double a1=pdp.getCurrent(c1);
		double a2=pdp.getCurrent(c2);
		double out=Math.max(a1, a2);
		return out;
	}
	
	
	private void setMotors(double speed){
		for(int i=0;i<2;i++){
			motors[i].set(speed);
		}
	}
	
	public void run(){
		switch(state){
			case states.ENABLED:
				runMotors();
			break;
			
			case states.DISABLED:
				
			break;
		}
		
	}
	
}

