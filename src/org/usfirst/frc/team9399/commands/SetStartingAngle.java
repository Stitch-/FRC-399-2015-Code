package org.usfirst.frc.team9399.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team9399.robot.SuperSystem;

public class SetStartingAngle extends Command {
	SuperSystem ss;
	double angle;
	public SetStartingAngle(double ang){
		ss=SuperSystem.getInstance();
		angle=ang;
	}
		
	protected void initialize() {
		ss.drivetrain.setStartingAng(angle);
	}


	protected void execute() {
	}


	protected boolean isFinished() {
		return true;
	}

	protected void end() {
	}
	
	protected void interrupted() {
	}

}
