package org.usfirst.frc.team9399.systems;

import edu.wpi.first.wpilibj.VictorSP;

import org.usfirst.frc.team9399.util.SubSystem;

public class Intake extends SubSystem{
	VictorSP left,right;
	double speed=0.0;
	double speedMult;
	
	public Intake(int[] ports,double speed){
		left=new VictorSP(ports[0]);
		right=new VictorSP(ports[1]);
		speedMult=speed;
	}
	
	public class states{
		public static final int DISABLED=0;
		public static final int ACTIVE=1;
	}
	
	public void setWheels(double powa){
		speed=-powa*speedMult;
	}
	
	public void run(){
		switch(this.state){
			case states.ACTIVE:
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
