package org.usfirst.frc.team9399.systems;

import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Encoder;

import org.usfirst.frc.team9399.util.PIDLoop;
import org.usfirst.frc.team9399.util.SubSystem;

public class DriveTrain extends SubSystem{
	public static final double nonTurboMax=0.4; //max speed w/out turbo
	
	VictorSP[] motors=new VictorSP[4];
	Encoder[] encoders=new Encoder[4];
	IMU gyro = IMU.getInstance();
	double[] commandVector = {0,0,0}; //x,y,rotation
	double[] powers = new double[4];
	int[] encoderPorts = new int[8];
	int orbitRotation = 0;
	boolean freed=true;
	PIDLoop pidAng;
	PIDLoop pidWheel;
	boolean turbo=false;
	final boolean pidOnWheels=false;
	double ticksToInches;
	double angOut;
	
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
	public DriveTrain(int[] ports, int[] encoderPorts,double ticks){
		this.encoderPorts=encoderPorts;
		initEncoders();
		for(int i=0;i<4;i++){
			motors[i]=new VictorSP(ports[i]);
		}
		calibrateGyro();
		ticksToInches=ticks;
	}
	
	
	public class states{
		public static final int FIELD_CENTRIC = 1;
		public static final int ROBOT_CENTRIC = 2;
		public static final int DISABLED = 0;
		public static final int RESET_FIELD_REF = 3;
		public static final int FIELD_CENTRIC_W_GYRO_HOLD = 4;
		public static final int TANK_DRIVE=5;
		public static final int FIELD_CENTRIC_WHEEL_CORRECT=6;
		public static final int PRINT_FROM_ENCODERS=7;
	}
	
	public void initPid(double[] gyro,double[] wheels){
		pidAng=new PIDLoop(gyro[0],gyro[1],gyro[2]);
		pidWheel=new PIDLoop(wheels[0],wheels[1],wheels[2]);
	}
	
	public void freeEncoders(){
		for(int i=0;i<4;i++){
			encoders[i]=null;
		}
	}
	
	public void initEncoders(){
		if(freed){	
			int firstPort=0;
			int lastPort=0;
			for(int i=0;i<4;i++){
				firstPort=i*2;
				lastPort=firstPort+1;
				encoders[i]=new Encoder(encoderPorts[firstPort],encoderPorts[lastPort]);
				encoders[i].reset();
			}
		}
		freed=false;
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
	
	public double getEncoderDistance(int id){
		return encoders[id].get()*ticksToInches;
	}
	
	public double getEncoderAverage(){
		double total=0;
		for(int i=2;i<4;i++){
			int neg=(i%2!=0)?-1:1;
			if(i!=1){
				total+=neg*getEncoderDistance(i);
			}
		}
		double mean=total/3;
		return mean;
	}
	
	public void setHeading(double[] vector){
		commandVector=vector;
		//System.out.println("Powers "+commandVector[0]+" "+commandVector[1]+" "+commandVector[2]);
	}
	
	public void setHeadingAng(double[] vector, double angTar){
		double[] vectorOut = {0,0,0};
		double angCurr = gyro.IMUA.getYaw();
		angOut = pidAng.correct(angTar, angCurr);
		vectorOut[0]=vector[0];
		vectorOut[1]=vector[1];
		vectorOut[2]=angOut;
		setHeading(vectorOut);
	}
	
	//public void checkDistance

	public void setTurbo(boolean in){
		turbo=in;
	}
	
	public void setYawTarget(double in){
		yawTarget=in;
	}
	
	public double getPidVal(){
		return angOut;
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
		System.out.println(yaw);
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
				/*if(!freed){
					freeEncoders();
					freed=true;
				}*/
				for(int i=0;i<4;i++){
					encoders[i].reset();
				}
				commandVector[0]=0;
				commandVector[1]=0;
				commandVector[2]=0;
				
			break;
			case states.FIELD_CENTRIC:
				rotated = fieldCentricRotate(commandVector[0], commandVector[1]);
				commandVector[0] = rotated[0];
				commandVector[1] = rotated[1];
				//System.out.println(getEncoderAverage());
			break;
			case states.ROBOT_CENTRIC:
				//System.out.println(getEncoderAverage());
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
				for(int i=0;i<4;i++){
					encoders[i].reset();
				}
			break;
			case states.FIELD_CENTRIC_WHEEL_CORRECT:
				rotated = fieldCentricRotate(commandVector[0], commandVector[1]);
				commandVector[0] = rotated[0];
				commandVector[1] = rotated[1];
			break;
			case states.PRINT_FROM_ENCODERS:
				System.out.println(getEncoderDistance(3));
			break;
		}
		setMotors();
		
		
	}
}