package org.usfirst.frc.team9399.commands;

import org.usfirst.frc.team9399.robot.SuperSystem;
import org.usfirst.frc.team9399.systems.DriveTrain;
import org.usfirst.frc.team9399.util.PIDLoop;

import edu.wpi.first.wpilibj.command.Command;

public class DriveStraight extends Command {
	SuperSystem ss;
	double distance,speed;
	double deltaDistance=0;
	double p=0;
	double i=0;
	double d=0;
	double start;
	PIDLoop pid;
	int encoderID=3; //temporary until I can get an accurate average of all four encoders.

	public DriveStraight(double timeout,double distance,double speed) {
        if (timeout < 0) {
            throw new IllegalArgumentException("Timeout must not be negative.  Given:" + timeout);
        }
		ss=SuperSystem.getInstance();
        setTimeout(timeout);
        this.distance=distance;
        this.speed=speed;
        pid=new PIDLoop(p,i,d);
        start=ss.drivetrain.getEncoderDistance(encoderID);
	}
	protected void initialize() {
		ss.drivetrain.setState(DriveTrain.states.FIELD_CENTRIC_W_GYRO_HOLD);
	}
	protected void execute() {
		deltaDistance=ss.drivetrain.getEncoderDistance(encoderID)-start;
		double powa = pid.correct(distance, deltaDistance);
		double[] commandVector={speed*powa,0,0};
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
		return this.isTimedOut() || deltaDistance==distance;
	}
}
