package org.usfirst.frc.team9399.systems;

import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.DigitalInput;

import org.usfirst.frc.team9399.util.SubSystem;

public class Intake extends SubSystem{
	VictorSP left,right;
	double speed=0.0;
	double speedMult;
	Solenoid sol;
	DigitalInput button;
	
	public Intake(int[] ports,double speed,int solPort,int buttonPort){
		left=new VictorSP(ports[0]);
		right=new VictorSP(ports[1]);
		speedMult=speed;
		sol=new Solenoid(solPort);
		sol.set(false);
		button=new DigitalInput(buttonPort);
	}
	
	public class states{
		public static final int DISABLED=0;
		public static final int ENABLED=1;
	}
	
	public boolean getButton(){
		return !button.get();
	}
	
	public void setWheels(double powa){
		/*if(powa>0 && button.get()){
			speed=0;
		}else{*/
			speed=-powa*speedMult;
		//}
	}
	
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
