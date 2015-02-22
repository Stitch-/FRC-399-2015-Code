package org.usfirst.frc.team9399.commands;

import org.usfirst.frc.team9399.robot.SuperSystem;
import edu.wpi.first.wpilibj.command.Command;

public class SetIntake extends Command {
	
	SuperSystem ss;
	double speed;
	
	public SetIntake(double timeout,double speed){
		setTimeout(timeout);
		this.speed=speed;
		ss=SuperSystem.getInstance();
	}
	

	protected void initialize() {
		System.out.println("Running intake");
		
		ss.sucker.setWheels(speed);
	}


	protected void execute() {

	}


	protected boolean isFinished() {
		return isTimedOut();
	}

	protected void end() {
		ss.sucker.setWheels(0);
	}


	protected void interrupted() {
		ss.sucker.setWheels(0);
	}

}
