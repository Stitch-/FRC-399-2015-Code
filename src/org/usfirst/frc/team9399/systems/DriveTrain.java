package org.usfirst.frc.team9399.systems;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Gyro;
import org.usfirst.frc.team9399.util.SubSystem;

public class DriveTrain implements SubSystem{
	Talon flMotor,frMotor,blMotor,brMotor;
	Gyro gyro;
	public int state=0;
	double[] heading = {0,0,0}; //x,y,rotation
	double[] powers = new double[4];
	
	public DriveTrain(int FLPort, int FRPort, int BLPort, int BRPort, int GyroPort, 
			double gyroSensitivity){		
		flMotor = new Talon(FLPort);
		frMotor = new Talon(FRPort);
		blMotor = new Talon(BLPort);
		brMotor = new Talon(BRPort);
		gyro = new Gyro(GyroPort);
		calibrateGyro(gyroSensitivity);
	}
	
	public class states{
		public static final int FIELD_CENTRIC = 1;
		public static final int ROBOT_CENTRIC = 2;
		public static final int DISABLED = 0;
		public static final int RESET_FIELD_REF = 3;
	}
	
	public void setState(int state){
		this.state=state;
	}
	
	public void calibrateGyro(double sensitivity){
		gyro.reset();
		gyro.setSensitivity(sensitivity);
	}
	
	public void setHeading(double[] vector){
		heading=vector;
	}
	
	protected void setMotors(){
		powers[0]=heading[0]+heading[1]+heading[2];
		powers[1]=-heading[0]+heading[1]-heading[2];
		powers[2]=-heading[0]+heading[1]+heading[2];
		powers[3]=heading[0]+heading[1]-heading[2];
		flMotor.set(powers[0]);
		frMotor.set(-powers[1]);
		blMotor.set(powers[2]);
		brMotor.set(-powers[3]);
		
	}
	
	public void run(){
		switch(this.state){
			case states.DISABLED:
				System.out.println("DriveTrain is disabled.");
				heading[0]=0;
				heading[1]=0;
				heading[2]=0;
				
			break;
			case states.FIELD_CENTRIC:
				double yaw = Math.toRadians(gyro.getAngle());
				double x = heading[0];
				double y = heading[1];
				double sin = Math.sin(yaw);
				double cos = Math.cos(yaw);
				
				heading[0] = x * cos - y * sin;
				heading[1] = x * sin + y * cos;
			break;
			case states.ROBOT_CENTRIC:
			break;
			
			case states.RESET_FIELD_REF:
				gyro.reset();
			break;
		}
		setMotors();
		
		
	}
}