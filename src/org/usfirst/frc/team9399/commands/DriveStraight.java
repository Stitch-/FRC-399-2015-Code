package org.usfirst.frc.team9399.commands;

import org.usfirst.frc.team9399.robot.SuperSystem;
//import org.usfirst.frc.team9399.systems.DriveTrain;
import org.usfirst.frc.team9399.util.PIDLoop;

import edu.wpi.first.wpilibj.command.Command;

public class DriveStraight extends Command {
	SuperSystem ss;
	double deltaDistance=0;
	double p=0.072; //0.9
	double i=0;
	double d=0;
	double startAvg,speed,distance;
	PIDLoop pid;
	final int encoderID1=3; //temporary until I can get an accurate average of all four encoders.
	final int encoderID2=2;
	boolean turbo=true;
	double checkVal;

	public DriveStraight(double timeout,double distance,double speed,boolean turbo) {
		ss=SuperSystem.getInstance();
        setTimeout(timeout);
        this.distance=distance;
        this.speed=speed;
        pid=new PIDLoop(p,i,d);
        this.turbo=turbo;
	}
	
	public DriveStraight(double timeout,double distance,double speed) {
		ss=SuperSystem.getInstance();
        setTimeout(timeout);
        this.distance=distance;
        this.speed=speed;
        pid=new PIDLoop(p,i,d);
	}
	
	protected void initialize() {
		//ss.drivetrain.setState(DriveTrain.states.FIELD_CENTRIC_W_GYRO_HOLD);\
		ss.drivetrain.freeEncoders();
		ss.drivetrain.initEncoders();
		/*double start1=ss.drivetrain.getEncoderDistance(encoderID1);
		double start2=ss.drivetrain.getEncoderDistance(encoderID2);
		startAvg=(start1+start2)/2;*/
		System.out.println("Driving Straight");
		//ss.drivetrain.setTurbo(turbo);
	}
	protected void execute() {
		double distance1=ss.drivetrain.getEncoderDistance(encoderID1);
		double distance2=-ss.drivetrain.getEncoderDistance(encoderID2);
		deltaDistance=(distance1+distance2)/2;
		//deltaDistance-=startAvg;
		
		double powa = pid.correct(distance, -deltaDistance);
		double[] commandVector={0,speed*powa,0};
		System.out.println((speed*powa)+"|"+deltaDistance);
		checkVal=powa;
		ss.drivetrain.setHeading(commandVector);
		
	}
	protected void interrupted() {
		double[] commandVector = {0,0,0};
		ss.drivetrain.setHeading(commandVector);
	}
	protected void end() {
		double[] commandVector = {0,0,0};
		ss.drivetrain.setHeading(commandVector);
	}
	protected boolean isFinished() {	
		return this.isTimedOut() || (checkVal<0.09 && checkVal>-0.09);
	}
}
