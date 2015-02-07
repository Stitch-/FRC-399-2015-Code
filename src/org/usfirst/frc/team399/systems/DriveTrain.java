package org.usfirst.frc.team399.systems;

import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Encoder;

import org.usfirst.frc.team399.util.PIDLoop;
import org.usfirst.frc.team399.util.SubSystem;
//import org.usfirst.frc.team9399.util.PhoenixMath;

public class DriveTrain extends SubSystem{
	public static final double nonTurboMax=0.4; //max speed w/out turbo
	
	VictorSP[] motors;
	Encoder[] encoders;
	IMU gyro = IMU.getInstance();
	double[] commandVector = {0,0,0}; //x,y,rotation
	double[] powers = new double[4];
	int orbitRotation = 0;
	PIDLoop pidAng;
	PIDLoop pidWheel;
	boolean turbo=false;
	final boolean pidOnWheels=false;
	
	final double cWidth=25; //inches
	final double cLength=28.5;
	final double wDiameter = 6; // Inch wheels
	final double wRadius = wDiameter / 2.0;
	
	// For details visit 
	// http://www.chiefdelphi.com/media/papers/download/2722
	final double cRotK = ((cWidth + cLength) / 2.0) / wRadius;
	final double invMatrix[][] = new double[][] {
		{ 1, 1,  cRotK},
		{-1, 1, -cRotK},
		{-1, 1,  cRotK},
		{ 1, 1, -cRotK},
	};
	
	//fl.fr.bl.br
	public DriveTrain(int[] ports, int[] encoderPorts){
		int firstPort=0;
		int lastPort=0;
		for(int i=0;i<4;i++){
			motors[i]=new VictorSP(ports[i]);
			lastPort++;
			encoders[i]=new Encoder(firstPort,lastPort);
			firstPort=lastPort+1;
		}
		calibrateGyro();
	}
	
	
	public class states{
		public static final int FIELD_CENTRIC = 1;
		public static final int ROBOT_CENTRIC = 2;
		public static final int DISABLED = 0;
		public static final int RESET_FIELD_REF = 3;
		public static final int FIELD_CENTRIC_W_GYRO_HOLD = 4;
		public static final int TANK_DRIVE=5;
	}
	
	public void initPid(double[] gyro,double[] wheels){
		pidAng=new PIDLoop(gyro[0],gyro[1],gyro[2]);
		pidWheel=new PIDLoop(wheels[0],wheels[1],wheels[2]);
	}
	
	
	public void calibrateGyro(){
		boolean isCal= true;
		for(int i = 0; i < 50 && isCal;i++)
		{
			isCal = gyro.IMUA.isCalibrating();
			Timer.delay(0.1);
		}
		gyro.IMUA.zeroYaw();
	}
	
	public void setHeading(double[] vector){
		commandVector=vector;
		//System.out.println("Powers "+commandVector[0]+" "+commandVector[1]+" "+commandVector[2]);
	}
	
	public void setHeadingAng(double[] vector, double angTar){
		double[] vectorOut = {0,0,0};
		double angCurr = gyro.IMUA.getYaw();
		double angOut = pidAng.correct(angTar, angCurr);
		System.out.println(angOut);
		vectorOut[0]=vector[0];
		vectorOut[1]=vector[1];
		vectorOut[2]=angOut;
		setHeading(vectorOut);
	}
	
	public void setTurbo(boolean in){
		turbo=in;
	}
	
	protected void setMotors(){
		double max=1;
		
		if(!turbo){
			max=nonTurboMax;
		}
		for(int wheel = 0; wheel < 4; wheel++)
		{
			powers[wheel] = 0;
			for(int i = 0; i < 3; i++)
			{
				powers[wheel] += commandVector[i] * invMatrix[wheel][i];
			}
			if(turbo){
				max = Math.max(max, Math.abs(powers[wheel]));
			}
		}
		
		
		for(int i = 0; i < 4; i ++)
		{
			powers[i] = powers[i]*max;
		}
		
		//PhoenixMath.clamp(-1.0, 1.0, powers);
		for(int i=0;i<4;i++){
			byte negate=1;
			if(i%2!=0){
				negate=-1;
			}else{
				negate=1;
			}
			motors[i].set(negate*powers[i]);
		}
		/*flMotor.set(powers[0]);
		frMotor.set(-powers[1]);
		blMotor.set(powers[2]);
		brMotor.set(-powers[3]);*/
		
		
	}
	
	
	private double[] fieldCentricRotate(double x, double y)
	{
		double[] rotated = new double[2];
		double yaw = Math.toRadians(gyro.IMUA.getYaw());
		double sin = Math.sin(yaw);
		double cos = Math.cos(yaw);
		
		rotated[0] = x * cos - y * sin;
		rotated[1] = x * sin + y * cos;
		return rotated;
	}
	
	double yawTarget = 0;
	double twistToYawScalar = 4;
	
	public void run(){
		double[] rotated;
		switch(getState()){
			case states.DISABLED:
				//System.out.println("DriveTrain is disabled.");
				commandVector[0]=0;
				commandVector[1]=0;
				commandVector[2]=0;
				
			break;
			case states.FIELD_CENTRIC:
				rotated = fieldCentricRotate(commandVector[0], commandVector[1]);
				commandVector[0] = rotated[0];
				commandVector[1] = rotated[1];
			break;
			case states.ROBOT_CENTRIC:
			break;
			case states.FIELD_CENTRIC_W_GYRO_HOLD:
				rotated = fieldCentricRotate(commandVector[0], commandVector[1]);
				yawTarget += commandVector[2] * twistToYawScalar;
				if (Math.abs(yawTarget)
	                    > 180) {
	                if (yawTarget > 0) {
	                    yawTarget -= 360;
	                } else {
	                   yawTarget += 360;
	                }
	            }
				setHeadingAng(rotated,yawTarget);
			break;
			case states.RESET_FIELD_REF:
				gyro.IMUA.zeroYaw();
				yawTarget=0;
			break;
		}
		setMotors();
		
		
	}
}