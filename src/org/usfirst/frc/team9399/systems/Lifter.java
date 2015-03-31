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
	Solenoid grabberSol,lifterSol;
	Encoder coder;
	AnalogInput lowerSwitch1,lowerSwitch2, upperSwitch;
	double leadScrewConstant,maxHeight,minHeight,deadband,startingAmp;
	double speed=0;
	int threshold,c1,c2,turns;
	PowerDistributionPanel pdp;
	PIDLoop pidLoop;
	boolean isBottomClosed,isTopClosed=false;
	
	
	
	/**
	 * @author Aaron Elersich (aelersich1@gmail.com)
	 * @param motorPorts
	 * @param solPorts
	 * @param encoderPorts
	 * @param constant
	 * @param turn
	 * @param max
	 * @param switchPorts
	 * @param threshold
	 * @param band
	 * @param PDPTerminals
	 * @param pidVals
	 */
	public Lifter(int[] motorPorts,int[] solPorts,int[] encoderPorts,double constant,int turn,double max,
			int[] switchPorts,int threshold,double band,int[] PDPTerminals,double[] pidVals){
		for(int i=0;i<2;i++){
			motors[i]=new VictorSP(motorPorts[i]);
		}
		grabberSol=new Solenoid(solPorts[0]);
		grabberSol.set(false);
		lifterSol=new Solenoid(solPorts[1]);
		lifterSol.set(false);
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
		c1=PDPTerminals[0];
		c2=PDPTerminals[1];
		pidLoop=new PIDLoop(pidVals[0],pidVals[1],pidVals[2]);
		startingAmp=getAmperage();
		//coder=new Encoder(encoderPorts[0],encoderPorts[1],false);
		//coder.reset(); //Lifter should be placed in lowest possible positions when starting.
	}
	
	public class states{
		public static final int DISABLED=0;
		public static final int ENABLED=1;
	}
	
	
	/**Returns the distance traveled by the lifter, based on an encoder value*/
	/*public double getLeadScrewDistance(){
		int count=coder.get();
		double rotations=count/turns;
		double distance=rotations*leadScrewConstant;
		return distance;
	}*/
	
	/**Sets the intended speed for the lifter*/
	public void setSpeed(double in){
		speed=in;
	}
	
	/**Actuates the RC-grabber*/
	public void actuateClaw(boolean in){
		if(grabberSol.get()!=in){
			grabberSol.set(in);
		}
	}
	
	/**Actuates the clips on the lifter. Not used now, but may be in the future*/
	public void actuateClips(boolean in){
		if(lifterSol.get()!=in){
			lifterSol.set(in);
		}
	}
	
	/**What is the RC-grabber's state?*/
	public boolean getClaw(){
		boolean out=grabberSol.get();
		return out;
	}
	
	/**Is a switch triggered? True for bottom, false for top.*/
	public boolean getSwitch(boolean select){
		return select?isBottomClosed:isTopClosed;
	}
	
	
	/**runs the motors, based on input values and limit switches*/
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
		isBottomClosed=lowerVal;
		isTopClosed=upperVal;
		
		setMotors(motorSpeed);
		/*double distance=getLeadScrewDistance();
		if(speed>0 && distance<maxHeight){
			setMotors(speed);q
		}else if(speed<0 && distance>minHeight){
			setMotors(speed);
		}*/
		
		
	}
	
	
	/**Use a pid controller to adjust the speed of the lifter based on current. 
	 * originally intended as a fail-safe to prevent damaged when the lead screw was binding.
	 * Not used now as the problem is more or less solved mechanically.
	 */
	/*private void adjustSpeed(){
		double displacement=pidLoop.correct(startingAmp,getAmperage());
		speed+=displacement;
	}*/
	
	
	/**Get the total amperage the lifter is drawing*/
	private double getAmperage(){
		double a1=pdp.getCurrent(c1);
		double a2=pdp.getCurrent(c2);
		double out=Math.max(a1, a2);
		return out;
	}
	
	
	/**Set the motors' speed*/
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

