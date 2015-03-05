package org.usfirst.frc.team9399.commands;

import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc.team9399.robot.SuperSystem;

public class SetGrabber extends Command {
	boolean setVal;
	SuperSystem ss;
	boolean isDone=false;
	
	public SetGrabber(double timeout, boolean in){
		setTimeout(timeout);
		setVal=in;
		ss=SuperSystem.getInstance();
	}
	
	protected void initialize() {
		ss.funkyClips.actuateClaw(setVal);
		isDone=true;
	}

	
	protected void execute() {
	}

	protected boolean isFinished() {
		return isTimedOut()||isDone;
	}


	protected void end() {
	}


	protected void interrupted() {
		ss.funkyClips.actuateClaw(false);
	}

}
