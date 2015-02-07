package org.usfirst.frc.team9399.systems;

import edu.wpi.first.wpilibj.VictorSP;

import org.usfirst.frc.team9399.util.SubSystem;

public class Intake extends SubSystem{
	VictorSP left,right;
	double speed=0.0;
	double band=0.1;
	
	public Intake(int leftPort,int rightPort){
		left=new VictorSP(leftPort);
		right=new VictorSP(rightPort);
	}
	
	public void setWheels(double powa){
		speed=powa;
	}
	
	public class states{
		public static final int DISABLED=0;
		public static final int ACTIVE=1;
	}
	
	public void run(){
		switch(this.state){
			case states.ACTIVE:
				if(speed<=-band||speed>=band){
					left.set(-speed);
					right.set(speed);
				}else{
					left.set(0);
					right.set(0);
				}
			break;
			case states.DISABLED:
				left.set(0);
				right.set(0);
			break;
		}
	}
}
