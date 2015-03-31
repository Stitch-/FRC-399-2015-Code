package org.usfirst.frc.team9399.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team9399.robot.SuperSystem;

public class SetIntakeActuators extends Command {
	
	boolean setVal;
	boolean done=false;
	SuperSystem ss;
	
	public SetIntakeActuators(double timeout,boolean in){
		setTimeout(timeout);
		setVal=in;
		ss=SuperSystem.getInstance();
	}

	protected void initialize() {	
		System.out.println("Setting intake actuators");
		ss.sucker.actuateIntake(setVal);
		done=true;
	}
	
	protected void execute() {
		// TODO Auto-generated method stub

	}

	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return isTimedOut()||done;
	}

	protected void end() {
		// TODO Auto-generated method stub

	}

	protected void interrupted() {
		// TODO Auto-generated method stub

	}

}
