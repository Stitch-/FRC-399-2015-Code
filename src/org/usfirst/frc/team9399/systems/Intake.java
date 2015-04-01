package org.usfirst.frc.team9399.systems;

import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.DigitalInput;

import org.usfirst.frc.team9399.util.SubSystem;

public class Intake extends SubSystem{
	VictorSP left,right; //intake motors
	double speed=0; //PWN value for motors
	double speedMult; //multiplier for PWN value
	Solenoid sol; //solenoid for intake actuators
	DigitalInput button; //input for limit switch to detect when tote has been intaken (intaked? intook?), unused 
	
	/**
	 * @Author Aaron Elersich (aelersich1@gmail.com)
	 * @param ports
	 * @param speed
	 * @param solPort
	 * @param buttonPort
	 */
	
	public Intake(int[] ports,double speed,int solPort,int buttonPort){
		left=new VictorSP(ports[0]);
		right=new VictorSP(ports[1]);
		speedMult=speed;
		sol=new Solenoid(solPort);
		sol.set(false);
		button=new DigitalInput(buttonPort);
		//instantiate that stuff
	}
	
	
	/**
	 * States governing the intake
	 */
	public class states{
		public static final int DISABLED=0;
		public static final int ENABLED=1;
	}
	
	/**Is the intake button pressed?**/
	public boolean getButton(){
		return !button.get();
	}
	
	/**Sets the target wheel speed**/
	public void setWheels(double powa){
		/*if(powa>0 && button.get()){
			speed=0;
		}else{*/
			speed=-powa*speedMult;
		//}
	}
	
	
	/**Actuate the intake actuators**/
	public void actuateIntake(boolean in){
		if(sol.get()!=in){
			sol.set(in);
		}
	}
	
	public void run(){
		switch(this.state){
			case states.ENABLED:
				left.set(-speed);
				right.set(speed);
			break;
			case states.DISABLED:
				left.set(0);
				right.set(0);
			break;
		}
	}
}
