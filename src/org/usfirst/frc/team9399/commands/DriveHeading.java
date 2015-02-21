package org.usfirst.frc.team9399.commands;

import org.usfirst.frc.team9399.robot.SuperSystem;
//import org.usfirst.frc.team9399.systems.DriveTrain;

import edu.wpi.first.wpilibj.command.Command;

public class DriveHeading extends Command {
	SuperSystem ss;
	double[] heading;
	
	public DriveHeading(double timeout,double[] heading) {
        if (timeout < 0) {
            throw new IllegalArgumentException("Timeout must not be negative.  Given:" + timeout);
        }
		ss=SuperSystem.getInstance();
        setTimeout(timeout);
        this.heading=heading;
	}
	protected void initialize() {
		System.out.println("Driving with a heading");
	}
	protected void execute() {
		ss.drivetrain.setHeading(heading);
		
	}
	protected void interrupted() {
		double[] heading = {0,0,0};
		ss.drivetrain.setHeading(heading);
	}
	protected void end() {
		double[] heading = {0,0,0};
		ss.drivetrain.setHeading(heading);
	}
	protected boolean isFinished() {	
		return this.isTimedOut();
	}
}
