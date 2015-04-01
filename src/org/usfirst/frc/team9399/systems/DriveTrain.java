package org.usfirst.frc.team9399.systems;


import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.smartdashboard.*;

import org.usfirst.frc.team9399.util.PIDLoop;
import org.usfirst.frc.team9399.util.SubSystem;
import org.usfirst.frc.team9399.util.PhoenixMath;

public class DriveTrain extends SubSystem{
	final double nonTurboMax=0.4; //max speed w/out turbo
	//final double checkPrecision=10000E20;
	double ticksToInches,angOut; //constant for encoders, target rotation value after pid
	double lastYaw,yawOut,startingAng=0; //the last measured yaw, the current yaw, the angle the robot starts at
	double[] commandVector = {0,0,0}; //x,y,rotation
	double[] powers = new double[4]; //target motor pwn vals
	
	final double cWidth=25; //inches
	final double cLength=28.5; 
	final double wDiameter = 6; // Inch wheels
	final double wRadius = wDiameter / 2.0; 
	
	// For details visit:
	// http://www.chiefdelphi.com/media/papers/download/2722
	final double cRotK = ((cWidth + cLength) / 2.0) / wRadius;
	final double invMatrix[][] = new double[][] {
		{ 1, 1,  cRotK},
		{-1, 1, -cRotK},
		{-1, 1,  cRotK},
		{ 1, 1, -cRotK},
	};
	
	int failStreak=0; //how many times has the gyro repeated its value?
	int failThreshold; //how many repeats should require the program to switch to the analog gyro?
	int[] encoderPorts = new int[8]; //the coder ports
	
	boolean turbo,hasFailed=false; //is the turbo value on, has the program switched to the analog gyro
	boolean freed=true; //have the encoders been realeased/not yet instantiated?
	//final boolean pidOnWheels=false;
	
	
	VictorSP[] motors=new VictorSP[4];
	Encoder[] encoders=new Encoder[4];
	IMU gyro = IMU.getInstance();
	PIDLoop pidAng;
	PIDLoop pidWheel;
	Gyro gyroAux;
	
	
	/**
	 * @Author Aaron Elersich (aelersich1@gmail.com)
	 * @param ports
	 * @param encoderPorts
	 * @param ticks
	 * @param fails
	 */
	//fl.fr.bl.br
	public DriveTrain(int[] ports, int[] encoderPorts,double ticks,int fails){
		this.encoderPorts=encoderPorts;
		initEncoders();
		for(int i=0;i<4;i++){
			motors[i]=new VictorSP(ports[i]);
		}
		calibrateGyro();
		ticksToInches=ticks;
		gyro=IMU.getInstance(); 
		gyroAux=new Gyro(0);
		failThreshold=fails;
	}
	
	/**states governing the drivetrain*/
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
	
	/**
	 * initialize the pidController for angle correction
	 * @param gyro
	 * @param wheels
	 */
	public void initPid(double[] gyro,double[] wheels){
		pidAng=new PIDLoop(gyro[0],gyro[1],gyro[2]);
		pidWheel=new PIDLoop(wheels[0],wheels[1],wheels[2]);
	}
	
	/**
	 * Set the starting angle
	 * @param in
	 */
	public void setStartingAng(double in){
		gyro.IMUA.zeroYaw();
		if(gyroAux!=null)gyroAux.reset();
		startingAng=in;
	}
	
	/**
	 * Get the current angle
	 * @return
	 */
	public double getYaw(){
		return yawOut;
	}
	
	/**
	 * Release the encoders, allowing them to be re-intitialized
	 */
	public void freeEncoders(){
		for(int i=0;i<4;i++){
			encoders[i]=null;
		}
		freed=true;
	}
	
	/**
	 * Init the encoders
	 */
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
	
	
	/**
	 * re-calibrate both gyros
	 */
	public void calibrateGyro(){
		boolean isCal= true;
		for(int i = 0; i < 50 && isCal;i++)
		{
			isCal = gyro.IMUA.isCalibrating();
			Timer.delay(0.1);
		}
		gyro.IMUA.zeroYaw();
		if(gyroAux!=null)gyroAux.reset();
	}
	
	/**get the distance traveled*/
	public double getEncoderDistance(int id){
		return encoders[id].get()*ticksToInches;
	}
	
	/**get the distance based on four encoders, needs work*/
	public double getEncoderAverage(){
		double total=0;
		for(int i=2;i<4;i++){
			int neg=(i%2!=0)?-1:1;
			if(i!=1){
				total+=neg*getEncoderDistance(i);
			}
		}
		double mean=total/4;
		return mean;
	}
	
	/**set the heading vector*/
	public void setHeading(double[] vector){
		commandVector=vector;
		//System.out.println("Powers "+commandVector[0]+" "+commandVector[1]+" "+commandVector[2]);
	}
	
	/**set the heading based on a target angle, rather than joystick vals*/
	public void setHeadingAng(double[] vector, double angTar){
		double[] vectorOut = {0,0,0};
		//double angCurr = /*gyro.getAngle();*/gyro.IMUA.getYaw();
		angOut = pidAng.correct(angTar, yawOut);// angCurr);
		vectorOut[0]=vector[0];
		vectorOut[1]=vector[1];
		vectorOut[2]=angOut;
		setHeading(vectorOut);
	}
	
	//public void checkDistance
	
	/**set turbo on/off*/
	public void setTurbo(boolean in){
		turbo=in;
	}
	
	/**Set the yaw target*/
	public void setYawTarget(double in){
		yawTarget=in;
	}
	
	//return the current pid val for anglular correction, useful for debugging
	public double getPidVal(){
		return angOut;
	}
	
	/**set the motors' pwn vals*/
	private void setMotors(){
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
	
	/**correct the strafe and drive vals based on angle*/
	private double[] fieldCentricRotate(double x, double y)
	{
		double[] rotated = new double[2];
		String out;
		double yaw=0;
		if(!hasFailed){
			yaw=gyro.IMUA.getYaw()+startingAng;
			if(yaw>=360){
				yaw-=360;
			}else if(yaw<0){
				yaw+=360;
			}
			out="Main";
		}else{
			yaw=gyroAux.getAngle()+startingAng;
			/*if (Math.abs(yaw) > 180) {
                if (yaw > 0) {
                    yaw -= 360;
                } else {
                   yaw += 360;
                }
            }*/
			while(yaw>=360){
				yaw-=360;
			}
			while(yaw<0){
				yaw+=360;
			}
			out="Aux";
			System.out.print("(Aux)");
			//System.out.println(ang);
		}
		System.out.println("Yaw: "+yaw);
		SmartDashboard.putString("Gyro:",out);
		yawOut=yaw;	
		yaw=Math.toRadians(yaw);
		boolean isEqual=PhoenixMath.checkDouble(lastYaw,yaw);
		
		if(!hasFailed && isEqual){
			failStreak++;
			System.out.println("Repeat detected: Streak: "+failStreak);
		}else{
			failStreak=0;
		}
		
		if(failStreak >= failThreshold){
			
			System.out.println("Gyro has failed! Switching to auxilliary!");
			hasFailed=true;
		}
		//System.out.println(lastYaw);
		//System.out.println(yaw);
		double sin = Math.sin(yaw);
		double cos = Math.cos(yaw);
		lastYaw=yaw;
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
	            if (yawTarget > 360) {
	               yawTarget -= 360;
	            } else if(yawTarget<0) {
	               yawTarget += 360;
	            }
				setHeadingAng(rotated,yawTarget);
			break;
			case states.RESET_FIELD_REF:
				gyro.IMUA.zeroYaw();
				gyroAux.reset();
				yawTarget=0;
				for(int i=0;i<4;i++){
					encoders[i].reset();
				}
				setStartingAng(0);
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